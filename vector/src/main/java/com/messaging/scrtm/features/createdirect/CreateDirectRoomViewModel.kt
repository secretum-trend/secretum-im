/*
 * Copyright (c) 2020 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.messaging.scrtm.features.createdirect

import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.hiltMavericksViewModelFactory
import com.messaging.scrtm.core.mvrx.runCatchingToAsync
import com.messaging.scrtm.core.platform.VectorViewModel
import com.messaging.scrtm.features.analytics.AnalyticsTracker
import com.messaging.scrtm.features.analytics.plan.CreatedRoom
import com.messaging.scrtm.features.raw.wellknown.getElementWellknown
import com.messaging.scrtm.features.raw.wellknown.isE2EByDefault
import com.messaging.scrtm.features.settings.VectorPreferences
import com.messaging.scrtm.features.userdirectory.PendingSelection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.extensions.orFalse
import org.matrix.android.sdk.api.raw.RawService
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.getUserOrDefault
import org.matrix.android.sdk.api.session.permalinks.PermalinkData
import org.matrix.android.sdk.api.session.permalinks.PermalinkParser
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomParams

class CreateDirectRoomViewModel @AssistedInject constructor(
        @Assisted initialState: CreateDirectRoomViewState,
        private val rawService: RawService,
        private val vectorPreferences: VectorPreferences,
        val session: Session,
        val analyticsTracker: AnalyticsTracker,
) :
        VectorViewModel<CreateDirectRoomViewState, CreateDirectRoomAction, CreateDirectRoomViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<CreateDirectRoomViewModel, CreateDirectRoomViewState> {
        override fun create(initialState: CreateDirectRoomViewState): CreateDirectRoomViewModel
    }

    companion object : MavericksViewModelFactory<CreateDirectRoomViewModel, CreateDirectRoomViewState> by hiltMavericksViewModelFactory()

    override fun handle(action: CreateDirectRoomAction) {
        when (action) {
            is CreateDirectRoomAction.PrepareRoomWithSelectedUsers -> onSubmitInvitees(action.selections)
            is CreateDirectRoomAction.CreateRoomAndInviteSelectedUsers -> onCreateRoomWithInvitees()
            is CreateDirectRoomAction.QrScannedAction -> onCodeParsed(action)
        }
    }

    private fun onCodeParsed(action: CreateDirectRoomAction.QrScannedAction) {
        val mxid = (PermalinkParser.parse(action.result) as? PermalinkData.UserLink)?.userId

        if (mxid === null) {
            _viewEvents.post(CreateDirectRoomViewEvents.InvalidCode)
        } else {
            // The following assumes MXIDs are case insensitive
            if (mxid.equals(other = session.myUserId, ignoreCase = true)) {
                _viewEvents.post(CreateDirectRoomViewEvents.DmSelf)
            } else {
                // Try to get user from known users and fall back to creating a User object from MXID
                val qrInvitee = session.getUserOrDefault(mxid)
                onSubmitInvitees(setOf(PendingSelection.UserPendingSelection(qrInvitee)))
            }
        }
    }

    /**
     * If users already have a DM room then navigate to it instead of creating a new room.
     */
    private fun onSubmitInvitees(selections: Set<PendingSelection>) {
        val existingRoomId = selections.singleOrNull()?.getMxId()?.let { userId ->
            session.roomService().getExistingDirectRoomWithUser(userId)
        }
        if (existingRoomId != null) {
            // Do not create a new DM, just tell that the creation is successful by passing the existing roomId
            setState { copy(createAndInviteState = Success(existingRoomId)) }
        } else {
            createLocalRoomWithSelectedUsers(selections)
        }
    }

    private fun onCreateRoomWithInvitees() {
        // Create the DM
        withState { createLocalRoomWithSelectedUsers(it.pendingSelections) }
    }

    private fun createLocalRoomWithSelectedUsers(selections: Set<PendingSelection>) {
        setState { copy(createAndInviteState = Loading()) }

        viewModelScope.launch(Dispatchers.IO) {
            val adminE2EByDefault = rawService.getElementWellknown(session.sessionParams)
                    ?.isE2EByDefault()
                    ?: true

            val roomParams = CreateRoomParams()
                    .apply {
                        selections.forEach {
                            when (it) {
                                is PendingSelection.UserPendingSelection -> invitedUserIds.add(it.user.userId)
                                is PendingSelection.ThreePidPendingSelection -> invite3pids.add(it.threePid)
                            }
                        }
                        setDirectMessage()
                        enableEncryptionIfInvitedUsersSupportIt = adminE2EByDefault
                    }

            val result = runCatchingToAsync {
                if (vectorPreferences.isDeferredDmEnabled()) {
                    session.roomService().createLocalRoom(roomParams)
                } else {
                    analyticsTracker.capture(CreatedRoom(isDM = roomParams.isDirect.orFalse()))
                    session.roomService().createRoom(roomParams)
                }
            }

            setState {
                copy(
                        createAndInviteState = result
                )
            }
        }
    }
}
