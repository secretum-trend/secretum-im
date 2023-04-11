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

@ScopeMetadata("javax.inject.Singleton")
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
public final class VectorStaticModule_ProvidesDefaultSharedPreferencesFactory implements Factory<SharedPreferences> {
  private final Provider<Context> contextProvider;

  public VectorStaticModule_ProvidesDefaultSharedPreferencesFactory(
      Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public SharedPreferences get() {
    return providesDefaultSharedPreferences(contextProvider.get());
  }

  public static VectorStaticModule_ProvidesDefaultSharedPreferencesFactory create(
      Provider<Context> contextProvider) {
    return new VectorStaticModule_ProvidesDefaultSharedPreferencesFactory(contextProvider);
  }

  public static SharedPreferences providesDefaultSharedPreferences(Context context) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesDefaultSharedPreferences(context));
  }
}
