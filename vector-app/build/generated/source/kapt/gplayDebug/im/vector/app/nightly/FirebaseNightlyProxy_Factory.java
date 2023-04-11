package im.vector.app.nightly;

import android.content.SharedPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.resources.BuildMeta;
import im.vector.app.core.time.Clock;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("im.vector.app.core.di.DefaultPreferences")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class FirebaseNightlyProxy_Factory implements Factory<FirebaseNightlyProxy> {
  private final Provider<Clock> clockProvider;

  private final Provider<SharedPreferences> sharedPreferencesProvider;

  private final Provider<BuildMeta> buildMetaProvider;

  public FirebaseNightlyProxy_Factory(Provider<Clock> clockProvider,
      Provider<SharedPreferences> sharedPreferencesProvider,
      Provider<BuildMeta> buildMetaProvider) {
    this.clockProvider = clockProvider;
    this.sharedPreferencesProvider = sharedPreferencesProvider;
    this.buildMetaProvider = buildMetaProvider;
  }

  @Override
  public FirebaseNightlyProxy get() {
    return newInstance(clockProvider.get(), sharedPreferencesProvider.get(), buildMetaProvider.get());
  }

  public static FirebaseNightlyProxy_Factory create(Provider<Clock> clockProvider,
      Provider<SharedPreferences> sharedPreferencesProvider,
      Provider<BuildMeta> buildMetaProvider) {
    return new FirebaseNightlyProxy_Factory(clockProvider, sharedPreferencesProvider, buildMetaProvider);
  }

  public static FirebaseNightlyProxy newInstance(Clock clock, SharedPreferences sharedPreferences,
      BuildMeta buildMeta) {
    return new FirebaseNightlyProxy(clock, sharedPreferences, buildMeta);
  }
}
