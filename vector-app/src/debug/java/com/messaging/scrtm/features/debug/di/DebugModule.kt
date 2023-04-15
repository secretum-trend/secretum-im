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

package com.messaging.scrtm.features.debug.di

import android.content.Context
import android.content.Intent
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.messaging.scrtm.core.debug.DebugNavigator
import com.messaging.scrtm.core.debug.DebugReceiver
import com.messaging.scrtm.core.debug.FlipperProxy
import com.messaging.scrtm.core.debug.LeakDetector
import com.messaging.scrtm.features.debug.DebugMenuActivity
import com.messaging.scrtm.flipper.VectorFlipperProxy
import com.messaging.scrtm.leakcanary.LeakCanaryLeakDetector
import com.messaging.scrtm.receivers.VectorDebugReceiver

@InstallIn(SingletonComponent::class)
@Module
abstract class DebugModule {

    companion object {

        @Provides
        fun providesDebugNavigator() = object : DebugNavigator {
            override fun openDebugMenu(context: Context) {
                context.startActivity(Intent(context, DebugMenuActivity::class.java))
            }
        }
    }

    @Binds
    abstract fun bindsDebugReceiver(receiver: VectorDebugReceiver): DebugReceiver

    @Binds
    abstract fun bindsFlipperProxy(flipperProxy: VectorFlipperProxy): FlipperProxy

    @Binds
    abstract fun bindsLeakDetector(leakDetector: LeakCanaryLeakDetector): LeakDetector
}
