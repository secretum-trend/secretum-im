/*
 * Copyright 2019 New Vector Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messaging.scrtm.features.settings.push

import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import dagger.hilt.EntryPoints
import com.messaging.scrtm.core.di.SingletonEntryPoint
import com.messaging.scrtm.core.platform.EmptyAction
import com.messaging.scrtm.core.platform.EmptyViewEvents
import com.messaging.scrtm.core.platform.VectorViewModel
import org.matrix.android.sdk.api.session.pushrules.rest.PushRule

data class PushRulesViewState(
        val rules: List<PushRule> = emptyList()
) : MavericksState

class PushRulesViewModel(initialState: PushRulesViewState) :
        VectorViewModel<PushRulesViewState, EmptyAction, EmptyViewEvents>(initialState) {

    companion object : MavericksViewModelFactory<PushRulesViewModel, PushRulesViewState> {

        override fun initialState(viewModelContext: ViewModelContext): PushRulesViewState? {
            val session = EntryPoints.get(viewModelContext.app(), SingletonEntryPoint::class.java).activeSessionHolder().getActiveSession()
            val rules = session.pushRuleService().getPushRules().getAllRules()
            return PushRulesViewState(rules)
        }
    }

    override fun handle(action: EmptyAction) {
        // No op
    }
}
