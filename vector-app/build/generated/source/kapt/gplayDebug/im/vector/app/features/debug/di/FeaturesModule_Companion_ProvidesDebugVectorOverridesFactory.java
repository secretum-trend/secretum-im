package im.vector.app.features.debug.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.features.debug.features.DebugVectorOverrides;
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
public final class FeaturesModule_Companion_ProvidesDebugVectorOverridesFactory implements Factory<DebugVectorOverrides> {
  private final Provider<Context> contextProvider;

  public FeaturesModule_Companion_ProvidesDebugVectorOverridesFactory(
      Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public DebugVectorOverrides get() {
    return providesDebugVectorOverrides(contextProvider.get());
  }

  public static FeaturesModule_Companion_ProvidesDebugVectorOverridesFactory create(
      Provider<Context> contextProvider) {
    return new FeaturesModule_Companion_ProvidesDebugVectorOverridesFactory(contextProvider);
  }

  public static DebugVectorOverrides providesDebugVectorOverrides(Context context) {
    return Preconditions.checkNotNullFromProvides(FeaturesModule.Companion.providesDebugVectorOverrides(context));
  }
}
