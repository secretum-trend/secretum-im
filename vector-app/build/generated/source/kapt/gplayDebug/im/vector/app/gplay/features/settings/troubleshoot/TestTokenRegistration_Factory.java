package im.vector.app.gplay.features.settings.troubleshoot;

import androidx.fragment.app.FragmentActivity;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.pushers.PushersManager;
import im.vector.app.core.resources.StringProvider;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class TestTokenRegistration_Factory implements Factory<TestTokenRegistration> {
  private final Provider<FragmentActivity> contextProvider;

  private final Provider<StringProvider> stringProvider;

  private final Provider<PushersManager> pushersManagerProvider;

  private final Provider<ActiveSessionHolder> activeSessionHolderProvider;

  private final Provider<FcmHelper> fcmHelperProvider;

  public TestTokenRegistration_Factory(Provider<FragmentActivity> contextProvider,
      Provider<StringProvider> stringProvider, Provider<PushersManager> pushersManagerProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<FcmHelper> fcmHelperProvider) {
    this.contextProvider = contextProvider;
    this.stringProvider = stringProvider;
    this.pushersManagerProvider = pushersManagerProvider;
    this.activeSessionHolderProvider = activeSessionHolderProvider;
    this.fcmHelperProvider = fcmHelperProvider;
  }

  @Override
  public TestTokenRegistration get() {
    return newInstance(contextProvider.get(), stringProvider.get(), pushersManagerProvider.get(), activeSessionHolderProvider.get(), fcmHelperProvider.get());
  }

  public static TestTokenRegistration_Factory create(Provider<FragmentActivity> contextProvider,
      Provider<StringProvider> stringProvider, Provider<PushersManager> pushersManagerProvider,
      Provider<ActiveSessionHolder> activeSessionHolderProvider,
      Provider<FcmHelper> fcmHelperProvider) {
    return new TestTokenRegistration_Factory(contextProvider, stringProvider, pushersManagerProvider, activeSessionHolderProvider, fcmHelperProvider);
  }

  public static TestTokenRegistration newInstance(FragmentActivity context,
      StringProvider stringProvider, PushersManager pushersManager,
      ActiveSessionHolder activeSessionHolder, FcmHelper fcmHelper) {
    return new TestTokenRegistration(context, stringProvider, pushersManager, activeSessionHolder, fcmHelper);
  }
}
