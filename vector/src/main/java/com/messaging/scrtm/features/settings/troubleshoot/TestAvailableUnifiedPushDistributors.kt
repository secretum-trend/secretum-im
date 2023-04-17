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

package com.messaging.scrtm.features.settings.troubleshoot

import com.messaging.scrtm.R
import com.messaging.scrtm.core.pushers.FcmHelper
import com.messaging.scrtm.core.pushers.UnifiedPushHelper
import com.messaging.scrtm.core.resources.StringProvider
import javax.inject.Inject

class TestAvailableUnifiedPushDistributors @Inject constructor(
        private val unifiedPushHelper: UnifiedPushHelper,
        private val stringProvider: StringProvider,
        private val fcmHelper: FcmHelper,
) : TroubleshootTest(R.string.settings_troubleshoot_test_distributors_title) {

    override fun perform(testParameters: TestParameters) {
        val distributors = unifiedPushHelper.getExternalDistributors()
        description = if (distributors.isEmpty()) {
            stringProvider.getString(
                    if (fcmHelper.isFirebaseAvailable()) {
                        R.string.settings_troubleshoot_test_distributors_gplay
                    } else {
                        R.string.settings_troubleshoot_test_distributors_fdroid
                    }
            )
        } else {
            val quantity = distributors.size + 1
            stringProvider.getQuantityString(R.plurals.settings_troubleshoot_test_distributors_many, quantity, quantity)
        }
        status = TestStatus.SUCCESS
    }
}