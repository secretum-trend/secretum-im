/*
 * Copyright 2018 New Vector Ltd
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

import androidx.fragment.app.Fragment
import com.messaging.scrtm.core.pushers.UnifiedPushHelper
import com.messaging.scrtm.features.VectorFeatures
import com.messaging.scrtm.features.push.NotificationTroubleshootTestManagerFactory
import com.messaging.scrtm.features.settings.troubleshoot.NotificationTroubleshootTestManager
import com.messaging.scrtm.features.settings.troubleshoot.TestAccountSettings
import com.messaging.scrtm.features.settings.troubleshoot.TestAvailableUnifiedPushDistributors
import com.messaging.scrtm.features.settings.troubleshoot.TestCurrentUnifiedPushDistributor
import com.messaging.scrtm.features.settings.troubleshoot.TestDeviceSettings
import com.messaging.scrtm.features.settings.troubleshoot.TestEndpointAsTokenRegistration
import com.messaging.scrtm.features.settings.troubleshoot.TestNotification
import com.messaging.scrtm.features.settings.troubleshoot.TestPushFromPushGateway
import com.messaging.scrtm.features.settings.troubleshoot.TestPushRulesSettings
import com.messaging.scrtm.features.settings.troubleshoot.TestSystemSettings
import com.messaging.scrtm.features.settings.troubleshoot.TestUnifiedPushEndpoint
import com.messaging.scrtm.features.settings.troubleshoot.TestUnifiedPushGateway
import com.messaging.scrtm.gplay.features.settings.troubleshoot.TestFirebaseToken
import com.messaging.scrtm.gplay.features.settings.troubleshoot.TestPlayServices
import com.messaging.scrtm.gplay.features.settings.troubleshoot.TestTokenRegistration
import javax.inject.Inject

class GoogleNotificationTroubleshootTestManagerFactory @Inject constructor(
        private val unifiedPushHelper: UnifiedPushHelper,
        private val testSystemSettings: TestSystemSettings,
        private val testAccountSettings: TestAccountSettings,
        private val testDeviceSettings: TestDeviceSettings,
        private val testPushRulesSettings: TestPushRulesSettings,
        private val testPlayServices: TestPlayServices,
        private val testFirebaseToken: TestFirebaseToken,
        private val testTokenRegistration: TestTokenRegistration,
        private val testCurrentUnifiedPushDistributor: TestCurrentUnifiedPushDistributor,
        private val testUnifiedPushGateway: TestUnifiedPushGateway,
        private val testUnifiedPushEndpoint: TestUnifiedPushEndpoint,
        private val testAvailableUnifiedPushDistributors: TestAvailableUnifiedPushDistributors,
        private val testEndpointAsTokenRegistration: TestEndpointAsTokenRegistration,
        private val testPushFromPushGateway: TestPushFromPushGateway,
        private val testNotification: TestNotification,
        private val vectorFeatures: VectorFeatures,
) : NotificationTroubleshootTestManagerFactory {

    override fun create(fragment: Fragment): NotificationTroubleshootTestManager {
        val mgr = NotificationTroubleshootTestManager(fragment)
        mgr.addTest(testSystemSettings)
        mgr.addTest(testAccountSettings)
        mgr.addTest(testDeviceSettings)
        mgr.addTest(testPushRulesSettings)
        if (vectorFeatures.allowExternalUnifiedPushDistributors()) {
            mgr.addTest(testAvailableUnifiedPushDistributors)
            mgr.addTest(testCurrentUnifiedPushDistributor)
        }
        if (unifiedPushHelper.isEmbeddedDistributor()) {
            mgr.addTest(testPlayServices)
            mgr.addTest(testFirebaseToken)
            mgr.addTest(testTokenRegistration)
        } else {
            mgr.addTest(testUnifiedPushGateway)
            mgr.addTest(testUnifiedPushEndpoint)
            mgr.addTest(testEndpointAsTokenRegistration)
        }
        mgr.addTest(testPushFromPushGateway)
        mgr.addTest(testNotification)
        return mgr
    }
}
