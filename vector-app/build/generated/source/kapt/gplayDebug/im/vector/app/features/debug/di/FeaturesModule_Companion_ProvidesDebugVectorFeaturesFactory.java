package im.vector.app.features.debug.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.features.DefaultVectorFeatures;
import im.vector.app.features.debug.features.DebugVectorFeatures;
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
public final class FeaturesModule_Companion_ProvidesDebugVectorFeaturesFactory implements Factory<DebugVectorFeatures> {
  private final Provider<Context> contextProvider;

  private final Provider<DefaultVectorFeatures> defaultVectorFeaturesProvider;

  public FeaturesModule_Companion_ProvidesDebugVectorFeaturesFactory(
      Provider<Context> contextProvider,
      Provider<DefaultVectorFeatures> defaultVectorFeaturesProvider) {
    this.contextProvider = contextProvider;
    this.defaultVectorFeaturesProvider = defaultVectorFeaturesProvider;
  }

  @Override
  public DebugVectorFeatures get() {
    return providesDebugVectorFeatures(contextProvider.get(), defaultVectorFeaturesProvider.get());
  }

  public static FeaturesModule_Companion_ProvidesDebugVectorFeaturesFactory create(
      Provider<Context> contextProvider,
      Provider<DefaultVectorFeatures> defaultVectorFeaturesProvider) {
    return new FeaturesModule_Companion_ProvidesDebugVectorFeaturesFactory(contextProvider, defaultVectorFeaturesProvider);
  }

  public static DebugVectorFeatures providesDebugVectorFeatures(Context context,
      DefaultVectorFeatures defaultVectorFeatures) {
    return Preconditions.checkNotNullFromProvides(FeaturesModule.Companion.providesDebugVectorFeatures(context, defaultVectorFeatures));
  }
}
