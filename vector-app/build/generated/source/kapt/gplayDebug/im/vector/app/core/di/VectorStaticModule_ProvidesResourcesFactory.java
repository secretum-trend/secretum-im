package im.vector.app.core.di;

import android.content.Context;
import android.content.res.Resources;
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
public final class VectorStaticModule_ProvidesResourcesFactory implements Factory<Resources> {
  private final Provider<Context> contextProvider;

  public VectorStaticModule_ProvidesResourcesFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public Resources get() {
    return providesResources(contextProvider.get());
  }

  public static VectorStaticModule_ProvidesResourcesFactory create(
      Provider<Context> contextProvider) {
    return new VectorStaticModule_ProvidesResourcesFactory(contextProvider);
  }

  public static Resources providesResources(Context context) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesResources(context));
  }
}
