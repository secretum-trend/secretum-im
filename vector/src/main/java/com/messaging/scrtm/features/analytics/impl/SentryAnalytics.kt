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

package com.messaging.scrtm.features.analytics.impl

import android.content.Context
import com.messaging.scrtm.features.analytics.AnalyticsConfig
import com.messaging.scrtm.features.analytics.errors.ErrorTracker
import com.messaging.scrtm.features.analytics.log.analyticsTag
import io.sentry.Sentry
import io.sentry.SentryOptions
import io.sentry.android.core.SentryAndroid
import timber.log.Timber
import javax.inject.Inject

class SentryAnalytics @Inject constructor(
        private val context: Context,
        private val analyticsConfig: AnalyticsConfig,
) : ErrorTracker {

    fun initSentry() {
        Timber.tag(analyticsTag.value).d("Initializing Sentry")
        if (Sentry.isEnabled()) return
        SentryAndroid.init(context) { options ->
            options.dsn = analyticsConfig.sentryDSN
            options.beforeSend = SentryOptions.BeforeSendCallback { event, _ -> event }
            options.tracesSampleRate = 1.0
            options.isEnableUserInteractionTracing = true
            options.environment = analyticsConfig.sentryEnvironment
            options.diagnosticLevel
        }
    }

    fun stopSentry() {
        Timber.tag(analyticsTag.value).d("Stopping Sentry")
        Sentry.close()
    }

    override fun trackError(throwable: Throwable) {
        Sentry.captureException(throwable)
    }
}
