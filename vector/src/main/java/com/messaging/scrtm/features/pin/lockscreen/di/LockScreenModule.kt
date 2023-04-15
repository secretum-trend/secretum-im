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

package com.messaging.scrtm.features.pin.lockscreen.di

import android.app.KeyguardManager
import android.content.Context
import androidx.biometric.BiometricManager
import androidx.core.content.getSystemService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.MavericksViewModelKey
import com.messaging.scrtm.features.pin.PinCodeStore
import com.messaging.scrtm.features.pin.lockscreen.configuration.LockScreenConfiguration
import com.messaging.scrtm.features.pin.lockscreen.configuration.LockScreenMode
import com.messaging.scrtm.features.pin.lockscreen.crypto.migrations.LegacyPinCodeMigrator
import com.messaging.scrtm.features.pin.lockscreen.pincode.EncryptedPinCodeStorage
import com.messaging.scrtm.features.pin.lockscreen.ui.LockScreenViewModel
import org.matrix.android.sdk.api.securestorage.SecretStoringUtils
import org.matrix.android.sdk.api.util.BuildVersionSdkIntProvider
import org.matrix.android.sdk.api.util.DefaultBuildVersionSdkIntProvider
import java.security.KeyStore

@Module
@InstallIn(SingletonComponent::class)
object LockScreenModule {

    @Provides
    @PinCodeKeyAlias
    fun providePinCodeKeyAlias(): String = "vector.pin_code"

    @Provides
    @BiometricKeyAlias
    fun provideSystemKeyAlias(): String = "vector.system_v2"

    @Provides
    fun provideKeyStore(): KeyStore = KeyStore.getInstance("AndroidKeyStore").also { it.load(null) }

    @Provides
    fun provideBuildVersionSdkIntProvider(): BuildVersionSdkIntProvider = DefaultBuildVersionSdkIntProvider()

    @Provides
    fun provideLockScreenConfig() = LockScreenConfiguration(
            mode = LockScreenMode.VERIFY,
            pinCodeLength = 4,
            isWeakBiometricsEnabled = false,
            isDeviceCredentialUnlockEnabled = false,
            isStrongBiometricsEnabled = true,
            needsNewCodeValidation = true,
    )

    @Provides
    fun provideBiometricManager(@ApplicationContext context: Context) = BiometricManager.from(context)

    @Provides
    fun provideLegacyPinCodeMigrator(
            @PinCodeKeyAlias pinCodeKeyAlias: String,
            context: Context,
            pinCodeStore: PinCodeStore,
            keyStore: KeyStore,
            buildVersionSdkIntProvider: BuildVersionSdkIntProvider,
    ) = LegacyPinCodeMigrator(
            pinCodeKeyAlias,
            pinCodeStore,
            keyStore,
            SecretStoringUtils(context, keyStore, buildVersionSdkIntProvider),
            buildVersionSdkIntProvider,
    )

    @Provides
    fun provideKeyguardManager(context: Context): KeyguardManager = context.getSystemService()!!
}

@Module
@InstallIn(SingletonComponent::class)
interface LockScreenBindsModule {

    @Binds
    @IntoMap
    @MavericksViewModelKey(LockScreenViewModel::class)
    fun bindLockScreenViewModel(factory: LockScreenViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    fun bindSharedPreferencesStorage(pinCodeStore: PinCodeStore): EncryptedPinCodeStorage
}
