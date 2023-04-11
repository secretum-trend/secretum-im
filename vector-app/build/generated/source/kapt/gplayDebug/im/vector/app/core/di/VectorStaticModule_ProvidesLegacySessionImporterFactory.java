package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.legacy.LegacySessionImporter;

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
public final class VectorStaticModule_ProvidesLegacySessionImporterFactory implements Factory<LegacySessionImporter> {
  private final Provider<Matrix> matrixProvider;

  public VectorStaticModule_ProvidesLegacySessionImporterFactory(Provider<Matrix> matrixProvider) {
    this.matrixProvider = matrixProvider;
  }

  @Override
  public LegacySessionImporter get() {
    return providesLegacySessionImporter(matrixProvider.get());
  }

  public static VectorStaticModule_ProvidesLegacySessionImporterFactory create(
      Provider<Matrix> matrixProvider) {
    return new VectorStaticModule_ProvidesLegacySessionImporterFactory(matrixProvider);
  }

  public static LegacySessionImporter providesLegacySessionImporter(Matrix matrix) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesLegacySessionImporter(matrix));
  }
}
