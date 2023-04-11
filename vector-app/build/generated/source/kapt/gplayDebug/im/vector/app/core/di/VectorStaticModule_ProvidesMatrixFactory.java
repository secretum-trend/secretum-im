package im.vector.app.core.di;

import android.content.Context;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.MatrixConfiguration;

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
public final class VectorStaticModule_ProvidesMatrixFactory implements Factory<Matrix> {
  private final Provider<Context> contextProvider;

  private final Provider<MatrixConfiguration> configurationProvider;

  public VectorStaticModule_ProvidesMatrixFactory(Provider<Context> contextProvider,
      Provider<MatrixConfiguration> configurationProvider) {
    this.contextProvider = contextProvider;
    this.configurationProvider = configurationProvider;
  }

  @Override
  public Matrix get() {
    return providesMatrix(contextProvider.get(), configurationProvider.get());
  }

  public static VectorStaticModule_ProvidesMatrixFactory create(Provider<Context> contextProvider,
      Provider<MatrixConfiguration> configurationProvider) {
    return new VectorStaticModule_ProvidesMatrixFactory(contextProvider, configurationProvider);
  }

  public static Matrix providesMatrix(Context context, MatrixConfiguration configuration) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesMatrix(context, configuration));
  }
}
