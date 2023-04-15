/*
 * Copyright 2019 New Vector Ltd
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
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.airbnb.mvrx.withState
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.R
import com.messaging.scrtm.core.extensions.toReducedUrl
import com.messaging.scrtm.databinding.FragmentLoginSignupSigninSelectionBinding
import com.messaging.scrtm.features.login.LoginMode
import com.messaging.scrtm.features.login.SSORedirectRouterActivity
import com.messaging.scrtm.features.login.ServerType
import com.messaging.scrtm.features.login.SignMode
import com.messaging.scrtm.features.login.SocialLoginButtonsView.Mode
import com.messaging.scrtm.features.login.render
import com.messaging.scrtm.features.onboarding.OnboardingAction
import com.messaging.scrtm.features.onboarding.OnboardingViewState

/**
 * In this screen, the user is asked to sign up or to sign in to the homeserver.
 */
@AndroidEntryPoint
class FtueAuthSignUpSignInSelectionFragment :
        AbstractSSOFtueAuthFragment<FragmentLoginSignupSigninSelectionBinding>() {

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginSignupSigninSelectionBinding {
        return FragmentLoginSignupSigninSelectionBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
    }

    private fun setupViews() {
        views.loginSignupSigninSubmit.setOnClickListener { submit() }
        views.loginSignupSigninSignIn.setOnClickListener { signIn() }
    }

    private fun render(state: OnboardingViewState) {
        when (state.serverType) {
            ServerType.MatrixOrg -> renderServerInformation(
                    icon = R.drawable.ic_logo_matrix_org,
                    title = getString(R.string.login_connect_to, state.selectedHomeserver.userFacingUrl.toReducedUrl()),
                    subtitle = getString(R.string.login_server_matrix_org_text)
            )
            ServerType.EMS -> renderServerInformation(
                    icon = R.drawable.ic_logo_element_matrix_services,
                    title = getString(R.string.login_connect_to_modular),
                    subtitle = state.selectedHomeserver.userFacingUrl.toReducedUrl()
            )
            ServerType.Other -> renderServerInformation(
                    icon = null,
                    title = getString(R.string.login_server_other_title),
                    subtitle = getString(R.string.login_connect_to, state.selectedHomeserver.userFacingUrl.toReducedUrl())
            )
            ServerType.Unknown -> Unit /* Should not happen */
        }

        when (state.selectedHomeserver.preferredLoginMode) {
            is LoginMode.SsoAndPassword -> {
                views.loginSignupSigninSignInSocialLoginContainer.isVisible = true
                views.loginSignupSigninSocialLoginButtons.render(state.selectedHomeserver.preferredLoginMode.ssoState, Mode.MODE_CONTINUE) { provider ->
                    viewModel.fetchSsoUrl(
                            redirectUrl = SSORedirectRouterActivity.VECTOR_REDIRECT_URL,
                            deviceId = state.deviceId,
                            provider = provider
                    )
                            ?.let { openInCustomTab(it) }
                }
            }
            else -> {
                // SSO only is managed without container as well as No sso
                views.loginSignupSigninSignInSocialLoginContainer.isVisible = false
                views.loginSignupSigninSocialLoginButtons.ssoIdentityProviders = null
            }
        }
    }

    private fun renderServerInformation(@DrawableRes icon: Int?, title: String, subtitle: String) {
        icon?.let { views.loginSignupSigninServerIcon.setImageResource(it) }
        views.loginSignupSigninServerIcon.isVisible = icon != null
        views.loginSignupSigninServerIcon.setImageResource(R.drawable.ic_logo_matrix_org)
        views.loginSignupSigninTitle.text = title
        views.loginSignupSigninText.text = subtitle
    }

    private fun setupButtons(state: OnboardingViewState) {
        when (state.selectedHomeserver.preferredLoginMode) {
            is LoginMode.Sso -> {
                // change to only one button that is sign in with sso
                views.loginSignupSigninSubmit.text = getString(R.string.login_signin_sso)
                views.loginSignupSigninSignIn.isVisible = false
            }
            else -> {
                views.loginSignupSigninSubmit.text = getString(R.string.login_signup)
                views.loginSignupSigninSignIn.isVisible = true
            }
        }
    }

    private fun submit() = withState(viewModel) { state ->
        if (state.selectedHomeserver.preferredLoginMode is LoginMode.Sso) {
            viewModel.fetchSsoUrl(
                    redirectUrl = SSORedirectRouterActivity.VECTOR_REDIRECT_URL,
                    deviceId = state.deviceId,
                    provider = null
            )
                    ?.let { openInCustomTab(it) }
        } else {
            viewModel.handle(OnboardingAction.UpdateSignMode(SignMode.SignUp))
        }
    }

    private fun signIn() {
        viewModel.handle(OnboardingAction.UpdateSignMode(SignMode.SignIn))
    }

    override fun resetViewModel() {
        viewModel.handle(OnboardingAction.ResetSignMode)
    }

    override fun updateWithState(state: OnboardingViewState) {
        render(state)
        setupButtons(state)
    }
}
