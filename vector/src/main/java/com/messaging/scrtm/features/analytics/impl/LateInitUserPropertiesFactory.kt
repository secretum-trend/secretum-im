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
import com.messaging.scrtm.ActiveSessionDataSource
import com.messaging.scrtm.core.extensions.vectorStore
import com.messaging.scrtm.features.analytics.extensions.toTrackingValue
import com.messaging.scrtm.features.analytics.plan.UserProperties
import javax.inject.Inject

class LateInitUserPropertiesFactory @Inject constructor(
        private val activeSessionDataSource: ActiveSessionDataSource,
        private val context: Context,
) {
    suspend fun createUserProperties(): UserProperties? {
        val useCase = activeSessionDataSource.currentValue?.orNull()?.vectorStore(context)?.readUseCase()
        return useCase?.let {
            UserProperties(ftueUseCaseSelection = it.toTrackingValue())
        }
    }
}
