package im.vector.app;

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
public final class GoogleFlavorLegals_Factory implements Factory<GoogleFlavorLegals> {
  @Override
  public GoogleFlavorLegals get() {
    return newInstance();
  }

  public static GoogleFlavorLegals_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GoogleFlavorLegals newInstance() {
    return new GoogleFlavorLegals();
  }

  private static final class InstanceHolder {
    private static final GoogleFlavorLegals_Factory INSTANCE = new GoogleFlavorLegals_Factory();
  }
}
