package im.vector.app.push.fcm;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata({
    "dagger.hilt.android.qualifiers.ApplicationContext",
    "im.vector.app.core.di.DefaultPreferences"
})
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class GoogleFcmHelper_Factory implements Factory<GoogleFcmHelper> {
  private final Provider<Context> contextProvider;

  private final Provider<SharedPreferences> sharedPrefsProvider;

  public GoogleFcmHelper_Factory(Provider<Context> contextProvider,
      Provider<SharedPreferences> sharedPrefsProvider) {
    this.contextProvider = contextProvider;
    this.sharedPrefsProvider = sharedPrefsProvider;
  }

  @Override
  public GoogleFcmHelper get() {
    return newInstance(contextProvider.get(), sharedPrefsProvider.get());
  }

  public static GoogleFcmHelper_Factory create(Provider<Context> contextProvider,
      Provider<SharedPreferences> sharedPrefsProvider) {
    return new GoogleFcmHelper_Factory(contextProvider, sharedPrefsProvider);
  }

  public static GoogleFcmHelper newInstance(Context context, SharedPreferences sharedPrefs) {
    return new GoogleFcmHelper(context, sharedPrefs);
  }
}
