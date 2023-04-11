package im.vector.app;

import dagger.Binds;
import dagger.Component;
import dagger.Module;
import dagger.Subcomponent;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.components.ServiceComponent;
import dagger.hilt.android.components.ViewComponent;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.components.ViewWithFragmentComponent;
import dagger.hilt.android.flags.FragmentGetContextFix;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.HiltViewModelFactory;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_DefaultViewModelFactories_ActivityModule;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint;
import dagger.hilt.android.internal.lifecycle.HiltWrapper_HiltViewModelFactory_ViewModelModule;
import dagger.hilt.android.internal.managers.ActivityComponentManager;
import dagger.hilt.android.internal.managers.FragmentComponentManager;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint;
import dagger.hilt.android.internal.managers.HiltWrapper_ActivityRetainedComponentManager_LifecycleModule;
import dagger.hilt.android.internal.managers.ServiceComponentManager;
import dagger.hilt.android.internal.managers.ViewComponentManager;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.HiltWrapper_ActivityModule;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import dagger.hilt.android.scopes.ServiceScoped;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.android.scopes.ViewScoped;
import dagger.hilt.components.SingletonComponent;
import dagger.hilt.internal.GeneratedComponent;
import dagger.hilt.migration.DisableInstallInCheck;
import im.vector.app.core.di.ActivityEntryPoint;
import im.vector.app.core.di.ConfigurationModule;
import im.vector.app.core.di.CreateMavericksViewModelComponent;
import im.vector.app.core.di.HiltMavericksEntryPoint;
import im.vector.app.core.di.HomeModule;
import im.vector.app.core.di.MavericksViewModelComponent;
import im.vector.app.core.di.MavericksViewModelComponentBuilder;
import im.vector.app.core.di.MavericksViewModelModule;
import im.vector.app.core.di.MavericksViewModelScoped;
import im.vector.app.core.di.ScreenModule;
import im.vector.app.core.di.SingletonEntryPoint;
import im.vector.app.core.di.VectorBindModule;
import im.vector.app.core.di.VectorStaticModule;
import im.vector.app.core.di.ViewModelModule;
import im.vector.app.core.di.VoiceModule;
import im.vector.app.core.di.login.di.LoginModule;
import im.vector.app.core.pushers.VectorUnifiedPushMessagingReceiver_GeneratedInjector;
import im.vector.app.core.services.CallAndroidService_GeneratedInjector;
import im.vector.app.core.services.VectorSyncAndroidService_GeneratedInjector;
import im.vector.app.core.ui.views.TypingMessageView_GeneratedInjector;
import im.vector.app.di.FlavorModule;
import im.vector.app.di.NotificationTestModule;
import im.vector.app.features.MainActivity_GeneratedInjector;
import im.vector.app.features.analytics.ui.consent.AnalyticsOptInActivity_GeneratedInjector;
import im.vector.app.features.analytics.ui.consent.AnalyticsOptInFragment_GeneratedInjector;
import im.vector.app.features.attachments.AttachmentTypeSelectorBottomSheet_GeneratedInjector;
import im.vector.app.features.attachments.preview.AttachmentsPreviewActivity_GeneratedInjector;
import im.vector.app.features.attachments.preview.AttachmentsPreviewFragment_GeneratedInjector;
import im.vector.app.features.auth.ReAuthActivity_GeneratedInjector;
import im.vector.app.features.call.CallControlsBottomSheet_GeneratedInjector;
import im.vector.app.features.call.VectorCallActivity_GeneratedInjector;
import im.vector.app.features.call.conference.VectorJitsiActivity_GeneratedInjector;
import im.vector.app.features.call.dialpad.CallDialPadBottomSheet_GeneratedInjector;
import im.vector.app.features.call.dialpad.PstnDialActivity_GeneratedInjector;
import im.vector.app.features.call.transfer.CallTransferActivity_GeneratedInjector;
import im.vector.app.features.call.webrtc.ScreenCaptureAndroidService_GeneratedInjector;
import im.vector.app.features.contactsbook.ContactsBookFragment_GeneratedInjector;
import im.vector.app.features.createdirect.CreateDirectRoomActivity_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreActivity_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromKeyFragment_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromPassphraseFragment_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreSuccessFragment_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupManageActivity_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupSettingsFragment_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupActivity_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupStep1Fragment_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupStep2Fragment_GeneratedInjector;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupStep3Fragment_GeneratedInjector;
import im.vector.app.features.crypto.quads.SharedSecureStorageActivity_GeneratedInjector;
import im.vector.app.features.crypto.quads.SharedSecuredStorageKeyFragment_GeneratedInjector;
import im.vector.app.features.crypto.quads.SharedSecuredStoragePassphraseFragment_GeneratedInjector;
import im.vector.app.features.crypto.quads.SharedSecuredStorageResetAllFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapBottomSheet_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapConclusionFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapConfirmPassphraseFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapEnterPassphraseFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapMigrateBackupFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapReAuthFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapSaveRecoveryKeyFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapSetupRecoveryKeyFragment_GeneratedInjector;
import im.vector.app.features.crypto.recover.BootstrapWaitingFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.QuadSLoadingFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.VerificationBottomSheet_GeneratedInjector;
import im.vector.app.features.crypto.verification.cancel.VerificationCancelFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.cancel.VerificationNotMeFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.choose.VerificationChooseMethodFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.conclusion.VerificationConclusionFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.emoji.VerificationEmojiCodeFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQRWaitingFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQrScannedByOtherFragment_GeneratedInjector;
import im.vector.app.features.crypto.verification.request.VerificationRequestFragment_GeneratedInjector;
import im.vector.app.features.debug.DebugMenuActivity_GeneratedInjector;
import im.vector.app.features.debug.DebugPermissionActivity_GeneratedInjector;
import im.vector.app.features.debug.analytics.DebugAnalyticsActivity_GeneratedInjector;
import im.vector.app.features.debug.di.DebugModule;
import im.vector.app.features.debug.di.FeaturesModule;
import im.vector.app.features.debug.di.MavericksViewModelDebugModule;
import im.vector.app.features.debug.features.DebugFeaturesSettingsActivity_GeneratedInjector;
import im.vector.app.features.debug.jitsi.DebugJitsiActivity_GeneratedInjector;
import im.vector.app.features.debug.leak.DebugMemoryLeaksActivity_GeneratedInjector;
import im.vector.app.features.debug.leak.DebugMemoryLeaksFragment_GeneratedInjector;
import im.vector.app.features.debug.settings.DebugPrivateSettingsActivity_GeneratedInjector;
import im.vector.app.features.devtools.RoomDevToolActivity_GeneratedInjector;
import im.vector.app.features.devtools.RoomDevToolEditFragment_GeneratedInjector;
import im.vector.app.features.devtools.RoomDevToolFragment_GeneratedInjector;
import im.vector.app.features.devtools.RoomDevToolSendFormFragment_GeneratedInjector;
import im.vector.app.features.devtools.RoomDevToolStateEventListFragment_GeneratedInjector;
import im.vector.app.features.discovery.DiscoverySettingsFragment_GeneratedInjector;
import im.vector.app.features.discovery.change.SetIdentityServerFragment_GeneratedInjector;
import im.vector.app.features.home.HomeActivity_GeneratedInjector;
import im.vector.app.features.home.HomeDetailFragment_GeneratedInjector;
import im.vector.app.features.home.HomeDrawerFragment_GeneratedInjector;
import im.vector.app.features.home.LoadingFragment_GeneratedInjector;
import im.vector.app.features.home.NewHomeDetailFragment_GeneratedInjector;
import im.vector.app.features.home.room.breadcrumbs.BreadcrumbsFragment_GeneratedInjector;
import im.vector.app.features.home.room.detail.JoinReplacementRoomBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.detail.RoomDetailActivity_GeneratedInjector;
import im.vector.app.features.home.room.detail.TimelineFragment_GeneratedInjector;
import im.vector.app.features.home.room.detail.composer.MessageComposerFragment_GeneratedInjector;
import im.vector.app.features.home.room.detail.composer.PlainTextComposerLayout_GeneratedInjector;
import im.vector.app.features.home.room.detail.composer.link.SetLinkFragment_GeneratedInjector;
import im.vector.app.features.home.room.detail.composer.voice.VoiceMessageRecorderView_GeneratedInjector;
import im.vector.app.features.home.room.detail.composer.voice.VoiceRecorderFragment_GeneratedInjector;
import im.vector.app.features.home.room.detail.readreceipts.DisplayReadReceiptsBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.detail.search.SearchActivity_GeneratedInjector;
import im.vector.app.features.home.room.detail.search.SearchFragment_GeneratedInjector;
import im.vector.app.features.home.room.detail.timeline.action.MessageActionsBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.detail.timeline.edithistory.ViewEditHistoryBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.detail.timeline.reactions.ViewReactionsBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.detail.upgrade.MigrateRoomBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.detail.widget.RoomWidgetsBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.filtered.FilteredRoomsActivity_GeneratedInjector;
import im.vector.app.features.home.room.list.RoomListFragment_GeneratedInjector;
import im.vector.app.features.home.room.list.actions.RoomListQuickActionsBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.list.home.HomeRoomListFragment_GeneratedInjector;
import im.vector.app.features.home.room.list.home.NewChatBottomSheet_GeneratedInjector;
import im.vector.app.features.home.room.list.home.invites.InvitesActivity_GeneratedInjector;
import im.vector.app.features.home.room.list.home.invites.InvitesFragment_GeneratedInjector;
import im.vector.app.features.home.room.list.home.layout.HomeLayoutSettingBottomDialogFragment_GeneratedInjector;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesActivity_GeneratedInjector;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesFragment_GeneratedInjector;
import im.vector.app.features.home.room.threads.ThreadsActivity_GeneratedInjector;
import im.vector.app.features.home.room.threads.list.views.ThreadListFragment_GeneratedInjector;
import im.vector.app.features.invite.InviteUsersToRoomActivity_GeneratedInjector;
import im.vector.app.features.invite.VectorInviteView_GeneratedInjector;
import im.vector.app.features.link.LinkHandlerActivity_GeneratedInjector;
import im.vector.app.features.location.LocationSharingActivity_GeneratedInjector;
import im.vector.app.features.location.LocationSharingFragment_GeneratedInjector;
import im.vector.app.features.location.live.duration.ChooseLiveDurationBottomSheet_GeneratedInjector;
import im.vector.app.features.location.live.map.LiveLocationMapViewActivity_GeneratedInjector;
import im.vector.app.features.location.live.map.LiveLocationMapViewFragment_GeneratedInjector;
import im.vector.app.features.location.live.tracking.LocationSharingAndroidService_GeneratedInjector;
import im.vector.app.features.location.preview.LocationPreviewFragment_GeneratedInjector;
import im.vector.app.features.login.LoginActivity_GeneratedInjector;
import im.vector.app.features.login.LoginCaptchaFragment_GeneratedInjector;
import im.vector.app.features.login.LoginFragment_GeneratedInjector;
import im.vector.app.features.login.LoginGenericTextInputFormFragment_GeneratedInjector;
import im.vector.app.features.login.LoginResetPasswordFragment_GeneratedInjector;
import im.vector.app.features.login.LoginResetPasswordMailConfirmationFragment_GeneratedInjector;
import im.vector.app.features.login.LoginResetPasswordSuccessFragment_GeneratedInjector;
import im.vector.app.features.login.LoginServerSelectionFragment_GeneratedInjector;
import im.vector.app.features.login.LoginServerUrlFormFragment_GeneratedInjector;
import im.vector.app.features.login.LoginSignUpSignInSelectionFragment_GeneratedInjector;
import im.vector.app.features.login.LoginSplashFragment_GeneratedInjector;
import im.vector.app.features.login.LoginWaitForEmailFragment_GeneratedInjector;
import im.vector.app.features.login.LoginWebFragment_GeneratedInjector;
import im.vector.app.features.login.SSORedirectRouterActivity_GeneratedInjector;
import im.vector.app.features.login.qr.QrCodeLoginActivity_GeneratedInjector;
import im.vector.app.features.login.qr.QrCodeLoginInstructionsFragment_GeneratedInjector;
import im.vector.app.features.login.qr.QrCodeLoginShowQrCodeFragment_GeneratedInjector;
import im.vector.app.features.login.qr.QrCodeLoginStatusFragment_GeneratedInjector;
import im.vector.app.features.matrixto.MatrixToBottomSheet_GeneratedInjector;
import im.vector.app.features.matrixto.MatrixToRoomSpaceFragment_GeneratedInjector;
import im.vector.app.features.matrixto.MatrixToUserFragment_GeneratedInjector;
import im.vector.app.features.media.BigImageViewerActivity_GeneratedInjector;
import im.vector.app.features.media.VectorAttachmentViewerActivity_GeneratedInjector;
import im.vector.app.features.notifications.NotificationBroadcastReceiver_GeneratedInjector;
import im.vector.app.features.onboarding.OnboardingActivity_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthAccountCreatedFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCaptchaFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthChooseDisplayNameFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthChooseProfilePictureFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCombinedLoginFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCombinedRegisterFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCombinedServerSelectionFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthEmailEntryFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthGenericTextInputFormFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthLegacyStyleCaptchaFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthLegacyWaitForEmailFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthLoginFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthPersonalizationCompleteFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthPhoneConfirmationFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthPhoneEntryFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordBreakerFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordEmailEntryFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordEntryFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordMailConfirmationFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordSuccessFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthServerSelectionFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthServerUrlFormFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSignUpSignInSelectionFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSplashCarouselFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSplashFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthUseCaseFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthWaitForEmailFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthWebFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.terms.FtueAuthLegacyStyleTermsFragment_GeneratedInjector;
import im.vector.app.features.onboarding.ftueauth.terms.FtueAuthTermsFragment_GeneratedInjector;
import im.vector.app.features.pin.PinActivity_GeneratedInjector;
import im.vector.app.features.pin.PinFragment_GeneratedInjector;
import im.vector.app.features.pin.lockscreen.di.LockScreenBindsModule;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule;
import im.vector.app.features.pin.lockscreen.ui.LockScreenFragment_GeneratedInjector;
import im.vector.app.features.poll.create.CreatePollActivity_GeneratedInjector;
import im.vector.app.features.poll.create.CreatePollFragment_GeneratedInjector;
import im.vector.app.features.qrcode.QrCodeScannerActivity_GeneratedInjector;
import im.vector.app.features.qrcode.QrCodeScannerFragment_GeneratedInjector;
import im.vector.app.features.rageshake.BugReportActivity_GeneratedInjector;
import im.vector.app.features.reactions.EmojiChooserFragment_GeneratedInjector;
import im.vector.app.features.reactions.EmojiReactionPickerActivity_GeneratedInjector;
import im.vector.app.features.reactions.EmojiSearchResultFragment_GeneratedInjector;
import im.vector.app.features.reactions.widget.ReactionButton_GeneratedInjector;
import im.vector.app.features.roomdirectory.PublicRoomsFragment_GeneratedInjector;
import im.vector.app.features.roomdirectory.RoomDirectoryActivity_GeneratedInjector;
import im.vector.app.features.roomdirectory.createroom.CreateRoomActivity_GeneratedInjector;
import im.vector.app.features.roomdirectory.createroom.CreateRoomFragment_GeneratedInjector;
import im.vector.app.features.roomdirectory.picker.RoomDirectoryPickerFragment_GeneratedInjector;
import im.vector.app.features.roomdirectory.roompreview.RoomPreviewActivity_GeneratedInjector;
import im.vector.app.features.roomdirectory.roompreview.RoomPreviewNoPreviewFragment_GeneratedInjector;
import im.vector.app.features.roommemberprofile.RoomMemberProfileActivity_GeneratedInjector;
import im.vector.app.features.roommemberprofile.RoomMemberProfileFragment_GeneratedInjector;
import im.vector.app.features.roommemberprofile.devices.DeviceListBottomSheet_GeneratedInjector;
import im.vector.app.features.roommemberprofile.devices.DeviceListFragment_GeneratedInjector;
import im.vector.app.features.roommemberprofile.devices.DeviceTrustInfoActionFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.RoomProfileActivity_GeneratedInjector;
import im.vector.app.features.roomprofile.RoomProfileFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.alias.RoomAliasFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheet_GeneratedInjector;
import im.vector.app.features.roomprofile.banned.RoomBannedMemberListFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.members.RoomMemberListFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.permissions.RoomPermissionsFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.polls.RoomPollsFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.polls.active.RoomActivePollsFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.polls.ended.RoomEndedPollsFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.settings.RoomSettingsFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.settings.historyvisibility.RoomHistoryVisibilityBottomSheet_GeneratedInjector;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleActivity_GeneratedInjector;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleBottomSheet_GeneratedInjector;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.uploads.RoomUploadsFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.uploads.files.RoomUploadsFilesFragment_GeneratedInjector;
import im.vector.app.features.roomprofile.uploads.media.RoomUploadsMediaFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsActivity_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsAdvancedSettingsFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsGeneralFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsHelpAboutFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsPinFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsPreferencesFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsRootFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsSecurityPrivacyFragment_GeneratedInjector;
import im.vector.app.features.settings.VectorSettingsVoiceVideoFragment_GeneratedInjector;
import im.vector.app.features.settings.account.deactivation.DeactivateAccountFragment_GeneratedInjector;
import im.vector.app.features.settings.crosssigning.CrossSigningSettingsFragment_GeneratedInjector;
import im.vector.app.features.settings.devices.DeviceVerificationInfoBottomSheet_GeneratedInjector;
import im.vector.app.features.settings.devices.VectorSettingsDevicesFragment_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsActivity_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsFragment_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.filter.DeviceManagerFilterBottomSheet_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.list.OtherSessionsView_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.more.SessionLearnMoreBottomSheet_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsActivity_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsFragment_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsSecurityRecommendationView_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewActivity_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewFragment_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionActivity_GeneratedInjector;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionFragment_GeneratedInjector;
import im.vector.app.features.settings.devtools.AccountDataFragment_GeneratedInjector;
import im.vector.app.features.settings.devtools.GossipingEventsPaperTrailFragment_GeneratedInjector;
import im.vector.app.features.settings.devtools.IncomingKeyRequestListFragment_GeneratedInjector;
import im.vector.app.features.settings.devtools.KeyRequestsFragment_GeneratedInjector;
import im.vector.app.features.settings.devtools.OutgoingKeyRequestListFragment_GeneratedInjector;
import im.vector.app.features.settings.font.FontScaleSettingActivity_GeneratedInjector;
import im.vector.app.features.settings.font.FontScaleSettingFragment_GeneratedInjector;
import im.vector.app.features.settings.homeserver.HomeserverSettingsFragment_GeneratedInjector;
import im.vector.app.features.settings.ignored.VectorSettingsIgnoredUsersFragment_GeneratedInjector;
import im.vector.app.features.settings.labs.VectorSettingsLabsFragment_GeneratedInjector;
import im.vector.app.features.settings.legals.LegalsFragment_GeneratedInjector;
import im.vector.app.features.settings.locale.LocalePickerFragment_GeneratedInjector;
import im.vector.app.features.settings.notifications.VectorSettingsAdvancedNotificationPreferenceFragment_GeneratedInjector;
import im.vector.app.features.settings.notifications.VectorSettingsNotificationPreferenceFragment_GeneratedInjector;
import im.vector.app.features.settings.notifications.VectorSettingsNotificationsTroubleshootFragment_GeneratedInjector;
import im.vector.app.features.settings.push.PushGatewaysFragment_GeneratedInjector;
import im.vector.app.features.settings.push.PushRulesFragment_GeneratedInjector;
import im.vector.app.features.settings.threepids.ThreePidsSettingsFragment_GeneratedInjector;
import im.vector.app.features.share.IncomingShareActivity_GeneratedInjector;
import im.vector.app.features.share.IncomingShareFragment_GeneratedInjector;
import im.vector.app.features.signout.hard.SignedOutActivity_GeneratedInjector;
import im.vector.app.features.signout.soft.SoftLogoutActivity_GeneratedInjector;
import im.vector.app.features.signout.soft.SoftLogoutFragment_GeneratedInjector;
import im.vector.app.features.spaces.InviteRoomSpaceChooserBottomSheet_GeneratedInjector;
import im.vector.app.features.spaces.SpaceCreationActivity_GeneratedInjector;
import im.vector.app.features.spaces.SpaceExploreActivity_GeneratedInjector;
import im.vector.app.features.spaces.SpaceListFragment_GeneratedInjector;
import im.vector.app.features.spaces.SpacePreviewActivity_GeneratedInjector;
import im.vector.app.features.spaces.SpaceSettingsMenuBottomSheet_GeneratedInjector;
import im.vector.app.features.spaces.create.ChoosePrivateSpaceTypeFragment_GeneratedInjector;
import im.vector.app.features.spaces.create.ChooseSpaceTypeFragment_GeneratedInjector;
import im.vector.app.features.spaces.create.CreateSpaceAdd3pidInvitesFragment_GeneratedInjector;
import im.vector.app.features.spaces.create.CreateSpaceDefaultRoomsFragment_GeneratedInjector;
import im.vector.app.features.spaces.create.CreateSpaceDetailsFragment_GeneratedInjector;
import im.vector.app.features.spaces.explore.SpaceDirectoryFragment_GeneratedInjector;
import im.vector.app.features.spaces.invite.SpaceInviteBottomSheet_GeneratedInjector;
import im.vector.app.features.spaces.leave.SpaceLeaveAdvancedActivity_GeneratedInjector;
import im.vector.app.features.spaces.leave.SpaceLeaveAdvancedFragment_GeneratedInjector;
import im.vector.app.features.spaces.manage.SpaceAddRoomFragment_GeneratedInjector;
import im.vector.app.features.spaces.manage.SpaceManageActivity_GeneratedInjector;
import im.vector.app.features.spaces.manage.SpaceManageRoomsFragment_GeneratedInjector;
import im.vector.app.features.spaces.manage.SpaceSettingsFragment_GeneratedInjector;
import im.vector.app.features.spaces.people.SpacePeopleActivity_GeneratedInjector;
import im.vector.app.features.spaces.people.SpacePeopleFragment_GeneratedInjector;
import im.vector.app.features.spaces.preview.SpacePreviewFragment_GeneratedInjector;
import im.vector.app.features.spaces.share.ShareSpaceBottomSheet_GeneratedInjector;
import im.vector.app.features.start.StartAppAndroidService_GeneratedInjector;
import im.vector.app.features.terms.ReviewTermsActivity_GeneratedInjector;
import im.vector.app.features.terms.ReviewTermsFragment_GeneratedInjector;
import im.vector.app.features.usercode.ShowUserCodeFragment_GeneratedInjector;
import im.vector.app.features.usercode.UserCodeActivity_GeneratedInjector;
import im.vector.app.features.userdirectory.UserListFragment_GeneratedInjector;
import im.vector.app.features.webview.VectorWebViewActivity_GeneratedInjector;
import im.vector.app.features.widgets.WidgetActivity_GeneratedInjector;
import im.vector.app.features.widgets.WidgetFragment_GeneratedInjector;
import im.vector.app.features.widgets.permissions.RoomWidgetPermissionBottomSheet_GeneratedInjector;
import im.vector.app.features.workers.signout.SignOutBottomSheetDialogFragment_GeneratedInjector;
import im.vector.app.push.fcm.VectorFirebaseMessagingService_GeneratedInjector;
import javax.annotation.processing.Generated;
import javax.inject.Singleton;

@Generated("dagger.hilt.processor.internal.root.RootProcessor")
public final class VectorApplication_HiltComponents {
  private VectorApplication_HiltComponents() {
  }

  @Module(
      subcomponents = ServiceC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ServiceCBuilderModule {
    @Binds
    ServiceComponentBuilder bind(ServiceC.Builder builder);
  }

  @Module(
      subcomponents = ActivityRetainedC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityRetainedCBuilderModule {
    @Binds
    ActivityRetainedComponentBuilder bind(ActivityRetainedC.Builder builder);
  }

  @Module(
      subcomponents = MavericksViewModelC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface MavericksViewModelCBuilderModule {
    @Binds
    MavericksViewModelComponentBuilder bind(MavericksViewModelC.Builder builder);
  }

  @Module(
      subcomponents = ActivityC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ActivityCBuilderModule {
    @Binds
    ActivityComponentBuilder bind(ActivityC.Builder builder);
  }

  @Module(
      subcomponents = ViewModelC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewModelCBuilderModule {
    @Binds
    ViewModelComponentBuilder bind(ViewModelC.Builder builder);
  }

  @Module(
      subcomponents = ViewC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewCBuilderModule {
    @Binds
    ViewComponentBuilder bind(ViewC.Builder builder);
  }

  @Module(
      subcomponents = FragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface FragmentCBuilderModule {
    @Binds
    FragmentComponentBuilder bind(FragmentC.Builder builder);
  }

  @Module(
      subcomponents = ViewWithFragmentC.class
  )
  @DisableInstallInCheck
  @Generated("dagger.hilt.processor.internal.root.RootProcessor")
  abstract interface ViewWithFragmentCBuilderModule {
    @Binds
    ViewWithFragmentComponentBuilder bind(ViewWithFragmentC.Builder builder);
  }

  @Component(
      modules = {
          ApplicationContextModule.class,
          ConfigurationModule.class,
          DebugModule.class,
          FeaturesModule.class,
          FlavorModule.class,
          HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule.class,
          LockScreenBindsModule.class,
          LockScreenModule.class,
          LoginModule.class,
          ActivityRetainedCBuilderModule.class,
          MavericksViewModelCBuilderModule.class,
          ServiceCBuilderModule.class,
          VectorBindModule.class,
          VectorStaticModule.class,
          VoiceModule.class
      }
  )
  @Singleton
  public abstract static class SingletonC implements FragmentGetContextFix.FragmentGetContextFixEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedComponentBuilderEntryPoint,
      ServiceComponentManager.ServiceComponentBuilderEntryPoint,
      SingletonComponent,
      GeneratedComponent,
      VectorApplication_GeneratedInjector,
      CreateMavericksViewModelComponent,
      SingletonEntryPoint,
      VectorUnifiedPushMessagingReceiver_GeneratedInjector,
      NotificationBroadcastReceiver_GeneratedInjector {
  }

  @Subcomponent
  @ServiceScoped
  public abstract static class ServiceC implements ServiceComponent,
      GeneratedComponent,
      CallAndroidService_GeneratedInjector,
      VectorSyncAndroidService_GeneratedInjector,
      ScreenCaptureAndroidService_GeneratedInjector,
      LocationSharingAndroidService_GeneratedInjector,
      StartAppAndroidService_GeneratedInjector,
      VectorFirebaseMessagingService_GeneratedInjector {
    @Subcomponent.Builder
    abstract interface Builder extends ServiceComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          HiltWrapper_ActivityRetainedComponentManager_LifecycleModule.class,
          ActivityCBuilderModule.class,
          ViewModelCBuilderModule.class
      }
  )
  @ActivityRetainedScoped
  public abstract static class ActivityRetainedC implements ActivityRetainedComponent,
      ActivityComponentManager.ActivityComponentBuilderEntryPoint,
      HiltWrapper_ActivityRetainedComponentManager_ActivityRetainedLifecycleEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityRetainedComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          MavericksViewModelDebugModule.class,
          MavericksViewModelModule.class
      }
  )
  @MavericksViewModelScoped
  public abstract static class MavericksViewModelC implements GeneratedComponent,
      HiltMavericksEntryPoint,
      MavericksViewModelComponent {
    @Subcomponent.Builder
    abstract interface Builder extends MavericksViewModelComponentBuilder {
    }
  }

  @Subcomponent(
      modules = {
          HiltWrapper_ActivityModule.class,
          HiltWrapper_DefaultViewModelFactories_ActivityModule.class,
          HomeModule.class,
          NotificationTestModule.class,
          ScreenModule.class,
          FragmentCBuilderModule.class,
          ViewCBuilderModule.class,
          ViewModelModule.class
      }
  )
  @ActivityScoped
  public abstract static class ActivityC implements ActivityComponent,
      DefaultViewModelFactories.ActivityEntryPoint,
      HiltWrapper_HiltViewModelFactory_ActivityCreatorEntryPoint,
      FragmentComponentManager.FragmentComponentBuilderEntryPoint,
      ViewComponentManager.ViewComponentBuilderEntryPoint,
      GeneratedComponent,
      ActivityEntryPoint,
      MainActivity_GeneratedInjector,
      AnalyticsOptInActivity_GeneratedInjector,
      AttachmentsPreviewActivity_GeneratedInjector,
      ReAuthActivity_GeneratedInjector,
      VectorCallActivity_GeneratedInjector,
      VectorJitsiActivity_GeneratedInjector,
      PstnDialActivity_GeneratedInjector,
      CallTransferActivity_GeneratedInjector,
      CreateDirectRoomActivity_GeneratedInjector,
      KeysBackupRestoreActivity_GeneratedInjector,
      KeysBackupManageActivity_GeneratedInjector,
      KeysBackupSetupActivity_GeneratedInjector,
      SharedSecureStorageActivity_GeneratedInjector,
      DebugMenuActivity_GeneratedInjector,
      DebugPermissionActivity_GeneratedInjector,
      DebugAnalyticsActivity_GeneratedInjector,
      DebugFeaturesSettingsActivity_GeneratedInjector,
      DebugJitsiActivity_GeneratedInjector,
      DebugMemoryLeaksActivity_GeneratedInjector,
      DebugPrivateSettingsActivity_GeneratedInjector,
      RoomDevToolActivity_GeneratedInjector,
      HomeActivity_GeneratedInjector,
      RoomDetailActivity_GeneratedInjector,
      SearchActivity_GeneratedInjector,
      FilteredRoomsActivity_GeneratedInjector,
      InvitesActivity_GeneratedInjector,
      ReleaseNotesActivity_GeneratedInjector,
      ThreadsActivity_GeneratedInjector,
      InviteUsersToRoomActivity_GeneratedInjector,
      LinkHandlerActivity_GeneratedInjector,
      LocationSharingActivity_GeneratedInjector,
      LiveLocationMapViewActivity_GeneratedInjector,
      LoginActivity_GeneratedInjector,
      SSORedirectRouterActivity_GeneratedInjector,
      QrCodeLoginActivity_GeneratedInjector,
      BigImageViewerActivity_GeneratedInjector,
      VectorAttachmentViewerActivity_GeneratedInjector,
      OnboardingActivity_GeneratedInjector,
      PinActivity_GeneratedInjector,
      CreatePollActivity_GeneratedInjector,
      QrCodeScannerActivity_GeneratedInjector,
      BugReportActivity_GeneratedInjector,
      EmojiReactionPickerActivity_GeneratedInjector,
      RoomDirectoryActivity_GeneratedInjector,
      CreateRoomActivity_GeneratedInjector,
      RoomPreviewActivity_GeneratedInjector,
      RoomMemberProfileActivity_GeneratedInjector,
      RoomProfileActivity_GeneratedInjector,
      RoomJoinRuleActivity_GeneratedInjector,
      VectorSettingsActivity_GeneratedInjector,
      SessionDetailsActivity_GeneratedInjector,
      OtherSessionsActivity_GeneratedInjector,
      SessionOverviewActivity_GeneratedInjector,
      RenameSessionActivity_GeneratedInjector,
      FontScaleSettingActivity_GeneratedInjector,
      IncomingShareActivity_GeneratedInjector,
      SignedOutActivity_GeneratedInjector,
      SoftLogoutActivity_GeneratedInjector,
      SpaceCreationActivity_GeneratedInjector,
      SpaceExploreActivity_GeneratedInjector,
      SpacePreviewActivity_GeneratedInjector,
      SpaceLeaveAdvancedActivity_GeneratedInjector,
      SpaceManageActivity_GeneratedInjector,
      SpacePeopleActivity_GeneratedInjector,
      ReviewTermsActivity_GeneratedInjector,
      UserCodeActivity_GeneratedInjector,
      VectorWebViewActivity_GeneratedInjector,
      WidgetActivity_GeneratedInjector {
    @Subcomponent.Builder
    abstract interface Builder extends ActivityComponentBuilder {
    }
  }

  @Subcomponent(
      modules = HiltWrapper_HiltViewModelFactory_ViewModelModule.class
  )
  @ViewModelScoped
  public abstract static class ViewModelC implements ViewModelComponent,
      HiltViewModelFactory.ViewModelFactoriesEntryPoint,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewModelComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewC implements ViewComponent,
      GeneratedComponent,
      TypingMessageView_GeneratedInjector,
      PlainTextComposerLayout_GeneratedInjector,
      VoiceMessageRecorderView_GeneratedInjector,
      VectorInviteView_GeneratedInjector,
      ReactionButton_GeneratedInjector,
      OtherSessionsView_GeneratedInjector,
      OtherSessionsSecurityRecommendationView_GeneratedInjector {
    @Subcomponent.Builder
    abstract interface Builder extends ViewComponentBuilder {
    }
  }

  @Subcomponent(
      modules = ViewWithFragmentCBuilderModule.class
  )
  @FragmentScoped
  public abstract static class FragmentC implements FragmentComponent,
      DefaultViewModelFactories.FragmentEntryPoint,
      ViewComponentManager.ViewWithFragmentComponentBuilderEntryPoint,
      GeneratedComponent,
      AnalyticsOptInFragment_GeneratedInjector,
      AttachmentTypeSelectorBottomSheet_GeneratedInjector,
      AttachmentsPreviewFragment_GeneratedInjector,
      CallControlsBottomSheet_GeneratedInjector,
      CallDialPadBottomSheet_GeneratedInjector,
      ContactsBookFragment_GeneratedInjector,
      KeysBackupRestoreFromKeyFragment_GeneratedInjector,
      KeysBackupRestoreFromPassphraseFragment_GeneratedInjector,
      KeysBackupRestoreSuccessFragment_GeneratedInjector,
      KeysBackupSettingsFragment_GeneratedInjector,
      KeysBackupSetupStep1Fragment_GeneratedInjector,
      KeysBackupSetupStep2Fragment_GeneratedInjector,
      KeysBackupSetupStep3Fragment_GeneratedInjector,
      SharedSecuredStorageKeyFragment_GeneratedInjector,
      SharedSecuredStoragePassphraseFragment_GeneratedInjector,
      SharedSecuredStorageResetAllFragment_GeneratedInjector,
      BootstrapBottomSheet_GeneratedInjector,
      BootstrapConclusionFragment_GeneratedInjector,
      BootstrapConfirmPassphraseFragment_GeneratedInjector,
      BootstrapEnterPassphraseFragment_GeneratedInjector,
      BootstrapMigrateBackupFragment_GeneratedInjector,
      BootstrapReAuthFragment_GeneratedInjector,
      BootstrapSaveRecoveryKeyFragment_GeneratedInjector,
      BootstrapSetupRecoveryKeyFragment_GeneratedInjector,
      BootstrapWaitingFragment_GeneratedInjector,
      QuadSLoadingFragment_GeneratedInjector,
      VerificationBottomSheet_GeneratedInjector,
      VerificationCancelFragment_GeneratedInjector,
      VerificationNotMeFragment_GeneratedInjector,
      VerificationChooseMethodFragment_GeneratedInjector,
      VerificationConclusionFragment_GeneratedInjector,
      VerificationEmojiCodeFragment_GeneratedInjector,
      VerificationQRWaitingFragment_GeneratedInjector,
      VerificationQrScannedByOtherFragment_GeneratedInjector,
      VerificationRequestFragment_GeneratedInjector,
      DebugMemoryLeaksFragment_GeneratedInjector,
      RoomDevToolEditFragment_GeneratedInjector,
      RoomDevToolFragment_GeneratedInjector,
      RoomDevToolSendFormFragment_GeneratedInjector,
      RoomDevToolStateEventListFragment_GeneratedInjector,
      DiscoverySettingsFragment_GeneratedInjector,
      SetIdentityServerFragment_GeneratedInjector,
      HomeDetailFragment_GeneratedInjector,
      HomeDrawerFragment_GeneratedInjector,
      LoadingFragment_GeneratedInjector,
      NewHomeDetailFragment_GeneratedInjector,
      BreadcrumbsFragment_GeneratedInjector,
      JoinReplacementRoomBottomSheet_GeneratedInjector,
      TimelineFragment_GeneratedInjector,
      MessageComposerFragment_GeneratedInjector,
      SetLinkFragment_GeneratedInjector,
      VoiceRecorderFragment_GeneratedInjector,
      DisplayReadReceiptsBottomSheet_GeneratedInjector,
      SearchFragment_GeneratedInjector,
      MessageActionsBottomSheet_GeneratedInjector,
      ViewEditHistoryBottomSheet_GeneratedInjector,
      ViewReactionsBottomSheet_GeneratedInjector,
      MigrateRoomBottomSheet_GeneratedInjector,
      RoomWidgetsBottomSheet_GeneratedInjector,
      RoomListFragment_GeneratedInjector,
      RoomListQuickActionsBottomSheet_GeneratedInjector,
      HomeRoomListFragment_GeneratedInjector,
      NewChatBottomSheet_GeneratedInjector,
      InvitesFragment_GeneratedInjector,
      HomeLayoutSettingBottomDialogFragment_GeneratedInjector,
      ReleaseNotesFragment_GeneratedInjector,
      ThreadListFragment_GeneratedInjector,
      LocationSharingFragment_GeneratedInjector,
      ChooseLiveDurationBottomSheet_GeneratedInjector,
      LiveLocationMapViewFragment_GeneratedInjector,
      LocationPreviewFragment_GeneratedInjector,
      LoginCaptchaFragment_GeneratedInjector,
      LoginFragment_GeneratedInjector,
      LoginGenericTextInputFormFragment_GeneratedInjector,
      LoginResetPasswordFragment_GeneratedInjector,
      LoginResetPasswordMailConfirmationFragment_GeneratedInjector,
      LoginResetPasswordSuccessFragment_GeneratedInjector,
      LoginServerSelectionFragment_GeneratedInjector,
      LoginServerUrlFormFragment_GeneratedInjector,
      LoginSignUpSignInSelectionFragment_GeneratedInjector,
      LoginSplashFragment_GeneratedInjector,
      LoginWaitForEmailFragment_GeneratedInjector,
      LoginWebFragment_GeneratedInjector,
      QrCodeLoginInstructionsFragment_GeneratedInjector,
      QrCodeLoginShowQrCodeFragment_GeneratedInjector,
      QrCodeLoginStatusFragment_GeneratedInjector,
      MatrixToBottomSheet_GeneratedInjector,
      MatrixToRoomSpaceFragment_GeneratedInjector,
      MatrixToUserFragment_GeneratedInjector,
      FtueAuthAccountCreatedFragment_GeneratedInjector,
      FtueAuthCaptchaFragment_GeneratedInjector,
      FtueAuthChooseDisplayNameFragment_GeneratedInjector,
      FtueAuthChooseProfilePictureFragment_GeneratedInjector,
      FtueAuthCombinedLoginFragment_GeneratedInjector,
      FtueAuthCombinedRegisterFragment_GeneratedInjector,
      FtueAuthCombinedServerSelectionFragment_GeneratedInjector,
      FtueAuthEmailEntryFragment_GeneratedInjector,
      FtueAuthGenericTextInputFormFragment_GeneratedInjector,
      FtueAuthLegacyStyleCaptchaFragment_GeneratedInjector,
      FtueAuthLegacyWaitForEmailFragment_GeneratedInjector,
      FtueAuthLoginFragment_GeneratedInjector,
      FtueAuthPersonalizationCompleteFragment_GeneratedInjector,
      FtueAuthPhoneConfirmationFragment_GeneratedInjector,
      FtueAuthPhoneEntryFragment_GeneratedInjector,
      FtueAuthResetPasswordBreakerFragment_GeneratedInjector,
      FtueAuthResetPasswordEmailEntryFragment_GeneratedInjector,
      FtueAuthResetPasswordEntryFragment_GeneratedInjector,
      FtueAuthResetPasswordFragment_GeneratedInjector,
      FtueAuthResetPasswordMailConfirmationFragment_GeneratedInjector,
      FtueAuthResetPasswordSuccessFragment_GeneratedInjector,
      FtueAuthServerSelectionFragment_GeneratedInjector,
      FtueAuthServerUrlFormFragment_GeneratedInjector,
      FtueAuthSignUpSignInSelectionFragment_GeneratedInjector,
      FtueAuthSplashCarouselFragment_GeneratedInjector,
      FtueAuthSplashFragment_GeneratedInjector,
      FtueAuthUseCaseFragment_GeneratedInjector,
      FtueAuthWaitForEmailFragment_GeneratedInjector,
      FtueAuthWebFragment_GeneratedInjector,
      FtueAuthLegacyStyleTermsFragment_GeneratedInjector,
      FtueAuthTermsFragment_GeneratedInjector,
      PinFragment_GeneratedInjector,
      LockScreenFragment_GeneratedInjector,
      CreatePollFragment_GeneratedInjector,
      QrCodeScannerFragment_GeneratedInjector,
      EmojiChooserFragment_GeneratedInjector,
      EmojiSearchResultFragment_GeneratedInjector,
      PublicRoomsFragment_GeneratedInjector,
      CreateRoomFragment_GeneratedInjector,
      RoomDirectoryPickerFragment_GeneratedInjector,
      RoomPreviewNoPreviewFragment_GeneratedInjector,
      RoomMemberProfileFragment_GeneratedInjector,
      DeviceListBottomSheet_GeneratedInjector,
      DeviceListFragment_GeneratedInjector,
      DeviceTrustInfoActionFragment_GeneratedInjector,
      RoomProfileFragment_GeneratedInjector,
      RoomAliasFragment_GeneratedInjector,
      RoomAliasBottomSheet_GeneratedInjector,
      RoomBannedMemberListFragment_GeneratedInjector,
      RoomMemberListFragment_GeneratedInjector,
      RoomNotificationSettingsFragment_GeneratedInjector,
      RoomPermissionsFragment_GeneratedInjector,
      RoomPollsFragment_GeneratedInjector,
      RoomActivePollsFragment_GeneratedInjector,
      RoomEndedPollsFragment_GeneratedInjector,
      RoomSettingsFragment_GeneratedInjector,
      RoomHistoryVisibilityBottomSheet_GeneratedInjector,
      RoomJoinRuleBottomSheet_GeneratedInjector,
      RoomJoinRuleFragment_GeneratedInjector,
      RoomJoinRuleChooseRestrictedFragment_GeneratedInjector,
      RoomUploadsFragment_GeneratedInjector,
      RoomUploadsFilesFragment_GeneratedInjector,
      RoomUploadsMediaFragment_GeneratedInjector,
      VectorSettingsAdvancedSettingsFragment_GeneratedInjector,
      VectorSettingsGeneralFragment_GeneratedInjector,
      VectorSettingsHelpAboutFragment_GeneratedInjector,
      VectorSettingsPinFragment_GeneratedInjector,
      VectorSettingsPreferencesFragment_GeneratedInjector,
      VectorSettingsRootFragment_GeneratedInjector,
      VectorSettingsSecurityPrivacyFragment_GeneratedInjector,
      VectorSettingsVoiceVideoFragment_GeneratedInjector,
      DeactivateAccountFragment_GeneratedInjector,
      CrossSigningSettingsFragment_GeneratedInjector,
      DeviceVerificationInfoBottomSheet_GeneratedInjector,
      VectorSettingsDevicesFragment_GeneratedInjector,
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_GeneratedInjector,
      SessionDetailsFragment_GeneratedInjector,
      DeviceManagerFilterBottomSheet_GeneratedInjector,
      SessionLearnMoreBottomSheet_GeneratedInjector,
      OtherSessionsFragment_GeneratedInjector,
      SessionOverviewFragment_GeneratedInjector,
      RenameSessionFragment_GeneratedInjector,
      AccountDataFragment_GeneratedInjector,
      GossipingEventsPaperTrailFragment_GeneratedInjector,
      IncomingKeyRequestListFragment_GeneratedInjector,
      KeyRequestsFragment_GeneratedInjector,
      OutgoingKeyRequestListFragment_GeneratedInjector,
      FontScaleSettingFragment_GeneratedInjector,
      HomeserverSettingsFragment_GeneratedInjector,
      VectorSettingsIgnoredUsersFragment_GeneratedInjector,
      VectorSettingsLabsFragment_GeneratedInjector,
      LegalsFragment_GeneratedInjector,
      LocalePickerFragment_GeneratedInjector,
      VectorSettingsAdvancedNotificationPreferenceFragment_GeneratedInjector,
      VectorSettingsNotificationPreferenceFragment_GeneratedInjector,
      VectorSettingsNotificationsTroubleshootFragment_GeneratedInjector,
      PushGatewaysFragment_GeneratedInjector,
      PushRulesFragment_GeneratedInjector,
      ThreePidsSettingsFragment_GeneratedInjector,
      IncomingShareFragment_GeneratedInjector,
      SoftLogoutFragment_GeneratedInjector,
      InviteRoomSpaceChooserBottomSheet_GeneratedInjector,
      SpaceListFragment_GeneratedInjector,
      SpaceSettingsMenuBottomSheet_GeneratedInjector,
      ChoosePrivateSpaceTypeFragment_GeneratedInjector,
      ChooseSpaceTypeFragment_GeneratedInjector,
      CreateSpaceAdd3pidInvitesFragment_GeneratedInjector,
      CreateSpaceDefaultRoomsFragment_GeneratedInjector,
      CreateSpaceDetailsFragment_GeneratedInjector,
      SpaceDirectoryFragment_GeneratedInjector,
      SpaceInviteBottomSheet_GeneratedInjector,
      SpaceLeaveAdvancedFragment_GeneratedInjector,
      SpaceAddRoomFragment_GeneratedInjector,
      SpaceManageRoomsFragment_GeneratedInjector,
      SpaceSettingsFragment_GeneratedInjector,
      SpacePeopleFragment_GeneratedInjector,
      SpacePreviewFragment_GeneratedInjector,
      ShareSpaceBottomSheet_GeneratedInjector,
      ReviewTermsFragment_GeneratedInjector,
      ShowUserCodeFragment_GeneratedInjector,
      UserListFragment_GeneratedInjector,
      WidgetFragment_GeneratedInjector,
      RoomWidgetPermissionBottomSheet_GeneratedInjector,
      SignOutBottomSheetDialogFragment_GeneratedInjector {
    @Subcomponent.Builder
    abstract interface Builder extends FragmentComponentBuilder {
    }
  }

  @Subcomponent
  @ViewScoped
  public abstract static class ViewWithFragmentC implements ViewWithFragmentComponent,
      GeneratedComponent {
    @Subcomponent.Builder
    abstract interface Builder extends ViewWithFragmentComponentBuilder {
    }
  }
}
