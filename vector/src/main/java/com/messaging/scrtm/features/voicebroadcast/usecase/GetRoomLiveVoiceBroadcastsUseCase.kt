/*
 * Copyright (c) 2022 New Vector Ltd
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

package com.messaging.scrtm.features.voicebroadcast.usecase

import com.messaging.scrtm.core.di.ActiveSessionHolder
import com.messaging.scrtm.features.voicebroadcast.VoiceBroadcastConstants
import com.messaging.scrtm.features.voicebroadcast.isLive
import com.messaging.scrtm.features.voicebroadcast.model.VoiceBroadcast
import com.messaging.scrtm.features.voicebroadcast.model.VoiceBroadcastEvent
import com.messaging.scrtm.features.voicebroadcast.model.asVoiceBroadcastEvent
import com.messaging.scrtm.features.voicebroadcast.voiceBroadcastId
import org.matrix.android.sdk.api.query.QueryStringValue
import org.matrix.android.sdk.api.session.getRoom
import javax.inject.Inject

/**
 * Get the list of live (not ended) voice broadcast events in the given room.
 */
class GetRoomLiveVoiceBroadcastsUseCase @Inject constructor(
        private val activeSessionHolder: ActiveSessionHolder,
        private val getVoiceBroadcastStateEventUseCase: GetVoiceBroadcastStateEventUseCase,
) {

    fun execute(roomId: String): List<VoiceBroadcastEvent> {
        val session = activeSessionHolder.getSafeActiveSession() ?: return emptyList()
        val room = session.getRoom(roomId) ?: error("Unknown roomId: $roomId")

        return room.stateService().getStateEvents(
                setOf(VoiceBroadcastConstants.STATE_ROOM_VOICE_BROADCAST_INFO),
                QueryStringValue.IsNotEmpty
        )
                .mapNotNull { stateEvent -> stateEvent.asVoiceBroadcastEvent()?.voiceBroadcastId }
                .mapNotNull { voiceBroadcastId -> getVoiceBroadcastStateEventUseCase.execute(VoiceBroadcast(voiceBroadcastId, roomId)) }
                .filter { it.isLive }
    }
}
