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

package com.messaging.scrtm.features.settings.font

import com.messaging.scrtm.features.settings.FontScalePreferencesImpl
import com.messaging.scrtm.test.fakes.FakeSharedPreferences
import com.messaging.scrtm.test.fakes.FakeSystemSettingsProvider
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class FontScalePreferencesTest {

    private val fakeSharedPreferences = FakeSharedPreferences()
    private val fakeSystemSettingsProvider = FakeSystemSettingsProvider()
    private val fontScalePreferences = FontScalePreferencesImpl(
            preferences = fakeSharedPreferences,
            systemSettingsProvider = fakeSystemSettingsProvider
    )

    @Test
    fun `given app setting is different from system setting and useSystemSetting is set to true, then returns system-level setting`() {
        val scaleOptions = fontScalePreferences.getAvailableScales()
        val tinyScale = scaleOptions[0]
        val normalScale = scaleOptions[2]
        fakeSharedPreferences.givenFontScaleIsSetTo(tinyScale)
        fakeSharedPreferences.givenUseSystemFontScaleIsSetTo(true)
        fakeSystemSettingsProvider.givenSystemFontScaleIs(normalScale.scale)

        fontScalePreferences.getResolvedFontScaleValue() shouldBeEqualTo normalScale
    }

    @Test
    fun `given app setting is different from system setting and useSystemSetting is set to false, then returns app-level setting`() {
        val scaleOptions = fontScalePreferences.getAvailableScales()
        val tinyScale = scaleOptions[0]
        val normalScale = scaleOptions[2]
        fakeSharedPreferences.givenFontScaleIsSetTo(tinyScale)
        fakeSharedPreferences.givenUseSystemFontScaleIsSetTo(false)
        fakeSystemSettingsProvider.givenSystemFontScaleIs(normalScale.scale)

        fontScalePreferences.getResolvedFontScaleValue() shouldBeEqualTo tinyScale
    }
}
