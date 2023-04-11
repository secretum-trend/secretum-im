package im.vector.app.features.debug.features;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class FeaturesController_Factory implements Factory<FeaturesController> {
  @Override
  public FeaturesController get() {
    return newInstance();
  }

  public static FeaturesController_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static FeaturesController newInstance() {
    return new FeaturesController();
  }

  private static final class InstanceHolder {
    private static final FeaturesController_Factory INSTANCE = new FeaturesController_Factory();
  }
}
