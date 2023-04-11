package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.auth.HomeServerHistoryService;

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
public final class VectorStaticModule_ProvidesHomeServerHistoryServiceFactory implements Factory<HomeServerHistoryService> {
  private final Provider<Matrix> matrixProvider;

  public VectorStaticModule_ProvidesHomeServerHistoryServiceFactory(
      Provider<Matrix> matrixProvider) {
    this.matrixProvider = matrixProvider;
  }

  @Override
  public HomeServerHistoryService get() {
    return providesHomeServerHistoryService(matrixProvider.get());
  }

  public static VectorStaticModule_ProvidesHomeServerHistoryServiceFactory create(
      Provider<Matrix> matrixProvider) {
    return new VectorStaticModule_ProvidesHomeServerHistoryServiceFactory(matrixProvider);
  }

  public static HomeServerHistoryService providesHomeServerHistoryService(Matrix matrix) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesHomeServerHistoryService(matrix));
  }
}
