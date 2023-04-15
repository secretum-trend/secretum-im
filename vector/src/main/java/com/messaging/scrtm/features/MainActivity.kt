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

package com.messaging.scrtm.features

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.viewModel
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.R
import com.messaging.scrtm.core.extensions.startSyncing
import com.messaging.scrtm.core.extensions.vectorStore
import com.messaging.scrtm.core.platform.VectorBaseActivity
import com.messaging.scrtm.core.utils.deleteAllFiles
import com.messaging.scrtm.databinding.ActivityMainBinding
import com.messaging.scrtm.features.analytics.VectorAnalytics
import com.messaging.scrtm.features.home.HomeActivity
import com.messaging.scrtm.features.home.ShortcutsHandler
import com.messaging.scrtm.features.notifications.NotificationDrawerManager
import com.messaging.scrtm.features.pin.UnlockedActivity
import com.messaging.scrtm.features.pin.lockscreen.crypto.LockScreenKeyRepository
import com.messaging.scrtm.features.pin.lockscreen.pincode.PinCodeHelper
import com.messaging.scrtm.features.popup.PopupAlertManager
import com.messaging.scrtm.features.session.VectorSessionStore
import com.messaging.scrtm.features.signout.hard.SignedOutActivity
import com.messaging.scrtm.features.start.StartAppAction
import com.messaging.scrtm.features.start.StartAppAndroidService
import com.messaging.scrtm.features.start.StartAppViewEvent
import com.messaging.scrtm.features.start.StartAppViewModel
import com.messaging.scrtm.features.start.StartAppViewState
import com.messaging.scrtm.features.themes.ActivityOtherThemes
import com.messaging.scrtm.features.ui.UiStateRepository
import com.messaging.lib.core.utils.compat.getParcelableExtraCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.parcelize.Parcelize
import org.matrix.android.sdk.api.failure.GlobalError
import timber.log.Timber
import javax.inject.Inject

@Parcelize
data class MainActivityArgs(
        val clearCache: Boolean = false,
        val clearCredentials: Boolean = false,
        val isUserLoggedOut: Boolean = false,
        val isAccountDeactivated: Boolean = false,
        val isSoftLogout: Boolean = false
) : Parcelable

/**
 * This is the entry point of Element Android.
 * This Activity, when started with argument, is also doing some cleanup when user signs out,
 * clears cache, is logged out, or is soft logged out.
 */
@AndroidEntryPoint
class MainActivity : VectorBaseActivity<ActivityMainBinding>(), UnlockedActivity {

    companion object {
        private const val EXTRA_ARGS = "EXTRA_ARGS"
        private const val EXTRA_NEXT_INTENT = "EXTRA_NEXT_INTENT"
        private const val EXTRA_INIT_SESSION = "EXTRA_INIT_SESSION"
        private const val EXTRA_ROOM_ID = "EXTRA_ROOM_ID"
        private const val ACTION_ROOM_DETAILS_FROM_SHORTCUT = "ROOM_DETAILS_FROM_SHORTCUT"

        // Special action to clear cache and/or clear credentials
        fun restartApp(activity: Activity, args: MainActivityArgs) {
            val intent = Intent(activity, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

            intent.putExtra(EXTRA_ARGS, args)
            activity.startActivity(intent)
        }

        fun getIntentToInitSession(activity: Activity): Intent {
            val intent = Intent(activity, MainActivity::class.java)
            intent.putExtra(EXTRA_INIT_SESSION, true)
            return intent
        }

        fun getIntentWithNextIntent(context: Context, nextIntent: Intent): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_NEXT_INTENT, nextIntent)
            return intent
        }

        // Shortcuts can't have intents with parcelables
        fun shortcutIntent(context: Context, roomId: String): Intent {
            return Intent(context, MainActivity::class.java).apply {
                action = ACTION_ROOM_DETAILS_FROM_SHORTCUT
                putExtra(EXTRA_ROOM_ID, roomId)
            }
        }
    }

    private val startAppViewModel: StartAppViewModel by viewModel()

    override fun getBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun getOtherThemes() = ActivityOtherThemes.Launcher

    private lateinit var args: MainActivityArgs

    @Inject lateinit var notificationDrawerManager: NotificationDrawerManager
    @Inject lateinit var uiStateRepository: UiStateRepository
    @Inject lateinit var shortcutsHandler: ShortcutsHandler
    @Inject lateinit var pinCodeHelper: PinCodeHelper
    @Inject lateinit var popupAlertManager: PopupAlertManager
    @Inject lateinit var vectorAnalytics: VectorAnalytics
    @Inject lateinit var lockScreenKeyRepository: LockScreenKeyRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        shortcutsHandler.updateShortcutsWithPreviousIntent()

        startAppViewModel.onEach {
            renderState(it)
        }
        startAppViewModel.observeViewEvents {
            handleViewEvents(it)
        }

        startAppViewModel.handle(StartAppAction.StartApp)
    }

    private fun renderState(state: StartAppViewState) {
        if (state.mayBeLongToProcess) {
            views.status.setText(R.string.updating_your_data)
        }
        views.status.isVisible = state.mayBeLongToProcess
    }

    private fun handleViewEvents(event: StartAppViewEvent) {
        when (event) {
            StartAppViewEvent.StartForegroundService -> handleStartForegroundService()
            StartAppViewEvent.AppStarted -> handleAppStarted()
        }
    }

    private fun handleStartForegroundService() {
        if (startAppViewModel.shouldStartApp()) {
            // Start foreground service, because the operation may take a while
            val intent = Intent(this, StartAppAndroidService::class.java)
            ContextCompat.startForegroundService(this, intent)
        }
    }

    private fun handleAppStarted() {
        args = parseArgs()
        startSyncing()
        startNextActivityAndFinish()
//        if (intent.hasExtra(EXTRA_NEXT_INTENT)) {
//            // Start the next Activity
//            startSyncing()
//            val nextIntent = intent.getParcelableExtraCompat<Intent>(EXTRA_NEXT_INTENT)
//            startIntentAndFinish(nextIntent)
//        } else if (intent.hasExtra(EXTRA_INIT_SESSION)) {
//            startSyncing()
//            setResult(RESULT_OK)
//            finish()
//        } else if (intent.action == ACTION_ROOM_DETAILS_FROM_SHORTCUT) {
//            startSyncing()
//            val roomId = intent.getStringExtra(EXTRA_ROOM_ID)
//            if (roomId?.isNotEmpty() == true) {
//                navigator.openRoom(this, roomId, trigger = ViewRoom.Trigger.Shortcut)
//            }
//            finish()
//        } else {
//            args = parseArgs()
//            if (args.clearCredentials || args.isUserLoggedOut || args.clearCache) {
//                clearNotifications()
//            }
//            // Handle some wanted cleanup
//            if (args.clearCache || args.clearCredentials) {
//                doCleanUp()
//            } else {
//                startSyncing()
//                startNextActivityAndFinish()
//            }
//        }
    }

    private fun startSyncing() {
        activeSessionHolder.getSafeActiveSession()?.startSyncing(this)
    }

    private fun clearNotifications() {
        // Dismiss all notifications
        notificationDrawerManager.clearAllEvents()

        // Also clear the dynamic shortcuts
        shortcutsHandler.clearShortcuts()

        // Also clear the alerts
        popupAlertManager.cancelAll()
    }

    private fun parseArgs(): MainActivityArgs {
        val argsFromIntent: MainActivityArgs? = intent.getParcelableExtraCompat(EXTRA_ARGS)
        Timber.w("Starting MainActivity with $argsFromIntent")

        return MainActivityArgs(
                clearCache = argsFromIntent?.clearCache ?: false,
                clearCredentials = argsFromIntent?.clearCredentials ?: false,
                isUserLoggedOut = argsFromIntent?.isUserLoggedOut ?: false,
                isAccountDeactivated = argsFromIntent?.isAccountDeactivated ?: false,
                isSoftLogout = argsFromIntent?.isSoftLogout ?: false
        )
    }

    private fun doCleanUp() {
        val session = activeSessionHolder.getSafeActiveSession()
        if (session == null) {
            startNextActivityAndFinish()
            return
        }

        val onboardingStore = session.vectorStore(this)
        when {
            args.isAccountDeactivated -> {
                lifecycleScope.launch {
                    // Just do the local cleanup
                    Timber.w("Account deactivated, start app")
                    activeSessionHolder.clearActiveSession()
                    doLocalCleanup(clearPreferences = true, onboardingStore)
                    startNextActivityAndFinish()
                }
            }
            args.clearCredentials -> {
                lifecycleScope.launch {
                    try {
                        session.signOutService().signOut(!args.isUserLoggedOut)
                    } catch (failure: Throwable) {
                        displayError(failure)
                        return@launch
                    }
                    Timber.w("SIGN_OUT: success, start app")
                    activeSessionHolder.clearActiveSession()
                    doLocalCleanup(clearPreferences = true, onboardingStore)
                    startNextActivityAndFinish()
                }
            }
            args.clearCache -> {
                lifecycleScope.launch {
                    session.clearCache()
                    doLocalCleanup(clearPreferences = false, onboardingStore)
                    session.startSyncing(applicationContext)
                    startNextActivityAndFinish()
                }
            }
        }
    }

    override fun handleInvalidToken(globalError: GlobalError.InvalidToken) {
        // No op here
        Timber.w("Ignoring invalid token global error")
    }

    private suspend fun doLocalCleanup(clearPreferences: Boolean, vectorSessionStore: VectorSessionStore) {
        // On UI Thread
        Glide.get(this@MainActivity).clearMemory()

        if (clearPreferences) {
            vectorPreferences.clearPreferences()
            uiStateRepository.reset()
            pinLocker.unlock()
            pinCodeHelper.deletePinCode()
            vectorAnalytics.onSignOut()
            vectorSessionStore.clear()
            lockScreenKeyRepository.deleteSystemKey()
        }
        withContext(Dispatchers.IO) {
            // On BG thread
            Glide.get(this@MainActivity).clearDiskCache()

            // Also clear cache (Logs, etc...)
            deleteAllFiles(this@MainActivity.cacheDir)
        }
    }

    private fun displayError(failure: Throwable) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)) {
            MaterialAlertDialogBuilder(this)
                    .setTitle(R.string.dialog_title_error)
                    .setMessage(errorFormatter.toHumanReadable(failure))
                    .setPositiveButton(R.string.global_retry) { _, _ -> doCleanUp() }
                    .setNegativeButton(R.string.action_cancel) { _, _ -> startNextActivityAndFinish() }
                    .setCancelable(false)
                    .show()
        }
    }

    private fun startNextActivityAndFinish() {
        val intent =
//            when {
//            activeSessionHolder.hasActiveSession() ->
//                // We have a session.
//                // Check it can be opened
//                if (activeSessionHolder.getActiveSession().isOpenable) {
//                    HomeActivity.newIntent(this, firstStartMainActivity = false, existingSession = true)
//                } else {
//                    // The token is still invalid
//                    navigator.softLogout(this)
//                    null
//                }
//            else -> {
//                // First start, or no active session
//                navigator.openLogin(this, null)
//                null
//            }
//        }

            when {
            args.clearCredentials &&
                    (!args.isUserLoggedOut || args.isAccountDeactivated) -> {
                // User has explicitly asked to log out or deactivated his account
                navigator.openLogin(this, null)
                null
            }
            args.isSoftLogout -> {
                // The homeserver has invalidated the token, with a soft logout
                navigator.softLogout(this)
                null
            }
            args.isUserLoggedOut ->
                // the homeserver has invalidated the token (password changed, device deleted, other security reasons)
                SignedOutActivity.newIntent(this)
            activeSessionHolder.hasActiveSession() ->
                // We have a session.
                // Check it can be opened
                if (activeSessionHolder.getActiveSession().isOpenable) {
                    HomeActivity.newIntent(this, firstStartMainActivity = false, existingSession = true)
                } else {
                    // The token is still invalid
                    navigator.softLogout(this)
                    null
                }
            else -> {
                // First start, or no active session
                navigator.openLogin(this, null)
                null
            }
        }
        startIntentAndFinish(intent)
    }

    private fun startIntentAndFinish(intent: Intent?) {
        intent?.let { startActivity(it) }
        finish()
    }
}
