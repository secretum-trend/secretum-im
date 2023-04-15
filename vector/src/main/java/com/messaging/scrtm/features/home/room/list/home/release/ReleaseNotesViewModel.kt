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

package com.messaging.scrtm.features.home.room.list.home.release

import com.airbnb.mvrx.MavericksViewModelFactory
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.hiltMavericksViewModelFactory
import com.messaging.scrtm.core.platform.VectorDummyViewState
import com.messaging.scrtm.core.platform.VectorViewModel

class ReleaseNotesViewModel @AssistedInject constructor(
        @Assisted initialState: VectorDummyViewState,
) : VectorViewModel<VectorDummyViewState, ReleaseNotesAction, ReleaseNotesViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<ReleaseNotesViewModel, VectorDummyViewState> {
        override fun create(initialState: VectorDummyViewState): ReleaseNotesViewModel
    }

    companion object : MavericksViewModelFactory<ReleaseNotesViewModel, VectorDummyViewState> by hiltMavericksViewModelFactory()

    private var selectedPageIndex = 0

    init {
        _viewEvents.post(ReleaseNotesViewEvents.SelectPage(0))
    }

    override fun handle(action: ReleaseNotesAction) {
        when (action) {
            is ReleaseNotesAction.NextPressed -> handleNextPressed(action)
            is ReleaseNotesAction.PageSelected -> handlePageSelected(action)
        }
    }

    private fun handlePageSelected(action: ReleaseNotesAction.PageSelected) {
        selectedPageIndex = action.selectedPageIndex
    }

    private fun handleNextPressed(action: ReleaseNotesAction.NextPressed) {
        if (action.isLastItemSelected) {
            _viewEvents.post(ReleaseNotesViewEvents.Close)
        } else {
            _viewEvents.post(ReleaseNotesViewEvents.SelectPage(++selectedPageIndex))
        }
    }
}
