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

package com.messaging.scrtm.features.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.airbnb.mvrx.activityViewModel
import com.airbnb.mvrx.fragmentViewModel
import com.airbnb.mvrx.withState
import com.google.android.material.appbar.AppBarLayout
import dagger.hilt.android.AndroidEntryPoint
import com.messaging.scrtm.R
import com.messaging.scrtm.SpaceStateHandler
import com.messaging.scrtm.core.extensions.commitTransaction
import com.messaging.scrtm.core.platform.OnBackPressed
import com.messaging.scrtm.core.platform.VectorBaseActivity
import com.messaging.scrtm.core.platform.VectorBaseFragment
import com.messaging.scrtm.core.platform.VectorMenuProvider
import com.messaging.scrtm.core.resources.BuildMeta
import com.messaging.scrtm.core.resources.ColorProvider
import com.messaging.scrtm.core.ui.views.CurrentCallsView
import com.messaging.scrtm.core.ui.views.CurrentCallsViewPresenter
import com.messaging.scrtm.core.ui.views.KeysBackupBanner
import com.messaging.scrtm.databinding.FragmentNewHomeDetailBinding
import com.messaging.scrtm.features.call.SharedKnownCallsViewModel
import com.messaging.scrtm.features.call.VectorCallActivity
import com.messaging.scrtm.features.call.dialpad.PstnDialActivity
import com.messaging.scrtm.features.call.webrtc.WebRtcCallManager
import com.messaging.scrtm.features.home.room.list.actions.RoomListSharedAction
import com.messaging.scrtm.features.home.room.list.actions.RoomListSharedActionViewModel
import com.messaging.scrtm.features.home.room.list.home.HomeRoomListFragment
import com.messaging.scrtm.features.home.room.list.home.NewChatBottomSheet
import com.messaging.scrtm.features.popup.PopupAlertManager
import com.messaging.scrtm.features.popup.VerificationVectorAlert
import com.messaging.scrtm.features.settings.VectorPreferences
import com.messaging.scrtm.features.settings.VectorSettingsActivity.Companion.EXTRA_DIRECT_ACCESS_SECURITY_PRIVACY_MANAGE_SESSIONS
import com.messaging.scrtm.features.spaces.SpaceListBottomSheet
import com.messaging.scrtm.features.workers.signout.BannerState
import com.messaging.scrtm.features.workers.signout.ServerBackupStatusAction
import com.messaging.scrtm.features.workers.signout.ServerBackupStatusViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.matrix.android.sdk.api.session.crypto.model.DeviceInfo
import org.matrix.android.sdk.api.session.room.model.RoomSummary
import javax.inject.Inject

@AndroidEntryPoint
class NewHomeDetailFragment :
        VectorBaseFragment<FragmentNewHomeDetailBinding>(),
        KeysBackupBanner.Delegate,
        CurrentCallsView.Callback,
        OnBackPressed,
        VectorMenuProvider {

    @Inject lateinit var avatarRenderer: AvatarRenderer
    @Inject lateinit var colorProvider: ColorProvider
    @Inject lateinit var alertManager: PopupAlertManager
    @Inject lateinit var callManager: WebRtcCallManager
    @Inject lateinit var vectorPreferences: VectorPreferences
    @Inject lateinit var spaceStateHandler: SpaceStateHandler
    @Inject lateinit var buildMeta: BuildMeta


    private val viewModel: HomeDetailViewModel by fragmentViewModel()
    private val unknownDeviceDetectorSharedViewModel: UnknownDeviceDetectorSharedViewModel by activityViewModel()
    private val serverBackupStatusViewModel: ServerBackupStatusViewModel by activityViewModel()

    private lateinit var sharedActionViewModel: HomeSharedActionViewModel
    private lateinit var sharedRoomListActionViewModel: RoomListSharedActionViewModel
    private lateinit var sharedCallActionViewModel: SharedKnownCallsViewModel

    private val newChatBottomSheet = NewChatBottomSheet()
    private val spaceListBottomSheet = SpaceListBottomSheet()

    private var hasUnreadRooms = false
        set(value) {
            if (value != field) {
                field = value
                invalidateOptionsMenu()
            }
        }

    private var searchButton = R.id.search_button
    private var moreButton = R.id.more_button

    override fun getMenuRes() = R.menu.room_list

    override fun handleMenuItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_home_mark_all_as_read -> {
                viewModel.handle(HomeDetailAction.MarkAllRoomsRead)
                true
            }
            R.id.menu_home_dialpad -> {
                startActivity(Intent(requireContext(), PstnDialActivity::class.java))
                true
            }
            else -> false
        }
    }

    override fun handlePrepareMenu(menu: Menu) {
        withState(viewModel) { state ->
            val isRoomList = state.currentTab is HomeTab.RoomList
            menu.findItem(R.id.menu_home_mark_all_as_read).isVisible = isRoomList && hasUnreadRooms
            menu.findItem(R.id.menu_home_dialpad).isVisible = state.showDialPadTab
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentNewHomeDetailBinding {
        return FragmentNewHomeDetailBinding.inflate(inflater, container, false)
    }

    private val currentCallsViewPresenter = CurrentCallsViewPresenter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedActionViewModel = activityViewModelProvider.get(HomeSharedActionViewModel::class.java)
        sharedCallActionViewModel = activityViewModelProvider.get(SharedKnownCallsViewModel::class.java)
        setupToolbar()
        setupKeysBackupBanner()
        setupActiveCallView()
        setupDebugButton()
        setupFabs()
        setupObservers()

        childFragmentManager.commitTransaction {
            add(R.id.roomListContainer, HomeRoomListFragment::class.java, null, HOME_ROOM_LIST_FRAGMENT_TAG)
        }

        viewModel.onEach(HomeDetailViewState::selectedSpace) { selectedSpace ->
            onSpaceChange(selectedSpace)
        }

        viewModel.observeViewEvents { viewEvent ->
            when (viewEvent) {
                HomeDetailViewEvents.CallStarted -> Unit
                is HomeDetailViewEvents.FailToCall -> Unit
                HomeDetailViewEvents.Loading -> showLoadingDialog()
            }
        }

        unknownDeviceDetectorSharedViewModel.onEach { state ->
            state.unknownSessions.invoke()?.let { unknownDevices ->
                if (unknownDevices.firstOrNull()?.currentSessionTrust == true) {
                    val uid = PopupAlertManager.REVIEW_LOGIN_UID
                    alertManager.cancelAlert(uid)
                    val olderUnverified = unknownDevices.filter { !it.isNew }
                    val newest = unknownDevices.firstOrNull { it.isNew }?.deviceInfo
                    if (newest != null) {
                        promptForNewUnknownDevices(uid, state, newest)
                    } else if (olderUnverified.isNotEmpty()) {
                        // In this case we prompt to go to settings to review logins
                        promptToReviewChanges(uid, state, olderUnverified.map { it.deviceInfo })
                    }
                }
            }
        }

        sharedCallActionViewModel
                .liveKnownCalls
                .observe(viewLifecycleOwner) {
                    currentCallsViewPresenter.updateCall(callManager.getCurrentCall(), callManager.getCalls())
                    invalidateOptionsMenu()
                }
    }

    private fun setupObservers() {
        sharedRoomListActionViewModel = activityViewModelProvider[RoomListSharedActionViewModel::class.java]

        sharedRoomListActionViewModel
                .stream()
                .onEach(::handleSharedAction)
                .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleSharedAction(action: RoomListSharedAction) {
        when (action) {
            RoomListSharedAction.CloseBottomSheet -> spaceListBottomSheet.dismiss()
        }
    }

    private fun setupFabs() {
        showFABs()

        views.newLayoutCreateChatButton.debouncedClicks {
            newChatBottomSheet.takeIf { !it.isAdded }?.show(requireActivity().supportFragmentManager, NewChatBottomSheet.TAG)
        }

        views.newLayoutOpenSpacesButton.debouncedClicks {
            spaceListBottomSheet.takeIf { !it.isAdded }?.show(requireActivity().supportFragmentManager, SpaceListBottomSheet.TAG)
        }
    }

    private fun showFABs() {
        views.newLayoutCreateChatButton.show()
        views.newLayoutOpenSpacesButton.show()
    }

    private fun setCurrentSpace(spaceId: String?) {
        spaceStateHandler.setCurrentSpace(spaceId, isForwardNavigation = false)
        sharedActionViewModel.post(HomeActivitySharedAction.OnCloseSpace)
    }

    override fun onDestroyView() {
        currentCallsViewPresenter.unBind()
        super.onDestroyView()
    }

    override fun onResume() {
        super.onResume()
        callManager.checkForProtocolsSupportIfNeeded()
        refreshSpaceState()
    }

    private fun refreshSpaceState() {
        spaceStateHandler.getCurrentSpace()?.let {
            onSpaceChange(it)
        }
    }

    private fun promptForNewUnknownDevices(uid: String, state: UnknownDevicesState, newest: DeviceInfo) {
        val user = state.myMatrixItem
        alertManager.postVectorAlert(
                VerificationVectorAlert(
                        uid = uid,
                        title = getString(R.string.new_session),
                        description = getString(R.string.verify_this_session, newest.displayName ?: newest.deviceId ?: ""),
                        iconId = R.drawable.ic_shield_warning
                ).apply {
                    viewBinder = VerificationVectorAlert.ViewBinder(user, avatarRenderer)
                    colorInt = colorProvider.getColorFromAttribute(R.attr.colorPrimary)
                    contentAction = Runnable {
                        (weakCurrentActivity?.get() as? VectorBaseActivity<*>)?.let { vectorBaseActivity ->
                            vectorBaseActivity.navigator
                                    .requestSessionVerification(vectorBaseActivity, newest.deviceId ?: "")
                        }
                        unknownDeviceDetectorSharedViewModel.handle(
                                UnknownDeviceDetectorSharedViewModel.Action.IgnoreNewLogin(newest.deviceId?.let { listOf(it) }.orEmpty())
                        )
                    }
                    dismissedAction = Runnable {
                        unknownDeviceDetectorSharedViewModel.handle(
                                UnknownDeviceDetectorSharedViewModel.Action.IgnoreNewLogin(newest.deviceId?.let { listOf(it) }.orEmpty())
                        )
                    }
                }
        )
    }

    private fun promptToReviewChanges(uid: String, state: UnknownDevicesState, oldUnverified: List<DeviceInfo>) {
        val user = state.myMatrixItem
        alertManager.postVectorAlert(
                VerificationVectorAlert(
                        uid = uid,
                        title = getString(R.string.review_unverified_sessions_title),
                        description = getString(R.string.review_unverified_sessions_description),
                        iconId = R.drawable.ic_shield_warning
                ).apply {
                    viewBinder = VerificationVectorAlert.ViewBinder(user, avatarRenderer)
                    colorInt = colorProvider.getColorFromAttribute(R.attr.colorPrimary)
                    contentAction = Runnable {
                        (weakCurrentActivity?.get() as? VectorBaseActivity<*>)?.let { activity ->
                            // mark as ignored to avoid showing it again
                            unknownDeviceDetectorSharedViewModel.handle(
                                    UnknownDeviceDetectorSharedViewModel.Action.IgnoreDevice(oldUnverified.mapNotNull { it.deviceId })
                            )
                            activity.navigator.openSettings(activity, EXTRA_DIRECT_ACCESS_SECURITY_PRIVACY_MANAGE_SESSIONS)
                        }
                    }
                    dismissedAction = Runnable {
                        unknownDeviceDetectorSharedViewModel.handle(
                                UnknownDeviceDetectorSharedViewModel.Action.IgnoreDevice(oldUnverified.mapNotNull { it.deviceId })
                        )
                    }
                }
        )
    }

    private fun onSpaceChange(spaceSummary: RoomSummary?) {
        // Home page navigation bar
        views.collapsingToolbar.title = (spaceSummary?.displayName ?: getString(R.string.all_chats))
    }

    private fun setupKeysBackupBanner() {
        serverBackupStatusViewModel.handle(ServerBackupStatusAction.OnBannerDisplayed)
        serverBackupStatusViewModel
                .onEach {
                    when (val banState = it.bannerState.invoke()) {
                        is BannerState.Setup,
                        BannerState.BackingUp,
                        BannerState.Hidden -> views.homeKeysBackupBanner.render(banState, false)
                        null -> views.homeKeysBackupBanner.render(BannerState.Hidden, false)
                        else -> Unit /* No op? */
                    }
                }
        views.homeKeysBackupBanner.delegate = this
    }

    private fun setupActiveCallView() {
        currentCallsViewPresenter.bind(views.currentCallsView, this)
    }

    private fun setupToolbar() {
//        setupToolbar(views.toolbar)
//
//        views.collapsingToolbar.debouncedClicks(::openSpaceSettings)
//        views.toolbar.debouncedClicks(::openSpaceSettings)
//        views.searchButton.debouncedClicks {
//            navigator.openCreateDirectRoom(requireActivity())
//        }
//

        views.searchButton.setOnClickListener {
            navigator.openCreateDirectRoom(requireActivity())
        }

        views.avatar.debouncedClicks {
            navigator.openSettings(requireContext())
        }
    }

    private fun openSpaceSettings() = withState(viewModel) { viewState ->
        viewState.selectedSpace?.let {
            sharedActionViewModel.post(HomeActivitySharedAction.ShowSpaceSettings(it.roomId))
        }
    }

    private fun setupDebugButton() {
        views.debugButton.debouncedClicks {
            sharedActionViewModel.post(HomeActivitySharedAction.CloseDrawer)
            navigator.openDebug(requireActivity())
        }

        views.appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
            views.debugButton.isVisible = verticalOffset == 0 && buildMeta.isDebug && vectorPreferences.developerMode()
        })
    }

    /* ==========================================================================================
     * KeysBackupBanner Listener
     * ========================================================================================== */

    override fun onCloseClicked() {
        serverBackupStatusViewModel.handle(ServerBackupStatusAction.OnBannerClosed)
    }

    override fun setupKeysBackup() {
        navigator.openKeysBackupSetup(requireActivity(), false)
    }

    override fun recoverKeysBackup() {
        navigator.openKeysBackupManager(requireActivity())
    }

    override fun invalidate() = withState(viewModel) {
        views.syncStateView.render(
                it.syncState,
                it.incrementalSyncRequestState,
                it.pushCounter,
                vectorPreferences.developerShowDebugInfo()
        )

        refreshAvatar()
        hasUnreadRooms = it.hasUnreadMessages
    }

    private fun refreshAvatar() = withState(viewModel) { state ->
        state.myMatrixItem?.let { user ->
            avatarRenderer.render(user, views.avatar)
        }
    }

    override fun onTapToReturnToCall() {
        callManager.getCurrentCall()?.let { call ->
            VectorCallActivity.newIntent(
                    context = requireContext(),
                    callId = call.callId,
                    signalingRoomId = call.signalingRoomId,
                    otherUserId = call.mxCall.opponentUserId,
                    isIncomingCall = !call.mxCall.isOutgoing,
                    isVideoCall = call.mxCall.isVideoCall,
                    mode = null
            ).let {
                startActivity(it)
            }
        }
    }

    override fun onBackPressed(toolbarButton: Boolean) = if (spaceStateHandler.isRoot()) {
        false
    } else {
        val lastSpace = spaceStateHandler.popSpaceBackstack()
        spaceStateHandler.setCurrentSpace(lastSpace, isForwardNavigation = false)
        true
    }

    private fun SpaceStateHandler.isRoot() = getSpaceBackstack().isEmpty()

    companion object {
        private const val HOME_ROOM_LIST_FRAGMENT_TAG = "TAG_HOME_ROOM_LIST"
    }
}
