package im.vector.app.features.debug.leak;

import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.debug.LeakDetector;
import im.vector.app.features.settings.VectorPreferences;
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
public final class DebugMemoryLeaksViewModel_Factory {
  private final Provider<VectorPreferences> vectorPreferencesProvider;

  private final Provider<LeakDetector> leakDetectorProvider;

  public DebugMemoryLeaksViewModel_Factory(Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<LeakDetector> leakDetectorProvider) {
    this.vectorPreferencesProvider = vectorPreferencesProvider;
    this.leakDetectorProvider = leakDetectorProvider;
  }

  public DebugMemoryLeaksViewModel get(DebugMemoryLeaksViewState initialState) {
    return newInstance(initialState, vectorPreferencesProvider.get(), leakDetectorProvider.get());
  }

  public static DebugMemoryLeaksViewModel_Factory create(
      Provider<VectorPreferences> vectorPreferencesProvider,
      Provider<LeakDetector> leakDetectorProvider) {
    return new DebugMemoryLeaksViewModel_Factory(vectorPreferencesProvider, leakDetectorProvider);
  }

  public static DebugMemoryLeaksViewModel newInstance(DebugMemoryLeaksViewState initialState,
      VectorPreferences vectorPreferences, LeakDetector leakDetector) {
    return new DebugMemoryLeaksViewModel(initialState, vectorPreferences, leakDetector);
  }
}
