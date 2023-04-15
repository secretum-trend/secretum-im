/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.messaging.scrtm

import androidx.lifecycle.LifecycleOwner
import com.messaging.scrtm.core.di.ActiveSessionHolder
import com.messaging.scrtm.core.utils.BehaviorDataSource
import com.messaging.scrtm.features.analytics.AnalyticsTracker
import com.messaging.scrtm.features.analytics.plan.UserProperties
import com.messaging.scrtm.features.analytics.plan.ViewRoom
import com.messaging.scrtm.features.session.coroutineScope
import com.messaging.scrtm.features.settings.VectorPreferences
import com.messaging.scrtm.features.ui.UiStateRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.extensions.tryOrNull
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.getRoom
import org.matrix.android.sdk.api.session.getRoomSummary
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.sync.SyncRequestState
import org.matrix.android.sdk.api.util.Optional
import org.matrix.android.sdk.api.util.toOption
import javax.inject.Inject
import javax.inject.Singleton

/**
 * This class handles the global app state.
 * It is required that this class is added as an observer to ProcessLifecycleOwner.get().lifecycle in [VectorApplication]
 */
@Singleton
class SpaceStateHandlerImpl @Inject constructor(
        private val sessionDataSource: ActiveSessionDataSource,
        private val uiStateRepository: UiStateRepository,
        private val activeSessionHolder: ActiveSessionHolder,
        private val analyticsTracker: AnalyticsTracker,
        private val vectorPreferences: VectorPreferences,
) : SpaceStateHandler {

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    private val selectedSpaceDataSource = BehaviorDataSource<Optional<RoomSummary>>(Optional.empty())
    private val selectedSpaceFlow = selectedSpaceDataSource.stream()

    override fun getCurrentSpace(): RoomSummary? {
        return selectedSpaceDataSource.currentValue?.orNull()?.let { spaceSummary ->
            activeSessionHolder.getSafeActiveSession()?.roomService()?.getRoomSummary(spaceSummary.roomId)
        }
    }

    override fun setCurrentSpace(
            spaceId: String?,
            session: Session?,
            persistNow: Boolean,
            isForwardNavigation: Boolean,
    ) {
        val activeSession = session ?: activeSessionHolder.getSafeActiveSession() ?: return
        val spaceToLeave = selectedSpaceDataSource.currentValue?.orNull()
        val spaceToSet = spaceId?.let { activeSession.getRoomSummary(spaceId) }
        val sameSpaceSelected = spaceId == spaceToLeave?.roomId

        if (sameSpaceSelected) {
            return
        }

        analyticsTracker.capture(
                ViewRoom(
                        isDM = false,
                        isSpace = true,
                )
        )

        if (isForwardNavigation) {
            addToBackstack(spaceToLeave, spaceToSet)
        }

        if (persistNow) {
            uiStateRepository.storeSelectedSpace(spaceToSet?.roomId, activeSession.sessionId)
        }

        selectedSpaceDataSource.post(spaceToSet.toOption())

        if (spaceId != null) {
            activeSession.coroutineScope.launch(Dispatchers.IO) {
                tryOrNull {
                    activeSession.getRoom(spaceId)?.membershipService()?.loadRoomMembersIfNeeded()
                }
            }
        }
    }

    private fun addToBackstack(spaceToLeave: RoomSummary?, spaceToSet: RoomSummary?) {
        // Only add to the backstack if the space to set is not All Chats, else clear the backstack
        if (spaceToSet != null) {
            val currentPersistedBackstack = vectorPreferences.getSpaceBackstack().toMutableList()
            currentPersistedBackstack.add(spaceToLeave?.roomId)
            vectorPreferences.setSpaceBackstack(currentPersistedBackstack)
        } else {
            vectorPreferences.setSpaceBackstack(emptyList())
        }
    }

    private fun observeActiveSession() {
        sessionDataSource.stream()
                .distinctUntilChanged()
                .onEach {
                    // sessionDataSource could already return a session while activeSession holder still returns null
                    it.orNull()?.let { session ->
                        setCurrentSpace(uiStateRepository.getSelectedSpace(session.sessionId), session)
                        observeSyncStatus(session)
                    }
                }
                .launchIn(coroutineScope)
    }

    private fun observeSyncStatus(session: Session) {
        session.syncService().getSyncRequestStateFlow()
                .filterIsInstance<SyncRequestState.IncrementalSyncDone>()
                .map { session.spaceService().getRootSpaceSummaries().size }
                .distinctUntilChanged()
                .onEach { spacesNumber ->
                    analyticsTracker.updateUserProperties(UserProperties(numSpaces = spacesNumber))
                }.launchIn(session.coroutineScope)
    }

    override fun popSpaceBackstack(): String? {
        vectorPreferences.getSpaceBackstack().toMutableList().apply {
            val poppedSpaceId = removeLast()
            vectorPreferences.setSpaceBackstack(this)
            return poppedSpaceId
        }
    }

    override fun getSpaceBackstack() = vectorPreferences.getSpaceBackstack()

    override fun getSelectedSpaceFlow() = selectedSpaceFlow

    override fun getSafeActiveSpaceId(): String? {
        return selectedSpaceDataSource.currentValue?.orNull()?.roomId
    }

    override fun onResume(owner: LifecycleOwner) {
        observeActiveSession()
    }

    override fun onPause(owner: LifecycleOwner) {
        coroutineScope.coroutineContext.cancelChildren()
        val session = activeSessionHolder.getSafeActiveSession() ?: return
        uiStateRepository.storeSelectedSpace(selectedSpaceDataSource.currentValue?.orNull()?.roomId, session.sessionId)
    }
}
