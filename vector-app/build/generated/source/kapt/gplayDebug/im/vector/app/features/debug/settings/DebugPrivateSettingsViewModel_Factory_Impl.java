package im.vector.app.features.debug.settings;

import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DebugPrivateSettingsViewModel_Factory_Impl implements DebugPrivateSettingsViewModel.Factory {
  private final DebugPrivateSettingsViewModel_Factory delegateFactory;

  DebugPrivateSettingsViewModel_Factory_Impl(
      DebugPrivateSettingsViewModel_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public DebugPrivateSettingsViewModel create(DebugPrivateSettingsViewState initialState) {
    return delegateFactory.get(initialState);
  }

  public static Provider<DebugPrivateSettingsViewModel.Factory> create(
      DebugPrivateSettingsViewModel_Factory delegateFactory) {
    return InstanceFactory.create(new DebugPrivateSettingsViewModel_Factory_Impl(delegateFactory));
  }
}
