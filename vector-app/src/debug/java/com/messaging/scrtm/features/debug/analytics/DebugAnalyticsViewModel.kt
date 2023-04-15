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

package com.messaging.scrtm.features.debug.analytics

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.hiltMavericksViewModelFactory
import com.messaging.scrtm.core.platform.EmptyViewEvents
import com.messaging.scrtm.core.platform.VectorViewModel
import com.messaging.scrtm.features.analytics.store.AnalyticsStore
import kotlinx.coroutines.launch

class DebugAnalyticsViewModel @AssistedInject constructor(
        @Assisted initialState: DebugAnalyticsViewState,
        private val analyticsStore: AnalyticsStore
) : VectorViewModel<DebugAnalyticsViewState, DebugAnalyticsViewActions, EmptyViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<DebugAnalyticsViewModel, DebugAnalyticsViewState> {
        override fun create(initialState: DebugAnalyticsViewState): DebugAnalyticsViewModel
    }

    companion object : MavericksViewModelFactory<DebugAnalyticsViewModel, DebugAnalyticsViewState> by hiltMavericksViewModelFactory()

    init {
        observerStore()
    }

    private fun observerStore() {
        analyticsStore.analyticsIdFlow.setOnEach { copy(analyticsId = it) }
        analyticsStore.userConsentFlow.setOnEach { copy(userConsent = it) }
        analyticsStore.didAskUserConsentFlow.setOnEach { copy(didAskUserConsent = it) }
    }

    override fun handle(action: DebugAnalyticsViewActions) {
        when (action) {
            DebugAnalyticsViewActions.ResetAnalyticsOptInDisplayed -> handleResetAnalyticsOptInDisplayed()
        }
    }

    private fun handleResetAnalyticsOptInDisplayed() {
        viewModelScope.launch {
            analyticsStore.setDidAskUserConsent(false)
        }
    }
}
