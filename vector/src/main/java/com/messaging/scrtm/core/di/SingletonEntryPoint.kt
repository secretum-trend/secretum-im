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

package com.messaging.scrtm.core.di

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.messaging.scrtm.core.dialogs.UnrecognizedCertificateDialog
import com.messaging.scrtm.core.error.ErrorFormatter
import com.messaging.scrtm.core.time.Clock
import com.messaging.scrtm.features.analytics.AnalyticsTracker
import com.messaging.scrtm.features.call.webrtc.WebRtcCallManager
import com.messaging.scrtm.features.home.AvatarRenderer
import com.messaging.scrtm.features.navigation.Navigator
import com.messaging.scrtm.features.pin.PinLocker
import com.messaging.scrtm.features.rageshake.BugReporter
import com.messaging.scrtm.features.session.SessionListener
import com.messaging.scrtm.features.settings.VectorPreferences
import com.messaging.scrtm.features.ui.UiStateRepository
import kotlinx.coroutines.CoroutineScope

@InstallIn(SingletonComponent::class)
@EntryPoint
interface SingletonEntryPoint {

    fun sessionListener(): SessionListener

    fun avatarRenderer(): AvatarRenderer

    fun activeSessionHolder(): ActiveSessionHolder

    fun unrecognizedCertificateDialog(): UnrecognizedCertificateDialog

    fun navigator(): Navigator

    fun clock(): Clock

    fun errorFormatter(): ErrorFormatter

    fun bugReporter(): BugReporter

    fun vectorPreferences(): VectorPreferences

    fun uiStateRepository(): UiStateRepository

    fun pinLocker(): PinLocker

    fun analyticsTracker(): AnalyticsTracker

    fun webRtcCallManager(): WebRtcCallManager

    fun appCoroutineScope(): CoroutineScope
}
