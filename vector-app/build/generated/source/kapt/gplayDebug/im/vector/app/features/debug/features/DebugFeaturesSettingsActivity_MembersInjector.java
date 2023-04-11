package im.vector.app.features.debug.features;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import im.vector.app.core.debug.DebugReceiver;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.error.ErrorFormatter;
import im.vector.app.core.platform.VectorBaseActivity_MembersInjector;
import im.vector.app.core.resources.BuildMeta;
import im.vector.app.features.VectorFeatures;
import im.vector.app.features.analytics.AnalyticsTracker;
import im.vector.app.features.navigation.Navigator;
import im.vector.app.features.pin.PinLocker;
import im.vector.app.features.rageshake.BugReporter;
import im.vector.app.features.rageshake.RageShake;
import im.vector.app.features.session.SessionListener;
import im.vector.app.features.settings.FontScalePreferences;
import im.vector.app.features.settings.VectorLocaleProvider;
import im.vector.app.features.settings.VectorPreferences;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DebugFeaturesSettingsActivity_MembersInjector implements MembersInjector<DebugFeaturesSettingsActivity> {
  private final Provider<AnalyticsTracker> analyticsTrackerProvider;

  private final Provider<SessionListener> sessionListenerProvider;

  private final Provider<BugReporter> bugReporterProvider;

  private final Provider<PinLocker> pinLockerProvider;

  private final Provider<RageShake> rageShakeProvider;

  private final Provider<BuildMeta> buildMetaProvider;

  private final Provider<FontScalePreferences> fontScalePreferencesProvider;

  private final Provider<VectorLocaleProvider> vectorLocaleProvider;

  private final Provider<VectorFeatures> vectorFeaturesProvider;

  private final Provider<Navigator> navigatorProvider;

  private final Provider<ActiveSessionHolder> activeSessionHolderProvider;

  private final Provider<VectorPreferences> vectorPreferencesProvider;

  private final Provider<ErrorFormatter> errorFormatterProvider;

  private final Provider<DebugReceiver> debugReceiverProvider;

  private final Provider<DebugVectorFeatures> debugFeaturesProvider;

  private final Provider<DebugFeaturesStateFactory> debugFeaturesStateFactoryProvider;

  private final Provider<FeaturesController> controllerProvider;

  public DebugFeaturesSettingsActivity_MembersInjector(
      Provider<AnalyticsTracker> analyticsTrackerProvider,
      Provider<SessionListener> sessionListenerProvider, Provider<BugReporter> bugReporterProvider,
      Provider<PinLocker> pinLockerProvider, Provider<RageShake> rageShakeProvider,
      Provider<BuildMeta> buildMetaProvider,
      Provider<FontScalePreferences> fontScalePreferencesProvider,
      Provider<VectorLocaleProvider> vectorLocaleProvider,
      Provider<VectorFeatures> vectorFeaturesProvider, Provider<Navigator> navigatorProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<ErrorFormatter> errorFormatterProvider,
      Provider<DebugReceiver> debugReceiverProvider,
      Provider<DebugVectorFeatures> debugFeaturesProvider,
      Provider<DebugFeaturesStateFactory> debugFeaturesStateFactoryProvider,
      Provider<FeaturesController> controllerProvider) {
    this.analyticsTrackerProvider = analyticsTrackerProvider;
    this.sessionListenerProvider = sessionListenerProvider;
    this.bugReporterProvider = bugReporterProvider;
    this.pinLockerProvider = pinLockerProvider;
    this.rageShakeProvider = rageShakeProvider;
    this.buildMetaProvider = buildMetaProvider;
    this.fontScalePreferencesProvider = fontScalePreferencesProvider;
    this.vectorLocaleProvider = vectorLocaleProvider;
    this.vectorFeaturesProvider = vectorFeaturesProvider;
    this.navigatorProvider = navigatorProvider;
    this.activeSessionHolderProvider = activeSessionHolderProvider;
    this.vectorPreferencesProvider = vectorPreferencesProvider;
    this.errorFormatterProvider = errorFormatterProvider;
    this.debugReceiverProvider = debugReceiverProvider;
    this.debugFeaturesProvider = debugFeaturesProvider;
    this.debugFeaturesStateFactoryProvider = debugFeaturesStateFactoryProvider;
    this.controllerProvider = controllerProvider;
  }

  public static MembersInjector<DebugFeaturesSettingsActivity> create(
      Provider<AnalyticsTracker> analyticsTrackerProvider,
      Provider<SessionListener> sessionListenerProvider, Provider<BugReporter> bugReporterProvider,
      Provider<PinLocker> pinLockerProvider, Provider<RageShake> rageShakeProvider,
      Provider<BuildMeta> buildMetaProvider,
      Provider<FontScalePreferences> fontScalePreferencesProvider,
      Provider<VectorLocaleProvider> vectorLocaleProvider,
      Provider<VectorFeatures> vectorFeaturesProvider, Provider<Navigator> navigatorProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<ErrorFormatter> errorFormatterProvider,
      Provider<DebugReceiver> debugReceiverProvider,
      Provider<DebugVectorFeatures> debugFeaturesProvider,
      Provider<DebugFeaturesStateFactory> debugFeaturesStateFactoryProvider,
      Provider<FeaturesController> controllerProvider) {
    return new DebugFeaturesSettingsActivity_MembersInjector(analyticsTrackerProvider, sessionListenerProvider, bugReporterProvider, pinLockerProvider, rageShakeProvider, buildMetaProvider, fontScalePreferencesProvider, vectorLocaleProvider, vectorFeaturesProvider, navigatorProvider, activeSessionHolderProvider, vectorPreferencesProvider, errorFormatterProvider, debugReceiverProvider, debugFeaturesProvider, debugFeaturesStateFactoryProvider, controllerProvider);
  }

  @Override
  public void injectMembers(DebugFeaturesSettingsActivity instance) {
    VectorBaseActivity_MembersInjector.injectAnalyticsTracker(instance, analyticsTrackerProvider.get());
    VectorBaseActivity_MembersInjector.injectSessionListener(instance, sessionListenerProvider.get());
    VectorBaseActivity_MembersInjector.injectBugReporter(instance, bugReporterProvider.get());
    VectorBaseActivity_MembersInjector.injectPinLocker(instance, pinLockerProvider.get());
    VectorBaseActivity_MembersInjector.injectRageShake(instance, rageShakeProvider.get());
    VectorBaseActivity_MembersInjector.injectBuildMeta(instance, buildMetaProvider.get());
    VectorBaseActivity_MembersInjector.injectFontScalePreferences(instance, fontScalePreferencesProvider.get());
    VectorBaseActivity_MembersInjector.injectVectorLocale(instance, vectorLocaleProvider.get());
    VectorBaseActivity_MembersInjector.injectVectorFeatures(instance, vectorFeaturesProvider.get());
    VectorBaseActivity_MembersInjector.injectNavigator(instance, navigatorProvider.get());
    VectorBaseActivity_MembersInjector.injectActiveSessionHolder(instance, activeSessionHolderProvider.get());
    VectorBaseActivity_MembersInjector.injectVectorPreferences(instance, vectorPreferencesProvider.get());
    VectorBaseActivity_MembersInjector.injectErrorFormatter(instance, errorFormatterProvider.get());
    VectorBaseActivity_MembersInjector.injectDebugReceiver(instance, debugReceiverProvider.get());
    injectDebugFeatures(instance, debugFeaturesProvider.get());
    injectDebugFeaturesStateFactory(instance, debugFeaturesStateFactoryProvider.get());
    injectController(instance, controllerProvider.get());
  }

  @InjectedFieldSignature("im.vector.app.features.debug.features.DebugFeaturesSettingsActivity.debugFeatures")
  public static void injectDebugFeatures(DebugFeaturesSettingsActivity instance,
      DebugVectorFeatures debugFeatures) {
    instance.debugFeatures = debugFeatures;
  }

  @InjectedFieldSignature("im.vector.app.features.debug.features.DebugFeaturesSettingsActivity.debugFeaturesStateFactory")
  public static void injectDebugFeaturesStateFactory(DebugFeaturesSettingsActivity instance,
      DebugFeaturesStateFactory debugFeaturesStateFactory) {
    instance.debugFeaturesStateFactory = debugFeaturesStateFactory;
  }

  @InjectedFieldSignature("im.vector.app.features.debug.features.DebugFeaturesSettingsActivity.controller")
  public static void injectController(DebugFeaturesSettingsActivity instance,
      FeaturesController controller) {
    instance.controller = controller;
  }
}
