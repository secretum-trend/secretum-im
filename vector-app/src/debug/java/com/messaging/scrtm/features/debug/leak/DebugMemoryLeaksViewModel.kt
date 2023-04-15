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

package com.messaging.scrtm.features.debug.leak

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.messaging.scrtm.core.debug.LeakDetector
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.hiltMavericksViewModelFactory
import com.messaging.scrtm.core.platform.EmptyViewEvents
import com.messaging.scrtm.core.platform.VectorViewModel
import com.messaging.scrtm.features.settings.VectorPreferences
import kotlinx.coroutines.launch

class DebugMemoryLeaksViewModel @AssistedInject constructor(
        @Assisted initialState: DebugMemoryLeaksViewState,
        private val vectorPreferences: VectorPreferences,
        private val leakDetector: LeakDetector,
) : VectorViewModel<DebugMemoryLeaksViewState, DebugMemoryLeaksViewActions, EmptyViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<DebugMemoryLeaksViewModel, DebugMemoryLeaksViewState> {
        override fun create(initialState: DebugMemoryLeaksViewState): DebugMemoryLeaksViewModel
    }

    companion object : MavericksViewModelFactory<DebugMemoryLeaksViewModel, DebugMemoryLeaksViewState> by hiltMavericksViewModelFactory()

    init {
        viewModelScope.launch {
            refreshStateFromPreferences()
        }
    }

    override fun handle(action: DebugMemoryLeaksViewActions) {
        when (action) {
            is DebugMemoryLeaksViewActions.EnableMemoryLeaksAnalysis -> handleEnableMemoryLeaksAnalysis(action)
        }
    }

    private fun handleEnableMemoryLeaksAnalysis(action: DebugMemoryLeaksViewActions.EnableMemoryLeaksAnalysis) {
        viewModelScope.launch {
            vectorPreferences.enableMemoryLeakAnalysis(action.isEnabled)
            leakDetector.enable(action.isEnabled)
            refreshStateFromPreferences()
        }
    }

    private fun refreshStateFromPreferences() {
        setState { copy(isMemoryLeaksAnalysisEnabled = vectorPreferences.isMemoryLeakAnalysisEnabled()) }
    }
}
