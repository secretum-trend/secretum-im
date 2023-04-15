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

package com.messaging.scrtm.core.di

import android.content.Context
import android.os.Build
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.messaging.scrtm.features.voicebroadcast.listening.VoiceBroadcastPlayer
import com.messaging.scrtm.features.voicebroadcast.listening.VoiceBroadcastPlayerImpl
import com.messaging.scrtm.features.voicebroadcast.recording.VoiceBroadcastRecorder
import com.messaging.scrtm.features.voicebroadcast.recording.VoiceBroadcastRecorderQ
import com.messaging.scrtm.features.voicebroadcast.usecase.GetVoiceBroadcastStateEventLiveUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class VoiceModule {

    companion object {
        @Provides
        @Singleton
        fun providesVoiceBroadcastRecorder(
                context: Context,
                sessionHolder: ActiveSessionHolder,
                getVoiceBroadcastStateEventLiveUseCase: GetVoiceBroadcastStateEventLiveUseCase,
        ): VoiceBroadcastRecorder? {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                VoiceBroadcastRecorderQ(
                        context = context,
                        sessionHolder = sessionHolder,
                        getVoiceBroadcastEventUseCase = getVoiceBroadcastStateEventLiveUseCase
                )
            } else {
                null
            }
        }
    }

    @Binds
    abstract fun bindVoiceBroadcastPlayer(player: VoiceBroadcastPlayerImpl): VoiceBroadcastPlayer
}
