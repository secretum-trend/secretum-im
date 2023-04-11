package im.vector.app.gplay.features.settings.troubleshoot;

import androidx.fragment.app.FragmentActivity;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class TestPlayServices_Factory implements Factory<TestPlayServices> {
  private final Provider<FragmentActivity> contextProvider;

  private final Provider<StringProvider> stringProvider;

  public TestPlayServices_Factory(Provider<FragmentActivity> contextProvider,
      Provider<StringProvider> stringProvider) {
    this.contextProvider = contextProvider;
    this.stringProvider = stringProvider;
  }

  @Override
  public TestPlayServices get() {
    return newInstance(contextProvider.get(), stringProvider.get());
  }

  public static TestPlayServices_Factory create(Provider<FragmentActivity> contextProvider,
      Provider<StringProvider> stringProvider) {
    return new TestPlayServices_Factory(contextProvider, stringProvider);
  }

  public static TestPlayServices newInstance(FragmentActivity context,
      StringProvider stringProvider) {
    return new TestPlayServices(context, stringProvider);
  }
}
