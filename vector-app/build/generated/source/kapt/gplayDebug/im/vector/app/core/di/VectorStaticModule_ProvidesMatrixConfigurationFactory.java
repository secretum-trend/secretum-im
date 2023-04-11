package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.debug.FlipperProxy;
import im.vector.app.features.analytics.metrics.VectorPlugins;
import im.vector.app.features.configuration.VectorCustomEventTypesProvider;
import im.vector.app.features.room.VectorRoomDisplayNameFallbackProvider;
import im.vector.app.features.settings.VectorPreferences;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.MatrixConfiguration;

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
public final class VectorStaticModule_ProvidesMatrixConfigurationFactory implements Factory<MatrixConfiguration> {
  private final Provider<VectorPreferences> vectorPreferencesProvider;

  private final Provider<VectorRoomDisplayNameFallbackProvider> vectorRoomDisplayNameFallbackProvider;

  private final Provider<FlipperProxy> flipperProxyProvider;

  private final Provider<VectorPlugins> vectorPluginsProvider;

  private final Provider<VectorCustomEventTypesProvider> vectorCustomEventTypesProvider;

  public VectorStaticModule_ProvidesMatrixConfigurationFactory(
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<VectorRoomDisplayNameFallbackProvider> vectorRoomDisplayNameFallbackProvider,
      Provider<FlipperProxy> flipperProxyProvider, Provider<VectorPlugins> vectorPluginsProvider,
      Provider<VectorCustomEventTypesProvider> vectorCustomEventTypesProvider) {
    this.vectorPreferencesProvider = vectorPreferencesProvider;
    this.vectorRoomDisplayNameFallbackProvider = vectorRoomDisplayNameFallbackProvider;
    this.flipperProxyProvider = flipperProxyProvider;
    this.vectorPluginsProvider = vectorPluginsProvider;
    this.vectorCustomEventTypesProvider = vectorCustomEventTypesProvider;
  }

  @Override
  public MatrixConfiguration get() {
    return providesMatrixConfiguration(vectorPreferencesProvider.get(), vectorRoomDisplayNameFallbackProvider.get(), flipperProxyProvider.get(), vectorPluginsProvider.get(), vectorCustomEventTypesProvider.get());
  }

  public static VectorStaticModule_ProvidesMatrixConfigurationFactory create(
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<VectorRoomDisplayNameFallbackProvider> vectorRoomDisplayNameFallbackProvider,
      Provider<FlipperProxy> flipperProxyProvider, Provider<VectorPlugins> vectorPluginsProvider,
      Provider<VectorCustomEventTypesProvider> vectorCustomEventTypesProvider) {
    return new VectorStaticModule_ProvidesMatrixConfigurationFactory(vectorPreferencesProvider, vectorRoomDisplayNameFallbackProvider, flipperProxyProvider, vectorPluginsProvider, vectorCustomEventTypesProvider);
  }

  public static MatrixConfiguration providesMatrixConfiguration(VectorPreferences vectorPreferences,
      VectorRoomDisplayNameFallbackProvider vectorRoomDisplayNameFallbackProvider,
      FlipperProxy flipperProxy, VectorPlugins vectorPlugins,
      VectorCustomEventTypesProvider vectorCustomEventTypesProvider) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesMatrixConfiguration(vectorPreferences, vectorRoomDisplayNameFallbackProvider, flipperProxy, vectorPlugins, vectorCustomEventTypesProvider));
  }
}
