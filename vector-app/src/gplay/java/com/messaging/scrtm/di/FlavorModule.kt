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

package com.messaging.scrtm.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.messaging.scrtm.GoogleFlavorLegals
import com.messaging.scrtm.core.pushers.FcmHelper
import com.messaging.scrtm.core.resources.AppNameProvider
import com.messaging.scrtm.core.resources.DefaultAppNameProvider
import com.messaging.scrtm.core.resources.DefaultLocaleProvider
import com.messaging.scrtm.core.resources.LocaleProvider
import com.messaging.scrtm.core.services.GuardServiceStarter
import com.messaging.scrtm.features.home.NightlyProxy
import com.messaging.scrtm.features.settings.legals.FlavorLegals
import com.messaging.scrtm.nightly.FirebaseNightlyProxy
import com.messaging.scrtm.push.fcm.GoogleFcmHelper

@InstallIn(SingletonComponent::class)
@Module
abstract class FlavorModule {

    companion object {
        @Provides
        fun provideGuardServiceStarter(): GuardServiceStarter {
            return object : GuardServiceStarter {}
        }
    }

    @Binds
    abstract fun bindsNightlyProxy(nightlyProxy: FirebaseNightlyProxy): NightlyProxy

    @Binds
    abstract fun bindsFcmHelper(fcmHelper: GoogleFcmHelper): FcmHelper

    @Binds
    abstract fun bindsLocaleProvider(localeProvider: DefaultLocaleProvider): LocaleProvider

    @Binds
    abstract fun bindsAppNameProvider(appNameProvider: DefaultAppNameProvider): AppNameProvider

    @Binds
    abstract fun bindsFlavorLegals(legals: GoogleFlavorLegals): FlavorLegals
}