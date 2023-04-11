package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.raw.RawService;

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
public final class VectorStaticModule_ProvidesRawServiceFactory implements Factory<RawService> {
  private final Provider<Matrix> matrixProvider;

  public VectorStaticModule_ProvidesRawServiceFactory(Provider<Matrix> matrixProvider) {
    this.matrixProvider = matrixProvider;
  }

  @Override
  public RawService get() {
    return providesRawService(matrixProvider.get());
  }

  public static VectorStaticModule_ProvidesRawServiceFactory create(
      Provider<Matrix> matrixProvider) {
    return new VectorStaticModule_ProvidesRawServiceFactory(matrixProvider);
  }

  public static RawService providesRawService(Matrix matrix) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesRawService(matrix));
  }
}
