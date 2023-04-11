package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.auth.AuthenticationService;

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
public final class VectorStaticModule_ProvidesAuthenticationServiceFactory implements Factory<AuthenticationService> {
  private final Provider<Matrix> matrixProvider;

  public VectorStaticModule_ProvidesAuthenticationServiceFactory(Provider<Matrix> matrixProvider) {
    this.matrixProvider = matrixProvider;
  }

  @Override
  public AuthenticationService get() {
    return providesAuthenticationService(matrixProvider.get());
  }

  public static VectorStaticModule_ProvidesAuthenticationServiceFactory create(
      Provider<Matrix> matrixProvider) {
    return new VectorStaticModule_ProvidesAuthenticationServiceFactory(matrixProvider);
  }

  public static AuthenticationService providesAuthenticationService(Matrix matrix) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesAuthenticationService(matrix));
  }
}
