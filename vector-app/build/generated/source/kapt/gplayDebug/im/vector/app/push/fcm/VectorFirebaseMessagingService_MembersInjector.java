package im.vector.app.push.fcm;

import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.pushers.PushParser;
import im.vector.app.core.pushers.PushersManager;
import im.vector.app.core.pushers.UnifiedPushHelper;
import im.vector.app.core.pushers.VectorPushHandler;
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
public final class VectorFirebaseMessagingService_MembersInjector implements MembersInjector<VectorFirebaseMessagingService> {
  private final Provider<FcmHelper> fcmHelperProvider;

  private final Provider<VectorPreferences> vectorPreferencesProvider;

  private final Provider<ActiveSessionHolder> activeSessionHolderProvider;

  private final Provider<PushersManager> pushersManagerProvider;

  private final Provider<PushParser> pushParserProvider;

  private final Provider<VectorPushHandler> vectorPushHandlerProvider;

  private final Provider<UnifiedPushHelper> unifiedPushHelperProvider;

  public VectorFirebaseMessagingService_MembersInjector(Provider<FcmHelper> fcmHelperProvider,
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<PushersManager> pushersManagerProvider, Provider<PushParser> pushParserProvider,
      Provider<VectorPushHandler> vectorPushHandlerProvider,
      Provider<UnifiedPushHelper> unifiedPushHelperProvider) {
    this.fcmHelperProvider = fcmHelperProvider;
    this.vectorPreferencesProvider = vectorPreferencesProvider;
    this.activeSessionHolderProvider = activeSessionHolderProvider;
    this.pushersManagerProvider = pushersManagerProvider;
    this.pushParserProvider = pushParserProvider;
    this.vectorPushHandlerProvider = vectorPushHandlerProvider;
    this.unifiedPushHelperProvider = unifiedPushHelperProvider;
  }

  public static MembersInjector<VectorFirebaseMessagingService> create(
      Provider<FcmHelper> fcmHelperProvider, Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<PushersManager> pushersManagerProvider, Provider<PushParser> pushParserProvider,
      Provider<VectorPushHandler> vectorPushHandlerProvider,
      Provider<UnifiedPushHelper> unifiedPushHelperProvider) {
    return new VectorFirebaseMessagingService_MembersInjector(fcmHelperProvider, vectorPreferencesProvider, activeSessionHolderProvider, pushersManagerProvider, pushParserProvider, vectorPushHandlerProvider, unifiedPushHelperProvider);
  }

  @Override
  public void injectMembers(VectorFirebaseMessagingService instance) {
    injectFcmHelper(instance, fcmHelperProvider.get());
    injectVectorPreferences(instance, vectorPreferencesProvider.get());
    injectActiveSessionHolder(instance, activeSessionHolderProvider.get());
    injectPushersManager(instance, pushersManagerProvider.get());
    injectPushParser(instance, pushParserProvider.get());
    injectVectorPushHandler(instance, vectorPushHandlerProvider.get());
    injectUnifiedPushHelper(instance, unifiedPushHelperProvider.get());
  }

  @InjectedFieldSignature("im.vector.app.push.fcm.VectorFirebaseMessagingService.fcmHelper")
  public static void injectFcmHelper(VectorFirebaseMessagingService instance, FcmHelper fcmHelper) {
    instance.fcmHelper = fcmHelper;
  }

  @InjectedFieldSignature("im.vector.app.push.fcm.VectorFirebaseMessagingService.vectorPreferences")
  public static void injectVectorPreferences(VectorFirebaseMessagingService instance,
      VectorPreferences vectorPreferences) {
    instance.vectorPreferences = vectorPreferences;
  }

  @InjectedFieldSignature("im.vector.app.push.fcm.VectorFirebaseMessagingService.activeSessionHolder")
  public static void injectActiveSessionHolder(VectorFirebaseMessagingService instance,
      ActiveSessionHolder activeSessionHolder) {
    instance.activeSessionHolder = activeSessionHolder;
  }

  @InjectedFieldSignature("im.vector.app.push.fcm.VectorFirebaseMessagingService.pushersManager")
  public static void injectPushersManager(VectorFirebaseMessagingService instance,
      PushersManager pushersManager) {
    instance.pushersManager = pushersManager;
  }

  @InjectedFieldSignature("im.vector.app.push.fcm.VectorFirebaseMessagingService.pushParser")
  public static void injectPushParser(VectorFirebaseMessagingService instance,
      PushParser pushParser) {
    instance.pushParser = pushParser;
  }

  @InjectedFieldSignature("im.vector.app.push.fcm.VectorFirebaseMessagingService.vectorPushHandler")
  public static void injectVectorPushHandler(VectorFirebaseMessagingService instance,
      VectorPushHandler vectorPushHandler) {
    instance.vectorPushHandler = vectorPushHandler;
  }

  @InjectedFieldSignature("im.vector.app.push.fcm.VectorFirebaseMessagingService.unifiedPushHelper")
  public static void injectUnifiedPushHelper(VectorFirebaseMessagingService instance,
      UnifiedPushHelper unifiedPushHelper) {
    instance.unifiedPushHelper = unifiedPushHelper;
  }
}
