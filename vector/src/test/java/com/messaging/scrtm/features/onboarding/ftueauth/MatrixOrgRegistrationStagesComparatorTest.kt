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

package com.messaging.scrtm.features.onboarding.ftueauth

import com.messaging.scrtm.test.fixtures.aDummyStage
import com.messaging.scrtm.test.fixtures.aMsisdnStage
import com.messaging.scrtm.test.fixtures.aRecaptchaStage
import com.messaging.scrtm.test.fixtures.aTermsStage
import com.messaging.scrtm.test.fixtures.anEmailStage
import com.messaging.scrtm.test.fixtures.anOtherStage
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class MatrixOrgRegistrationStagesComparatorTest {

    @Test
    fun `when ordering stages, then prioritizes email`() {
        val input = listOf(
                aDummyStage(),
                anOtherStage(),
                aMsisdnStage(),
                anEmailStage(),
                aRecaptchaStage(),
                aTermsStage()
        )

        val result = input.sortedWith(MatrixOrgRegistrationStagesComparator())

        result shouldBeEqualTo listOf(
                anEmailStage(),
                aMsisdnStage(),
                aTermsStage(),
                aRecaptchaStage(),
                anOtherStage(),
                aDummyStage()
        )
    }
}
