package im.vector.app.receivers;

import android.content.SharedPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class VectorDebugReceiver_Factory implements Factory<VectorDebugReceiver> {
  private final Provider<SharedPreferences> sharedPreferencesProvider;

  public VectorDebugReceiver_Factory(Provider<SharedPreferences> sharedPreferencesProvider) {
    this.sharedPreferencesProvider = sharedPreferencesProvider;
  }

  @Override
  public VectorDebugReceiver get() {
    return newInstance(sharedPreferencesProvider.get());
  }

  public static VectorDebugReceiver_Factory create(
      Provider<SharedPreferences> sharedPreferencesProvider) {
    return new VectorDebugReceiver_Factory(sharedPreferencesProvider);
  }

  public static VectorDebugReceiver newInstance(SharedPreferences sharedPreferences) {
    return new VectorDebugReceiver(sharedPreferences);
  }
}
