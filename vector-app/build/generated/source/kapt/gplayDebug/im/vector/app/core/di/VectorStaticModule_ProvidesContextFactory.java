package im.vector.app.core.di;

import android.app.Application;
import android.content.Context;
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
public final class VectorStaticModule_ProvidesContextFactory implements Factory<Context> {
  private final Provider<Application> applicationProvider;

  public VectorStaticModule_ProvidesContextFactory(Provider<Application> applicationProvider) {
    this.applicationProvider = applicationProvider;
  }

  @Override
  public Context get() {
    return providesContext(applicationProvider.get());
  }

  public static VectorStaticModule_ProvidesContextFactory create(
      Provider<Application> applicationProvider) {
    return new VectorStaticModule_ProvidesContextFactory(applicationProvider);
  }

  public static Context providesContext(Application application) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesContext(application));
  }
}
