package im.vector.app.features.debug.settings;

import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.features.debug.features.DebugVectorOverrides;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesPreferencesStore;
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
public final class DebugPrivateSettingsViewModel_Factory {
  private final Provider<DebugVectorOverrides> debugVectorOverridesProvider;

  private final Provider<ReleaseNotesPreferencesStore> releaseNotesPreferencesStoreProvider;

  public DebugPrivateSettingsViewModel_Factory(
      Provider<DebugVectorOverrides> debugVectorOverridesProvider,
      Provider<ReleaseNotesPreferencesStore> releaseNotesPreferencesStoreProvider) {
    this.debugVectorOverridesProvider = debugVectorOverridesProvider;
    this.releaseNotesPreferencesStoreProvider = releaseNotesPreferencesStoreProvider;
  }

  public DebugPrivateSettingsViewModel get(DebugPrivateSettingsViewState initialState) {
    return newInstance(initialState, debugVectorOverridesProvider.get(), releaseNotesPreferencesStoreProvider.get());
  }

  public static DebugPrivateSettingsViewModel_Factory create(
      Provider<DebugVectorOverrides> debugVectorOverridesProvider,
      Provider<ReleaseNotesPreferencesStore> releaseNotesPreferencesStoreProvider) {
    return new DebugPrivateSettingsViewModel_Factory(debugVectorOverridesProvider, releaseNotesPreferencesStoreProvider);
  }

  public static DebugPrivateSettingsViewModel newInstance(
      DebugPrivateSettingsViewState initialState, DebugVectorOverrides debugVectorOverrides,
      ReleaseNotesPreferencesStore releaseNotesPreferencesStore) {
    return new DebugPrivateSettingsViewModel(initialState, debugVectorOverrides, releaseNotesPreferencesStore);
  }
}
