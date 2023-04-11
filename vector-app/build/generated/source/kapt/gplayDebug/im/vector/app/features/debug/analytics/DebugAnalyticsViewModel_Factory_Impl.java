package im.vector.app.features.debug.analytics;

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
public final class DebugAnalyticsViewModel_Factory_Impl implements DebugAnalyticsViewModel.Factory {
  private final DebugAnalyticsViewModel_Factory delegateFactory;

  DebugAnalyticsViewModel_Factory_Impl(DebugAnalyticsViewModel_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public DebugAnalyticsViewModel create(DebugAnalyticsViewState initialState) {
    return delegateFactory.get(initialState);
  }

  public static Provider<DebugAnalyticsViewModel.Factory> create(
      DebugAnalyticsViewModel_Factory delegateFactory) {
    return InstanceFactory.create(new DebugAnalyticsViewModel_Factory_Impl(delegateFactory));
  }
}
