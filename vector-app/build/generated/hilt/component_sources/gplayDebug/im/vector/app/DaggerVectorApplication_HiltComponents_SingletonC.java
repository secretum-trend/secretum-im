package im.vector.app;

import android.app.Activity;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.view.View;
import androidx.biometric.BiometricManager;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.mvrx.MavericksViewModel;
import com.apollographql.apollo3.ApolloClient;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.errorprone.annotations.CanIgnoreReturnValue;
import dagger.hilt.android.ActivityRetainedLifecycle;
import dagger.hilt.android.ViewModelLifecycle;
import dagger.hilt.android.flags.HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule;
import dagger.hilt.android.internal.builders.ActivityComponentBuilder;
import dagger.hilt.android.internal.builders.ActivityRetainedComponentBuilder;
import dagger.hilt.android.internal.builders.FragmentComponentBuilder;
import dagger.hilt.android.internal.builders.ServiceComponentBuilder;
import dagger.hilt.android.internal.builders.ViewComponentBuilder;
import dagger.hilt.android.internal.builders.ViewModelComponentBuilder;
import dagger.hilt.android.internal.builders.ViewWithFragmentComponentBuilder;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories;
import dagger.hilt.android.internal.lifecycle.DefaultViewModelFactories_InternalFactoryFactory_Factory;
import dagger.hilt.android.internal.managers.ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory;
import dagger.hilt.android.internal.modules.ActivityModule_ProvideFragmentActivityFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideApplicationFactory;
import dagger.hilt.android.internal.modules.ApplicationContextModule_ProvideContextFactory;
import dagger.internal.DaggerGenerated;
import dagger.internal.DoubleCheck;
import dagger.internal.Preconditions;
import dagger.internal.SingleCheck;
import im.vector.app.core.contacts.ContactsDataSource;
import im.vector.app.core.date.AbbrevDateFormatterProvider;
import im.vector.app.core.date.DateFormatterProviders;
import im.vector.app.core.date.DefaultDateFormatterProvider;
import im.vector.app.core.date.VectorDateFormatter;
import im.vector.app.core.device.DefaultGetDeviceInfoUseCase;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.di.ConfigurationModule;
import im.vector.app.core.di.ConfigurationModule_ProvidesAnalyticsConfigFactory;
import im.vector.app.core.di.ConfigurationModule_ProvidesCryptoConfigFactory;
import im.vector.app.core.di.ConfigurationModule_ProvidesLocationSharingConfigFactory;
import im.vector.app.core.di.ConfigurationModule_ProvidesVoiceMessageConfigFactory;
import im.vector.app.core.di.ConfigurationModule_ProvidesVoipConfigFactory;
import im.vector.app.core.di.HomeModule_ProvidesTimelineBackgroundHandlerFactory;
import im.vector.app.core.di.ImageManager;
import im.vector.app.core.di.MavericksAssistedViewModelFactory;
import im.vector.app.core.di.MavericksViewModelComponentBuilder;
import im.vector.app.core.di.ScreenModule_ProvidesSharedViewPoolFactory;
import im.vector.app.core.di.SessionInitializer;
import im.vector.app.core.di.VectorStaticModule;
import im.vector.app.core.di.VectorStaticModule_ProvidesApplicationCoroutineScopeFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesAuthenticationServiceFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesBuildMetaFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesContextFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesCoroutineDispatchersFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesCurrentSessionFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesDefaultSharedPreferencesFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesGlobalScopeFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesHomeServerHistoryServiceFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesLegacySessionImporterFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesLightweightSettingsStorageFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesMatrixConfigurationFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesMatrixFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesPhoneNumberUtilFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesRawServiceFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesResourcesFactory;
import im.vector.app.core.di.VectorStaticModule_ProvidesSharedPreferencesFactory;
import im.vector.app.core.di.VectorViewModelFactory;
import im.vector.app.core.di.VoiceModule;
import im.vector.app.core.di.login.di.LoginModule;
import im.vector.app.core.di.login.di.LoginModule_OkhttpClientFactory;
import im.vector.app.core.di.login.di.LoginModule_ProvideApolloFactory;
import im.vector.app.core.di.login.di.LoginModule_ProvideLoginRepositoryFactory;
import im.vector.app.core.di.login.di.LoginModule_ProvideRemoteLoginDataSourceFactory;
import im.vector.app.core.di.login.domain.ApolloLoginClient;
import im.vector.app.core.di.login.repository.LoginRepository;
import im.vector.app.core.dialogs.GalleryOrCameraDialogHelperFactory;
import im.vector.app.core.dialogs.UnrecognizedCertificateDialog;
import im.vector.app.core.error.DefaultErrorFormatter;
import im.vector.app.core.error.ErrorFormatter;
import im.vector.app.core.files.LocalFilesHelper;
import im.vector.app.core.hardware.HardwareInfo;
import im.vector.app.core.network.WifiDetector;
import im.vector.app.core.notification.NotificationsSettingUpdater;
import im.vector.app.core.notification.UpdateEnableNotificationsSettingOnChangeUseCase;
import im.vector.app.core.platform.ConfigurationViewModel;
import im.vector.app.core.platform.ScreenOrientationLocker;
import im.vector.app.core.platform.VectorBaseActivity_MembersInjector;
import im.vector.app.core.platform.VectorDummyViewState;
import im.vector.app.core.pushers.EnsureFcmTokenIsRetrievedUseCase;
import im.vector.app.core.pushers.PushParser;
import im.vector.app.core.pushers.PushersManager;
import im.vector.app.core.pushers.RegisterUnifiedPushUseCase;
import im.vector.app.core.pushers.UnifiedPushHelper;
import im.vector.app.core.pushers.UnifiedPushStore;
import im.vector.app.core.pushers.UnregisterUnifiedPushUseCase;
import im.vector.app.core.pushers.VectorPushHandler;
import im.vector.app.core.pushers.VectorUnifiedPushMessagingReceiver;
import im.vector.app.core.pushers.VectorUnifiedPushMessagingReceiver_MembersInjector;
import im.vector.app.core.resources.BuildMeta;
import im.vector.app.core.resources.ColorProvider;
import im.vector.app.core.resources.DefaultAppNameProvider;
import im.vector.app.core.resources.DefaultLocaleProvider;
import im.vector.app.core.resources.DrawableProvider;
import im.vector.app.core.resources.StringArrayProvider;
import im.vector.app.core.resources.StringProvider;
import im.vector.app.core.resources.UserPreferencesProvider;
import im.vector.app.core.resources.VersionCodeProvider;
import im.vector.app.core.services.CallAndroidService;
import im.vector.app.core.services.CallAndroidService_MembersInjector;
import im.vector.app.core.services.VectorSyncAndroidService;
import im.vector.app.core.services.VectorSyncAndroidService_MembersInjector;
import im.vector.app.core.session.ConfigureAndStartSessionUseCase;
import im.vector.app.core.session.EnsureSessionSyncingUseCase;
import im.vector.app.core.session.clientinfo.DeleteMatrixClientInfoUseCase;
import im.vector.app.core.session.clientinfo.DeleteUnusedClientInformationUseCase;
import im.vector.app.core.session.clientinfo.GetMatrixClientInfoUseCase;
import im.vector.app.core.session.clientinfo.SetMatrixClientInfoUseCase;
import im.vector.app.core.session.clientinfo.UpdateMatrixClientInfoUseCase;
import im.vector.app.core.time.Clock;
import im.vector.app.core.time.DefaultClock;
import im.vector.app.core.ui.bottomsheet.BottomSheetGeneric_MembersInjector;
import im.vector.app.core.ui.views.TypingMessageView;
import im.vector.app.core.ui.views.TypingMessageView_MembersInjector;
import im.vector.app.core.utils.AndroidSystemSettingsProvider;
import im.vector.app.core.utils.AssetReader;
import im.vector.app.core.utils.CheckWebViewPermissionsUseCase;
import im.vector.app.core.utils.CopyToClipboardUseCase;
import im.vector.app.core.utils.DimensionConverter;
import im.vector.app.core.utils.RingtoneUtils;
import im.vector.app.di.FlavorModule_Companion_ProvideGuardServiceStarterFactory;
import im.vector.app.features.MainActivity;
import im.vector.app.features.MainActivity_MembersInjector;
import im.vector.app.features.analytics.AnalyticsTracker;
import im.vector.app.features.analytics.DecryptionFailureTracker;
import im.vector.app.features.analytics.accountdata.AnalyticsAccountDataViewModel;
import im.vector.app.features.analytics.impl.DefaultVectorAnalytics;
import im.vector.app.features.analytics.impl.LateInitUserPropertiesFactory;
import im.vector.app.features.analytics.impl.PostHogFactory;
import im.vector.app.features.analytics.impl.SentryAnalytics;
import im.vector.app.features.analytics.metrics.VectorPlugins;
import im.vector.app.features.analytics.metrics.sentry.SentryDownloadDeviceKeysMetrics;
import im.vector.app.features.analytics.metrics.sentry.SentrySyncDurationMetrics;
import im.vector.app.features.analytics.store.AnalyticsStore;
import im.vector.app.features.analytics.ui.consent.AnalyticsConsentViewModel;
import im.vector.app.features.analytics.ui.consent.AnalyticsConsentViewState;
import im.vector.app.features.analytics.ui.consent.AnalyticsOptInActivity;
import im.vector.app.features.analytics.ui.consent.AnalyticsOptInActivity_MembersInjector;
import im.vector.app.features.analytics.ui.consent.AnalyticsOptInFragment;
import im.vector.app.features.analytics.ui.consent.AnalyticsOptInFragment_MembersInjector;
import im.vector.app.features.attachments.AttachmentTypeSelectorBottomSheet;
import im.vector.app.features.attachments.AttachmentTypeSelectorViewModel;
import im.vector.app.features.attachments.AttachmentTypeSelectorViewState;
import im.vector.app.features.attachments.MultiPickerIncomingFiles;
import im.vector.app.features.attachments.ShareIntentHandler;
import im.vector.app.features.attachments.preview.AttachmentBigPreviewController;
import im.vector.app.features.attachments.preview.AttachmentMiniaturePreviewController;
import im.vector.app.features.attachments.preview.AttachmentsPreviewActivity;
import im.vector.app.features.attachments.preview.AttachmentsPreviewFragment;
import im.vector.app.features.attachments.preview.AttachmentsPreviewFragment_MembersInjector;
import im.vector.app.features.auth.PendingAuthHandler;
import im.vector.app.features.auth.ReAuthActivity;
import im.vector.app.features.auth.ReAuthActivity_MembersInjector;
import im.vector.app.features.auth.ReAuthState;
import im.vector.app.features.auth.ReAuthViewModel;
import im.vector.app.features.autocomplete.command.AutocompleteCommandController;
import im.vector.app.features.autocomplete.command.AutocompleteCommandPresenter;
import im.vector.app.features.autocomplete.command.CommandAutocompletePolicy;
import im.vector.app.features.autocomplete.emoji.AutocompleteEmojiController;
import im.vector.app.features.autocomplete.emoji.AutocompleteEmojiPresenter;
import im.vector.app.features.autocomplete.member.AutocompleteMemberController;
import im.vector.app.features.autocomplete.member.AutocompleteMemberController_Factory;
import im.vector.app.features.autocomplete.member.AutocompleteMemberController_MembersInjector;
import im.vector.app.features.autocomplete.member.AutocompleteMemberPresenter;
import im.vector.app.features.autocomplete.room.AutocompleteRoomController;
import im.vector.app.features.autocomplete.room.AutocompleteRoomPresenter;
import im.vector.app.features.call.CallControlsBottomSheet;
import im.vector.app.features.call.CallControlsBottomSheet_MembersInjector;
import im.vector.app.features.call.CallProximityManager;
import im.vector.app.features.call.SharedKnownCallsViewModel;
import im.vector.app.features.call.VectorCallActivity;
import im.vector.app.features.call.VectorCallActivity_MembersInjector;
import im.vector.app.features.call.VectorCallViewModel;
import im.vector.app.features.call.VectorCallViewState;
import im.vector.app.features.call.conference.JitsiActiveConferenceHolder;
import im.vector.app.features.call.conference.JitsiCallViewModel;
import im.vector.app.features.call.conference.JitsiCallViewState;
import im.vector.app.features.call.conference.JitsiService;
import im.vector.app.features.call.conference.VectorJitsiActivity;
import im.vector.app.features.call.conference.jwt.JitsiJWTFactory;
import im.vector.app.features.call.dialpad.CallDialPadBottomSheet;
import im.vector.app.features.call.dialpad.CallDialPadBottomSheet_MembersInjector;
import im.vector.app.features.call.dialpad.DialPadLookup;
import im.vector.app.features.call.dialpad.PstnDialActivity;
import im.vector.app.features.call.dialpad.PstnDialActivity_MembersInjector;
import im.vector.app.features.call.transfer.CallTransferActivity;
import im.vector.app.features.call.transfer.CallTransferViewModel;
import im.vector.app.features.call.transfer.CallTransferViewState;
import im.vector.app.features.call.webrtc.ScreenCaptureAndroidService;
import im.vector.app.features.call.webrtc.ScreenCaptureAndroidService_MembersInjector;
import im.vector.app.features.call.webrtc.ScreenCaptureServiceConnection;
import im.vector.app.features.call.webrtc.WebRtcCallManager;
import im.vector.app.features.command.CommandParser;
import im.vector.app.features.configuration.VectorConfiguration;
import im.vector.app.features.configuration.VectorCustomEventTypesProvider;
import im.vector.app.features.contactsbook.ContactsBookController;
import im.vector.app.features.contactsbook.ContactsBookFragment;
import im.vector.app.features.contactsbook.ContactsBookFragment_MembersInjector;
import im.vector.app.features.contactsbook.ContactsBookViewModel;
import im.vector.app.features.contactsbook.ContactsBookViewState;
import im.vector.app.features.createdirect.CreateDirectRoomActivity;
import im.vector.app.features.createdirect.CreateDirectRoomViewModel;
import im.vector.app.features.createdirect.CreateDirectRoomViewState;
import im.vector.app.features.createdirect.DirectRoomHelper;
import im.vector.app.features.crypto.keys.KeysExporter;
import im.vector.app.features.crypto.keys.KeysImporter;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreActivity;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromKeyFragment;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromKeyViewModel;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromPassphraseFragment;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromPassphraseViewModel;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreSharedViewModel;
import im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreSuccessFragment;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupManageActivity;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupSettingViewState;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupSettingsFragment;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupSettingsFragment_MembersInjector;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupSettingsRecyclerViewController;
import im.vector.app.features.crypto.keysbackup.settings.KeysBackupSettingsViewModel;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupActivity;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupActivity_MembersInjector;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupSharedViewModel;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupStep1Fragment;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupStep2Fragment;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupStep2Fragment_MembersInjector;
import im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupStep3Fragment;
import im.vector.app.features.crypto.keysrequest.KeyRequestHandler;
import im.vector.app.features.crypto.quads.SharedSecureStorageActivity;
import im.vector.app.features.crypto.quads.SharedSecureStorageViewModel;
import im.vector.app.features.crypto.quads.SharedSecureStorageViewState;
import im.vector.app.features.crypto.quads.SharedSecuredStorageKeyFragment;
import im.vector.app.features.crypto.quads.SharedSecuredStoragePassphraseFragment;
import im.vector.app.features.crypto.quads.SharedSecuredStorageResetAllFragment;
import im.vector.app.features.crypto.recover.BackupToQuadSMigrationTask;
import im.vector.app.features.crypto.recover.BootstrapBottomSheet;
import im.vector.app.features.crypto.recover.BootstrapConclusionFragment;
import im.vector.app.features.crypto.recover.BootstrapConclusionFragment_MembersInjector;
import im.vector.app.features.crypto.recover.BootstrapConfirmPassphraseFragment;
import im.vector.app.features.crypto.recover.BootstrapCrossSigningTask;
import im.vector.app.features.crypto.recover.BootstrapEnterPassphraseFragment;
import im.vector.app.features.crypto.recover.BootstrapEnterPassphraseFragment_MembersInjector;
import im.vector.app.features.crypto.recover.BootstrapMigrateBackupFragment;
import im.vector.app.features.crypto.recover.BootstrapMigrateBackupFragment_MembersInjector;
import im.vector.app.features.crypto.recover.BootstrapReAuthFragment;
import im.vector.app.features.crypto.recover.BootstrapSaveRecoveryKeyFragment;
import im.vector.app.features.crypto.recover.BootstrapSetupRecoveryKeyFragment;
import im.vector.app.features.crypto.recover.BootstrapSharedViewModel;
import im.vector.app.features.crypto.recover.BootstrapViewState;
import im.vector.app.features.crypto.recover.BootstrapWaitingFragment;
import im.vector.app.features.crypto.verification.IncomingVerificationRequestHandler;
import im.vector.app.features.crypto.verification.QuadSLoadingFragment;
import im.vector.app.features.crypto.verification.SupportedVerificationMethodsProvider;
import im.vector.app.features.crypto.verification.VerificationBottomSheet;
import im.vector.app.features.crypto.verification.VerificationBottomSheetViewModel;
import im.vector.app.features.crypto.verification.VerificationBottomSheetViewState;
import im.vector.app.features.crypto.verification.VerificationBottomSheet_MembersInjector;
import im.vector.app.features.crypto.verification.cancel.VerificationCancelController;
import im.vector.app.features.crypto.verification.cancel.VerificationCancelFragment;
import im.vector.app.features.crypto.verification.cancel.VerificationCancelFragment_MembersInjector;
import im.vector.app.features.crypto.verification.cancel.VerificationNotMeController;
import im.vector.app.features.crypto.verification.cancel.VerificationNotMeFragment;
import im.vector.app.features.crypto.verification.cancel.VerificationNotMeFragment_MembersInjector;
import im.vector.app.features.crypto.verification.choose.VerificationChooseMethodController;
import im.vector.app.features.crypto.verification.choose.VerificationChooseMethodFragment;
import im.vector.app.features.crypto.verification.choose.VerificationChooseMethodFragment_MembersInjector;
import im.vector.app.features.crypto.verification.choose.VerificationChooseMethodViewModel;
import im.vector.app.features.crypto.verification.choose.VerificationChooseMethodViewState;
import im.vector.app.features.crypto.verification.conclusion.VerificationConclusionController;
import im.vector.app.features.crypto.verification.conclusion.VerificationConclusionFragment;
import im.vector.app.features.crypto.verification.conclusion.VerificationConclusionFragment_MembersInjector;
import im.vector.app.features.crypto.verification.emoji.VerificationEmojiCodeController;
import im.vector.app.features.crypto.verification.emoji.VerificationEmojiCodeFragment;
import im.vector.app.features.crypto.verification.emoji.VerificationEmojiCodeFragment_MembersInjector;
import im.vector.app.features.crypto.verification.emoji.VerificationEmojiCodeViewModel;
import im.vector.app.features.crypto.verification.emoji.VerificationEmojiCodeViewState;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQRWaitingController;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQRWaitingFragment;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQRWaitingFragment_MembersInjector;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQrScannedByOtherController;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQrScannedByOtherFragment;
import im.vector.app.features.crypto.verification.qrconfirmation.VerificationQrScannedByOtherFragment_MembersInjector;
import im.vector.app.features.crypto.verification.request.VerificationRequestController;
import im.vector.app.features.crypto.verification.request.VerificationRequestFragment;
import im.vector.app.features.crypto.verification.request.VerificationRequestFragment_MembersInjector;
import im.vector.app.features.debug.DebugMenuActivity;
import im.vector.app.features.debug.DebugMenuActivity_MembersInjector;
import im.vector.app.features.debug.DebugPermissionActivity;
import im.vector.app.features.debug.analytics.DebugAnalyticsActivity;
import im.vector.app.features.debug.analytics.DebugAnalyticsViewModel;
import im.vector.app.features.debug.analytics.DebugAnalyticsViewState;
import im.vector.app.features.debug.di.DebugModule_Companion_ProvidesDebugNavigatorFactory;
import im.vector.app.features.debug.di.FeaturesModule_Companion_ProvidesDebugVectorFeaturesFactory;
import im.vector.app.features.debug.di.FeaturesModule_Companion_ProvidesDebugVectorOverridesFactory;
import im.vector.app.features.debug.di.FeaturesModule_Companion_ProvidesDefaultVectorFeaturesFactory;
import im.vector.app.features.debug.features.DebugFeaturesSettingsActivity;
import im.vector.app.features.debug.features.DebugFeaturesSettingsActivity_MembersInjector;
import im.vector.app.features.debug.features.DebugFeaturesStateFactory;
import im.vector.app.features.debug.features.DebugVectorFeatures;
import im.vector.app.features.debug.features.DebugVectorOverrides;
import im.vector.app.features.debug.features.FeaturesController;
import im.vector.app.features.debug.jitsi.DebugJitsiActivity;
import im.vector.app.features.debug.leak.DebugMemoryLeaksActivity;
import im.vector.app.features.debug.leak.DebugMemoryLeaksFragment;
import im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel;
import im.vector.app.features.debug.leak.DebugMemoryLeaksViewState;
import im.vector.app.features.debug.settings.DebugPrivateSettingsActivity;
import im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel;
import im.vector.app.features.debug.settings.DebugPrivateSettingsViewState;
import im.vector.app.features.devtools.RoomDevToolActivity;
import im.vector.app.features.devtools.RoomDevToolActivity_MembersInjector;
import im.vector.app.features.devtools.RoomDevToolEditFragment;
import im.vector.app.features.devtools.RoomDevToolFragment;
import im.vector.app.features.devtools.RoomDevToolFragment_MembersInjector;
import im.vector.app.features.devtools.RoomDevToolRootController;
import im.vector.app.features.devtools.RoomDevToolSendFormController;
import im.vector.app.features.devtools.RoomDevToolSendFormFragment;
import im.vector.app.features.devtools.RoomDevToolSendFormFragment_MembersInjector;
import im.vector.app.features.devtools.RoomDevToolStateEventListFragment;
import im.vector.app.features.devtools.RoomDevToolStateEventListFragment_MembersInjector;
import im.vector.app.features.devtools.RoomDevToolViewModel;
import im.vector.app.features.devtools.RoomDevToolViewState;
import im.vector.app.features.devtools.RoomStateListController;
import im.vector.app.features.disclaimer.DisclaimerDialog;
import im.vector.app.features.discovery.DiscoverySettingsController;
import im.vector.app.features.discovery.DiscoverySettingsFragment;
import im.vector.app.features.discovery.DiscoverySettingsFragment_MembersInjector;
import im.vector.app.features.discovery.DiscoverySettingsState;
import im.vector.app.features.discovery.DiscoverySettingsViewModel;
import im.vector.app.features.discovery.DiscoverySharedViewModel;
import im.vector.app.features.discovery.change.SetIdentityServerFragment;
import im.vector.app.features.discovery.change.SetIdentityServerFragment_MembersInjector;
import im.vector.app.features.discovery.change.SetIdentityServerState;
import im.vector.app.features.discovery.change.SetIdentityServerViewModel;
import im.vector.app.features.home.AvatarRenderer;
import im.vector.app.features.home.HomeActivity;
import im.vector.app.features.home.HomeActivityViewModel;
import im.vector.app.features.home.HomeActivityViewState;
import im.vector.app.features.home.HomeActivity_MembersInjector;
import im.vector.app.features.home.HomeDetailFragment;
import im.vector.app.features.home.HomeDetailFragment_MembersInjector;
import im.vector.app.features.home.HomeDetailViewModel;
import im.vector.app.features.home.HomeDetailViewState;
import im.vector.app.features.home.HomeDrawerFragment;
import im.vector.app.features.home.HomeDrawerFragment_MembersInjector;
import im.vector.app.features.home.HomeSharedActionViewModel;
import im.vector.app.features.home.InitSyncStepFormatter;
import im.vector.app.features.home.IsNewLoginAlertShownUseCase;
import im.vector.app.features.home.LoadingFragment;
import im.vector.app.features.home.NewHomeDetailFragment;
import im.vector.app.features.home.NewHomeDetailFragment_MembersInjector;
import im.vector.app.features.home.NotificationPermissionManager;
import im.vector.app.features.home.SetNewLoginAlertShownUseCase;
import im.vector.app.features.home.SetUnverifiedSessionsAlertShownUseCase;
import im.vector.app.features.home.ShortcutCreator;
import im.vector.app.features.home.ShortcutCreator_Factory;
import im.vector.app.features.home.ShortcutCreator_MembersInjector;
import im.vector.app.features.home.ShortcutsHandler;
import im.vector.app.features.home.ShouldShowUnverifiedSessionsAlertUseCase;
import im.vector.app.features.home.UnknownDeviceDetectorSharedViewModel;
import im.vector.app.features.home.UnknownDevicesState;
import im.vector.app.features.home.UnreadMessagesSharedViewModel;
import im.vector.app.features.home.UnreadMessagesState;
import im.vector.app.features.home.UserColorAccountDataViewModel;
import im.vector.app.features.home.room.breadcrumbs.BreadcrumbsController;
import im.vector.app.features.home.room.breadcrumbs.BreadcrumbsFragment;
import im.vector.app.features.home.room.breadcrumbs.BreadcrumbsFragment_MembersInjector;
import im.vector.app.features.home.room.breadcrumbs.BreadcrumbsViewModel;
import im.vector.app.features.home.room.breadcrumbs.BreadcrumbsViewState;
import im.vector.app.features.home.room.detail.AutoCompleter;
import im.vector.app.features.home.room.detail.ChatEffectManager;
import im.vector.app.features.home.room.detail.JoinReplacementRoomBottomSheet;
import im.vector.app.features.home.room.detail.JoinReplacementRoomBottomSheet_MembersInjector;
import im.vector.app.features.home.room.detail.RoomDetailActivity;
import im.vector.app.features.home.room.detail.RoomDetailActivity_MembersInjector;
import im.vector.app.features.home.room.detail.RoomDetailPendingActionStore;
import im.vector.app.features.home.room.detail.RoomDetailSharedActionViewModel;
import im.vector.app.features.home.room.detail.RoomDetailViewState;
import im.vector.app.features.home.room.detail.TimelineFragment;
import im.vector.app.features.home.room.detail.TimelineFragment_MembersInjector;
import im.vector.app.features.home.room.detail.TimelineViewModel;
import im.vector.app.features.home.room.detail.composer.AudioMessageHelper;
import im.vector.app.features.home.room.detail.composer.MessageComposerFragment;
import im.vector.app.features.home.room.detail.composer.MessageComposerFragment_MembersInjector;
import im.vector.app.features.home.room.detail.composer.MessageComposerViewModel;
import im.vector.app.features.home.room.detail.composer.MessageComposerViewState;
import im.vector.app.features.home.room.detail.composer.PlainTextComposerLayout;
import im.vector.app.features.home.room.detail.composer.PlainTextComposerLayout_MembersInjector;
import im.vector.app.features.home.room.detail.composer.link.SetLinkFragment;
import im.vector.app.features.home.room.detail.composer.link.SetLinkViewModel;
import im.vector.app.features.home.room.detail.composer.link.SetLinkViewState;
import im.vector.app.features.home.room.detail.composer.rainbow.RainbowGenerator;
import im.vector.app.features.home.room.detail.composer.voice.VoiceMessageRecorderView;
import im.vector.app.features.home.room.detail.composer.voice.VoiceMessageRecorderView_MembersInjector;
import im.vector.app.features.home.room.detail.composer.voice.VoiceRecorderFragment;
import im.vector.app.features.home.room.detail.composer.voice.VoiceRecorderFragment_MembersInjector;
import im.vector.app.features.home.room.detail.location.RedactLiveLocationShareEventUseCase;
import im.vector.app.features.home.room.detail.readreceipts.DisplayReadReceiptsBottomSheet;
import im.vector.app.features.home.room.detail.readreceipts.DisplayReadReceiptsBottomSheet_MembersInjector;
import im.vector.app.features.home.room.detail.readreceipts.DisplayReadReceiptsController;
import im.vector.app.features.home.room.detail.search.SearchActivity;
import im.vector.app.features.home.room.detail.search.SearchFragment;
import im.vector.app.features.home.room.detail.search.SearchFragment_MembersInjector;
import im.vector.app.features.home.room.detail.search.SearchResultController;
import im.vector.app.features.home.room.detail.search.SearchViewModel;
import im.vector.app.features.home.room.detail.search.SearchViewState;
import im.vector.app.features.home.room.detail.sticker.StickerPickerActionHandler;
import im.vector.app.features.home.room.detail.timeline.MessageColorProvider;
import im.vector.app.features.home.room.detail.timeline.TimelineEventController;
import im.vector.app.features.home.room.detail.timeline.action.CheckIfCanRedactEventUseCase;
import im.vector.app.features.home.room.detail.timeline.action.CheckIfCanReplyEventUseCase;
import im.vector.app.features.home.room.detail.timeline.action.MessageActionState;
import im.vector.app.features.home.room.detail.timeline.action.MessageActionsBottomSheet;
import im.vector.app.features.home.room.detail.timeline.action.MessageActionsBottomSheet_MembersInjector;
import im.vector.app.features.home.room.detail.timeline.action.MessageActionsEpoxyController;
import im.vector.app.features.home.room.detail.timeline.action.MessageActionsViewModel;
import im.vector.app.features.home.room.detail.timeline.action.MessageSharedActionViewModel;
import im.vector.app.features.home.room.detail.timeline.edithistory.ViewEditHistoryBottomSheet;
import im.vector.app.features.home.room.detail.timeline.edithistory.ViewEditHistoryBottomSheet_MembersInjector;
import im.vector.app.features.home.room.detail.timeline.edithistory.ViewEditHistoryEpoxyController;
import im.vector.app.features.home.room.detail.timeline.edithistory.ViewEditHistoryViewModel;
import im.vector.app.features.home.room.detail.timeline.edithistory.ViewEditHistoryViewState;
import im.vector.app.features.home.room.detail.timeline.factory.CallItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.DefaultItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.EncryptedItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.EncryptionItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.LiveLocationShareMessageItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.MergedHeaderItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.MessageItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.NoticeItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.PollItemViewStateFactory;
import im.vector.app.features.home.room.detail.timeline.factory.PollOptionViewStateFactory;
import im.vector.app.features.home.room.detail.timeline.factory.ReadReceiptsItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.RoomCreateItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.TimelineFactory;
import im.vector.app.features.home.room.detail.timeline.factory.TimelineItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.VerificationItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.VoiceBroadcastItemFactory;
import im.vector.app.features.home.room.detail.timeline.factory.WidgetItemFactory;
import im.vector.app.features.home.room.detail.timeline.format.DisplayableEventFormatter;
import im.vector.app.features.home.room.detail.timeline.format.EventDetailsFormatter;
import im.vector.app.features.home.room.detail.timeline.format.NoticeEventFormatter;
import im.vector.app.features.home.room.detail.timeline.format.RoomHistoryVisibilityFormatter;
import im.vector.app.features.home.room.detail.timeline.helper.AudioMessagePlaybackTracker;
import im.vector.app.features.home.room.detail.timeline.helper.AvatarSizeProvider;
import im.vector.app.features.home.room.detail.timeline.helper.ContentDownloadStateTrackerBinder;
import im.vector.app.features.home.room.detail.timeline.helper.ContentUploadStateTrackerBinder;
import im.vector.app.features.home.room.detail.timeline.helper.LocationPinProvider;
import im.vector.app.features.home.room.detail.timeline.helper.MatrixItemColorProvider;
import im.vector.app.features.home.room.detail.timeline.helper.MessageInformationDataFactory;
import im.vector.app.features.home.room.detail.timeline.helper.MessageItemAttributesFactory;
import im.vector.app.features.home.room.detail.timeline.helper.PollResponseDataFactory;
import im.vector.app.features.home.room.detail.timeline.helper.ReactionsSummaryFactory;
import im.vector.app.features.home.room.detail.timeline.helper.TimelineEventVisibilityHelper;
import im.vector.app.features.home.room.detail.timeline.helper.TimelineMediaSizeProvider;
import im.vector.app.features.home.room.detail.timeline.helper.TimelineSettingsFactory;
import im.vector.app.features.home.room.detail.timeline.reactions.DisplayReactionsViewState;
import im.vector.app.features.home.room.detail.timeline.reactions.ViewReactionsBottomSheet;
import im.vector.app.features.home.room.detail.timeline.reactions.ViewReactionsBottomSheet_MembersInjector;
import im.vector.app.features.home.room.detail.timeline.reactions.ViewReactionsEpoxyController;
import im.vector.app.features.home.room.detail.timeline.reactions.ViewReactionsViewModel;
import im.vector.app.features.home.room.detail.timeline.render.EventTextRenderer;
import im.vector.app.features.home.room.detail.timeline.render.ProcessBodyOfReplyToEventUseCase;
import im.vector.app.features.home.room.detail.timeline.style.TimelineLayoutSettingsProvider;
import im.vector.app.features.home.room.detail.timeline.style.TimelineMessageLayoutFactory;
import im.vector.app.features.home.room.detail.upgrade.MigrateRoomBottomSheet;
import im.vector.app.features.home.room.detail.upgrade.MigrateRoomBottomSheet_MembersInjector;
import im.vector.app.features.home.room.detail.upgrade.MigrateRoomViewModel;
import im.vector.app.features.home.room.detail.upgrade.MigrateRoomViewState;
import im.vector.app.features.home.room.detail.upgrade.UpgradeRoomViewModelTask;
import im.vector.app.features.home.room.detail.widget.RoomWidgetsBottomSheet;
import im.vector.app.features.home.room.detail.widget.RoomWidgetsBottomSheet_MembersInjector;
import im.vector.app.features.home.room.detail.widget.RoomWidgetsController;
import im.vector.app.features.home.room.filtered.FilteredRoomsActivity;
import im.vector.app.features.home.room.list.BreadcrumbsRoomComparator;
import im.vector.app.features.home.room.list.ChronologicalRoomComparator;
import im.vector.app.features.home.room.list.RoomListFooterController;
import im.vector.app.features.home.room.list.RoomListFragment;
import im.vector.app.features.home.room.list.RoomListFragment_MembersInjector;
import im.vector.app.features.home.room.list.RoomListViewModel;
import im.vector.app.features.home.room.list.RoomListViewState;
import im.vector.app.features.home.room.list.RoomSummaryItemFactory;
import im.vector.app.features.home.room.list.RoomSummaryPagedControllerFactory;
import im.vector.app.features.home.room.list.actions.RoomListQuickActionsBottomSheet;
import im.vector.app.features.home.room.list.actions.RoomListQuickActionsBottomSheet_MembersInjector;
import im.vector.app.features.home.room.list.actions.RoomListQuickActionsEpoxyController;
import im.vector.app.features.home.room.list.actions.RoomListQuickActionsSharedActionViewModel;
import im.vector.app.features.home.room.list.actions.RoomListSharedActionViewModel;
import im.vector.app.features.home.room.list.home.HomeFilteredRoomsController;
import im.vector.app.features.home.room.list.home.HomeLayoutPreferencesStore;
import im.vector.app.features.home.room.list.home.HomeRoomListFragment;
import im.vector.app.features.home.room.list.home.HomeRoomListFragment_MembersInjector;
import im.vector.app.features.home.room.list.home.HomeRoomListViewModel;
import im.vector.app.features.home.room.list.home.HomeRoomListViewState;
import im.vector.app.features.home.room.list.home.NewChatBottomSheet;
import im.vector.app.features.home.room.list.home.NewChatBottomSheet_MembersInjector;
import im.vector.app.features.home.room.list.home.header.HomeRoomsHeadersController;
import im.vector.app.features.home.room.list.home.invites.InvitesActivity;
import im.vector.app.features.home.room.list.home.invites.InvitesController;
import im.vector.app.features.home.room.list.home.invites.InvitesFragment;
import im.vector.app.features.home.room.list.home.invites.InvitesFragment_MembersInjector;
import im.vector.app.features.home.room.list.home.invites.InvitesViewModel;
import im.vector.app.features.home.room.list.home.invites.InvitesViewState;
import im.vector.app.features.home.room.list.home.layout.HomeLayoutSettingBottomDialogFragment;
import im.vector.app.features.home.room.list.home.layout.HomeLayoutSettingBottomDialogFragment_MembersInjector;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesActivity;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesActivity_MembersInjector;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesCarouselController;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesFragment;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesFragment_MembersInjector;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesPreferencesStore;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesViewModel;
import im.vector.app.features.home.room.list.usecase.GetLatestPreviewableEventUseCase;
import im.vector.app.features.home.room.threads.ThreadsActivity;
import im.vector.app.features.home.room.threads.ThreadsActivity_MembersInjector;
import im.vector.app.features.home.room.threads.ThreadsManager;
import im.vector.app.features.home.room.threads.list.viewmodel.ThreadListController;
import im.vector.app.features.home.room.threads.list.viewmodel.ThreadListPagedController;
import im.vector.app.features.home.room.threads.list.viewmodel.ThreadListViewModel;
import im.vector.app.features.home.room.threads.list.viewmodel.ThreadListViewState;
import im.vector.app.features.home.room.threads.list.views.ThreadListFragment;
import im.vector.app.features.home.room.threads.list.views.ThreadListFragment_MembersInjector;
import im.vector.app.features.home.room.typing.TypingHelper;
import im.vector.app.features.homeserver.HomeServerCapabilitiesViewModel;
import im.vector.app.features.homeserver.HomeServerCapabilitiesViewState;
import im.vector.app.features.html.EventHtmlRenderer;
import im.vector.app.features.html.MatrixHtmlPluginConfigure;
import im.vector.app.features.html.PillsPostProcessor;
import im.vector.app.features.html.SpanUtils;
import im.vector.app.features.html.VectorHtmlCompressor;
import im.vector.app.features.invite.CompileTimeAutoAcceptInvites;
import im.vector.app.features.invite.InviteUsersToRoomActivity;
import im.vector.app.features.invite.InviteUsersToRoomViewModel;
import im.vector.app.features.invite.InviteUsersToRoomViewState;
import im.vector.app.features.invite.InvitesAcceptor;
import im.vector.app.features.invite.VectorInviteView;
import im.vector.app.features.invite.VectorInviteView_MembersInjector;
import im.vector.app.features.link.LinkHandlerActivity;
import im.vector.app.features.link.LinkHandlerActivity_MembersInjector;
import im.vector.app.features.location.LocationSharingActivity;
import im.vector.app.features.location.LocationSharingFragment;
import im.vector.app.features.location.LocationSharingFragment_MembersInjector;
import im.vector.app.features.location.LocationSharingViewModel;
import im.vector.app.features.location.LocationSharingViewState;
import im.vector.app.features.location.LocationTracker;
import im.vector.app.features.location.UrlMapProvider;
import im.vector.app.features.location.domain.usecase.CompareLocationsUseCase;
import im.vector.app.features.location.live.GetLiveLocationShareSummaryUseCase;
import im.vector.app.features.location.live.StopLiveLocationShareUseCase;
import im.vector.app.features.location.live.duration.ChooseLiveDurationBottomSheet;
import im.vector.app.features.location.live.map.GetListOfUserLiveLocationUseCase;
import im.vector.app.features.location.live.map.LiveLocationBottomSheetController;
import im.vector.app.features.location.live.map.LiveLocationMapViewActivity;
import im.vector.app.features.location.live.map.LiveLocationMapViewFragment;
import im.vector.app.features.location.live.map.LiveLocationMapViewFragment_MembersInjector;
import im.vector.app.features.location.live.map.LiveLocationMapViewModel;
import im.vector.app.features.location.live.map.LiveLocationMapViewState;
import im.vector.app.features.location.live.map.UserLiveLocationViewStateMapper;
import im.vector.app.features.location.live.tracking.LiveLocationNotificationBuilder;
import im.vector.app.features.location.live.tracking.LocationSharingAndroidService;
import im.vector.app.features.location.live.tracking.LocationSharingAndroidService_MembersInjector;
import im.vector.app.features.location.live.tracking.LocationSharingServiceConnection;
import im.vector.app.features.location.preview.LocationPreviewFragment;
import im.vector.app.features.location.preview.LocationPreviewFragment_MembersInjector;
import im.vector.app.features.location.preview.LocationPreviewViewModel;
import im.vector.app.features.location.preview.LocationPreviewViewState;
import im.vector.app.features.login.HomeServerConnectionConfigFactory;
import im.vector.app.features.login.LoginActivity;
import im.vector.app.features.login.LoginCaptchaFragment;
import im.vector.app.features.login.LoginCaptchaFragment_MembersInjector;
import im.vector.app.features.login.LoginFragment;
import im.vector.app.features.login.LoginGenericTextInputFormFragment;
import im.vector.app.features.login.LoginResetPasswordFragment;
import im.vector.app.features.login.LoginResetPasswordMailConfirmationFragment;
import im.vector.app.features.login.LoginResetPasswordSuccessFragment;
import im.vector.app.features.login.LoginServerSelectionFragment;
import im.vector.app.features.login.LoginServerUrlFormFragment;
import im.vector.app.features.login.LoginServerUrlFormFragment_MembersInjector;
import im.vector.app.features.login.LoginSignUpSignInSelectionFragment;
import im.vector.app.features.login.LoginSplashFragment;
import im.vector.app.features.login.LoginSplashFragment_MembersInjector;
import im.vector.app.features.login.LoginViewModel;
import im.vector.app.features.login.LoginViewState;
import im.vector.app.features.login.LoginWaitForEmailFragment;
import im.vector.app.features.login.LoginWebFragment;
import im.vector.app.features.login.LoginWebFragment_MembersInjector;
import im.vector.app.features.login.ReAuthHelper;
import im.vector.app.features.login.SSORedirectRouterActivity;
import im.vector.app.features.login.SSORedirectRouterActivity_MembersInjector;
import im.vector.app.features.login.qr.QrCodeLoginActivity;
import im.vector.app.features.login.qr.QrCodeLoginInstructionsFragment;
import im.vector.app.features.login.qr.QrCodeLoginShowQrCodeFragment;
import im.vector.app.features.login.qr.QrCodeLoginStatusFragment;
import im.vector.app.features.login.qr.QrCodeLoginViewModel;
import im.vector.app.features.login.qr.QrCodeLoginViewState;
import im.vector.app.features.login.terms.PolicyController;
import im.vector.app.features.matrixto.MatrixToBottomSheet;
import im.vector.app.features.matrixto.MatrixToBottomSheetState;
import im.vector.app.features.matrixto.MatrixToBottomSheetViewModel;
import im.vector.app.features.matrixto.MatrixToBottomSheet_MembersInjector;
import im.vector.app.features.matrixto.MatrixToRoomSpaceFragment;
import im.vector.app.features.matrixto.MatrixToRoomSpaceFragment_MembersInjector;
import im.vector.app.features.matrixto.MatrixToUserFragment;
import im.vector.app.features.matrixto.MatrixToUserFragment_MembersInjector;
import im.vector.app.features.matrixto.SpaceCardRenderer;
import im.vector.app.features.media.AttachmentProviderFactory;
import im.vector.app.features.media.BigImageViewerActivity;
import im.vector.app.features.media.BigImageViewerActivity_MembersInjector;
import im.vector.app.features.media.ImageContentRenderer;
import im.vector.app.features.media.VectorAttachmentViewerActivity;
import im.vector.app.features.media.VectorAttachmentViewerActivity_MembersInjector;
import im.vector.app.features.media.VectorAttachmentViewerViewModel;
import im.vector.app.features.media.domain.usecase.DownloadMediaUseCase;
import im.vector.app.features.navigation.DefaultNavigator;
import im.vector.app.features.navigation.Navigator;
import im.vector.app.features.notifications.FilteredEventDetector;
import im.vector.app.features.notifications.NotifiableEventProcessor;
import im.vector.app.features.notifications.NotifiableEventResolver;
import im.vector.app.features.notifications.NotificationActionIds;
import im.vector.app.features.notifications.NotificationBitmapLoader;
import im.vector.app.features.notifications.NotificationBroadcastReceiver;
import im.vector.app.features.notifications.NotificationBroadcastReceiver_MembersInjector;
import im.vector.app.features.notifications.NotificationDisplayer;
import im.vector.app.features.notifications.NotificationDrawerManager;
import im.vector.app.features.notifications.NotificationEventPersistence;
import im.vector.app.features.notifications.NotificationFactory;
import im.vector.app.features.notifications.NotificationRenderer;
import im.vector.app.features.notifications.NotificationUtils;
import im.vector.app.features.notifications.OutdatedEventDetector;
import im.vector.app.features.notifications.PushRuleTriggerListener;
import im.vector.app.features.notifications.RoomGroupMessageCreator;
import im.vector.app.features.notifications.SummaryGroupMessageCreator;
import im.vector.app.features.onboarding.DirectLoginUseCase;
import im.vector.app.features.onboarding.OnboardingActivity;
import im.vector.app.features.onboarding.OnboardingActivity_MembersInjector;
import im.vector.app.features.onboarding.OnboardingVariantFactory;
import im.vector.app.features.onboarding.OnboardingViewModel;
import im.vector.app.features.onboarding.OnboardingViewState;
import im.vector.app.features.onboarding.RegistrationActionHandler;
import im.vector.app.features.onboarding.RegistrationWizardActionDelegate;
import im.vector.app.features.onboarding.StartAuthenticationFlowUseCase;
import im.vector.app.features.onboarding.UriFactory;
import im.vector.app.features.onboarding.UriFilenameResolver;
import im.vector.app.features.onboarding.ftueauth.CaptchaWebview;
import im.vector.app.features.onboarding.ftueauth.FtueAuthAccountCreatedFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCaptchaFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCaptchaFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthChooseDisplayNameFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthChooseProfilePictureFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthChooseProfilePictureFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCombinedLoginFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCombinedLoginFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCombinedRegisterFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthCombinedServerSelectionFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthEmailEntryFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthGenericTextInputFormFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthLegacyStyleCaptchaFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthLegacyStyleCaptchaFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthLegacyWaitForEmailFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthLoginFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthPersonalizationCompleteFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthPhoneConfirmationFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthPhoneEntryFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthPhoneEntryFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordBreakerFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordBreakerFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordEmailEntryFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordEntryFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordMailConfirmationFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthResetPasswordSuccessFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthServerSelectionFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthServerUrlFormFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthServerUrlFormFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSignUpSignInSelectionFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSplashCarouselFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSplashCarouselFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSplashFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthSplashFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthUseCaseFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthUseCaseFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthWaitForEmailFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthWaitForEmailFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.FtueAuthWebFragment;
import im.vector.app.features.onboarding.ftueauth.FtueAuthWebFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.LoginErrorParser;
import im.vector.app.features.onboarding.ftueauth.LoginFieldsValidation;
import im.vector.app.features.onboarding.ftueauth.PhoneNumberParser;
import im.vector.app.features.onboarding.ftueauth.SplashCarouselController;
import im.vector.app.features.onboarding.ftueauth.SplashCarouselStateFactory;
import im.vector.app.features.onboarding.ftueauth.terms.FtueAuthLegacyStyleTermsFragment;
import im.vector.app.features.onboarding.ftueauth.terms.FtueAuthLegacyStyleTermsFragment_MembersInjector;
import im.vector.app.features.onboarding.ftueauth.terms.FtueAuthTermsFragment;
import im.vector.app.features.onboarding.ftueauth.terms.FtueAuthTermsFragment_MembersInjector;
import im.vector.app.features.permalink.PermalinkFactory;
import im.vector.app.features.permalink.PermalinkHandler;
import im.vector.app.features.pin.PinActivity;
import im.vector.app.features.pin.PinFragment;
import im.vector.app.features.pin.PinFragment_MembersInjector;
import im.vector.app.features.pin.PinLocker;
import im.vector.app.features.pin.SharedPrefPinCodeStore;
import im.vector.app.features.pin.lockscreen.biometrics.BiometricHelper;
import im.vector.app.features.pin.lockscreen.configuration.LockScreenConfiguration;
import im.vector.app.features.pin.lockscreen.crypto.KeyStoreCrypto;
import im.vector.app.features.pin.lockscreen.crypto.LockScreenKeyRepository;
import im.vector.app.features.pin.lockscreen.crypto.LockScreenKeysMigrator;
import im.vector.app.features.pin.lockscreen.crypto.migrations.LegacyPinCodeMigrator;
import im.vector.app.features.pin.lockscreen.crypto.migrations.MissingSystemKeyMigrator;
import im.vector.app.features.pin.lockscreen.crypto.migrations.SystemKeyV1Migrator;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvideBiometricManagerFactory;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvideBuildVersionSdkIntProviderFactory;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvideKeyStoreFactory;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvideKeyguardManagerFactory;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvideLegacyPinCodeMigratorFactory;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvideLockScreenConfigFactory;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvidePinCodeKeyAliasFactory;
import im.vector.app.features.pin.lockscreen.di.LockScreenModule_ProvideSystemKeyAliasFactory;
import im.vector.app.features.pin.lockscreen.pincode.PinCodeHelper;
import im.vector.app.features.pin.lockscreen.ui.LockScreenFragment;
import im.vector.app.features.pin.lockscreen.ui.LockScreenViewModel;
import im.vector.app.features.pin.lockscreen.ui.LockScreenViewState;
import im.vector.app.features.poll.create.CreatePollActivity;
import im.vector.app.features.poll.create.CreatePollController;
import im.vector.app.features.poll.create.CreatePollFragment;
import im.vector.app.features.poll.create.CreatePollFragment_MembersInjector;
import im.vector.app.features.poll.create.CreatePollViewModel;
import im.vector.app.features.poll.create.CreatePollViewState;
import im.vector.app.features.popup.PopupAlertManager;
import im.vector.app.features.qrcode.QrCodeScannerActivity;
import im.vector.app.features.qrcode.QrCodeScannerFragment;
import im.vector.app.features.qrcode.QrCodeScannerViewModel;
import im.vector.app.features.rageshake.BugReportActivity;
import im.vector.app.features.rageshake.BugReportState;
import im.vector.app.features.rageshake.BugReportViewModel;
import im.vector.app.features.rageshake.BugReporter;
import im.vector.app.features.rageshake.ProcessInfo;
import im.vector.app.features.rageshake.RageShake;
import im.vector.app.features.rageshake.VectorFileLogger;
import im.vector.app.features.rageshake.VectorUncaughtExceptionHandler;
import im.vector.app.features.reactions.EmojiChooserFragment;
import im.vector.app.features.reactions.EmojiChooserFragment_MembersInjector;
import im.vector.app.features.reactions.EmojiChooserViewModel;
import im.vector.app.features.reactions.EmojiReactionPickerActivity;
import im.vector.app.features.reactions.EmojiReactionPickerActivity_MembersInjector;
import im.vector.app.features.reactions.EmojiRecyclerAdapter;
import im.vector.app.features.reactions.EmojiSearchResultController;
import im.vector.app.features.reactions.EmojiSearchResultFragment;
import im.vector.app.features.reactions.EmojiSearchResultFragment_MembersInjector;
import im.vector.app.features.reactions.EmojiSearchResultViewModel;
import im.vector.app.features.reactions.EmojiSearchResultViewState;
import im.vector.app.features.reactions.data.EmojiDataSource;
import im.vector.app.features.reactions.widget.ReactionButton;
import im.vector.app.features.reactions.widget.ReactionButton_MembersInjector;
import im.vector.app.features.redaction.CheckIfEventIsRedactedUseCase;
import im.vector.app.features.room.RequireActiveMembershipViewModel;
import im.vector.app.features.room.RequireActiveMembershipViewState;
import im.vector.app.features.room.VectorRoomDisplayNameFallbackProvider;
import im.vector.app.features.roomdirectory.ExplicitTermFilter;
import im.vector.app.features.roomdirectory.PublicRoomsController;
import im.vector.app.features.roomdirectory.PublicRoomsFragment;
import im.vector.app.features.roomdirectory.PublicRoomsFragment_MembersInjector;
import im.vector.app.features.roomdirectory.PublicRoomsViewState;
import im.vector.app.features.roomdirectory.RoomDirectoryActivity;
import im.vector.app.features.roomdirectory.RoomDirectoryActivity_MembersInjector;
import im.vector.app.features.roomdirectory.RoomDirectorySharedActionViewModel;
import im.vector.app.features.roomdirectory.RoomDirectoryViewModel;
import im.vector.app.features.roomdirectory.createroom.CreateRoomActivity;
import im.vector.app.features.roomdirectory.createroom.CreateRoomController;
import im.vector.app.features.roomdirectory.createroom.CreateRoomFragment;
import im.vector.app.features.roomdirectory.createroom.CreateRoomFragment_MembersInjector;
import im.vector.app.features.roomdirectory.createroom.CreateRoomViewModel;
import im.vector.app.features.roomdirectory.createroom.CreateRoomViewState;
import im.vector.app.features.roomdirectory.createroom.CreateSubSpaceController;
import im.vector.app.features.roomdirectory.createroom.RoomAliasErrorFormatter;
import im.vector.app.features.roomdirectory.picker.RoomDirectoryListCreator;
import im.vector.app.features.roomdirectory.picker.RoomDirectoryPickerController;
import im.vector.app.features.roomdirectory.picker.RoomDirectoryPickerFragment;
import im.vector.app.features.roomdirectory.picker.RoomDirectoryPickerFragment_MembersInjector;
import im.vector.app.features.roomdirectory.picker.RoomDirectoryPickerViewModel;
import im.vector.app.features.roomdirectory.picker.RoomDirectoryPickerViewState;
import im.vector.app.features.roomdirectory.roompreview.RoomPreviewActivity;
import im.vector.app.features.roomdirectory.roompreview.RoomPreviewNoPreviewFragment;
import im.vector.app.features.roomdirectory.roompreview.RoomPreviewNoPreviewFragment_MembersInjector;
import im.vector.app.features.roomdirectory.roompreview.RoomPreviewViewModel;
import im.vector.app.features.roomdirectory.roompreview.RoomPreviewViewState;
import im.vector.app.features.roommemberprofile.RoomMemberProfileActivity;
import im.vector.app.features.roommemberprofile.RoomMemberProfileController;
import im.vector.app.features.roommemberprofile.RoomMemberProfileFragment;
import im.vector.app.features.roommemberprofile.RoomMemberProfileFragment_MembersInjector;
import im.vector.app.features.roommemberprofile.RoomMemberProfileViewModel;
import im.vector.app.features.roommemberprofile.RoomMemberProfileViewState;
import im.vector.app.features.roommemberprofile.devices.DeviceListBottomSheet;
import im.vector.app.features.roommemberprofile.devices.DeviceListBottomSheetViewModel;
import im.vector.app.features.roommemberprofile.devices.DeviceListEpoxyController;
import im.vector.app.features.roommemberprofile.devices.DeviceListFragment;
import im.vector.app.features.roommemberprofile.devices.DeviceListFragment_MembersInjector;
import im.vector.app.features.roommemberprofile.devices.DeviceListViewState;
import im.vector.app.features.roommemberprofile.devices.DeviceTrustInfoActionFragment;
import im.vector.app.features.roommemberprofile.devices.DeviceTrustInfoActionFragment_MembersInjector;
import im.vector.app.features.roommemberprofile.devices.DeviceTrustInfoEpoxyController;
import im.vector.app.features.roomprofile.RoomProfileActivity;
import im.vector.app.features.roomprofile.RoomProfileActivity_MembersInjector;
import im.vector.app.features.roomprofile.RoomProfileController;
import im.vector.app.features.roomprofile.RoomProfileFragment;
import im.vector.app.features.roomprofile.RoomProfileFragment_MembersInjector;
import im.vector.app.features.roomprofile.RoomProfileSharedActionViewModel;
import im.vector.app.features.roomprofile.RoomProfileViewModel;
import im.vector.app.features.roomprofile.RoomProfileViewState;
import im.vector.app.features.roomprofile.alias.RoomAliasController;
import im.vector.app.features.roomprofile.alias.RoomAliasFragment;
import im.vector.app.features.roomprofile.alias.RoomAliasFragment_MembersInjector;
import im.vector.app.features.roomprofile.alias.RoomAliasViewModel;
import im.vector.app.features.roomprofile.alias.RoomAliasViewState;
import im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheet;
import im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheetController;
import im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheetSharedActionViewModel;
import im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheetState;
import im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheetViewModel;
import im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheet_MembersInjector;
import im.vector.app.features.roomprofile.banned.RoomBannedMemberListController;
import im.vector.app.features.roomprofile.banned.RoomBannedMemberListFragment;
import im.vector.app.features.roomprofile.banned.RoomBannedMemberListFragment_MembersInjector;
import im.vector.app.features.roomprofile.banned.RoomBannedMemberListViewModel;
import im.vector.app.features.roomprofile.banned.RoomBannedMemberListViewState;
import im.vector.app.features.roomprofile.members.RoomMemberListController;
import im.vector.app.features.roomprofile.members.RoomMemberListFragment;
import im.vector.app.features.roomprofile.members.RoomMemberListFragment_MembersInjector;
import im.vector.app.features.roomprofile.members.RoomMemberListViewModel;
import im.vector.app.features.roomprofile.members.RoomMemberListViewState;
import im.vector.app.features.roomprofile.members.RoomMemberSummaryComparator;
import im.vector.app.features.roomprofile.members.RoomMemberSummaryFilter;
import im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsController;
import im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsFragment;
import im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsFragment_MembersInjector;
import im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsViewModel;
import im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsViewState;
import im.vector.app.features.roomprofile.permissions.RoleFormatter;
import im.vector.app.features.roomprofile.permissions.RoomPermissionsController;
import im.vector.app.features.roomprofile.permissions.RoomPermissionsFragment;
import im.vector.app.features.roomprofile.permissions.RoomPermissionsFragment_MembersInjector;
import im.vector.app.features.roomprofile.permissions.RoomPermissionsViewModel;
import im.vector.app.features.roomprofile.permissions.RoomPermissionsViewState;
import im.vector.app.features.roomprofile.polls.RoomPollsFragment;
import im.vector.app.features.roomprofile.polls.RoomPollsViewModel;
import im.vector.app.features.roomprofile.polls.RoomPollsViewState;
import im.vector.app.features.roomprofile.polls.active.RoomActivePollsFragment;
import im.vector.app.features.roomprofile.polls.ended.RoomEndedPollsFragment;
import im.vector.app.features.roomprofile.polls.list.data.RoomPollDataSource;
import im.vector.app.features.roomprofile.polls.list.data.RoomPollRepository;
import im.vector.app.features.roomprofile.polls.list.domain.DisposePollHistoryUseCase;
import im.vector.app.features.roomprofile.polls.list.domain.GetLoadedPollsStatusUseCase;
import im.vector.app.features.roomprofile.polls.list.domain.GetPollsUseCase;
import im.vector.app.features.roomprofile.polls.list.domain.LoadMorePollsUseCase;
import im.vector.app.features.roomprofile.polls.list.domain.SyncPollsUseCase;
import im.vector.app.features.roomprofile.polls.list.ui.PollSummaryMapper;
import im.vector.app.features.roomprofile.polls.list.ui.RoomPollsController;
import im.vector.app.features.roomprofile.polls.list.ui.RoomPollsListFragment_MembersInjector;
import im.vector.app.features.roomprofile.settings.RoomSettingsController;
import im.vector.app.features.roomprofile.settings.RoomSettingsFragment;
import im.vector.app.features.roomprofile.settings.RoomSettingsFragment_MembersInjector;
import im.vector.app.features.roomprofile.settings.RoomSettingsViewModel;
import im.vector.app.features.roomprofile.settings.RoomSettingsViewState;
import im.vector.app.features.roomprofile.settings.historyvisibility.RoomHistoryVisibilityBottomSheet;
import im.vector.app.features.roomprofile.settings.historyvisibility.RoomHistoryVisibilityBottomSheet_MembersInjector;
import im.vector.app.features.roomprofile.settings.historyvisibility.RoomHistoryVisibilityController;
import im.vector.app.features.roomprofile.settings.historyvisibility.RoomHistoryVisibilitySharedActionViewModel;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleActivity;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleAdvancedController;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleBottomSheet;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleBottomSheet_MembersInjector;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleController;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleFragment;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleFragment_MembersInjector;
import im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleSharedActionViewModel;
import im.vector.app.features.roomprofile.settings.joinrule.advanced.ChooseRestrictedController;
import im.vector.app.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedFragment;
import im.vector.app.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedFragment_MembersInjector;
import im.vector.app.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedState;
import im.vector.app.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedViewModel;
import im.vector.app.features.roomprofile.uploads.RoomUploadsFragment;
import im.vector.app.features.roomprofile.uploads.RoomUploadsFragment_MembersInjector;
import im.vector.app.features.roomprofile.uploads.RoomUploadsViewModel;
import im.vector.app.features.roomprofile.uploads.RoomUploadsViewState;
import im.vector.app.features.roomprofile.uploads.files.RoomUploadsFilesFragment;
import im.vector.app.features.roomprofile.uploads.files.RoomUploadsFilesFragment_MembersInjector;
import im.vector.app.features.roomprofile.uploads.files.UploadsFileController;
import im.vector.app.features.roomprofile.uploads.media.RoomUploadsMediaFragment;
import im.vector.app.features.roomprofile.uploads.media.RoomUploadsMediaFragment_MembersInjector;
import im.vector.app.features.roomprofile.uploads.media.UploadsMediaController;
import im.vector.app.features.session.SessionListener;
import im.vector.app.features.settings.FontScalePreferencesImpl;
import im.vector.app.features.settings.VectorDataStore;
import im.vector.app.features.settings.VectorLocale;
import im.vector.app.features.settings.VectorLocaleProvider;
import im.vector.app.features.settings.VectorPreferences;
import im.vector.app.features.settings.VectorSettingsActivity;
import im.vector.app.features.settings.VectorSettingsActivity_MembersInjector;
import im.vector.app.features.settings.VectorSettingsAdvancedSettingsFragment;
import im.vector.app.features.settings.VectorSettingsAdvancedSettingsFragment_MembersInjector;
import im.vector.app.features.settings.VectorSettingsGeneralFragment;
import im.vector.app.features.settings.VectorSettingsGeneralFragment_MembersInjector;
import im.vector.app.features.settings.VectorSettingsHelpAboutFragment;
import im.vector.app.features.settings.VectorSettingsHelpAboutFragment_MembersInjector;
import im.vector.app.features.settings.VectorSettingsPinFragment;
import im.vector.app.features.settings.VectorSettingsPinFragment_MembersInjector;
import im.vector.app.features.settings.VectorSettingsPreferencesFragment;
import im.vector.app.features.settings.VectorSettingsPreferencesFragment_MembersInjector;
import im.vector.app.features.settings.VectorSettingsRootFragment;
import im.vector.app.features.settings.VectorSettingsSecurityPrivacyFragment;
import im.vector.app.features.settings.VectorSettingsSecurityPrivacyFragment_MembersInjector;
import im.vector.app.features.settings.VectorSettingsVoiceVideoFragment;
import im.vector.app.features.settings.VectorSettingsVoiceVideoFragment_MembersInjector;
import im.vector.app.features.settings.account.deactivation.DeactivateAccountFragment;
import im.vector.app.features.settings.account.deactivation.DeactivateAccountViewModel;
import im.vector.app.features.settings.account.deactivation.DeactivateAccountViewState;
import im.vector.app.features.settings.crosssigning.CrossSigningSettingsController;
import im.vector.app.features.settings.crosssigning.CrossSigningSettingsFragment;
import im.vector.app.features.settings.crosssigning.CrossSigningSettingsFragment_MembersInjector;
import im.vector.app.features.settings.crosssigning.CrossSigningSettingsViewModel;
import im.vector.app.features.settings.crosssigning.CrossSigningSettingsViewState;
import im.vector.app.features.settings.devices.DeviceVerificationInfoBottomSheet;
import im.vector.app.features.settings.devices.DeviceVerificationInfoBottomSheetController;
import im.vector.app.features.settings.devices.DeviceVerificationInfoBottomSheetViewModel;
import im.vector.app.features.settings.devices.DeviceVerificationInfoBottomSheetViewState;
import im.vector.app.features.settings.devices.DeviceVerificationInfoBottomSheet_MembersInjector;
import im.vector.app.features.settings.devices.DevicesController;
import im.vector.app.features.settings.devices.DevicesViewModel;
import im.vector.app.features.settings.devices.DevicesViewState;
import im.vector.app.features.settings.devices.GetCurrentSessionCrossSigningInfoUseCase;
import im.vector.app.features.settings.devices.VectorSettingsDevicesFragment;
import im.vector.app.features.settings.devices.VectorSettingsDevicesFragment_MembersInjector;
import im.vector.app.features.settings.devices.v2.GetDeviceFullInfoListUseCase;
import im.vector.app.features.settings.devices.v2.ParseDeviceUserAgentUseCase;
import im.vector.app.features.settings.devices.v2.RefreshDevicesOnCryptoDevicesChangeUseCase;
import im.vector.app.features.settings.devices.v2.RefreshDevicesUseCase;
import im.vector.app.features.settings.devices.v2.ToggleIpAddressVisibilityUseCase;
import im.vector.app.features.settings.devices.v2.VectorSettingsDevicesViewNavigator;
import im.vector.app.features.settings.devices.v2.details.CheckIfSectionApplicationIsVisibleUseCase;
import im.vector.app.features.settings.devices.v2.details.CheckIfSectionDeviceIsVisibleUseCase;
import im.vector.app.features.settings.devices.v2.details.CheckIfSectionSessionIsVisibleUseCase;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsActivity;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsController;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsFragment;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsFragment_MembersInjector;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsViewModel;
import im.vector.app.features.settings.devices.v2.details.SessionDetailsViewState;
import im.vector.app.features.settings.devices.v2.filter.DeviceManagerFilterBottomSheet;
import im.vector.app.features.settings.devices.v2.filter.FilterDevicesUseCase;
import im.vector.app.features.settings.devices.v2.list.CheckIfSessionIsInactiveUseCase;
import im.vector.app.features.settings.devices.v2.list.OtherSessionsController;
import im.vector.app.features.settings.devices.v2.list.OtherSessionsView;
import im.vector.app.features.settings.devices.v2.list.OtherSessionsView_MembersInjector;
import im.vector.app.features.settings.devices.v2.more.SessionLearnMoreBottomSheet;
import im.vector.app.features.settings.devices.v2.more.SessionLearnMoreViewModel;
import im.vector.app.features.settings.devices.v2.more.SessionLearnMoreViewState;
import im.vector.app.features.settings.devices.v2.notification.CanToggleNotificationsViaAccountDataUseCase;
import im.vector.app.features.settings.devices.v2.notification.CanToggleNotificationsViaPusherUseCase;
import im.vector.app.features.settings.devices.v2.notification.CheckIfCanToggleNotificationsViaAccountDataUseCase;
import im.vector.app.features.settings.devices.v2.notification.CheckIfCanToggleNotificationsViaPusherUseCase;
import im.vector.app.features.settings.devices.v2.notification.DeleteNotificationSettingsAccountDataUseCase;
import im.vector.app.features.settings.devices.v2.notification.GetNotificationSettingsAccountDataUpdatesUseCase;
import im.vector.app.features.settings.devices.v2.notification.GetNotificationSettingsAccountDataUseCase;
import im.vector.app.features.settings.devices.v2.notification.GetNotificationsStatusUseCase;
import im.vector.app.features.settings.devices.v2.notification.SetNotificationSettingsAccountDataUseCase;
import im.vector.app.features.settings.devices.v2.notification.ToggleNotificationsUseCase;
import im.vector.app.features.settings.devices.v2.notification.UpdateNotificationSettingsAccountDataUseCase;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsActivity;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsFragment;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsFragment_MembersInjector;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsSecurityRecommendationView;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsViewModel;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsViewNavigator;
import im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsViewState;
import im.vector.app.features.settings.devices.v2.overview.GetDeviceFullInfoUseCase;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewActivity;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewFragment;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewFragment_MembersInjector;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewViewModel;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewViewNavigator;
import im.vector.app.features.settings.devices.v2.overview.SessionOverviewViewState;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionActivity;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionFragment;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionFragment_MembersInjector;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionUseCase;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionViewModel;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionViewNavigator;
import im.vector.app.features.settings.devices.v2.rename.RenameSessionViewState;
import im.vector.app.features.settings.devices.v2.signout.BuildConfirmSignoutDialogUseCase;
import im.vector.app.features.settings.devices.v2.signout.InterceptSignoutFlowResponseUseCase;
import im.vector.app.features.settings.devices.v2.signout.SignoutSessionsUseCase;
import im.vector.app.features.settings.devices.v2.verification.CheckIfCurrentSessionCanBeVerifiedUseCase;
import im.vector.app.features.settings.devices.v2.verification.GetEncryptionTrustLevelForCurrentDeviceUseCase;
import im.vector.app.features.settings.devices.v2.verification.GetEncryptionTrustLevelForDeviceUseCase;
import im.vector.app.features.settings.devices.v2.verification.GetEncryptionTrustLevelForOtherDeviceUseCase;
import im.vector.app.features.settings.devtools.AccountDataEpoxyController;
import im.vector.app.features.settings.devtools.AccountDataFragment;
import im.vector.app.features.settings.devtools.AccountDataFragment_MembersInjector;
import im.vector.app.features.settings.devtools.AccountDataViewModel;
import im.vector.app.features.settings.devtools.AccountDataViewState;
import im.vector.app.features.settings.devtools.GossipingEventsPaperTrailFragment;
import im.vector.app.features.settings.devtools.GossipingEventsPaperTrailFragment_MembersInjector;
import im.vector.app.features.settings.devtools.GossipingEventsPaperTrailState;
import im.vector.app.features.settings.devtools.GossipingEventsPaperTrailViewModel;
import im.vector.app.features.settings.devtools.GossipingTrailPagedEpoxyController;
import im.vector.app.features.settings.devtools.IncomingKeyRequestListFragment;
import im.vector.app.features.settings.devtools.IncomingKeyRequestListFragment_MembersInjector;
import im.vector.app.features.settings.devtools.IncomingKeyRequestPagedController;
import im.vector.app.features.settings.devtools.KeyRequestListViewModel;
import im.vector.app.features.settings.devtools.KeyRequestListViewState;
import im.vector.app.features.settings.devtools.KeyRequestViewModel;
import im.vector.app.features.settings.devtools.KeyRequestViewState;
import im.vector.app.features.settings.devtools.KeyRequestsFragment;
import im.vector.app.features.settings.devtools.KeyRequestsFragment_MembersInjector;
import im.vector.app.features.settings.devtools.OutgoingKeyRequestListFragment;
import im.vector.app.features.settings.devtools.OutgoingKeyRequestListFragment_MembersInjector;
import im.vector.app.features.settings.devtools.OutgoingKeyRequestPagedController;
import im.vector.app.features.settings.font.FontScaleSettingActivity;
import im.vector.app.features.settings.font.FontScaleSettingController;
import im.vector.app.features.settings.font.FontScaleSettingFragment;
import im.vector.app.features.settings.font.FontScaleSettingFragment_MembersInjector;
import im.vector.app.features.settings.font.FontScaleSettingViewModel;
import im.vector.app.features.settings.font.FontScaleSettingViewState;
import im.vector.app.features.settings.homeserver.HomeServerSettingsViewState;
import im.vector.app.features.settings.homeserver.HomeserverSettingsController;
import im.vector.app.features.settings.homeserver.HomeserverSettingsFragment;
import im.vector.app.features.settings.homeserver.HomeserverSettingsFragment_MembersInjector;
import im.vector.app.features.settings.homeserver.HomeserverSettingsViewModel;
import im.vector.app.features.settings.ignored.IgnoredUsersController;
import im.vector.app.features.settings.ignored.IgnoredUsersViewModel;
import im.vector.app.features.settings.ignored.IgnoredUsersViewState;
import im.vector.app.features.settings.ignored.VectorSettingsIgnoredUsersFragment;
import im.vector.app.features.settings.ignored.VectorSettingsIgnoredUsersFragment_MembersInjector;
import im.vector.app.features.settings.labs.VectorSettingsLabsFragment;
import im.vector.app.features.settings.labs.VectorSettingsLabsFragment_MembersInjector;
import im.vector.app.features.settings.labs.VectorSettingsLabsViewModel;
import im.vector.app.features.settings.labs.VectorSettingsLabsViewState;
import im.vector.app.features.settings.legals.ElementLegals;
import im.vector.app.features.settings.legals.LegalsController;
import im.vector.app.features.settings.legals.LegalsFragment;
import im.vector.app.features.settings.legals.LegalsFragment_MembersInjector;
import im.vector.app.features.settings.legals.LegalsState;
import im.vector.app.features.settings.legals.LegalsViewModel;
import im.vector.app.features.settings.locale.LocalePickerController;
import im.vector.app.features.settings.locale.LocalePickerFragment;
import im.vector.app.features.settings.locale.LocalePickerFragment_MembersInjector;
import im.vector.app.features.settings.locale.LocalePickerViewModel;
import im.vector.app.features.settings.locale.LocalePickerViewState;
import im.vector.app.features.settings.locale.SystemLocaleProvider;
import im.vector.app.features.settings.notifications.DisableNotificationsForCurrentSessionUseCase;
import im.vector.app.features.settings.notifications.EnableNotificationsForCurrentSessionUseCase;
import im.vector.app.features.settings.notifications.ToggleNotificationsForCurrentSessionUseCase;
import im.vector.app.features.settings.notifications.VectorSettingsAdvancedNotificationPreferenceFragment;
import im.vector.app.features.settings.notifications.VectorSettingsNotificationPreferenceFragment;
import im.vector.app.features.settings.notifications.VectorSettingsNotificationPreferenceFragment_MembersInjector;
import im.vector.app.features.settings.notifications.VectorSettingsNotificationPreferenceViewModel;
import im.vector.app.features.settings.notifications.VectorSettingsNotificationsTroubleshootFragment;
import im.vector.app.features.settings.notifications.VectorSettingsNotificationsTroubleshootFragment_MembersInjector;
import im.vector.app.features.settings.push.PushGateWayController;
import im.vector.app.features.settings.push.PushGatewayViewState;
import im.vector.app.features.settings.push.PushGatewaysFragment;
import im.vector.app.features.settings.push.PushGatewaysFragment_MembersInjector;
import im.vector.app.features.settings.push.PushGatewaysViewModel;
import im.vector.app.features.settings.push.PushRulesController;
import im.vector.app.features.settings.push.PushRulesFragment;
import im.vector.app.features.settings.push.PushRulesFragment_MembersInjector;
import im.vector.app.features.settings.threepids.ThreePidsSettingsController;
import im.vector.app.features.settings.threepids.ThreePidsSettingsFragment;
import im.vector.app.features.settings.threepids.ThreePidsSettingsFragment_MembersInjector;
import im.vector.app.features.settings.threepids.ThreePidsSettingsViewModel;
import im.vector.app.features.settings.threepids.ThreePidsSettingsViewState;
import im.vector.app.features.settings.troubleshoot.TestAccountSettings;
import im.vector.app.features.settings.troubleshoot.TestAvailableUnifiedPushDistributors;
import im.vector.app.features.settings.troubleshoot.TestCurrentUnifiedPushDistributor;
import im.vector.app.features.settings.troubleshoot.TestDeviceSettings;
import im.vector.app.features.settings.troubleshoot.TestEndpointAsTokenRegistration;
import im.vector.app.features.settings.troubleshoot.TestNotification;
import im.vector.app.features.settings.troubleshoot.TestPushFromPushGateway;
import im.vector.app.features.settings.troubleshoot.TestPushRulesSettings;
import im.vector.app.features.settings.troubleshoot.TestSystemSettings;
import im.vector.app.features.settings.troubleshoot.TestUnifiedPushEndpoint;
import im.vector.app.features.settings.troubleshoot.TestUnifiedPushGateway;
import im.vector.app.features.share.IncomingShareActivity;
import im.vector.app.features.share.IncomingShareController;
import im.vector.app.features.share.IncomingShareFragment;
import im.vector.app.features.share.IncomingShareFragment_MembersInjector;
import im.vector.app.features.share.IncomingShareViewModel;
import im.vector.app.features.share.IncomingShareViewState;
import im.vector.app.features.signout.hard.SignedOutActivity;
import im.vector.app.features.signout.soft.SoftLogoutActivity;
import im.vector.app.features.signout.soft.SoftLogoutActivity_MembersInjector;
import im.vector.app.features.signout.soft.SoftLogoutController;
import im.vector.app.features.signout.soft.SoftLogoutFragment;
import im.vector.app.features.signout.soft.SoftLogoutFragment_MembersInjector;
import im.vector.app.features.signout.soft.SoftLogoutViewModel;
import im.vector.app.features.signout.soft.SoftLogoutViewState;
import im.vector.app.features.spaces.InviteRoomSpaceChooserBottomSheet;
import im.vector.app.features.spaces.InviteRoomSpaceChooserBottomSheet_MembersInjector;
import im.vector.app.features.spaces.NewSpaceSummaryController;
import im.vector.app.features.spaces.SpaceCreationActivity;
import im.vector.app.features.spaces.SpaceExploreActivity;
import im.vector.app.features.spaces.SpaceListFragment;
import im.vector.app.features.spaces.SpaceListFragment_MembersInjector;
import im.vector.app.features.spaces.SpaceListViewModel;
import im.vector.app.features.spaces.SpaceListViewState;
import im.vector.app.features.spaces.SpaceMenuState;
import im.vector.app.features.spaces.SpaceMenuViewModel;
import im.vector.app.features.spaces.SpacePreviewActivity;
import im.vector.app.features.spaces.SpacePreviewSharedActionViewModel;
import im.vector.app.features.spaces.SpaceSettingsMenuBottomSheet;
import im.vector.app.features.spaces.SpaceSettingsMenuBottomSheet_MembersInjector;
import im.vector.app.features.spaces.SpaceSummaryController;
import im.vector.app.features.spaces.create.ChoosePrivateSpaceTypeFragment;
import im.vector.app.features.spaces.create.ChoosePrivateSpaceTypeFragment_MembersInjector;
import im.vector.app.features.spaces.create.ChooseSpaceTypeFragment;
import im.vector.app.features.spaces.create.CreateSpaceAdd3pidInvitesFragment;
import im.vector.app.features.spaces.create.CreateSpaceAdd3pidInvitesFragment_MembersInjector;
import im.vector.app.features.spaces.create.CreateSpaceDefaultRoomsFragment;
import im.vector.app.features.spaces.create.CreateSpaceDefaultRoomsFragment_MembersInjector;
import im.vector.app.features.spaces.create.CreateSpaceDetailsFragment;
import im.vector.app.features.spaces.create.CreateSpaceDetailsFragment_MembersInjector;
import im.vector.app.features.spaces.create.CreateSpaceState;
import im.vector.app.features.spaces.create.CreateSpaceViewModel;
import im.vector.app.features.spaces.create.CreateSpaceViewModelTask;
import im.vector.app.features.spaces.create.SpaceAdd3pidEpoxyController;
import im.vector.app.features.spaces.create.SpaceDefaultRoomEpoxyController;
import im.vector.app.features.spaces.create.SpaceDetailEpoxyController;
import im.vector.app.features.spaces.explore.SpaceDirectoryController;
import im.vector.app.features.spaces.explore.SpaceDirectoryFragment;
import im.vector.app.features.spaces.explore.SpaceDirectoryFragment_MembersInjector;
import im.vector.app.features.spaces.explore.SpaceDirectoryState;
import im.vector.app.features.spaces.explore.SpaceDirectoryViewModel;
import im.vector.app.features.spaces.invite.SpaceInviteBottomSheet;
import im.vector.app.features.spaces.invite.SpaceInviteBottomSheetState;
import im.vector.app.features.spaces.invite.SpaceInviteBottomSheetViewModel;
import im.vector.app.features.spaces.invite.SpaceInviteBottomSheet_MembersInjector;
import im.vector.app.features.spaces.leave.SelectChildrenController;
import im.vector.app.features.spaces.leave.SpaceLeaveAdvanceViewState;
import im.vector.app.features.spaces.leave.SpaceLeaveAdvancedActivity;
import im.vector.app.features.spaces.leave.SpaceLeaveAdvancedFragment;
import im.vector.app.features.spaces.leave.SpaceLeaveAdvancedFragment_MembersInjector;
import im.vector.app.features.spaces.leave.SpaceLeaveAdvancedViewModel;
import im.vector.app.features.spaces.manage.AddRoomListController;
import im.vector.app.features.spaces.manage.SpaceAddRoomFragment;
import im.vector.app.features.spaces.manage.SpaceAddRoomFragment_MembersInjector;
import im.vector.app.features.spaces.manage.SpaceAddRoomsState;
import im.vector.app.features.spaces.manage.SpaceAddRoomsViewModel;
import im.vector.app.features.spaces.manage.SpaceManageActivity;
import im.vector.app.features.spaces.manage.SpaceManageRoomViewState;
import im.vector.app.features.spaces.manage.SpaceManageRoomsController;
import im.vector.app.features.spaces.manage.SpaceManageRoomsFragment;
import im.vector.app.features.spaces.manage.SpaceManageRoomsFragment_MembersInjector;
import im.vector.app.features.spaces.manage.SpaceManageRoomsViewModel;
import im.vector.app.features.spaces.manage.SpaceManageSharedViewModel;
import im.vector.app.features.spaces.manage.SpaceManageViewState;
import im.vector.app.features.spaces.manage.SpaceSettingsController;
import im.vector.app.features.spaces.manage.SpaceSettingsFragment;
import im.vector.app.features.spaces.manage.SpaceSettingsFragment_MembersInjector;
import im.vector.app.features.spaces.people.SpacePeopleActivity;
import im.vector.app.features.spaces.people.SpacePeopleFragment;
import im.vector.app.features.spaces.people.SpacePeopleFragment_MembersInjector;
import im.vector.app.features.spaces.people.SpacePeopleListController;
import im.vector.app.features.spaces.people.SpacePeopleSharedActionViewModel;
import im.vector.app.features.spaces.people.SpacePeopleViewModel;
import im.vector.app.features.spaces.people.SpacePeopleViewState;
import im.vector.app.features.spaces.preview.SpacePreviewController;
import im.vector.app.features.spaces.preview.SpacePreviewFragment;
import im.vector.app.features.spaces.preview.SpacePreviewFragment_MembersInjector;
import im.vector.app.features.spaces.preview.SpacePreviewState;
import im.vector.app.features.spaces.preview.SpacePreviewViewModel;
import im.vector.app.features.spaces.share.ShareSpaceBottomSheet;
import im.vector.app.features.spaces.share.ShareSpaceViewModel;
import im.vector.app.features.spaces.share.ShareSpaceViewState;
import im.vector.app.features.start.StartAppAndroidService;
import im.vector.app.features.start.StartAppAndroidService_MembersInjector;
import im.vector.app.features.start.StartAppViewModel;
import im.vector.app.features.start.StartAppViewState;
import im.vector.app.features.terms.ReviewTermsActivity;
import im.vector.app.features.terms.ReviewTermsFragment;
import im.vector.app.features.terms.ReviewTermsFragment_MembersInjector;
import im.vector.app.features.terms.ReviewTermsViewModel;
import im.vector.app.features.terms.ReviewTermsViewState;
import im.vector.app.features.terms.TermsController;
import im.vector.app.features.themes.ThemeProvider;
import im.vector.app.features.ui.SharedPreferencesUiStateRepository;
import im.vector.app.features.ui.UiStateRepository;
import im.vector.app.features.usercode.ShowUserCodeFragment;
import im.vector.app.features.usercode.ShowUserCodeFragment_MembersInjector;
import im.vector.app.features.usercode.UserCodeActivity;
import im.vector.app.features.usercode.UserCodeSharedViewModel;
import im.vector.app.features.usercode.UserCodeState;
import im.vector.app.features.userdirectory.UserListController;
import im.vector.app.features.userdirectory.UserListFragment;
import im.vector.app.features.userdirectory.UserListFragment_MembersInjector;
import im.vector.app.features.userdirectory.UserListSharedActionViewModel;
import im.vector.app.features.userdirectory.UserListViewModel;
import im.vector.app.features.userdirectory.UserListViewState;
import im.vector.app.features.version.VersionProvider;
import im.vector.app.features.voice.VoiceRecorderProvider;
import im.vector.app.features.voicebroadcast.VoiceBroadcastHelper;
import im.vector.app.features.voicebroadcast.listening.VoiceBroadcastPlayerImpl;
import im.vector.app.features.voicebroadcast.listening.usecase.GetLiveVoiceBroadcastChunksUseCase;
import im.vector.app.features.voicebroadcast.recording.VoiceBroadcastRecorder;
import im.vector.app.features.voicebroadcast.recording.usecase.PauseVoiceBroadcastUseCase;
import im.vector.app.features.voicebroadcast.recording.usecase.ResumeVoiceBroadcastUseCase;
import im.vector.app.features.voicebroadcast.recording.usecase.StartVoiceBroadcastUseCase;
import im.vector.app.features.voicebroadcast.recording.usecase.StopOngoingVoiceBroadcastUseCase;
import im.vector.app.features.voicebroadcast.recording.usecase.StopVoiceBroadcastUseCase;
import im.vector.app.features.voicebroadcast.usecase.GetRoomLiveVoiceBroadcastsUseCase;
import im.vector.app.features.voicebroadcast.usecase.GetVoiceBroadcastStateEventLiveUseCase;
import im.vector.app.features.voicebroadcast.usecase.GetVoiceBroadcastStateEventUseCase;
import im.vector.app.features.webview.VectorWebViewActivity;
import im.vector.app.features.widgets.WidgetActivity;
import im.vector.app.features.widgets.WidgetArgsBuilder;
import im.vector.app.features.widgets.WidgetFragment;
import im.vector.app.features.widgets.WidgetFragment_MembersInjector;
import im.vector.app.features.widgets.WidgetPostAPIHandler;
import im.vector.app.features.widgets.WidgetViewModel;
import im.vector.app.features.widgets.WidgetViewState;
import im.vector.app.features.widgets.permissions.RoomWidgetPermissionBottomSheet;
import im.vector.app.features.widgets.permissions.RoomWidgetPermissionBottomSheet_MembersInjector;
import im.vector.app.features.widgets.permissions.RoomWidgetPermissionViewModel;
import im.vector.app.features.widgets.permissions.RoomWidgetPermissionViewState;
import im.vector.app.features.widgets.webview.WebviewPermissionUtils;
import im.vector.app.features.workers.signout.ServerBackupStatusViewModel;
import im.vector.app.features.workers.signout.ServerBackupStatusViewState;
import im.vector.app.features.workers.signout.SignOutBottomSheetDialogFragment;
import im.vector.app.features.workers.signout.SignoutCheckViewModel;
import im.vector.app.features.workers.signout.SignoutCheckViewState;
import im.vector.app.flipper.VectorFlipperProxy;
import im.vector.app.gplay.features.settings.troubleshoot.TestFirebaseToken;
import im.vector.app.gplay.features.settings.troubleshoot.TestPlayServices;
import im.vector.app.gplay.features.settings.troubleshoot.TestTokenRegistration;
import im.vector.app.leakcanary.LeakCanaryLeakDetector;
import im.vector.app.nightly.FirebaseNightlyProxy;
import im.vector.app.push.fcm.GoogleFcmHelper;
import im.vector.app.push.fcm.GoogleNotificationTroubleshootTestManagerFactory;
import im.vector.app.push.fcm.VectorFirebaseMessagingService;
import im.vector.app.push.fcm.VectorFirebaseMessagingService_MembersInjector;
import im.vector.app.receivers.VectorDebugReceiver;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineScope;
import okhttp3.OkHttpClient;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.MatrixConfiguration;
import org.matrix.android.sdk.api.auth.AuthenticationService;
import org.matrix.android.sdk.api.auth.HomeServerHistoryService;
import org.matrix.android.sdk.api.legacy.LegacySessionImporter;
import org.matrix.android.sdk.api.raw.RawService;
import org.matrix.android.sdk.api.session.Session;
import org.matrix.android.sdk.api.settings.LightweightSettingsStorage;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DaggerVectorApplication_HiltComponents_SingletonC {
  private DaggerVectorApplication_HiltComponents_SingletonC() {
  }

  public static Builder builder() {
    return new Builder();
  }

  public static final class Builder {
    private ApplicationContextModule applicationContextModule;

    private LoginModule loginModule;

    private Builder() {
    }

    public Builder applicationContextModule(ApplicationContextModule applicationContextModule) {
      this.applicationContextModule = Preconditions.checkNotNull(applicationContextModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder configurationModule(ConfigurationModule configurationModule) {
      Preconditions.checkNotNull(configurationModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule(
        HiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule) {
      Preconditions.checkNotNull(hiltWrapper_FragmentGetContextFix_FragmentGetContextFixModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder lockScreenModule(LockScreenModule lockScreenModule) {
      Preconditions.checkNotNull(lockScreenModule);
      return this;
    }

    public Builder loginModule(LoginModule loginModule) {
      this.loginModule = Preconditions.checkNotNull(loginModule);
      return this;
    }

    /**
     * @deprecated This module is declared, but an instance is not used in the component. This method is a no-op. For more, see https://dagger.dev/unused-modules.
     */
    @Deprecated
    public Builder vectorStaticModule(VectorStaticModule vectorStaticModule) {
      Preconditions.checkNotNull(vectorStaticModule);
      return this;
    }

    public VectorApplication_HiltComponents.SingletonC build() {
      Preconditions.checkBuilderRequirement(applicationContextModule, ApplicationContextModule.class);
      if (loginModule == null) {
        this.loginModule = new LoginModule();
      }
      return new SingletonCImpl(applicationContextModule, loginModule);
    }
  }

  private static final class ActivityRetainedCBuilder implements VectorApplication_HiltComponents.ActivityRetainedC.Builder {
    private final SingletonCImpl singletonCImpl;

    private ActivityRetainedCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public VectorApplication_HiltComponents.ActivityRetainedC build() {
      return new ActivityRetainedCImpl(singletonCImpl);
    }
  }

  private static final class ActivityCBuilder implements VectorApplication_HiltComponents.ActivityC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private Activity activity;

    private ActivityCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ActivityCBuilder activity(Activity activity) {
      this.activity = Preconditions.checkNotNull(activity);
      return this;
    }

    @Override
    public VectorApplication_HiltComponents.ActivityC build() {
      Preconditions.checkBuilderRequirement(activity, Activity.class);
      return new ActivityCImpl(singletonCImpl, activityRetainedCImpl, activity);
    }
  }

  private static final class FragmentCBuilder implements VectorApplication_HiltComponents.FragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private Fragment fragment;

    private FragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public FragmentCBuilder fragment(Fragment fragment) {
      this.fragment = Preconditions.checkNotNull(fragment);
      return this;
    }

    @Override
    public VectorApplication_HiltComponents.FragmentC build() {
      Preconditions.checkBuilderRequirement(fragment, Fragment.class);
      return new FragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragment);
    }
  }

  private static final class ViewWithFragmentCBuilder implements VectorApplication_HiltComponents.ViewWithFragmentC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private View view;

    private ViewWithFragmentCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;
    }

    @Override
    public ViewWithFragmentCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VectorApplication_HiltComponents.ViewWithFragmentC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewWithFragmentCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, view);
    }
  }

  private static final class ViewCBuilder implements VectorApplication_HiltComponents.ViewC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private View view;

    private ViewCBuilder(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
    }

    @Override
    public ViewCBuilder view(View view) {
      this.view = Preconditions.checkNotNull(view);
      return this;
    }

    @Override
    public VectorApplication_HiltComponents.ViewC build() {
      Preconditions.checkBuilderRequirement(view, View.class);
      return new ViewCImpl(singletonCImpl, activityRetainedCImpl, activityCImpl, view);
    }
  }

  private static final class ViewModelCBuilder implements VectorApplication_HiltComponents.ViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private SavedStateHandle savedStateHandle;

    private ViewModelLifecycle viewModelLifecycle;

    private ViewModelCBuilder(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
    }

    @Override
    public ViewModelCBuilder savedStateHandle(SavedStateHandle handle) {
      this.savedStateHandle = Preconditions.checkNotNull(handle);
      return this;
    }

    @Override
    public ViewModelCBuilder viewModelLifecycle(ViewModelLifecycle viewModelLifecycle) {
      this.viewModelLifecycle = Preconditions.checkNotNull(viewModelLifecycle);
      return this;
    }

    @Override
    public VectorApplication_HiltComponents.ViewModelC build() {
      Preconditions.checkBuilderRequirement(savedStateHandle, SavedStateHandle.class);
      Preconditions.checkBuilderRequirement(viewModelLifecycle, ViewModelLifecycle.class);
      return new ViewModelCImpl(singletonCImpl, activityRetainedCImpl, savedStateHandle, viewModelLifecycle);
    }
  }

  private static final class ServiceCBuilder implements VectorApplication_HiltComponents.ServiceC.Builder {
    private final SingletonCImpl singletonCImpl;

    private Service service;

    private ServiceCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public ServiceCBuilder service(Service service) {
      this.service = Preconditions.checkNotNull(service);
      return this;
    }

    @Override
    public VectorApplication_HiltComponents.ServiceC build() {
      Preconditions.checkBuilderRequirement(service, Service.class);
      return new ServiceCImpl(singletonCImpl, service);
    }
  }

  private static final class MavericksViewModelCBuilder implements VectorApplication_HiltComponents.MavericksViewModelC.Builder {
    private final SingletonCImpl singletonCImpl;

    private MavericksViewModelCBuilder(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;
    }

    @Override
    public VectorApplication_HiltComponents.MavericksViewModelC build() {
      return new MavericksViewModelCImpl(singletonCImpl);
    }
  }

  private static final class ViewWithFragmentCImpl extends VectorApplication_HiltComponents.ViewWithFragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl;

    private final ViewWithFragmentCImpl viewWithFragmentCImpl = this;

    private ViewWithFragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        FragmentCImpl fragmentCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;
      this.fragmentCImpl = fragmentCImpl;


    }
  }

  private static final class FragmentCImpl extends VectorApplication_HiltComponents.FragmentC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final FragmentCImpl fragmentCImpl = this;

    private Provider<EventTextRenderer.Factory> factoryProvider;

    private Provider<PillsPostProcessor.Factory> factoryProvider2;

    private Provider<AutocompleteCommandPresenter.Factory> factoryProvider3;

    private Provider<AutocompleteMemberPresenter.Factory> factoryProvider4;

    private Provider<AutoCompleter.Factory> factoryProvider5;

    private Provider<ThreadListViewModel.Factory> factoryProvider6;

    private Provider<RoomNotificationSettingsViewModel.Factory> factoryProvider7;

    private Provider<BiometricHelper.BiometricHelperFactory> biometricHelperFactoryProvider;

    private FragmentCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, ActivityCImpl activityCImpl,
        Fragment fragmentParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;

      initialize(fragmentParam);

    }

    private ContactsBookController contactsBookController() {
      return new ContactsBookController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.defaultErrorFormatter());
    }

    private KeysBackupSettingsRecyclerViewController keysBackupSettingsRecyclerViewController() {
      return new KeysBackupSettingsRecyclerViewController(singletonCImpl.stringProvider(), singletonCImpl.vectorPreferences(), singletonCImpl.session());
    }

    private VerificationCancelController verificationCancelController() {
      return new VerificationCancelController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private VerificationNotMeController verificationNotMeController() {
      return new VerificationNotMeController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.eventHtmlRendererProvider.get());
    }

    private VerificationChooseMethodController verificationChooseMethodController() {
      return new VerificationChooseMethodController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private VerificationConclusionController verificationConclusionController() {
      return new VerificationConclusionController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.eventHtmlRendererProvider.get());
    }

    private VerificationEmojiCodeController verificationEmojiCodeController() {
      return new VerificationEmojiCodeController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.defaultErrorFormatter());
    }

    private VerificationQRWaitingController verificationQRWaitingController() {
      return new VerificationQRWaitingController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private VerificationQrScannedByOtherController verificationQrScannedByOtherController() {
      return new VerificationQrScannedByOtherController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private VerificationRequestController verificationRequestController() {
      return new VerificationRequestController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private RoomDevToolRootController roomDevToolRootController() {
      return new RoomDevToolRootController(singletonCImpl.stringProvider());
    }

    private RoomDevToolSendFormController roomDevToolSendFormController() {
      return new RoomDevToolSendFormController(singletonCImpl.stringProvider());
    }

    private RoomStateListController roomStateListController() {
      return new RoomStateListController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private DiscoverySettingsController discoverySettingsController() {
      return new DiscoverySettingsController(singletonCImpl.colorProvider(), singletonCImpl.stringProvider(), singletonCImpl.defaultErrorFormatter());
    }

    private PermalinkFactory permalinkFactory() {
      return new PermalinkFactory(singletonCImpl.session());
    }

    private BreadcrumbsController breadcrumbsController() {
      return new BreadcrumbsController(singletonCImpl.avatarRendererProvider.get());
    }

    private TimelineLayoutSettingsProvider timelineLayoutSettingsProvider() {
      return new TimelineLayoutSettingsProvider(singletonCImpl.vectorPreferences());
    }

    private TimelineMessageLayoutFactory timelineMessageLayoutFactory() {
      return new TimelineMessageLayoutFactory(singletonCImpl.session(), timelineLayoutSettingsProvider(), singletonCImpl.defaultLocaleProvider(), singletonCImpl.resources(), singletonCImpl.vectorPreferences());
    }

    private PollResponseDataFactory pollResponseDataFactory() {
      return new PollResponseDataFactory(singletonCImpl.activeSessionHolderProvider.get());
    }

    private MessageInformationDataFactory messageInformationDataFactory() {
      return new MessageInformationDataFactory(singletonCImpl.session(), singletonCImpl.vectorDateFormatter(), timelineMessageLayoutFactory(), activityCImpl.reactionsSummaryFactoryProvider.get(), pollResponseDataFactory());
    }

    private AvatarSizeProvider avatarSizeProvider() {
      return new AvatarSizeProvider(singletonCImpl.dimensionConverter(), timelineLayoutSettingsProvider());
    }

    private MessageItemAttributesFactory messageItemAttributesFactory() {
      return new MessageItemAttributesFactory(singletonCImpl.avatarRendererProvider.get(), activityCImpl.messageColorProvider(), avatarSizeProvider(), singletonCImpl.stringProvider(), singletonCImpl.displayableEventFormatter(), activityCImpl.userPreferencesProvider(), singletonCImpl.emojiCompatFontProvider.get());
    }

    private DefaultItemFactory defaultItemFactory() {
      return new DefaultItemFactory(avatarSizeProvider(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider(), messageInformationDataFactory());
    }

    private NoticeItemFactory noticeItemFactory() {
      return new NoticeItemFactory(singletonCImpl.noticeEventFormatter(), singletonCImpl.avatarRendererProvider.get(), messageInformationDataFactory(), avatarSizeProvider());
    }

    private SpanUtils spanUtils() {
      return new SpanUtils(singletonCImpl.emojiCompatWrapperProvider.get());
    }

    private UrlMapProvider urlMapProvider() {
      return new UrlMapProvider(singletonCImpl.session(), singletonCImpl.rawService(), ConfigurationModule_ProvidesLocationSharingConfigFactory.providesLocationSharingConfig());
    }

    private LiveLocationShareMessageItemFactory liveLocationShareMessageItemFactory() {
      return new LiveLocationShareMessageItemFactory(singletonCImpl.session(), singletonCImpl.dimensionConverter(), activityCImpl.timelineMediaSizeProvider.get(), avatarSizeProvider(), urlMapProvider(), singletonCImpl.locationPinProvider.get(), singletonCImpl.vectorDateFormatter());
    }

    private PollItemViewStateFactory pollItemViewStateFactory() {
      return new PollItemViewStateFactory(singletonCImpl.stringProvider(), new PollOptionViewStateFactory());
    }

    private VoiceBroadcastItemFactory voiceBroadcastItemFactory() {
      return new VoiceBroadcastItemFactory(singletonCImpl.session(), avatarSizeProvider(), singletonCImpl.colorProvider(), singletonCImpl.drawableProvider(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.providesVoiceBroadcastRecorderProvider.get(), singletonCImpl.voiceBroadcastPlayerImplProvider.get(), singletonCImpl.audioMessagePlaybackTrackerProvider.get(), noticeItemFactory());
    }

    private ProcessBodyOfReplyToEventUseCase processBodyOfReplyToEventUseCase() {
      return new ProcessBodyOfReplyToEventUseCase(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.stringProvider());
    }

    private MessageItemFactory messageItemFactory() {
      return new MessageItemFactory(activityCImpl.localFilesHelper(), singletonCImpl.colorProvider(), singletonCImpl.dimensionConverter(), activityCImpl.timelineMediaSizeProvider.get(), DoubleCheck.lazy(singletonCImpl.eventHtmlRendererProvider), singletonCImpl.vectorHtmlCompressorProvider.get(), factoryProvider.get(), singletonCImpl.stringProvider(), activityCImpl.imageContentRenderer(), messageInformationDataFactory(), messageItemAttributesFactory(), activityCImpl.contentUploadStateTrackerBinderProvider.get(), activityCImpl.contentDownloadStateTrackerBinderProvider.get(), defaultItemFactory(), noticeItemFactory(), avatarSizeProvider(), factoryProvider2.get(), singletonCImpl.lightweightSettingsStorage(), spanUtils(), singletonCImpl.session(), new DefaultClock(), singletonCImpl.audioMessagePlaybackTrackerProvider.get(), singletonCImpl.locationPinProvider.get(), singletonCImpl.vectorPreferences(), urlMapProvider(), liveLocationShareMessageItemFactory(), pollItemViewStateFactory(), voiceBroadcastItemFactory(), processBodyOfReplyToEventUseCase());
    }

    private EncryptedItemFactory encryptedItemFactory() {
      return new EncryptedItemFactory(messageInformationDataFactory(), singletonCImpl.colorProvider(), singletonCImpl.stringProvider(), avatarSizeProvider(), singletonCImpl.drawableProvider(), messageItemAttributesFactory(), singletonCImpl.vectorPreferences());
    }

    private EncryptionItemFactory encryptionItemFactory() {
      return new EncryptionItemFactory(messageItemAttributesFactory(), activityCImpl.messageColorProvider(), singletonCImpl.stringProvider(), messageInformationDataFactory(), avatarSizeProvider(), singletonCImpl.session());
    }

    private RoomCreateItemFactory roomCreateItemFactory() {
      return new RoomCreateItemFactory(singletonCImpl.stringProvider(), activityCImpl.userPreferencesProvider(), singletonCImpl.session(), noticeItemFactory());
    }

    private WidgetItemFactory widgetItemFactory() {
      return new WidgetItemFactory(messageInformationDataFactory(), noticeItemFactory(), avatarSizeProvider(), activityCImpl.messageColorProvider(), singletonCImpl.avatarRendererProvider.get(), activityCImpl.userPreferencesProvider());
    }

    private VerificationItemFactory verificationItemFactory() {
      return new VerificationItemFactory(activityCImpl.messageColorProvider(), messageInformationDataFactory(), messageItemAttributesFactory(), avatarSizeProvider(), noticeItemFactory(), activityCImpl.userPreferencesProvider(), singletonCImpl.stringProvider(), singletonCImpl.session());
    }

    private CallItemFactory callItemFactory() {
      return new CallItemFactory(singletonCImpl.session(), activityCImpl.userPreferencesProvider(), activityCImpl.messageColorProvider(), messageInformationDataFactory(), messageItemAttributesFactory(), avatarSizeProvider(), noticeItemFactory());
    }

    private TimelineEventVisibilityHelper timelineEventVisibilityHelper() {
      return new TimelineEventVisibilityHelper(activityCImpl.userPreferencesProvider());
    }

    private TimelineItemFactory timelineItemFactory() {
      return new TimelineItemFactory(messageItemFactory(), encryptedItemFactory(), noticeItemFactory(), defaultItemFactory(), encryptionItemFactory(), roomCreateItemFactory(), widgetItemFactory(), verificationItemFactory(), callItemFactory(), singletonCImpl.decryptionFailureTrackerProvider.get(), timelineEventVisibilityHelper());
    }

    private MergedHeaderItemFactory mergedHeaderItemFactory() {
      return new MergedHeaderItemFactory(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.avatarRendererProvider.get(), avatarSizeProvider(), timelineEventVisibilityHelper());
    }

    private ReadReceiptsItemFactory readReceiptsItemFactory() {
      return new ReadReceiptsItemFactory(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.session());
    }

    private TimelineEventController timelineEventController() {
      return new TimelineEventController(singletonCImpl.vectorDateFormatter(), singletonCImpl.vectorPreferences(), activityCImpl.contentUploadStateTrackerBinderProvider.get(), activityCImpl.contentDownloadStateTrackerBinderProvider.get(), timelineItemFactory(), activityCImpl.timelineMediaSizeProvider.get(), mergedHeaderItemFactory(), singletonCImpl.session(), HomeModule_ProvidesTimelineBackgroundHandlerFactory.providesTimelineBackgroundHandler(), timelineEventVisibilityHelper(), readReceiptsItemFactory(), activityCImpl.reactionsSummaryFactoryProvider.get(), new DefaultClock(), singletonCImpl.avatarRendererProvider.get());
    }

    private ThreadsManager threadsManager() {
      return new ThreadsManager(singletonCImpl.vectorPreferences(), singletonCImpl.lightweightSettingsStorage(), singletonCImpl.stringProvider());
    }

    private MultiPickerIncomingFiles multiPickerIncomingFiles() {
      return new MultiPickerIncomingFiles(singletonCImpl.context());
    }

    private ShareIntentHandler shareIntentHandler() {
      return new ShareIntentHandler(multiPickerIncomingFiles(), singletonCImpl.context());
    }

    private GalleryOrCameraDialogHelperFactory galleryOrCameraDialogHelperFactory() {
      return new GalleryOrCameraDialogHelperFactory(singletonCImpl.colorProvider(), new DefaultClock());
    }

    private AutocompleteCommandController autocompleteCommandController() {
      return new AutocompleteCommandController(singletonCImpl.stringProvider());
    }

    private AutocompleteMemberController autocompleteMemberController() {
      return injectAutocompleteMemberController(AutocompleteMemberController_Factory.newInstance(singletonCImpl.context()));
    }

    private AutocompleteRoomController autocompleteRoomController() {
      return new AutocompleteRoomController(singletonCImpl.avatarRendererProvider.get());
    }

    private AutocompleteRoomPresenter autocompleteRoomPresenter() {
      return new AutocompleteRoomPresenter(singletonCImpl.context(), autocompleteRoomController(), singletonCImpl.session());
    }

    private AutocompleteEmojiController autocompleteEmojiController() {
      return new AutocompleteEmojiController(singletonCImpl.emojiCompatFontProvider.get());
    }

    private AutocompleteEmojiPresenter autocompleteEmojiPresenter() {
      return new AutocompleteEmojiPresenter(singletonCImpl.context(), singletonCImpl.emojiDataSourceProvider.get(), autocompleteEmojiController());
    }

    private DisplayReadReceiptsController displayReadReceiptsController() {
      return new DisplayReadReceiptsController(singletonCImpl.vectorDateFormatter(), singletonCImpl.session(), singletonCImpl.avatarRendererProvider.get());
    }

    private SearchResultController searchResultController() {
      return new SearchResultController(singletonCImpl.session(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.vectorDateFormatter(), singletonCImpl.displayableEventFormatter(), activityCImpl.userPreferencesProvider(), new DefaultClock());
    }

    private EventDetailsFormatter eventDetailsFormatter() {
      return new EventDetailsFormatter(singletonCImpl.context());
    }

    private MessageActionsEpoxyController messageActionsEpoxyController() {
      return new MessageActionsEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.emojiCompatFontProvider.get(), activityCImpl.imageContentRenderer(), singletonCImpl.dimensionConverter(), singletonCImpl.defaultErrorFormatter(), spanUtils(), eventDetailsFormatter(), singletonCImpl.vectorPreferences(), singletonCImpl.vectorDateFormatter(), urlMapProvider(), singletonCImpl.locationPinProvider.get());
    }

    private ViewEditHistoryEpoxyController viewEditHistoryEpoxyController() {
      return new ViewEditHistoryEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.eventHtmlRendererProvider.get(), singletonCImpl.vectorDateFormatter(), new DefaultClock());
    }

    private ViewReactionsEpoxyController viewReactionsEpoxyController() {
      return new ViewReactionsEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.emojiCompatWrapperProvider.get());
    }

    private RoomWidgetsController roomWidgetsController() {
      return new RoomWidgetsController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private TypingHelper typingHelper() {
      return new TypingHelper(singletonCImpl.stringProvider());
    }

    private GetRoomLiveVoiceBroadcastsUseCase getRoomLiveVoiceBroadcastsUseCase() {
      return new GetRoomLiveVoiceBroadcastsUseCase(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.getVoiceBroadcastStateEventUseCase());
    }

    private GetLatestPreviewableEventUseCase getLatestPreviewableEventUseCase() {
      return new GetLatestPreviewableEventUseCase(singletonCImpl.activeSessionHolderProvider.get(), getRoomLiveVoiceBroadcastsUseCase(), singletonCImpl.vectorPreferences());
    }

    private RoomSummaryItemFactory roomSummaryItemFactory() {
      return new RoomSummaryItemFactory(singletonCImpl.displayableEventFormatter(), singletonCImpl.vectorDateFormatter(), singletonCImpl.stringProvider(), typingHelper(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.defaultErrorFormatter(), getLatestPreviewableEventUseCase());
    }

    private RoomSummaryPagedControllerFactory roomSummaryPagedControllerFactory() {
      return new RoomSummaryPagedControllerFactory(roomSummaryItemFactory(), singletonCImpl.fontScalePreferencesImpl());
    }

    private RoomListFooterController roomListFooterController() {
      return new RoomListFooterController(singletonCImpl.stringProvider(), activityCImpl.userPreferencesProvider());
    }

    private RoomListQuickActionsEpoxyController roomListQuickActionsEpoxyController() {
      return new RoomListQuickActionsEpoxyController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.colorProvider(), singletonCImpl.stringProvider());
    }

    private HomeRoomsHeadersController homeRoomsHeadersController() {
      return new HomeRoomsHeadersController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.resources(), singletonCImpl.defaultVectorAnalyticsProvider.get());
    }

    private HomeFilteredRoomsController homeFilteredRoomsController() {
      return new HomeFilteredRoomsController(roomSummaryItemFactory(), singletonCImpl.fontScalePreferencesImpl());
    }

    private InvitesController invitesController() {
      return new InvitesController(roomSummaryItemFactory());
    }

    private HomeLayoutPreferencesStore homeLayoutPreferencesStore() {
      return new HomeLayoutPreferencesStore(singletonCImpl.context());
    }

    private ThreadListPagedController threadListPagedController() {
      return new ThreadListPagedController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.vectorDateFormatter(), singletonCImpl.displayableEventFormatter());
    }

    private ThreadListController threadListController() {
      return new ThreadListController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.vectorDateFormatter(), singletonCImpl.displayableEventFormatter());
    }

    private LiveLocationBottomSheetController liveLocationBottomSheetController() {
      return new LiveLocationBottomSheetController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.vectorDateFormatter(), singletonCImpl.stringProvider(), new DefaultClock());
    }

    private SpaceCardRenderer spaceCardRenderer() {
      return new SpaceCardRenderer(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider());
    }

    private CaptchaWebview captchaWebview() {
      return new CaptchaWebview(activityCImpl.assetReader());
    }

    private LoginFieldsValidation loginFieldsValidation() {
      return new LoginFieldsValidation(singletonCImpl.stringProvider());
    }

    private LoginErrorParser loginErrorParser() {
      return new LoginErrorParser(singletonCImpl.defaultErrorFormatter(), singletonCImpl.stringProvider());
    }

    private PhoneNumberParser phoneNumberParser() {
      return new PhoneNumberParser(VectorStaticModule_ProvidesPhoneNumberUtilFactory.providesPhoneNumberUtil());
    }

    private SplashCarouselStateFactory splashCarouselStateFactory() {
      return new SplashCarouselStateFactory(singletonCImpl.context(), singletonCImpl.stringProvider(), singletonCImpl.defaultLocaleProvider(), singletonCImpl.themeProvider());
    }

    private CreatePollController createPollController() {
      return new CreatePollController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private EmojiSearchResultController emojiSearchResultController() {
      return new EmojiSearchResultController(singletonCImpl.stringProvider(), singletonCImpl.emojiCompatFontProvider.get());
    }

    private PublicRoomsController publicRoomsController() {
      return new PublicRoomsController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.defaultErrorFormatter());
    }

    private RoomAliasErrorFormatter roomAliasErrorFormatter() {
      return new RoomAliasErrorFormatter(singletonCImpl.stringProvider());
    }

    private CreateRoomController createRoomController() {
      return new CreateRoomController(singletonCImpl.stringProvider(), roomAliasErrorFormatter());
    }

    private CreateSubSpaceController createSubSpaceController() {
      return new CreateSubSpaceController(singletonCImpl.stringProvider(), roomAliasErrorFormatter());
    }

    private RoomDirectoryPickerController roomDirectoryPickerController() {
      return new RoomDirectoryPickerController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.dimensionConverter(), singletonCImpl.defaultErrorFormatter());
    }

    private RoomMemberProfileController roomMemberProfileController() {
      return new RoomMemberProfileController(singletonCImpl.stringProvider(), singletonCImpl.session());
    }

    private DeviceListEpoxyController deviceListEpoxyController() {
      return new DeviceListEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.dimensionConverter(), singletonCImpl.vectorPreferences());
    }

    private DeviceTrustInfoEpoxyController deviceTrustInfoEpoxyController() {
      return new DeviceTrustInfoEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.dimensionConverter(), singletonCImpl.vectorPreferences());
    }

    private RoomProfileController roomProfileController() {
      return new RoomProfileController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.vectorPreferences(), singletonCImpl.drawableProvider(), activityCImpl.shortcutCreator());
    }

    private RoomAliasController roomAliasController() {
      return new RoomAliasController(singletonCImpl.stringProvider(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.colorProvider(), roomAliasErrorFormatter());
    }

    private RoomBannedMemberListController roomBannedMemberListController() {
      return new RoomBannedMemberListController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider(), new RoomMemberSummaryFilter());
    }

    private RoomMemberListController roomMemberListController() {
      return new RoomMemberListController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), new RoomMemberSummaryFilter());
    }

    private RoomPermissionsController roomPermissionsController() {
      return new RoomPermissionsController(singletonCImpl.stringProvider(), singletonCImpl.roleFormatter());
    }

    private RoomPollsController roomPollsController() {
      return new RoomPollsController(singletonCImpl.vectorDateFormatter(), singletonCImpl.stringProvider());
    }

    private RoomSettingsController roomSettingsController() {
      return new RoomSettingsController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.dimensionConverter(), singletonCImpl.roomHistoryVisibilityFormatter(), singletonCImpl.vectorPreferences());
    }

    private RoomHistoryVisibilityController roomHistoryVisibilityController() {
      return new RoomHistoryVisibilityController(singletonCImpl.roomHistoryVisibilityFormatter(), singletonCImpl.stringProvider());
    }

    private RoomJoinRuleController roomJoinRuleController() {
      return new RoomJoinRuleController(singletonCImpl.stringProvider(), singletonCImpl.drawableProvider());
    }

    private RoomJoinRuleAdvancedController roomJoinRuleAdvancedController() {
      return new RoomJoinRuleAdvancedController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.avatarRendererProvider.get());
    }

    private ChooseRestrictedController chooseRestrictedController() {
      return new ChooseRestrictedController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get());
    }

    private UploadsFileController uploadsFileController() {
      return new UploadsFileController(singletonCImpl.stringProvider(), singletonCImpl.vectorDateFormatter());
    }

    private UploadsMediaController uploadsMediaController() {
      return new UploadsMediaController(singletonCImpl.defaultErrorFormatter(), activityCImpl.imageContentRenderer(), singletonCImpl.stringProvider(), singletonCImpl.dimensionConverter());
    }

    private KeysImporter keysImporter() {
      return new KeysImporter(singletonCImpl.context(), singletonCImpl.session());
    }

    private RingtoneUtils ringtoneUtils() {
      return new RingtoneUtils(singletonCImpl.providesDefaultSharedPreferencesProvider.get(), singletonCImpl.context());
    }

    private CrossSigningSettingsController crossSigningSettingsController() {
      return new CrossSigningSettingsController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.dimensionConverter());
    }

    private DeviceVerificationInfoBottomSheetController deviceVerificationInfoBottomSheetController(
        ) {
      return new DeviceVerificationInfoBottomSheetController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private DevicesController devicesController() {
      return new DevicesController(singletonCImpl.defaultErrorFormatter(), singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.vectorDateFormatter(), singletonCImpl.dimensionConverter(), singletonCImpl.vectorPreferences());
    }

    private SessionDetailsController sessionDetailsController() {
      return new SessionDetailsController(new CheckIfSectionSessionIsVisibleUseCase(), new CheckIfSectionDeviceIsVisibleUseCase(), new CheckIfSectionApplicationIsVisibleUseCase(), singletonCImpl.stringProvider(), singletonCImpl.vectorDateFormatter(), singletonCImpl.dimensionConverter());
    }

    private AccountDataEpoxyController accountDataEpoxyController() {
      return new AccountDataEpoxyController(singletonCImpl.stringProvider());
    }

    private GossipingTrailPagedEpoxyController gossipingTrailPagedEpoxyController() {
      return new GossipingTrailPagedEpoxyController(singletonCImpl.vectorDateFormatter(), singletonCImpl.colorProvider());
    }

    private IncomingKeyRequestPagedController incomingKeyRequestPagedController() {
      return new IncomingKeyRequestPagedController(singletonCImpl.vectorDateFormatter());
    }

    private FontScaleSettingController fontScaleSettingController() {
      return new FontScaleSettingController(singletonCImpl.stringProvider());
    }

    private HomeserverSettingsController homeserverSettingsController() {
      return new HomeserverSettingsController(singletonCImpl.stringProvider(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.vectorPreferences());
    }

    private IgnoredUsersController ignoredUsersController() {
      return new IgnoredUsersController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get());
    }

    private ElementLegals elementLegals() {
      return new ElementLegals(singletonCImpl.stringProvider());
    }

    private LegalsController legalsController() {
      return new LegalsController(singletonCImpl.stringProvider(), singletonCImpl.resources(), elementLegals(), singletonCImpl.defaultErrorFormatter(), new GoogleFlavorLegals());
    }

    private LocalePickerController localePickerController() {
      return new LocalePickerController(singletonCImpl.vectorPreferences(), singletonCImpl.stringProvider(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.vectorLocaleProvider.get());
    }

    private EnsureFcmTokenIsRetrievedUseCase ensureFcmTokenIsRetrievedUseCase() {
      return new EnsureFcmTokenIsRetrievedUseCase(singletonCImpl.unifiedPushHelper(), singletonCImpl.googleFcmHelper(), singletonCImpl.activeSessionHolderProvider.get());
    }

    private PushGateWayController pushGateWayController() {
      return new PushGateWayController(singletonCImpl.stringProvider());
    }

    private PushRulesController pushRulesController() {
      return new PushRulesController(singletonCImpl.stringProvider());
    }

    private ThreePidsSettingsController threePidsSettingsController() {
      return new ThreePidsSettingsController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.defaultErrorFormatter());
    }

    private IncomingShareController incomingShareController() {
      return new IncomingShareController(roomSummaryItemFactory(), singletonCImpl.stringProvider());
    }

    private SoftLogoutController softLogoutController() {
      return new SoftLogoutController(singletonCImpl.stringProvider(), singletonCImpl.defaultErrorFormatter());
    }

    private SpaceSummaryController spaceSummaryController() {
      return new SpaceSummaryController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider());
    }

    private NewSpaceSummaryController newSpaceSummaryController() {
      return new NewSpaceSummaryController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider());
    }

    private SpaceAdd3pidEpoxyController spaceAdd3pidEpoxyController() {
      return new SpaceAdd3pidEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private SpaceDefaultRoomEpoxyController spaceDefaultRoomEpoxyController() {
      return new SpaceDefaultRoomEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.colorProvider());
    }

    private SpaceDetailEpoxyController spaceDetailEpoxyController() {
      return new SpaceDetailEpoxyController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get(), roomAliasErrorFormatter());
    }

    private SpaceDirectoryController spaceDirectoryController() {
      return new SpaceDirectoryController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.defaultErrorFormatter());
    }

    private SelectChildrenController selectChildrenController() {
      return new SelectChildrenController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider());
    }

    private AddRoomListController addRoomListController() {
      return new AddRoomListController(singletonCImpl.avatarRendererProvider.get());
    }

    private SpaceManageRoomsController spaceManageRoomsController() {
      return new SpaceManageRoomsController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.stringProvider());
    }

    private SpaceSettingsController spaceSettingsController() {
      return new SpaceSettingsController(singletonCImpl.stringProvider(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.vectorPreferences());
    }

    private SpacePeopleListController spacePeopleListController() {
      return new SpacePeopleListController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.colorProvider(), singletonCImpl.stringProvider(), singletonCImpl.dimensionConverter(), new RoomMemberSummaryFilter());
    }

    private SpacePreviewController spacePreviewController() {
      return new SpacePreviewController(singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider());
    }

    private TermsController termsController() {
      return new TermsController(singletonCImpl.defaultErrorFormatter());
    }

    private UserListController userListController() {
      return new UserListController(singletonCImpl.session(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.colorProvider(), singletonCImpl.defaultErrorFormatter());
    }

    private WebviewPermissionUtils webviewPermissionUtils() {
      return new WebviewPermissionUtils(singletonCImpl.vectorPreferences());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final Fragment fragmentParam) {
      this.factoryProvider = SingleCheck.provider(new SwitchingProvider<EventTextRenderer.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 0));
      this.factoryProvider2 = SingleCheck.provider(new SwitchingProvider<PillsPostProcessor.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 1));
      this.factoryProvider3 = SingleCheck.provider(new SwitchingProvider<AutocompleteCommandPresenter.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 3));
      this.factoryProvider4 = SingleCheck.provider(new SwitchingProvider<AutocompleteMemberPresenter.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 4));
      this.factoryProvider5 = SingleCheck.provider(new SwitchingProvider<AutoCompleter.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 2));
      this.factoryProvider6 = SingleCheck.provider(new SwitchingProvider<ThreadListViewModel.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 5));
      this.factoryProvider7 = SingleCheck.provider(new SwitchingProvider<RoomNotificationSettingsViewModel.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 6));
      this.biometricHelperFactoryProvider = SingleCheck.provider(new SwitchingProvider<BiometricHelper.BiometricHelperFactory>(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl, 7));
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return activityCImpl.getHiltInternalFactoryFactory();
    }

    @Override
    public ViewWithFragmentComponentBuilder viewWithFragmentComponentBuilder() {
      return new ViewWithFragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl, fragmentCImpl);
    }

    @Override
    public void injectAnalyticsOptInFragment(AnalyticsOptInFragment arg0) {
      injectAnalyticsOptInFragment2(arg0);
    }

    @Override
    public void injectAttachmentTypeSelectorBottomSheet(AttachmentTypeSelectorBottomSheet arg0) {
    }

    @Override
    public void injectAttachmentsPreviewFragment(AttachmentsPreviewFragment arg0) {
      injectAttachmentsPreviewFragment2(arg0);
    }

    @Override
    public void injectCallControlsBottomSheet(CallControlsBottomSheet arg0) {
      injectCallControlsBottomSheet2(arg0);
    }

    @Override
    public void injectCallDialPadBottomSheet(CallDialPadBottomSheet arg0) {
      injectCallDialPadBottomSheet2(arg0);
    }

    @Override
    public void injectContactsBookFragment(ContactsBookFragment arg0) {
      injectContactsBookFragment2(arg0);
    }

    @Override
    public void injectKeysBackupRestoreFromKeyFragment(KeysBackupRestoreFromKeyFragment arg0) {
    }

    @Override
    public void injectKeysBackupRestoreFromPassphraseFragment(
        KeysBackupRestoreFromPassphraseFragment arg0) {
    }

    @Override
    public void injectKeysBackupRestoreSuccessFragment(KeysBackupRestoreSuccessFragment arg0) {
    }

    @Override
    public void injectKeysBackupSettingsFragment(KeysBackupSettingsFragment arg0) {
      injectKeysBackupSettingsFragment2(arg0);
    }

    @Override
    public void injectKeysBackupSetupStep1Fragment(KeysBackupSetupStep1Fragment arg0) {
    }

    @Override
    public void injectKeysBackupSetupStep2Fragment(KeysBackupSetupStep2Fragment arg0) {
      injectKeysBackupSetupStep2Fragment2(arg0);
    }

    @Override
    public void injectKeysBackupSetupStep3Fragment(KeysBackupSetupStep3Fragment arg0) {
    }

    @Override
    public void injectSharedSecuredStorageKeyFragment(SharedSecuredStorageKeyFragment arg0) {
    }

    @Override
    public void injectSharedSecuredStoragePassphraseFragment(
        SharedSecuredStoragePassphraseFragment arg0) {
    }

    @Override
    public void injectSharedSecuredStorageResetAllFragment(
        SharedSecuredStorageResetAllFragment arg0) {
    }

    @Override
    public void injectBootstrapBottomSheet(BootstrapBottomSheet arg0) {
    }

    @Override
    public void injectBootstrapConclusionFragment(BootstrapConclusionFragment arg0) {
      injectBootstrapConclusionFragment2(arg0);
    }

    @Override
    public void injectBootstrapConfirmPassphraseFragment(BootstrapConfirmPassphraseFragment arg0) {
    }

    @Override
    public void injectBootstrapEnterPassphraseFragment(BootstrapEnterPassphraseFragment arg0) {
      injectBootstrapEnterPassphraseFragment2(arg0);
    }

    @Override
    public void injectBootstrapMigrateBackupFragment(BootstrapMigrateBackupFragment arg0) {
      injectBootstrapMigrateBackupFragment2(arg0);
    }

    @Override
    public void injectBootstrapReAuthFragment(BootstrapReAuthFragment arg0) {
    }

    @Override
    public void injectBootstrapSaveRecoveryKeyFragment(BootstrapSaveRecoveryKeyFragment arg0) {
    }

    @Override
    public void injectBootstrapSetupRecoveryKeyFragment(BootstrapSetupRecoveryKeyFragment arg0) {
    }

    @Override
    public void injectBootstrapWaitingFragment(BootstrapWaitingFragment arg0) {
    }

    @Override
    public void injectQuadSLoadingFragment(QuadSLoadingFragment arg0) {
    }

    @Override
    public void injectVerificationBottomSheet(VerificationBottomSheet arg0) {
      injectVerificationBottomSheet2(arg0);
    }

    @Override
    public void injectVerificationCancelFragment(VerificationCancelFragment arg0) {
      injectVerificationCancelFragment2(arg0);
    }

    @Override
    public void injectVerificationNotMeFragment(VerificationNotMeFragment arg0) {
      injectVerificationNotMeFragment2(arg0);
    }

    @Override
    public void injectVerificationChooseMethodFragment(VerificationChooseMethodFragment arg0) {
      injectVerificationChooseMethodFragment2(arg0);
    }

    @Override
    public void injectVerificationConclusionFragment(VerificationConclusionFragment arg0) {
      injectVerificationConclusionFragment2(arg0);
    }

    @Override
    public void injectVerificationEmojiCodeFragment(VerificationEmojiCodeFragment arg0) {
      injectVerificationEmojiCodeFragment2(arg0);
    }

    @Override
    public void injectVerificationQRWaitingFragment(VerificationQRWaitingFragment arg0) {
      injectVerificationQRWaitingFragment2(arg0);
    }

    @Override
    public void injectVerificationQrScannedByOtherFragment(
        VerificationQrScannedByOtherFragment arg0) {
      injectVerificationQrScannedByOtherFragment2(arg0);
    }

    @Override
    public void injectVerificationRequestFragment(VerificationRequestFragment arg0) {
      injectVerificationRequestFragment2(arg0);
    }

    @Override
    public void injectDebugMemoryLeaksFragment(DebugMemoryLeaksFragment arg0) {
    }

    @Override
    public void injectRoomDevToolEditFragment(RoomDevToolEditFragment arg0) {
    }

    @Override
    public void injectRoomDevToolFragment(RoomDevToolFragment arg0) {
      injectRoomDevToolFragment2(arg0);
    }

    @Override
    public void injectRoomDevToolSendFormFragment(RoomDevToolSendFormFragment arg0) {
      injectRoomDevToolSendFormFragment2(arg0);
    }

    @Override
    public void injectRoomDevToolStateEventListFragment(RoomDevToolStateEventListFragment arg0) {
      injectRoomDevToolStateEventListFragment2(arg0);
    }

    @Override
    public void injectDiscoverySettingsFragment(DiscoverySettingsFragment arg0) {
      injectDiscoverySettingsFragment2(arg0);
    }

    @Override
    public void injectSetIdentityServerFragment(SetIdentityServerFragment arg0) {
      injectSetIdentityServerFragment2(arg0);
    }

    @Override
    public void injectHomeDetailFragment(HomeDetailFragment arg0) {
      injectHomeDetailFragment2(arg0);
    }

    @Override
    public void injectHomeDrawerFragment(HomeDrawerFragment arg0) {
      injectHomeDrawerFragment2(arg0);
    }

    @Override
    public void injectLoadingFragment(LoadingFragment arg0) {
    }

    @Override
    public void injectNewHomeDetailFragment(NewHomeDetailFragment arg0) {
      injectNewHomeDetailFragment2(arg0);
    }

    @Override
    public void injectBreadcrumbsFragment(BreadcrumbsFragment arg0) {
      injectBreadcrumbsFragment2(arg0);
    }

    @Override
    public void injectJoinReplacementRoomBottomSheet(JoinReplacementRoomBottomSheet arg0) {
      injectJoinReplacementRoomBottomSheet2(arg0);
    }

    @Override
    public void injectTimelineFragment(TimelineFragment arg0) {
      injectTimelineFragment2(arg0);
    }

    @Override
    public void injectMessageComposerFragment(MessageComposerFragment arg0) {
      injectMessageComposerFragment2(arg0);
    }

    @Override
    public void injectSetLinkFragment(SetLinkFragment arg0) {
    }

    @Override
    public void injectVoiceRecorderFragment(VoiceRecorderFragment arg0) {
      injectVoiceRecorderFragment2(arg0);
    }

    @Override
    public void injectDisplayReadReceiptsBottomSheet(DisplayReadReceiptsBottomSheet arg0) {
      injectDisplayReadReceiptsBottomSheet2(arg0);
    }

    @Override
    public void injectSearchFragment(SearchFragment arg0) {
      injectSearchFragment2(arg0);
    }

    @Override
    public void injectMessageActionsBottomSheet(MessageActionsBottomSheet arg0) {
      injectMessageActionsBottomSheet2(arg0);
    }

    @Override
    public void injectViewEditHistoryBottomSheet(ViewEditHistoryBottomSheet arg0) {
      injectViewEditHistoryBottomSheet2(arg0);
    }

    @Override
    public void injectViewReactionsBottomSheet(ViewReactionsBottomSheet arg0) {
      injectViewReactionsBottomSheet2(arg0);
    }

    @Override
    public void injectMigrateRoomBottomSheet(MigrateRoomBottomSheet arg0) {
      injectMigrateRoomBottomSheet2(arg0);
    }

    @Override
    public void injectRoomWidgetsBottomSheet(RoomWidgetsBottomSheet arg0) {
      injectRoomWidgetsBottomSheet2(arg0);
    }

    @Override
    public void injectRoomListFragment(RoomListFragment arg0) {
      injectRoomListFragment2(arg0);
    }

    @Override
    public void injectRoomListQuickActionsBottomSheet(RoomListQuickActionsBottomSheet arg0) {
      injectRoomListQuickActionsBottomSheet2(arg0);
    }

    @Override
    public void injectHomeRoomListFragment(HomeRoomListFragment arg0) {
      injectHomeRoomListFragment2(arg0);
    }

    @Override
    public void injectNewChatBottomSheet(NewChatBottomSheet arg0) {
      injectNewChatBottomSheet2(arg0);
    }

    @Override
    public void injectInvitesFragment(InvitesFragment arg0) {
      injectInvitesFragment2(arg0);
    }

    @Override
    public void injectHomeLayoutSettingBottomDialogFragment(
        HomeLayoutSettingBottomDialogFragment arg0) {
      injectHomeLayoutSettingBottomDialogFragment2(arg0);
    }

    @Override
    public void injectReleaseNotesFragment(ReleaseNotesFragment arg0) {
      injectReleaseNotesFragment2(arg0);
    }

    @Override
    public void injectThreadListFragment(ThreadListFragment arg0) {
      injectThreadListFragment2(arg0);
    }

    @Override
    public void injectLocationSharingFragment(LocationSharingFragment arg0) {
      injectLocationSharingFragment2(arg0);
    }

    @Override
    public void injectChooseLiveDurationBottomSheet(ChooseLiveDurationBottomSheet arg0) {
    }

    @Override
    public void injectLiveLocationMapViewFragment(LiveLocationMapViewFragment arg0) {
      injectLiveLocationMapViewFragment2(arg0);
    }

    @Override
    public void injectLocationPreviewFragment(LocationPreviewFragment arg0) {
      injectLocationPreviewFragment2(arg0);
    }

    @Override
    public void injectLoginCaptchaFragment(LoginCaptchaFragment arg0) {
      injectLoginCaptchaFragment2(arg0);
    }

    @Override
    public void injectLoginFragment(LoginFragment arg0) {
    }

    @Override
    public void injectLoginGenericTextInputFormFragment(LoginGenericTextInputFormFragment arg0) {
    }

    @Override
    public void injectLoginResetPasswordFragment(LoginResetPasswordFragment arg0) {
    }

    @Override
    public void injectLoginResetPasswordMailConfirmationFragment(
        LoginResetPasswordMailConfirmationFragment arg0) {
    }

    @Override
    public void injectLoginResetPasswordSuccessFragment(LoginResetPasswordSuccessFragment arg0) {
    }

    @Override
    public void injectLoginServerSelectionFragment(LoginServerSelectionFragment arg0) {
    }

    @Override
    public void injectLoginServerUrlFormFragment(LoginServerUrlFormFragment arg0) {
      injectLoginServerUrlFormFragment2(arg0);
    }

    @Override
    public void injectLoginSignUpSignInSelectionFragment(LoginSignUpSignInSelectionFragment arg0) {
    }

    @Override
    public void injectLoginSplashFragment(LoginSplashFragment arg0) {
      injectLoginSplashFragment2(arg0);
    }

    @Override
    public void injectLoginWaitForEmailFragment(LoginWaitForEmailFragment arg0) {
    }

    @Override
    public void injectLoginWebFragment(LoginWebFragment arg0) {
      injectLoginWebFragment2(arg0);
    }

    @Override
    public void injectQrCodeLoginInstructionsFragment(QrCodeLoginInstructionsFragment arg0) {
    }

    @Override
    public void injectQrCodeLoginShowQrCodeFragment(QrCodeLoginShowQrCodeFragment arg0) {
    }

    @Override
    public void injectQrCodeLoginStatusFragment(QrCodeLoginStatusFragment arg0) {
    }

    @Override
    public void injectMatrixToBottomSheet(MatrixToBottomSheet arg0) {
      injectMatrixToBottomSheet2(arg0);
    }

    @Override
    public void injectMatrixToRoomSpaceFragment(MatrixToRoomSpaceFragment arg0) {
      injectMatrixToRoomSpaceFragment2(arg0);
    }

    @Override
    public void injectMatrixToUserFragment(MatrixToUserFragment arg0) {
      injectMatrixToUserFragment2(arg0);
    }

    @Override
    public void injectFtueAuthAccountCreatedFragment(FtueAuthAccountCreatedFragment arg0) {
    }

    @Override
    public void injectFtueAuthCaptchaFragment(FtueAuthCaptchaFragment arg0) {
      injectFtueAuthCaptchaFragment2(arg0);
    }

    @Override
    public void injectFtueAuthChooseDisplayNameFragment(FtueAuthChooseDisplayNameFragment arg0) {
    }

    @Override
    public void injectFtueAuthChooseProfilePictureFragment(
        FtueAuthChooseProfilePictureFragment arg0) {
      injectFtueAuthChooseProfilePictureFragment2(arg0);
    }

    @Override
    public void injectFtueAuthCombinedLoginFragment(FtueAuthCombinedLoginFragment arg0) {
      injectFtueAuthCombinedLoginFragment2(arg0);
    }

    @Override
    public void injectFtueAuthCombinedRegisterFragment(FtueAuthCombinedRegisterFragment arg0) {
    }

    @Override
    public void injectFtueAuthCombinedServerSelectionFragment(
        FtueAuthCombinedServerSelectionFragment arg0) {
    }

    @Override
    public void injectFtueAuthEmailEntryFragment(FtueAuthEmailEntryFragment arg0) {
    }

    @Override
    public void injectFtueAuthGenericTextInputFormFragment(
        FtueAuthGenericTextInputFormFragment arg0) {
    }

    @Override
    public void injectFtueAuthLegacyStyleCaptchaFragment(FtueAuthLegacyStyleCaptchaFragment arg0) {
      injectFtueAuthLegacyStyleCaptchaFragment2(arg0);
    }

    @Override
    public void injectFtueAuthLegacyWaitForEmailFragment(FtueAuthLegacyWaitForEmailFragment arg0) {
    }

    @Override
    public void injectFtueAuthLoginFragment(FtueAuthLoginFragment arg0) {
    }

    @Override
    public void injectFtueAuthPersonalizationCompleteFragment(
        FtueAuthPersonalizationCompleteFragment arg0) {
    }

    @Override
    public void injectFtueAuthPhoneConfirmationFragment(FtueAuthPhoneConfirmationFragment arg0) {
    }

    @Override
    public void injectFtueAuthPhoneEntryFragment(FtueAuthPhoneEntryFragment arg0) {
      injectFtueAuthPhoneEntryFragment2(arg0);
    }

    @Override
    public void injectFtueAuthResetPasswordBreakerFragment(
        FtueAuthResetPasswordBreakerFragment arg0) {
      injectFtueAuthResetPasswordBreakerFragment2(arg0);
    }

    @Override
    public void injectFtueAuthResetPasswordEmailEntryFragment(
        FtueAuthResetPasswordEmailEntryFragment arg0) {
    }

    @Override
    public void injectFtueAuthResetPasswordEntryFragment(FtueAuthResetPasswordEntryFragment arg0) {
    }

    @Override
    public void injectFtueAuthResetPasswordFragment(FtueAuthResetPasswordFragment arg0) {
    }

    @Override
    public void injectFtueAuthResetPasswordMailConfirmationFragment(
        FtueAuthResetPasswordMailConfirmationFragment arg0) {
    }

    @Override
    public void injectFtueAuthResetPasswordSuccessFragment(
        FtueAuthResetPasswordSuccessFragment arg0) {
    }

    @Override
    public void injectFtueAuthServerSelectionFragment(FtueAuthServerSelectionFragment arg0) {
    }

    @Override
    public void injectFtueAuthServerUrlFormFragment(FtueAuthServerUrlFormFragment arg0) {
      injectFtueAuthServerUrlFormFragment2(arg0);
    }

    @Override
    public void injectFtueAuthSignUpSignInSelectionFragment(
        FtueAuthSignUpSignInSelectionFragment arg0) {
    }

    @Override
    public void injectFtueAuthSplashCarouselFragment(FtueAuthSplashCarouselFragment arg0) {
      injectFtueAuthSplashCarouselFragment2(arg0);
    }

    @Override
    public void injectFtueAuthSplashFragment(FtueAuthSplashFragment arg0) {
      injectFtueAuthSplashFragment2(arg0);
    }

    @Override
    public void injectFtueAuthUseCaseFragment(FtueAuthUseCaseFragment arg0) {
      injectFtueAuthUseCaseFragment2(arg0);
    }

    @Override
    public void injectFtueAuthWaitForEmailFragment(FtueAuthWaitForEmailFragment arg0) {
      injectFtueAuthWaitForEmailFragment2(arg0);
    }

    @Override
    public void injectFtueAuthWebFragment(FtueAuthWebFragment arg0) {
      injectFtueAuthWebFragment2(arg0);
    }

    @Override
    public void injectFtueAuthLegacyStyleTermsFragment(FtueAuthLegacyStyleTermsFragment arg0) {
      injectFtueAuthLegacyStyleTermsFragment2(arg0);
    }

    @Override
    public void injectFtueAuthTermsFragment(FtueAuthTermsFragment arg0) {
      injectFtueAuthTermsFragment2(arg0);
    }

    @Override
    public void injectPinFragment(PinFragment arg0) {
      injectPinFragment2(arg0);
    }

    @Override
    public void injectLockScreenFragment(LockScreenFragment arg0) {
    }

    @Override
    public void injectCreatePollFragment(CreatePollFragment arg0) {
      injectCreatePollFragment2(arg0);
    }

    @Override
    public void injectQrCodeScannerFragment(QrCodeScannerFragment arg0) {
    }

    @Override
    public void injectEmojiChooserFragment(EmojiChooserFragment arg0) {
      injectEmojiChooserFragment2(arg0);
    }

    @Override
    public void injectEmojiSearchResultFragment(EmojiSearchResultFragment arg0) {
      injectEmojiSearchResultFragment2(arg0);
    }

    @Override
    public void injectPublicRoomsFragment(PublicRoomsFragment arg0) {
      injectPublicRoomsFragment2(arg0);
    }

    @Override
    public void injectCreateRoomFragment(CreateRoomFragment arg0) {
      injectCreateRoomFragment2(arg0);
    }

    @Override
    public void injectRoomDirectoryPickerFragment(RoomDirectoryPickerFragment arg0) {
      injectRoomDirectoryPickerFragment2(arg0);
    }

    @Override
    public void injectRoomPreviewNoPreviewFragment(RoomPreviewNoPreviewFragment arg0) {
      injectRoomPreviewNoPreviewFragment2(arg0);
    }

    @Override
    public void injectRoomMemberProfileFragment(RoomMemberProfileFragment arg0) {
      injectRoomMemberProfileFragment2(arg0);
    }

    @Override
    public void injectDeviceListBottomSheet(DeviceListBottomSheet arg0) {
    }

    @Override
    public void injectDeviceListFragment(DeviceListFragment arg0) {
      injectDeviceListFragment2(arg0);
    }

    @Override
    public void injectDeviceTrustInfoActionFragment(DeviceTrustInfoActionFragment arg0) {
      injectDeviceTrustInfoActionFragment2(arg0);
    }

    @Override
    public void injectRoomProfileFragment(RoomProfileFragment arg0) {
      injectRoomProfileFragment2(arg0);
    }

    @Override
    public void injectRoomAliasFragment(RoomAliasFragment arg0) {
      injectRoomAliasFragment2(arg0);
    }

    @Override
    public void injectRoomAliasBottomSheet(RoomAliasBottomSheet arg0) {
      injectRoomAliasBottomSheet2(arg0);
    }

    @Override
    public void injectRoomBannedMemberListFragment(RoomBannedMemberListFragment arg0) {
      injectRoomBannedMemberListFragment2(arg0);
    }

    @Override
    public void injectRoomMemberListFragment(RoomMemberListFragment arg0) {
      injectRoomMemberListFragment2(arg0);
    }

    @Override
    public void injectRoomNotificationSettingsFragment(RoomNotificationSettingsFragment arg0) {
      injectRoomNotificationSettingsFragment2(arg0);
    }

    @Override
    public void injectRoomPermissionsFragment(RoomPermissionsFragment arg0) {
      injectRoomPermissionsFragment2(arg0);
    }

    @Override
    public void injectRoomPollsFragment(RoomPollsFragment arg0) {
    }

    @Override
    public void injectRoomActivePollsFragment(RoomActivePollsFragment arg0) {
      injectRoomActivePollsFragment2(arg0);
    }

    @Override
    public void injectRoomEndedPollsFragment(RoomEndedPollsFragment arg0) {
      injectRoomEndedPollsFragment2(arg0);
    }

    @Override
    public void injectRoomSettingsFragment(RoomSettingsFragment arg0) {
      injectRoomSettingsFragment2(arg0);
    }

    @Override
    public void injectRoomHistoryVisibilityBottomSheet(RoomHistoryVisibilityBottomSheet arg0) {
      injectRoomHistoryVisibilityBottomSheet2(arg0);
    }

    @Override
    public void injectRoomJoinRuleBottomSheet(RoomJoinRuleBottomSheet arg0) {
      injectRoomJoinRuleBottomSheet2(arg0);
    }

    @Override
    public void injectRoomJoinRuleFragment(RoomJoinRuleFragment arg0) {
      injectRoomJoinRuleFragment2(arg0);
    }

    @Override
    public void injectRoomJoinRuleChooseRestrictedFragment(
        RoomJoinRuleChooseRestrictedFragment arg0) {
      injectRoomJoinRuleChooseRestrictedFragment2(arg0);
    }

    @Override
    public void injectRoomUploadsFragment(RoomUploadsFragment arg0) {
      injectRoomUploadsFragment2(arg0);
    }

    @Override
    public void injectRoomUploadsFilesFragment(RoomUploadsFilesFragment arg0) {
      injectRoomUploadsFilesFragment2(arg0);
    }

    @Override
    public void injectRoomUploadsMediaFragment(RoomUploadsMediaFragment arg0) {
      injectRoomUploadsMediaFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsAdvancedSettingsFragment(
        VectorSettingsAdvancedSettingsFragment arg0) {
      injectVectorSettingsAdvancedSettingsFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsGeneralFragment(VectorSettingsGeneralFragment arg0) {
      injectVectorSettingsGeneralFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsHelpAboutFragment(VectorSettingsHelpAboutFragment arg0) {
      injectVectorSettingsHelpAboutFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsPinFragment(VectorSettingsPinFragment arg0) {
      injectVectorSettingsPinFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsPreferencesFragment(VectorSettingsPreferencesFragment arg0) {
      injectVectorSettingsPreferencesFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsRootFragment(VectorSettingsRootFragment arg0) {
    }

    @Override
    public void injectVectorSettingsSecurityPrivacyFragment(
        VectorSettingsSecurityPrivacyFragment arg0) {
      injectVectorSettingsSecurityPrivacyFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsVoiceVideoFragment(VectorSettingsVoiceVideoFragment arg0) {
      injectVectorSettingsVoiceVideoFragment2(arg0);
    }

    @Override
    public void injectDeactivateAccountFragment(DeactivateAccountFragment arg0) {
    }

    @Override
    public void injectCrossSigningSettingsFragment(CrossSigningSettingsFragment arg0) {
      injectCrossSigningSettingsFragment2(arg0);
    }

    @Override
    public void injectDeviceVerificationInfoBottomSheet(DeviceVerificationInfoBottomSheet arg0) {
      injectDeviceVerificationInfoBottomSheet2(arg0);
    }

    @Override
    public void injectVectorSettingsDevicesFragment(VectorSettingsDevicesFragment arg0) {
      injectVectorSettingsDevicesFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsDevicesFragment(
        im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment arg0) {
      injectVectorSettingsDevicesFragment3(arg0);
    }

    @Override
    public void injectSessionDetailsFragment(SessionDetailsFragment arg0) {
      injectSessionDetailsFragment2(arg0);
    }

    @Override
    public void injectDeviceManagerFilterBottomSheet(DeviceManagerFilterBottomSheet arg0) {
    }

    @Override
    public void injectSessionLearnMoreBottomSheet(SessionLearnMoreBottomSheet arg0) {
    }

    @Override
    public void injectOtherSessionsFragment(OtherSessionsFragment arg0) {
      injectOtherSessionsFragment2(arg0);
    }

    @Override
    public void injectSessionOverviewFragment(SessionOverviewFragment arg0) {
      injectSessionOverviewFragment2(arg0);
    }

    @Override
    public void injectRenameSessionFragment(RenameSessionFragment arg0) {
      injectRenameSessionFragment2(arg0);
    }

    @Override
    public void injectAccountDataFragment(AccountDataFragment arg0) {
      injectAccountDataFragment2(arg0);
    }

    @Override
    public void injectGossipingEventsPaperTrailFragment(GossipingEventsPaperTrailFragment arg0) {
      injectGossipingEventsPaperTrailFragment2(arg0);
    }

    @Override
    public void injectIncomingKeyRequestListFragment(IncomingKeyRequestListFragment arg0) {
      injectIncomingKeyRequestListFragment2(arg0);
    }

    @Override
    public void injectKeyRequestsFragment(KeyRequestsFragment arg0) {
      injectKeyRequestsFragment2(arg0);
    }

    @Override
    public void injectOutgoingKeyRequestListFragment(OutgoingKeyRequestListFragment arg0) {
      injectOutgoingKeyRequestListFragment2(arg0);
    }

    @Override
    public void injectFontScaleSettingFragment(FontScaleSettingFragment arg0) {
      injectFontScaleSettingFragment2(arg0);
    }

    @Override
    public void injectHomeserverSettingsFragment(HomeserverSettingsFragment arg0) {
      injectHomeserverSettingsFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsIgnoredUsersFragment(VectorSettingsIgnoredUsersFragment arg0) {
      injectVectorSettingsIgnoredUsersFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsLabsFragment(VectorSettingsLabsFragment arg0) {
      injectVectorSettingsLabsFragment2(arg0);
    }

    @Override
    public void injectLegalsFragment(LegalsFragment arg0) {
      injectLegalsFragment2(arg0);
    }

    @Override
    public void injectLocalePickerFragment(LocalePickerFragment arg0) {
      injectLocalePickerFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsAdvancedNotificationPreferenceFragment(
        VectorSettingsAdvancedNotificationPreferenceFragment arg0) {
    }

    @Override
    public void injectVectorSettingsNotificationPreferenceFragment(
        VectorSettingsNotificationPreferenceFragment arg0) {
      injectVectorSettingsNotificationPreferenceFragment2(arg0);
    }

    @Override
    public void injectVectorSettingsNotificationsTroubleshootFragment(
        VectorSettingsNotificationsTroubleshootFragment arg0) {
      injectVectorSettingsNotificationsTroubleshootFragment2(arg0);
    }

    @Override
    public void injectPushGatewaysFragment(PushGatewaysFragment arg0) {
      injectPushGatewaysFragment2(arg0);
    }

    @Override
    public void injectPushRulesFragment(PushRulesFragment arg0) {
      injectPushRulesFragment2(arg0);
    }

    @Override
    public void injectThreePidsSettingsFragment(ThreePidsSettingsFragment arg0) {
      injectThreePidsSettingsFragment2(arg0);
    }

    @Override
    public void injectIncomingShareFragment(IncomingShareFragment arg0) {
      injectIncomingShareFragment2(arg0);
    }

    @Override
    public void injectSoftLogoutFragment(SoftLogoutFragment arg0) {
      injectSoftLogoutFragment2(arg0);
    }

    @Override
    public void injectInviteRoomSpaceChooserBottomSheet(InviteRoomSpaceChooserBottomSheet arg0) {
      injectInviteRoomSpaceChooserBottomSheet2(arg0);
    }

    @Override
    public void injectSpaceListFragment(SpaceListFragment arg0) {
      injectSpaceListFragment2(arg0);
    }

    @Override
    public void injectSpaceSettingsMenuBottomSheet(SpaceSettingsMenuBottomSheet arg0) {
      injectSpaceSettingsMenuBottomSheet2(arg0);
    }

    @Override
    public void injectChoosePrivateSpaceTypeFragment(ChoosePrivateSpaceTypeFragment arg0) {
      injectChoosePrivateSpaceTypeFragment2(arg0);
    }

    @Override
    public void injectChooseSpaceTypeFragment(ChooseSpaceTypeFragment arg0) {
    }

    @Override
    public void injectCreateSpaceAdd3pidInvitesFragment(CreateSpaceAdd3pidInvitesFragment arg0) {
      injectCreateSpaceAdd3pidInvitesFragment2(arg0);
    }

    @Override
    public void injectCreateSpaceDefaultRoomsFragment(CreateSpaceDefaultRoomsFragment arg0) {
      injectCreateSpaceDefaultRoomsFragment2(arg0);
    }

    @Override
    public void injectCreateSpaceDetailsFragment(CreateSpaceDetailsFragment arg0) {
      injectCreateSpaceDetailsFragment2(arg0);
    }

    @Override
    public void injectSpaceDirectoryFragment(SpaceDirectoryFragment arg0) {
      injectSpaceDirectoryFragment2(arg0);
    }

    @Override
    public void injectSpaceInviteBottomSheet(SpaceInviteBottomSheet arg0) {
      injectSpaceInviteBottomSheet2(arg0);
    }

    @Override
    public void injectSpaceLeaveAdvancedFragment(SpaceLeaveAdvancedFragment arg0) {
      injectSpaceLeaveAdvancedFragment2(arg0);
    }

    @Override
    public void injectSpaceAddRoomFragment(SpaceAddRoomFragment arg0) {
      injectSpaceAddRoomFragment2(arg0);
    }

    @Override
    public void injectSpaceManageRoomsFragment(SpaceManageRoomsFragment arg0) {
      injectSpaceManageRoomsFragment2(arg0);
    }

    @Override
    public void injectSpaceSettingsFragment(SpaceSettingsFragment arg0) {
      injectSpaceSettingsFragment2(arg0);
    }

    @Override
    public void injectSpacePeopleFragment(SpacePeopleFragment arg0) {
      injectSpacePeopleFragment2(arg0);
    }

    @Override
    public void injectSpacePreviewFragment(SpacePreviewFragment arg0) {
      injectSpacePreviewFragment2(arg0);
    }

    @Override
    public void injectShareSpaceBottomSheet(ShareSpaceBottomSheet arg0) {
    }

    @Override
    public void injectReviewTermsFragment(ReviewTermsFragment arg0) {
      injectReviewTermsFragment2(arg0);
    }

    @Override
    public void injectShowUserCodeFragment(ShowUserCodeFragment arg0) {
      injectShowUserCodeFragment2(arg0);
    }

    @Override
    public void injectUserListFragment(UserListFragment arg0) {
      injectUserListFragment2(arg0);
    }

    @Override
    public void injectWidgetFragment(WidgetFragment arg0) {
      injectWidgetFragment2(arg0);
    }

    @Override
    public void injectRoomWidgetPermissionBottomSheet(RoomWidgetPermissionBottomSheet arg0) {
      injectRoomWidgetPermissionBottomSheet2(arg0);
    }

    @Override
    public void injectSignOutBottomSheetDialogFragment(SignOutBottomSheetDialogFragment arg0) {
    }

    @CanIgnoreReturnValue
    private AnalyticsOptInFragment injectAnalyticsOptInFragment2(AnalyticsOptInFragment instance) {
      AnalyticsOptInFragment_MembersInjector.injectAnalyticsConfig(instance, ConfigurationModule_ProvidesAnalyticsConfigFactory.providesAnalyticsConfig());
      return instance;
    }

    @CanIgnoreReturnValue
    private AttachmentsPreviewFragment injectAttachmentsPreviewFragment2(
        AttachmentsPreviewFragment instance) {
      AttachmentsPreviewFragment_MembersInjector.injectAttachmentMiniaturePreviewController(instance, new AttachmentMiniaturePreviewController());
      AttachmentsPreviewFragment_MembersInjector.injectAttachmentBigPreviewController(instance, new AttachmentBigPreviewController());
      AttachmentsPreviewFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      AttachmentsPreviewFragment_MembersInjector.injectClock(instance, new DefaultClock());
      return instance;
    }

    @CanIgnoreReturnValue
    private CallControlsBottomSheet injectCallControlsBottomSheet2(
        CallControlsBottomSheet instance) {
      CallControlsBottomSheet_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      return instance;
    }

    @CanIgnoreReturnValue
    private CallDialPadBottomSheet injectCallDialPadBottomSheet2(CallDialPadBottomSheet instance) {
      CallDialPadBottomSheet_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private ContactsBookFragment injectContactsBookFragment2(ContactsBookFragment instance) {
      ContactsBookFragment_MembersInjector.injectContactsBookController(instance, contactsBookController());
      return instance;
    }

    @CanIgnoreReturnValue
    private KeysBackupSettingsFragment injectKeysBackupSettingsFragment2(
        KeysBackupSettingsFragment instance) {
      KeysBackupSettingsFragment_MembersInjector.injectKeysBackupSettingsRecyclerViewController(instance, keysBackupSettingsRecyclerViewController());
      return instance;
    }

    @CanIgnoreReturnValue
    private KeysBackupSetupStep2Fragment injectKeysBackupSetupStep2Fragment2(
        KeysBackupSetupStep2Fragment instance) {
      KeysBackupSetupStep2Fragment_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private BootstrapConclusionFragment injectBootstrapConclusionFragment2(
        BootstrapConclusionFragment instance) {
      BootstrapConclusionFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private BootstrapEnterPassphraseFragment injectBootstrapEnterPassphraseFragment2(
        BootstrapEnterPassphraseFragment instance) {
      BootstrapEnterPassphraseFragment_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private BootstrapMigrateBackupFragment injectBootstrapMigrateBackupFragment2(
        BootstrapMigrateBackupFragment instance) {
      BootstrapMigrateBackupFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationBottomSheet injectVerificationBottomSheet2(
        VerificationBottomSheet instance) {
      VerificationBottomSheet_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationCancelFragment injectVerificationCancelFragment2(
        VerificationCancelFragment instance) {
      VerificationCancelFragment_MembersInjector.injectController(instance, verificationCancelController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationNotMeFragment injectVerificationNotMeFragment2(
        VerificationNotMeFragment instance) {
      VerificationNotMeFragment_MembersInjector.injectController(instance, verificationNotMeController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationChooseMethodFragment injectVerificationChooseMethodFragment2(
        VerificationChooseMethodFragment instance) {
      VerificationChooseMethodFragment_MembersInjector.injectController(instance, verificationChooseMethodController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationConclusionFragment injectVerificationConclusionFragment2(
        VerificationConclusionFragment instance) {
      VerificationConclusionFragment_MembersInjector.injectController(instance, verificationConclusionController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationEmojiCodeFragment injectVerificationEmojiCodeFragment2(
        VerificationEmojiCodeFragment instance) {
      VerificationEmojiCodeFragment_MembersInjector.injectController(instance, verificationEmojiCodeController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationQRWaitingFragment injectVerificationQRWaitingFragment2(
        VerificationQRWaitingFragment instance) {
      VerificationQRWaitingFragment_MembersInjector.injectController(instance, verificationQRWaitingController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationQrScannedByOtherFragment injectVerificationQrScannedByOtherFragment2(
        VerificationQrScannedByOtherFragment instance) {
      VerificationQrScannedByOtherFragment_MembersInjector.injectController(instance, verificationQrScannedByOtherController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VerificationRequestFragment injectVerificationRequestFragment2(
        VerificationRequestFragment instance) {
      VerificationRequestFragment_MembersInjector.injectController(instance, verificationRequestController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomDevToolFragment injectRoomDevToolFragment2(RoomDevToolFragment instance) {
      RoomDevToolFragment_MembersInjector.injectEpoxyController(instance, roomDevToolRootController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomDevToolSendFormFragment injectRoomDevToolSendFormFragment2(
        RoomDevToolSendFormFragment instance) {
      RoomDevToolSendFormFragment_MembersInjector.injectEpoxyController(instance, roomDevToolSendFormController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomDevToolStateEventListFragment injectRoomDevToolStateEventListFragment2(
        RoomDevToolStateEventListFragment instance) {
      RoomDevToolStateEventListFragment_MembersInjector.injectEpoxyController(instance, roomStateListController());
      return instance;
    }

    @CanIgnoreReturnValue
    private DiscoverySettingsFragment injectDiscoverySettingsFragment2(
        DiscoverySettingsFragment instance) {
      DiscoverySettingsFragment_MembersInjector.injectController(instance, discoverySettingsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SetIdentityServerFragment injectSetIdentityServerFragment2(
        SetIdentityServerFragment instance) {
      SetIdentityServerFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private HomeDetailFragment injectHomeDetailFragment2(HomeDetailFragment instance) {
      HomeDetailFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      HomeDetailFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      HomeDetailFragment_MembersInjector.injectAlertManager(instance, singletonCImpl.popupAlertManagerProvider.get());
      HomeDetailFragment_MembersInjector.injectCallManager(instance, singletonCImpl.webRtcCallManagerProvider.get());
      HomeDetailFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      HomeDetailFragment_MembersInjector.injectSpaceStateHandler(instance, singletonCImpl.spaceStateHandlerImplProvider.get());
      HomeDetailFragment_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private HomeDrawerFragment injectHomeDrawerFragment2(HomeDrawerFragment instance) {
      HomeDrawerFragment_MembersInjector.injectSession(instance, singletonCImpl.session());
      HomeDrawerFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      HomeDrawerFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      HomeDrawerFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      HomeDrawerFragment_MembersInjector.injectPermalinkFactory(instance, permalinkFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private NewHomeDetailFragment injectNewHomeDetailFragment2(NewHomeDetailFragment instance) {
      NewHomeDetailFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      NewHomeDetailFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      NewHomeDetailFragment_MembersInjector.injectAlertManager(instance, singletonCImpl.popupAlertManagerProvider.get());
      NewHomeDetailFragment_MembersInjector.injectCallManager(instance, singletonCImpl.webRtcCallManagerProvider.get());
      NewHomeDetailFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      NewHomeDetailFragment_MembersInjector.injectSpaceStateHandler(instance, singletonCImpl.spaceStateHandlerImplProvider.get());
      NewHomeDetailFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private BreadcrumbsFragment injectBreadcrumbsFragment2(BreadcrumbsFragment instance) {
      BreadcrumbsFragment_MembersInjector.injectBreadcrumbsController(instance, breadcrumbsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private JoinReplacementRoomBottomSheet injectJoinReplacementRoomBottomSheet2(
        JoinReplacementRoomBottomSheet instance) {
      JoinReplacementRoomBottomSheet_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      return instance;
    }

    @CanIgnoreReturnValue
    private TimelineFragment injectTimelineFragment2(TimelineFragment instance) {
      TimelineFragment_MembersInjector.injectSession(instance, singletonCImpl.session());
      TimelineFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      TimelineFragment_MembersInjector.injectTimelineEventController(instance, timelineEventController());
      TimelineFragment_MembersInjector.injectPermalinkHandler(instance, activityCImpl.permalinkHandler());
      TimelineFragment_MembersInjector.injectNotificationDrawerManager(instance, singletonCImpl.notificationDrawerManagerProvider.get());
      TimelineFragment_MembersInjector.injectEventHtmlRenderer(instance, singletonCImpl.eventHtmlRendererProvider.get());
      TimelineFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      TimelineFragment_MembersInjector.injectThreadsManager(instance, threadsManager());
      TimelineFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      TimelineFragment_MembersInjector.injectDimensionConverter(instance, singletonCImpl.dimensionConverter());
      TimelineFragment_MembersInjector.injectUserPreferencesProvider(instance, activityCImpl.userPreferencesProvider());
      TimelineFragment_MembersInjector.injectNotificationUtils(instance, singletonCImpl.notificationUtilsProvider.get());
      TimelineFragment_MembersInjector.injectMatrixItemColorProvider(instance, singletonCImpl.matrixItemColorProvider.get());
      TimelineFragment_MembersInjector.injectImageContentRenderer(instance, activityCImpl.imageContentRenderer());
      TimelineFragment_MembersInjector.injectRoomDetailPendingActionStore(instance, singletonCImpl.roomDetailPendingActionStoreProvider.get());
      TimelineFragment_MembersInjector.injectPillsPostProcessorFactory(instance, factoryProvider2.get());
      TimelineFragment_MembersInjector.injectCallManager(instance, singletonCImpl.webRtcCallManagerProvider.get());
      TimelineFragment_MembersInjector.injectAudioMessagePlaybackTracker(instance, singletonCImpl.audioMessagePlaybackTrackerProvider.get());
      TimelineFragment_MembersInjector.injectShareIntentHandler(instance, shareIntentHandler());
      TimelineFragment_MembersInjector.injectClock(instance, new DefaultClock());
      TimelineFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      TimelineFragment_MembersInjector.injectGalleryOrCameraDialogHelperFactory(instance, galleryOrCameraDialogHelperFactory());
      TimelineFragment_MembersInjector.injectPermalinkFactory(instance, permalinkFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private AutocompleteMemberController injectAutocompleteMemberController(
        AutocompleteMemberController instance) {
      AutocompleteMemberController_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private MessageComposerFragment injectMessageComposerFragment2(
        MessageComposerFragment instance) {
      MessageComposerFragment_MembersInjector.injectAutoCompleterFactory(instance, factoryProvider5.get());
      MessageComposerFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      MessageComposerFragment_MembersInjector.injectShareIntentHandler(instance, shareIntentHandler());
      MessageComposerFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      MessageComposerFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      MessageComposerFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      MessageComposerFragment_MembersInjector.injectSession(instance, singletonCImpl.session());
      MessageComposerFragment_MembersInjector.injectErrorTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VoiceRecorderFragment injectVoiceRecorderFragment2(VoiceRecorderFragment instance) {
      VoiceRecorderFragment_MembersInjector.injectAudioMessagePlaybackTracker(instance, singletonCImpl.audioMessagePlaybackTrackerProvider.get());
      VoiceRecorderFragment_MembersInjector.injectClock(instance, new DefaultClock());
      return instance;
    }

    @CanIgnoreReturnValue
    private DisplayReadReceiptsBottomSheet injectDisplayReadReceiptsBottomSheet2(
        DisplayReadReceiptsBottomSheet instance) {
      DisplayReadReceiptsBottomSheet_MembersInjector.injectEpoxyController(instance, displayReadReceiptsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SearchFragment injectSearchFragment2(SearchFragment instance) {
      SearchFragment_MembersInjector.injectController(instance, searchResultController());
      return instance;
    }

    @CanIgnoreReturnValue
    private MessageActionsBottomSheet injectMessageActionsBottomSheet2(
        MessageActionsBottomSheet instance) {
      MessageActionsBottomSheet_MembersInjector.injectMessageActionsEpoxyController(instance, messageActionsEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private ViewEditHistoryBottomSheet injectViewEditHistoryBottomSheet2(
        ViewEditHistoryBottomSheet instance) {
      ViewEditHistoryBottomSheet_MembersInjector.injectEpoxyController(instance, viewEditHistoryEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private ViewReactionsBottomSheet injectViewReactionsBottomSheet2(
        ViewReactionsBottomSheet instance) {
      ViewReactionsBottomSheet_MembersInjector.injectEpoxyController(instance, viewReactionsEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private MigrateRoomBottomSheet injectMigrateRoomBottomSheet2(MigrateRoomBottomSheet instance) {
      MigrateRoomBottomSheet_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomWidgetsBottomSheet injectRoomWidgetsBottomSheet2(RoomWidgetsBottomSheet instance) {
      RoomWidgetsBottomSheet_MembersInjector.injectEpoxyController(instance, roomWidgetsController());
      RoomWidgetsBottomSheet_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      RoomWidgetsBottomSheet_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomListFragment injectRoomListFragment2(RoomListFragment instance) {
      RoomListFragment_MembersInjector.injectPagedControllerFactory(instance, roomSummaryPagedControllerFactory());
      RoomListFragment_MembersInjector.injectNotificationDrawerManager(instance, singletonCImpl.notificationDrawerManagerProvider.get());
      RoomListFragment_MembersInjector.injectFooterController(instance, roomListFooterController());
      RoomListFragment_MembersInjector.injectUserPreferencesProvider(instance, activityCImpl.userPreferencesProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomListQuickActionsBottomSheet injectRoomListQuickActionsBottomSheet2(
        RoomListQuickActionsBottomSheet instance) {
      RoomListQuickActionsBottomSheet_MembersInjector.injectSharedViewPool(instance, activityCImpl.providesSharedViewPoolProvider.get());
      RoomListQuickActionsBottomSheet_MembersInjector.injectRoomListActionsEpoxyController(instance, roomListQuickActionsEpoxyController());
      RoomListQuickActionsBottomSheet_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      RoomListQuickActionsBottomSheet_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      return instance;
    }

    @CanIgnoreReturnValue
    private HomeRoomListFragment injectHomeRoomListFragment2(HomeRoomListFragment instance) {
      HomeRoomListFragment_MembersInjector.injectUserPreferencesProvider(instance, activityCImpl.userPreferencesProvider());
      HomeRoomListFragment_MembersInjector.injectHeadersController(instance, homeRoomsHeadersController());
      HomeRoomListFragment_MembersInjector.injectRoomsController(instance, homeFilteredRoomsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private NewChatBottomSheet injectNewChatBottomSheet2(NewChatBottomSheet instance) {
      NewChatBottomSheet_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private InvitesFragment injectInvitesFragment2(InvitesFragment instance) {
      InvitesFragment_MembersInjector.injectController(instance, invitesController());
      InvitesFragment_MembersInjector.injectNotificationDrawerManager(instance, singletonCImpl.notificationDrawerManagerProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private HomeLayoutSettingBottomDialogFragment injectHomeLayoutSettingBottomDialogFragment2(
        HomeLayoutSettingBottomDialogFragment instance) {
      HomeLayoutSettingBottomDialogFragment_MembersInjector.injectPreferencesStore(instance, homeLayoutPreferencesStore());
      return instance;
    }

    @CanIgnoreReturnValue
    private ReleaseNotesFragment injectReleaseNotesFragment2(ReleaseNotesFragment instance) {
      ReleaseNotesFragment_MembersInjector.injectCarouselController(instance, new ReleaseNotesCarouselController());
      return instance;
    }

    @CanIgnoreReturnValue
    private ThreadListFragment injectThreadListFragment2(ThreadListFragment instance) {
      ThreadListFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      ThreadListFragment_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      ThreadListFragment_MembersInjector.injectThreadListController(instance, threadListPagedController());
      ThreadListFragment_MembersInjector.injectLegacyThreadListController(instance, threadListController());
      ThreadListFragment_MembersInjector.injectThreadListViewModelFactory(instance, factoryProvider6.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private LocationSharingFragment injectLocationSharingFragment2(
        LocationSharingFragment instance) {
      LocationSharingFragment_MembersInjector.injectUrlMapProvider(instance, urlMapProvider());
      LocationSharingFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      LocationSharingFragment_MembersInjector.injectMatrixItemColorProvider(instance, singletonCImpl.matrixItemColorProvider.get());
      LocationSharingFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      return instance;
    }

    @CanIgnoreReturnValue
    private LiveLocationMapViewFragment injectLiveLocationMapViewFragment2(
        LiveLocationMapViewFragment instance) {
      LiveLocationMapViewFragment_MembersInjector.injectUrlMapProvider(instance, urlMapProvider());
      LiveLocationMapViewFragment_MembersInjector.injectBottomSheetController(instance, liveLocationBottomSheetController());
      LiveLocationMapViewFragment_MembersInjector.injectDimensionConverter(instance, singletonCImpl.dimensionConverter());
      return instance;
    }

    @CanIgnoreReturnValue
    private LocationPreviewFragment injectLocationPreviewFragment2(
        LocationPreviewFragment instance) {
      LocationPreviewFragment_MembersInjector.injectUrlMapProvider(instance, urlMapProvider());
      LocationPreviewFragment_MembersInjector.injectLocationPinProvider(instance, singletonCImpl.locationPinProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private LoginCaptchaFragment injectLoginCaptchaFragment2(LoginCaptchaFragment instance) {
      LoginCaptchaFragment_MembersInjector.injectAssetReader(instance, activityCImpl.assetReader());
      return instance;
    }

    @CanIgnoreReturnValue
    private LoginServerUrlFormFragment injectLoginServerUrlFormFragment2(
        LoginServerUrlFormFragment instance) {
      LoginServerUrlFormFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private LoginSplashFragment injectLoginSplashFragment2(LoginSplashFragment instance) {
      LoginSplashFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      LoginSplashFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private LoginWebFragment injectLoginWebFragment2(LoginWebFragment instance) {
      LoginWebFragment_MembersInjector.injectAssetReader(instance, activityCImpl.assetReader());
      return instance;
    }

    @CanIgnoreReturnValue
    private MatrixToBottomSheet injectMatrixToBottomSheet2(MatrixToBottomSheet instance) {
      MatrixToBottomSheet_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private MatrixToRoomSpaceFragment injectMatrixToRoomSpaceFragment2(
        MatrixToRoomSpaceFragment instance) {
      MatrixToRoomSpaceFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      MatrixToRoomSpaceFragment_MembersInjector.injectSpaceCardRenderer(instance, spaceCardRenderer());
      return instance;
    }

    @CanIgnoreReturnValue
    private MatrixToUserFragment injectMatrixToUserFragment2(MatrixToUserFragment instance) {
      MatrixToUserFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthCaptchaFragment injectFtueAuthCaptchaFragment2(
        FtueAuthCaptchaFragment instance) {
      FtueAuthCaptchaFragment_MembersInjector.injectCaptchaWebview(instance, captchaWebview());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthChooseProfilePictureFragment injectFtueAuthChooseProfilePictureFragment2(
        FtueAuthChooseProfilePictureFragment instance) {
      FtueAuthChooseProfilePictureFragment_MembersInjector.injectGalleryOrCameraDialogHelperFactory(instance, galleryOrCameraDialogHelperFactory());
      FtueAuthChooseProfilePictureFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthCombinedLoginFragment injectFtueAuthCombinedLoginFragment2(
        FtueAuthCombinedLoginFragment instance) {
      FtueAuthCombinedLoginFragment_MembersInjector.injectLoginFieldsValidation(instance, loginFieldsValidation());
      FtueAuthCombinedLoginFragment_MembersInjector.injectLoginErrorParser(instance, loginErrorParser());
      FtueAuthCombinedLoginFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthLegacyStyleCaptchaFragment injectFtueAuthLegacyStyleCaptchaFragment2(
        FtueAuthLegacyStyleCaptchaFragment instance) {
      FtueAuthLegacyStyleCaptchaFragment_MembersInjector.injectCaptchaWebview(instance, captchaWebview());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthPhoneEntryFragment injectFtueAuthPhoneEntryFragment2(
        FtueAuthPhoneEntryFragment instance) {
      FtueAuthPhoneEntryFragment_MembersInjector.injectPhoneNumberParser(instance, phoneNumberParser());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthResetPasswordBreakerFragment injectFtueAuthResetPasswordBreakerFragment2(
        FtueAuthResetPasswordBreakerFragment instance) {
      FtueAuthResetPasswordBreakerFragment_MembersInjector.injectThemeProvider(instance, singletonCImpl.themeProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthServerUrlFormFragment injectFtueAuthServerUrlFormFragment2(
        FtueAuthServerUrlFormFragment instance) {
      FtueAuthServerUrlFormFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthSplashCarouselFragment injectFtueAuthSplashCarouselFragment2(
        FtueAuthSplashCarouselFragment instance) {
      FtueAuthSplashCarouselFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      FtueAuthSplashCarouselFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      FtueAuthSplashCarouselFragment_MembersInjector.injectCarouselController(instance, new SplashCarouselController());
      FtueAuthSplashCarouselFragment_MembersInjector.injectCarouselStateFactory(instance, splashCarouselStateFactory());
      FtueAuthSplashCarouselFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthSplashFragment injectFtueAuthSplashFragment2(FtueAuthSplashFragment instance) {
      FtueAuthSplashFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      FtueAuthSplashFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      FtueAuthSplashFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthUseCaseFragment injectFtueAuthUseCaseFragment2(
        FtueAuthUseCaseFragment instance) {
      FtueAuthUseCaseFragment_MembersInjector.injectThemeProvider(instance, singletonCImpl.themeProvider());
      FtueAuthUseCaseFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthWaitForEmailFragment injectFtueAuthWaitForEmailFragment2(
        FtueAuthWaitForEmailFragment instance) {
      FtueAuthWaitForEmailFragment_MembersInjector.injectThemeProvider(instance, singletonCImpl.themeProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthWebFragment injectFtueAuthWebFragment2(FtueAuthWebFragment instance) {
      FtueAuthWebFragment_MembersInjector.injectAssetReader(instance, activityCImpl.assetReader());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthLegacyStyleTermsFragment injectFtueAuthLegacyStyleTermsFragment2(
        FtueAuthLegacyStyleTermsFragment instance) {
      FtueAuthLegacyStyleTermsFragment_MembersInjector.injectPolicyController(instance, new PolicyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private FtueAuthTermsFragment injectFtueAuthTermsFragment2(FtueAuthTermsFragment instance) {
      FtueAuthTermsFragment_MembersInjector.injectPolicyController(instance, new PolicyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private PinFragment injectPinFragment2(PinFragment instance) {
      PinFragment_MembersInjector.injectPinCodeStore(instance, singletonCImpl.sharedPrefPinCodeStoreProvider.get());
      PinFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      PinFragment_MembersInjector.injectDefaultConfiguration(instance, LockScreenModule_ProvideLockScreenConfigFactory.provideLockScreenConfig());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreatePollFragment injectCreatePollFragment2(CreatePollFragment instance) {
      CreatePollFragment_MembersInjector.injectController(instance, createPollController());
      return instance;
    }

    @CanIgnoreReturnValue
    private EmojiChooserFragment injectEmojiChooserFragment2(EmojiChooserFragment instance) {
      EmojiChooserFragment_MembersInjector.injectEmojiRecyclerAdapter(instance, new EmojiRecyclerAdapter());
      return instance;
    }

    @CanIgnoreReturnValue
    private EmojiSearchResultFragment injectEmojiSearchResultFragment2(
        EmojiSearchResultFragment instance) {
      EmojiSearchResultFragment_MembersInjector.injectEpoxyController(instance, emojiSearchResultController());
      return instance;
    }

    @CanIgnoreReturnValue
    private PublicRoomsFragment injectPublicRoomsFragment2(PublicRoomsFragment instance) {
      PublicRoomsFragment_MembersInjector.injectPublicRoomsController(instance, publicRoomsController());
      PublicRoomsFragment_MembersInjector.injectPermalinkHandler(instance, activityCImpl.permalinkHandler());
      PublicRoomsFragment_MembersInjector.injectPermalinkFactory(instance, permalinkFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreateRoomFragment injectCreateRoomFragment2(CreateRoomFragment instance) {
      CreateRoomFragment_MembersInjector.injectCreateRoomController(instance, createRoomController());
      CreateRoomFragment_MembersInjector.injectCreateSpaceController(instance, createSubSpaceController());
      CreateRoomFragment_MembersInjector.injectGalleryOrCameraDialogHelperFactory(instance, galleryOrCameraDialogHelperFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomDirectoryPickerFragment injectRoomDirectoryPickerFragment2(
        RoomDirectoryPickerFragment instance) {
      RoomDirectoryPickerFragment_MembersInjector.injectRoomDirectoryPickerController(instance, roomDirectoryPickerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomPreviewNoPreviewFragment injectRoomPreviewNoPreviewFragment2(
        RoomPreviewNoPreviewFragment instance) {
      RoomPreviewNoPreviewFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomMemberProfileFragment injectRoomMemberProfileFragment2(
        RoomMemberProfileFragment instance) {
      RoomMemberProfileFragment_MembersInjector.injectRoomMemberProfileController(instance, roomMemberProfileController());
      RoomMemberProfileFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      RoomMemberProfileFragment_MembersInjector.injectRoomDetailPendingActionStore(instance, singletonCImpl.roomDetailPendingActionStoreProvider.get());
      RoomMemberProfileFragment_MembersInjector.injectMatrixItemColorProvider(instance, singletonCImpl.matrixItemColorProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private DeviceListFragment injectDeviceListFragment2(DeviceListFragment instance) {
      DeviceListFragment_MembersInjector.injectDimensionConverter(instance, singletonCImpl.dimensionConverter());
      DeviceListFragment_MembersInjector.injectEpoxyController(instance, deviceListEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private DeviceTrustInfoActionFragment injectDeviceTrustInfoActionFragment2(
        DeviceTrustInfoActionFragment instance) {
      DeviceTrustInfoActionFragment_MembersInjector.injectDimensionConverter(instance, singletonCImpl.dimensionConverter());
      DeviceTrustInfoActionFragment_MembersInjector.injectEpoxyController(instance, deviceTrustInfoEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomProfileFragment injectRoomProfileFragment2(RoomProfileFragment instance) {
      RoomProfileFragment_MembersInjector.injectRoomProfileController(instance, roomProfileController());
      RoomProfileFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      RoomProfileFragment_MembersInjector.injectRoomDetailPendingActionStore(instance, singletonCImpl.roomDetailPendingActionStoreProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomAliasFragment injectRoomAliasFragment2(RoomAliasFragment instance) {
      RoomAliasFragment_MembersInjector.injectController(instance, roomAliasController());
      RoomAliasFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomAliasBottomSheet injectRoomAliasBottomSheet2(RoomAliasBottomSheet instance) {
      RoomAliasBottomSheet_MembersInjector.injectSharedViewPool(instance, activityCImpl.providesSharedViewPoolProvider.get());
      RoomAliasBottomSheet_MembersInjector.injectController(instance, new RoomAliasBottomSheetController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomBannedMemberListFragment injectRoomBannedMemberListFragment2(
        RoomBannedMemberListFragment instance) {
      RoomBannedMemberListFragment_MembersInjector.injectRoomMemberListController(instance, roomBannedMemberListController());
      RoomBannedMemberListFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomMemberListFragment injectRoomMemberListFragment2(RoomMemberListFragment instance) {
      RoomMemberListFragment_MembersInjector.injectRoomMemberListController(instance, roomMemberListController());
      RoomMemberListFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomNotificationSettingsFragment injectRoomNotificationSettingsFragment2(
        RoomNotificationSettingsFragment instance) {
      RoomNotificationSettingsFragment_MembersInjector.injectViewModelFactory(instance, factoryProvider7.get());
      RoomNotificationSettingsFragment_MembersInjector.injectRoomNotificationSettingsController(instance, new RoomNotificationSettingsController());
      RoomNotificationSettingsFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomPermissionsFragment injectRoomPermissionsFragment2(
        RoomPermissionsFragment instance) {
      RoomPermissionsFragment_MembersInjector.injectController(instance, roomPermissionsController());
      RoomPermissionsFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomActivePollsFragment injectRoomActivePollsFragment2(
        RoomActivePollsFragment instance) {
      RoomPollsListFragment_MembersInjector.injectRoomPollsController(instance, roomPollsController());
      RoomPollsListFragment_MembersInjector.injectStringProvider(instance, singletonCImpl.stringProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomEndedPollsFragment injectRoomEndedPollsFragment2(RoomEndedPollsFragment instance) {
      RoomPollsListFragment_MembersInjector.injectRoomPollsController(instance, roomPollsController());
      RoomPollsListFragment_MembersInjector.injectStringProvider(instance, singletonCImpl.stringProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomSettingsFragment injectRoomSettingsFragment2(RoomSettingsFragment instance) {
      RoomSettingsFragment_MembersInjector.injectController(instance, roomSettingsController());
      RoomSettingsFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      RoomSettingsFragment_MembersInjector.injectGalleryOrCameraDialogHelperFactory(instance, galleryOrCameraDialogHelperFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomHistoryVisibilityBottomSheet injectRoomHistoryVisibilityBottomSheet2(
        RoomHistoryVisibilityBottomSheet instance) {
      BottomSheetGeneric_MembersInjector.injectSharedViewPool(instance, activityCImpl.providesSharedViewPoolProvider.get());
      RoomHistoryVisibilityBottomSheet_MembersInjector.injectController(instance, roomHistoryVisibilityController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomJoinRuleBottomSheet injectRoomJoinRuleBottomSheet2(
        RoomJoinRuleBottomSheet instance) {
      BottomSheetGeneric_MembersInjector.injectSharedViewPool(instance, activityCImpl.providesSharedViewPoolProvider.get());
      RoomJoinRuleBottomSheet_MembersInjector.injectController(instance, roomJoinRuleController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomJoinRuleFragment injectRoomJoinRuleFragment2(RoomJoinRuleFragment instance) {
      RoomJoinRuleFragment_MembersInjector.injectController(instance, roomJoinRuleAdvancedController());
      RoomJoinRuleFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomJoinRuleChooseRestrictedFragment injectRoomJoinRuleChooseRestrictedFragment2(
        RoomJoinRuleChooseRestrictedFragment instance) {
      RoomJoinRuleChooseRestrictedFragment_MembersInjector.injectController(instance, chooseRestrictedController());
      RoomJoinRuleChooseRestrictedFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomUploadsFragment injectRoomUploadsFragment2(RoomUploadsFragment instance) {
      RoomUploadsFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      RoomUploadsFragment_MembersInjector.injectNotificationUtils(instance, singletonCImpl.notificationUtilsProvider.get());
      RoomUploadsFragment_MembersInjector.injectClock(instance, new DefaultClock());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomUploadsFilesFragment injectRoomUploadsFilesFragment2(
        RoomUploadsFilesFragment instance) {
      RoomUploadsFilesFragment_MembersInjector.injectController(instance, uploadsFileController());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomUploadsMediaFragment injectRoomUploadsMediaFragment2(
        RoomUploadsMediaFragment instance) {
      RoomUploadsMediaFragment_MembersInjector.injectController(instance, uploadsMediaController());
      RoomUploadsMediaFragment_MembersInjector.injectDimensionConverter(instance, singletonCImpl.dimensionConverter());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsAdvancedSettingsFragment injectVectorSettingsAdvancedSettingsFragment2(
        VectorSettingsAdvancedSettingsFragment instance) {
      VectorSettingsAdvancedSettingsFragment_MembersInjector.injectNightlyProxy(instance, singletonCImpl.firebaseNightlyProxy());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsGeneralFragment injectVectorSettingsGeneralFragment2(
        VectorSettingsGeneralFragment instance) {
      VectorSettingsGeneralFragment_MembersInjector.injectGalleryOrCameraDialogHelperFactory(instance, galleryOrCameraDialogHelperFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsHelpAboutFragment injectVectorSettingsHelpAboutFragment2(
        VectorSettingsHelpAboutFragment instance) {
      VectorSettingsHelpAboutFragment_MembersInjector.injectVersionProvider(instance, singletonCImpl.versionProvider());
      VectorSettingsHelpAboutFragment_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsPinFragment injectVectorSettingsPinFragment2(
        VectorSettingsPinFragment instance) {
      VectorSettingsPinFragment_MembersInjector.injectPinCodeStore(instance, singletonCImpl.sharedPrefPinCodeStoreProvider.get());
      VectorSettingsPinFragment_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorSettingsPinFragment_MembersInjector.injectNotificationDrawerManager(instance, singletonCImpl.notificationDrawerManagerProvider.get());
      VectorSettingsPinFragment_MembersInjector.injectBiometricHelperFactory(instance, biometricHelperFactoryProvider.get());
      VectorSettingsPinFragment_MembersInjector.injectDefaultLockScreenConfiguration(instance, LockScreenModule_ProvideLockScreenConfigFactory.provideLockScreenConfig());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsPreferencesFragment injectVectorSettingsPreferencesFragment2(
        VectorSettingsPreferencesFragment instance) {
      VectorSettingsPreferencesFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorSettingsPreferencesFragment_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorSettingsPreferencesFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorSettingsPreferencesFragment_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsSecurityPrivacyFragment injectVectorSettingsSecurityPrivacyFragment2(
        VectorSettingsSecurityPrivacyFragment instance) {
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectPinCodeStore(instance, singletonCImpl.sharedPrefPinCodeStoreProvider.get());
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectKeysExporter(instance, activityCImpl.keysExporter());
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectKeysImporter(instance, keysImporter());
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectRawService(instance, singletonCImpl.rawService());
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectAnalyticsConfig(instance, ConfigurationModule_ProvidesAnalyticsConfigFactory.providesAnalyticsConfig());
      VectorSettingsSecurityPrivacyFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsVoiceVideoFragment injectVectorSettingsVoiceVideoFragment2(
        VectorSettingsVoiceVideoFragment instance) {
      VectorSettingsVoiceVideoFragment_MembersInjector.injectRingtoneUtils(instance, ringtoneUtils());
      return instance;
    }

    @CanIgnoreReturnValue
    private CrossSigningSettingsFragment injectCrossSigningSettingsFragment2(
        CrossSigningSettingsFragment instance) {
      CrossSigningSettingsFragment_MembersInjector.injectController(instance, crossSigningSettingsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private DeviceVerificationInfoBottomSheet injectDeviceVerificationInfoBottomSheet2(
        DeviceVerificationInfoBottomSheet instance) {
      DeviceVerificationInfoBottomSheet_MembersInjector.injectController(instance, deviceVerificationInfoBottomSheetController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsDevicesFragment injectVectorSettingsDevicesFragment2(
        VectorSettingsDevicesFragment instance) {
      VectorSettingsDevicesFragment_MembersInjector.injectDevicesController(instance, devicesController());
      return instance;
    }

    @CanIgnoreReturnValue
    private im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment injectVectorSettingsDevicesFragment3(
        im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment instance) {
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_MembersInjector.injectViewNavigator(instance, new VectorSettingsDevicesViewNavigator());
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_MembersInjector.injectDateFormatter(instance, singletonCImpl.vectorDateFormatter());
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_MembersInjector.injectDrawableProvider(instance, singletonCImpl.drawableProvider());
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_MembersInjector.injectStringProvider(instance, singletonCImpl.stringProvider());
      im.vector.app.features.settings.devices.v2.VectorSettingsDevicesFragment_MembersInjector.injectBuildConfirmSignoutDialogUseCase(instance, new BuildConfirmSignoutDialogUseCase());
      return instance;
    }

    @CanIgnoreReturnValue
    private SessionDetailsFragment injectSessionDetailsFragment2(SessionDetailsFragment instance) {
      SessionDetailsFragment_MembersInjector.injectSessionDetailsController(instance, sessionDetailsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private OtherSessionsFragment injectOtherSessionsFragment2(OtherSessionsFragment instance) {
      OtherSessionsFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      OtherSessionsFragment_MembersInjector.injectStringProvider(instance, singletonCImpl.stringProvider());
      OtherSessionsFragment_MembersInjector.injectViewNavigator(instance, new OtherSessionsViewNavigator());
      OtherSessionsFragment_MembersInjector.injectBuildConfirmSignoutDialogUseCase(instance, new BuildConfirmSignoutDialogUseCase());
      return instance;
    }

    @CanIgnoreReturnValue
    private SessionOverviewFragment injectSessionOverviewFragment2(
        SessionOverviewFragment instance) {
      SessionOverviewFragment_MembersInjector.injectViewNavigator(instance, new SessionOverviewViewNavigator());
      SessionOverviewFragment_MembersInjector.injectDateFormatter(instance, singletonCImpl.vectorDateFormatter());
      SessionOverviewFragment_MembersInjector.injectDrawableProvider(instance, singletonCImpl.drawableProvider());
      SessionOverviewFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      SessionOverviewFragment_MembersInjector.injectStringProvider(instance, singletonCImpl.stringProvider());
      SessionOverviewFragment_MembersInjector.injectBuildConfirmSignoutDialogUseCase(instance, new BuildConfirmSignoutDialogUseCase());
      return instance;
    }

    @CanIgnoreReturnValue
    private RenameSessionFragment injectRenameSessionFragment2(RenameSessionFragment instance) {
      RenameSessionFragment_MembersInjector.injectViewNavigator(instance, new RenameSessionViewNavigator());
      return instance;
    }

    @CanIgnoreReturnValue
    private AccountDataFragment injectAccountDataFragment2(AccountDataFragment instance) {
      AccountDataFragment_MembersInjector.injectEpoxyController(instance, accountDataEpoxyController());
      AccountDataFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private GossipingEventsPaperTrailFragment injectGossipingEventsPaperTrailFragment2(
        GossipingEventsPaperTrailFragment instance) {
      GossipingEventsPaperTrailFragment_MembersInjector.injectEpoxyController(instance, gossipingTrailPagedEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private IncomingKeyRequestListFragment injectIncomingKeyRequestListFragment2(
        IncomingKeyRequestListFragment instance) {
      IncomingKeyRequestListFragment_MembersInjector.injectEpoxyController(instance, incomingKeyRequestPagedController());
      return instance;
    }

    @CanIgnoreReturnValue
    private KeyRequestsFragment injectKeyRequestsFragment2(KeyRequestsFragment instance) {
      KeyRequestsFragment_MembersInjector.injectClock(instance, new DefaultClock());
      return instance;
    }

    @CanIgnoreReturnValue
    private OutgoingKeyRequestListFragment injectOutgoingKeyRequestListFragment2(
        OutgoingKeyRequestListFragment instance) {
      OutgoingKeyRequestListFragment_MembersInjector.injectEpoxyController(instance, new OutgoingKeyRequestPagedController());
      return instance;
    }

    @CanIgnoreReturnValue
    private FontScaleSettingFragment injectFontScaleSettingFragment2(
        FontScaleSettingFragment instance) {
      FontScaleSettingFragment_MembersInjector.injectFontListController(instance, fontScaleSettingController());
      return instance;
    }

    @CanIgnoreReturnValue
    private HomeserverSettingsFragment injectHomeserverSettingsFragment2(
        HomeserverSettingsFragment instance) {
      HomeserverSettingsFragment_MembersInjector.injectHomeserverSettingsController(instance, homeserverSettingsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsIgnoredUsersFragment injectVectorSettingsIgnoredUsersFragment2(
        VectorSettingsIgnoredUsersFragment instance) {
      VectorSettingsIgnoredUsersFragment_MembersInjector.injectIgnoredUsersController(instance, ignoredUsersController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsLabsFragment injectVectorSettingsLabsFragment2(
        VectorSettingsLabsFragment instance) {
      VectorSettingsLabsFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorSettingsLabsFragment_MembersInjector.injectLightweightSettingsStorage(instance, singletonCImpl.lightweightSettingsStorage());
      VectorSettingsLabsFragment_MembersInjector.injectThreadsManager(instance, threadsManager());
      VectorSettingsLabsFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      return instance;
    }

    @CanIgnoreReturnValue
    private LegalsFragment injectLegalsFragment2(LegalsFragment instance) {
      LegalsFragment_MembersInjector.injectController(instance, legalsController());
      LegalsFragment_MembersInjector.injectFlavorLegals(instance, new GoogleFlavorLegals());
      return instance;
    }

    @CanIgnoreReturnValue
    private LocalePickerFragment injectLocalePickerFragment2(LocalePickerFragment instance) {
      LocalePickerFragment_MembersInjector.injectController(instance, localePickerController());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsNotificationPreferenceFragment injectVectorSettingsNotificationPreferenceFragment2(
        VectorSettingsNotificationPreferenceFragment instance) {
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectUnifiedPushHelper(instance, singletonCImpl.unifiedPushHelper());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectPushersManager(instance, singletonCImpl.pushersManager());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectFcmHelper(instance, singletonCImpl.googleFcmHelper());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectGuardServiceStarter(instance, FlavorModule_Companion_ProvideGuardServiceStarterFactory.provideGuardServiceStarter());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectNotificationPermissionManager(instance, activityCImpl.notificationPermissionManager());
      VectorSettingsNotificationPreferenceFragment_MembersInjector.injectEnsureFcmTokenIsRetrievedUseCase(instance, ensureFcmTokenIsRetrievedUseCase());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsNotificationsTroubleshootFragment injectVectorSettingsNotificationsTroubleshootFragment2(
        VectorSettingsNotificationsTroubleshootFragment instance) {
      VectorSettingsNotificationsTroubleshootFragment_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorSettingsNotificationsTroubleshootFragment_MembersInjector.injectTestManagerFactory(instance, activityCImpl.googleNotificationTroubleshootTestManagerFactory());
      VectorSettingsNotificationsTroubleshootFragment_MembersInjector.injectActionIds(instance, singletonCImpl.notificationActionIds());
      return instance;
    }

    @CanIgnoreReturnValue
    private PushGatewaysFragment injectPushGatewaysFragment2(PushGatewaysFragment instance) {
      PushGatewaysFragment_MembersInjector.injectEpoxyController(instance, pushGateWayController());
      return instance;
    }

    @CanIgnoreReturnValue
    private PushRulesFragment injectPushRulesFragment2(PushRulesFragment instance) {
      PushRulesFragment_MembersInjector.injectEpoxyController(instance, pushRulesController());
      return instance;
    }

    @CanIgnoreReturnValue
    private ThreePidsSettingsFragment injectThreePidsSettingsFragment2(
        ThreePidsSettingsFragment instance) {
      ThreePidsSettingsFragment_MembersInjector.injectEpoxyController(instance, threePidsSettingsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private IncomingShareFragment injectIncomingShareFragment2(IncomingShareFragment instance) {
      IncomingShareFragment_MembersInjector.injectIncomingShareController(instance, incomingShareController());
      IncomingShareFragment_MembersInjector.injectShareIntentHandler(instance, shareIntentHandler());
      return instance;
    }

    @CanIgnoreReturnValue
    private SoftLogoutFragment injectSoftLogoutFragment2(SoftLogoutFragment instance) {
      SoftLogoutFragment_MembersInjector.injectSoftLogoutController(instance, softLogoutController());
      return instance;
    }

    @CanIgnoreReturnValue
    private InviteRoomSpaceChooserBottomSheet injectInviteRoomSpaceChooserBottomSheet2(
        InviteRoomSpaceChooserBottomSheet instance) {
      InviteRoomSpaceChooserBottomSheet_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceListFragment injectSpaceListFragment2(SpaceListFragment instance) {
      SpaceListFragment_MembersInjector.injectSpaceController(instance, spaceSummaryController());
      SpaceListFragment_MembersInjector.injectNewSpaceController(instance, newSpaceSummaryController());
      SpaceListFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceSettingsMenuBottomSheet injectSpaceSettingsMenuBottomSheet2(
        SpaceSettingsMenuBottomSheet instance) {
      SpaceSettingsMenuBottomSheet_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      SpaceSettingsMenuBottomSheet_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      SpaceSettingsMenuBottomSheet_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private ChoosePrivateSpaceTypeFragment injectChoosePrivateSpaceTypeFragment2(
        ChoosePrivateSpaceTypeFragment instance) {
      ChoosePrivateSpaceTypeFragment_MembersInjector.injectStringProvider(instance, singletonCImpl.stringProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreateSpaceAdd3pidInvitesFragment injectCreateSpaceAdd3pidInvitesFragment2(
        CreateSpaceAdd3pidInvitesFragment instance) {
      CreateSpaceAdd3pidInvitesFragment_MembersInjector.injectEpoxyController(instance, spaceAdd3pidEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreateSpaceDefaultRoomsFragment injectCreateSpaceDefaultRoomsFragment2(
        CreateSpaceDefaultRoomsFragment instance) {
      CreateSpaceDefaultRoomsFragment_MembersInjector.injectEpoxyController(instance, spaceDefaultRoomEpoxyController());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreateSpaceDetailsFragment injectCreateSpaceDetailsFragment2(
        CreateSpaceDetailsFragment instance) {
      CreateSpaceDetailsFragment_MembersInjector.injectEpoxyController(instance, spaceDetailEpoxyController());
      CreateSpaceDetailsFragment_MembersInjector.injectGalleryOrCameraDialogHelperFactory(instance, galleryOrCameraDialogHelperFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceDirectoryFragment injectSpaceDirectoryFragment2(SpaceDirectoryFragment instance) {
      SpaceDirectoryFragment_MembersInjector.injectEpoxyController(instance, spaceDirectoryController());
      SpaceDirectoryFragment_MembersInjector.injectPermalinkHandler(instance, activityCImpl.permalinkHandler());
      SpaceDirectoryFragment_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceInviteBottomSheet injectSpaceInviteBottomSheet2(SpaceInviteBottomSheet instance) {
      SpaceInviteBottomSheet_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      SpaceInviteBottomSheet_MembersInjector.injectSpaceCardRenderer(instance, spaceCardRenderer());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceLeaveAdvancedFragment injectSpaceLeaveAdvancedFragment2(
        SpaceLeaveAdvancedFragment instance) {
      SpaceLeaveAdvancedFragment_MembersInjector.injectController(instance, selectChildrenController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceAddRoomFragment injectSpaceAddRoomFragment2(SpaceAddRoomFragment instance) {
      SpaceAddRoomFragment_MembersInjector.injectSpaceEpoxyController(instance, addRoomListController());
      SpaceAddRoomFragment_MembersInjector.injectRoomEpoxyController(instance, addRoomListController());
      SpaceAddRoomFragment_MembersInjector.injectDmEpoxyController(instance, addRoomListController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceManageRoomsFragment injectSpaceManageRoomsFragment2(
        SpaceManageRoomsFragment instance) {
      SpaceManageRoomsFragment_MembersInjector.injectEpoxyController(instance, spaceManageRoomsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceSettingsFragment injectSpaceSettingsFragment2(SpaceSettingsFragment instance) {
      SpaceSettingsFragment_MembersInjector.injectEpoxyController(instance, spaceSettingsController());
      SpaceSettingsFragment_MembersInjector.injectGalleryOrCameraDialogHelperFactory(instance, galleryOrCameraDialogHelperFactory());
      SpaceSettingsFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpacePeopleFragment injectSpacePeopleFragment2(SpacePeopleFragment instance) {
      SpacePeopleFragment_MembersInjector.injectEpoxyController(instance, spacePeopleListController());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpacePreviewFragment injectSpacePreviewFragment2(SpacePreviewFragment instance) {
      SpacePreviewFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      SpacePreviewFragment_MembersInjector.injectEpoxyController(instance, spacePreviewController());
      return instance;
    }

    @CanIgnoreReturnValue
    private ReviewTermsFragment injectReviewTermsFragment2(ReviewTermsFragment instance) {
      ReviewTermsFragment_MembersInjector.injectTermsController(instance, termsController());
      return instance;
    }

    @CanIgnoreReturnValue
    private ShowUserCodeFragment injectShowUserCodeFragment2(ShowUserCodeFragment instance) {
      ShowUserCodeFragment_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private UserListFragment injectUserListFragment2(UserListFragment instance) {
      UserListFragment_MembersInjector.injectUserListController(instance, userListController());
      UserListFragment_MembersInjector.injectDimensionConverter(instance, singletonCImpl.dimensionConverter());
      return instance;
    }

    @CanIgnoreReturnValue
    private WidgetFragment injectWidgetFragment2(WidgetFragment instance) {
      WidgetFragment_MembersInjector.injectPermissionUtils(instance, webviewPermissionUtils());
      WidgetFragment_MembersInjector.injectCheckWebViewPermissionsUseCase(instance, new CheckWebViewPermissionsUseCase());
      WidgetFragment_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomWidgetPermissionBottomSheet injectRoomWidgetPermissionBottomSheet2(
        RoomWidgetPermissionBottomSheet instance) {
      RoomWidgetPermissionBottomSheet_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ActivityCImpl activityCImpl;

      private final FragmentCImpl fragmentCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ActivityCImpl activityCImpl, FragmentCImpl fragmentCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.activityCImpl = activityCImpl;
        this.fragmentCImpl = fragmentCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // im.vector.app.features.home.room.detail.timeline.render.EventTextRenderer.Factory 
          return (T) new EventTextRenderer.Factory() {
            @Override
            public EventTextRenderer create(String roomId) {
              return new EventTextRenderer(roomId, singletonCImpl.context(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.activeSessionHolderProvider.get());
            }
          };

          case 1: // im.vector.app.features.html.PillsPostProcessor.Factory 
          return (T) new PillsPostProcessor.Factory() {
            @Override
            public PillsPostProcessor create(String roomId2) {
              return new PillsPostProcessor(roomId2, singletonCImpl.context(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.activeSessionHolderProvider.get());
            }
          };

          case 2: // im.vector.app.features.home.room.detail.AutoCompleter.Factory 
          return (T) new AutoCompleter.Factory() {
            @Override
            public AutoCompleter create(String roomId3, boolean isInThreadTimeline) {
              return new AutoCompleter(roomId3, isInThreadTimeline, singletonCImpl.avatarRendererProvider.get(), new CommandAutocompletePolicy(), fragmentCImpl.factoryProvider3.get(), fragmentCImpl.factoryProvider4.get(), fragmentCImpl.autocompleteRoomPresenter(), fragmentCImpl.autocompleteEmojiPresenter());
            }
          };

          case 3: // im.vector.app.features.autocomplete.command.AutocompleteCommandPresenter.Factory 
          return (T) new AutocompleteCommandPresenter.Factory() {
            @Override
            public AutocompleteCommandPresenter create(boolean isInThreadTimeline2) {
              return new AutocompleteCommandPresenter(isInThreadTimeline2, singletonCImpl.context(), fragmentCImpl.autocompleteCommandController(), singletonCImpl.vectorPreferences());
            }
          };

          case 4: // im.vector.app.features.autocomplete.member.AutocompleteMemberPresenter.Factory 
          return (T) new AutocompleteMemberPresenter.Factory() {
            @Override
            public AutocompleteMemberPresenter create(String roomId4) {
              return new AutocompleteMemberPresenter(singletonCImpl.context(), roomId4, singletonCImpl.session(), fragmentCImpl.autocompleteMemberController());
            }
          };

          case 5: // im.vector.app.features.home.room.threads.list.viewmodel.ThreadListViewModel.Factory 
          return (T) new ThreadListViewModel.Factory() {
            @Override
            public ThreadListViewModel create(ThreadListViewState initialState) {
              return new ThreadListViewModel(initialState, singletonCImpl.defaultVectorAnalyticsProvider.get(), singletonCImpl.session());
            }
          };

          case 6: // im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsViewModel.Factory 
          return (T) new RoomNotificationSettingsViewModel.Factory() {
            @Override
            public RoomNotificationSettingsViewModel create(
                RoomNotificationSettingsViewState initialState2) {
              return new RoomNotificationSettingsViewModel(initialState2, singletonCImpl.session());
            }
          };

          case 7: // im.vector.app.features.pin.lockscreen.biometrics.BiometricHelper.BiometricHelperFactory 
          return (T) new BiometricHelper.BiometricHelperFactory() {
            @Override
            public BiometricHelper create(LockScreenConfiguration configuration) {
              return new BiometricHelper(configuration, ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.lockScreenKeyRepositoryProvider.get(), singletonCImpl.biometricManager(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider());
            }
          };

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ViewCImpl extends VectorApplication_HiltComponents.ViewC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl;

    private final ViewCImpl viewCImpl = this;

    private Provider<PillsPostProcessor.Factory> factoryProvider;

    private ViewCImpl(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
        ActivityCImpl activityCImpl, View viewParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activityCImpl = activityCImpl;

      initialize(viewParam);

    }

    private TypingHelper typingHelper() {
      return new TypingHelper(singletonCImpl.stringProvider());
    }

    private OtherSessionsController otherSessionsController() {
      return new OtherSessionsController(singletonCImpl.stringProvider(), singletonCImpl.vectorDateFormatter(), singletonCImpl.drawableProvider(), singletonCImpl.colorProvider());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final View viewParam) {
      this.factoryProvider = SingleCheck.provider(new SwitchingProvider<PillsPostProcessor.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, viewCImpl, 0));
    }

    @Override
    public void injectTypingMessageView(TypingMessageView arg0) {
      injectTypingMessageView2(arg0);
    }

    @Override
    public void injectPlainTextComposerLayout(PlainTextComposerLayout arg0) {
      injectPlainTextComposerLayout2(arg0);
    }

    @Override
    public void injectVoiceMessageRecorderView(VoiceMessageRecorderView arg0) {
      injectVoiceMessageRecorderView2(arg0);
    }

    @Override
    public void injectVectorInviteView(VectorInviteView arg0) {
      injectVectorInviteView2(arg0);
    }

    @Override
    public void injectReactionButton(ReactionButton arg0) {
      injectReactionButton2(arg0);
    }

    @Override
    public void injectOtherSessionsView(OtherSessionsView arg0) {
      injectOtherSessionsView2(arg0);
    }

    @Override
    public void injectOtherSessionsSecurityRecommendationView(
        OtherSessionsSecurityRecommendationView arg0) {
    }

    @CanIgnoreReturnValue
    private TypingMessageView injectTypingMessageView2(TypingMessageView instance) {
      TypingMessageView_MembersInjector.injectTypingHelper(instance, typingHelper());
      return instance;
    }

    @CanIgnoreReturnValue
    private PlainTextComposerLayout injectPlainTextComposerLayout2(
        PlainTextComposerLayout instance) {
      PlainTextComposerLayout_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      PlainTextComposerLayout_MembersInjector.injectMatrixItemColorProvider(instance, singletonCImpl.matrixItemColorProvider.get());
      PlainTextComposerLayout_MembersInjector.injectEventHtmlRenderer(instance, singletonCImpl.eventHtmlRendererProvider.get());
      PlainTextComposerLayout_MembersInjector.injectDimensionConverter(instance, singletonCImpl.dimensionConverter());
      PlainTextComposerLayout_MembersInjector.injectImageContentRenderer(instance, activityCImpl.imageContentRenderer());
      PlainTextComposerLayout_MembersInjector.injectPillsPostProcessorFactory(instance, factoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VoiceMessageRecorderView injectVoiceMessageRecorderView2(
        VoiceMessageRecorderView instance) {
      VoiceMessageRecorderView_MembersInjector.injectClock(instance, new DefaultClock());
      VoiceMessageRecorderView_MembersInjector.injectVoiceMessageConfig(instance, ConfigurationModule_ProvidesVoiceMessageConfigFactory.providesVoiceMessageConfig());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorInviteView injectVectorInviteView2(VectorInviteView instance) {
      VectorInviteView_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private ReactionButton injectReactionButton2(ReactionButton instance) {
      ReactionButton_MembersInjector.injectEmojiSpanify(instance, singletonCImpl.emojiCompatWrapperProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private OtherSessionsView injectOtherSessionsView2(OtherSessionsView instance) {
      OtherSessionsView_MembersInjector.injectOtherSessionsController(instance, otherSessionsController());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ActivityCImpl activityCImpl;

      private final ViewCImpl viewCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ActivityCImpl activityCImpl, ViewCImpl viewCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.activityCImpl = activityCImpl;
        this.viewCImpl = viewCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // im.vector.app.features.html.PillsPostProcessor.Factory 
          return (T) new PillsPostProcessor.Factory() {
            @Override
            public PillsPostProcessor create(String roomId) {
              return new PillsPostProcessor(roomId, singletonCImpl.context(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.activeSessionHolderProvider.get());
            }
          };

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ActivityCImpl extends VectorApplication_HiltComponents.ActivityC {
    private final Activity activity;

    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ActivityCImpl activityCImpl = this;

    private Provider<EmojiChooserViewModel> emojiChooserViewModelProvider;

    private Provider<KeysBackupRestoreFromKeyViewModel> keysBackupRestoreFromKeyViewModelProvider;

    private Provider<KeysBackupRestoreSharedViewModel> keysBackupRestoreSharedViewModelProvider;

    private Provider<KeysBackupRestoreFromPassphraseViewModel> keysBackupRestoreFromPassphraseViewModelProvider;

    private Provider<KeysBackupSetupSharedViewModel> keysBackupSetupSharedViewModelProvider;

    private Provider<ConfigurationViewModel> configurationViewModelProvider;

    private Provider<SharedKnownCallsViewModel> sharedKnownCallsViewModelProvider;

    private Provider<UserListSharedActionViewModel> userListSharedActionViewModelProvider;

    private Provider<HomeSharedActionViewModel> homeSharedActionViewModelProvider;

    private Provider<MessageSharedActionViewModel> messageSharedActionViewModelProvider;

    private Provider<RoomListQuickActionsSharedActionViewModel> roomListQuickActionsSharedActionViewModelProvider;

    private Provider<RoomAliasBottomSheetSharedActionViewModel> roomAliasBottomSheetSharedActionViewModelProvider;

    private Provider<RoomHistoryVisibilitySharedActionViewModel> roomHistoryVisibilitySharedActionViewModelProvider;

    private Provider<RoomJoinRuleSharedActionViewModel> roomJoinRuleSharedActionViewModelProvider;

    private Provider<RoomDirectorySharedActionViewModel> roomDirectorySharedActionViewModelProvider;

    private Provider<RoomDetailSharedActionViewModel> roomDetailSharedActionViewModelProvider;

    private Provider<RoomProfileSharedActionViewModel> roomProfileSharedActionViewModelProvider;

    private Provider<DiscoverySharedViewModel> discoverySharedViewModelProvider;

    private Provider<SpacePreviewSharedActionViewModel> spacePreviewSharedActionViewModelProvider;

    private Provider<SpacePeopleSharedActionViewModel> spacePeopleSharedActionViewModelProvider;

    private Provider<RoomListSharedActionViewModel> roomListSharedActionViewModelProvider;

    private Provider<FragmentActivity> provideFragmentActivityProvider;

    private Provider<RoomDirectoryViewModel.Factory> factoryProvider;

    private Provider<ContentUploadStateTrackerBinder> contentUploadStateTrackerBinderProvider;

    private Provider<ContentDownloadStateTrackerBinder> contentDownloadStateTrackerBinderProvider;

    private Provider<TimelineMediaSizeProvider> timelineMediaSizeProvider;

    private Provider<ReactionsSummaryFactory> reactionsSummaryFactoryProvider;

    private Provider<RecyclerView.RecycledViewPool> providesSharedViewPoolProvider;

    private ActivityCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, Activity activityParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;
      this.activity = activityParam;
      initialize(activityParam);

    }

    private Map<Class<? extends ViewModel>, Provider<ViewModel>> mapOfClassOfAndProviderOfViewModel(
        ) {
      return ImmutableMap.<Class<? extends ViewModel>, Provider<ViewModel>>builderWithExpectedSize(21).put(EmojiChooserViewModel.class, ((Provider) emojiChooserViewModelProvider)).put(KeysBackupRestoreFromKeyViewModel.class, ((Provider) keysBackupRestoreFromKeyViewModelProvider)).put(KeysBackupRestoreSharedViewModel.class, ((Provider) keysBackupRestoreSharedViewModelProvider)).put(KeysBackupRestoreFromPassphraseViewModel.class, ((Provider) keysBackupRestoreFromPassphraseViewModelProvider)).put(KeysBackupSetupSharedViewModel.class, ((Provider) keysBackupSetupSharedViewModelProvider)).put(ConfigurationViewModel.class, ((Provider) configurationViewModelProvider)).put(SharedKnownCallsViewModel.class, ((Provider) sharedKnownCallsViewModelProvider)).put(UserListSharedActionViewModel.class, ((Provider) userListSharedActionViewModelProvider)).put(HomeSharedActionViewModel.class, ((Provider) homeSharedActionViewModelProvider)).put(MessageSharedActionViewModel.class, ((Provider) messageSharedActionViewModelProvider)).put(RoomListQuickActionsSharedActionViewModel.class, ((Provider) roomListQuickActionsSharedActionViewModelProvider)).put(RoomAliasBottomSheetSharedActionViewModel.class, ((Provider) roomAliasBottomSheetSharedActionViewModelProvider)).put(RoomHistoryVisibilitySharedActionViewModel.class, ((Provider) roomHistoryVisibilitySharedActionViewModelProvider)).put(RoomJoinRuleSharedActionViewModel.class, ((Provider) roomJoinRuleSharedActionViewModelProvider)).put(RoomDirectorySharedActionViewModel.class, ((Provider) roomDirectorySharedActionViewModelProvider)).put(RoomDetailSharedActionViewModel.class, ((Provider) roomDetailSharedActionViewModelProvider)).put(RoomProfileSharedActionViewModel.class, ((Provider) roomProfileSharedActionViewModelProvider)).put(DiscoverySharedViewModel.class, ((Provider) discoverySharedViewModelProvider)).put(SpacePreviewSharedActionViewModel.class, ((Provider) spacePreviewSharedActionViewModelProvider)).put(SpacePeopleSharedActionViewModel.class, ((Provider) spacePeopleSharedActionViewModelProvider)).put(RoomListSharedActionViewModel.class, ((Provider) roomListSharedActionViewModelProvider)).build();
    }

    private VectorViewModelFactory vectorViewModelFactory() {
      return new VectorViewModelFactory(mapOfClassOfAndProviderOfViewModel());
    }

    private RageShake rageShake() {
      return new RageShake(provideFragmentActivityProvider.get(), singletonCImpl.bugReporterProvider.get(), singletonCImpl.defaultNavigatorProvider.get(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.vectorPreferences());
    }

    private ShortcutCreator shortcutCreator() {
      return injectShortcutCreator(ShortcutCreator_Factory.newInstance(singletonCImpl.context(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.dimensionConverter(), singletonCImpl.providesBuildMetaProvider.get()));
    }

    private ShortcutsHandler shortcutsHandler() {
      return new ShortcutsHandler(singletonCImpl.context(), singletonCImpl.stringProvider(), VectorStaticModule_ProvidesCoroutineDispatchersFactory.providesCoroutineDispatchers(), shortcutCreator(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.sharedPrefPinCodeStoreProvider.get(), singletonCImpl.providesDefaultSharedPreferencesProvider.get());
    }

    private PinCodeHelper pinCodeHelper() {
      return new PinCodeHelper(singletonCImpl.lockScreenKeyRepositoryProvider.get(), singletonCImpl.sharedPrefPinCodeStoreProvider.get());
    }

    private ScreenOrientationLocker screenOrientationLocker() {
      return new ScreenOrientationLocker(singletonCImpl.resources());
    }

    private ScreenCaptureServiceConnection screenCaptureServiceConnection() {
      return new ScreenCaptureServiceConnection(singletonCImpl.context());
    }

    private DirectRoomHelper directRoomHelper() {
      return new DirectRoomHelper(singletonCImpl.rawService(), singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get(), singletonCImpl.vectorPreferences());
    }

    private KeysExporter keysExporter() {
      return new KeysExporter(singletonCImpl.session(), singletonCImpl.context(), VectorStaticModule_ProvidesCoroutineDispatchersFactory.providesCoroutineDispatchers());
    }

    private DebugFeaturesStateFactory debugFeaturesStateFactory() {
      return new DebugFeaturesStateFactory(singletonCImpl.debugVectorFeatures(), FeaturesModule_Companion_ProvidesDefaultVectorFeaturesFactory.providesDefaultVectorFeatures());
    }

    private UserPreferencesProvider userPreferencesProvider() {
      return new UserPreferencesProvider(singletonCImpl.vectorPreferences());
    }

    private PermalinkHandler permalinkHandler() {
      return new PermalinkHandler(singletonCImpl.activeSessionHolderProvider.get(), userPreferencesProvider(), singletonCImpl.defaultNavigatorProvider.get());
    }

    private InitSyncStepFormatter initSyncStepFormatter() {
      return new InitSyncStepFormatter(singletonCImpl.stringProvider());
    }

    private NotificationPermissionManager notificationPermissionManager() {
      return new NotificationPermissionManager(LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider(), singletonCImpl.vectorPreferences());
    }

    private ReleaseNotesPreferencesStore releaseNotesPreferencesStore() {
      return new ReleaseNotesPreferencesStore(singletonCImpl.context());
    }

    private LocalFilesHelper localFilesHelper() {
      return new LocalFilesHelper(singletonCImpl.context());
    }

    private ImageContentRenderer imageContentRenderer() {
      return new ImageContentRenderer(localFilesHelper(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.dimensionConverter());
    }

    private AttachmentProviderFactory attachmentProviderFactory() {
      return new AttachmentProviderFactory(imageContentRenderer(), singletonCImpl.vectorDateFormatter(), singletonCImpl.stringProvider(), singletonCImpl.session());
    }

    private OnboardingVariantFactory onboardingVariantFactory() {
      return new OnboardingVariantFactory(singletonCImpl.debugVectorFeatures(), screenOrientationLocker());
    }

    private AssetReader assetReader() {
      return new AssetReader(singletonCImpl.context());
    }

    private ExplicitTermFilter explicitTermFilter() {
      return new ExplicitTermFilter(assetReader());
    }

    private MessageColorProvider messageColorProvider() {
      return new MessageColorProvider(singletonCImpl.colorProvider(), singletonCImpl.matrixItemColorProvider.get(), singletonCImpl.vectorPreferences());
    }

    private TestSystemSettings testSystemSettings() {
      return new TestSystemSettings(provideFragmentActivityProvider.get(), singletonCImpl.stringProvider(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider(), notificationPermissionManager());
    }

    private TestAccountSettings testAccountSettings() {
      return new TestAccountSettings(singletonCImpl.stringProvider(), singletonCImpl.activeSessionHolderProvider.get());
    }

    private TestDeviceSettings testDeviceSettings() {
      return new TestDeviceSettings(singletonCImpl.vectorPreferences(), singletonCImpl.stringProvider());
    }

    private TestPushRulesSettings testPushRulesSettings() {
      return new TestPushRulesSettings(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.stringProvider());
    }

    private TestPlayServices testPlayServices() {
      return new TestPlayServices(provideFragmentActivityProvider.get(), singletonCImpl.stringProvider());
    }

    private TestFirebaseToken testFirebaseToken() {
      return new TestFirebaseToken(provideFragmentActivityProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.googleFcmHelper());
    }

    private TestTokenRegistration testTokenRegistration() {
      return new TestTokenRegistration(provideFragmentActivityProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.pushersManager(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.googleFcmHelper());
    }

    private TestCurrentUnifiedPushDistributor testCurrentUnifiedPushDistributor() {
      return new TestCurrentUnifiedPushDistributor(singletonCImpl.unifiedPushHelper(), singletonCImpl.stringProvider());
    }

    private TestUnifiedPushGateway testUnifiedPushGateway() {
      return new TestUnifiedPushGateway(singletonCImpl.unifiedPushHelper(), singletonCImpl.stringProvider());
    }

    private TestUnifiedPushEndpoint testUnifiedPushEndpoint() {
      return new TestUnifiedPushEndpoint(singletonCImpl.stringProvider(), singletonCImpl.unifiedPushHelper());
    }

    private TestAvailableUnifiedPushDistributors testAvailableUnifiedPushDistributors() {
      return new TestAvailableUnifiedPushDistributors(singletonCImpl.unifiedPushHelper(), singletonCImpl.stringProvider(), singletonCImpl.googleFcmHelper());
    }

    private RegisterUnifiedPushUseCase registerUnifiedPushUseCase() {
      return new RegisterUnifiedPushUseCase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.debugVectorFeatures());
    }

    private TestEndpointAsTokenRegistration testEndpointAsTokenRegistration() {
      return new TestEndpointAsTokenRegistration(provideFragmentActivityProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.pushersManager(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.unifiedPushHelper(), registerUnifiedPushUseCase(), singletonCImpl.unregisterUnifiedPushUseCase());
    }

    private TestPushFromPushGateway testPushFromPushGateway() {
      return new TestPushFromPushGateway(singletonCImpl.stringProvider(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.pushersManager(), singletonCImpl.activeSessionHolderProvider.get());
    }

    private TestNotification testNotification() {
      return new TestNotification(singletonCImpl.context(), singletonCImpl.notificationUtilsProvider.get(), singletonCImpl.stringProvider());
    }

    private GoogleNotificationTroubleshootTestManagerFactory googleNotificationTroubleshootTestManagerFactory(
        ) {
      return new GoogleNotificationTroubleshootTestManagerFactory(singletonCImpl.unifiedPushHelper(), testSystemSettings(), testAccountSettings(), testDeviceSettings(), testPushRulesSettings(), testPlayServices(), testFirebaseToken(), testTokenRegistration(), testCurrentUnifiedPushDistributor(), testUnifiedPushGateway(), testUnifiedPushEndpoint(), testAvailableUnifiedPushDistributors(), testEndpointAsTokenRegistration(), testPushFromPushGateway(), testNotification(), singletonCImpl.debugVectorFeatures());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final Activity activityParam) {
      this.emojiChooserViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 0);
      this.keysBackupRestoreFromKeyViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 1);
      this.keysBackupRestoreSharedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 2);
      this.keysBackupRestoreFromPassphraseViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 3);
      this.keysBackupSetupSharedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 4);
      this.configurationViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 5);
      this.sharedKnownCallsViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 6);
      this.userListSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 7);
      this.homeSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 8);
      this.messageSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 9);
      this.roomListQuickActionsSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 10);
      this.roomAliasBottomSheetSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 11);
      this.roomHistoryVisibilitySharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 12);
      this.roomJoinRuleSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 13);
      this.roomDirectorySharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 14);
      this.roomDetailSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 15);
      this.roomProfileSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 16);
      this.discoverySharedViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 17);
      this.spacePreviewSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 18);
      this.spacePeopleSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 19);
      this.roomListSharedActionViewModelProvider = new SwitchingProvider<>(singletonCImpl, activityRetainedCImpl, activityCImpl, 20);
      this.provideFragmentActivityProvider = SingleCheck.provider(new SwitchingProvider<FragmentActivity>(singletonCImpl, activityRetainedCImpl, activityCImpl, 21));
      this.factoryProvider = SingleCheck.provider(new SwitchingProvider<RoomDirectoryViewModel.Factory>(singletonCImpl, activityRetainedCImpl, activityCImpl, 22));
      this.contentUploadStateTrackerBinderProvider = DoubleCheck.provider(new SwitchingProvider<ContentUploadStateTrackerBinder>(singletonCImpl, activityRetainedCImpl, activityCImpl, 23));
      this.contentDownloadStateTrackerBinderProvider = DoubleCheck.provider(new SwitchingProvider<ContentDownloadStateTrackerBinder>(singletonCImpl, activityRetainedCImpl, activityCImpl, 24));
      this.timelineMediaSizeProvider = DoubleCheck.provider(new SwitchingProvider<TimelineMediaSizeProvider>(singletonCImpl, activityRetainedCImpl, activityCImpl, 25));
      this.reactionsSummaryFactoryProvider = DoubleCheck.provider(new SwitchingProvider<ReactionsSummaryFactory>(singletonCImpl, activityRetainedCImpl, activityCImpl, 26));
      this.providesSharedViewPoolProvider = DoubleCheck.provider(new SwitchingProvider<RecyclerView.RecycledViewPool>(singletonCImpl, activityRetainedCImpl, activityCImpl, 27));
    }

    @Override
    public DefaultViewModelFactories.InternalFactoryFactory getHiltInternalFactoryFactory() {
      return DefaultViewModelFactories_InternalFactoryFactory_Factory.newInstance(ImmutableSet.<String>of(), new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl));
    }

    @Override
    public Set<String> getViewModelKeys() {
      return ImmutableSet.<String>of();
    }

    @Override
    public ViewModelComponentBuilder getViewModelComponentBuilder() {
      return new ViewModelCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public FragmentComponentBuilder fragmentComponentBuilder() {
      return new FragmentCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewComponentBuilder viewComponentBuilder() {
      return new ViewCBuilder(singletonCImpl, activityRetainedCImpl, activityCImpl);
    }

    @Override
    public ViewModelProvider.Factory viewModelFactory() {
      return vectorViewModelFactory();
    }

    @Override
    public void injectMainActivity(MainActivity arg0) {
      injectMainActivity2(arg0);
    }

    @Override
    public void injectAnalyticsOptInActivity(AnalyticsOptInActivity arg0) {
      injectAnalyticsOptInActivity2(arg0);
    }

    @Override
    public void injectAttachmentsPreviewActivity(AttachmentsPreviewActivity arg0) {
      injectAttachmentsPreviewActivity2(arg0);
    }

    @Override
    public void injectReAuthActivity(ReAuthActivity arg0) {
      injectReAuthActivity2(arg0);
    }

    @Override
    public void injectVectorCallActivity(VectorCallActivity arg0) {
      injectVectorCallActivity2(arg0);
    }

    @Override
    public void injectVectorJitsiActivity(VectorJitsiActivity arg0) {
      injectVectorJitsiActivity2(arg0);
    }

    @Override
    public void injectPstnDialActivity(PstnDialActivity arg0) {
      injectPstnDialActivity2(arg0);
    }

    @Override
    public void injectCallTransferActivity(CallTransferActivity arg0) {
      injectCallTransferActivity2(arg0);
    }

    @Override
    public void injectCreateDirectRoomActivity(CreateDirectRoomActivity arg0) {
      injectCreateDirectRoomActivity2(arg0);
    }

    @Override
    public void injectKeysBackupRestoreActivity(KeysBackupRestoreActivity arg0) {
      injectKeysBackupRestoreActivity2(arg0);
    }

    @Override
    public void injectKeysBackupManageActivity(KeysBackupManageActivity arg0) {
      injectKeysBackupManageActivity2(arg0);
    }

    @Override
    public void injectKeysBackupSetupActivity(KeysBackupSetupActivity arg0) {
      injectKeysBackupSetupActivity2(arg0);
    }

    @Override
    public void injectSharedSecureStorageActivity(SharedSecureStorageActivity arg0) {
      injectSharedSecureStorageActivity2(arg0);
    }

    @Override
    public void injectDebugMenuActivity(DebugMenuActivity arg0) {
      injectDebugMenuActivity2(arg0);
    }

    @Override
    public void injectDebugPermissionActivity(DebugPermissionActivity arg0) {
      injectDebugPermissionActivity2(arg0);
    }

    @Override
    public void injectDebugAnalyticsActivity(DebugAnalyticsActivity arg0) {
      injectDebugAnalyticsActivity2(arg0);
    }

    @Override
    public void injectDebugFeaturesSettingsActivity(DebugFeaturesSettingsActivity arg0) {
      injectDebugFeaturesSettingsActivity2(arg0);
    }

    @Override
    public void injectDebugJitsiActivity(DebugJitsiActivity arg0) {
      injectDebugJitsiActivity2(arg0);
    }

    @Override
    public void injectDebugMemoryLeaksActivity(DebugMemoryLeaksActivity arg0) {
      injectDebugMemoryLeaksActivity2(arg0);
    }

    @Override
    public void injectDebugPrivateSettingsActivity(DebugPrivateSettingsActivity arg0) {
      injectDebugPrivateSettingsActivity2(arg0);
    }

    @Override
    public void injectRoomDevToolActivity(RoomDevToolActivity arg0) {
      injectRoomDevToolActivity2(arg0);
    }

    @Override
    public void injectHomeActivity(HomeActivity arg0) {
      injectHomeActivity2(arg0);
    }

    @Override
    public void injectRoomDetailActivity(RoomDetailActivity arg0) {
      injectRoomDetailActivity2(arg0);
    }

    @Override
    public void injectSearchActivity(SearchActivity arg0) {
      injectSearchActivity2(arg0);
    }

    @Override
    public void injectFilteredRoomsActivity(FilteredRoomsActivity arg0) {
      injectFilteredRoomsActivity2(arg0);
    }

    @Override
    public void injectInvitesActivity(InvitesActivity arg0) {
      injectInvitesActivity2(arg0);
    }

    @Override
    public void injectReleaseNotesActivity(ReleaseNotesActivity arg0) {
      injectReleaseNotesActivity2(arg0);
    }

    @Override
    public void injectThreadsActivity(ThreadsActivity arg0) {
      injectThreadsActivity2(arg0);
    }

    @Override
    public void injectInviteUsersToRoomActivity(InviteUsersToRoomActivity arg0) {
      injectInviteUsersToRoomActivity2(arg0);
    }

    @Override
    public void injectLinkHandlerActivity(LinkHandlerActivity arg0) {
      injectLinkHandlerActivity2(arg0);
    }

    @Override
    public void injectLocationSharingActivity(LocationSharingActivity arg0) {
      injectLocationSharingActivity2(arg0);
    }

    @Override
    public void injectLiveLocationMapViewActivity(LiveLocationMapViewActivity arg0) {
      injectLiveLocationMapViewActivity2(arg0);
    }

    @Override
    public void injectLoginActivity(LoginActivity arg0) {
      injectLoginActivity2(arg0);
    }

    @Override
    public void injectSSORedirectRouterActivity(SSORedirectRouterActivity arg0) {
      injectSSORedirectRouterActivity2(arg0);
    }

    @Override
    public void injectQrCodeLoginActivity(QrCodeLoginActivity arg0) {
      injectQrCodeLoginActivity2(arg0);
    }

    @Override
    public void injectBigImageViewerActivity(BigImageViewerActivity arg0) {
      injectBigImageViewerActivity2(arg0);
    }

    @Override
    public void injectVectorAttachmentViewerActivity(VectorAttachmentViewerActivity arg0) {
      injectVectorAttachmentViewerActivity2(arg0);
    }

    @Override
    public void injectOnboardingActivity(OnboardingActivity arg0) {
      injectOnboardingActivity2(arg0);
    }

    @Override
    public void injectPinActivity(PinActivity arg0) {
      injectPinActivity2(arg0);
    }

    @Override
    public void injectCreatePollActivity(CreatePollActivity arg0) {
      injectCreatePollActivity2(arg0);
    }

    @Override
    public void injectQrCodeScannerActivity(QrCodeScannerActivity arg0) {
      injectQrCodeScannerActivity2(arg0);
    }

    @Override
    public void injectBugReportActivity(BugReportActivity arg0) {
      injectBugReportActivity2(arg0);
    }

    @Override
    public void injectEmojiReactionPickerActivity(EmojiReactionPickerActivity arg0) {
      injectEmojiReactionPickerActivity2(arg0);
    }

    @Override
    public void injectRoomDirectoryActivity(RoomDirectoryActivity arg0) {
      injectRoomDirectoryActivity2(arg0);
    }

    @Override
    public void injectCreateRoomActivity(CreateRoomActivity arg0) {
      injectCreateRoomActivity2(arg0);
    }

    @Override
    public void injectRoomPreviewActivity(RoomPreviewActivity arg0) {
      injectRoomPreviewActivity2(arg0);
    }

    @Override
    public void injectRoomMemberProfileActivity(RoomMemberProfileActivity arg0) {
      injectRoomMemberProfileActivity2(arg0);
    }

    @Override
    public void injectRoomProfileActivity(RoomProfileActivity arg0) {
      injectRoomProfileActivity2(arg0);
    }

    @Override
    public void injectRoomJoinRuleActivity(RoomJoinRuleActivity arg0) {
      injectRoomJoinRuleActivity2(arg0);
    }

    @Override
    public void injectVectorSettingsActivity(VectorSettingsActivity arg0) {
      injectVectorSettingsActivity2(arg0);
    }

    @Override
    public void injectSessionDetailsActivity(SessionDetailsActivity arg0) {
      injectSessionDetailsActivity2(arg0);
    }

    @Override
    public void injectOtherSessionsActivity(OtherSessionsActivity arg0) {
      injectOtherSessionsActivity2(arg0);
    }

    @Override
    public void injectSessionOverviewActivity(SessionOverviewActivity arg0) {
      injectSessionOverviewActivity2(arg0);
    }

    @Override
    public void injectRenameSessionActivity(RenameSessionActivity arg0) {
      injectRenameSessionActivity2(arg0);
    }

    @Override
    public void injectFontScaleSettingActivity(FontScaleSettingActivity arg0) {
      injectFontScaleSettingActivity2(arg0);
    }

    @Override
    public void injectIncomingShareActivity(IncomingShareActivity arg0) {
      injectIncomingShareActivity2(arg0);
    }

    @Override
    public void injectSignedOutActivity(SignedOutActivity arg0) {
      injectSignedOutActivity2(arg0);
    }

    @Override
    public void injectSoftLogoutActivity(SoftLogoutActivity arg0) {
      injectSoftLogoutActivity2(arg0);
    }

    @Override
    public void injectSpaceCreationActivity(SpaceCreationActivity arg0) {
      injectSpaceCreationActivity2(arg0);
    }

    @Override
    public void injectSpaceExploreActivity(SpaceExploreActivity arg0) {
      injectSpaceExploreActivity2(arg0);
    }

    @Override
    public void injectSpacePreviewActivity(SpacePreviewActivity arg0) {
      injectSpacePreviewActivity2(arg0);
    }

    @Override
    public void injectSpaceLeaveAdvancedActivity(SpaceLeaveAdvancedActivity arg0) {
      injectSpaceLeaveAdvancedActivity2(arg0);
    }

    @Override
    public void injectSpaceManageActivity(SpaceManageActivity arg0) {
      injectSpaceManageActivity2(arg0);
    }

    @Override
    public void injectSpacePeopleActivity(SpacePeopleActivity arg0) {
      injectSpacePeopleActivity2(arg0);
    }

    @Override
    public void injectReviewTermsActivity(ReviewTermsActivity arg0) {
      injectReviewTermsActivity2(arg0);
    }

    @Override
    public void injectUserCodeActivity(UserCodeActivity arg0) {
      injectUserCodeActivity2(arg0);
    }

    @Override
    public void injectVectorWebViewActivity(VectorWebViewActivity arg0) {
      injectVectorWebViewActivity2(arg0);
    }

    @Override
    public void injectWidgetActivity(WidgetActivity arg0) {
      injectWidgetActivity2(arg0);
    }

    @CanIgnoreReturnValue
    private ShortcutCreator injectShortcutCreator(ShortcutCreator instance) {
      ShortcutCreator_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      return instance;
    }

    @CanIgnoreReturnValue
    private MainActivity injectMainActivity2(MainActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      MainActivity_MembersInjector.injectNotificationDrawerManager(instance, singletonCImpl.notificationDrawerManagerProvider.get());
      MainActivity_MembersInjector.injectUiStateRepository(instance, singletonCImpl.sharedPreferencesUiStateRepository());
      MainActivity_MembersInjector.injectShortcutsHandler(instance, shortcutsHandler());
      MainActivity_MembersInjector.injectPinCodeHelper(instance, pinCodeHelper());
      MainActivity_MembersInjector.injectPopupAlertManager(instance, singletonCImpl.popupAlertManagerProvider.get());
      MainActivity_MembersInjector.injectVectorAnalytics(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      MainActivity_MembersInjector.injectLockScreenKeyRepository(instance, singletonCImpl.lockScreenKeyRepositoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private AnalyticsOptInActivity injectAnalyticsOptInActivity2(AnalyticsOptInActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      AnalyticsOptInActivity_MembersInjector.injectOrientationLocker(instance, screenOrientationLocker());
      return instance;
    }

    @CanIgnoreReturnValue
    private AttachmentsPreviewActivity injectAttachmentsPreviewActivity2(
        AttachmentsPreviewActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private ReAuthActivity injectReAuthActivity2(ReAuthActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      ReAuthActivity_MembersInjector.injectAuthenticationService(instance, singletonCImpl.authenticationService());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorCallActivity injectVectorCallActivity2(VectorCallActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      VectorCallActivity_MembersInjector.injectCallManager(instance, singletonCImpl.webRtcCallManagerProvider.get());
      VectorCallActivity_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      VectorCallActivity_MembersInjector.injectScreenCaptureServiceConnection(instance, screenCaptureServiceConnection());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorJitsiActivity injectVectorJitsiActivity2(VectorJitsiActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private PstnDialActivity injectPstnDialActivity2(PstnDialActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      PstnDialActivity_MembersInjector.injectCallManager(instance, singletonCImpl.webRtcCallManagerProvider.get());
      PstnDialActivity_MembersInjector.injectDirectRoomHelper(instance, directRoomHelper());
      PstnDialActivity_MembersInjector.injectSession(instance, singletonCImpl.session());
      return instance;
    }

    @CanIgnoreReturnValue
    private CallTransferActivity injectCallTransferActivity2(CallTransferActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreateDirectRoomActivity injectCreateDirectRoomActivity2(
        CreateDirectRoomActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private KeysBackupRestoreActivity injectKeysBackupRestoreActivity2(
        KeysBackupRestoreActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private KeysBackupManageActivity injectKeysBackupManageActivity2(
        KeysBackupManageActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private KeysBackupSetupActivity injectKeysBackupSetupActivity2(
        KeysBackupSetupActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      KeysBackupSetupActivity_MembersInjector.injectKeysExporter(instance, keysExporter());
      return instance;
    }

    @CanIgnoreReturnValue
    private SharedSecureStorageActivity injectSharedSecureStorageActivity2(
        SharedSecureStorageActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private DebugMenuActivity injectDebugMenuActivity2(DebugMenuActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      DebugMenuActivity_MembersInjector.injectClock(instance, new DefaultClock());
      return instance;
    }

    @CanIgnoreReturnValue
    private DebugPermissionActivity injectDebugPermissionActivity2(
        DebugPermissionActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private DebugAnalyticsActivity injectDebugAnalyticsActivity2(DebugAnalyticsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private DebugFeaturesSettingsActivity injectDebugFeaturesSettingsActivity2(
        DebugFeaturesSettingsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      DebugFeaturesSettingsActivity_MembersInjector.injectDebugFeatures(instance, singletonCImpl.debugVectorFeatures());
      DebugFeaturesSettingsActivity_MembersInjector.injectDebugFeaturesStateFactory(instance, debugFeaturesStateFactory());
      DebugFeaturesSettingsActivity_MembersInjector.injectController(instance, new FeaturesController());
      return instance;
    }

    @CanIgnoreReturnValue
    private DebugJitsiActivity injectDebugJitsiActivity2(DebugJitsiActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private DebugMemoryLeaksActivity injectDebugMemoryLeaksActivity2(
        DebugMemoryLeaksActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private DebugPrivateSettingsActivity injectDebugPrivateSettingsActivity2(
        DebugPrivateSettingsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomDevToolActivity injectRoomDevToolActivity2(RoomDevToolActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      RoomDevToolActivity_MembersInjector.injectColorProvider(instance, singletonCImpl.colorProvider());
      return instance;
    }

    @CanIgnoreReturnValue
    private HomeActivity injectHomeActivity2(HomeActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      HomeActivity_MembersInjector.injectVectorUncaughtExceptionHandler(instance, singletonCImpl.vectorUncaughtExceptionHandlerProvider.get());
      HomeActivity_MembersInjector.injectNotificationDrawerManager(instance, singletonCImpl.notificationDrawerManagerProvider.get());
      HomeActivity_MembersInjector.injectPopupAlertManager(instance, singletonCImpl.popupAlertManagerProvider.get());
      HomeActivity_MembersInjector.injectShortcutsHandler(instance, shortcutsHandler());
      HomeActivity_MembersInjector.injectPermalinkHandler(instance, permalinkHandler());
      HomeActivity_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      HomeActivity_MembersInjector.injectInitSyncStepFormatter(instance, initSyncStepFormatter());
      HomeActivity_MembersInjector.injectSpaceStateHandler(instance, singletonCImpl.spaceStateHandlerImplProvider.get());
      HomeActivity_MembersInjector.injectUnifiedPushHelper(instance, singletonCImpl.unifiedPushHelper());
      HomeActivity_MembersInjector.injectNightlyProxy(instance, singletonCImpl.firebaseNightlyProxy());
      HomeActivity_MembersInjector.injectDisclaimerDialog(instance, singletonCImpl.disclaimerDialog());
      HomeActivity_MembersInjector.injectNotificationPermissionManager(instance, notificationPermissionManager());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomDetailActivity injectRoomDetailActivity2(RoomDetailActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      RoomDetailActivity_MembersInjector.injectPlaybackTracker(instance, singletonCImpl.audioMessagePlaybackTrackerProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private SearchActivity injectSearchActivity2(SearchActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private FilteredRoomsActivity injectFilteredRoomsActivity2(FilteredRoomsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private InvitesActivity injectInvitesActivity2(InvitesActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private ReleaseNotesActivity injectReleaseNotesActivity2(ReleaseNotesActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      ReleaseNotesActivity_MembersInjector.injectOrientationLocker(instance, screenOrientationLocker());
      ReleaseNotesActivity_MembersInjector.injectReleaseNotesPreferencesStore(instance, releaseNotesPreferencesStore());
      return instance;
    }

    @CanIgnoreReturnValue
    private ThreadsActivity injectThreadsActivity2(ThreadsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      ThreadsActivity_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private InviteUsersToRoomActivity injectInviteUsersToRoomActivity2(
        InviteUsersToRoomActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private LinkHandlerActivity injectLinkHandlerActivity2(LinkHandlerActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      LinkHandlerActivity_MembersInjector.injectPermalinkHandler(instance, permalinkHandler());
      return instance;
    }

    @CanIgnoreReturnValue
    private LocationSharingActivity injectLocationSharingActivity2(
        LocationSharingActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private LiveLocationMapViewActivity injectLiveLocationMapViewActivity2(
        LiveLocationMapViewActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private LoginActivity injectLoginActivity2(LoginActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SSORedirectRouterActivity injectSSORedirectRouterActivity2(
        SSORedirectRouterActivity instance) {
      SSORedirectRouterActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private QrCodeLoginActivity injectQrCodeLoginActivity2(QrCodeLoginActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private BigImageViewerActivity injectBigImageViewerActivity2(BigImageViewerActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      BigImageViewerActivity_MembersInjector.injectSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorAttachmentViewerActivity injectVectorAttachmentViewerActivity2(
        VectorAttachmentViewerActivity instance) {
      VectorAttachmentViewerActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorAttachmentViewerActivity_MembersInjector.injectDataSourceFactory(instance, attachmentProviderFactory());
      VectorAttachmentViewerActivity_MembersInjector.injectImageContentRenderer(instance, imageContentRenderer());
      return instance;
    }

    @CanIgnoreReturnValue
    private OnboardingActivity injectOnboardingActivity2(OnboardingActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      OnboardingActivity_MembersInjector.injectOnboardingVariantFactory(instance, onboardingVariantFactory());
      return instance;
    }

    @CanIgnoreReturnValue
    private PinActivity injectPinActivity2(PinActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreatePollActivity injectCreatePollActivity2(CreatePollActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private QrCodeScannerActivity injectQrCodeScannerActivity2(QrCodeScannerActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private BugReportActivity injectBugReportActivity2(BugReportActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private EmojiReactionPickerActivity injectEmojiReactionPickerActivity2(
        EmojiReactionPickerActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      EmojiReactionPickerActivity_MembersInjector.injectEmojiCompatFontProvider(instance, singletonCImpl.emojiCompatFontProvider.get());
      EmojiReactionPickerActivity_MembersInjector.injectEmojiDataSource(instance, singletonCImpl.emojiDataSourceProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomDirectoryActivity injectRoomDirectoryActivity2(RoomDirectoryActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      RoomDirectoryActivity_MembersInjector.injectRoomDirectoryViewModelFactory(instance, factoryProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private CreateRoomActivity injectCreateRoomActivity2(CreateRoomActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomPreviewActivity injectRoomPreviewActivity2(RoomPreviewActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomMemberProfileActivity injectRoomMemberProfileActivity2(
        RoomMemberProfileActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomProfileActivity injectRoomProfileActivity2(RoomProfileActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      RoomProfileActivity_MembersInjector.injectRoomDetailPendingActionStore(instance, singletonCImpl.roomDetailPendingActionStoreProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private RoomJoinRuleActivity injectRoomJoinRuleActivity2(RoomJoinRuleActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSettingsActivity injectVectorSettingsActivity2(VectorSettingsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      VectorSettingsActivity_MembersInjector.injectSession(instance, singletonCImpl.session());
      return instance;
    }

    @CanIgnoreReturnValue
    private SessionDetailsActivity injectSessionDetailsActivity2(SessionDetailsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private OtherSessionsActivity injectOtherSessionsActivity2(OtherSessionsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SessionOverviewActivity injectSessionOverviewActivity2(
        SessionOverviewActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private RenameSessionActivity injectRenameSessionActivity2(RenameSessionActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private FontScaleSettingActivity injectFontScaleSettingActivity2(
        FontScaleSettingActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private IncomingShareActivity injectIncomingShareActivity2(IncomingShareActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SignedOutActivity injectSignedOutActivity2(SignedOutActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SoftLogoutActivity injectSoftLogoutActivity2(SoftLogoutActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      SoftLogoutActivity_MembersInjector.injectSession(instance, singletonCImpl.session());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceCreationActivity injectSpaceCreationActivity2(SpaceCreationActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceExploreActivity injectSpaceExploreActivity2(SpaceExploreActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpacePreviewActivity injectSpacePreviewActivity2(SpacePreviewActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceLeaveAdvancedActivity injectSpaceLeaveAdvancedActivity2(
        SpaceLeaveAdvancedActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpaceManageActivity injectSpaceManageActivity2(SpaceManageActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private SpacePeopleActivity injectSpacePeopleActivity2(SpacePeopleActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private ReviewTermsActivity injectReviewTermsActivity2(ReviewTermsActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private UserCodeActivity injectUserCodeActivity2(UserCodeActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorWebViewActivity injectVectorWebViewActivity2(VectorWebViewActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    @CanIgnoreReturnValue
    private WidgetActivity injectWidgetActivity2(WidgetActivity instance) {
      VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, singletonCImpl.defaultVectorAnalyticsProvider.get());
      VectorBaseActivity_MembersInjector.injectSessionListener(instance, singletonCImpl.sessionListenerProvider.get());
      VectorBaseActivity_MembersInjector.injectBugReporter(instance, singletonCImpl.bugReporterProvider.get());
      VectorBaseActivity_MembersInjector.injectPinLocker(instance, singletonCImpl.pinLockerProvider.get());
      VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShake());
      VectorBaseActivity_MembersInjector.injectBuildMeta(instance, singletonCImpl.providesBuildMetaProvider.get());
      VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, singletonCImpl.fontScalePreferencesImpl());
      VectorBaseActivity_MembersInjector.injectVectorLocale(instance, singletonCImpl.vectorLocaleProvider());
      VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, singletonCImpl.debugVectorFeatures());
      VectorBaseActivity_MembersInjector.injectNavigator(instance, singletonCImpl.defaultNavigatorProvider.get());
      VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, singletonCImpl.defaultErrorFormatter());
      VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, singletonCImpl.vectorDebugReceiver());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final ActivityCImpl activityCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          ActivityCImpl activityCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.activityCImpl = activityCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // im.vector.app.features.reactions.EmojiChooserViewModel 
          return (T) new EmojiChooserViewModel(singletonCImpl.emojiDataSourceProvider.get());

          case 1: // im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromKeyViewModel 
          return (T) new KeysBackupRestoreFromKeyViewModel(singletonCImpl.stringProvider());

          case 2: // im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreSharedViewModel 
          return (T) new KeysBackupRestoreSharedViewModel(singletonCImpl.stringProvider(), singletonCImpl.providesMatrixProvider.get());

          case 3: // im.vector.app.features.crypto.keysbackup.restore.KeysBackupRestoreFromPassphraseViewModel 
          return (T) new KeysBackupRestoreFromPassphraseViewModel(singletonCImpl.stringProvider());

          case 4: // im.vector.app.features.crypto.keysbackup.setup.KeysBackupSetupSharedViewModel 
          return (T) new KeysBackupSetupSharedViewModel(new DefaultClock());

          case 5: // im.vector.app.core.platform.ConfigurationViewModel 
          return (T) new ConfigurationViewModel(singletonCImpl.vectorConfiguration());

          case 6: // im.vector.app.features.call.SharedKnownCallsViewModel 
          return (T) new SharedKnownCallsViewModel(singletonCImpl.webRtcCallManagerProvider.get());

          case 7: // im.vector.app.features.userdirectory.UserListSharedActionViewModel 
          return (T) new UserListSharedActionViewModel();

          case 8: // im.vector.app.features.home.HomeSharedActionViewModel 
          return (T) new HomeSharedActionViewModel(singletonCImpl.session());

          case 9: // im.vector.app.features.home.room.detail.timeline.action.MessageSharedActionViewModel 
          return (T) new MessageSharedActionViewModel();

          case 10: // im.vector.app.features.home.room.list.actions.RoomListQuickActionsSharedActionViewModel 
          return (T) new RoomListQuickActionsSharedActionViewModel();

          case 11: // im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheetSharedActionViewModel 
          return (T) new RoomAliasBottomSheetSharedActionViewModel();

          case 12: // im.vector.app.features.roomprofile.settings.historyvisibility.RoomHistoryVisibilitySharedActionViewModel 
          return (T) new RoomHistoryVisibilitySharedActionViewModel();

          case 13: // im.vector.app.features.roomprofile.settings.joinrule.RoomJoinRuleSharedActionViewModel 
          return (T) new RoomJoinRuleSharedActionViewModel();

          case 14: // im.vector.app.features.roomdirectory.RoomDirectorySharedActionViewModel 
          return (T) new RoomDirectorySharedActionViewModel();

          case 15: // im.vector.app.features.home.room.detail.RoomDetailSharedActionViewModel 
          return (T) new RoomDetailSharedActionViewModel();

          case 16: // im.vector.app.features.roomprofile.RoomProfileSharedActionViewModel 
          return (T) new RoomProfileSharedActionViewModel();

          case 17: // im.vector.app.features.discovery.DiscoverySharedViewModel 
          return (T) new DiscoverySharedViewModel();

          case 18: // im.vector.app.features.spaces.SpacePreviewSharedActionViewModel 
          return (T) new SpacePreviewSharedActionViewModel();

          case 19: // im.vector.app.features.spaces.people.SpacePeopleSharedActionViewModel 
          return (T) new SpacePeopleSharedActionViewModel();

          case 20: // im.vector.app.features.home.room.list.actions.RoomListSharedActionViewModel 
          return (T) new RoomListSharedActionViewModel();

          case 21: // androidx.fragment.app.FragmentActivity 
          return (T) ActivityModule_ProvideFragmentActivityFactory.provideFragmentActivity(activityCImpl.activity);

          case 22: // im.vector.app.features.roomdirectory.RoomDirectoryViewModel.Factory 
          return (T) new RoomDirectoryViewModel.Factory() {
            @Override
            public RoomDirectoryViewModel create(PublicRoomsViewState initialState) {
              return new RoomDirectoryViewModel(initialState, singletonCImpl.vectorPreferences(), singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get(), activityCImpl.explicitTermFilter());
            }
          };

          case 23: // im.vector.app.features.home.room.detail.timeline.helper.ContentUploadStateTrackerBinder 
          return (T) new ContentUploadStateTrackerBinder(singletonCImpl.activeSessionHolderProvider.get(), activityCImpl.messageColorProvider(), singletonCImpl.defaultErrorFormatter());

          case 24: // im.vector.app.features.home.room.detail.timeline.helper.ContentDownloadStateTrackerBinder 
          return (T) new ContentDownloadStateTrackerBinder(singletonCImpl.activeSessionHolderProvider.get());

          case 25: // im.vector.app.features.home.room.detail.timeline.helper.TimelineMediaSizeProvider 
          return (T) new TimelineMediaSizeProvider(singletonCImpl.resources(), singletonCImpl.vectorPreferences());

          case 26: // im.vector.app.features.home.room.detail.timeline.helper.ReactionsSummaryFactory 
          return (T) new ReactionsSummaryFactory();

          case 27: // androidx.recyclerview.widget.RecyclerView.RecycledViewPool 
          return (T) ScreenModule_ProvidesSharedViewPoolFactory.providesSharedViewPool();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ViewModelCImpl extends VectorApplication_HiltComponents.ViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl;

    private final ViewModelCImpl viewModelCImpl = this;

    private ViewModelCImpl(SingletonCImpl singletonCImpl,
        ActivityRetainedCImpl activityRetainedCImpl, SavedStateHandle savedStateHandleParam,
        ViewModelLifecycle viewModelLifecycleParam) {
      this.singletonCImpl = singletonCImpl;
      this.activityRetainedCImpl = activityRetainedCImpl;


    }

    @Override
    public Map<String, Provider<ViewModel>> getHiltViewModelMap() {
      return ImmutableMap.<String, Provider<ViewModel>>of();
    }
  }

  private static final class ActivityRetainedCImpl extends VectorApplication_HiltComponents.ActivityRetainedC {
    private final SingletonCImpl singletonCImpl;

    private final ActivityRetainedCImpl activityRetainedCImpl = this;

    private Provider<ActivityRetainedLifecycle> provideActivityRetainedLifecycleProvider;

    private ActivityRetainedCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();

    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.provideActivityRetainedLifecycleProvider = DoubleCheck.provider(new SwitchingProvider<ActivityRetainedLifecycle>(singletonCImpl, activityRetainedCImpl, 0));
    }

    @Override
    public ActivityComponentBuilder activityComponentBuilder() {
      return new ActivityCBuilder(singletonCImpl, activityRetainedCImpl);
    }

    @Override
    public ActivityRetainedLifecycle getActivityRetainedLifecycle() {
      return provideActivityRetainedLifecycleProvider.get();
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final ActivityRetainedCImpl activityRetainedCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, ActivityRetainedCImpl activityRetainedCImpl,
          int id) {
        this.singletonCImpl = singletonCImpl;
        this.activityRetainedCImpl = activityRetainedCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // dagger.hilt.android.ActivityRetainedLifecycle 
          return (T) ActivityRetainedComponentManager_LifecycleModule_ProvideActivityRetainedLifecycleFactory.provideActivityRetainedLifecycle();

          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class ServiceCImpl extends VectorApplication_HiltComponents.ServiceC {
    private final SingletonCImpl singletonCImpl;

    private final ServiceCImpl serviceCImpl = this;

    private ServiceCImpl(SingletonCImpl singletonCImpl, Service serviceParam) {
      this.singletonCImpl = singletonCImpl;


    }

    private GetLiveLocationShareSummaryUseCase getLiveLocationShareSummaryUseCase() {
      return new GetLiveLocationShareSummaryUseCase(singletonCImpl.session());
    }

    private CheckIfEventIsRedactedUseCase checkIfEventIsRedactedUseCase() {
      return new CheckIfEventIsRedactedUseCase(singletonCImpl.session());
    }

    @Override
    public void injectCallAndroidService(CallAndroidService arg0) {
      injectCallAndroidService2(arg0);
    }

    @Override
    public void injectVectorSyncAndroidService(VectorSyncAndroidService arg0) {
      injectVectorSyncAndroidService2(arg0);
    }

    @Override
    public void injectScreenCaptureAndroidService(ScreenCaptureAndroidService arg0) {
      injectScreenCaptureAndroidService2(arg0);
    }

    @Override
    public void injectLocationSharingAndroidService(LocationSharingAndroidService arg0) {
      injectLocationSharingAndroidService2(arg0);
    }

    @Override
    public void injectStartAppAndroidService(StartAppAndroidService arg0) {
      injectStartAppAndroidService2(arg0);
    }

    @Override
    public void injectVectorFirebaseMessagingService(VectorFirebaseMessagingService arg0) {
      injectVectorFirebaseMessagingService2(arg0);
    }

    @CanIgnoreReturnValue
    private CallAndroidService injectCallAndroidService2(CallAndroidService instance) {
      CallAndroidService_MembersInjector.injectNotificationUtils(instance, singletonCImpl.notificationUtilsProvider.get());
      CallAndroidService_MembersInjector.injectCallManager(instance, singletonCImpl.webRtcCallManagerProvider.get());
      CallAndroidService_MembersInjector.injectAvatarRenderer(instance, singletonCImpl.avatarRendererProvider.get());
      CallAndroidService_MembersInjector.injectAlertManager(instance, singletonCImpl.popupAlertManagerProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorSyncAndroidService injectVectorSyncAndroidService2(
        VectorSyncAndroidService instance) {
      VectorSyncAndroidService_MembersInjector.injectNotificationUtils(instance, singletonCImpl.notificationUtilsProvider.get());
      VectorSyncAndroidService_MembersInjector.injectMatrix(instance, singletonCImpl.providesMatrixProvider.get());
      VectorSyncAndroidService_MembersInjector.injectClock(instance, new DefaultClock());
      return instance;
    }

    @CanIgnoreReturnValue
    private ScreenCaptureAndroidService injectScreenCaptureAndroidService2(
        ScreenCaptureAndroidService instance) {
      ScreenCaptureAndroidService_MembersInjector.injectNotificationUtils(instance, singletonCImpl.notificationUtilsProvider.get());
      ScreenCaptureAndroidService_MembersInjector.injectClock(instance, new DefaultClock());
      return instance;
    }

    @CanIgnoreReturnValue
    private LocationSharingAndroidService injectLocationSharingAndroidService2(
        LocationSharingAndroidService instance) {
      LocationSharingAndroidService_MembersInjector.injectLiveLocationNotificationBuilder(instance, singletonCImpl.liveLocationNotificationBuilderProvider.get());
      LocationSharingAndroidService_MembersInjector.injectLocationTracker(instance, singletonCImpl.locationTrackerProvider.get());
      LocationSharingAndroidService_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      LocationSharingAndroidService_MembersInjector.injectGetLiveLocationShareSummaryUseCase(instance, getLiveLocationShareSummaryUseCase());
      LocationSharingAndroidService_MembersInjector.injectCheckIfEventIsRedactedUseCase(instance, checkIfEventIsRedactedUseCase());
      return instance;
    }

    @CanIgnoreReturnValue
    private StartAppAndroidService injectStartAppAndroidService2(StartAppAndroidService instance) {
      StartAppAndroidService_MembersInjector.injectGlobalScope(instance, VectorStaticModule_ProvidesGlobalScopeFactory.providesGlobalScope());
      StartAppAndroidService_MembersInjector.injectNotificationUtils(instance, singletonCImpl.notificationUtilsProvider.get());
      StartAppAndroidService_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorFirebaseMessagingService injectVectorFirebaseMessagingService2(
        VectorFirebaseMessagingService instance) {
      VectorFirebaseMessagingService_MembersInjector.injectFcmHelper(instance, singletonCImpl.googleFcmHelper());
      VectorFirebaseMessagingService_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      VectorFirebaseMessagingService_MembersInjector.injectActiveSessionHolder(instance, singletonCImpl.activeSessionHolderProvider.get());
      VectorFirebaseMessagingService_MembersInjector.injectPushersManager(instance, singletonCImpl.pushersManager());
      VectorFirebaseMessagingService_MembersInjector.injectPushParser(instance, new PushParser());
      VectorFirebaseMessagingService_MembersInjector.injectVectorPushHandler(instance, singletonCImpl.vectorPushHandler());
      VectorFirebaseMessagingService_MembersInjector.injectUnifiedPushHelper(instance, singletonCImpl.unifiedPushHelper());
      return instance;
    }
  }

  private static final class MavericksViewModelCImpl extends VectorApplication_HiltComponents.MavericksViewModelC {
    private final SingletonCImpl singletonCImpl;

    private final MavericksViewModelCImpl mavericksViewModelCImpl = this;

    private Provider<BiometricHelper.BiometricHelperFactory> biometricHelperFactoryProvider;

    private Provider<LockScreenViewModel.Factory> factoryProvider;

    private Provider<DebugAnalyticsViewModel.Factory> factoryProvider2;

    private Provider<DebugPrivateSettingsViewModel.Factory> factoryProvider3;

    private Provider<DebugMemoryLeaksViewModel.Factory> factoryProvider4;

    private Provider<RoomListViewModel.Factory> factoryProvider5;

    private Provider<SpaceManageRoomsViewModel.Factory> factoryProvider6;

    private Provider<SpaceManageSharedViewModel.Factory> factoryProvider7;

    private Provider<SpaceListViewModel.Factory> factoryProvider8;

    private Provider<ReAuthViewModel.Factory> factoryProvider9;

    private Provider<VectorCallViewModel.Factory> factoryProvider10;

    private Provider<JitsiCallViewModel.Factory> factoryProvider11;

    private Provider<RoomDirectoryViewModel.Factory> factoryProvider12;

    private Provider<ViewReactionsViewModel.Factory> factoryProvider13;

    private Provider<RoomWidgetPermissionViewModel.Factory> factoryProvider14;

    private Provider<WidgetPostAPIHandler.Factory> factoryProvider15;

    private Provider<WidgetViewModel.Factory> factoryProvider16;

    private Provider<ServerBackupStatusViewModel.Factory> factoryProvider17;

    private Provider<SignoutCheckViewModel.Factory> factoryProvider18;

    private Provider<RoomDirectoryPickerViewModel.Factory> factoryProvider19;

    private Provider<RoomDevToolViewModel.Factory> factoryProvider20;

    private Provider<MigrateRoomViewModel.Factory> factoryProvider21;

    private Provider<IgnoredUsersViewModel.Factory> factoryProvider22;

    private Provider<CallTransferViewModel.Factory> factoryProvider23;

    private Provider<ContactsBookViewModel.Factory> factoryProvider24;

    private Provider<CreateDirectRoomViewModel.Factory> factoryProvider25;

    private Provider<QrCodeScannerViewModel.Factory> factoryProvider26;

    private Provider<RoomNotificationSettingsViewModel.Factory> factoryProvider27;

    private Provider<KeysBackupSettingsViewModel.Factory> factoryProvider28;

    private Provider<SharedSecureStorageViewModel.Factory> factoryProvider29;

    private Provider<UserListViewModel.Factory> factoryProvider30;

    private Provider<UserCodeSharedViewModel.Factory> factoryProvider31;

    private Provider<ReviewTermsViewModel.Factory> factoryProvider32;

    private Provider<ShareSpaceViewModel.Factory> factoryProvider33;

    private Provider<SpacePreviewViewModel.Factory> factoryProvider34;

    private Provider<SpacePeopleViewModel.Factory> factoryProvider35;

    private Provider<SpaceAddRoomsViewModel.Factory> factoryProvider36;

    private Provider<SpaceLeaveAdvancedViewModel.Factory> factoryProvider37;

    private Provider<SpaceInviteBottomSheetViewModel.Factory> factoryProvider38;

    private Provider<SpaceDirectoryViewModel.Factory> factoryProvider39;

    private Provider<CreateSpaceViewModel.Factory> factoryProvider40;

    private Provider<SpaceMenuViewModel.Factory> factoryProvider41;

    private Provider<SoftLogoutViewModel.Factory> factoryProvider42;

    private Provider<IncomingShareViewModel.Factory> factoryProvider43;

    private Provider<ThreePidsSettingsViewModel.Factory> factoryProvider44;

    private Provider<PushGatewaysViewModel.Factory> factoryProvider45;

    private Provider<HomeserverSettingsViewModel.Factory> factoryProvider46;

    private Provider<LocalePickerViewModel.Factory> factoryProvider47;

    private Provider<GossipingEventsPaperTrailViewModel.Factory> factoryProvider48;

    private Provider<AccountDataViewModel.Factory> factoryProvider49;

    private Provider<DevicesViewModel.Factory> factoryProvider50;

    private Provider<im.vector.app.features.settings.devices.v2.DevicesViewModel.Factory> factoryProvider51;

    private Provider<KeyRequestListViewModel.Factory> factoryProvider52;

    private Provider<KeyRequestViewModel.Factory> factoryProvider53;

    private Provider<CrossSigningSettingsViewModel.Factory> factoryProvider54;

    private Provider<DeactivateAccountViewModel.Factory> factoryProvider55;

    private Provider<RoomUploadsViewModel.Factory> factoryProvider56;

    private Provider<RoomJoinRuleChooseRestrictedViewModel.Factory> factoryProvider57;

    private Provider<RoomSettingsViewModel.Factory> factoryProvider58;

    private Provider<RoomPermissionsViewModel.Factory> factoryProvider59;

    private Provider<RoomMemberListViewModel.Factory> factoryProvider60;

    private Provider<RoomBannedMemberListViewModel.Factory> factoryProvider61;

    private Provider<RoomAliasViewModel.Factory> factoryProvider62;

    private Provider<RoomAliasBottomSheetViewModel.Factory> factoryProvider63;

    private Provider<RoomProfileViewModel.Factory> factoryProvider64;

    private Provider<RoomMemberProfileViewModel.Factory> factoryProvider65;

    private Provider<UserColorAccountDataViewModel.Factory> factoryProvider66;

    private Provider<RoomPreviewViewModel.Factory> factoryProvider67;

    private Provider<CreateRoomViewModel.Factory> factoryProvider68;

    private Provider<RequireActiveMembershipViewModel.Factory> factoryProvider69;

    private Provider<EmojiSearchResultViewModel.Factory> factoryProvider70;

    private Provider<BugReportViewModel.Factory> factoryProvider71;

    private Provider<MatrixToBottomSheetViewModel.Factory> factoryProvider72;

    private Provider<OnboardingViewModel.Factory> factoryProvider73;

    private Provider<LoginViewModel.Factory> factoryProvider74;

    private Provider<AnalyticsConsentViewModel.Factory> factoryProvider75;

    private Provider<AnalyticsAccountDataViewModel.Factory> factoryProvider76;

    private Provider<StartAppViewModel.Factory> factoryProvider77;

    private Provider<HomeServerCapabilitiesViewModel.Factory> factoryProvider78;

    private Provider<InviteUsersToRoomViewModel.Factory> factoryProvider79;

    private Provider<ViewEditHistoryViewModel.Factory> factoryProvider80;

    private Provider<PillsPostProcessor.Factory> factoryProvider81;

    private Provider<MessageActionsViewModel.Factory> factoryProvider82;

    private Provider<VerificationChooseMethodViewModel.Factory> factoryProvider83;

    private Provider<VerificationEmojiCodeViewModel.Factory> factoryProvider84;

    private Provider<SearchViewModel.Factory> factoryProvider85;

    private Provider<UnreadMessagesSharedViewModel.Factory> factoryProvider86;

    private Provider<UnknownDeviceDetectorSharedViewModel.Factory> factoryProvider87;

    private Provider<DiscoverySettingsViewModel.Factory> factoryProvider88;

    private Provider<LegalsViewModel.Factory> factoryProvider89;

    private Provider<TimelineViewModel.Factory> factoryProvider90;

    private Provider<MessageComposerViewModel.Factory> factoryProvider91;

    private Provider<SetIdentityServerViewModel.Factory> factoryProvider92;

    private Provider<BreadcrumbsViewModel.Factory> factoryProvider93;

    private Provider<HomeDetailViewModel.Factory> factoryProvider94;

    private Provider<DeviceVerificationInfoBottomSheetViewModel.Factory> factoryProvider95;

    private Provider<DeviceListBottomSheetViewModel.Factory> factoryProvider96;

    private Provider<HomeActivityViewModel.Factory> factoryProvider97;

    private Provider<BootstrapSharedViewModel.Factory> factoryProvider98;

    private Provider<VerificationBottomSheetViewModel.Factory> factoryProvider99;

    private Provider<CreatePollViewModel.Factory> factoryProvider100;

    private Provider<LocationSharingViewModel.Factory> factoryProvider101;

    private Provider<LocationPreviewViewModel.Factory> factoryProvider102;

    private Provider<VectorAttachmentViewerViewModel.Factory> factoryProvider103;

    private Provider<LiveLocationMapViewModel.Factory> factoryProvider104;

    private Provider<FontScaleSettingViewModel.Factory> factoryProvider105;

    private Provider<HomeRoomListViewModel.Factory> factoryProvider106;

    private Provider<InvitesViewModel.Factory> factoryProvider107;

    private Provider<ReleaseNotesViewModel.Factory> factoryProvider108;

    private Provider<SessionOverviewViewModel.Factory> factoryProvider109;

    private Provider<OtherSessionsViewModel.Factory> factoryProvider110;

    private Provider<SessionDetailsViewModel.Factory> factoryProvider111;

    private Provider<RenameSessionViewModel.Factory> factoryProvider112;

    private Provider<QrCodeLoginViewModel.Factory> factoryProvider113;

    private Provider<SessionLearnMoreViewModel.Factory> factoryProvider114;

    private Provider<VectorSettingsLabsViewModel.Factory> factoryProvider115;

    private Provider<AttachmentTypeSelectorViewModel.Factory> factoryProvider116;

    private Provider<VectorSettingsNotificationPreferenceViewModel.Factory> factoryProvider117;

    private Provider<SetLinkViewModel.Factory> factoryProvider118;

    private Provider<RoomPollsViewModel.Factory> factoryProvider119;

    private MavericksViewModelCImpl(SingletonCImpl singletonCImpl) {
      this.singletonCImpl = singletonCImpl;

      initialize();
      initialize2();

    }

    private PinCodeHelper pinCodeHelper() {
      return new PinCodeHelper(singletonCImpl.lockScreenKeyRepositoryProvider.get(), singletonCImpl.sharedPrefPinCodeStoreProvider.get());
    }

    private MissingSystemKeyMigrator missingSystemKeyMigrator() {
      return new MissingSystemKeyMigrator(LockScreenModule_ProvideSystemKeyAliasFactory.provideSystemKeyAlias(), singletonCImpl.factoryProvider.get(), singletonCImpl.vectorPreferences(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider());
    }

    private SystemKeyV1Migrator systemKeyV1Migrator() {
      return new SystemKeyV1Migrator(LockScreenModule_ProvideSystemKeyAliasFactory.provideSystemKeyAlias(), LockScreenModule_ProvideKeyStoreFactory.provideKeyStore(), singletonCImpl.factoryProvider.get(), singletonCImpl.vectorPreferences());
    }

    private LockScreenKeysMigrator lockScreenKeysMigrator() {
      return new LockScreenKeysMigrator(singletonCImpl.legacyPinCodeMigrator(), missingSystemKeyMigrator(), systemKeyV1Migrator(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider());
    }

    private ReleaseNotesPreferencesStore releaseNotesPreferencesStore() {
      return new ReleaseNotesPreferencesStore(singletonCImpl.context());
    }

    private CallProximityManager callProximityManager() {
      return new CallProximityManager(singletonCImpl.context(), singletonCImpl.stringProvider());
    }

    private DirectRoomHelper directRoomHelper() {
      return new DirectRoomHelper(singletonCImpl.rawService(), singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get(), singletonCImpl.vectorPreferences());
    }

    private DialPadLookup dialPadLookup() {
      return new DialPadLookup(singletonCImpl.session(), singletonCImpl.webRtcCallManagerProvider.get(), directRoomHelper());
    }

    private JitsiService jitsiService() {
      return new JitsiService(singletonCImpl.session(), singletonCImpl.rawService(), singletonCImpl.stringProvider(), singletonCImpl.themeProvider(), new JitsiJWTFactory(), new DefaultClock(), singletonCImpl.vectorLocaleProvider());
    }

    private AssetReader assetReader() {
      return new AssetReader(singletonCImpl.context());
    }

    private ExplicitTermFilter explicitTermFilter() {
      return new ExplicitTermFilter(assetReader());
    }

    private KeysExporter keysExporter() {
      return new KeysExporter(singletonCImpl.session(), singletonCImpl.context(), VectorStaticModule_ProvidesCoroutineDispatchersFactory.providesCoroutineDispatchers());
    }

    private StringArrayProvider stringArrayProvider() {
      return new StringArrayProvider(singletonCImpl.resources());
    }

    private RoomDirectoryListCreator roomDirectoryListCreator() {
      return new RoomDirectoryListCreator(stringArrayProvider(), singletonCImpl.session());
    }

    private UpgradeRoomViewModelTask upgradeRoomViewModelTask() {
      return new UpgradeRoomViewModelTask(singletonCImpl.session(), singletonCImpl.stringProvider());
    }

    private ContactsDataSource contactsDataSource() {
      return new ContactsDataSource(singletonCImpl.context());
    }

    private CreateSpaceViewModelTask createSpaceViewModelTask() {
      return new CreateSpaceViewModelTask(singletonCImpl.session(), singletonCImpl.vectorPreferences(), singletonCImpl.rawService());
    }

    private BreadcrumbsRoomComparator breadcrumbsRoomComparator() {
      return new BreadcrumbsRoomComparator(new ChronologicalRoomComparator());
    }

    private PendingAuthHandler pendingAuthHandler() {
      return new PendingAuthHandler(singletonCImpl.providesMatrixProvider.get(), singletonCImpl.activeSessionHolderProvider.get());
    }

    private CheckIfSessionIsInactiveUseCase checkIfSessionIsInactiveUseCase() {
      return new CheckIfSessionIsInactiveUseCase(new DefaultClock());
    }

    private GetCurrentSessionCrossSigningInfoUseCase getCurrentSessionCrossSigningInfoUseCase() {
      return new GetCurrentSessionCrossSigningInfoUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private GetEncryptionTrustLevelForDeviceUseCase getEncryptionTrustLevelForDeviceUseCase() {
      return new GetEncryptionTrustLevelForDeviceUseCase(new GetEncryptionTrustLevelForCurrentDeviceUseCase(), new GetEncryptionTrustLevelForOtherDeviceUseCase());
    }

    private im.vector.app.features.settings.devices.v2.verification.GetCurrentSessionCrossSigningInfoUseCase getCurrentSessionCrossSigningInfoUseCase2(
        ) {
      return new im.vector.app.features.settings.devices.v2.verification.GetCurrentSessionCrossSigningInfoUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private GetDeviceFullInfoListUseCase getDeviceFullInfoListUseCase() {
      return new GetDeviceFullInfoListUseCase(singletonCImpl.activeSessionHolderProvider.get(), checkIfSessionIsInactiveUseCase(), getEncryptionTrustLevelForDeviceUseCase(), getCurrentSessionCrossSigningInfoUseCase2(), new FilterDevicesUseCase(), new ParseDeviceUserAgentUseCase(), new GetMatrixClientInfoUseCase());
    }

    private RefreshDevicesOnCryptoDevicesChangeUseCase refreshDevicesOnCryptoDevicesChangeUseCase(
        ) {
      return new RefreshDevicesOnCryptoDevicesChangeUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private CheckIfCurrentSessionCanBeVerifiedUseCase checkIfCurrentSessionCanBeVerifiedUseCase() {
      return new CheckIfCurrentSessionCanBeVerifiedUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private InterceptSignoutFlowResponseUseCase interceptSignoutFlowResponseUseCase() {
      return new InterceptSignoutFlowResponseUseCase(singletonCImpl.reAuthHelperProvider.get(), singletonCImpl.activeSessionHolderProvider.get());
    }

    private SignoutSessionsUseCase signoutSessionsUseCase() {
      return new SignoutSessionsUseCase(singletonCImpl.activeSessionHolderProvider.get(), interceptSignoutFlowResponseUseCase());
    }

    private RefreshDevicesUseCase refreshDevicesUseCase() {
      return new RefreshDevicesUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private ToggleIpAddressVisibilityUseCase toggleIpAddressVisibilityUseCase() {
      return new ToggleIpAddressVisibilityUseCase(singletonCImpl.vectorPreferences());
    }

    private ShortcutCreator shortcutCreator() {
      return injectShortcutCreator(ShortcutCreator_Factory.newInstance(singletonCImpl.context(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.dimensionConverter(), singletonCImpl.providesBuildMetaProvider.get()));
    }

    private UriFilenameResolver uriFilenameResolver() {
      return new UriFilenameResolver(singletonCImpl.context());
    }

    private DirectLoginUseCase directLoginUseCase() {
      return new DirectLoginUseCase(singletonCImpl.authenticationService(), singletonCImpl.stringProvider(), new UriFactory());
    }

    private StartAuthenticationFlowUseCase startAuthenticationFlowUseCase() {
      return new StartAuthenticationFlowUseCase(singletonCImpl.authenticationService());
    }

    private RegistrationWizardActionDelegate registrationWizardActionDelegate() {
      return new RegistrationWizardActionDelegate(singletonCImpl.authenticationService());
    }

    private RegistrationActionHandler registrationActionHandler() {
      return new RegistrationActionHandler(registrationWizardActionDelegate(), singletonCImpl.authenticationService(), singletonCImpl.debugVectorOverrides(), singletonCImpl.debugVectorFeatures(), singletonCImpl.stringProvider());
    }

    private CheckIfCanRedactEventUseCase checkIfCanRedactEventUseCase() {
      return new CheckIfCanRedactEventUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private ShouldShowUnverifiedSessionsAlertUseCase shouldShowUnverifiedSessionsAlertUseCase() {
      return new ShouldShowUnverifiedSessionsAlertUseCase(singletonCImpl.debugVectorFeatures(), singletonCImpl.vectorPreferences(), new DefaultClock());
    }

    private SetUnverifiedSessionsAlertShownUseCase setUnverifiedSessionsAlertShownUseCase() {
      return new SetUnverifiedSessionsAlertShownUseCase(singletonCImpl.vectorPreferences(), new DefaultClock());
    }

    private IsNewLoginAlertShownUseCase isNewLoginAlertShownUseCase() {
      return new IsNewLoginAlertShownUseCase(singletonCImpl.vectorPreferences());
    }

    private SetNewLoginAlertShownUseCase setNewLoginAlertShownUseCase() {
      return new SetNewLoginAlertShownUseCase(singletonCImpl.vectorPreferences());
    }

    private DeleteUnusedClientInformationUseCase deleteUnusedClientInformationUseCase() {
      return new DeleteUnusedClientInformationUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private StickerPickerActionHandler stickerPickerActionHandler() {
      return new StickerPickerActionHandler(singletonCImpl.session());
    }

    private TypingHelper typingHelper() {
      return new TypingHelper(singletonCImpl.stringProvider());
    }

    private ChatEffectManager chatEffectManager() {
      return new ChatEffectManager(new DefaultClock());
    }

    private StopLiveLocationShareUseCase stopLiveLocationShareUseCase() {
      return new StopLiveLocationShareUseCase(singletonCImpl.activeSessionHolderProvider.get());
    }

    private UserPreferencesProvider userPreferencesProvider() {
      return new UserPreferencesProvider(singletonCImpl.vectorPreferences());
    }

    private TimelineSettingsFactory timelineSettingsFactory() {
      return new TimelineSettingsFactory(userPreferencesProvider());
    }

    private TimelineFactory timelineFactory() {
      return new TimelineFactory(singletonCImpl.session(), timelineSettingsFactory());
    }

    private GetRoomLiveVoiceBroadcastsUseCase getRoomLiveVoiceBroadcastsUseCase() {
      return new GetRoomLiveVoiceBroadcastsUseCase(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.getVoiceBroadcastStateEventUseCase());
    }

    private StopVoiceBroadcastUseCase stopVoiceBroadcastUseCase() {
      return new StopVoiceBroadcastUseCase(singletonCImpl.session(), singletonCImpl.providesVoiceBroadcastRecorderProvider.get());
    }

    private PauseVoiceBroadcastUseCase pauseVoiceBroadcastUseCase() {
      return new PauseVoiceBroadcastUseCase(singletonCImpl.session(), singletonCImpl.providesVoiceBroadcastRecorderProvider.get());
    }

    private StartVoiceBroadcastUseCase startVoiceBroadcastUseCase() {
      return new StartVoiceBroadcastUseCase(singletonCImpl.session(), singletonCImpl.providesVoiceBroadcastRecorderProvider.get(), singletonCImpl.context(), singletonCImpl.providesBuildMetaProvider.get(), getRoomLiveVoiceBroadcastsUseCase(), stopVoiceBroadcastUseCase(), pauseVoiceBroadcastUseCase());
    }

    private ResumeVoiceBroadcastUseCase resumeVoiceBroadcastUseCase() {
      return new ResumeVoiceBroadcastUseCase(singletonCImpl.session());
    }

    private VoiceBroadcastHelper voiceBroadcastHelper() {
      return new VoiceBroadcastHelper(startVoiceBroadcastUseCase(), pauseVoiceBroadcastUseCase(), resumeVoiceBroadcastUseCase(), stopVoiceBroadcastUseCase(), singletonCImpl.voiceBroadcastPlayerImplProvider.get());
    }

    private CommandParser commandParser() {
      return new CommandParser(singletonCImpl.vectorPreferences());
    }

    private VoiceRecorderProvider voiceRecorderProvider() {
      return new VoiceRecorderProvider(singletonCImpl.context(), singletonCImpl.debugVectorFeatures(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider());
    }

    private AudioMessageHelper audioMessageHelper() {
      return new AudioMessageHelper(singletonCImpl.context(), singletonCImpl.audioMessagePlaybackTrackerProvider.get(), singletonCImpl.providesBuildMetaProvider.get(), voiceRecorderProvider());
    }

    private StopOngoingVoiceBroadcastUseCase stopOngoingVoiceBroadcastUseCase() {
      return new StopOngoingVoiceBroadcastUseCase(singletonCImpl.activeSessionHolderProvider.get(), getRoomLiveVoiceBroadcastsUseCase(), voiceBroadcastHelper());
    }

    private RegisterUnifiedPushUseCase registerUnifiedPushUseCase() {
      return new RegisterUnifiedPushUseCase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.debugVectorFeatures());
    }

    private EnsureFcmTokenIsRetrievedUseCase ensureFcmTokenIsRetrievedUseCase() {
      return new EnsureFcmTokenIsRetrievedUseCase(singletonCImpl.unifiedPushHelper(), singletonCImpl.googleFcmHelper(), singletonCImpl.activeSessionHolderProvider.get());
    }

    private EnsureSessionSyncingUseCase ensureSessionSyncingUseCase() {
      return new EnsureSessionSyncingUseCase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.activeSessionHolderProvider.get());
    }

    private BootstrapCrossSigningTask bootstrapCrossSigningTask() {
      return new BootstrapCrossSigningTask(singletonCImpl.session(), singletonCImpl.stringProvider());
    }

    private BackupToQuadSMigrationTask backupToQuadSMigrationTask() {
      return new BackupToQuadSMigrationTask(singletonCImpl.session(), singletonCImpl.stringProvider());
    }

    private CompareLocationsUseCase compareLocationsUseCase() {
      return new CompareLocationsUseCase(singletonCImpl.session());
    }

    private DownloadMediaUseCase downloadMediaUseCase() {
      return new DownloadMediaUseCase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.session(), singletonCImpl.notificationUtilsProvider.get(), new DefaultClock());
    }

    private UserLiveLocationViewStateMapper userLiveLocationViewStateMapper() {
      return new UserLiveLocationViewStateMapper(singletonCImpl.locationPinProvider.get(), singletonCImpl.activeSessionHolderProvider.get());
    }

    private GetListOfUserLiveLocationUseCase getListOfUserLiveLocationUseCase() {
      return new GetListOfUserLiveLocationUseCase(singletonCImpl.session(), userLiveLocationViewStateMapper());
    }

    private HomeLayoutPreferencesStore homeLayoutPreferencesStore() {
      return new HomeLayoutPreferencesStore(singletonCImpl.context());
    }

    private GetDeviceFullInfoUseCase getDeviceFullInfoUseCase() {
      return new GetDeviceFullInfoUseCase(singletonCImpl.activeSessionHolderProvider.get(), getCurrentSessionCrossSigningInfoUseCase2(), getEncryptionTrustLevelForDeviceUseCase(), checkIfSessionIsInactiveUseCase(), new ParseDeviceUserAgentUseCase(), new GetMatrixClientInfoUseCase());
    }

    private CheckIfCanToggleNotificationsViaAccountDataUseCase checkIfCanToggleNotificationsViaAccountDataUseCase(
        ) {
      return new CheckIfCanToggleNotificationsViaAccountDataUseCase(new GetNotificationSettingsAccountDataUseCase());
    }

    private ToggleNotificationsUseCase toggleNotificationsUseCase() {
      return new ToggleNotificationsUseCase(singletonCImpl.activeSessionHolderProvider.get(), new CheckIfCanToggleNotificationsViaPusherUseCase(), checkIfCanToggleNotificationsViaAccountDataUseCase(), new SetNotificationSettingsAccountDataUseCase());
    }

    private CopyToClipboardUseCase copyToClipboardUseCase() {
      return new CopyToClipboardUseCase(ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule));
    }

    private RenameSessionUseCase renameSessionUseCase() {
      return new RenameSessionUseCase(singletonCImpl.activeSessionHolderProvider.get(), refreshDevicesUseCase());
    }

    private DeleteMatrixClientInfoUseCase deleteMatrixClientInfoUseCase() {
      return new DeleteMatrixClientInfoUseCase(singletonCImpl.activeSessionHolderProvider.get(), new SetMatrixClientInfoUseCase());
    }

    private ToggleNotificationsForCurrentSessionUseCase toggleNotificationsForCurrentSessionUseCase(
        ) {
      return new ToggleNotificationsForCurrentSessionUseCase(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.unifiedPushHelper(), new CheckIfCanToggleNotificationsViaPusherUseCase(), new SetNotificationSettingsAccountDataUseCase(), singletonCImpl.deleteNotificationSettingsAccountDataUseCase());
    }

    private EnableNotificationsForCurrentSessionUseCase enableNotificationsForCurrentSessionUseCase(
        ) {
      return new EnableNotificationsForCurrentSessionUseCase(singletonCImpl.pushersManager(), toggleNotificationsForCurrentSessionUseCase(), registerUnifiedPushUseCase(), ensureFcmTokenIsRetrievedUseCase());
    }

    private DisableNotificationsForCurrentSessionUseCase disableNotificationsForCurrentSessionUseCase(
        ) {
      return new DisableNotificationsForCurrentSessionUseCase(singletonCImpl.pushersManager(), toggleNotificationsForCurrentSessionUseCase(), singletonCImpl.unregisterUnifiedPushUseCase());
    }

    private RoomPollDataSource roomPollDataSource() {
      return new RoomPollDataSource(singletonCImpl.activeSessionHolderProvider.get());
    }

    private RoomPollRepository roomPollRepository() {
      return new RoomPollRepository(roomPollDataSource());
    }

    private GetPollsUseCase getPollsUseCase() {
      return new GetPollsUseCase(roomPollRepository());
    }

    private LoadMorePollsUseCase loadMorePollsUseCase() {
      return new LoadMorePollsUseCase(roomPollRepository());
    }

    private GetLoadedPollsStatusUseCase getLoadedPollsStatusUseCase() {
      return new GetLoadedPollsStatusUseCase(roomPollRepository());
    }

    private SyncPollsUseCase syncPollsUseCase() {
      return new SyncPollsUseCase(roomPollRepository(), getLoadedPollsStatusUseCase(), loadMorePollsUseCase());
    }

    private DisposePollHistoryUseCase disposePollHistoryUseCase() {
      return new DisposePollHistoryUseCase(roomPollRepository());
    }

    private PollResponseDataFactory pollResponseDataFactory() {
      return new PollResponseDataFactory(singletonCImpl.activeSessionHolderProvider.get());
    }

    private PollSummaryMapper pollSummaryMapper() {
      return new PollSummaryMapper(pollResponseDataFactory(), new PollOptionViewStateFactory());
    }

    @SuppressWarnings("unchecked")
    private void initialize() {
      this.biometricHelperFactoryProvider = SingleCheck.provider(new SwitchingProvider<BiometricHelper.BiometricHelperFactory>(singletonCImpl, mavericksViewModelCImpl, 1));
      this.factoryProvider = SingleCheck.provider(new SwitchingProvider<LockScreenViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 0));
      this.factoryProvider2 = SingleCheck.provider(new SwitchingProvider<DebugAnalyticsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 2));
      this.factoryProvider3 = SingleCheck.provider(new SwitchingProvider<DebugPrivateSettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 3));
      this.factoryProvider4 = SingleCheck.provider(new SwitchingProvider<DebugMemoryLeaksViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 4));
      this.factoryProvider5 = SingleCheck.provider(new SwitchingProvider<RoomListViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 5));
      this.factoryProvider6 = SingleCheck.provider(new SwitchingProvider<SpaceManageRoomsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 6));
      this.factoryProvider7 = SingleCheck.provider(new SwitchingProvider<SpaceManageSharedViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 7));
      this.factoryProvider8 = SingleCheck.provider(new SwitchingProvider<SpaceListViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 8));
      this.factoryProvider9 = SingleCheck.provider(new SwitchingProvider<ReAuthViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 9));
      this.factoryProvider10 = SingleCheck.provider(new SwitchingProvider<VectorCallViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 10));
      this.factoryProvider11 = SingleCheck.provider(new SwitchingProvider<JitsiCallViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 11));
      this.factoryProvider12 = SingleCheck.provider(new SwitchingProvider<RoomDirectoryViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 12));
      this.factoryProvider13 = SingleCheck.provider(new SwitchingProvider<ViewReactionsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 13));
      this.factoryProvider14 = SingleCheck.provider(new SwitchingProvider<RoomWidgetPermissionViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 14));
      this.factoryProvider15 = SingleCheck.provider(new SwitchingProvider<WidgetPostAPIHandler.Factory>(singletonCImpl, mavericksViewModelCImpl, 16));
      this.factoryProvider16 = SingleCheck.provider(new SwitchingProvider<WidgetViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 15));
      this.factoryProvider17 = SingleCheck.provider(new SwitchingProvider<ServerBackupStatusViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 17));
      this.factoryProvider18 = SingleCheck.provider(new SwitchingProvider<SignoutCheckViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 18));
      this.factoryProvider19 = SingleCheck.provider(new SwitchingProvider<RoomDirectoryPickerViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 19));
      this.factoryProvider20 = SingleCheck.provider(new SwitchingProvider<RoomDevToolViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 20));
      this.factoryProvider21 = SingleCheck.provider(new SwitchingProvider<MigrateRoomViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 21));
      this.factoryProvider22 = SingleCheck.provider(new SwitchingProvider<IgnoredUsersViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 22));
      this.factoryProvider23 = SingleCheck.provider(new SwitchingProvider<CallTransferViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 23));
      this.factoryProvider24 = SingleCheck.provider(new SwitchingProvider<ContactsBookViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 24));
      this.factoryProvider25 = SingleCheck.provider(new SwitchingProvider<CreateDirectRoomViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 25));
      this.factoryProvider26 = SingleCheck.provider(new SwitchingProvider<QrCodeScannerViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 26));
      this.factoryProvider27 = SingleCheck.provider(new SwitchingProvider<RoomNotificationSettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 27));
      this.factoryProvider28 = SingleCheck.provider(new SwitchingProvider<KeysBackupSettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 28));
      this.factoryProvider29 = SingleCheck.provider(new SwitchingProvider<SharedSecureStorageViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 29));
      this.factoryProvider30 = SingleCheck.provider(new SwitchingProvider<UserListViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 30));
      this.factoryProvider31 = SingleCheck.provider(new SwitchingProvider<UserCodeSharedViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 31));
      this.factoryProvider32 = SingleCheck.provider(new SwitchingProvider<ReviewTermsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 32));
      this.factoryProvider33 = SingleCheck.provider(new SwitchingProvider<ShareSpaceViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 33));
      this.factoryProvider34 = SingleCheck.provider(new SwitchingProvider<SpacePreviewViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 34));
      this.factoryProvider35 = SingleCheck.provider(new SwitchingProvider<SpacePeopleViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 35));
      this.factoryProvider36 = SingleCheck.provider(new SwitchingProvider<SpaceAddRoomsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 36));
      this.factoryProvider37 = SingleCheck.provider(new SwitchingProvider<SpaceLeaveAdvancedViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 37));
      this.factoryProvider38 = SingleCheck.provider(new SwitchingProvider<SpaceInviteBottomSheetViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 38));
      this.factoryProvider39 = SingleCheck.provider(new SwitchingProvider<SpaceDirectoryViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 39));
      this.factoryProvider40 = SingleCheck.provider(new SwitchingProvider<CreateSpaceViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 40));
      this.factoryProvider41 = SingleCheck.provider(new SwitchingProvider<SpaceMenuViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 41));
      this.factoryProvider42 = SingleCheck.provider(new SwitchingProvider<SoftLogoutViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 42));
      this.factoryProvider43 = SingleCheck.provider(new SwitchingProvider<IncomingShareViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 43));
      this.factoryProvider44 = SingleCheck.provider(new SwitchingProvider<ThreePidsSettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 44));
      this.factoryProvider45 = SingleCheck.provider(new SwitchingProvider<PushGatewaysViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 45));
      this.factoryProvider46 = SingleCheck.provider(new SwitchingProvider<HomeserverSettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 46));
      this.factoryProvider47 = SingleCheck.provider(new SwitchingProvider<LocalePickerViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 47));
      this.factoryProvider48 = SingleCheck.provider(new SwitchingProvider<GossipingEventsPaperTrailViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 48));
      this.factoryProvider49 = SingleCheck.provider(new SwitchingProvider<AccountDataViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 49));
      this.factoryProvider50 = SingleCheck.provider(new SwitchingProvider<DevicesViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 50));
      this.factoryProvider51 = SingleCheck.provider(new SwitchingProvider<im.vector.app.features.settings.devices.v2.DevicesViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 51));
      this.factoryProvider52 = SingleCheck.provider(new SwitchingProvider<KeyRequestListViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 52));
      this.factoryProvider53 = SingleCheck.provider(new SwitchingProvider<KeyRequestViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 53));
      this.factoryProvider54 = SingleCheck.provider(new SwitchingProvider<CrossSigningSettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 54));
      this.factoryProvider55 = SingleCheck.provider(new SwitchingProvider<DeactivateAccountViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 55));
      this.factoryProvider56 = SingleCheck.provider(new SwitchingProvider<RoomUploadsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 56));
      this.factoryProvider57 = SingleCheck.provider(new SwitchingProvider<RoomJoinRuleChooseRestrictedViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 57));
      this.factoryProvider58 = SingleCheck.provider(new SwitchingProvider<RoomSettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 58));
      this.factoryProvider59 = SingleCheck.provider(new SwitchingProvider<RoomPermissionsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 59));
      this.factoryProvider60 = SingleCheck.provider(new SwitchingProvider<RoomMemberListViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 60));
      this.factoryProvider61 = SingleCheck.provider(new SwitchingProvider<RoomBannedMemberListViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 61));
      this.factoryProvider62 = SingleCheck.provider(new SwitchingProvider<RoomAliasViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 62));
      this.factoryProvider63 = SingleCheck.provider(new SwitchingProvider<RoomAliasBottomSheetViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 63));
      this.factoryProvider64 = SingleCheck.provider(new SwitchingProvider<RoomProfileViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 64));
      this.factoryProvider65 = SingleCheck.provider(new SwitchingProvider<RoomMemberProfileViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 65));
      this.factoryProvider66 = SingleCheck.provider(new SwitchingProvider<UserColorAccountDataViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 66));
      this.factoryProvider67 = SingleCheck.provider(new SwitchingProvider<RoomPreviewViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 67));
      this.factoryProvider68 = SingleCheck.provider(new SwitchingProvider<CreateRoomViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 68));
      this.factoryProvider69 = SingleCheck.provider(new SwitchingProvider<RequireActiveMembershipViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 69));
      this.factoryProvider70 = SingleCheck.provider(new SwitchingProvider<EmojiSearchResultViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 70));
      this.factoryProvider71 = SingleCheck.provider(new SwitchingProvider<BugReportViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 71));
      this.factoryProvider72 = SingleCheck.provider(new SwitchingProvider<MatrixToBottomSheetViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 72));
      this.factoryProvider73 = SingleCheck.provider(new SwitchingProvider<OnboardingViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 73));
      this.factoryProvider74 = SingleCheck.provider(new SwitchingProvider<LoginViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 74));
      this.factoryProvider75 = SingleCheck.provider(new SwitchingProvider<AnalyticsConsentViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 75));
      this.factoryProvider76 = SingleCheck.provider(new SwitchingProvider<AnalyticsAccountDataViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 76));
      this.factoryProvider77 = SingleCheck.provider(new SwitchingProvider<StartAppViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 77));
      this.factoryProvider78 = SingleCheck.provider(new SwitchingProvider<HomeServerCapabilitiesViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 78));
      this.factoryProvider79 = SingleCheck.provider(new SwitchingProvider<InviteUsersToRoomViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 79));
      this.factoryProvider80 = SingleCheck.provider(new SwitchingProvider<ViewEditHistoryViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 80));
      this.factoryProvider81 = SingleCheck.provider(new SwitchingProvider<PillsPostProcessor.Factory>(singletonCImpl, mavericksViewModelCImpl, 82));
      this.factoryProvider82 = SingleCheck.provider(new SwitchingProvider<MessageActionsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 81));
      this.factoryProvider83 = SingleCheck.provider(new SwitchingProvider<VerificationChooseMethodViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 83));
      this.factoryProvider84 = SingleCheck.provider(new SwitchingProvider<VerificationEmojiCodeViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 84));
      this.factoryProvider85 = SingleCheck.provider(new SwitchingProvider<SearchViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 85));
      this.factoryProvider86 = SingleCheck.provider(new SwitchingProvider<UnreadMessagesSharedViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 86));
      this.factoryProvider87 = SingleCheck.provider(new SwitchingProvider<UnknownDeviceDetectorSharedViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 87));
      this.factoryProvider88 = SingleCheck.provider(new SwitchingProvider<DiscoverySettingsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 88));
      this.factoryProvider89 = SingleCheck.provider(new SwitchingProvider<LegalsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 89));
      this.factoryProvider90 = SingleCheck.provider(new SwitchingProvider<TimelineViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 90));
      this.factoryProvider91 = SingleCheck.provider(new SwitchingProvider<MessageComposerViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 91));
      this.factoryProvider92 = SingleCheck.provider(new SwitchingProvider<SetIdentityServerViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 92));
      this.factoryProvider93 = SingleCheck.provider(new SwitchingProvider<BreadcrumbsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 93));
      this.factoryProvider94 = SingleCheck.provider(new SwitchingProvider<HomeDetailViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 94));
      this.factoryProvider95 = SingleCheck.provider(new SwitchingProvider<DeviceVerificationInfoBottomSheetViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 95));
      this.factoryProvider96 = SingleCheck.provider(new SwitchingProvider<DeviceListBottomSheetViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 96));
      this.factoryProvider97 = SingleCheck.provider(new SwitchingProvider<HomeActivityViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 97));
      this.factoryProvider98 = SingleCheck.provider(new SwitchingProvider<BootstrapSharedViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 98));
      this.factoryProvider99 = SingleCheck.provider(new SwitchingProvider<VerificationBottomSheetViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 99));
    }

    @SuppressWarnings("unchecked")
    private void initialize2() {
      this.factoryProvider100 = SingleCheck.provider(new SwitchingProvider<CreatePollViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 100));
      this.factoryProvider101 = SingleCheck.provider(new SwitchingProvider<LocationSharingViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 101));
      this.factoryProvider102 = SingleCheck.provider(new SwitchingProvider<LocationPreviewViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 102));
      this.factoryProvider103 = SingleCheck.provider(new SwitchingProvider<VectorAttachmentViewerViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 103));
      this.factoryProvider104 = SingleCheck.provider(new SwitchingProvider<LiveLocationMapViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 104));
      this.factoryProvider105 = SingleCheck.provider(new SwitchingProvider<FontScaleSettingViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 105));
      this.factoryProvider106 = SingleCheck.provider(new SwitchingProvider<HomeRoomListViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 106));
      this.factoryProvider107 = SingleCheck.provider(new SwitchingProvider<InvitesViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 107));
      this.factoryProvider108 = SingleCheck.provider(new SwitchingProvider<ReleaseNotesViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 108));
      this.factoryProvider109 = SingleCheck.provider(new SwitchingProvider<SessionOverviewViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 109));
      this.factoryProvider110 = SingleCheck.provider(new SwitchingProvider<OtherSessionsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 110));
      this.factoryProvider111 = SingleCheck.provider(new SwitchingProvider<SessionDetailsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 111));
      this.factoryProvider112 = SingleCheck.provider(new SwitchingProvider<RenameSessionViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 112));
      this.factoryProvider113 = SingleCheck.provider(new SwitchingProvider<QrCodeLoginViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 113));
      this.factoryProvider114 = SingleCheck.provider(new SwitchingProvider<SessionLearnMoreViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 114));
      this.factoryProvider115 = SingleCheck.provider(new SwitchingProvider<VectorSettingsLabsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 115));
      this.factoryProvider116 = SingleCheck.provider(new SwitchingProvider<AttachmentTypeSelectorViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 116));
      this.factoryProvider117 = SingleCheck.provider(new SwitchingProvider<VectorSettingsNotificationPreferenceViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 117));
      this.factoryProvider118 = SingleCheck.provider(new SwitchingProvider<SetLinkViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 118));
      this.factoryProvider119 = SingleCheck.provider(new SwitchingProvider<RoomPollsViewModel.Factory>(singletonCImpl, mavericksViewModelCImpl, 119));
    }

    @Override
    public Map<Class<? extends MavericksViewModel<?>>, MavericksAssistedViewModelFactory<?, ?>> getViewModelFactories(
        ) {
      return ImmutableMap.<Class<? extends MavericksViewModel<?>>, MavericksAssistedViewModelFactory<?, ?>>builderWithExpectedSize(117).put(LockScreenViewModel.class, factoryProvider.get()).put(DebugAnalyticsViewModel.class, factoryProvider2.get()).put(DebugPrivateSettingsViewModel.class, factoryProvider3.get()).put(DebugMemoryLeaksViewModel.class, factoryProvider4.get()).put(RoomListViewModel.class, factoryProvider5.get()).put(SpaceManageRoomsViewModel.class, factoryProvider6.get()).put(SpaceManageSharedViewModel.class, factoryProvider7.get()).put(SpaceListViewModel.class, factoryProvider8.get()).put(ReAuthViewModel.class, factoryProvider9.get()).put(VectorCallViewModel.class, factoryProvider10.get()).put(JitsiCallViewModel.class, factoryProvider11.get()).put(RoomDirectoryViewModel.class, factoryProvider12.get()).put(ViewReactionsViewModel.class, factoryProvider13.get()).put(RoomWidgetPermissionViewModel.class, factoryProvider14.get()).put(WidgetViewModel.class, factoryProvider16.get()).put(ServerBackupStatusViewModel.class, factoryProvider17.get()).put(SignoutCheckViewModel.class, factoryProvider18.get()).put(RoomDirectoryPickerViewModel.class, factoryProvider19.get()).put(RoomDevToolViewModel.class, factoryProvider20.get()).put(MigrateRoomViewModel.class, factoryProvider21.get()).put(IgnoredUsersViewModel.class, factoryProvider22.get()).put(CallTransferViewModel.class, factoryProvider23.get()).put(ContactsBookViewModel.class, factoryProvider24.get()).put(CreateDirectRoomViewModel.class, factoryProvider25.get()).put(QrCodeScannerViewModel.class, factoryProvider26.get()).put(RoomNotificationSettingsViewModel.class, factoryProvider27.get()).put(KeysBackupSettingsViewModel.class, factoryProvider28.get()).put(SharedSecureStorageViewModel.class, factoryProvider29.get()).put(UserListViewModel.class, factoryProvider30.get()).put(UserCodeSharedViewModel.class, factoryProvider31.get()).put(ReviewTermsViewModel.class, factoryProvider32.get()).put(ShareSpaceViewModel.class, factoryProvider33.get()).put(SpacePreviewViewModel.class, factoryProvider34.get()).put(SpacePeopleViewModel.class, factoryProvider35.get()).put(SpaceAddRoomsViewModel.class, factoryProvider36.get()).put(SpaceLeaveAdvancedViewModel.class, factoryProvider37.get()).put(SpaceInviteBottomSheetViewModel.class, factoryProvider38.get()).put(SpaceDirectoryViewModel.class, factoryProvider39.get()).put(CreateSpaceViewModel.class, factoryProvider40.get()).put(SpaceMenuViewModel.class, factoryProvider41.get()).put(SoftLogoutViewModel.class, factoryProvider42.get()).put(IncomingShareViewModel.class, factoryProvider43.get()).put(ThreePidsSettingsViewModel.class, factoryProvider44.get()).put(PushGatewaysViewModel.class, factoryProvider45.get()).put(HomeserverSettingsViewModel.class, factoryProvider46.get()).put(LocalePickerViewModel.class, factoryProvider47.get()).put(GossipingEventsPaperTrailViewModel.class, factoryProvider48.get()).put(AccountDataViewModel.class, factoryProvider49.get()).put(DevicesViewModel.class, factoryProvider50.get()).put(im.vector.app.features.settings.devices.v2.DevicesViewModel.class, factoryProvider51.get()).put(KeyRequestListViewModel.class, factoryProvider52.get()).put(KeyRequestViewModel.class, factoryProvider53.get()).put(CrossSigningSettingsViewModel.class, factoryProvider54.get()).put(DeactivateAccountViewModel.class, factoryProvider55.get()).put(RoomUploadsViewModel.class, factoryProvider56.get()).put(RoomJoinRuleChooseRestrictedViewModel.class, factoryProvider57.get()).put(RoomSettingsViewModel.class, factoryProvider58.get()).put(RoomPermissionsViewModel.class, factoryProvider59.get()).put(RoomMemberListViewModel.class, factoryProvider60.get()).put(RoomBannedMemberListViewModel.class, factoryProvider61.get()).put(RoomAliasViewModel.class, factoryProvider62.get()).put(RoomAliasBottomSheetViewModel.class, factoryProvider63.get()).put(RoomProfileViewModel.class, factoryProvider64.get()).put(RoomMemberProfileViewModel.class, factoryProvider65.get()).put(UserColorAccountDataViewModel.class, factoryProvider66.get()).put(RoomPreviewViewModel.class, factoryProvider67.get()).put(CreateRoomViewModel.class, factoryProvider68.get()).put(RequireActiveMembershipViewModel.class, factoryProvider69.get()).put(EmojiSearchResultViewModel.class, factoryProvider70.get()).put(BugReportViewModel.class, factoryProvider71.get()).put(MatrixToBottomSheetViewModel.class, factoryProvider72.get()).put(OnboardingViewModel.class, factoryProvider73.get()).put(LoginViewModel.class, factoryProvider74.get()).put(AnalyticsConsentViewModel.class, factoryProvider75.get()).put(AnalyticsAccountDataViewModel.class, factoryProvider76.get()).put(StartAppViewModel.class, factoryProvider77.get()).put(HomeServerCapabilitiesViewModel.class, factoryProvider78.get()).put(InviteUsersToRoomViewModel.class, factoryProvider79.get()).put(ViewEditHistoryViewModel.class, factoryProvider80.get()).put(MessageActionsViewModel.class, factoryProvider82.get()).put(VerificationChooseMethodViewModel.class, factoryProvider83.get()).put(VerificationEmojiCodeViewModel.class, factoryProvider84.get()).put(SearchViewModel.class, factoryProvider85.get()).put(UnreadMessagesSharedViewModel.class, factoryProvider86.get()).put(UnknownDeviceDetectorSharedViewModel.class, factoryProvider87.get()).put(DiscoverySettingsViewModel.class, factoryProvider88.get()).put(LegalsViewModel.class, factoryProvider89.get()).put(TimelineViewModel.class, factoryProvider90.get()).put(MessageComposerViewModel.class, factoryProvider91.get()).put(SetIdentityServerViewModel.class, factoryProvider92.get()).put(BreadcrumbsViewModel.class, factoryProvider93.get()).put(HomeDetailViewModel.class, factoryProvider94.get()).put(DeviceVerificationInfoBottomSheetViewModel.class, factoryProvider95.get()).put(DeviceListBottomSheetViewModel.class, factoryProvider96.get()).put(HomeActivityViewModel.class, factoryProvider97.get()).put(BootstrapSharedViewModel.class, factoryProvider98.get()).put(VerificationBottomSheetViewModel.class, factoryProvider99.get()).put(CreatePollViewModel.class, factoryProvider100.get()).put(LocationSharingViewModel.class, factoryProvider101.get()).put(LocationPreviewViewModel.class, factoryProvider102.get()).put(VectorAttachmentViewerViewModel.class, factoryProvider103.get()).put(LiveLocationMapViewModel.class, factoryProvider104.get()).put(FontScaleSettingViewModel.class, factoryProvider105.get()).put(HomeRoomListViewModel.class, factoryProvider106.get()).put(InvitesViewModel.class, factoryProvider107.get()).put(ReleaseNotesViewModel.class, factoryProvider108.get()).put(SessionOverviewViewModel.class, factoryProvider109.get()).put(OtherSessionsViewModel.class, factoryProvider110.get()).put(SessionDetailsViewModel.class, factoryProvider111.get()).put(RenameSessionViewModel.class, factoryProvider112.get()).put(QrCodeLoginViewModel.class, factoryProvider113.get()).put(SessionLearnMoreViewModel.class, factoryProvider114.get()).put(VectorSettingsLabsViewModel.class, factoryProvider115.get()).put(AttachmentTypeSelectorViewModel.class, factoryProvider116.get()).put(VectorSettingsNotificationPreferenceViewModel.class, factoryProvider117.get()).put(SetLinkViewModel.class, factoryProvider118.get()).put(RoomPollsViewModel.class, factoryProvider119.get()).build();
    }

    @CanIgnoreReturnValue
    private ShortcutCreator injectShortcutCreator(ShortcutCreator instance) {
      ShortcutCreator_MembersInjector.injectVectorPreferences(instance, singletonCImpl.vectorPreferences());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final MavericksViewModelCImpl mavericksViewModelCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl,
          MavericksViewModelCImpl mavericksViewModelCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.mavericksViewModelCImpl = mavericksViewModelCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      private T get0() {
        switch (id) {
          case 0: // im.vector.app.features.pin.lockscreen.ui.LockScreenViewModel.Factory 
          return (T) new LockScreenViewModel.Factory() {
            @Override
            public LockScreenViewModel create(LockScreenViewState initialState) {
              return new LockScreenViewModel(initialState, mavericksViewModelCImpl.pinCodeHelper(), mavericksViewModelCImpl.biometricHelperFactoryProvider.get(), mavericksViewModelCImpl.lockScreenKeysMigrator(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider(), singletonCImpl.keyguardManager());
            }
          };

          case 1: // im.vector.app.features.pin.lockscreen.biometrics.BiometricHelper.BiometricHelperFactory 
          return (T) new BiometricHelper.BiometricHelperFactory() {
            @Override
            public BiometricHelper create(LockScreenConfiguration configuration) {
              return new BiometricHelper(configuration, ApplicationContextModule_ProvideContextFactory.provideContext(singletonCImpl.applicationContextModule), singletonCImpl.lockScreenKeyRepositoryProvider.get(), singletonCImpl.biometricManager(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider());
            }
          };

          case 2: // im.vector.app.features.debug.analytics.DebugAnalyticsViewModel.Factory 
          return (T) new DebugAnalyticsViewModel.Factory() {
            @Override
            public DebugAnalyticsViewModel create(DebugAnalyticsViewState initialState2) {
              return new DebugAnalyticsViewModel(initialState2, singletonCImpl.analyticsStore());
            }
          };

          case 3: // im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel.Factory 
          return (T) new DebugPrivateSettingsViewModel.Factory() {
            @Override
            public DebugPrivateSettingsViewModel create(
                DebugPrivateSettingsViewState initialState3) {
              return new DebugPrivateSettingsViewModel(initialState3, singletonCImpl.debugVectorOverrides(), mavericksViewModelCImpl.releaseNotesPreferencesStore());
            }
          };

          case 4: // im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel.Factory 
          return (T) new DebugMemoryLeaksViewModel.Factory() {
            @Override
            public DebugMemoryLeaksViewModel create(DebugMemoryLeaksViewState initialState4) {
              return new DebugMemoryLeaksViewModel(initialState4, singletonCImpl.vectorPreferences(), new LeakCanaryLeakDetector());
            }
          };

          case 5: // im.vector.app.features.home.room.list.RoomListViewModel.Factory 
          return (T) new RoomListViewModel.Factory() {
            @Override
            public RoomListViewModel create(RoomListViewState initialState5) {
              return new RoomListViewModel(initialState5, singletonCImpl.session(), singletonCImpl.stringProvider(), singletonCImpl.spaceStateHandlerImplProvider.get(), singletonCImpl.vectorPreferences(), new CompileTimeAutoAcceptInvites(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 6: // im.vector.app.features.spaces.manage.SpaceManageRoomsViewModel.Factory 
          return (T) new SpaceManageRoomsViewModel.Factory() {
            @Override
            public SpaceManageRoomsViewModel create(SpaceManageRoomViewState initialState6) {
              return new SpaceManageRoomsViewModel(initialState6, singletonCImpl.session());
            }
          };

          case 7: // im.vector.app.features.spaces.manage.SpaceManageSharedViewModel.Factory 
          return (T) new SpaceManageSharedViewModel.Factory() {
            @Override
            public SpaceManageSharedViewModel create(SpaceManageViewState initialState7) {
              return new SpaceManageSharedViewModel(initialState7, singletonCImpl.session());
            }
          };

          case 8: // im.vector.app.features.spaces.SpaceListViewModel.Factory 
          return (T) new SpaceListViewModel.Factory() {
            @Override
            public SpaceListViewModel create(SpaceListViewState initialState8) {
              return new SpaceListViewModel(initialState8, singletonCImpl.spaceStateHandlerImplProvider.get(), singletonCImpl.session(), singletonCImpl.vectorPreferences(), new CompileTimeAutoAcceptInvites(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 9: // im.vector.app.features.auth.ReAuthViewModel.Factory 
          return (T) new ReAuthViewModel.Factory() {
            @Override
            public ReAuthViewModel create(ReAuthState initialState9) {
              return new ReAuthViewModel(initialState9, singletonCImpl.session(), singletonCImpl.providesMatrixProvider.get());
            }
          };

          case 10: // im.vector.app.features.call.VectorCallViewModel.Factory 
          return (T) new VectorCallViewModel.Factory() {
            @Override
            public VectorCallViewModel create(VectorCallViewState initialState10) {
              return new VectorCallViewModel(initialState10, singletonCImpl.session(), singletonCImpl.webRtcCallManagerProvider.get(), mavericksViewModelCImpl.callProximityManager(), mavericksViewModelCImpl.dialPadLookup(), mavericksViewModelCImpl.directRoomHelper());
            }
          };

          case 11: // im.vector.app.features.call.conference.JitsiCallViewModel.Factory 
          return (T) new JitsiCallViewModel.Factory() {
            @Override
            public JitsiCallViewModel create(JitsiCallViewState initialState11) {
              return new JitsiCallViewModel(initialState11, singletonCImpl.session(), mavericksViewModelCImpl.jitsiService());
            }
          };

          case 12: // im.vector.app.features.roomdirectory.RoomDirectoryViewModel.Factory 
          return (T) new RoomDirectoryViewModel.Factory() {
            @Override
            public RoomDirectoryViewModel create(PublicRoomsViewState initialState12) {
              return new RoomDirectoryViewModel(initialState12, singletonCImpl.vectorPreferences(), singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get(), mavericksViewModelCImpl.explicitTermFilter());
            }
          };

          case 13: // im.vector.app.features.home.room.detail.timeline.reactions.ViewReactionsViewModel.Factory 
          return (T) new ViewReactionsViewModel.Factory() {
            @Override
            public ViewReactionsViewModel create(DisplayReactionsViewState initialState13) {
              return new ViewReactionsViewModel(initialState13, singletonCImpl.session(), singletonCImpl.vectorDateFormatter());
            }
          };

          case 14: // im.vector.app.features.widgets.permissions.RoomWidgetPermissionViewModel.Factory 
          return (T) new RoomWidgetPermissionViewModel.Factory() {
            @Override
            public RoomWidgetPermissionViewModel create(
                RoomWidgetPermissionViewState initialState14) {
              return new RoomWidgetPermissionViewModel(initialState14, singletonCImpl.session());
            }
          };

          case 15: // im.vector.app.features.widgets.WidgetViewModel.Factory 
          return (T) new WidgetViewModel.Factory() {
            @Override
            public WidgetViewModel create(WidgetViewState initialState15) {
              return new WidgetViewModel(initialState15, mavericksViewModelCImpl.factoryProvider15.get(), singletonCImpl.stringProvider(), singletonCImpl.session());
            }
          };

          case 16: // im.vector.app.features.widgets.WidgetPostAPIHandler.Factory 
          return (T) new WidgetPostAPIHandler.Factory() {
            @Override
            public WidgetPostAPIHandler create(String roomId) {
              return new WidgetPostAPIHandler(roomId, singletonCImpl.stringProvider(), singletonCImpl.session());
            }
          };

          case 17: // im.vector.app.features.workers.signout.ServerBackupStatusViewModel.Factory 
          return (T) new ServerBackupStatusViewModel.Factory() {
            @Override
            public ServerBackupStatusViewModel create(ServerBackupStatusViewState initialState16) {
              return new ServerBackupStatusViewModel(initialState16, singletonCImpl.session(), singletonCImpl.providesDefaultSharedPreferencesProvider.get());
            }
          };

          case 18: // im.vector.app.features.workers.signout.SignoutCheckViewModel.Factory 
          return (T) new SignoutCheckViewModel.Factory() {
            @Override
            public SignoutCheckViewModel create(SignoutCheckViewState initialState17) {
              return new SignoutCheckViewModel(initialState17, singletonCImpl.session(), mavericksViewModelCImpl.keysExporter());
            }
          };

          case 19: // im.vector.app.features.roomdirectory.picker.RoomDirectoryPickerViewModel.Factory 
          return (T) new RoomDirectoryPickerViewModel.Factory() {
            @Override
            public RoomDirectoryPickerViewModel create(
                RoomDirectoryPickerViewState initialState18) {
              return new RoomDirectoryPickerViewModel(initialState18, singletonCImpl.session(), singletonCImpl.sharedPreferencesUiStateRepository(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.roomDirectoryListCreator());
            }
          };

          case 20: // im.vector.app.features.devtools.RoomDevToolViewModel.Factory 
          return (T) new RoomDevToolViewModel.Factory() {
            @Override
            public RoomDevToolViewModel create(RoomDevToolViewState initialState19) {
              return new RoomDevToolViewModel(initialState19, singletonCImpl.defaultErrorFormatter(), singletonCImpl.stringProvider(), singletonCImpl.session());
            }
          };

          case 21: // im.vector.app.features.home.room.detail.upgrade.MigrateRoomViewModel.Factory 
          return (T) new MigrateRoomViewModel.Factory() {
            @Override
            public MigrateRoomViewModel create(MigrateRoomViewState initialState20) {
              return new MigrateRoomViewModel(initialState20, singletonCImpl.session(), mavericksViewModelCImpl.upgradeRoomViewModelTask());
            }
          };

          case 22: // im.vector.app.features.settings.ignored.IgnoredUsersViewModel.Factory 
          return (T) new IgnoredUsersViewModel.Factory() {
            @Override
            public IgnoredUsersViewModel create(IgnoredUsersViewState initialState21) {
              return new IgnoredUsersViewModel(initialState21, singletonCImpl.session());
            }
          };

          case 23: // im.vector.app.features.call.transfer.CallTransferViewModel.Factory 
          return (T) new CallTransferViewModel.Factory() {
            @Override
            public CallTransferViewModel create(CallTransferViewState initialState22) {
              return new CallTransferViewModel(initialState22, singletonCImpl.webRtcCallManagerProvider.get());
            }
          };

          case 24: // im.vector.app.features.contactsbook.ContactsBookViewModel.Factory 
          return (T) new ContactsBookViewModel.Factory() {
            @Override
            public ContactsBookViewModel create(ContactsBookViewState initialState23) {
              return new ContactsBookViewModel(initialState23, mavericksViewModelCImpl.contactsDataSource(), singletonCImpl.stringProvider(), singletonCImpl.session());
            }
          };

          case 25: // im.vector.app.features.createdirect.CreateDirectRoomViewModel.Factory 
          return (T) new CreateDirectRoomViewModel.Factory() {
            @Override
            public CreateDirectRoomViewModel create(CreateDirectRoomViewState initialState24) {
              return new CreateDirectRoomViewModel(initialState24, singletonCImpl.rawService(), singletonCImpl.vectorPreferences(), singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 26: // im.vector.app.features.qrcode.QrCodeScannerViewModel.Factory 
          return (T) new QrCodeScannerViewModel.Factory() {
            @Override
            public QrCodeScannerViewModel create(VectorDummyViewState initialState25) {
              return new QrCodeScannerViewModel(initialState25);
            }
          };

          case 27: // im.vector.app.features.roomprofile.notifications.RoomNotificationSettingsViewModel.Factory 
          return (T) new RoomNotificationSettingsViewModel.Factory() {
            @Override
            public RoomNotificationSettingsViewModel create(
                RoomNotificationSettingsViewState initialState26) {
              return new RoomNotificationSettingsViewModel(initialState26, singletonCImpl.session());
            }
          };

          case 28: // im.vector.app.features.crypto.keysbackup.settings.KeysBackupSettingsViewModel.Factory 
          return (T) new KeysBackupSettingsViewModel.Factory() {
            @Override
            public KeysBackupSettingsViewModel create(KeysBackupSettingViewState initialState27) {
              return new KeysBackupSettingsViewModel(initialState27, singletonCImpl.session());
            }
          };

          case 29: // im.vector.app.features.crypto.quads.SharedSecureStorageViewModel.Factory 
          return (T) new SharedSecureStorageViewModel.Factory() {
            @Override
            public SharedSecureStorageViewModel create(
                SharedSecureStorageViewState initialState28) {
              return new SharedSecureStorageViewModel(initialState28, singletonCImpl.stringProvider(), singletonCImpl.session(), singletonCImpl.providesMatrixProvider.get());
            }
          };

          case 30: // im.vector.app.features.userdirectory.UserListViewModel.Factory 
          return (T) new UserListViewModel.Factory() {
            @Override
            public UserListViewModel create(UserListViewState initialState29) {
              return new UserListViewModel(initialState29, singletonCImpl.stringProvider(), singletonCImpl.session());
            }
          };

          case 31: // im.vector.app.features.usercode.UserCodeSharedViewModel.Factory 
          return (T) new UserCodeSharedViewModel.Factory() {
            @Override
            public UserCodeSharedViewModel create(UserCodeState initialState30) {
              return new UserCodeSharedViewModel(initialState30, singletonCImpl.session(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.directRoomHelper());
            }
          };

          case 32: // im.vector.app.features.terms.ReviewTermsViewModel.Factory 
          return (T) new ReviewTermsViewModel.Factory() {
            @Override
            public ReviewTermsViewModel create(ReviewTermsViewState initialState31) {
              return new ReviewTermsViewModel(initialState31, singletonCImpl.session());
            }
          };

          case 33: // im.vector.app.features.spaces.share.ShareSpaceViewModel.Factory 
          return (T) new ShareSpaceViewModel.Factory() {
            @Override
            public ShareSpaceViewModel create(ShareSpaceViewState initialState32) {
              return new ShareSpaceViewModel(initialState32, singletonCImpl.session());
            }
          };

          case 34: // im.vector.app.features.spaces.preview.SpacePreviewViewModel.Factory 
          return (T) new SpacePreviewViewModel.Factory() {
            @Override
            public SpacePreviewViewModel create(SpacePreviewState initialState33) {
              return new SpacePreviewViewModel(initialState33, singletonCImpl.defaultErrorFormatter(), singletonCImpl.session());
            }
          };

          case 35: // im.vector.app.features.spaces.people.SpacePeopleViewModel.Factory 
          return (T) new SpacePeopleViewModel.Factory() {
            @Override
            public SpacePeopleViewModel create(SpacePeopleViewState initialState34) {
              return new SpacePeopleViewModel(initialState34, singletonCImpl.rawService(), singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 36: // im.vector.app.features.spaces.manage.SpaceAddRoomsViewModel.Factory 
          return (T) new SpaceAddRoomsViewModel.Factory() {
            @Override
            public SpaceAddRoomsViewModel create(SpaceAddRoomsState initialState35) {
              return new SpaceAddRoomsViewModel(initialState35, singletonCImpl.session());
            }
          };

          case 37: // im.vector.app.features.spaces.leave.SpaceLeaveAdvancedViewModel.Factory 
          return (T) new SpaceLeaveAdvancedViewModel.Factory() {
            @Override
            public SpaceLeaveAdvancedViewModel create(SpaceLeaveAdvanceViewState initialState36) {
              return new SpaceLeaveAdvancedViewModel(initialState36, singletonCImpl.session(), singletonCImpl.spaceStateHandlerImplProvider.get());
            }
          };

          case 38: // im.vector.app.features.spaces.invite.SpaceInviteBottomSheetViewModel.Factory 
          return (T) new SpaceInviteBottomSheetViewModel.Factory() {
            @Override
            public SpaceInviteBottomSheetViewModel create(
                SpaceInviteBottomSheetState initialState37) {
              return new SpaceInviteBottomSheetViewModel(initialState37, singletonCImpl.session(), singletonCImpl.defaultErrorFormatter());
            }
          };

          case 39: // im.vector.app.features.spaces.explore.SpaceDirectoryViewModel.Factory 
          return (T) new SpaceDirectoryViewModel.Factory() {
            @Override
            public SpaceDirectoryViewModel create(SpaceDirectoryState initialState38) {
              return new SpaceDirectoryViewModel(initialState38, singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 40: // im.vector.app.features.spaces.create.CreateSpaceViewModel.Factory 
          return (T) new CreateSpaceViewModel.Factory() {
            @Override
            public CreateSpaceViewModel create(CreateSpaceState initialState39) {
              return new CreateSpaceViewModel(initialState39, singletonCImpl.session(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.createSpaceViewModelTask(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 41: // im.vector.app.features.spaces.SpaceMenuViewModel.Factory 
          return (T) new SpaceMenuViewModel.Factory() {
            @Override
            public SpaceMenuViewModel create(SpaceMenuState initialState40) {
              return new SpaceMenuViewModel(initialState40, singletonCImpl.session(), singletonCImpl.spaceStateHandlerImplProvider.get());
            }
          };

          case 42: // im.vector.app.features.signout.soft.SoftLogoutViewModel.Factory 
          return (T) new SoftLogoutViewModel.Factory() {
            @Override
            public SoftLogoutViewModel create(SoftLogoutViewState initialState41) {
              return new SoftLogoutViewModel(initialState41, singletonCImpl.session(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.authenticationService());
            }
          };

          case 43: // im.vector.app.features.share.IncomingShareViewModel.Factory 
          return (T) new IncomingShareViewModel.Factory() {
            @Override
            public IncomingShareViewModel create(IncomingShareViewState initialState42) {
              return new IncomingShareViewModel(initialState42, singletonCImpl.session(), mavericksViewModelCImpl.breadcrumbsRoomComparator());
            }
          };

          case 44: // im.vector.app.features.settings.threepids.ThreePidsSettingsViewModel.Factory 
          return (T) new ThreePidsSettingsViewModel.Factory() {
            @Override
            public ThreePidsSettingsViewModel create(ThreePidsSettingsViewState initialState43) {
              return new ThreePidsSettingsViewModel(initialState43, singletonCImpl.session(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.pendingAuthHandler());
            }
          };

          case 45: // im.vector.app.features.settings.push.PushGatewaysViewModel.Factory 
          return (T) new PushGatewaysViewModel.Factory() {
            @Override
            public PushGatewaysViewModel create(PushGatewayViewState initialState44) {
              return new PushGatewaysViewModel(initialState44, singletonCImpl.session());
            }
          };

          case 46: // im.vector.app.features.settings.homeserver.HomeserverSettingsViewModel.Factory 
          return (T) new HomeserverSettingsViewModel.Factory() {
            @Override
            public HomeserverSettingsViewModel create(HomeServerSettingsViewState initialState45) {
              return new HomeserverSettingsViewModel(initialState45, singletonCImpl.session());
            }
          };

          case 47: // im.vector.app.features.settings.locale.LocalePickerViewModel.Factory 
          return (T) new LocalePickerViewModel.Factory() {
            @Override
            public LocalePickerViewModel create(LocalePickerViewState initialState46) {
              return new LocalePickerViewModel(initialState46, singletonCImpl.vectorConfiguration(), singletonCImpl.vectorLocaleProvider.get());
            }
          };

          case 48: // im.vector.app.features.settings.devtools.GossipingEventsPaperTrailViewModel.Factory 
          return (T) new GossipingEventsPaperTrailViewModel.Factory() {
            @Override
            public GossipingEventsPaperTrailViewModel create(
                GossipingEventsPaperTrailState initialState47) {
              return new GossipingEventsPaperTrailViewModel(initialState47, singletonCImpl.session());
            }
          };

          case 49: // im.vector.app.features.settings.devtools.AccountDataViewModel.Factory 
          return (T) new AccountDataViewModel.Factory() {
            @Override
            public AccountDataViewModel create(AccountDataViewState initialState48) {
              return new AccountDataViewModel(initialState48, singletonCImpl.session());
            }
          };

          case 50: // im.vector.app.features.settings.devices.DevicesViewModel.Factory 
          return (T) new DevicesViewModel.Factory() {
            @Override
            public DevicesViewModel create(DevicesViewState initialState49) {
              return new DevicesViewModel(initialState49, singletonCImpl.session(), singletonCImpl.reAuthHelperProvider.get(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.pendingAuthHandler(), mavericksViewModelCImpl.checkIfSessionIsInactiveUseCase(), mavericksViewModelCImpl.getCurrentSessionCrossSigningInfoUseCase(), mavericksViewModelCImpl.getEncryptionTrustLevelForDeviceUseCase());
            }
          };

          case 51: // im.vector.app.features.settings.devices.v2.DevicesViewModel.Factory 
          return (T) new im.vector.app.features.settings.devices.v2.DevicesViewModel.Factory() {
            @Override
            public im.vector.app.features.settings.devices.v2.DevicesViewModel create(
                im.vector.app.features.settings.devices.v2.DevicesViewState initialState50) {
              return new im.vector.app.features.settings.devices.v2.DevicesViewModel(initialState50, singletonCImpl.activeSessionHolderProvider.get(), mavericksViewModelCImpl.getCurrentSessionCrossSigningInfoUseCase2(), mavericksViewModelCImpl.getDeviceFullInfoListUseCase(), mavericksViewModelCImpl.refreshDevicesOnCryptoDevicesChangeUseCase(), mavericksViewModelCImpl.checkIfCurrentSessionCanBeVerifiedUseCase(), mavericksViewModelCImpl.signoutSessionsUseCase(), mavericksViewModelCImpl.pendingAuthHandler(), mavericksViewModelCImpl.refreshDevicesUseCase(), singletonCImpl.vectorPreferences(), mavericksViewModelCImpl.toggleIpAddressVisibilityUseCase());
            }
          };

          case 52: // im.vector.app.features.settings.devtools.KeyRequestListViewModel.Factory 
          return (T) new KeyRequestListViewModel.Factory() {
            @Override
            public KeyRequestListViewModel create(KeyRequestListViewState initialState51) {
              return new KeyRequestListViewModel(initialState51, singletonCImpl.session());
            }
          };

          case 53: // im.vector.app.features.settings.devtools.KeyRequestViewModel.Factory 
          return (T) new KeyRequestViewModel.Factory() {
            @Override
            public KeyRequestViewModel create(KeyRequestViewState initialState52) {
              return new KeyRequestViewModel(initialState52, singletonCImpl.session());
            }
          };

          case 54: // im.vector.app.features.settings.crosssigning.CrossSigningSettingsViewModel.Factory 
          return (T) new CrossSigningSettingsViewModel.Factory() {
            @Override
            public CrossSigningSettingsViewModel create(
                CrossSigningSettingsViewState initialState53) {
              return new CrossSigningSettingsViewModel(initialState53, singletonCImpl.session(), singletonCImpl.reAuthHelperProvider.get(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.pendingAuthHandler());
            }
          };

          case 55: // im.vector.app.features.settings.account.deactivation.DeactivateAccountViewModel.Factory 
          return (T) new DeactivateAccountViewModel.Factory() {
            @Override
            public DeactivateAccountViewModel create(DeactivateAccountViewState initialState54) {
              return new DeactivateAccountViewModel(initialState54, singletonCImpl.session(), mavericksViewModelCImpl.pendingAuthHandler());
            }
          };

          case 56: // im.vector.app.features.roomprofile.uploads.RoomUploadsViewModel.Factory 
          return (T) new RoomUploadsViewModel.Factory() {
            @Override
            public RoomUploadsViewModel create(RoomUploadsViewState initialState55) {
              return new RoomUploadsViewModel(initialState55, singletonCImpl.session());
            }
          };

          case 57: // im.vector.app.features.roomprofile.settings.joinrule.advanced.RoomJoinRuleChooseRestrictedViewModel.Factory 
          return (T) new RoomJoinRuleChooseRestrictedViewModel.Factory() {
            @Override
            public RoomJoinRuleChooseRestrictedViewModel create(
                RoomJoinRuleChooseRestrictedState initialState56) {
              return new RoomJoinRuleChooseRestrictedViewModel(initialState56, singletonCImpl.session(), singletonCImpl.vectorPreferences(), singletonCImpl.stringProvider());
            }
          };

          case 58: // im.vector.app.features.roomprofile.settings.RoomSettingsViewModel.Factory 
          return (T) new RoomSettingsViewModel.Factory() {
            @Override
            public RoomSettingsViewModel create(RoomSettingsViewState initialState57) {
              return new RoomSettingsViewModel(initialState57, singletonCImpl.vectorPreferences(), singletonCImpl.session());
            }
          };

          case 59: // im.vector.app.features.roomprofile.permissions.RoomPermissionsViewModel.Factory 
          return (T) new RoomPermissionsViewModel.Factory() {
            @Override
            public RoomPermissionsViewModel create(RoomPermissionsViewState initialState58) {
              return new RoomPermissionsViewModel(initialState58, singletonCImpl.session());
            }
          };

          case 60: // im.vector.app.features.roomprofile.members.RoomMemberListViewModel.Factory 
          return (T) new RoomMemberListViewModel.Factory() {
            @Override
            public RoomMemberListViewModel create(RoomMemberListViewState initialState59) {
              return new RoomMemberListViewModel(initialState59, new RoomMemberSummaryComparator(), singletonCImpl.session());
            }
          };

          case 61: // im.vector.app.features.roomprofile.banned.RoomBannedMemberListViewModel.Factory 
          return (T) new RoomBannedMemberListViewModel.Factory() {
            @Override
            public RoomBannedMemberListViewModel create(
                RoomBannedMemberListViewState initialState60) {
              return new RoomBannedMemberListViewModel(initialState60, singletonCImpl.stringProvider(), singletonCImpl.session());
            }
          };

          case 62: // im.vector.app.features.roomprofile.alias.RoomAliasViewModel.Factory 
          return (T) new RoomAliasViewModel.Factory() {
            @Override
            public RoomAliasViewModel create(RoomAliasViewState initialState61) {
              return new RoomAliasViewModel(initialState61, singletonCImpl.session());
            }
          };

          case 63: // im.vector.app.features.roomprofile.alias.detail.RoomAliasBottomSheetViewModel.Factory 
          return (T) new RoomAliasBottomSheetViewModel.Factory() {
            @Override
            public RoomAliasBottomSheetViewModel create(RoomAliasBottomSheetState initialState62) {
              return new RoomAliasBottomSheetViewModel(initialState62, singletonCImpl.session());
            }
          };

          case 64: // im.vector.app.features.roomprofile.RoomProfileViewModel.Factory 
          return (T) new RoomProfileViewModel.Factory() {
            @Override
            public RoomProfileViewModel create(RoomProfileViewState initialState63) {
              return new RoomProfileViewModel(initialState63, singletonCImpl.stringProvider(), mavericksViewModelCImpl.shortcutCreator(), singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 65: // im.vector.app.features.roommemberprofile.RoomMemberProfileViewModel.Factory 
          return (T) new RoomMemberProfileViewModel.Factory() {
            @Override
            public RoomMemberProfileViewModel create(RoomMemberProfileViewState initialState64) {
              return new RoomMemberProfileViewModel(initialState64, singletonCImpl.stringProvider(), singletonCImpl.matrixItemColorProvider.get(), mavericksViewModelCImpl.directRoomHelper(), singletonCImpl.session());
            }
          };

          case 66: // im.vector.app.features.home.UserColorAccountDataViewModel.Factory 
          return (T) new UserColorAccountDataViewModel.Factory() {
            @Override
            public UserColorAccountDataViewModel create(VectorDummyViewState initialState65) {
              return new UserColorAccountDataViewModel(initialState65, singletonCImpl.session(), singletonCImpl.matrixItemColorProvider.get());
            }
          };

          case 67: // im.vector.app.features.roomdirectory.roompreview.RoomPreviewViewModel.Factory 
          return (T) new RoomPreviewViewModel.Factory() {
            @Override
            public RoomPreviewViewModel create(RoomPreviewViewState initialState66) {
              return new RoomPreviewViewModel(initialState66, singletonCImpl.defaultVectorAnalyticsProvider.get(), singletonCImpl.session());
            }
          };

          case 68: // im.vector.app.features.roomdirectory.createroom.CreateRoomViewModel.Factory 
          return (T) new CreateRoomViewModel.Factory() {
            @Override
            public CreateRoomViewModel create(CreateRoomViewState initialState67) {
              return new CreateRoomViewModel(initialState67, singletonCImpl.session(), singletonCImpl.rawService(), singletonCImpl.spaceStateHandlerImplProvider.get(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 69: // im.vector.app.features.room.RequireActiveMembershipViewModel.Factory 
          return (T) new RequireActiveMembershipViewModel.Factory() {
            @Override
            public RequireActiveMembershipViewModel create(
                RequireActiveMembershipViewState initialState68) {
              return new RequireActiveMembershipViewModel(initialState68, singletonCImpl.stringProvider(), singletonCImpl.session());
            }
          };

          case 70: // im.vector.app.features.reactions.EmojiSearchResultViewModel.Factory 
          return (T) new EmojiSearchResultViewModel.Factory() {
            @Override
            public EmojiSearchResultViewModel create(EmojiSearchResultViewState initialState69) {
              return new EmojiSearchResultViewModel(initialState69, singletonCImpl.emojiDataSourceProvider.get());
            }
          };

          case 71: // im.vector.app.features.rageshake.BugReportViewModel.Factory 
          return (T) new BugReportViewModel.Factory() {
            @Override
            public BugReportViewModel create(BugReportState initialState70) {
              return new BugReportViewModel(initialState70, singletonCImpl.activeSessionHolderProvider.get());
            }
          };

          case 72: // im.vector.app.features.matrixto.MatrixToBottomSheetViewModel.Factory 
          return (T) new MatrixToBottomSheetViewModel.Factory() {
            @Override
            public MatrixToBottomSheetViewModel create(MatrixToBottomSheetState initialState71) {
              return new MatrixToBottomSheetViewModel(initialState71, singletonCImpl.session(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.directRoomHelper(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 73: // im.vector.app.features.onboarding.OnboardingViewModel.Factory 
          return (T) new OnboardingViewModel.Factory() {
            @Override
            public OnboardingViewModel create(OnboardingViewState initialState72) {
              return new OnboardingViewModel(initialState72, singletonCImpl.context(), singletonCImpl.authenticationService(), singletonCImpl.activeSessionHolderProvider.get(), new HomeServerConnectionConfigFactory(), singletonCImpl.reAuthHelperProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.homeServerHistoryService(), singletonCImpl.debugVectorFeatures(), singletonCImpl.defaultVectorAnalyticsProvider.get(), mavericksViewModelCImpl.uriFilenameResolver(), mavericksViewModelCImpl.directLoginUseCase(), mavericksViewModelCImpl.startAuthenticationFlowUseCase(), singletonCImpl.debugVectorOverrides(), mavericksViewModelCImpl.registrationActionHandler(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider(), singletonCImpl.configureAndStartSessionUseCase(), singletonCImpl.provideLoginRepositoryProvider.get());
            }
          };

          case 74: // im.vector.app.features.login.LoginViewModel.Factory 
          return (T) new LoginViewModel.Factory() {
            @Override
            public LoginViewModel create(LoginViewState initialState73) {
              return new LoginViewModel(initialState73, singletonCImpl.context(), singletonCImpl.authenticationService(), singletonCImpl.activeSessionHolderProvider.get(), new HomeServerConnectionConfigFactory(), singletonCImpl.reAuthHelperProvider.get(), singletonCImpl.stringProvider(), singletonCImpl.homeServerHistoryService(), singletonCImpl.configureAndStartSessionUseCase());
            }
          };

          case 75: // im.vector.app.features.analytics.ui.consent.AnalyticsConsentViewModel.Factory 
          return (T) new AnalyticsConsentViewModel.Factory() {
            @Override
            public AnalyticsConsentViewModel create(AnalyticsConsentViewState initialState74) {
              return new AnalyticsConsentViewModel(initialState74, singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 76: // im.vector.app.features.analytics.accountdata.AnalyticsAccountDataViewModel.Factory 
          return (T) new AnalyticsAccountDataViewModel.Factory() {
            @Override
            public AnalyticsAccountDataViewModel create(VectorDummyViewState initialState75) {
              return new AnalyticsAccountDataViewModel(initialState75, singletonCImpl.session(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 77: // im.vector.app.features.start.StartAppViewModel.Factory 
          return (T) new StartAppViewModel.Factory() {
            @Override
            public StartAppViewModel create(StartAppViewState initialState76) {
              return new StartAppViewModel(initialState76, singletonCImpl.authenticationService(), singletonCImpl.activeSessionHolderProvider.get(), VectorStaticModule_ProvidesCoroutineDispatchersFactory.providesCoroutineDispatchers());
            }
          };

          case 78: // im.vector.app.features.homeserver.HomeServerCapabilitiesViewModel.Factory 
          return (T) new HomeServerCapabilitiesViewModel.Factory() {
            @Override
            public HomeServerCapabilitiesViewModel create(
                HomeServerCapabilitiesViewState initialState77) {
              return new HomeServerCapabilitiesViewModel(initialState77, singletonCImpl.session(), singletonCImpl.rawService());
            }
          };

          case 79: // im.vector.app.features.invite.InviteUsersToRoomViewModel.Factory 
          return (T) new InviteUsersToRoomViewModel.Factory() {
            @Override
            public InviteUsersToRoomViewModel create(InviteUsersToRoomViewState initialState78) {
              return new InviteUsersToRoomViewModel(initialState78, singletonCImpl.session(), singletonCImpl.stringProvider());
            }
          };

          case 80: // im.vector.app.features.home.room.detail.timeline.edithistory.ViewEditHistoryViewModel.Factory 
          return (T) new ViewEditHistoryViewModel.Factory() {
            @Override
            public ViewEditHistoryViewModel create(ViewEditHistoryViewState initialState79) {
              return new ViewEditHistoryViewModel(initialState79, singletonCImpl.session());
            }
          };

          case 81: // im.vector.app.features.home.room.detail.timeline.action.MessageActionsViewModel.Factory 
          return (T) new MessageActionsViewModel.Factory() {
            @Override
            public MessageActionsViewModel create(MessageActionState initialState80) {
              return new MessageActionsViewModel(initialState80, DoubleCheck.lazy(singletonCImpl.eventHtmlRendererProvider), singletonCImpl.vectorHtmlCompressorProvider.get(), singletonCImpl.session(), singletonCImpl.noticeEventFormatter(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.stringProvider(), mavericksViewModelCImpl.factoryProvider81.get(), singletonCImpl.vectorPreferences(), new CheckIfCanReplyEventUseCase(), mavericksViewModelCImpl.checkIfCanRedactEventUseCase());
            }
          };

          case 82: // im.vector.app.features.html.PillsPostProcessor.Factory 
          return (T) new PillsPostProcessor.Factory() {
            @Override
            public PillsPostProcessor create(String roomId2) {
              return new PillsPostProcessor(roomId2, singletonCImpl.context(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.activeSessionHolderProvider.get());
            }
          };

          case 83: // im.vector.app.features.crypto.verification.choose.VerificationChooseMethodViewModel.Factory 
          return (T) new VerificationChooseMethodViewModel.Factory() {
            @Override
            public VerificationChooseMethodViewModel create(
                VerificationChooseMethodViewState initialState81) {
              return new VerificationChooseMethodViewModel(initialState81, singletonCImpl.session());
            }
          };

          case 84: // im.vector.app.features.crypto.verification.emoji.VerificationEmojiCodeViewModel.Factory 
          return (T) new VerificationEmojiCodeViewModel.Factory() {
            @Override
            public VerificationEmojiCodeViewModel create(
                VerificationEmojiCodeViewState initialState82) {
              return new VerificationEmojiCodeViewModel(initialState82, singletonCImpl.session());
            }
          };

          case 85: // im.vector.app.features.home.room.detail.search.SearchViewModel.Factory 
          return (T) new SearchViewModel.Factory() {
            @Override
            public SearchViewModel create(SearchViewState initialState83) {
              return new SearchViewModel(initialState83, singletonCImpl.session());
            }
          };

          case 86: // im.vector.app.features.home.UnreadMessagesSharedViewModel.Factory 
          return (T) new UnreadMessagesSharedViewModel.Factory() {
            @Override
            public UnreadMessagesSharedViewModel create(UnreadMessagesState initialState84) {
              return new UnreadMessagesSharedViewModel(initialState84, singletonCImpl.session(), singletonCImpl.vectorPreferences(), singletonCImpl.spaceStateHandlerImplProvider.get(), new CompileTimeAutoAcceptInvites());
            }
          };

          case 87: // im.vector.app.features.home.UnknownDeviceDetectorSharedViewModel.Factory 
          return (T) new UnknownDeviceDetectorSharedViewModel.Factory() {
            @Override
            public UnknownDeviceDetectorSharedViewModel create(UnknownDevicesState initialState85) {
              return new UnknownDeviceDetectorSharedViewModel(initialState85, singletonCImpl.session(), new DefaultClock(), mavericksViewModelCImpl.shouldShowUnverifiedSessionsAlertUseCase(), mavericksViewModelCImpl.setUnverifiedSessionsAlertShownUseCase(), mavericksViewModelCImpl.isNewLoginAlertShownUseCase(), mavericksViewModelCImpl.setNewLoginAlertShownUseCase(), mavericksViewModelCImpl.deleteUnusedClientInformationUseCase());
            }
          };

          case 88: // im.vector.app.features.discovery.DiscoverySettingsViewModel.Factory 
          return (T) new DiscoverySettingsViewModel.Factory() {
            @Override
            public DiscoverySettingsViewModel create(DiscoverySettingsState initialState86) {
              return new DiscoverySettingsViewModel(initialState86, singletonCImpl.session(), singletonCImpl.stringProvider());
            }
          };

          case 89: // im.vector.app.features.settings.legals.LegalsViewModel.Factory 
          return (T) new LegalsViewModel.Factory() {
            @Override
            public LegalsViewModel create(LegalsState initialState87) {
              return new LegalsViewModel(initialState87, singletonCImpl.session(), singletonCImpl.stringProvider());
            }
          };

          case 90: // im.vector.app.features.home.room.detail.TimelineViewModel.Factory 
          return (T) new TimelineViewModel.Factory() {
            @Override
            public TimelineViewModel create(RoomDetailViewState initialState88) {
              return new TimelineViewModel(initialState88, singletonCImpl.vectorPreferences(), singletonCImpl.vectorDataStore(), singletonCImpl.stringProvider(), singletonCImpl.session(), singletonCImpl.rawService(), singletonCImpl.supportedVerificationMethodsProvider(), mavericksViewModelCImpl.stickerPickerActionHandler(), mavericksViewModelCImpl.typingHelper(), singletonCImpl.webRtcCallManagerProvider.get(), mavericksViewModelCImpl.chatEffectManager(), mavericksViewModelCImpl.directRoomHelper(), mavericksViewModelCImpl.jitsiService(), singletonCImpl.defaultVectorAnalyticsProvider.get(), singletonCImpl.jitsiActiveConferenceHolderProvider.get(), singletonCImpl.decryptionFailureTrackerProvider.get(), singletonCImpl.notificationDrawerManagerProvider.get(), singletonCImpl.locationSharingServiceConnectionProvider.get(), mavericksViewModelCImpl.stopLiveLocationShareUseCase(), new RedactLiveLocationShareEventUseCase(), ConfigurationModule_ProvidesCryptoConfigFactory.providesCryptoConfig(), singletonCImpl.providesBuildMetaProvider.get(), mavericksViewModelCImpl.timelineFactory(), singletonCImpl.spaceStateHandlerImplProvider.get(), mavericksViewModelCImpl.voiceBroadcastHelper());
            }
          };

          case 91: // im.vector.app.features.home.room.detail.composer.MessageComposerViewModel.Factory 
          return (T) new MessageComposerViewModel.Factory() {
            @Override
            public MessageComposerViewModel create(MessageComposerViewState initialState89) {
              return new MessageComposerViewModel(initialState89, singletonCImpl.session(), singletonCImpl.stringProvider(), singletonCImpl.vectorPreferences(), mavericksViewModelCImpl.commandParser(), new RainbowGenerator(), mavericksViewModelCImpl.audioMessageHelper(), singletonCImpl.defaultVectorAnalyticsProvider.get(), mavericksViewModelCImpl.voiceBroadcastHelper(), new DefaultClock(), singletonCImpl.getVoiceBroadcastStateEventLiveUseCase());
            }
          };

          case 92: // im.vector.app.features.discovery.change.SetIdentityServerViewModel.Factory 
          return (T) new SetIdentityServerViewModel.Factory() {
            @Override
            public SetIdentityServerViewModel create(SetIdentityServerState initialState90) {
              return new SetIdentityServerViewModel(initialState90, singletonCImpl.session(), singletonCImpl.stringProvider());
            }
          };

          case 93: // im.vector.app.features.home.room.breadcrumbs.BreadcrumbsViewModel.Factory 
          return (T) new BreadcrumbsViewModel.Factory() {
            @Override
            public BreadcrumbsViewModel create(BreadcrumbsViewState initialState91) {
              return new BreadcrumbsViewModel(initialState91, singletonCImpl.session());
            }
          };

          case 94: // im.vector.app.features.home.HomeDetailViewModel.Factory 
          return (T) new HomeDetailViewModel.Factory() {
            @Override
            public HomeDetailViewModel create(HomeDetailViewState initialState92) {
              return new HomeDetailViewModel(initialState92, singletonCImpl.session(), singletonCImpl.sharedPreferencesUiStateRepository(), singletonCImpl.vectorDataStore(), singletonCImpl.webRtcCallManagerProvider.get(), mavericksViewModelCImpl.directRoomHelper(), singletonCImpl.spaceStateHandlerImplProvider.get(), new CompileTimeAutoAcceptInvites(), singletonCImpl.debugVectorOverrides());
            }
          };

          case 95: // im.vector.app.features.settings.devices.DeviceVerificationInfoBottomSheetViewModel.Factory 
          return (T) new DeviceVerificationInfoBottomSheetViewModel.Factory() {
            @Override
            public DeviceVerificationInfoBottomSheetViewModel create(
                DeviceVerificationInfoBottomSheetViewState initialState93) {
              return new DeviceVerificationInfoBottomSheetViewModel(initialState93, singletonCImpl.session());
            }
          };

          case 96: // im.vector.app.features.roommemberprofile.devices.DeviceListBottomSheetViewModel.Factory 
          return (T) new DeviceListBottomSheetViewModel.Factory() {
            @Override
            public DeviceListBottomSheetViewModel create(DeviceListViewState initialState94) {
              return new DeviceListBottomSheetViewModel(initialState94, singletonCImpl.session());
            }
          };

          case 97: // im.vector.app.features.home.HomeActivityViewModel.Factory 
          return (T) new HomeActivityViewModel.Factory() {
            @Override
            public HomeActivityViewModel create(HomeActivityViewState initialState95) {
              return new HomeActivityViewModel(initialState95, singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.rawService(), singletonCImpl.reAuthHelperProvider.get(), singletonCImpl.analyticsStore(), singletonCImpl.lightweightSettingsStorage(), singletonCImpl.vectorPreferences(), singletonCImpl.defaultVectorAnalyticsProvider.get(), ConfigurationModule_ProvidesAnalyticsConfigFactory.providesAnalyticsConfig(), mavericksViewModelCImpl.releaseNotesPreferencesStore(), mavericksViewModelCImpl.stopOngoingVoiceBroadcastUseCase(), singletonCImpl.pushersManager(), mavericksViewModelCImpl.registerUnifiedPushUseCase(), singletonCImpl.unregisterUnifiedPushUseCase(), mavericksViewModelCImpl.ensureFcmTokenIsRetrievedUseCase(), mavericksViewModelCImpl.ensureSessionSyncingUseCase());
            }
          };

          case 98: // im.vector.app.features.crypto.recover.BootstrapSharedViewModel.Factory 
          return (T) new BootstrapSharedViewModel.Factory() {
            @Override
            public BootstrapSharedViewModel create(BootstrapViewState initialState96) {
              return new BootstrapSharedViewModel(initialState96, singletonCImpl.stringProvider(), singletonCImpl.defaultErrorFormatter(), singletonCImpl.session(), singletonCImpl.rawService(), mavericksViewModelCImpl.bootstrapCrossSigningTask(), mavericksViewModelCImpl.backupToQuadSMigrationTask(), mavericksViewModelCImpl.pendingAuthHandler());
            }
          };

          case 99: // im.vector.app.features.crypto.verification.VerificationBottomSheetViewModel.Factory 
          return (T) new VerificationBottomSheetViewModel.Factory() {
            @Override
            public VerificationBottomSheetViewModel create(
                VerificationBottomSheetViewState initialState97) {
              return new VerificationBottomSheetViewModel(initialState97, singletonCImpl.rawService(), singletonCImpl.session(), singletonCImpl.supportedVerificationMethodsProvider(), singletonCImpl.stringProvider(), singletonCImpl.providesMatrixProvider.get());
            }
          };

          default: throw new AssertionError(id);
        }
      }

      @SuppressWarnings("unchecked")
      private T get1() {
        switch (id) {
          case 100: // im.vector.app.features.poll.create.CreatePollViewModel.Factory 
          return (T) new CreatePollViewModel.Factory() {
            @Override
            public CreatePollViewModel create(CreatePollViewState initialState98) {
              return new CreatePollViewModel(initialState98, singletonCImpl.session());
            }
          };

          case 101: // im.vector.app.features.location.LocationSharingViewModel.Factory 
          return (T) new LocationSharingViewModel.Factory() {
            @Override
            public LocationSharingViewModel create(LocationSharingViewState initialState99) {
              return new LocationSharingViewModel(initialState99, singletonCImpl.locationTrackerProvider.get(), singletonCImpl.locationPinProvider.get(), singletonCImpl.session(), mavericksViewModelCImpl.compareLocationsUseCase(), singletonCImpl.vectorPreferences());
            }
          };

          case 102: // im.vector.app.features.location.preview.LocationPreviewViewModel.Factory 
          return (T) new LocationPreviewViewModel.Factory() {
            @Override
            public LocationPreviewViewModel create(LocationPreviewViewState initialState100) {
              return new LocationPreviewViewModel(initialState100);
            }
          };

          case 103: // im.vector.app.features.media.VectorAttachmentViewerViewModel.Factory 
          return (T) new VectorAttachmentViewerViewModel.Factory() {
            @Override
            public VectorAttachmentViewerViewModel create(VectorDummyViewState initialState101) {
              return new VectorAttachmentViewerViewModel(initialState101, singletonCImpl.session(), mavericksViewModelCImpl.downloadMediaUseCase());
            }
          };

          case 104: // im.vector.app.features.location.live.map.LiveLocationMapViewModel.Factory 
          return (T) new LiveLocationMapViewModel.Factory() {
            @Override
            public LiveLocationMapViewModel create(LiveLocationMapViewState initialState102) {
              return new LiveLocationMapViewModel(initialState102, mavericksViewModelCImpl.getListOfUserLiveLocationUseCase(), singletonCImpl.locationSharingServiceConnectionProvider.get(), mavericksViewModelCImpl.stopLiveLocationShareUseCase());
            }
          };

          case 105: // im.vector.app.features.settings.font.FontScaleSettingViewModel.Factory 
          return (T) new FontScaleSettingViewModel.Factory() {
            @Override
            public FontScaleSettingViewModel create(FontScaleSettingViewState initialState103) {
              return new FontScaleSettingViewModel(initialState103, singletonCImpl.vectorConfiguration(), singletonCImpl.fontScalePreferencesImpl());
            }
          };

          case 106: // im.vector.app.features.home.room.list.home.HomeRoomListViewModel.Factory 
          return (T) new HomeRoomListViewModel.Factory() {
            @Override
            public HomeRoomListViewModel create(HomeRoomListViewState initialState104) {
              return new HomeRoomListViewModel(initialState104, singletonCImpl.session(), singletonCImpl.spaceStateHandlerImplProvider.get(), mavericksViewModelCImpl.homeLayoutPreferencesStore(), singletonCImpl.stringProvider(), singletonCImpl.drawableProvider(), singletonCImpl.defaultVectorAnalyticsProvider.get());
            }
          };

          case 107: // im.vector.app.features.home.room.list.home.invites.InvitesViewModel.Factory 
          return (T) new InvitesViewModel.Factory() {
            @Override
            public InvitesViewModel create(InvitesViewState initialState105) {
              return new InvitesViewModel(initialState105, singletonCImpl.session(), singletonCImpl.stringProvider(), singletonCImpl.drawableProvider());
            }
          };

          case 108: // im.vector.app.features.home.room.list.home.release.ReleaseNotesViewModel.Factory 
          return (T) new ReleaseNotesViewModel.Factory() {
            @Override
            public ReleaseNotesViewModel create(VectorDummyViewState initialState106) {
              return new ReleaseNotesViewModel(initialState106);
            }
          };

          case 109: // im.vector.app.features.settings.devices.v2.overview.SessionOverviewViewModel.Factory 
          return (T) new SessionOverviewViewModel.Factory() {
            @Override
            public SessionOverviewViewModel create(SessionOverviewViewState initialState107) {
              return new SessionOverviewViewModel(initialState107, mavericksViewModelCImpl.getDeviceFullInfoUseCase(), mavericksViewModelCImpl.checkIfCurrentSessionCanBeVerifiedUseCase(), mavericksViewModelCImpl.signoutSessionsUseCase(), mavericksViewModelCImpl.pendingAuthHandler(), singletonCImpl.activeSessionHolderProvider.get(), mavericksViewModelCImpl.toggleNotificationsUseCase(), singletonCImpl.getNotificationsStatusUseCase(), mavericksViewModelCImpl.refreshDevicesUseCase(), singletonCImpl.vectorPreferences(), mavericksViewModelCImpl.toggleIpAddressVisibilityUseCase());
            }
          };

          case 110: // im.vector.app.features.settings.devices.v2.othersessions.OtherSessionsViewModel.Factory 
          return (T) new OtherSessionsViewModel.Factory() {
            @Override
            public OtherSessionsViewModel create(OtherSessionsViewState initialState108) {
              return new OtherSessionsViewModel(initialState108, singletonCImpl.activeSessionHolderProvider.get(), mavericksViewModelCImpl.getDeviceFullInfoListUseCase(), mavericksViewModelCImpl.signoutSessionsUseCase(), mavericksViewModelCImpl.pendingAuthHandler(), mavericksViewModelCImpl.refreshDevicesUseCase(), singletonCImpl.vectorPreferences(), mavericksViewModelCImpl.toggleIpAddressVisibilityUseCase());
            }
          };

          case 111: // im.vector.app.features.settings.devices.v2.details.SessionDetailsViewModel.Factory 
          return (T) new SessionDetailsViewModel.Factory() {
            @Override
            public SessionDetailsViewModel create(SessionDetailsViewState initialState109) {
              return new SessionDetailsViewModel(initialState109, mavericksViewModelCImpl.getDeviceFullInfoUseCase(), mavericksViewModelCImpl.copyToClipboardUseCase());
            }
          };

          case 112: // im.vector.app.features.settings.devices.v2.rename.RenameSessionViewModel.Factory 
          return (T) new RenameSessionViewModel.Factory() {
            @Override
            public RenameSessionViewModel create(RenameSessionViewState initialState110) {
              return new RenameSessionViewModel(initialState110, mavericksViewModelCImpl.getDeviceFullInfoUseCase(), mavericksViewModelCImpl.renameSessionUseCase());
            }
          };

          case 113: // im.vector.app.features.login.qr.QrCodeLoginViewModel.Factory 
          return (T) new QrCodeLoginViewModel.Factory() {
            @Override
            public QrCodeLoginViewModel create(QrCodeLoginViewState initialState111) {
              return new QrCodeLoginViewModel(initialState111, singletonCImpl.authenticationService(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.configureAndStartSessionUseCase());
            }
          };

          case 114: // im.vector.app.features.settings.devices.v2.more.SessionLearnMoreViewModel.Factory 
          return (T) new SessionLearnMoreViewModel.Factory() {
            @Override
            public SessionLearnMoreViewModel create(SessionLearnMoreViewState initialState112) {
              return new SessionLearnMoreViewModel(initialState112);
            }
          };

          case 115: // im.vector.app.features.settings.labs.VectorSettingsLabsViewModel.Factory 
          return (T) new VectorSettingsLabsViewModel.Factory() {
            @Override
            public VectorSettingsLabsViewModel create(VectorSettingsLabsViewState initialState113) {
              return new VectorSettingsLabsViewModel(initialState113, singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.updateMatrixClientInfoUseCase(), mavericksViewModelCImpl.deleteMatrixClientInfoUseCase());
            }
          };

          case 116: // im.vector.app.features.attachments.AttachmentTypeSelectorViewModel.Factory 
          return (T) new AttachmentTypeSelectorViewModel.Factory() {
            @Override
            public AttachmentTypeSelectorViewModel create(
                AttachmentTypeSelectorViewState initialState114) {
              return new AttachmentTypeSelectorViewModel(initialState114, singletonCImpl.debugVectorFeatures(), singletonCImpl.vectorPreferences());
            }
          };

          case 117: // im.vector.app.features.settings.notifications.VectorSettingsNotificationPreferenceViewModel.Factory 
          return (T) new VectorSettingsNotificationPreferenceViewModel.Factory() {
            @Override
            public VectorSettingsNotificationPreferenceViewModel create(
                VectorDummyViewState initialState115) {
              return new VectorSettingsNotificationPreferenceViewModel(initialState115, singletonCImpl.pushersManager(), singletonCImpl.vectorPreferences(), mavericksViewModelCImpl.enableNotificationsForCurrentSessionUseCase(), mavericksViewModelCImpl.disableNotificationsForCurrentSessionUseCase(), singletonCImpl.unregisterUnifiedPushUseCase(), mavericksViewModelCImpl.registerUnifiedPushUseCase(), mavericksViewModelCImpl.ensureFcmTokenIsRetrievedUseCase(), mavericksViewModelCImpl.toggleNotificationsForCurrentSessionUseCase());
            }
          };

          case 118: // im.vector.app.features.home.room.detail.composer.link.SetLinkViewModel.Factory 
          return (T) new SetLinkViewModel.Factory() {
            @Override
            public SetLinkViewModel create(SetLinkViewState initialState116) {
              return new SetLinkViewModel(initialState116);
            }
          };

          case 119: // im.vector.app.features.roomprofile.polls.RoomPollsViewModel.Factory 
          return (T) new RoomPollsViewModel.Factory() {
            @Override
            public RoomPollsViewModel create(RoomPollsViewState initialState117) {
              return new RoomPollsViewModel(initialState117, mavericksViewModelCImpl.getPollsUseCase(), mavericksViewModelCImpl.loadMorePollsUseCase(), mavericksViewModelCImpl.syncPollsUseCase(), mavericksViewModelCImpl.disposePollHistoryUseCase(), mavericksViewModelCImpl.pollSummaryMapper());
            }
          };

          default: throw new AssertionError(id);
        }
      }

      @Override
      public T get() {
        switch (id / 100) {
          case 0: return get0();
          case 1: return get1();
          default: throw new AssertionError(id);
        }
      }
    }
  }

  private static final class SingletonCImpl extends VectorApplication_HiltComponents.SingletonC {
    private final ApplicationContextModule applicationContextModule;

    private final LoginModule loginModule;

    private final SingletonCImpl singletonCImpl = this;

    private Provider<VectorFlipperProxy> vectorFlipperProxyProvider;

    private Provider<VectorPlugins> vectorPluginsProvider;

    private Provider<Matrix> providesMatrixProvider;

    private Provider<SharedPreferences> providesDefaultSharedPreferencesProvider;

    private Provider<EmojiCompatFontProvider> emojiCompatFontProvider;

    private Provider<EmojiCompatWrapper> emojiCompatWrapperProvider;

    private Provider<ActiveSessionDataSource> activeSessionDataSourceProvider;

    private Provider<PopupAlertManager> popupAlertManagerProvider;

    private Provider<KeyRequestHandler> keyRequestHandlerProvider;

    private Provider<ActiveSessionHolder> activeSessionHolderProvider;

    private Provider<MatrixItemColorProvider> matrixItemColorProvider;

    private Provider<AvatarRenderer> avatarRendererProvider;

    private Provider<IncomingVerificationRequestHandler> incomingVerificationRequestHandlerProvider;

    private Provider<BuildMeta> providesBuildMetaProvider;

    private Provider<DefaultVectorAnalytics> defaultVectorAnalyticsProvider;

    private Provider<WebRtcCallManager> webRtcCallManagerProvider;

    private Provider<EventHtmlRenderer> eventHtmlRendererProvider;

    private Provider<NotificationUtils> notificationUtilsProvider;

    private Provider<NotificationBitmapLoader> notificationBitmapLoaderProvider;

    private Provider<NotificationDrawerManager> notificationDrawerManagerProvider;

    private Provider<PushRuleTriggerListener> pushRuleTriggerListenerProvider;

    private Provider<SessionListener> sessionListenerProvider;

    private Provider<NotificationsSettingUpdater> notificationsSettingUpdaterProvider;

    private Provider<VectorFileLogger> vectorFileLoggerProvider;

    private Provider<BugReporter> bugReporterProvider;

    private Provider<VectorUncaughtExceptionHandler> vectorUncaughtExceptionHandlerProvider;

    private Provider<SpaceStateHandlerImpl> spaceStateHandlerImplProvider;

    private Provider<SharedPrefPinCodeStore> sharedPrefPinCodeStoreProvider;

    private Provider<PinLocker> pinLockerProvider;

    private Provider<InvitesAcceptor> invitesAcceptorProvider;

    private Provider<AutoRageShaker> autoRageShakerProvider;

    private Provider<VectorLocale> vectorLocaleProvider;

    private Provider<DefaultNavigator> defaultNavigatorProvider;

    private Provider<CoroutineScope> providesApplicationCoroutineScopeProvider;

    private Provider<EmojiDataSource> emojiDataSourceProvider;

    private Provider<KeyStoreCrypto.Factory> factoryProvider;

    private Provider<LockScreenKeyRepository> lockScreenKeyRepositoryProvider;

    private Provider<AudioMessagePlaybackTracker> audioMessagePlaybackTrackerProvider;

    private Provider<RoomDetailPendingActionStore> roomDetailPendingActionStoreProvider;

    private Provider<VectorHtmlCompressor> vectorHtmlCompressorProvider;

    private Provider<LocationPinProvider> locationPinProvider;

    private Provider<VoiceBroadcastRecorder> providesVoiceBroadcastRecorderProvider;

    private Provider<VoiceBroadcastPlayerImpl> voiceBroadcastPlayerImplProvider;

    private Provider<DecryptionFailureTracker> decryptionFailureTrackerProvider;

    private Provider<LiveLocationNotificationBuilder> liveLocationNotificationBuilderProvider;

    private Provider<LocationTracker> locationTrackerProvider;

    private Provider<ReAuthHelper> reAuthHelperProvider;

    private Provider<OkHttpClient> okhttpClientProvider;

    private Provider<ApolloClient> provideApolloProvider;

    private Provider<ApolloLoginClient> provideRemoteLoginDataSourceProvider;

    private Provider<LoginRepository> provideLoginRepositoryProvider;

    private Provider<JitsiActiveConferenceHolder> jitsiActiveConferenceHolderProvider;

    private Provider<LocationSharingServiceConnection> locationSharingServiceConnectionProvider;

    private SingletonCImpl(ApplicationContextModule applicationContextModuleParam,
        LoginModule loginModuleParam) {
      this.applicationContextModule = applicationContextModuleParam;
      this.loginModule = loginModuleParam;
      initialize(applicationContextModuleParam, loginModuleParam);

    }

    private Context context() {
      return VectorStaticModule_ProvidesContextFactory.providesContext(ApplicationContextModule_ProvideApplicationFactory.provideApplication(applicationContextModule));
    }

    private VectorRoomDisplayNameFallbackProvider vectorRoomDisplayNameFallbackProvider() {
      return new VectorRoomDisplayNameFallbackProvider(context());
    }

    private MatrixConfiguration matrixConfiguration() {
      return VectorStaticModule_ProvidesMatrixConfigurationFactory.providesMatrixConfiguration(vectorPreferences(), vectorRoomDisplayNameFallbackProvider(), vectorFlipperProxyProvider.get(), vectorPluginsProvider.get(), new VectorCustomEventTypesProvider());
    }

    private LegacySessionImporter legacySessionImporter() {
      return VectorStaticModule_ProvidesLegacySessionImporterFactory.providesLegacySessionImporter(providesMatrixProvider.get());
    }

    private AuthenticationService authenticationService() {
      return VectorStaticModule_ProvidesAuthenticationServiceFactory.providesAuthenticationService(providesMatrixProvider.get());
    }

    private AndroidSystemSettingsProvider androidSystemSettingsProvider() {
      return new AndroidSystemSettingsProvider(context());
    }

    private FontScalePreferencesImpl fontScalePreferencesImpl() {
      return new FontScalePreferencesImpl(providesDefaultSharedPreferencesProvider.get(), androidSystemSettingsProvider());
    }

    private VectorLocaleProvider vectorLocaleProvider() {
      return new VectorLocaleProvider(providesDefaultSharedPreferencesProvider.get());
    }

    private VectorConfiguration vectorConfiguration() {
      return new VectorConfiguration(context(), fontScalePreferencesImpl(), vectorLocaleProvider());
    }

    private Resources resources() {
      return VectorStaticModule_ProvidesResourcesFactory.providesResources(context());
    }

    private DefaultLocaleProvider defaultLocaleProvider() {
      return new DefaultLocaleProvider(resources());
    }

    private DefaultDateFormatterProvider defaultDateFormatterProvider() {
      return new DefaultDateFormatterProvider(context(), defaultLocaleProvider());
    }

    private AbbrevDateFormatterProvider abbrevDateFormatterProvider() {
      return new AbbrevDateFormatterProvider(defaultLocaleProvider());
    }

    private DateFormatterProviders dateFormatterProviders() {
      return new DateFormatterProviders(defaultDateFormatterProvider(), abbrevDateFormatterProvider());
    }

    private VectorDateFormatter vectorDateFormatter() {
      return new VectorDateFormatter(context(), defaultLocaleProvider(), dateFormatterProviders(), new DefaultClock());
    }

    private ColorProvider colorProvider() {
      return new ColorProvider(context());
    }

    private DimensionConverter dimensionConverter() {
      return new DimensionConverter(resources());
    }

    private PostHogFactory postHogFactory() {
      return new PostHogFactory(context(), ConfigurationModule_ProvidesAnalyticsConfigFactory.providesAnalyticsConfig(), providesBuildMetaProvider.get());
    }

    private SentryAnalytics sentryAnalytics() {
      return new SentryAnalytics(context(), ConfigurationModule_ProvidesAnalyticsConfigFactory.providesAnalyticsConfig());
    }

    private AnalyticsStore analyticsStore() {
      return new AnalyticsStore(context());
    }

    private LateInitUserPropertiesFactory lateInitUserPropertiesFactory() {
      return new LateInitUserPropertiesFactory(activeSessionDataSourceProvider.get(), context());
    }

    private GoogleFcmHelper googleFcmHelper() {
      return new GoogleFcmHelper(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule), providesDefaultSharedPreferencesProvider.get());
    }

    private UnifiedPushStore unifiedPushStore() {
      return new UnifiedPushStore(context(), googleFcmHelper(), providesDefaultSharedPreferencesProvider.get());
    }

    private StringProvider stringProvider() {
      return new StringProvider(resources());
    }

    private UnifiedPushHelper unifiedPushHelper() {
      return new UnifiedPushHelper(context(), unifiedPushStore(), stringProvider(), providesMatrixProvider.get(), googleFcmHelper());
    }

    private RoomHistoryVisibilityFormatter roomHistoryVisibilityFormatter() {
      return new RoomHistoryVisibilityFormatter(stringProvider());
    }

    private RoleFormatter roleFormatter() {
      return new RoleFormatter(stringProvider());
    }

    private NoticeEventFormatter noticeEventFormatter() {
      return new NoticeEventFormatter(activeSessionDataSourceProvider.get(), roomHistoryVisibilityFormatter(), roleFormatter(), vectorPreferences(), stringProvider());
    }

    private DrawableProvider drawableProvider() {
      return new DrawableProvider(context());
    }

    private MatrixHtmlPluginConfigure matrixHtmlPluginConfigure() {
      return new MatrixHtmlPluginConfigure(colorProvider(), resources(), vectorPreferences());
    }

    private DisplayableEventFormatter displayableEventFormatter() {
      return new DisplayableEventFormatter(stringProvider(), colorProvider(), drawableProvider(), emojiCompatWrapperProvider.get(), noticeEventFormatter(), DoubleCheck.lazy(eventHtmlRendererProvider));
    }

    private NotifiableEventResolver notifiableEventResolver() {
      return new NotifiableEventResolver(stringProvider(), noticeEventFormatter(), displayableEventFormatter(), new DefaultClock(), providesBuildMetaProvider.get());
    }

    private NotificationDisplayer notificationDisplayer() {
      return new NotificationDisplayer(context());
    }

    private OutdatedEventDetector outdatedEventDetector() {
      return new OutdatedEventDetector(activeSessionDataSourceProvider.get());
    }

    private NotifiableEventProcessor notifiableEventProcessor() {
      return new NotifiableEventProcessor(outdatedEventDetector(), new CompileTimeAutoAcceptInvites());
    }

    private NotificationActionIds notificationActionIds() {
      return new NotificationActionIds(providesBuildMetaProvider.get());
    }

    private RoomGroupMessageCreator roomGroupMessageCreator() {
      return new RoomGroupMessageCreator(notificationBitmapLoaderProvider.get(), stringProvider(), notificationUtilsProvider.get());
    }

    private SummaryGroupMessageCreator summaryGroupMessageCreator() {
      return new SummaryGroupMessageCreator(stringProvider(), notificationUtilsProvider.get());
    }

    private NotificationFactory notificationFactory() {
      return new NotificationFactory(notificationUtilsProvider.get(), roomGroupMessageCreator(), summaryGroupMessageCreator());
    }

    private NotificationRenderer notificationRenderer() {
      return new NotificationRenderer(notificationDisplayer(), notificationFactory(), context());
    }

    private NotificationEventPersistence notificationEventPersistence() {
      return new NotificationEventPersistence(context(), providesMatrixProvider.get());
    }

    private FilteredEventDetector filteredEventDetector() {
      return new FilteredEventDetector(activeSessionDataSourceProvider.get());
    }

    private ImageManager imageManager() {
      return new ImageManager(context(), activeSessionDataSourceProvider.get());
    }

    private SessionInitializer sessionInitializer() {
      return new SessionInitializer(authenticationService());
    }

    private DefaultAppNameProvider defaultAppNameProvider() {
      return new DefaultAppNameProvider(context());
    }

    private UpdateMatrixClientInfoUseCase updateMatrixClientInfoUseCase() {
      return new UpdateMatrixClientInfoUseCase(defaultAppNameProvider(), providesBuildMetaProvider.get(), new GetMatrixClientInfoUseCase(), new SetMatrixClientInfoUseCase());
    }

    private CanToggleNotificationsViaAccountDataUseCase canToggleNotificationsViaAccountDataUseCase(
        ) {
      return new CanToggleNotificationsViaAccountDataUseCase(new GetNotificationSettingsAccountDataUpdatesUseCase());
    }

    private GetNotificationsStatusUseCase getNotificationsStatusUseCase() {
      return new GetNotificationsStatusUseCase(new CanToggleNotificationsViaPusherUseCase(), canToggleNotificationsViaAccountDataUseCase());
    }

    private UpdateEnableNotificationsSettingOnChangeUseCase updateEnableNotificationsSettingOnChangeUseCase(
        ) {
      return new UpdateEnableNotificationsSettingOnChangeUseCase(vectorPreferences(), getNotificationsStatusUseCase());
    }

    private DeleteNotificationSettingsAccountDataUseCase deleteNotificationSettingsAccountDataUseCase(
        ) {
      return new DeleteNotificationSettingsAccountDataUseCase(new GetNotificationSettingsAccountDataUseCase(), new SetNotificationSettingsAccountDataUseCase());
    }

    private UpdateNotificationSettingsAccountDataUseCase updateNotificationSettingsAccountDataUseCase(
        ) {
      return new UpdateNotificationSettingsAccountDataUseCase(vectorPreferences(), unifiedPushHelper(), new GetNotificationSettingsAccountDataUseCase(), new SetNotificationSettingsAccountDataUseCase(), deleteNotificationSettingsAccountDataUseCase());
    }

    private ConfigureAndStartSessionUseCase configureAndStartSessionUseCase() {
      return new ConfigureAndStartSessionUseCase(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule), webRtcCallManagerProvider.get(), updateMatrixClientInfoUseCase(), vectorPreferences(), notificationsSettingUpdaterProvider.get(), updateNotificationSettingsAccountDataUseCase());
    }

    private UnregisterUnifiedPushUseCase unregisterUnifiedPushUseCase() {
      return new UnregisterUnifiedPushUseCase(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule), vectorPreferences(), unifiedPushStore(), unifiedPushHelper());
    }

    private VersionCodeProvider versionCodeProvider() {
      return new VersionCodeProvider(context());
    }

    private VersionProvider versionProvider() {
      return new VersionProvider(versionCodeProvider(), providesBuildMetaProvider.get());
    }

    private SystemLocaleProvider systemLocaleProvider() {
      return new SystemLocaleProvider(context());
    }

    private SharedPreferences sharedPreferences() {
      return VectorStaticModule_ProvidesSharedPreferencesFactory.providesSharedPreferences(context());
    }

    private SharedPreferencesUiStateRepository sharedPreferencesUiStateRepository() {
      return new SharedPreferencesUiStateRepository(sharedPreferences(), vectorPreferences());
    }

    private DisclaimerDialog disclaimerDialog() {
      return new DisclaimerDialog(providesDefaultSharedPreferencesProvider.get());
    }

    private ThemeProvider themeProvider() {
      return new ThemeProvider(context());
    }

    private WidgetArgsBuilder widgetArgsBuilder() {
      return new WidgetArgsBuilder(activeSessionHolderProvider.get(), themeProvider());
    }

    private HardwareInfo hardwareInfo() {
      return new HardwareInfo(context());
    }

    private SupportedVerificationMethodsProvider supportedVerificationMethodsProvider() {
      return new SupportedVerificationMethodsProvider(hardwareInfo());
    }

    private DebugVectorFeatures debugVectorFeatures() {
      return FeaturesModule_Companion_ProvidesDebugVectorFeaturesFactory.providesDebugVectorFeatures(context(), FeaturesModule_Companion_ProvidesDefaultVectorFeaturesFactory.providesDefaultVectorFeatures());
    }

    private DefaultErrorFormatter defaultErrorFormatter() {
      return new DefaultErrorFormatter(stringProvider());
    }

    private DefaultGetDeviceInfoUseCase defaultGetDeviceInfoUseCase() {
      return new DefaultGetDeviceInfoUseCase(activeSessionHolderProvider.get());
    }

    private PushersManager pushersManager() {
      return new PushersManager(unifiedPushHelper(), activeSessionHolderProvider.get(), defaultLocaleProvider(), stringProvider(), defaultAppNameProvider(), defaultGetDeviceInfoUseCase());
    }

    private VectorDataStore vectorDataStore() {
      return new VectorDataStore(context());
    }

    private WifiDetector wifiDetector() {
      return new WifiDetector(context());
    }

    private VectorPushHandler vectorPushHandler() {
      return new VectorPushHandler(notificationDrawerManagerProvider.get(), notifiableEventResolver(), activeSessionHolderProvider.get(), vectorPreferences(), vectorDataStore(), wifiDetector(), notificationActionIds(), context(), providesBuildMetaProvider.get());
    }

    private Session session() {
      return VectorStaticModule_ProvidesCurrentSessionFactory.providesCurrentSession(activeSessionHolderProvider.get());
    }

    private VectorDebugReceiver vectorDebugReceiver() {
      return new VectorDebugReceiver(providesDefaultSharedPreferencesProvider.get());
    }

    private RawService rawService() {
      return VectorStaticModule_ProvidesRawServiceFactory.providesRawService(providesMatrixProvider.get());
    }

    private FirebaseNightlyProxy firebaseNightlyProxy() {
      return new FirebaseNightlyProxy(new DefaultClock(), providesDefaultSharedPreferencesProvider.get(), providesBuildMetaProvider.get());
    }

    private LightweightSettingsStorage lightweightSettingsStorage() {
      return VectorStaticModule_ProvidesLightweightSettingsStorageFactory.providesLightweightSettingsStorage(providesMatrixProvider.get());
    }

    private GetVoiceBroadcastStateEventUseCase getVoiceBroadcastStateEventUseCase() {
      return new GetVoiceBroadcastStateEventUseCase(session());
    }

    private GetVoiceBroadcastStateEventLiveUseCase getVoiceBroadcastStateEventLiveUseCase() {
      return new GetVoiceBroadcastStateEventLiveUseCase(session(), getVoiceBroadcastStateEventUseCase());
    }

    private GetLiveVoiceBroadcastChunksUseCase getLiveVoiceBroadcastChunksUseCase() {
      return new GetLiveVoiceBroadcastChunksUseCase(activeSessionHolderProvider.get(), getVoiceBroadcastStateEventUseCase());
    }

    private BiometricManager biometricManager() {
      return LockScreenModule_ProvideBiometricManagerFactory.provideBiometricManager(ApplicationContextModule_ProvideContextFactory.provideContext(applicationContextModule));
    }

    private LegacyPinCodeMigrator legacyPinCodeMigrator() {
      return LockScreenModule_ProvideLegacyPinCodeMigratorFactory.provideLegacyPinCodeMigrator(LockScreenModule_ProvidePinCodeKeyAliasFactory.providePinCodeKeyAlias(), context(), sharedPrefPinCodeStoreProvider.get(), LockScreenModule_ProvideKeyStoreFactory.provideKeyStore(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider());
    }

    private KeyguardManager keyguardManager() {
      return LockScreenModule_ProvideKeyguardManagerFactory.provideKeyguardManager(context());
    }

    private DebugVectorOverrides debugVectorOverrides() {
      return FeaturesModule_Companion_ProvidesDebugVectorOverridesFactory.providesDebugVectorOverrides(context());
    }

    private HomeServerHistoryService homeServerHistoryService() {
      return VectorStaticModule_ProvidesHomeServerHistoryServiceFactory.providesHomeServerHistoryService(providesMatrixProvider.get());
    }

    @SuppressWarnings("unchecked")
    private void initialize(final ApplicationContextModule applicationContextModuleParam,
        final LoginModule loginModuleParam) {
      this.vectorFlipperProxyProvider = DoubleCheck.provider(new SwitchingProvider<VectorFlipperProxy>(singletonCImpl, 1));
      this.vectorPluginsProvider = DoubleCheck.provider(new SwitchingProvider<VectorPlugins>(singletonCImpl, 2));
      this.providesMatrixProvider = DoubleCheck.provider(new SwitchingProvider<Matrix>(singletonCImpl, 0));
      this.providesDefaultSharedPreferencesProvider = DoubleCheck.provider(new SwitchingProvider<SharedPreferences>(singletonCImpl, 3));
      this.emojiCompatFontProvider = DoubleCheck.provider(new SwitchingProvider<EmojiCompatFontProvider>(singletonCImpl, 4));
      this.emojiCompatWrapperProvider = DoubleCheck.provider(new SwitchingProvider<EmojiCompatWrapper>(singletonCImpl, 5));
      this.activeSessionDataSourceProvider = DoubleCheck.provider(new SwitchingProvider<ActiveSessionDataSource>(singletonCImpl, 9));
      this.popupAlertManagerProvider = DoubleCheck.provider(new SwitchingProvider<PopupAlertManager>(singletonCImpl, 11));
      this.keyRequestHandlerProvider = DoubleCheck.provider(new SwitchingProvider<KeyRequestHandler>(singletonCImpl, 10));
      this.matrixItemColorProvider = DoubleCheck.provider(new SwitchingProvider<MatrixItemColorProvider>(singletonCImpl, 14));
      this.avatarRendererProvider = new SwitchingProvider<>(singletonCImpl, 13);
      this.incomingVerificationRequestHandlerProvider = DoubleCheck.provider(new SwitchingProvider<IncomingVerificationRequestHandler>(singletonCImpl, 12));
      this.providesBuildMetaProvider = DoubleCheck.provider(new SwitchingProvider<BuildMeta>(singletonCImpl, 17));
      this.defaultVectorAnalyticsProvider = DoubleCheck.provider(new SwitchingProvider<DefaultVectorAnalytics>(singletonCImpl, 16));
      this.webRtcCallManagerProvider = DoubleCheck.provider(new SwitchingProvider<WebRtcCallManager>(singletonCImpl, 15));
      this.eventHtmlRendererProvider = DoubleCheck.provider(new SwitchingProvider<EventHtmlRenderer>(singletonCImpl, 19));
      this.notificationUtilsProvider = DoubleCheck.provider(new SwitchingProvider<NotificationUtils>(singletonCImpl, 21));
      this.notificationBitmapLoaderProvider = DoubleCheck.provider(new SwitchingProvider<NotificationBitmapLoader>(singletonCImpl, 22));
      this.notificationDrawerManagerProvider = DoubleCheck.provider(new SwitchingProvider<NotificationDrawerManager>(singletonCImpl, 20));
      this.pushRuleTriggerListenerProvider = DoubleCheck.provider(new SwitchingProvider<PushRuleTriggerListener>(singletonCImpl, 18));
      this.sessionListenerProvider = DoubleCheck.provider(new SwitchingProvider<SessionListener>(singletonCImpl, 23));
      this.notificationsSettingUpdaterProvider = DoubleCheck.provider(new SwitchingProvider<NotificationsSettingUpdater>(singletonCImpl, 24));
      this.activeSessionHolderProvider = DoubleCheck.provider(new SwitchingProvider<ActiveSessionHolder>(singletonCImpl, 8));
      this.vectorFileLoggerProvider = DoubleCheck.provider(new SwitchingProvider<VectorFileLogger>(singletonCImpl, 25));
      this.bugReporterProvider = DoubleCheck.provider(new SwitchingProvider<BugReporter>(singletonCImpl, 7));
      this.vectorUncaughtExceptionHandlerProvider = DoubleCheck.provider(new SwitchingProvider<VectorUncaughtExceptionHandler>(singletonCImpl, 6));
      this.spaceStateHandlerImplProvider = DoubleCheck.provider(new SwitchingProvider<SpaceStateHandlerImpl>(singletonCImpl, 26));
      this.sharedPrefPinCodeStoreProvider = DoubleCheck.provider(new SwitchingProvider<SharedPrefPinCodeStore>(singletonCImpl, 28));
      this.pinLockerProvider = DoubleCheck.provider(new SwitchingProvider<PinLocker>(singletonCImpl, 27));
      this.invitesAcceptorProvider = DoubleCheck.provider(new SwitchingProvider<InvitesAcceptor>(singletonCImpl, 29));
      this.autoRageShakerProvider = DoubleCheck.provider(new SwitchingProvider<AutoRageShaker>(singletonCImpl, 30));
      this.vectorLocaleProvider = DoubleCheck.provider(new SwitchingProvider<VectorLocale>(singletonCImpl, 31));
      this.defaultNavigatorProvider = DoubleCheck.provider(new SwitchingProvider<DefaultNavigator>(singletonCImpl, 32));
      this.providesApplicationCoroutineScopeProvider = DoubleCheck.provider(new SwitchingProvider<CoroutineScope>(singletonCImpl, 33));
      this.emojiDataSourceProvider = DoubleCheck.provider(new SwitchingProvider<EmojiDataSource>(singletonCImpl, 34));
      this.factoryProvider = SingleCheck.provider(new SwitchingProvider<KeyStoreCrypto.Factory>(singletonCImpl, 36));
      this.lockScreenKeyRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<LockScreenKeyRepository>(singletonCImpl, 35));
      this.audioMessagePlaybackTrackerProvider = DoubleCheck.provider(new SwitchingProvider<AudioMessagePlaybackTracker>(singletonCImpl, 37));
      this.roomDetailPendingActionStoreProvider = DoubleCheck.provider(new SwitchingProvider<RoomDetailPendingActionStore>(singletonCImpl, 38));
      this.vectorHtmlCompressorProvider = DoubleCheck.provider(new SwitchingProvider<VectorHtmlCompressor>(singletonCImpl, 39));
      this.locationPinProvider = DoubleCheck.provider(new SwitchingProvider<LocationPinProvider>(singletonCImpl, 40));
      this.providesVoiceBroadcastRecorderProvider = DoubleCheck.provider(new SwitchingProvider<VoiceBroadcastRecorder>(singletonCImpl, 41));
      this.voiceBroadcastPlayerImplProvider = DoubleCheck.provider(new SwitchingProvider<VoiceBroadcastPlayerImpl>(singletonCImpl, 42));
      this.decryptionFailureTrackerProvider = DoubleCheck.provider(new SwitchingProvider<DecryptionFailureTracker>(singletonCImpl, 43));
      this.liveLocationNotificationBuilderProvider = DoubleCheck.provider(new SwitchingProvider<LiveLocationNotificationBuilder>(singletonCImpl, 44));
      this.locationTrackerProvider = DoubleCheck.provider(new SwitchingProvider<LocationTracker>(singletonCImpl, 45));
      this.reAuthHelperProvider = DoubleCheck.provider(new SwitchingProvider<ReAuthHelper>(singletonCImpl, 46));
      this.okhttpClientProvider = DoubleCheck.provider(new SwitchingProvider<OkHttpClient>(singletonCImpl, 50));
      this.provideApolloProvider = DoubleCheck.provider(new SwitchingProvider<ApolloClient>(singletonCImpl, 49));
      this.provideRemoteLoginDataSourceProvider = DoubleCheck.provider(new SwitchingProvider<ApolloLoginClient>(singletonCImpl, 48));
      this.provideLoginRepositoryProvider = DoubleCheck.provider(new SwitchingProvider<LoginRepository>(singletonCImpl, 47));
      this.jitsiActiveConferenceHolderProvider = DoubleCheck.provider(new SwitchingProvider<JitsiActiveConferenceHolder>(singletonCImpl, 51));
      this.locationSharingServiceConnectionProvider = DoubleCheck.provider(new SwitchingProvider<LocationSharingServiceConnection>(singletonCImpl, 52));
    }

    @Override
    public Set<Boolean> getDisableFragmentGetContextFix() {
      return ImmutableSet.<Boolean>of();
    }

    @Override
    public ActivityRetainedComponentBuilder retainedComponentBuilder() {
      return new ActivityRetainedCBuilder(singletonCImpl);
    }

    @Override
    public ServiceComponentBuilder serviceComponentBuilder() {
      return new ServiceCBuilder(singletonCImpl);
    }

    @Override
    public void injectVectorApplication(VectorApplication vectorApplication) {
      injectVectorApplication2(vectorApplication);
    }

    @Override
    public MavericksViewModelComponentBuilder mavericksViewModelComponentBuilder() {
      return new MavericksViewModelCBuilder(singletonCImpl);
    }

    @Override
    public SessionListener sessionListener() {
      return sessionListenerProvider.get();
    }

    @Override
    public AvatarRenderer avatarRenderer() {
      return avatarRendererProvider.get();
    }

    @Override
    public ActiveSessionHolder activeSessionHolder() {
      return activeSessionHolderProvider.get();
    }

    @Override
    public UnrecognizedCertificateDialog unrecognizedCertificateDialog() {
      return new UnrecognizedCertificateDialog(activeSessionHolderProvider.get(), stringProvider());
    }

    @Override
    public Navigator navigator() {
      return defaultNavigatorProvider.get();
    }

    @Override
    public Clock clock() {
      return new DefaultClock();
    }

    @Override
    public ErrorFormatter errorFormatter() {
      return defaultErrorFormatter();
    }

    @Override
    public BugReporter bugReporter() {
      return bugReporterProvider.get();
    }

    @Override
    public VectorPreferences vectorPreferences() {
      return new VectorPreferences(context(), new DefaultClock(), providesBuildMetaProvider.get(), debugVectorFeatures(), providesDefaultSharedPreferencesProvider.get(), stringProvider());
    }

    @Override
    public UiStateRepository uiStateRepository() {
      return sharedPreferencesUiStateRepository();
    }

    @Override
    public PinLocker pinLocker() {
      return pinLockerProvider.get();
    }

    @Override
    public AnalyticsTracker analyticsTracker() {
      return defaultVectorAnalyticsProvider.get();
    }

    @Override
    public WebRtcCallManager webRtcCallManager() {
      return webRtcCallManagerProvider.get();
    }

    @Override
    public CoroutineScope appCoroutineScope() {
      return providesApplicationCoroutineScopeProvider.get();
    }

    @Override
    public void injectVectorUnifiedPushMessagingReceiver(VectorUnifiedPushMessagingReceiver arg0) {
      injectVectorUnifiedPushMessagingReceiver2(arg0);
    }

    @Override
    public void injectNotificationBroadcastReceiver(NotificationBroadcastReceiver arg0) {
      injectNotificationBroadcastReceiver2(arg0);
    }

    @CanIgnoreReturnValue
    private VectorApplication injectVectorApplication2(VectorApplication instance) {
      VectorApplication_MembersInjector.injectLegacySessionImporter(instance, legacySessionImporter());
      VectorApplication_MembersInjector.injectAuthenticationService(instance, authenticationService());
      VectorApplication_MembersInjector.injectVectorConfiguration(instance, vectorConfiguration());
      VectorApplication_MembersInjector.injectEmojiCompatFontProvider(instance, emojiCompatFontProvider.get());
      VectorApplication_MembersInjector.injectEmojiCompatWrapper(instance, emojiCompatWrapperProvider.get());
      VectorApplication_MembersInjector.injectVectorUncaughtExceptionHandler(instance, vectorUncaughtExceptionHandlerProvider.get());
      VectorApplication_MembersInjector.injectActiveSessionHolder(instance, activeSessionHolderProvider.get());
      VectorApplication_MembersInjector.injectNotificationDrawerManager(instance, notificationDrawerManagerProvider.get());
      VectorApplication_MembersInjector.injectVectorPreferences(instance, vectorPreferences());
      VectorApplication_MembersInjector.injectVersionProvider(instance, versionProvider());
      VectorApplication_MembersInjector.injectNotificationUtils(instance, notificationUtilsProvider.get());
      VectorApplication_MembersInjector.injectSpaceStateHandler(instance, spaceStateHandlerImplProvider.get());
      VectorApplication_MembersInjector.injectPopupAlertManager(instance, popupAlertManagerProvider.get());
      VectorApplication_MembersInjector.injectPinLocker(instance, pinLockerProvider.get());
      VectorApplication_MembersInjector.injectCallManager(instance, webRtcCallManagerProvider.get());
      VectorApplication_MembersInjector.injectInvitesAcceptor(instance, invitesAcceptorProvider.get());
      VectorApplication_MembersInjector.injectAutoRageShaker(instance, autoRageShakerProvider.get());
      VectorApplication_MembersInjector.injectVectorFileLogger(instance, vectorFileLoggerProvider.get());
      VectorApplication_MembersInjector.injectVectorAnalytics(instance, defaultVectorAnalyticsProvider.get());
      VectorApplication_MembersInjector.injectFlipperProxy(instance, vectorFlipperProxyProvider.get());
      VectorApplication_MembersInjector.injectMatrix(instance, providesMatrixProvider.get());
      VectorApplication_MembersInjector.injectFcmHelper(instance, googleFcmHelper());
      VectorApplication_MembersInjector.injectBuildMeta(instance, providesBuildMetaProvider.get());
      VectorApplication_MembersInjector.injectLeakDetector(instance, new LeakCanaryLeakDetector());
      VectorApplication_MembersInjector.injectVectorLocale(instance, vectorLocaleProvider.get());
      VectorApplication_MembersInjector.injectDisclaimerDialog(instance, disclaimerDialog());
      return instance;
    }

    @CanIgnoreReturnValue
    private VectorUnifiedPushMessagingReceiver injectVectorUnifiedPushMessagingReceiver2(
        VectorUnifiedPushMessagingReceiver instance) {
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectPushersManager(instance, pushersManager());
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectPushParser(instance, new PushParser());
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectActiveSessionHolder(instance, activeSessionHolderProvider.get());
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectVectorPreferences(instance, vectorPreferences());
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectVectorPushHandler(instance, vectorPushHandler());
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectGuardServiceStarter(instance, FlavorModule_Companion_ProvideGuardServiceStarterFactory.provideGuardServiceStarter());
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectUnifiedPushStore(instance, unifiedPushStore());
      VectorUnifiedPushMessagingReceiver_MembersInjector.injectUnifiedPushHelper(instance, unifiedPushHelper());
      return instance;
    }

    @CanIgnoreReturnValue
    private NotificationBroadcastReceiver injectNotificationBroadcastReceiver2(
        NotificationBroadcastReceiver instance) {
      NotificationBroadcastReceiver_MembersInjector.injectNotificationDrawerManager(instance, notificationDrawerManagerProvider.get());
      NotificationBroadcastReceiver_MembersInjector.injectActiveSessionHolder(instance, activeSessionHolderProvider.get());
      NotificationBroadcastReceiver_MembersInjector.injectAnalyticsTracker(instance, defaultVectorAnalyticsProvider.get());
      NotificationBroadcastReceiver_MembersInjector.injectClock(instance, new DefaultClock());
      NotificationBroadcastReceiver_MembersInjector.injectActionIds(instance, notificationActionIds());
      return instance;
    }

    private static final class SwitchingProvider<T> implements Provider<T> {
      private final SingletonCImpl singletonCImpl;

      private final int id;

      SwitchingProvider(SingletonCImpl singletonCImpl, int id) {
        this.singletonCImpl = singletonCImpl;
        this.id = id;
      }

      @SuppressWarnings("unchecked")
      @Override
      public T get() {
        switch (id) {
          case 0: // org.matrix.android.sdk.api.Matrix 
          return (T) VectorStaticModule_ProvidesMatrixFactory.providesMatrix(singletonCImpl.context(), singletonCImpl.matrixConfiguration());

          case 1: // im.vector.app.flipper.VectorFlipperProxy 
          return (T) new VectorFlipperProxy(singletonCImpl.context());

          case 2: // im.vector.app.features.analytics.metrics.VectorPlugins 
          return (T) new VectorPlugins(new SentryDownloadDeviceKeysMetrics(), new SentrySyncDurationMetrics());

          case 3: // @im.vector.app.core.di.DefaultPreferences android.content.SharedPreferences 
          return (T) VectorStaticModule_ProvidesDefaultSharedPreferencesFactory.providesDefaultSharedPreferences(singletonCImpl.context());

          case 4: // im.vector.app.EmojiCompatFontProvider 
          return (T) new EmojiCompatFontProvider();

          case 5: // im.vector.app.EmojiCompatWrapper 
          return (T) new EmojiCompatWrapper(singletonCImpl.context());

          case 6: // im.vector.app.features.rageshake.VectorUncaughtExceptionHandler 
          return (T) new VectorUncaughtExceptionHandler(singletonCImpl.providesDefaultSharedPreferencesProvider.get(), singletonCImpl.bugReporterProvider.get(), singletonCImpl.versionProvider(), singletonCImpl.versionCodeProvider());

          case 7: // im.vector.app.features.rageshake.BugReporter 
          return (T) new BugReporter(singletonCImpl.context(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.versionProvider(), singletonCImpl.vectorPreferences(), singletonCImpl.vectorFileLoggerProvider.get(), singletonCImpl.systemLocaleProvider(), singletonCImpl.providesMatrixProvider.get(), singletonCImpl.providesBuildMetaProvider.get(), new ProcessInfo(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider(), singletonCImpl.vectorLocaleProvider());

          case 8: // im.vector.app.core.di.ActiveSessionHolder 
          return (T) new ActiveSessionHolder(singletonCImpl.activeSessionDataSourceProvider.get(), singletonCImpl.keyRequestHandlerProvider.get(), singletonCImpl.incomingVerificationRequestHandlerProvider.get(), singletonCImpl.webRtcCallManagerProvider.get(), singletonCImpl.pushRuleTriggerListenerProvider.get(), singletonCImpl.sessionListenerProvider.get(), singletonCImpl.imageManager(), FlavorModule_Companion_ProvideGuardServiceStarterFactory.provideGuardServiceStarter(), singletonCImpl.sessionInitializer(), singletonCImpl.context(), singletonCImpl.authenticationService(), singletonCImpl.configureAndStartSessionUseCase(), singletonCImpl.unregisterUnifiedPushUseCase());

          case 9: // im.vector.app.ActiveSessionDataSource 
          return (T) new ActiveSessionDataSource();

          case 10: // im.vector.app.features.crypto.keysrequest.KeyRequestHandler 
          return (T) new KeyRequestHandler(singletonCImpl.context(), singletonCImpl.popupAlertManagerProvider.get(), singletonCImpl.vectorDateFormatter());

          case 11: // im.vector.app.features.popup.PopupAlertManager 
          return (T) new PopupAlertManager(new DefaultClock());

          case 12: // im.vector.app.features.crypto.verification.IncomingVerificationRequestHandler 
          return (T) new IncomingVerificationRequestHandler(singletonCImpl.context(), singletonCImpl.avatarRendererProvider, singletonCImpl.popupAlertManagerProvider.get(), new DefaultClock());

          case 13: // im.vector.app.features.home.AvatarRenderer 
          return (T) new AvatarRenderer(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.matrixItemColorProvider.get(), singletonCImpl.dimensionConverter());

          case 14: // im.vector.app.features.home.room.detail.timeline.helper.MatrixItemColorProvider 
          return (T) new MatrixItemColorProvider(singletonCImpl.colorProvider());

          case 15: // im.vector.app.features.call.webrtc.WebRtcCallManager 
          return (T) new WebRtcCallManager(singletonCImpl.context(), singletonCImpl.activeSessionDataSourceProvider.get(), singletonCImpl.defaultVectorAnalyticsProvider.get(), singletonCImpl.unifiedPushHelper(), ConfigurationModule_ProvidesVoipConfigFactory.providesVoipConfig());

          case 16: // im.vector.app.features.analytics.impl.DefaultVectorAnalytics 
          return (T) new DefaultVectorAnalytics(singletonCImpl.postHogFactory(), singletonCImpl.sentryAnalytics(), ConfigurationModule_ProvidesAnalyticsConfigFactory.providesAnalyticsConfig(), singletonCImpl.analyticsStore(), singletonCImpl.lateInitUserPropertiesFactory(), VectorStaticModule_ProvidesGlobalScopeFactory.providesGlobalScope());

          case 17: // im.vector.app.core.resources.BuildMeta 
          return (T) VectorStaticModule_ProvidesBuildMetaFactory.providesBuildMeta();

          case 18: // im.vector.app.features.notifications.PushRuleTriggerListener 
          return (T) new PushRuleTriggerListener(singletonCImpl.notifiableEventResolver(), singletonCImpl.notificationDrawerManagerProvider.get());

          case 19: // im.vector.app.features.html.EventHtmlRenderer 
          return (T) new EventHtmlRenderer(singletonCImpl.matrixHtmlPluginConfigure(), singletonCImpl.context(), singletonCImpl.vectorPreferences(), singletonCImpl.activeSessionHolderProvider.get());

          case 20: // im.vector.app.features.notifications.NotificationDrawerManager 
          return (T) new NotificationDrawerManager(singletonCImpl.context(), singletonCImpl.notificationDisplayer(), singletonCImpl.vectorPreferences(), singletonCImpl.activeSessionDataSourceProvider.get(), singletonCImpl.notifiableEventProcessor(), singletonCImpl.notificationRenderer(), singletonCImpl.notificationEventPersistence(), singletonCImpl.filteredEventDetector(), singletonCImpl.providesBuildMetaProvider.get());

          case 21: // im.vector.app.features.notifications.NotificationUtils 
          return (T) new NotificationUtils(singletonCImpl.context(), singletonCImpl.stringProvider(), singletonCImpl.vectorPreferences(), new DefaultClock(), singletonCImpl.notificationActionIds());

          case 22: // im.vector.app.features.notifications.NotificationBitmapLoader 
          return (T) new NotificationBitmapLoader(singletonCImpl.context());

          case 23: // im.vector.app.features.session.SessionListener 
          return (T) new SessionListener(singletonCImpl.defaultVectorAnalyticsProvider.get());

          case 24: // im.vector.app.core.notification.NotificationsSettingUpdater 
          return (T) new NotificationsSettingUpdater(singletonCImpl.updateEnableNotificationsSettingOnChangeUseCase());

          case 25: // im.vector.app.features.rageshake.VectorFileLogger 
          return (T) new VectorFileLogger(singletonCImpl.context(), singletonCImpl.vectorPreferences());

          case 26: // im.vector.app.SpaceStateHandlerImpl 
          return (T) new SpaceStateHandlerImpl(singletonCImpl.activeSessionDataSourceProvider.get(), singletonCImpl.sharedPreferencesUiStateRepository(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.defaultVectorAnalyticsProvider.get(), singletonCImpl.vectorPreferences());

          case 27: // im.vector.app.features.pin.PinLocker 
          return (T) new PinLocker(singletonCImpl.sharedPrefPinCodeStoreProvider.get(), singletonCImpl.vectorPreferences());

          case 28: // im.vector.app.features.pin.SharedPrefPinCodeStore 
          return (T) new SharedPrefPinCodeStore(singletonCImpl.sharedPreferences());

          case 29: // im.vector.app.features.invite.InvitesAcceptor 
          return (T) new InvitesAcceptor(singletonCImpl.activeSessionDataSourceProvider.get(), new CompileTimeAutoAcceptInvites());

          case 30: // im.vector.app.AutoRageShaker 
          return (T) new AutoRageShaker(singletonCImpl.activeSessionDataSourceProvider.get(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.bugReporterProvider.get(), singletonCImpl.vectorPreferences());

          case 31: // im.vector.app.features.settings.VectorLocale 
          return (T) new VectorLocale(singletonCImpl.context(), singletonCImpl.providesBuildMetaProvider.get(), singletonCImpl.providesDefaultSharedPreferencesProvider.get());

          case 32: // im.vector.app.features.navigation.DefaultNavigator 
          return (T) new DefaultNavigator(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.vectorPreferences(), singletonCImpl.widgetArgsBuilder(), singletonCImpl.spaceStateHandlerImplProvider.get(), singletonCImpl.supportedVerificationMethodsProvider(), singletonCImpl.debugVectorFeatures(), singletonCImpl.defaultVectorAnalyticsProvider.get(), DebugModule_Companion_ProvidesDebugNavigatorFactory.providesDebugNavigator());

          case 33: // kotlinx.coroutines.CoroutineScope 
          return (T) VectorStaticModule_ProvidesApplicationCoroutineScopeFactory.providesApplicationCoroutineScope();

          case 34: // im.vector.app.features.reactions.data.EmojiDataSource 
          return (T) new EmojiDataSource(singletonCImpl.providesApplicationCoroutineScopeProvider.get(), singletonCImpl.resources());

          case 35: // im.vector.app.features.pin.lockscreen.crypto.LockScreenKeyRepository 
          return (T) new LockScreenKeyRepository(LockScreenModule_ProvidePinCodeKeyAliasFactory.providePinCodeKeyAlias(), LockScreenModule_ProvideSystemKeyAliasFactory.provideSystemKeyAlias(), singletonCImpl.factoryProvider.get());

          case 36: // im.vector.app.features.pin.lockscreen.crypto.KeyStoreCrypto.Factory 
          return (T) new KeyStoreCrypto.Factory() {
            @Override
            public KeyStoreCrypto provide(String alias, boolean keyNeedsUserAuthentication) {
              return new KeyStoreCrypto(alias, keyNeedsUserAuthentication, singletonCImpl.context(), LockScreenModule_ProvideBuildVersionSdkIntProviderFactory.provideBuildVersionSdkIntProvider(), LockScreenModule_ProvideKeyStoreFactory.provideKeyStore());
            }
          };

          case 37: // im.vector.app.features.home.room.detail.timeline.helper.AudioMessagePlaybackTracker 
          return (T) new AudioMessagePlaybackTracker();

          case 38: // im.vector.app.features.home.room.detail.RoomDetailPendingActionStore 
          return (T) new RoomDetailPendingActionStore();

          case 39: // im.vector.app.features.html.VectorHtmlCompressor 
          return (T) new VectorHtmlCompressor();

          case 40: // im.vector.app.features.home.room.detail.timeline.helper.LocationPinProvider 
          return (T) new LocationPinProvider(singletonCImpl.context(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.dimensionConverter(), singletonCImpl.avatarRendererProvider.get(), singletonCImpl.matrixItemColorProvider.get());

          case 41: // im.vector.app.features.voicebroadcast.recording.VoiceBroadcastRecorder 
          return (T) VoiceModule.Companion.providesVoiceBroadcastRecorder(singletonCImpl.context(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.getVoiceBroadcastStateEventLiveUseCase());

          case 42: // im.vector.app.features.voicebroadcast.listening.VoiceBroadcastPlayerImpl 
          return (T) new VoiceBroadcastPlayerImpl(singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.audioMessagePlaybackTrackerProvider.get(), singletonCImpl.getVoiceBroadcastStateEventLiveUseCase(), singletonCImpl.getLiveVoiceBroadcastChunksUseCase());

          case 43: // im.vector.app.features.analytics.DecryptionFailureTracker 
          return (T) new DecryptionFailureTracker(singletonCImpl.defaultVectorAnalyticsProvider.get(), new DefaultClock());

          case 44: // im.vector.app.features.location.live.tracking.LiveLocationNotificationBuilder 
          return (T) new LiveLocationNotificationBuilder(singletonCImpl.context(), singletonCImpl.stringProvider(), new DefaultClock(), singletonCImpl.notificationActionIds());

          case 45: // im.vector.app.features.location.LocationTracker 
          return (T) new LocationTracker(singletonCImpl.context(), singletonCImpl.activeSessionHolderProvider.get(), singletonCImpl.providesBuildMetaProvider.get());

          case 46: // im.vector.app.features.login.ReAuthHelper 
          return (T) new ReAuthHelper();

          case 47: // im.vector.app.core.di.login.repository.LoginRepository 
          return (T) LoginModule_ProvideLoginRepositoryFactory.provideLoginRepository(singletonCImpl.loginModule, singletonCImpl.provideRemoteLoginDataSourceProvider.get());

          case 48: // im.vector.app.core.di.login.domain.ApolloLoginClient 
          return (T) LoginModule_ProvideRemoteLoginDataSourceFactory.provideRemoteLoginDataSource(singletonCImpl.loginModule, singletonCImpl.provideApolloProvider.get());

          case 49: // com.apollographql.apollo3.ApolloClient 
          return (T) LoginModule_ProvideApolloFactory.provideApollo(singletonCImpl.loginModule, singletonCImpl.okhttpClientProvider.get());

          case 50: // okhttp3.OkHttpClient 
          return (T) LoginModule_OkhttpClientFactory.okhttpClient(singletonCImpl.loginModule);

          case 51: // im.vector.app.features.call.conference.JitsiActiveConferenceHolder 
          return (T) new JitsiActiveConferenceHolder(singletonCImpl.context());

          case 52: // im.vector.app.features.location.live.tracking.LocationSharingServiceConnection 
          return (T) new LocationSharingServiceConnection(singletonCImpl.context(), singletonCImpl.activeSessionHolderProvider.get());

          default: throw new AssertionError(id);
        }
      }
    }
  }
}
