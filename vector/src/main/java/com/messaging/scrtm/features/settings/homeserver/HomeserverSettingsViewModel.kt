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

package com.messaging.scrtm.features.settings.homeserver

import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.Success
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.hiltMavericksViewModelFactory
import com.messaging.scrtm.core.platform.EmptyViewEvents
import com.messaging.scrtm.core.platform.VectorViewModel
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.session.Session

class HomeserverSettingsViewModel @AssistedInject constructor(
        @Assisted initialState: HomeServerSettingsViewState,
        private val session: Session
) : VectorViewModel<HomeServerSettingsViewState, HomeserverSettingsAction, EmptyViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<HomeserverSettingsViewModel, HomeServerSettingsViewState> {
        override fun create(initialState: HomeServerSettingsViewState): HomeserverSettingsViewModel
    }

    companion object : MavericksViewModelFactory<HomeserverSettingsViewModel, HomeServerSettingsViewState> by hiltMavericksViewModelFactory()

    init {
        setState {
            copy(
                    homeserverUrl = session.sessionParams.homeServerUrl,
                    homeserverClientServerApiUrl = session.sessionParams.homeServerUrlBase,
                    homeServerCapabilities = session.homeServerCapabilitiesService().getHomeServerCapabilities()
            )
        }
        fetchHomeserverVersion()
        refreshHomeServerCapabilities()
    }

    private fun refreshHomeServerCapabilities() {
        viewModelScope.launch {
            runCatching {
                session.homeServerCapabilitiesService().refreshHomeServerCapabilities()
            }

            setState {
                copy(
                        homeServerCapabilities = session.homeServerCapabilitiesService().getHomeServerCapabilities()
                )
            }
        }
    }

    private fun fetchHomeserverVersion() {
        setState {
            copy(
                    federationVersion = Loading()
            )
        }

        viewModelScope.launch {
            try {
                val federationVersion = session.federationService().getFederationVersion()
                setState {
                    copy(
                            federationVersion = Success(federationVersion)
                    )
                }
            } catch (failure: Throwable) {
                setState {
                    copy(
                            federationVersion = Fail(failure)
                    )
                }
            }
        }
    }

    override fun handle(action: HomeserverSettingsAction) {
        when (action) {
            HomeserverSettingsAction.Refresh -> fetchHomeserverVersion()
        }
    }
}
