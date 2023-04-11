package im.vector.app.features.debug.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.features.DefaultVectorFeatures;
import javax.annotation.processing.Generated;

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
public final class FeaturesModule_Companion_ProvidesDefaultVectorFeaturesFactory implements Factory<DefaultVectorFeatures> {
  @Override
  public DefaultVectorFeatures get() {
    return providesDefaultVectorFeatures();
  }

  public static FeaturesModule_Companion_ProvidesDefaultVectorFeaturesFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DefaultVectorFeatures providesDefaultVectorFeatures() {
    return Preconditions.checkNotNullFromProvides(FeaturesModule.Companion.providesDefaultVectorFeatures());
  }

  private static final class InstanceHolder {
    private static final FeaturesModule_Companion_ProvidesDefaultVectorFeaturesFactory INSTANCE = new FeaturesModule_Companion_ProvidesDefaultVectorFeaturesFactory();
  }
}
