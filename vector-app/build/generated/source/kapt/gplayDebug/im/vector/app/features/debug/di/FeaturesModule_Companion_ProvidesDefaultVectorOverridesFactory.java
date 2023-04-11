package im.vector.app.features.debug.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.features.DefaultVectorOverrides;
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
public final class FeaturesModule_Companion_ProvidesDefaultVectorOverridesFactory implements Factory<DefaultVectorOverrides> {
  @Override
  public DefaultVectorOverrides get() {
    return providesDefaultVectorOverrides();
  }

  public static FeaturesModule_Companion_ProvidesDefaultVectorOverridesFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DefaultVectorOverrides providesDefaultVectorOverrides() {
    return Preconditions.checkNotNullFromProvides(FeaturesModule.Companion.providesDefaultVectorOverrides());
  }

  private static final class InstanceHolder {
    private static final FeaturesModule_Companion_ProvidesDefaultVectorOverridesFactory INSTANCE = new FeaturesModule_Companion_ProvidesDefaultVectorOverridesFactory();
  }
}
