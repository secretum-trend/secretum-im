package im.vector.app.flipper;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class VectorFlipperProxy_Factory implements Factory<VectorFlipperProxy> {
  private final Provider<Context> contextProvider;

  public VectorFlipperProxy_Factory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public VectorFlipperProxy get() {
    return newInstance(contextProvider.get());
  }

  public static VectorFlipperProxy_Factory create(Provider<Context> contextProvider) {
    return new VectorFlipperProxy_Factory(contextProvider);
  }

  public static VectorFlipperProxy newInstance(Context context) {
    return new VectorFlipperProxy(context);
  }
}
