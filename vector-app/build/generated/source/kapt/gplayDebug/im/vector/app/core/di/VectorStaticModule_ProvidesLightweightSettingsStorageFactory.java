package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.settings.LightweightSettingsStorage;

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
public final class VectorStaticModule_ProvidesLightweightSettingsStorageFactory implements Factory<LightweightSettingsStorage> {
  private final Provider<Matrix> matrixProvider;

  public VectorStaticModule_ProvidesLightweightSettingsStorageFactory(
      Provider<Matrix> matrixProvider) {
    this.matrixProvider = matrixProvider;
  }

  @Override
  public LightweightSettingsStorage get() {
    return providesLightweightSettingsStorage(matrixProvider.get());
  }

  public static VectorStaticModule_ProvidesLightweightSettingsStorageFactory create(
      Provider<Matrix> matrixProvider) {
    return new VectorStaticModule_ProvidesLightweightSettingsStorageFactory(matrixProvider);
  }

  public static LightweightSettingsStorage providesLightweightSettingsStorage(Matrix matrix) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesLightweightSettingsStorage(matrix));
  }
}
