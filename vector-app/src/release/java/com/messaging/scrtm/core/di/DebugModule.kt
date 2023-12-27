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

package com.messaging.scrtm.core.di

import android.content.Context
import com.messaging.scrtm.core.debug.DebugNavigator
import com.messaging.scrtm.core.debug.DebugReceiver
import com.messaging.scrtm.core.debug.FlipperProxy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import okhttp3.Interceptor
import org.matrix.android.sdk.api.Matrix

@InstallIn(SingletonComponent::class)
@Module
object DebugModule {

    @Provides
    fun providesDebugNavigator() = object : DebugNavigator {
        override fun openDebugMenu(context: Context) {
            // no op
        }
    }

    @Provides
    fun providesDebugReceiver() = object : DebugReceiver {
        override fun register(context: Context) {
            // no op
        }

        override fun unregister(context: Context) {
            // no op
        }
    }

    @Provides
    fun providesFlipperProxy() = object : FlipperProxy {
        override fun init(matrix: Matrix) {
            // no op
        }

        override fun networkInterceptor(): Interceptor? = null
    }

//    @Provides
//    fun providesLeakDetector() = object : LeakDetector {
//        override fun enable(enable: Boolean) {
//            // no op
//        }
//    }
}
