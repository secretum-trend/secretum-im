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

package com.messaging.scrtm.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.multibindings.IntoMap
import com.messaging.scrtm.features.analytics.accountdata.AnalyticsAccountDataViewModel
import com.messaging.scrtm.features.analytics.ui.consent.AnalyticsConsentViewModel
import com.messaging.scrtm.features.attachments.AttachmentTypeSelectorViewModel
import com.messaging.scrtm.features.auth.ReAuthViewModel
import com.messaging.scrtm.features.call.VectorCallViewModel
import com.messaging.scrtm.features.call.conference.JitsiCallViewModel
import com.messaging.scrtm.features.call.transfer.CallTransferViewModel
import com.messaging.scrtm.features.contactsbook.ContactsBookViewModel
import com.messaging.scrtm.features.createdirect.CreateDirectRoomViewModel
import com.messaging.scrtm.features.crypto.keysbackup.settings.KeysBackupSettingsViewModel
import com.messaging.scrtm.features.crypto.quads.SharedSecureStorageViewModel
import com.messaging.scrtm.features.crypto.recover.BootstrapSharedViewModel
import com.messaging.scrtm.features.crypto.verification.VerificationBottomSheetViewModel
import com.messaging.scrtm.features.crypto.verification.choose.VerificationChooseMethodViewModel
import com.messaging.scrtm.features.crypto.verification.emoji.VerificationEmojiCodeViewModel
import com.messaging.scrtm.features.devtools.RoomDevToolViewModel
import com.messaging.scrtm.features.discovery.DiscoverySettingsViewModel
import com.messaging.scrtm.features.discovery.change.SetIdentityServerViewModel
import com.messaging.scrtm.features.home.HomeActivityViewModel
import com.messaging.scrtm.features.home.HomeDetailViewModel
import com.messaging.scrtm.features.home.UnknownDeviceDetectorSharedViewModel
import com.messaging.scrtm.features.home.UnreadMessagesSharedViewModel
import com.messaging.scrtm.features.home.UserColorAccountDataViewModel
import com.messaging.scrtm.features.home.room.breadcrumbs.BreadcrumbsViewModel
import com.messaging.scrtm.features.home.room.detail.TimelineViewModel
import com.messaging.scrtm.features.home.room.detail.composer.MessageComposerViewModel
import com.messaging.scrtm.features.home.room.detail.composer.link.SetLinkViewModel
import com.messaging.scrtm.features.home.room.detail.search.SearchViewModel
import com.messaging.scrtm.features.home.room.detail.timeline.action.MessageActionsViewModel
import com.messaging.scrtm.features.home.room.detail.timeline.edithistory.ViewEditHistoryViewModel
import com.messaging.scrtm.features.home.room.detail.timeline.reactions.ViewReactionsViewModel
import com.messaging.scrtm.features.home.room.detail.upgrade.MigrateRoomViewModel
import com.messaging.scrtm.features.home.room.list.RoomListViewModel
import com.messaging.scrtm.features.home.room.list.home.HomeRoomListViewModel
import com.messaging.scrtm.features.home.room.list.home.invites.InvitesViewModel
import com.messaging.scrtm.features.home.room.list.home.release.ReleaseNotesViewModel
import com.messaging.scrtm.features.homeserver.HomeServerCapabilitiesViewModel
import com.messaging.scrtm.features.invite.InviteUsersToRoomViewModel
import com.messaging.scrtm.features.location.LocationSharingViewModel
import com.messaging.scrtm.features.location.live.map.LiveLocationMapViewModel
import com.messaging.scrtm.features.location.preview.LocationPreviewViewModel
import com.messaging.scrtm.features.login.LoginViewModel
import com.messaging.scrtm.features.login.qr.QrCodeLoginViewModel
import com.messaging.scrtm.features.matrixto.MatrixToBottomSheetViewModel
import com.messaging.scrtm.features.media.VectorAttachmentViewerViewModel
import com.messaging.scrtm.features.onboarding.OnboardingViewModel
import com.messaging.scrtm.features.poll.create.CreatePollViewModel
import com.messaging.scrtm.features.qrcode.QrCodeScannerViewModel
import com.messaging.scrtm.features.rageshake.BugReportViewModel
import com.messaging.scrtm.features.reactions.EmojiSearchResultViewModel
import com.messaging.scrtm.features.room.RequireActiveMembershipViewModel
import com.messaging.scrtm.features.roomdirectory.RoomDirectoryViewModel
import com.messaging.scrtm.features.roomdirectory.createroom.CreateRoomViewModel
import com.messaging.scrtm.features.roomdirectory.picker.RoomDirectoryPickerViewModel
import com.messaging.scrtm.features.roomdirectory.roompreview.RoomPreviewViewModel
import com.messaging.scrtm.features.roommemberprofile.RoomMemberProfileViewModel
import com.messaging.scrtm.features.roommemberprofile.devices.DeviceListBottomSheetViewModel
import com.messaging.scrtm.features.roomprofile.RoomProfileViewModel
import com.messaging.scrtm.features.roomprofile.alias.RoomAliasViewModel
import com.messaging.scrtm.features.roomprofile.alias.detail.RoomAliasBottomSheetViewModel
import com.messaging.scrtm.features.roomprofile.banned.RoomBannedMemberListViewModel
import com.messaging.scrtm.features.roomprofile.members.RoomMemberListViewModel
import com.messaging.scrtm.features.roomprofile.notifications.RoomNotificationSettingsViewModel
import com.messaging.scrtm.features.roomprofile.permissions.RoomPermissionsViewModel
import com.messaging.scrtm.features.roomprofile.polls.RoomPollsViewModel
import com.messaging.scrtm.features.roomprofile.settings.RoomSettingsViewModel
import com.messaging.scrtm.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedViewModel
import com.messaging.scrtm.features.roomprofile.uploads.RoomUploadsViewModel
import com.messaging.scrtm.features.settings.account.deactivation.DeactivateAccountViewModel
import com.messaging.scrtm.features.settings.crosssigning.CrossSigningSettingsViewModel
import com.messaging.scrtm.features.settings.devices.DeviceVerificationInfoBottomSheetViewModel
import com.messaging.scrtm.features.settings.devices.DevicesViewModel
import com.messaging.scrtm.features.settings.devices.v2.details.SessionDetailsViewModel
import com.messaging.scrtm.features.settings.devices.v2.more.SessionLearnMoreViewModel
import com.messaging.scrtm.features.settings.devices.v2.othersessions.OtherSessionsViewModel
import com.messaging.scrtm.features.settings.devices.v2.overview.SessionOverviewViewModel
import com.messaging.scrtm.features.settings.devices.v2.rename.RenameSessionViewModel
import com.messaging.scrtm.features.settings.devtools.AccountDataViewModel
import com.messaging.scrtm.features.settings.devtools.GossipingEventsPaperTrailViewModel
import com.messaging.scrtm.features.settings.devtools.KeyRequestListViewModel
import com.messaging.scrtm.features.settings.devtools.KeyRequestViewModel
import com.messaging.scrtm.features.settings.font.FontScaleSettingViewModel
import com.messaging.scrtm.features.settings.homeserver.HomeserverSettingsViewModel
import com.messaging.scrtm.features.settings.ignored.IgnoredUsersViewModel
import com.messaging.scrtm.features.settings.labs.VectorSettingsLabsViewModel
import com.messaging.scrtm.features.settings.legals.LegalsViewModel
import com.messaging.scrtm.features.settings.locale.LocalePickerViewModel
import com.messaging.scrtm.features.settings.notifications.VectorSettingsNotificationPreferenceViewModel
import com.messaging.scrtm.features.settings.push.PushGatewaysViewModel
import com.messaging.scrtm.features.settings.threepids.ThreePidsSettingsViewModel
import com.messaging.scrtm.features.share.IncomingShareViewModel
import com.messaging.scrtm.features.signout.soft.SoftLogoutViewModel
import com.messaging.scrtm.features.spaces.SpaceListViewModel
import com.messaging.scrtm.features.spaces.SpaceMenuViewModel
import com.messaging.scrtm.features.spaces.create.CreateSpaceViewModel
import com.messaging.scrtm.features.spaces.explore.SpaceDirectoryViewModel
import com.messaging.scrtm.features.spaces.invite.SpaceInviteBottomSheetViewModel
import com.messaging.scrtm.features.spaces.leave.SpaceLeaveAdvancedViewModel
import com.messaging.scrtm.features.spaces.manage.SpaceAddRoomsViewModel
import com.messaging.scrtm.features.spaces.manage.SpaceManageRoomsViewModel
import com.messaging.scrtm.features.spaces.manage.SpaceManageSharedViewModel
import com.messaging.scrtm.features.spaces.people.SpacePeopleViewModel
import com.messaging.scrtm.features.spaces.preview.SpacePreviewViewModel
import com.messaging.scrtm.features.spaces.share.ShareSpaceViewModel
import com.messaging.scrtm.features.start.StartAppViewModel
import com.messaging.scrtm.features.terms.ReviewTermsViewModel
import com.messaging.scrtm.features.usercode.UserCodeSharedViewModel
import com.messaging.scrtm.features.userdirectory.UserListViewModel
import com.messaging.scrtm.features.widgets.WidgetViewModel
import com.messaging.scrtm.features.widgets.permissions.RoomWidgetPermissionViewModel
import com.messaging.scrtm.features.workers.signout.ServerBackupStatusViewModel
import com.messaging.scrtm.features.workers.signout.SignoutCheckViewModel

@InstallIn(MavericksViewModelComponent::class)
@Module
interface MavericksViewModelModule {

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomListViewModel::class)
    fun roomListViewModelFactory(factory: RoomListViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceManageRoomsViewModel::class)
    fun spaceManageRoomsViewModelFactory(factory: SpaceManageRoomsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceManageSharedViewModel::class)
    fun spaceManageSharedViewModelFactory(factory: SpaceManageSharedViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceListViewModel::class)
    fun spaceListViewModelFactory(factory: SpaceListViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ReAuthViewModel::class)
    fun reAuthViewModelFactory(factory: ReAuthViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(VectorCallViewModel::class)
    fun vectorCallViewModelFactory(factory: VectorCallViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(JitsiCallViewModel::class)
    fun jitsiCallViewModelFactory(factory: JitsiCallViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomDirectoryViewModel::class)
    fun roomDirectoryViewModelFactory(factory: RoomDirectoryViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ViewReactionsViewModel::class)
    fun viewReactionsViewModelFactory(factory: ViewReactionsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomWidgetPermissionViewModel::class)
    fun roomWidgetPermissionViewModelFactory(factory: RoomWidgetPermissionViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(WidgetViewModel::class)
    fun widgetViewModelFactory(factory: WidgetViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ServerBackupStatusViewModel::class)
    fun serverBackupStatusViewModelFactory(factory: ServerBackupStatusViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SignoutCheckViewModel::class)
    fun signoutCheckViewModelFactory(factory: SignoutCheckViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomDirectoryPickerViewModel::class)
    fun roomDirectoryPickerViewModelFactory(factory: RoomDirectoryPickerViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomDevToolViewModel::class)
    fun roomDevToolViewModelFactory(factory: RoomDevToolViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(MigrateRoomViewModel::class)
    fun migrateRoomViewModelFactory(factory: MigrateRoomViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(IgnoredUsersViewModel::class)
    fun ignoredUsersViewModelFactory(factory: IgnoredUsersViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(CallTransferViewModel::class)
    fun callTransferViewModelFactory(factory: CallTransferViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ContactsBookViewModel::class)
    fun contactsBookViewModelFactory(factory: ContactsBookViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(CreateDirectRoomViewModel::class)
    fun createDirectRoomViewModelFactory(factory: CreateDirectRoomViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(QrCodeScannerViewModel::class)
    fun qrCodeViewModelFactory(factory: QrCodeScannerViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomNotificationSettingsViewModel::class)
    fun roomNotificationSettingsViewModelFactory(factory: RoomNotificationSettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(KeysBackupSettingsViewModel::class)
    fun keysBackupSettingsViewModelFactory(factory: KeysBackupSettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SharedSecureStorageViewModel::class)
    fun sharedSecureStorageViewModelFactory(factory: SharedSecureStorageViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(UserListViewModel::class)
    fun userListViewModelFactory(factory: UserListViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(UserCodeSharedViewModel::class)
    fun userCodeSharedViewModelFactory(factory: UserCodeSharedViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ReviewTermsViewModel::class)
    fun reviewTermsViewModelFactory(factory: ReviewTermsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ShareSpaceViewModel::class)
    fun shareSpaceViewModelFactory(factory: ShareSpaceViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpacePreviewViewModel::class)
    fun spacePreviewViewModelFactory(factory: SpacePreviewViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpacePeopleViewModel::class)
    fun spacePeopleViewModelFactory(factory: SpacePeopleViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceAddRoomsViewModel::class)
    fun spaceAddRoomsViewModelFactory(factory: SpaceAddRoomsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceLeaveAdvancedViewModel::class)
    fun spaceLeaveAdvancedViewModelFactory(factory: SpaceLeaveAdvancedViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceInviteBottomSheetViewModel::class)
    fun spaceInviteBottomSheetViewModelFactory(factory: SpaceInviteBottomSheetViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceDirectoryViewModel::class)
    fun spaceDirectoryViewModelFactory(factory: SpaceDirectoryViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(CreateSpaceViewModel::class)
    fun createSpaceViewModelFactory(factory: CreateSpaceViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SpaceMenuViewModel::class)
    fun spaceMenuViewModelFactory(factory: SpaceMenuViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SoftLogoutViewModel::class)
    fun softLogoutViewModelFactory(factory: SoftLogoutViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(IncomingShareViewModel::class)
    fun incomingShareViewModelFactory(factory: IncomingShareViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ThreePidsSettingsViewModel::class)
    fun threePidsSettingsViewModelFactory(factory: ThreePidsSettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(PushGatewaysViewModel::class)
    fun pushGatewaysViewModelFactory(factory: PushGatewaysViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(HomeserverSettingsViewModel::class)
    fun homeserverSettingsViewModelFactory(factory: HomeserverSettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(LocalePickerViewModel::class)
    fun localePickerViewModelFactory(factory: LocalePickerViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(GossipingEventsPaperTrailViewModel::class)
    fun gossipingEventsPaperTrailViewModelFactory(factory: GossipingEventsPaperTrailViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(AccountDataViewModel::class)
    fun accountDataViewModelFactory(factory: AccountDataViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(DevicesViewModel::class)
    fun devicesViewModelFactory(factory: DevicesViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(com.messaging.scrtm.features.settings.devices.v2.DevicesViewModel::class)
    fun devicesViewModelV2Factory(factory: com.messaging.scrtm.features.settings.devices.v2.DevicesViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(KeyRequestListViewModel::class)
    fun keyRequestListViewModelFactory(factory: KeyRequestListViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(KeyRequestViewModel::class)
    fun keyRequestViewModelFactory(factory: KeyRequestViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(CrossSigningSettingsViewModel::class)
    fun crossSigningSettingsViewModelFactory(factory: CrossSigningSettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(DeactivateAccountViewModel::class)
    fun deactivateAccountViewModelFactory(factory: DeactivateAccountViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomUploadsViewModel::class)
    fun roomUploadsViewModelFactory(factory: RoomUploadsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomJoinRuleChooseRestrictedViewModel::class)
    fun roomJoinRuleChooseRestrictedViewModelFactory(factory: RoomJoinRuleChooseRestrictedViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomSettingsViewModel::class)
    fun roomSettingsViewModelFactory(factory: RoomSettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomPermissionsViewModel::class)
    fun roomPermissionsViewModelFactory(factory: RoomPermissionsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomMemberListViewModel::class)
    fun roomMemberListViewModelFactory(factory: RoomMemberListViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomBannedMemberListViewModel::class)
    fun roomBannedMemberListViewModelFactory(factory: RoomBannedMemberListViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomAliasViewModel::class)
    fun roomAliasViewModelFactory(factory: RoomAliasViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomAliasBottomSheetViewModel::class)
    fun roomAliasBottomSheetViewModelFactory(factory: RoomAliasBottomSheetViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomProfileViewModel::class)
    fun roomProfileViewModelFactory(factory: RoomProfileViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomMemberProfileViewModel::class)
    fun roomMemberProfileViewModelFactory(factory: RoomMemberProfileViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(UserColorAccountDataViewModel::class)
    fun userColorAccountDataViewModelFactory(factory: UserColorAccountDataViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomPreviewViewModel::class)
    fun roomPreviewViewModelFactory(factory: RoomPreviewViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(CreateRoomViewModel::class)
    fun createRoomViewModelFactory(factory: CreateRoomViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RequireActiveMembershipViewModel::class)
    fun requireActiveMembershipViewModelFactory(factory: RequireActiveMembershipViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(EmojiSearchResultViewModel::class)
    fun emojiSearchResultViewModelFactory(factory: EmojiSearchResultViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(BugReportViewModel::class)
    fun bugReportViewModelFactory(factory: BugReportViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(MatrixToBottomSheetViewModel::class)
    fun matrixToBottomSheetViewModelFactory(factory: MatrixToBottomSheetViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(OnboardingViewModel::class)
    fun onboardingViewModelFactory(factory: OnboardingViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(LoginViewModel::class)
    fun loginViewModelFactory(factory: LoginViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(AnalyticsConsentViewModel::class)
    fun analyticsConsentViewModelFactory(factory: AnalyticsConsentViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(AnalyticsAccountDataViewModel::class)
    fun analyticsAccountDataViewModelFactory(factory: AnalyticsAccountDataViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(StartAppViewModel::class)
    fun startAppViewModelFactory(factory: StartAppViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(HomeServerCapabilitiesViewModel::class)
    fun homeServerCapabilitiesViewModelFactory(factory: HomeServerCapabilitiesViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(InviteUsersToRoomViewModel::class)
    fun inviteUsersToRoomViewModelFactory(factory: InviteUsersToRoomViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ViewEditHistoryViewModel::class)
    fun viewEditHistoryViewModelFactory(factory: ViewEditHistoryViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(MessageActionsViewModel::class)
    fun messageActionsViewModelFactory(factory: MessageActionsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(VerificationChooseMethodViewModel::class)
    fun verificationChooseMethodViewModelFactory(factory: VerificationChooseMethodViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(VerificationEmojiCodeViewModel::class)
    fun verificationEmojiCodeViewModelFactory(factory: VerificationEmojiCodeViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SearchViewModel::class)
    fun searchViewModelFactory(factory: SearchViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(UnreadMessagesSharedViewModel::class)
    fun unreadMessagesSharedViewModelFactory(factory: UnreadMessagesSharedViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(UnknownDeviceDetectorSharedViewModel::class)
    fun unknownDeviceDetectorSharedViewModelFactory(factory: UnknownDeviceDetectorSharedViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(DiscoverySettingsViewModel::class)
    fun discoverySettingsViewModelFactory(factory: DiscoverySettingsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(LegalsViewModel::class)
    fun legalsViewModelFactory(factory: LegalsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(TimelineViewModel::class)
    fun roomDetailViewModelFactory(factory: TimelineViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(MessageComposerViewModel::class)
    fun messageComposerViewModelFactory(factory: MessageComposerViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SetIdentityServerViewModel::class)
    fun setIdentityServerViewModelFactory(factory: SetIdentityServerViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(BreadcrumbsViewModel::class)
    fun breadcrumbsViewModelFactory(factory: BreadcrumbsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(HomeDetailViewModel::class)
    fun homeDetailViewModelFactory(factory: HomeDetailViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(DeviceVerificationInfoBottomSheetViewModel::class)
    fun deviceVerificationInfoBottomSheetViewModelFactory(factory: DeviceVerificationInfoBottomSheetViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(DeviceListBottomSheetViewModel::class)
    fun deviceListBottomSheetViewModelFactory(factory: DeviceListBottomSheetViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(HomeActivityViewModel::class)
    fun homeActivityViewModelFactory(factory: HomeActivityViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(BootstrapSharedViewModel::class)
    fun bootstrapSharedViewModelFactory(factory: BootstrapSharedViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(VerificationBottomSheetViewModel::class)
    fun verificationBottomSheetViewModelFactory(factory: VerificationBottomSheetViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(CreatePollViewModel::class)
    fun createPollViewModelFactory(factory: CreatePollViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(LocationSharingViewModel::class)
    fun createLocationSharingViewModelFactory(factory: LocationSharingViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(LocationPreviewViewModel::class)
    fun createLocationPreviewViewModelFactory(factory: LocationPreviewViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(VectorAttachmentViewerViewModel::class)
    fun vectorAttachmentViewerViewModelFactory(factory: VectorAttachmentViewerViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(LiveLocationMapViewModel::class)
    fun liveLocationMapViewModelFactory(factory: LiveLocationMapViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(FontScaleSettingViewModel::class)
    fun fontScaleSettingViewModelFactory(factory: FontScaleSettingViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(HomeRoomListViewModel::class)
    fun homeRoomListViewModel(factory: HomeRoomListViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(InvitesViewModel::class)
    fun invitesViewModel(factory: InvitesViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(ReleaseNotesViewModel::class)
    fun releaseNotesViewModel(factory: ReleaseNotesViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SessionOverviewViewModel::class)
    fun sessionOverviewViewModelFactory(factory: SessionOverviewViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(OtherSessionsViewModel::class)
    fun otherSessionsViewModelFactory(factory: OtherSessionsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SessionDetailsViewModel::class)
    fun sessionDetailsViewModelFactory(factory: SessionDetailsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RenameSessionViewModel::class)
    fun renameSessionViewModelFactory(factory: RenameSessionViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(QrCodeLoginViewModel::class)
    fun qrCodeLoginViewModelFactory(factory: QrCodeLoginViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SessionLearnMoreViewModel::class)
    fun sessionLearnMoreViewModelFactory(factory: SessionLearnMoreViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(VectorSettingsLabsViewModel::class)
    fun vectorSettingsLabsViewModelFactory(factory: VectorSettingsLabsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(AttachmentTypeSelectorViewModel::class)
    fun attachmentTypeSelectorViewModelFactory(factory: AttachmentTypeSelectorViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(VectorSettingsNotificationPreferenceViewModel::class)
    fun vectorSettingsNotificationPreferenceViewModelFactory(
            factory: VectorSettingsNotificationPreferenceViewModel.Factory
    ): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(SetLinkViewModel::class)
    fun setLinkViewModelFactory(factory: SetLinkViewModel.Factory): MavericksAssistedViewModelFactory<*, *>

    @Binds
    @IntoMap
    @MavericksViewModelKey(RoomPollsViewModel::class)
    fun roomPollsViewModelFactory(factory: RoomPollsViewModel.Factory): MavericksAssistedViewModelFactory<*, *>
}
