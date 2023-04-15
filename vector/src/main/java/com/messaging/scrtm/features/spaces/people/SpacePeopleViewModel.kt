/*
 * Copyright (c) 2021 New Vector Ltd
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

package com.messaging.scrtm.features.spaces.people

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.hiltMavericksViewModelFactory
import com.messaging.scrtm.core.platform.VectorViewModel
import com.messaging.scrtm.features.analytics.AnalyticsTracker
import com.messaging.scrtm.features.analytics.plan.CreatedRoom
import com.messaging.scrtm.features.raw.wellknown.getElementWellknown
import com.messaging.scrtm.features.raw.wellknown.isE2EByDefault
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.extensions.orFalse
import org.matrix.android.sdk.api.raw.RawService
import org.matrix.android.sdk.api.session.Session
import org.matrix.android.sdk.api.session.room.model.create.CreateRoomParams

class SpacePeopleViewModel @AssistedInject constructor(
        @Assisted val initialState: SpacePeopleViewState,
        private val rawService: RawService,
        private val session: Session,
        private val analyticsTracker: AnalyticsTracker
) : VectorViewModel<SpacePeopleViewState, SpacePeopleViewAction, SpacePeopleViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<SpacePeopleViewModel, SpacePeopleViewState> {
        override fun create(initialState: SpacePeopleViewState): SpacePeopleViewModel
    }

    companion object : MavericksViewModelFactory<SpacePeopleViewModel, SpacePeopleViewState> by hiltMavericksViewModelFactory()

    override fun handle(action: SpacePeopleViewAction) {
        when (action) {
            is SpacePeopleViewAction.ChatWith -> handleChatWith(action)
            SpacePeopleViewAction.InviteToSpace -> handleInviteToSpace()
        }
    }

    private fun handleInviteToSpace() {
        _viewEvents.post(SpacePeopleViewEvents.InviteToSpace(initialState.spaceId))
    }

    private fun handleChatWith(action: SpacePeopleViewAction.ChatWith) {
        val otherUserId = action.member.userId
        if (otherUserId == session.myUserId) return
        val existingRoomId = session.roomService().getExistingDirectRoomWithUser(otherUserId)
        if (existingRoomId != null) {
            // just open it
            _viewEvents.post(SpacePeopleViewEvents.OpenRoom(existingRoomId))
            return
        }
        setState { copy(createAndInviteState = Loading()) }

        viewModelScope.launch(Dispatchers.IO) {
            val adminE2EByDefault = rawService.getElementWellknown(session.sessionParams)
                    ?.isE2EByDefault()
                    ?: true

            val roomParams = CreateRoomParams()
                    .apply {
                        invitedUserIds.add(otherUserId)
                        setDirectMessage()
                        enableEncryptionIfInvitedUsersSupportIt = adminE2EByDefault
                    }

            try {
                val roomId = session.roomService().createRoom(roomParams)
                analyticsTracker.capture(CreatedRoom(isDM = roomParams.isDirect.orFalse()))
                _viewEvents.post(SpacePeopleViewEvents.OpenRoom(roomId))
                setState { copy(createAndInviteState = Success(roomId)) }
            } catch (failure: Throwable) {
                setState { copy(createAndInviteState = Fail(failure)) }
            }
        }
    }
}
