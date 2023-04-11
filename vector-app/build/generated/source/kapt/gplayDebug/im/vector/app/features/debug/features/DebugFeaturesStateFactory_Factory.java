package im.vector.app.features.debug.features;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.features.DefaultVectorFeatures;
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
public final class DebugFeaturesStateFactory_Factory implements Factory<DebugFeaturesStateFactory> {
  private final Provider<DebugVectorFeatures> debugFeaturesProvider;

  private final Provider<DefaultVectorFeatures> defaultFeaturesProvider;

  public DebugFeaturesStateFactory_Factory(Provider<DebugVectorFeatures> debugFeaturesProvider,
      Provider<DefaultVectorFeatures> defaultFeaturesProvider) {
    this.debugFeaturesProvider = debugFeaturesProvider;
    this.defaultFeaturesProvider = defaultFeaturesProvider;
  }

  @Override
  public DebugFeaturesStateFactory get() {
    return newInstance(debugFeaturesProvider.get(), defaultFeaturesProvider.get());
  }

  public static DebugFeaturesStateFactory_Factory create(
      Provider<DebugVectorFeatures> debugFeaturesProvider,
      Provider<DefaultVectorFeatures> defaultFeaturesProvider) {
    return new DebugFeaturesStateFactory_Factory(debugFeaturesProvider, defaultFeaturesProvider);
  }

  public static DebugFeaturesStateFactory newInstance(DebugVectorFeatures debugFeatures,
      DefaultVectorFeatures defaultFeatures) {
    return new DebugFeaturesStateFactory(debugFeatures, defaultFeatures);
  }
}
