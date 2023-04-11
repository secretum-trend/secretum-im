package im.vector.app;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import im.vector.app.core.debug.FlipperProxy;
import im.vector.app.core.debug.LeakDetector;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.resources.BuildMeta;
import im.vector.app.features.analytics.VectorAnalytics;
import im.vector.app.features.call.webrtc.WebRtcCallManager;
import im.vector.app.features.configuration.VectorConfiguration;
import im.vector.app.features.disclaimer.DisclaimerDialog;
import im.vector.app.features.invite.InvitesAcceptor;
import im.vector.app.features.notifications.NotificationDrawerManager;
import im.vector.app.features.notifications.NotificationUtils;
import im.vector.app.features.pin.PinLocker;
import im.vector.app.features.popup.PopupAlertManager;
import im.vector.app.features.rageshake.VectorFileLogger;
import im.vector.app.features.rageshake.VectorUncaughtExceptionHandler;
import im.vector.app.features.settings.VectorLocale;
import im.vector.app.features.settings.VectorPreferences;
import im.vector.app.features.version.VersionProvider;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.auth.AuthenticationService;
import org.matrix.android.sdk.api.legacy.LegacySessionImporter;

@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class VectorApplication_MembersInjector implements MembersInjector<VectorApplication> {
  private final Provider<LegacySessionImporter> legacySessionImporterProvider;

  private final Provider<AuthenticationService> authenticationServiceProvider;

  private final Provider<VectorConfiguration> vectorConfigurationProvider;

  private final Provider<EmojiCompatFontProvider> emojiCompatFontProvider;

  private final Provider<EmojiCompatWrapper> emojiCompatWrapperProvider;

  private final Provider<VectorUncaughtExceptionHandler> vectorUncaughtExceptionHandlerProvider;

  private final Provider<ActiveSessionHolder> activeSessionHolderProvider;

  private final Provider<NotificationDrawerManager> notificationDrawerManagerProvider;

  private final Provider<VectorPreferences> vectorPreferencesProvider;

  private final Provider<VersionProvider> versionProvider;

  private final Provider<NotificationUtils> notificationUtilsProvider;

  private final Provider<SpaceStateHandler> spaceStateHandlerProvider;

  private final Provider<PopupAlertManager> popupAlertManagerProvider;

  private final Provider<PinLocker> pinLockerProvider;

  private final Provider<WebRtcCallManager> callManagerProvider;

  private final Provider<InvitesAcceptor> invitesAcceptorProvider;

  private final Provider<AutoRageShaker> autoRageShakerProvider;

  private final Provider<VectorFileLogger> vectorFileLoggerProvider;

  private final Provider<VectorAnalytics> vectorAnalyticsProvider;

  private final Provider<FlipperProxy> flipperProxyProvider;

  private final Provider<Matrix> matrixProvider;

  private final Provider<FcmHelper> fcmHelperProvider;

  private final Provider<BuildMeta> buildMetaProvider;

  private final Provider<LeakDetector> leakDetectorProvider;

  private final Provider<VectorLocale> vectorLocaleProvider;

  private final Provider<DisclaimerDialog> disclaimerDialogProvider;

  public VectorApplication_MembersInjector(
      Provider<LegacySessionImporter> legacySessionImporterProvider,
      Provider<AuthenticationService> authenticationServiceProvider,
      Provider<VectorConfiguration> vectorConfigurationProvider,
      Provider<EmojiCompatFontProvider> emojiCompatFontProvider,
      Provider<EmojiCompatWrapper> emojiCompatWrapperProvider,
      Provider<VectorUncaughtExceptionHandler> vectorUncaughtExceptionHandlerProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<NotificationDrawerManager> notificationDrawerManagerProvider,
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<VersionProvider> versionProvider,
      Provider<NotificationUtils> notificationUtilsProvider,
      Provider<SpaceStateHandler> spaceStateHandlerProvider,
      Provider<PopupAlertManager> popupAlertManagerProvider, Provider<PinLocker> pinLockerProvider,
      Provider<WebRtcCallManager> callManagerProvider,
      Provider<InvitesAcceptor> invitesAcceptorProvider,
      Provider<AutoRageShaker> autoRageShakerProvider,
      Provider<VectorFileLogger> vectorFileLoggerProvider,
      Provider<VectorAnalytics> vectorAnalyticsProvider,
      Provider<FlipperProxy> flipperProxyProvider, Provider<Matrix> matrixProvider,
      Provider<FcmHelper> fcmHelperProvider, Provider<BuildMeta> buildMetaProvider,
      Provider<LeakDetector> leakDetectorProvider, Provider<VectorLocale> vectorLocaleProvider,
      Provider<DisclaimerDialog> disclaimerDialogProvider) {
    this.legacySessionImporterProvider = legacySessionImporterProvider;
    this.authenticationServiceProvider = authenticationServiceProvider;
    this.vectorConfigurationProvider = vectorConfigurationProvider;
    this.emojiCompatFontProvider = emojiCompatFontProvider;
    this.emojiCompatWrapperProvider = emojiCompatWrapperProvider;
    this.vectorUncaughtExceptionHandlerProvider = vectorUncaughtExceptionHandlerProvider;
    this.activeSessionHolderProvider = activeSessionHolderProvider;
    this.notificationDrawerManagerProvider = notificationDrawerManagerProvider;
    this.vectorPreferencesProvider = vectorPreferencesProvider;
    this.versionProvider = versionProvider;
    this.notificationUtilsProvider = notificationUtilsProvider;
    this.spaceStateHandlerProvider = spaceStateHandlerProvider;
    this.popupAlertManagerProvider = popupAlertManagerProvider;
    this.pinLockerProvider = pinLockerProvider;
    this.callManagerProvider = callManagerProvider;
    this.invitesAcceptorProvider = invitesAcceptorProvider;
    this.autoRageShakerProvider = autoRageShakerProvider;
    this.vectorFileLoggerProvider = vectorFileLoggerProvider;
    this.vectorAnalyticsProvider = vectorAnalyticsProvider;
    this.flipperProxyProvider = flipperProxyProvider;
    this.matrixProvider = matrixProvider;
    this.fcmHelperProvider = fcmHelperProvider;
    this.buildMetaProvider = buildMetaProvider;
    this.leakDetectorProvider = leakDetectorProvider;
    this.vectorLocaleProvider = vectorLocaleProvider;
    this.disclaimerDialogProvider = disclaimerDialogProvider;
  }

  public static MembersInjector<VectorApplication> create(
      Provider<LegacySessionImporter> legacySessionImporterProvider,
      Provider<AuthenticationService> authenticationServiceProvider,
      Provider<VectorConfiguration> vectorConfigurationProvider,
      Provider<EmojiCompatFontProvider> emojiCompatFontProvider,
      Provider<EmojiCompatWrapper> emojiCompatWrapperProvider,
      Provider<VectorUncaughtExceptionHandler> vectorUncaughtExceptionHandlerProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<NotificationDrawerManager> notificationDrawerManagerProvider,
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<VersionProvider> versionProvider,
      Provider<NotificationUtils> notificationUtilsProvider,
      Provider<SpaceStateHandler> spaceStateHandlerProvider,
      Provider<PopupAlertManager> popupAlertManagerProvider, Provider<PinLocker> pinLockerProvider,
      Provider<WebRtcCallManager> callManagerProvider,
      Provider<InvitesAcceptor> invitesAcceptorProvider,
      Provider<AutoRageShaker> autoRageShakerProvider,
      Provider<VectorFileLogger> vectorFileLoggerProvider,
      Provider<VectorAnalytics> vectorAnalyticsProvider,
      Provider<FlipperProxy> flipperProxyProvider, Provider<Matrix> matrixProvider,
      Provider<FcmHelper> fcmHelperProvider, Provider<BuildMeta> buildMetaProvider,
      Provider<LeakDetector> leakDetectorProvider, Provider<VectorLocale> vectorLocaleProvider,
      Provider<DisclaimerDialog> disclaimerDialogProvider) {
    return new VectorApplication_MembersInjector(legacySessionImporterProvider, authenticationServiceProvider, vectorConfigurationProvider, emojiCompatFontProvider, emojiCompatWrapperProvider, vectorUncaughtExceptionHandlerProvider, activeSessionHolderProvider, notificationDrawerManagerProvider, vectorPreferencesProvider, versionProvider, notificationUtilsProvider, spaceStateHandlerProvider, popupAlertManagerProvider, pinLockerProvider, callManagerProvider, invitesAcceptorProvider, autoRageShakerProvider, vectorFileLoggerProvider, vectorAnalyticsProvider, flipperProxyProvider, matrixProvider, fcmHelperProvider, buildMetaProvider, leakDetectorProvider, vectorLocaleProvider, disclaimerDialogProvider);
  }

  @Override
  public void injectMembers(VectorApplication instance) {
    injectLegacySessionImporter(instance, legacySessionImporterProvider.get());
    injectAuthenticationService(instance, authenticationServiceProvider.get());
    injectVectorConfiguration(instance, vectorConfigurationProvider.get());
    injectEmojiCompatFontProvider(instance, emojiCompatFontProvider.get());
    injectEmojiCompatWrapper(instance, emojiCompatWrapperProvider.get());
    injectVectorUncaughtExceptionHandler(instance, vectorUncaughtExceptionHandlerProvider.get());
    injectActiveSessionHolder(instance, activeSessionHolderProvider.get());
    injectNotificationDrawerManager(instance, notificationDrawerManagerProvider.get());
    injectVectorPreferences(instance, vectorPreferencesProvider.get());
    injectVersionProvider(instance, versionProvider.get());
    injectNotificationUtils(instance, notificationUtilsProvider.get());
    injectSpaceStateHandler(instance, spaceStateHandlerProvider.get());
    injectPopupAlertManager(instance, popupAlertManagerProvider.get());
    injectPinLocker(instance, pinLockerProvider.get());
    injectCallManager(instance, callManagerProvider.get());
    injectInvitesAcceptor(instance, invitesAcceptorProvider.get());
    injectAutoRageShaker(instance, autoRageShakerProvider.get());
    injectVectorFileLogger(instance, vectorFileLoggerProvider.get());
    injectVectorAnalytics(instance, vectorAnalyticsProvider.get());
    injectFlipperProxy(instance, flipperProxyProvider.get());
    injectMatrix(instance, matrixProvider.get());
    injectFcmHelper(instance, fcmHelperProvider.get());
    injectBuildMeta(instance, buildMetaProvider.get());
    injectLeakDetector(instance, leakDetectorProvider.get());
    injectVectorLocale(instance, vectorLocaleProvider.get());
    injectDisclaimerDialog(instance, disclaimerDialogProvider.get());
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.legacySessionImporter")
  public static void injectLegacySessionImporter(VectorApplication instance,
      LegacySessionImporter legacySessionImporter) {
    instance.legacySessionImporter = legacySessionImporter;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.authenticationService")
  public static void injectAuthenticationService(VectorApplication instance,
      AuthenticationService authenticationService) {
    instance.authenticationService = authenticationService;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.vectorConfiguration")
  public static void injectVectorConfiguration(VectorApplication instance,
      VectorConfiguration vectorConfiguration) {
    instance.vectorConfiguration = vectorConfiguration;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.emojiCompatFontProvider")
  public static void injectEmojiCompatFontProvider(VectorApplication instance,
      EmojiCompatFontProvider emojiCompatFontProvider) {
    instance.emojiCompatFontProvider = emojiCompatFontProvider;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.emojiCompatWrapper")
  public static void injectEmojiCompatWrapper(VectorApplication instance,
      EmojiCompatWrapper emojiCompatWrapper) {
    instance.emojiCompatWrapper = emojiCompatWrapper;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.vectorUncaughtExceptionHandler")
  public static void injectVectorUncaughtExceptionHandler(VectorApplication instance,
      VectorUncaughtExceptionHandler vectorUncaughtExceptionHandler) {
    instance.vectorUncaughtExceptionHandler = vectorUncaughtExceptionHandler;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.activeSessionHolder")
  public static void injectActiveSessionHolder(VectorApplication instance,
      ActiveSessionHolder activeSessionHolder) {
    instance.activeSessionHolder = activeSessionHolder;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.notificationDrawerManager")
  public static void injectNotificationDrawerManager(VectorApplication instance,
      NotificationDrawerManager notificationDrawerManager) {
    instance.notificationDrawerManager = notificationDrawerManager;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.vectorPreferences")
  public static void injectVectorPreferences(VectorApplication instance,
      VectorPreferences vectorPreferences) {
    instance.vectorPreferences = vectorPreferences;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.versionProvider")
  public static void injectVersionProvider(VectorApplication instance,
      VersionProvider versionProvider) {
    instance.versionProvider = versionProvider;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.notificationUtils")
  public static void injectNotificationUtils(VectorApplication instance,
      NotificationUtils notificationUtils) {
    instance.notificationUtils = notificationUtils;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.spaceStateHandler")
  public static void injectSpaceStateHandler(VectorApplication instance,
      SpaceStateHandler spaceStateHandler) {
    instance.spaceStateHandler = spaceStateHandler;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.popupAlertManager")
  public static void injectPopupAlertManager(VectorApplication instance,
      PopupAlertManager popupAlertManager) {
    instance.popupAlertManager = popupAlertManager;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.pinLocker")
  public static void injectPinLocker(VectorApplication instance, PinLocker pinLocker) {
    instance.pinLocker = pinLocker;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.callManager")
  public static void injectCallManager(VectorApplication instance, WebRtcCallManager callManager) {
    instance.callManager = callManager;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.invitesAcceptor")
  public static void injectInvitesAcceptor(VectorApplication instance,
      InvitesAcceptor invitesAcceptor) {
    instance.invitesAcceptor = invitesAcceptor;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.autoRageShaker")
  public static void injectAutoRageShaker(VectorApplication instance,
      AutoRageShaker autoRageShaker) {
    instance.autoRageShaker = autoRageShaker;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.vectorFileLogger")
  public static void injectVectorFileLogger(VectorApplication instance,
      VectorFileLogger vectorFileLogger) {
    instance.vectorFileLogger = vectorFileLogger;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.vectorAnalytics")
  public static void injectVectorAnalytics(VectorApplication instance,
      VectorAnalytics vectorAnalytics) {
    instance.vectorAnalytics = vectorAnalytics;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.flipperProxy")
  public static void injectFlipperProxy(VectorApplication instance, FlipperProxy flipperProxy) {
    instance.flipperProxy = flipperProxy;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.matrix")
  public static void injectMatrix(VectorApplication instance, Matrix matrix) {
    instance.matrix = matrix;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.fcmHelper")
  public static void injectFcmHelper(VectorApplication instance, FcmHelper fcmHelper) {
    instance.fcmHelper = fcmHelper;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.buildMeta")
  public static void injectBuildMeta(VectorApplication instance, BuildMeta buildMeta) {
    instance.buildMeta = buildMeta;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.leakDetector")
  public static void injectLeakDetector(VectorApplication instance, LeakDetector leakDetector) {
    instance.leakDetector = leakDetector;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.vectorLocale")
  public static void injectVectorLocale(VectorApplication instance, VectorLocale vectorLocale) {
    instance.vectorLocale = vectorLocale;
  }

  @InjectedFieldSignature("im.vector.app.VectorApplication.disclaimerDialog")
  public static void injectDisclaimerDialog(VectorApplication instance,
      DisclaimerDialog disclaimerDialog) {
    instance.disclaimerDialog = disclaimerDialog;
  }
}
