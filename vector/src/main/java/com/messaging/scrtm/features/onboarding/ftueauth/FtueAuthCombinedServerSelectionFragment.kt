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

package com.messaging.scrtm.features.onboarding.ftueauth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.R
import com.messaging.scrtm.core.extensions.associateContentStateWith
import com.messaging.scrtm.core.extensions.clearErrorOnChange
import com.messaging.scrtm.core.extensions.content
import com.messaging.scrtm.core.extensions.editText
import com.messaging.scrtm.core.extensions.realignPercentagesToParent
import com.messaging.scrtm.core.extensions.setOnImeDoneListener
import com.messaging.scrtm.core.extensions.showKeyboard
import com.messaging.scrtm.core.extensions.toReducedUrl
import com.messaging.scrtm.core.utils.ensureProtocol
import com.messaging.scrtm.core.utils.ensureTrailingSlash
import com.messaging.scrtm.core.utils.openUrlInExternalBrowser
import com.messaging.scrtm.databinding.FragmentFtueServerSelectionCombinedBinding
import com.messaging.scrtm.features.onboarding.OnboardingAction
import com.messaging.scrtm.features.onboarding.OnboardingFlow
import com.messaging.scrtm.features.onboarding.OnboardingViewEvents
import com.messaging.scrtm.features.onboarding.OnboardingViewState
import org.matrix.android.sdk.api.failure.isHomeserverUnavailable

@AndroidEntryPoint
class FtueAuthCombinedServerSelectionFragment :
        AbstractFtueAuthFragment<FragmentFtueServerSelectionCombinedBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentFtueServerSelectionCombinedBinding {
        return FragmentFtueServerSelectionCombinedBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        views.chooseServerRoot.realignPercentagesToParent()
        views.chooseServerToolbar.setNavigationOnClickListener {
            viewModel.handle(OnboardingAction.PostViewEvent(OnboardingViewEvents.OnBack))
        }
        views.chooseServerInput.associateContentStateWith(button = views.chooseServerSubmit, enabledPredicate = { canSubmit(it) })
        views.chooseServerInput.setOnImeDoneListener {
            if (canSubmit(views.chooseServerInput.content())) {
                updateServerUrl()
            }
        }
        views.chooseServerGetInTouch.debouncedClicks { openUrlInExternalBrowser(requireContext(), getString(R.string.ftue_ems_url)) }
        views.chooseServerSubmit.debouncedClicks { updateServerUrl() }
        views.chooseServerInput.clearErrorOnChange(viewLifecycleOwner)
    }

    private fun canSubmit(url: String) = url.isNotEmpty()

    private fun updateServerUrl() {
        viewModel.handle(OnboardingAction.HomeServerChange.EditHomeServer(views.chooseServerInput.content().ensureProtocol().ensureTrailingSlash()))
    }

    override fun resetViewModel() {
        // do nothing
    }

    override fun updateWithState(state: OnboardingViewState) {
        views.chooseServerHeaderSubtitle.setText(
                when (state.onboardingFlow) {
                    OnboardingFlow.SignIn -> R.string.ftue_auth_choose_server_sign_in_subtitle
                    OnboardingFlow.SignUp -> R.string.ftue_auth_choose_server_subtitle
                    else -> throw IllegalStateException("Invalid flow state")
                }
        )

        if (views.chooseServerInput.content().isEmpty()) {
            val userUrlInput = state.selectedHomeserver.userFacingUrl?.toReducedUrlKeepingSchemaIfInsecure() ?: viewModel.getDefaultHomeserverUrl()
            views.chooseServerInput.editText().setText(userUrlInput)
        }

        views.chooseServerInput.editText().selectAll()
        views.chooseServerInput.editText().showKeyboard(true)
    }

    override fun onError(throwable: Throwable) {
        views.chooseServerInput.error = when {
            throwable.isHomeserverUnavailable() -> getString(R.string.login_error_homeserver_not_found)
            else -> errorFormatter.toHumanReadable(throwable)
        }
    }

    private fun String.toReducedUrlKeepingSchemaIfInsecure() = toReducedUrl(keepSchema = this.startsWith("http://"))
}
