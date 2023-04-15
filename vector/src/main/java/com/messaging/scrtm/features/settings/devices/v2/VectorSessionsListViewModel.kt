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

package com.messaging.scrtm.features.settings.devices.v2

import com.airbnb.mvrx.MavericksState
import com.messaging.scrtm.core.di.ActiveSessionHolder
import com.messaging.scrtm.core.platform.VectorViewEvents
import com.messaging.scrtm.core.platform.VectorViewModel
import com.messaging.scrtm.core.platform.VectorViewModelAction
import com.messaging.scrtm.core.utils.PublishDataSource
import com.messaging.lib.core.utils.flow.throttleFirst
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.matrix.android.sdk.api.session.crypto.verification.VerificationService
import org.matrix.android.sdk.api.session.crypto.verification.VerificationTransaction
import org.matrix.android.sdk.api.session.crypto.verification.VerificationTxState
import kotlin.time.Duration.Companion.seconds

abstract class VectorSessionsListViewModel<S : MavericksState, VA : VectorViewModelAction, VE : VectorViewEvents>(
        initialState: S,
        private val activeSessionHolder: ActiveSessionHolder,
        private val refreshDevicesUseCase: RefreshDevicesUseCase,
) : VectorViewModel<S, VA, VE>(initialState), VerificationService.Listener {

    private val refreshSource = PublishDataSource<Unit>()
    private val refreshThrottleDelayMs = 4.seconds.inWholeMilliseconds

    init {
        addVerificationListener()
        observeRefreshSource()
    }

    override fun onCleared() {
        removeVerificationListener()
        super.onCleared()
    }

    private fun addVerificationListener() {
        activeSessionHolder.getSafeActiveSession()
                ?.cryptoService()
                ?.verificationService()
                ?.addListener(this)
    }

    private fun removeVerificationListener() {
        activeSessionHolder.getSafeActiveSession()
                ?.cryptoService()
                ?.verificationService()
                ?.removeListener(this)
    }

    private fun observeRefreshSource() {
        refreshSource.stream()
                .throttleFirst(refreshThrottleDelayMs)
                .onEach { refreshDevicesUseCase.execute() }
                .launchIn(viewModelScope)
    }

    override fun transactionUpdated(tx: VerificationTransaction) {
        if (tx.state == VerificationTxState.Verified) {
            refreshDeviceList()
        }
    }

    /**
     * Force the refresh of the devices list.
     * The devices list is the list of the devices where the user is logged in.
     * It can be any mobile devices, and any browsers.
     */
    fun refreshDeviceList() {
        refreshSource.post(Unit)
    }
}
