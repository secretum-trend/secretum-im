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

package com.messaging.scrtm.features.navigation

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import android.view.Window
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.messaging.scrtm.R
import com.messaging.scrtm.SpaceStateHandler
import com.messaging.scrtm.config.OnboardingVariant
import com.messaging.scrtm.core.debug.DebugNavigator
import com.messaging.scrtm.core.di.ActiveSessionHolder
import com.messaging.scrtm.core.error.fatalError
import com.messaging.scrtm.features.VectorFeatures
import com.messaging.scrtm.features.analytics.AnalyticsTracker
import com.messaging.scrtm.features.analytics.extensions.toAnalyticsViewRoom
import com.messaging.scrtm.features.analytics.plan.ViewRoom
import com.messaging.scrtm.features.analytics.ui.consent.AnalyticsOptInActivity
import com.messaging.scrtm.features.call.conference.JitsiCallViewModel
import com.messaging.scrtm.features.call.conference.VectorJitsiActivity
import com.messaging.scrtm.features.call.transfer.CallTransferActivity
import com.messaging.scrtm.features.createdirect.CreateDirectRoomActivity
import com.messaging.scrtm.features.crypto.keysbackup.settings.KeysBackupManageActivity
import com.messaging.scrtm.features.crypto.keysbackup.setup.KeysBackupSetupActivity
import com.messaging.scrtm.features.crypto.recover.BootstrapBottomSheet
import com.messaging.scrtm.features.crypto.recover.SetupMode
import com.messaging.scrtm.features.crypto.verification.SupportedVerificationMethodsProvider
import com.messaging.scrtm.features.crypto.verification.VerificationBottomSheet
import com.messaging.scrtm.features.devtools.RoomDevToolActivity
import com.messaging.scrtm.features.home.room.detail.RoomDetailActivity
import com.messaging.scrtm.features.home.room.detail.arguments.TimelineArgs
import com.messaging.scrtm.features.home.room.detail.search.SearchActivity
import com.messaging.scrtm.features.home.room.detail.search.SearchArgs
import com.messaging.scrtm.features.home.room.filtered.FilteredRoomsActivity
import com.messaging.scrtm.features.home.room.threads.ThreadsActivity
import com.messaging.scrtm.features.home.room.threads.arguments.ThreadListArgs
import com.messaging.scrtm.features.home.room.threads.arguments.ThreadTimelineArgs
import com.messaging.scrtm.features.invite.InviteUsersToRoomActivity
import com.messaging.scrtm.features.location.LocationData
import com.messaging.scrtm.features.location.LocationSharingActivity
import com.messaging.scrtm.features.location.LocationSharingArgs
import com.messaging.scrtm.features.location.LocationSharingMode
import com.messaging.scrtm.features.location.live.map.LiveLocationMapViewActivity
import com.messaging.scrtm.features.location.live.map.LiveLocationMapViewArgs
import com.messaging.scrtm.features.login.LoginActivity
import com.messaging.scrtm.features.login.LoginConfig
import com.messaging.scrtm.features.login.qr.QrCodeLoginActivity
import com.messaging.scrtm.features.login.qr.QrCodeLoginArgs
import com.messaging.scrtm.features.matrixto.MatrixToBottomSheet
import com.messaging.scrtm.features.matrixto.OriginOfMatrixTo
import com.messaging.scrtm.features.media.AttachmentData
import com.messaging.scrtm.features.media.BigImageViewerActivity
import com.messaging.scrtm.features.media.VectorAttachmentViewerActivity
import com.messaging.scrtm.features.onboarding.OnboardingActivity
import com.messaging.scrtm.features.pin.PinActivity
import com.messaging.scrtm.features.pin.PinArgs
import com.messaging.scrtm.features.pin.PinMode
import com.messaging.scrtm.features.poll.PollMode
import com.messaging.scrtm.features.poll.create.CreatePollActivity
import com.messaging.scrtm.features.poll.create.CreatePollArgs
import com.messaging.scrtm.features.roomdirectory.RoomDirectoryActivity
import com.messaging.scrtm.features.roomdirectory.RoomDirectoryData
import com.messaging.scrtm.features.roomdirectory.createroom.CreateRoomActivity
import com.messaging.scrtm.features.roomdirectory.roompreview.RoomPreviewActivity
import com.messaging.scrtm.features.roomdirectory.roompreview.RoomPreviewData
import com.messaging.scrtm.features.roommemberprofile.RoomMemberProfileActivity
import com.messaging.scrtm.features.roommemberprofile.RoomMemberProfileArgs
import com.messaging.scrtm.features.roomprofile.RoomProfileActivity
import com.messaging.scrtm.features.settings.VectorPreferences
import com.messaging.scrtm.features.settings.VectorSettingsActivity
import com.messaging.scrtm.features.share.SharedData
import com.messaging.scrtm.features.signout.soft.SoftLogoutActivity
import com.messaging.scrtm.features.spaces.InviteRoomSpaceChooserBottomSheet
import com.messaging.scrtm.features.spaces.SpaceExploreActivity
import com.messaging.scrtm.features.spaces.SpacePreviewActivity
import com.messaging.scrtm.features.spaces.manage.ManageType
import com.messaging.scrtm.features.spaces.manage.SpaceManageActivity
import com.messaging.scrtm.features.spaces.people.SpacePeopleActivity
import com.messaging.scrtm.features.terms.ReviewTermsActivity
import com.messaging.scrtm.features.widgets.WidgetActivity
import com.messaging.scrtm.features.widgets.WidgetArgsBuilder
import org.matrix.android.sdk.api.session.crypto.verification.IncomingSasVerificationTransaction
import org.matrix.android.sdk.api.session.getRoom
import org.matrix.android.sdk.api.session.getRoomSummary
import org.matrix.android.sdk.api.session.permalinks.PermalinkData
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import org.matrix.android.sdk.api.session.room.model.roomdirectory.PublicRoom
import org.matrix.android.sdk.api.session.terms.TermsService
import org.matrix.android.sdk.api.session.widgets.model.Widget
import org.matrix.android.sdk.api.session.widgets.model.WidgetType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultNavigator @Inject constructor(
        private val sessionHolder: ActiveSessionHolder,
        private val vectorPreferences: VectorPreferences,
        private val widgetArgsBuilder: WidgetArgsBuilder,
        private val spaceStateHandler: SpaceStateHandler,
        private val supportedVerificationMethodsProvider: SupportedVerificationMethodsProvider,
        private val features: VectorFeatures,
        private val analyticsTracker: AnalyticsTracker,
        private val debugNavigator: DebugNavigator,
) : Navigator {

    override fun openLogin(context: Context, loginConfig: LoginConfig?, flags: Int) {
        val intent = when (features.onboardingVariant()) {
            OnboardingVariant.LEGACY -> LoginActivity.newIntent(context, loginConfig)
            OnboardingVariant.FTUE_AUTH -> OnboardingActivity.newIntent(context, loginConfig)
        }
        intent.addFlags(flags)
        context.startActivity(intent)
    }

    override fun loginSSORedirect(context: Context, data: Uri?) {
        val intent = when (features.onboardingVariant()) {
            OnboardingVariant.LEGACY -> LoginActivity.redirectIntent(context, data)
            OnboardingVariant.FTUE_AUTH -> OnboardingActivity.redirectIntent(context, data)
        }
        context.startActivity(intent)
    }

    override fun softLogout(context: Context) {
        val intent = SoftLogoutActivity.newIntent(context)
        context.startActivity(intent)
    }

    override fun openRoom(
            context: Context,
            roomId: String,
            eventId: String?,
            buildTask: Boolean,
            isInviteAlreadyAccepted: Boolean,
            trigger: ViewRoom.Trigger?
    ) {
        if (sessionHolder.getSafeActiveSession()?.getRoom(roomId) == null) {
            fatalError("Trying to open an unknown room $roomId", vectorPreferences.failFast())
            return
        }

        trigger?.let {
            analyticsTracker.capture(
                    sessionHolder.getActiveSession().getRoomSummary(roomId).toAnalyticsViewRoom(
                            trigger = trigger,
                            selectedSpace = spaceStateHandler.getCurrentSpace()
                    )
            )
        }

        val args = TimelineArgs(roomId = roomId, eventId = eventId, isInviteAlreadyAccepted = isInviteAlreadyAccepted)
        val intent = RoomDetailActivity.newIntent(context, args, false)
        startActivity(context, intent, buildTask)
    }

    override fun switchToSpace(
            context: Context,
            spaceId: String,
            postSwitchSpaceAction: Navigator.PostSwitchSpaceAction,
    ) {
        if (sessionHolder.getSafeActiveSession()?.getRoomSummary(spaceId) == null) {
            fatalError("Trying to open an unknown space $spaceId", vectorPreferences.failFast())
            return
        }
        spaceStateHandler.setCurrentSpace(spaceId)
        handlePostSwitchAction(context, spaceId, postSwitchSpaceAction)
    }

    private fun handlePostSwitchAction(
            context: Context,
            spaceId: String,
            action: Navigator.PostSwitchSpaceAction,
    ) {
        when (action) {
            Navigator.PostSwitchSpaceAction.None -> {
                // go back to home if we are showing room details?
                // This is a bit ugly, but the navigator is supposed to know about the activity stack
                if (context is RoomDetailActivity) {
                    context.finish()
                }
            }
            Navigator.PostSwitchSpaceAction.OpenAddExistingRooms -> {
                startActivity(context, SpaceManageActivity.newIntent(context, spaceId, ManageType.AddRooms), false)
            }
            Navigator.PostSwitchSpaceAction.OpenRoomList -> {
                startActivity(context, SpaceExploreActivity.newIntent(context, spaceId), buildTask = false)
            }
            is Navigator.PostSwitchSpaceAction.OpenDefaultRoom -> {
                val args = TimelineArgs(
                        action.roomId,
                        eventId = null,
                        openShareSpaceForId = spaceId.takeIf { action.showShareSheet }
                )
                val intent = RoomDetailActivity.newIntent(context, args, false)
                startActivity(context, intent, false)
            }
        }
    }

    override fun openSpacePreview(context: Context, spaceId: String) {
        startActivity(context, SpacePreviewActivity.newIntent(context, spaceId), false)
    }

    override fun performDeviceVerification(fragmentActivity: FragmentActivity, otherUserId: String, sasTransactionId: String) {
        val session = sessionHolder.getSafeActiveSession() ?: return
        val tx = session.cryptoService().verificationService().getExistingTransaction(otherUserId, sasTransactionId)
                ?: return
        (tx as? IncomingSasVerificationTransaction)?.performAccept()
        VerificationBottomSheet.withArgs(
                roomId = null,
                otherUserId = otherUserId,
                transactionId = sasTransactionId
        ).show(fragmentActivity.supportFragmentManager, "REQPOP")
    }

    override fun requestSessionVerification(fragmentActivity: FragmentActivity, otherSessionId: String) {
        val session = sessionHolder.getSafeActiveSession() ?: return
        val pr = session.cryptoService().verificationService().requestKeyVerification(
                supportedVerificationMethodsProvider.provide(),
                session.myUserId,
                listOf(otherSessionId)
        )
        VerificationBottomSheet.withArgs(
                roomId = null,
                otherUserId = session.myUserId,
                transactionId = pr.transactionId
        ).show(fragmentActivity.supportFragmentManager, VerificationBottomSheet.WAITING_SELF_VERIF_TAG)
    }

    override fun requestSelfSessionVerification(fragmentActivity: FragmentActivity) {
        val session = sessionHolder.getSafeActiveSession() ?: return
        val otherSessions = session.cryptoService()
                .getCryptoDeviceInfo(session.myUserId)
                .filter { it.deviceId != session.sessionParams.deviceId }
                .map { it.deviceId }
        if (otherSessions.isNotEmpty()) {
            val pr = session.cryptoService().verificationService().requestKeyVerification(
                    supportedVerificationMethodsProvider.provide(),
                    session.myUserId,
                    otherSessions
            )
            VerificationBottomSheet.forSelfVerification(session, pr.transactionId ?: pr.localId)
                    .show(fragmentActivity.supportFragmentManager, VerificationBottomSheet.WAITING_SELF_VERIF_TAG)
        } else {
            VerificationBottomSheet.forSelfVerification(session)
                    .show(fragmentActivity.supportFragmentManager, VerificationBottomSheet.WAITING_SELF_VERIF_TAG)
        }
    }

    override fun waitSessionVerification(fragmentActivity: FragmentActivity) {
        val session = sessionHolder.getSafeActiveSession() ?: return
        VerificationBottomSheet.forSelfVerification(session)
                .show(fragmentActivity.supportFragmentManager, VerificationBottomSheet.WAITING_SELF_VERIF_TAG)
    }

    override fun upgradeSessionSecurity(fragmentActivity: FragmentActivity, initCrossSigningOnly: Boolean) {
        BootstrapBottomSheet.show(
                fragmentActivity.supportFragmentManager,
                if (initCrossSigningOnly) SetupMode.CROSS_SIGNING_ONLY else SetupMode.NORMAL
        )
    }

    override fun openRoomMemberProfile(userId: String, roomId: String?, context: Context, buildTask: Boolean) {
        val args = RoomMemberProfileArgs(userId = userId, roomId = roomId)
        val intent = RoomMemberProfileActivity.newIntent(context, args)
        startActivity(context, intent, buildTask)
    }

    override fun openRoomForSharingAndFinish(activity: Activity, roomId: String, sharedData: SharedData) {
        val args = TimelineArgs(roomId, null, sharedData)
        val intent = RoomDetailActivity.newIntent(activity, args, false)
        activity.startActivity(intent)
        activity.finish()
    }

    override fun openRoomPreview(context: Context, publicRoom: PublicRoom, roomDirectoryData: RoomDirectoryData) {
        val intent = RoomPreviewActivity.newIntent(context, publicRoom, roomDirectoryData)
        context.startActivity(intent)
    }

    override fun openRoomPreview(context: Context, roomPreviewData: RoomPreviewData, fromEmailInviteLink: PermalinkData.RoomEmailInviteLink?) {
        val intent = RoomPreviewActivity.newIntent(context, roomPreviewData)
        context.startActivity(intent)
    }

    override fun openMatrixToBottomSheet(fragmentActivity: FragmentActivity, link: String, origin: OriginOfMatrixTo) {
        if (fragmentActivity !is MatrixToBottomSheet.InteractionListener) {
            fatalError("Caller context should implement MatrixToBottomSheet.InteractionListener", vectorPreferences.failFast())
            return
        }
        // TODO check if there is already one??
        MatrixToBottomSheet.withLink(link, origin)
                .show(fragmentActivity.supportFragmentManager, "HA#MatrixToBottomSheet")
    }

    override fun openRoomDirectory(context: Context, initialFilter: String) {
        when (val currentSpace = spaceStateHandler.getCurrentSpace()) {
            null -> RoomDirectoryActivity.getIntent(context, initialFilter)
            else -> SpaceExploreActivity.newIntent(context, currentSpace.roomId)
        }.start(context)
    }

    override fun openCreateRoom(context: Context, initialName: String, openAfterCreate: Boolean) {
        val intent = CreateRoomActivity.getIntent(context = context, initialName = initialName, openAfterCreate = openAfterCreate)
        context.startActivity(intent)
    }

    override fun openCreateDirectRoom(context: Context) {
        when (val currentSpace = spaceStateHandler.getCurrentSpace()) {
            null -> CreateDirectRoomActivity.getIntent(context)
            else -> SpacePeopleActivity.newIntent(context, currentSpace.roomId)
        }.start(context)
    }

    override fun openInviteUsersToRoom(fragmentActivity: FragmentActivity, roomId: String) {
        when (val currentSpace = spaceStateHandler.getCurrentSpace()) {
            null -> InviteUsersToRoomActivity.getIntent(fragmentActivity, roomId).start(fragmentActivity)
            else -> showInviteToDialog(fragmentActivity, currentSpace, roomId)
        }
    }

    private fun showInviteToDialog(fragmentActivity: FragmentActivity, currentSpace: RoomSummary, roomId: String) {
        InviteRoomSpaceChooserBottomSheet.showInstance(fragmentActivity.supportFragmentManager, currentSpace.roomId, roomId) { itemId ->
            InviteUsersToRoomActivity.getIntent(fragmentActivity, itemId).start(fragmentActivity)
        }
    }

    override fun openRoomsFiltering(context: Context) {
        val intent = FilteredRoomsActivity.newIntent(context)
        context.startActivity(intent)
    }

    override fun openSettings(context: Context, directAccess: Int) {
        val intent = VectorSettingsActivity.getIntent(context, directAccess)
        context.startActivity(intent)
    }

    override fun openSettings(context: Context, payload: SettingsActivityPayload) {
        val intent = VectorSettingsActivity.getIntent(context, payload)
        context.startActivity(intent)
    }

    override fun openDebug(context: Context) {
        debugNavigator.openDebugMenu(context)
    }

    override fun openKeysBackupSetup(fragmentActivity: FragmentActivity, showManualExport: Boolean) {
        // if cross signing is enabled and trusted or not set up at all we should propose full 4S
        sessionHolder.getSafeActiveSession()?.let { session ->
            if (session.cryptoService().crossSigningService().getMyCrossSigningKeys() == null ||
                    session.cryptoService().crossSigningService().canCrossSign()) {
                BootstrapBottomSheet.show(fragmentActivity.supportFragmentManager, SetupMode.NORMAL)
            } else {
                fragmentActivity.startActivity(KeysBackupSetupActivity.intent(fragmentActivity, showManualExport))
            }
        }
    }

    override fun open4SSetup(fragmentActivity: FragmentActivity, setupMode: SetupMode) {
        BootstrapBottomSheet.show(fragmentActivity.supportFragmentManager, setupMode)
    }

    override fun openKeysBackupManager(context: Context) {
        context.startActivity(KeysBackupManageActivity.intent(context))
    }

    override fun showGroupsUnsupportedWarning(context: Context) {
        Toast.makeText(context, context.getString(R.string.permalink_unsupported_groups), Toast.LENGTH_LONG).show()
    }

    override fun openRoomProfile(context: Context, roomId: String, directAccess: Int?) {
        context.startActivity(RoomProfileActivity.newIntent(context, roomId, directAccess))
    }

    override fun openBigImageViewer(activity: Activity, sharedElement: View?, mxcUrl: String?, title: String?) {
        mxcUrl
                ?.takeIf { it.isNotBlank() }
                ?.let { avatarUrl ->
                    val intent = BigImageViewerActivity.newIntent(activity, title, avatarUrl)
                    val options = sharedElement?.let {
                        ActivityOptionsCompat.makeSceneTransitionAnimation(activity, it, ViewCompat.getTransitionName(it) ?: "")
                    }
                    activity.startActivity(intent, options?.toBundle())
                }
    }

    override fun openAnalyticsOptIn(context: Context) {
        context.startActivity(Intent(context, AnalyticsOptInActivity::class.java))
    }

    override fun openTerms(
            context: Context,
            activityResultLauncher: ActivityResultLauncher<Intent>,
            serviceType: TermsService.ServiceType,
            baseUrl: String,
            token: String?
    ) {
        val intent = ReviewTermsActivity.intent(context, serviceType, baseUrl, token)
        activityResultLauncher.launch(intent)
    }

    override fun openStickerPicker(
            context: Context,
            activityResultLauncher: ActivityResultLauncher<Intent>,
            roomId: String,
            widget: Widget
    ) {
        val widgetArgs = widgetArgsBuilder.buildStickerPickerArgs(roomId, widget)
        val intent = WidgetActivity.newIntent(context, widgetArgs)
        activityResultLauncher.launch(intent)
    }

    override fun openIntegrationManager(
            context: Context,
            activityResultLauncher: ActivityResultLauncher<Intent>,
            roomId: String,
            integId: String?,
            screen: String?
    ) {
        val widgetArgs = widgetArgsBuilder.buildIntegrationManagerArgs(roomId, integId, screen)
        val intent = WidgetActivity.newIntent(context, widgetArgs)
        activityResultLauncher.launch(intent)
    }

    override fun openRoomWidget(context: Context, roomId: String, widget: Widget, options: Map<String, Any>?) {
        if (widget.type is WidgetType.Jitsi) {
            // Jitsi SDK is now for API 23+
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                MaterialAlertDialogBuilder(context)
                        .setTitle(R.string.dialog_title_error)
                        .setMessage(R.string.error_jitsi_not_supported_on_old_device)
                        .setPositiveButton(R.string.ok, null)
                        .show()
            } else {
                val enableVideo = options?.get(JitsiCallViewModel.ENABLE_VIDEO_OPTION) == true
                context.startActivity(VectorJitsiActivity.newIntent(context, roomId = roomId, widgetId = widget.widgetId, enableVideo = enableVideo))
            }
        } else if (widget.type is WidgetType.ElementCall) {
            val widgetArgs = widgetArgsBuilder.buildElementCallWidgetArgs(roomId, widget)
            context.startActivity(WidgetActivity.newIntent(context, widgetArgs))
        } else {
            val widgetArgs = widgetArgsBuilder.buildRoomWidgetArgs(roomId, widget)
            context.startActivity(WidgetActivity.newIntent(context, widgetArgs))
        }
    }

    override fun openPinCode(
            context: Context,
            activityResultLauncher: ActivityResultLauncher<Intent>,
            pinMode: PinMode
    ) {
        val intent = PinActivity.newIntent(context, PinArgs(pinMode))
        activityResultLauncher.launch(intent)
    }

    override fun openMediaViewer(
            activity: Activity,
            roomId: String,
            mediaData: AttachmentData,
            view: View,
            inMemory: List<AttachmentData>,
            options: ((MutableList<Pair<View, String>>) -> Unit)?
    ) {
        VectorAttachmentViewerActivity.newIntent(
                activity,
                mediaData,
                roomId,
                mediaData.eventId,
                inMemory,
                ViewCompat.getTransitionName(view)
        ).let { intent ->
            val pairs = ArrayList<Pair<View, String>>()
            activity.window.decorView.findViewById<View>(android.R.id.statusBarBackground)?.let {
                pairs.add(Pair(it, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME))
            }
            activity.window.decorView.findViewById<View>(android.R.id.navigationBarBackground)?.let {
                pairs.add(Pair(it, Window.NAVIGATION_BAR_BACKGROUND_TRANSITION_NAME))
            }

            pairs.add(Pair(view, ViewCompat.getTransitionName(view) ?: ""))
            options?.invoke(pairs)

            val bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *pairs.toTypedArray()).toBundle()
            activity.startActivity(intent, bundle)
        }
    }

    override fun openSearch(
            context: Context,
            roomId: String,
            roomDisplayName: String?,
            roomAvatarUrl: String?
    ) {
        val intent = SearchActivity.newIntent(context, SearchArgs(roomId, roomDisplayName, roomAvatarUrl))
        context.startActivity(intent)
    }

    override fun openDevTools(context: Context, roomId: String) {
        context.startActivity(RoomDevToolActivity.intent(context, roomId))
    }

    override fun openCallTransfer(
            context: Context,
            activityResultLauncher: ActivityResultLauncher<Intent>,
            callId: String
    ) {
        val intent = CallTransferActivity.newIntent(context, callId)
        activityResultLauncher.launch(intent)
    }

    override fun openCreatePoll(context: Context, roomId: String, editedEventId: String?, mode: PollMode) {
        val intent = CreatePollActivity.getIntent(
                context,
                CreatePollArgs(roomId = roomId, editedEventId = editedEventId, mode = mode)
        )
        context.startActivity(intent)
    }

    override fun openLocationSharing(
            context: Context,
            roomId: String,
            mode: LocationSharingMode,
            initialLocationData: LocationData?,
            locationOwnerId: String?
    ) {
        val intent = LocationSharingActivity.getIntent(
                context,
                LocationSharingArgs(roomId = roomId, mode = mode, initialLocationData = initialLocationData, locationOwnerId = locationOwnerId)
        )
        context.startActivity(intent)
    }

    override fun openLiveLocationMap(context: Context, roomId: String) {
        val intent = LiveLocationMapViewActivity.getIntent(
                context = context,
                liveLocationMapViewArgs = LiveLocationMapViewArgs(roomId = roomId)
        )
        context.startActivity(intent)
    }

    private fun startActivity(context: Context, intent: Intent, buildTask: Boolean) {
        if (buildTask) {
            val stackBuilder = TaskStackBuilder.create(context)
            stackBuilder.addNextIntentWithParentStack(intent)
            stackBuilder.startActivities()
        } else {
            context.startActivity(intent)
        }
    }

    override fun openThread(context: Context, threadTimelineArgs: ThreadTimelineArgs, eventIdToNavigate: String?) {
        context.startActivity(
                ThreadsActivity.newIntent(
                        context = context,
                        threadTimelineArgs = threadTimelineArgs,
                        threadListArgs = null,
                        eventIdToNavigate = eventIdToNavigate
                )
        )
    }

    override fun openThreadList(context: Context, threadTimelineArgs: ThreadTimelineArgs) {
        context.startActivity(
                ThreadsActivity.newIntent(
                        context = context,
                        threadTimelineArgs = null,
                        threadListArgs = ThreadListArgs(
                                roomId = threadTimelineArgs.roomId,
                                displayName = threadTimelineArgs.displayName,
                                avatarUrl = threadTimelineArgs.avatarUrl,
                                roomEncryptionTrustLevel = threadTimelineArgs.roomEncryptionTrustLevel
                        )
                )
        )
    }

    override fun openScreenSharingPermissionDialog(
            screenCaptureIntent: Intent,
            activityResultLauncher: ActivityResultLauncher<Intent>
    ) {
        activityResultLauncher.launch(screenCaptureIntent)
    }

    override fun openLoginWithQrCode(context: Context, qrCodeLoginArgs: QrCodeLoginArgs) {
        QrCodeLoginActivity
                .getIntent(context, qrCodeLoginArgs)
                .also {
                    context.startActivity(it)
                }
    }

    private fun Intent.start(context: Context) {
        context.startActivity(this)
    }
}
