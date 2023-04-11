package im.vector.app.features.debug.analytics;

import dagger.internal.DaggerGenerated;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.features.analytics.store.AnalyticsStore;
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
public final class DebugAnalyticsViewModel_Factory {
  private final Provider<AnalyticsStore> analyticsStoreProvider;

  public DebugAnalyticsViewModel_Factory(Provider<AnalyticsStore> analyticsStoreProvider) {
    this.analyticsStoreProvider = analyticsStoreProvider;
  }

  public DebugAnalyticsViewModel get(DebugAnalyticsViewState initialState) {
    return newInstance(initialState, analyticsStoreProvider.get());
  }

  public static DebugAnalyticsViewModel_Factory create(
      Provider<AnalyticsStore> analyticsStoreProvider) {
    return new DebugAnalyticsViewModel_Factory(analyticsStoreProvider);
  }

  public static DebugAnalyticsViewModel newInstance(DebugAnalyticsViewState initialState,
      AnalyticsStore analyticsStore) {
    return new DebugAnalyticsViewModel(initialState, analyticsStore);
  }
}
