package im.vector.app.gplay.features.settings.troubleshoot;

import androidx.fragment.app.FragmentActivity;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.pushers.FcmHelper;
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
public final class TestFirebaseToken_Factory implements Factory<TestFirebaseToken> {
  private final Provider<FragmentActivity> contextProvider;

  private final Provider<StringProvider> stringProvider;

  private final Provider<FcmHelper> fcmHelperProvider;

  public TestFirebaseToken_Factory(Provider<FragmentActivity> contextProvider,
      Provider<StringProvider> stringProvider, Provider<FcmHelper> fcmHelperProvider) {
    this.contextProvider = contextProvider;
    this.stringProvider = stringProvider;
    this.fcmHelperProvider = fcmHelperProvider;
  }

  @Override
  public TestFirebaseToken get() {
    return newInstance(contextProvider.get(), stringProvider.get(), fcmHelperProvider.get());
  }

  public static TestFirebaseToken_Factory create(Provider<FragmentActivity> contextProvider,
      Provider<StringProvider> stringProvider, Provider<FcmHelper> fcmHelperProvider) {
    return new TestFirebaseToken_Factory(contextProvider, stringProvider, fcmHelperProvider);
  }

  public static TestFirebaseToken newInstance(FragmentActivity context,
      StringProvider stringProvider, FcmHelper fcmHelper) {
    return new TestFirebaseToken(context, stringProvider, fcmHelper);
  }
}
