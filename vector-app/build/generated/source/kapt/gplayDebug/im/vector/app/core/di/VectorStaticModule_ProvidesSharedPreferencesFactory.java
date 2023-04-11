package im.vector.app.core.di;

import android.content.Context;
import android.content.SharedPreferences;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class VectorStaticModule_ProvidesSharedPreferencesFactory implements Factory<SharedPreferences> {
  private final Provider<Context> contextProvider;

  public VectorStaticModule_ProvidesSharedPreferencesFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SharedPreferences get() {
    return providesSharedPreferences(contextProvider.get());
  }

  public static VectorStaticModule_ProvidesSharedPreferencesFactory create(
      Provider<Context> contextProvider) {
    return new VectorStaticModule_ProvidesSharedPreferencesFactory(contextProvider);
  }

  public static SharedPreferences providesSharedPreferences(Context context) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesSharedPreferences(context));
  }
}
