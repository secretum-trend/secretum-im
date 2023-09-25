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

package com.messaging.scrtm.push.fcm

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.R
import com.messaging.scrtm.core.di.ActiveSessionHolder
import com.messaging.scrtm.core.pushers.FcmHelper
import com.messaging.scrtm.core.pushers.PushParser
import com.messaging.scrtm.core.pushers.PushersManager
import com.messaging.scrtm.core.pushers.UnifiedPushHelper
import com.messaging.scrtm.core.pushers.VectorPushHandler
import com.messaging.scrtm.features.settings.VectorPreferences
import org.matrix.android.sdk.api.logger.LoggerTag
import timber.log.Timber
import javax.inject.Inject

private val loggerTag = LoggerTag("Push", LoggerTag.SYNC)

@AndroidEntryPoint
class VectorFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var fcmHelper: FcmHelper
    @Inject lateinit var vectorPreferences: VectorPreferences
    @Inject lateinit var activeSessionHolder: ActiveSessionHolder
    @Inject lateinit var pushersManager: PushersManager
    @Inject lateinit var pushParser: PushParser
    @Inject lateinit var vectorPushHandler: VectorPushHandler
    @Inject lateinit var unifiedPushHelper: UnifiedPushHelper

    override fun onNewToken(token: String) {
        Timber.tag(loggerTag.value).d("New Firebase token")
        Timber.tag("bakdkafjlsdkjfa").d("New Firebase token = $token")

        Timber.tag("bakdkafjlsdkjfa").d("vectorPreferences.areNotificationEnabledForDevice() = ${vectorPreferences.areNotificationEnabledForDevice()}")
        Timber.tag("bakdkafjlsdkjfa").d("activeSessionHolder.hasActiveSession() ${activeSessionHolder.hasActiveSession()}")
        Timber.tag("bakdkafjlsdkjfa").d("unifiedPushHelper.isEmbeddedDistributor() ${unifiedPushHelper.isEmbeddedDistributor()}")

        fcmHelper.storeFcmToken(token)
        if (
                vectorPreferences.areNotificationEnabledForDevice() &&
                activeSessionHolder.hasActiveSession() &&
                unifiedPushHelper.isEmbeddedDistributor()
        ) {
            pushersManager.enqueueRegisterPusher(token, getString(R.string.pusher_http_url))
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Timber.tag(loggerTag.value).d("New Firebase message")
        pushParser.parsePushDataFcm(message.data).let {
            vectorPushHandler.handle(it)
        }
    }
}
