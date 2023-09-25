/*
 * Copyright (c) 2020 New Vector Ltd
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

package com.messaging.scrtm.features.home

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.StringRes
import androidx.lifecycle.viewModelScope
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksViewModelFactory
import com.airbnb.mvrx.ViewModelContext
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import com.messaging.scrtm.core.di.ActiveSessionHolder
import com.messaging.scrtm.core.di.MavericksAssistedViewModelFactory
import com.messaging.scrtm.core.di.hiltMavericksViewModelFactory
import com.messaging.scrtm.core.platform.VectorViewModel
import com.messaging.scrtm.core.pushers.EnsureFcmTokenIsRetrievedUseCase
import com.messaging.scrtm.core.pushers.PushersManager
import com.messaging.scrtm.core.pushers.RegisterUnifiedPushUseCase
import com.messaging.scrtm.core.pushers.UnregisterUnifiedPushUseCase
import com.messaging.scrtm.core.session.EnsureSessionSyncingUseCase
import com.messaging.scrtm.features.analytics.AnalyticsConfig
import com.messaging.scrtm.features.analytics.AnalyticsTracker
import com.messaging.scrtm.features.analytics.extensions.toAnalyticsType
import com.messaging.scrtm.features.analytics.plan.Signup
import com.messaging.scrtm.features.analytics.store.AnalyticsStore
import com.messaging.scrtm.features.home.room.list.home.release.ReleaseNotesPreferencesStore
import com.messaging.scrtm.features.login.ReAuthHelper
import com.messaging.scrtm.features.onboarding.AuthenticationDescription
import com.messaging.scrtm.features.raw.wellknown.ElementWellKnown
import com.messaging.scrtm.features.raw.wellknown.getElementWellknown
import com.messaging.scrtm.features.raw.wellknown.isSecureBackupRequired
import com.messaging.scrtm.features.raw.wellknown.withElementWellKnown
import com.messaging.scrtm.features.session.coroutineScope
import com.messaging.scrtm.features.settings.VectorPreferences
import com.messaging.scrtm.features.voicebroadcast.recording.usecase.StopOngoingVoiceBroadcastUseCase
import com.messaging.lib.core.utils.compat.getParcelableExtraCompat
import com.messaging.scrtm.R
import com.messaging.scrtm.data.SessionPref
import com.messaging.scrtm.features.onboarding.OnboardingViewModel
import com.messaging.scrtm.features.onboarding.OnboardingViewModel.Companion.CLUSTER_NAME
import com.messaging.scrtm.features.onboarding.OnboardingViewModel.Companion.IDENTITY
import com.messaging.scrtm.features.onboarding.usecase.Base58EncodeUseCase
import com.messaging.scrtm.features.onboarding.usecase.MobileWalletAdapterUseCase
import com.solana.mobilewalletadapter.clientlib.protocol.MobileWalletAdapterClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.matrix.android.sdk.api.auth.UIABaseAuth
import org.matrix.android.sdk.api.auth.UserInteractiveAuthInterceptor
import org.matrix.android.sdk.api.auth.UserPasswordAuth
import org.matrix.android.sdk.api.auth.data.LoginFlowTypes
import org.matrix.android.sdk.api.auth.registration.RegistrationFlowResponse
import org.matrix.android.sdk.api.auth.registration.nextUncompletedStage
import org.matrix.android.sdk.api.extensions.tryOrNull
import org.matrix.android.sdk.api.raw.RawService
import org.matrix.android.sdk.api.session.crypto.crosssigning.CrossSigningService
import org.matrix.android.sdk.api.session.crypto.model.CryptoDeviceInfo
import org.matrix.android.sdk.api.session.crypto.model.MXUsersDevicesMap
import org.matrix.android.sdk.api.session.getUserOrDefault
import org.matrix.android.sdk.api.session.pushrules.RuleIds
import org.matrix.android.sdk.api.session.room.model.Membership
import org.matrix.android.sdk.api.session.room.roomSummaryQueryParams
import org.matrix.android.sdk.api.session.sync.SyncRequestState
import org.matrix.android.sdk.api.settings.LightweightSettingsStorage
import org.matrix.android.sdk.api.util.awaitCallback
import org.matrix.android.sdk.api.util.toMatrixItem
import org.matrix.android.sdk.flow.flow
import timber.log.Timber
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class HomeActivityViewModel @AssistedInject constructor(
    @Assisted private val initialState: HomeActivityViewState,
    private val activeSessionHolder: ActiveSessionHolder,
    private val applicationContext: Context,
    private val rawService: RawService,
    private val reAuthHelper: ReAuthHelper,
    private val analyticsStore: AnalyticsStore,
    private val lightweightSettingsStorage: LightweightSettingsStorage,
    private val vectorPreferences: VectorPreferences,
    private val analyticsTracker: AnalyticsTracker,
    private val analyticsConfig: AnalyticsConfig,
    private val releaseNotesPreferencesStore: ReleaseNotesPreferencesStore,
    private val stopOngoingVoiceBroadcastUseCase: StopOngoingVoiceBroadcastUseCase,
    private val pushersManager: PushersManager,
    private val registerUnifiedPushUseCase: RegisterUnifiedPushUseCase,
    private val unregisterUnifiedPushUseCase: UnregisterUnifiedPushUseCase,
    private val ensureFcmTokenIsRetrievedUseCase: EnsureFcmTokenIsRetrievedUseCase,
    private val ensureSessionSyncingUseCase: EnsureSessionSyncingUseCase,
    private val sessionPref: SessionPref,
) : VectorViewModel<HomeActivityViewState, HomeActivityViewActions, HomeActivityViewEvents>(initialState) {

    @AssistedFactory
    interface Factory : MavericksAssistedViewModelFactory<HomeActivityViewModel, HomeActivityViewState> {
        override fun create(initialState: HomeActivityViewState): HomeActivityViewModel
    }

    companion object : MavericksViewModelFactory<HomeActivityViewModel, HomeActivityViewState> by hiltMavericksViewModelFactory() {
        override fun initialState(viewModelContext: ViewModelContext): HomeActivityViewState? {
            val activity: HomeActivity = viewModelContext.activity()
            val args: HomeActivityArgs? = activity.intent.getParcelableExtraCompat(Mavericks.KEY_ARG)
            return args?.let { HomeActivityViewState(authenticationDescription = it.authenticationDescription) }
                    ?: super.initialState(viewModelContext)
        }
    }

    private var isInitialized = false
    private var hasCheckedBootstrap = false
    private var onceTrusted = false

    private fun initialize() {
        if (isInitialized) return
        isInitialized = true
        // Ensure Session is syncing
        ensureSessionSyncingUseCase.execute()
        registerUnifiedPushIfNeeded()
        cleanupFiles()
        observeInitialSync()
        checkSessionPushIsOn()
        observeCrossSigningReset()
        observeAnalytics()
        observeReleaseNotes()
        initThreadsMigration()
        viewModelScope.launch { stopOngoingVoiceBroadcastUseCase.execute() }
    }

    private fun registerUnifiedPushIfNeeded() {
        if (vectorPreferences.areNotificationEnabledForDevice()) {
            registerUnifiedPush(distributor = "")
        } else {
            unregisterUnifiedPush()
        }
    }

    private fun registerUnifiedPush(distributor: String) {
        viewModelScope.launch {
            when (registerUnifiedPushUseCase.execute(distributor = distributor)) {
                is RegisterUnifiedPushUseCase.RegisterUnifiedPushResult.NeedToAskUserForDistributor -> {
                    _viewEvents.post(HomeActivityViewEvents.AskUserForPushDistributor)
                }
                RegisterUnifiedPushUseCase.RegisterUnifiedPushResult.Success -> {
                    ensureFcmTokenIsRetrievedUseCase.execute(pushersManager, registerPusher = vectorPreferences.areNotificationEnabledForDevice())
                }
            }
        }
    }

    private val _uiState = MutableStateFlow(OnboardingViewModel.UiState())
    val uiState = _uiState.asStateFlow()

    fun authorize(
        intentLauncher: ActivityResultLauncher<MobileWalletAdapterUseCase.StartMobileWalletAdapterActivity.CreateParams>
    ) = viewModelScope.launch {
        try {
            doLocalAssociateAndExecute(intentLauncher) { client ->
                doAuthorize(client, IDENTITY, CLUSTER_NAME)
            }.also {
                Log.d(TAG, "Authorized: $it")
                showMessage(R.string.msg_request_succeeded)
            }
        } catch (e: MobileWalletAdapterUseCase.LocalAssociationFailedException) {
            Log.e(TAG, "Error associating", e)
            showMessage(R.string.msg_association_failed)
        } catch (e: MobileWalletAdapterUseCase.MobileWalletAdapterOperationFailedException) {
            Log.e(TAG, "Failed invoking authorize", e)
            showMessage(R.string.msg_request_failed)
        }
    }

    private suspend fun <T> doLocalAssociateAndExecute(
        intentLauncher: ActivityResultLauncher<MobileWalletAdapterUseCase.StartMobileWalletAdapterActivity.CreateParams>,
        uriPrefix: Uri? = null,
        action: suspend (MobileWalletAdapterUseCase.Client) -> T
    ): T {
        return try {
            MobileWalletAdapterUseCase.localAssociateAndExecute(intentLauncher, uriPrefix, action)
        } catch (e: MobileWalletAdapterUseCase.NoWalletAvailableException) {
            showMessage(R.string.msg_no_wallet_found)
            throw e
        }
    }

    private fun showMessage(@StringRes resId: Int) {
        val str = applicationContext.getString(resId)
        _uiState.update {
            it.copy(messages = it.messages.plus(str))
        }
    }

    fun messageShown() {
        _uiState.update {
            it.copy(messages = it.messages.drop(1))
        }
    }
    private suspend fun doAuthorize(
        client: MobileWalletAdapterUseCase.Client,
        identity: MobileWalletAdapterUseCase.DappIdentity,
        cluster: String?
    ): MobileWalletAdapterClient.AuthorizationResult {
        val result = try {
            client.authorize(identity, cluster)
        } catch (e: MobileWalletAdapterUseCase.MobileWalletAdapterOperationFailedException) {
            _uiState.update {
                it.copy(
                    authToken = null,
                    publicKey = null,
                    accountLabel = null,
                    walletUriBase = null
                )
            }
            throw e
        }

        _uiState.update {
            it.copy(
                authToken = result.authToken,
                publicKey = result.publicKey,
                accountLabel = result.accountLabel,
                walletUriBase = result.walletUriBase
            )
        }
        sessionPref.address = Base58EncodeUseCase.invoke(_uiState.value.publicKey!!)
        sessionPref.authToken = _uiState.value.authToken!!
        return result
    }

    private fun unregisterUnifiedPush() {
        viewModelScope.launch {
            unregisterUnifiedPushUseCase.execute(pushersManager)
        }
    }

    private fun observeReleaseNotes() = withState { state ->
        if (vectorPreferences.isNewAppLayoutEnabled()) {
            // we don't want to show release notes for new users or after relogin
            if (state.authenticationDescription == null) {
                releaseNotesPreferencesStore.appLayoutOnboardingShown.onEach { isAppLayoutOnboardingShown ->
                    if (!isAppLayoutOnboardingShown) {
                        _viewEvents.post(HomeActivityViewEvents.ShowReleaseNotes)
                    }
                }.launchIn(viewModelScope)
            } else {
                // we assume that users which came from auth flow either have seen updates already (relogin) or don't need them (new user)
                viewModelScope.launch {
                    releaseNotesPreferencesStore.setAppLayoutOnboardingShown(true)
                }
            }
        }
    }

    private fun observeAnalytics() {
        if (analyticsConfig.isEnabled) {
            analyticsStore.didAskUserConsentFlow
                    .onEach { didAskUser ->
                        if (!didAskUser) {
                            _viewEvents.post(HomeActivityViewEvents.ShowAnalyticsOptIn)
                            _viewEvents.post(HomeActivityViewEvents.ShowNotificationDialog)

                        } else {
                            _viewEvents.post(HomeActivityViewEvents.ShowNotificationDialog)
                        }
                    }
                    .launchIn(viewModelScope)

            when (val recentAuthentication = initialState.authenticationDescription) {
                is AuthenticationDescription.Register -> {
                    viewModelScope.launch {
                        analyticsStore.onUserGaveConsent {
                            analyticsTracker.capture(Signup(authenticationType = recentAuthentication.type.toAnalyticsType()))
                        }
                    }
                }
                AuthenticationDescription.Login -> {
                    // do nothing
                }
                null -> {
                    // do nothing
                }
            }
        } else {
            _viewEvents.post(HomeActivityViewEvents.ShowNotificationDialog)
        }
    }

    private suspend fun AnalyticsStore.onUserGaveConsent(action: () -> Unit) {
        userConsentFlow
                .takeWhile { !it }
                .onCompletion { action() }
                .collect()
    }

    private fun cleanupFiles() {
        // Mitigation: delete all cached decrypted files each time the application is started.
        activeSessionHolder.getSafeActiveSession()?.fileService()?.clearDecryptedCache()
    }

    private fun observeCrossSigningReset() {
        val safeActiveSession = activeSessionHolder.getSafeActiveSession() ?: return

        onceTrusted = safeActiveSession
                .cryptoService()
                .crossSigningService().allPrivateKeysKnown()

        safeActiveSession
                .flow()
                .liveCrossSigningInfo(safeActiveSession.myUserId)
                .onEach { info ->
                    val isVerified = info.getOrNull()?.isTrusted() ?: false
                    if (!isVerified && onceTrusted) {
                        rawService.withElementWellKnown(viewModelScope, safeActiveSession.sessionParams) {
                            sessionHasBeenUnverified(it)
                        }
                    }
                    onceTrusted = isVerified
                }
                .launchIn(viewModelScope)
    }

    /**
     * Handle threads migration. The migration includes:
     * - Notify users that had io.element.thread enabled from labs
     * - Re-Enable m.thread to those users (that they had enabled labs threads)
     * - Handle migration when threads are enabled by default
     */
    private fun initThreadsMigration() {
        // When we would like to enable threads for all users
//        if(vectorPreferences.shouldMigrateThreads()) {
//            vectorPreferences.setThreadMessagesEnabled()
//            lightweightSettingsStorage.setThreadMessagesEnabled(vectorPreferences.areThreadMessagesEnabled())
//        }

        when {
            !vectorPreferences.areThreadMessagesEnabled() && !vectorPreferences.wasThreadFlagChangedManually() -> {
                vectorPreferences.setThreadMessagesEnabled()
                lightweightSettingsStorage.setThreadMessagesEnabled(vectorPreferences.areThreadMessagesEnabled())
                // Clear Cache
                _viewEvents.post(HomeActivityViewEvents.MigrateThreads(checkSession = false))
            }
            // Notify users
            vectorPreferences.shouldNotifyUserAboutThreads() && vectorPreferences.areThreadMessagesEnabled() -> {
                Timber.i("----> Notify users about threads")
                // Notify the user if needed that we migrated to support m.thread
                // instead of io.element.thread so old thread messages will be displayed as normal timeline messages
                _viewEvents.post(HomeActivityViewEvents.NotifyUserForThreadsMigration)
                vectorPreferences.userNotifiedAboutThreads()
            }
            // Migrate users with enabled lab settings
            vectorPreferences.shouldNotifyUserAboutThreads() && vectorPreferences.shouldMigrateThreads() -> {
                Timber.i("----> Migrate threads with enabled labs")
                // If user had io.element.thread enabled then enable the new thread support,
                // clear cache to sync messages appropriately
                vectorPreferences.setThreadMessagesEnabled()
                lightweightSettingsStorage.setThreadMessagesEnabled(vectorPreferences.areThreadMessagesEnabled())
                // Clear Cache
                _viewEvents.post(HomeActivityViewEvents.MigrateThreads(checkSession = false))
            }
            // Enable all users
            vectorPreferences.shouldMigrateThreads() && vectorPreferences.areThreadMessagesEnabled() -> {
                Timber.i("----> Try to migrate threads")
                _viewEvents.post(HomeActivityViewEvents.MigrateThreads(checkSession = true))
            }
        }
    }

    private fun observeInitialSync() {
        val session = activeSessionHolder.getSafeActiveSession() ?: return

        session.syncService().getSyncRequestStateFlow()
                .onEach { status ->
                    when (status) {
                        is SyncRequestState.Idle -> {
                            maybeVerifyOrBootstrapCrossSigning()
                        }
                        else -> Unit
                    }

                    setState {
                        copy(
                                syncRequestState = status
                        )
                    }
                }
                .launchIn(viewModelScope)

        if (session.syncService().hasAlreadySynced()) {
            maybeVerifyOrBootstrapCrossSigning()
        }
    }

    /**
     * After migration from riot to element some users reported that their
     * push setting for the session was set to off.
     * In order to mitigate this, we want to display a popup once to the user
     * giving him the option to review this setting.
     */
    private fun checkSessionPushIsOn() {
        viewModelScope.launch(Dispatchers.IO) {
            // Don't do that if it's a login or a register (pass in memory)
            if (reAuthHelper.data != null) return@launch
            // Check if disabled for this device
            if (!vectorPreferences.areNotificationEnabledForDevice()) {
                // Check if set at account level
                val mRuleMaster = activeSessionHolder.getSafeActiveSession()
                        ?.pushRuleService()
                        ?.getPushRules()
                        ?.getAllRules()
                        ?.find { it.ruleId == RuleIds.RULE_ID_DISABLE_ALL }
                if (mRuleMaster?.enabled == false) {
                    // So push are enabled at account level but not for this session
                    // Let's check that there are some rooms?
                    val knownRooms = activeSessionHolder.getSafeActiveSession()
                            ?.roomService()
                            ?.getRoomSummaries(roomSummaryQueryParams {
                                memberships = Membership.activeMemberships()
                            })?.size ?: 0

                    // Prompt once to the user
                    if (knownRooms > 1 && !vectorPreferences.didAskUserToEnableSessionPush()) {
                        // delay a bit
                        delay(1500)
                        _viewEvents.post(HomeActivityViewEvents.PromptToEnableSessionPush)
                    }
                }
            }
        }
    }

    private fun sessionHasBeenUnverified(elementWellKnown: ElementWellKnown?) {
        val session = activeSessionHolder.getSafeActiveSession() ?: return
        val isSecureBackupRequired = elementWellKnown?.isSecureBackupRequired() ?: false
        if (isSecureBackupRequired) {
            // If 4S is forced, force verification
            // for stability cancel all pending verifications?
            session.cryptoService().verificationService().getExistingVerificationRequests(session.myUserId).forEach {
                session.cryptoService().verificationService().cancelVerificationRequest(it)
            }
            _viewEvents.post(HomeActivityViewEvents.ForceVerification(false))
        } else {
            // cross signing keys have been reset
            // Trigger a popup to re-verify
            // Note: user can be unknown in case of logout
            session.getUserOrDefault(session.myUserId)
                    .toMatrixItem()
                    .let { user ->
                        _viewEvents.post(HomeActivityViewEvents.OnCrossSignedInvalidated(user))
                    }
        }
    }

    private fun maybeVerifyOrBootstrapCrossSigning() {
        // The contents of this method should only run once
        if (hasCheckedBootstrap) return
        hasCheckedBootstrap = true

        // We do not use the viewModel context because we do not want to tie this action to activity view model
        activeSessionHolder.getSafeActiveSession()?.coroutineScope?.launch(Dispatchers.IO) {
            val session = activeSessionHolder.getSafeActiveSession() ?: return@launch Unit.also {
                Timber.w("## No session to init cross signing or bootstrap")
            }

            val elementWellKnown = rawService.getElementWellknown(session.sessionParams)
            val isSecureBackupRequired = elementWellKnown?.isSecureBackupRequired() ?: false

            // In case of account creation, it is already done before
            if (initialState.authenticationDescription is AuthenticationDescription.Register) {
                if (isSecureBackupRequired) {
                    _viewEvents.post(HomeActivityViewEvents.StartRecoverySetupFlow)
                } else {
                    val password = reAuthHelper.data ?: return@launch Unit.also {
                        Timber.w("No password to init cross signing")
                    }

                    // Silently initialize cross signing without 4S
                    // We do not use the viewModel context because we do not want to cancel this action
                    Timber.d("Initialize cross signing")
                    try {
                        session.cryptoService().crossSigningService().awaitCrossSigninInitialization { response, _ ->
                            resume(
                                    UserPasswordAuth(
                                            session = response.session,
                                            user = session.myUserId,
                                            password = password
                                    )
                            )
                        }
                    } catch (failure: Throwable) {
                        Timber.e(failure, "Failed to initialize cross signing")
                    }
                }
                return@launch
            }

            tryOrNull("## MaybeVerifyOrBootstrapCrossSigning: Failed to download keys") {
                awaitCallback<MXUsersDevicesMap<CryptoDeviceInfo>> {
                    session.cryptoService().downloadKeys(listOf(session.myUserId), true, it)
                }
            }

            // From there we are up to date with server
            // Is there already cross signing keys here?
            val mxCrossSigningInfo = session.cryptoService().crossSigningService().getMyCrossSigningKeys()
            if (mxCrossSigningInfo != null) {
                if (isSecureBackupRequired && !session.sharedSecretStorageService().isRecoverySetup()) {
                    // If 4S is forced, start the full interactive setup flow
                    _viewEvents.post(HomeActivityViewEvents.StartRecoverySetupFlow)
                } else {
                    // Cross-signing is already set up for this user, is it trusted?
                    if (!mxCrossSigningInfo.isTrusted()) {
                        if (isSecureBackupRequired) {
                            // If 4S is forced, force verification
                            _viewEvents.post(HomeActivityViewEvents.ForceVerification(true))
                        } else {
                            // we wan't to check if there is a way to actually verify this session,
                            // that means that there is another session to verify against, or
                            // secure backup is setup
                            val hasTargetDeviceToVerifyAgainst = session
                                    .cryptoService()
                                    .getUserDevices(session.myUserId)
                                    .size >= 2 // this one + another
                            val is4Ssetup = session.sharedSecretStorageService().isRecoverySetup()
                            if (hasTargetDeviceToVerifyAgainst || is4Ssetup) {
                                // New session
                                _viewEvents.post(
                                        HomeActivityViewEvents.CurrentSessionNotVerified(
                                                session.getUserOrDefault(session.myUserId).toMatrixItem(),
                                                // Always send request instead of waiting for an incoming as per recent EW changes
                                                false
                                        )
                                )
                            } else {
                                _viewEvents.post(
                                        HomeActivityViewEvents.CurrentSessionCannotBeVerified(
                                                session.getUserOrDefault(session.myUserId).toMatrixItem(),
                                        )
                                )
                            }
                        }
                    }
                }
            } else {
                // Cross signing is not initialized
                if (isSecureBackupRequired) {
                    // If 4S is forced, start the full interactive setup flow
                    _viewEvents.post(HomeActivityViewEvents.StartRecoverySetupFlow)
                } else {
                    // Initialize cross-signing silently
                    val password = reAuthHelper.data

                    if (password == null) {
                        // Check this is not an SSO account
                        if (session.homeServerCapabilitiesService().getHomeServerCapabilities().canChangePassword) {
                            // Ask password to the user: Upgrade security
                            _viewEvents.post(HomeActivityViewEvents.AskPasswordToInitCrossSigning(session.getUserOrDefault(session.myUserId).toMatrixItem()))
                        }
                        // Else (SSO) just ignore for the moment
                    } else {
                        // Try to initialize cross signing in background if possible
                        Timber.d("Initialize cross signing...")
                        try {
                            session.cryptoService().crossSigningService().awaitCrossSigninInitialization { response, errCode ->
                                // We missed server grace period or it's not setup, see if we remember locally password
                                if (response.nextUncompletedStage() == LoginFlowTypes.PASSWORD &&
                                        errCode == null &&
                                        reAuthHelper.data != null) {
                                    resume(
                                            UserPasswordAuth(
                                                    session = response.session,
                                                    user = session.myUserId,
                                                    password = reAuthHelper.data
                                            )
                                    )
                                    Timber.d("Initialize cross signing SUCCESS")
                                } else {
                                    resumeWithException(Exception("Cannot silently initialize cross signing, UIA missing"))
                                }
                            }
                        } catch (failure: Throwable) {
                            Timber.e(failure, "Failed to initialize cross signing")
                        }
                    }
                }
            }
        }
    }

    override fun handle(action: HomeActivityViewActions) {
        when (action) {
            HomeActivityViewActions.PushPromptHasBeenReviewed -> {
                vectorPreferences.setDidAskUserToEnableSessionPush()
            }
            HomeActivityViewActions.ViewStarted -> {
                initialize()
            }
            is HomeActivityViewActions.RegisterPushDistributor -> {
                registerUnifiedPush(distributor = action.distributor)
            }
        }
    }
}

private suspend fun CrossSigningService.awaitCrossSigninInitialization(
        block: Continuation<UIABaseAuth>.(response: RegistrationFlowResponse, errCode: String?) -> Unit
) {
    awaitCallback<Unit> {
        initializeCrossSigning(
                object : UserInteractiveAuthInterceptor {
                    override fun performStage(flowResponse: RegistrationFlowResponse, errCode: String?, promise: Continuation<UIABaseAuth>) {
                        promise.block(flowResponse, errCode)
                    }
                },
                callback = it
        )
    }
}
