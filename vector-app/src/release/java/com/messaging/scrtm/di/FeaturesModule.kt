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

package com.messaging.scrtm.features.debug.di

import com.messaging.scrtm.features.DefaultVectorFeatures
import com.messaging.scrtm.features.DefaultVectorOverrides
import com.messaging.scrtm.features.VectorFeatures
import com.messaging.scrtm.features.VectorOverrides
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
interface FeaturesModule {

    @Binds
    fun bindFeatures(debugFeatures: DefaultVectorFeatures): VectorFeatures

    @Binds
    fun bindOverrides(debugOverrides: DefaultVectorOverrides): VectorOverrides

    companion object {

        @Provides
        fun providesDefaultVectorFeatures(): DefaultVectorFeatures {
            return DefaultVectorFeatures()
        }

        @Provides
        fun providesDefaultVectorOverrides(): DefaultVectorOverrides {
            return DefaultVectorOverrides()
        }

    }
}
