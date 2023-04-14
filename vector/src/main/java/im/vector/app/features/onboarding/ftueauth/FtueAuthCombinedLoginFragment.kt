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

package im.vector.app.features.onboarding.ftueauth

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.autofill.HintConstants
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import im.vector.app.R
import im.vector.app.core.extensions.*
import im.vector.app.core.utils.Resource
import im.vector.app.databinding.FragmentFtueCombinedLoginBinding
import im.vector.app.features.VectorFeatures
import im.vector.app.features.login.*
import im.vector.app.features.login.qr.QrCodeLoginArgs
import im.vector.app.features.login.qr.QrCodeLoginType
import im.vector.app.features.onboarding.OnboardingAction
import im.vector.app.features.onboarding.OnboardingFlow
import im.vector.app.features.onboarding.OnboardingViewEvents
import im.vector.app.features.onboarding.OnboardingViewState
import im.vector.app.features.onboarding.usecase.MobileWalletAdapterUseCase
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import reactivecircus.flowbinding.android.widget.textChanges
import javax.inject.Inject

@AndroidEntryPoint
class FtueAuthCombinedLoginFragment :
    AbstractSSOFtueAuthFragment<FragmentFtueCombinedLoginBinding>() {

    @Inject
    lateinit var loginFieldsValidation: LoginFieldsValidation
    @Inject
    lateinit var loginErrorParser: LoginErrorParser
    @Inject
    lateinit var vectorFeatures: VectorFeatures

    private val mwaLauncher = registerForActivityResult(
        MobileWalletAdapterUseCase.StartMobileWalletAdapterActivity(lifecycle)
    ) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun getBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFtueCombinedLoginBinding {
        return FragmentFtueCombinedLoginBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSubmitButton()
        observingValues()
        viewModel.handle(OnboardingAction.HomeServerChange.SelectHomeServer(viewModel.getDefaultHomeserverUrl()))
    }

    private fun observingValues() {
        //handle ui state
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.uiState.collect { uiState ->
                    uiState.hasAuthToken.let {
//                        if (isAuthorized) {
//                            //update view. not call api
//                        }
                    }

                    if (uiState.messages.isNotEmpty()) {
                        val message = uiState.messages.first()
                        Snackbar.make(views.root, message, Snackbar.LENGTH_SHORT)
                            .addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                override fun onDismissed(
                                    transientBottomBar: Snackbar?,
                                    event: Int
                                ) {
                                    viewModel.messageShown()
                                }
                            }).show()
                    }
                }
            }
        }

        viewModel.authenticate.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (it.data != null){
                        cleanupUi()
                        loginFieldsValidation.validate(
                            it.data.username.toString(),
                            it.data.password.toString()
                        ).onUsernameOrIdError { }
                            .onPasswordError { }
                            .onValid { usernameOrId, password ->
                                val initialDeviceName = getString(R.string.login_default_session_public_name)
                                viewModel.handle(
                                    OnboardingAction.AuthenticateAction.Login(
                                        usernameOrId,
                                        password,
                                        initialDeviceName
                                    )
                                )
                            }
                    }
                }
                Resource.Status.ERROR -> {
                }
                Resource.Status.LOADING -> {
                    //loading
                }
            }
        }

        viewModel.signature.observe(viewLifecycleOwner) {
            if (it != null) {
                viewModel.authenticate(it)
            }
        }

        viewModel.getNonce.observe(viewLifecycleOwner) {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.nonce?.let { nonce ->
                        viewModel.signNonce(mwaLauncher, nonce)
                    }
                }
                Resource.Status.ERROR -> {

                }

                Resource.Status.LOADING -> {

                }
            }
        }

    }
    private fun alreadyHaveAnAccount() {
        viewModel.handle(OnboardingAction.SplashAction.OnIAlreadyHaveAnAccount(onboardingFlow = OnboardingFlow.SignIn))
    }
    private fun setupSubmitButton() {

        views.cvLogin.debouncedClicks {
            viewModel.authorize(mwaLauncher)
        }
    }

    private fun cleanupUi() {
    }

    override fun resetViewModel() {
        viewModel.handle(OnboardingAction.ResetAuthenticationAttempt)
    }

    override fun onError(throwable: Throwable) {

    }

    override fun updateWithState(state: OnboardingViewState) {
        setupUi(state)
        setupAutoFill()
    }

    private fun setupUi(state: OnboardingViewState) {
        when (state.selectedHomeserver.preferredLoginMode) {
            is LoginMode.SsoAndPassword -> {
                showUsernamePassword()
                renderSsoProviders()
            }
            is LoginMode.Sso -> {
                hideUsernamePassword()
                renderSsoProviders()
            }
            else -> {
                showUsernamePassword()
                hideSsoProviders()
            }
        }
    }

    private fun renderSsoProviders() {
    }

    private fun hideSsoProviders() {

    }

    private fun hideUsernamePassword() {

    }

    private fun showUsernamePassword() {
    }
    private fun setupAutoFill() {

    }
}
