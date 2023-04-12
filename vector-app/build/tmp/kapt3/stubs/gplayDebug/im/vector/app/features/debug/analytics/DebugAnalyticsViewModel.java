package im.vector.app.features.debug.analytics;

import com.airbnb.mvrx.MavericksViewModelFactory;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import im.vector.app.core.di.MavericksAssistedViewModelFactory;
import im.vector.app.core.platform.EmptyViewEvents;
import im.vector.app.core.platform.VectorViewModel;
import im.vector.app.features.analytics.store.AnalyticsStore;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\u0018\u0000 \u000e2\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001:\u0002\u000e\u000fB\u0019\b\u0007\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0003H\u0016J\b\u0010\f\u001a\u00020\nH\u0002J\b\u0010\r\u001a\u00020\nH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lim/vector/app/features/debug/analytics/DebugAnalyticsViewModel;", "Lim/vector/app/core/platform/VectorViewModel;", "Lim/vector/app/features/debug/analytics/DebugAnalyticsViewState;", "Lim/vector/app/features/debug/analytics/DebugAnalyticsViewActions;", "Lim/vector/app/core/platform/EmptyViewEvents;", "initialState", "analyticsStore", "Lim/vector/app/features/analytics/store/AnalyticsStore;", "(Lim/vector/app/features/debug/analytics/DebugAnalyticsViewState;Lim/vector/app/features/analytics/store/AnalyticsStore;)V", "handle", "", "action", "handleResetAnalyticsOptInDisplayed", "observerStore", "Companion", "Factory", "Secretum-v1.0.0(1)_gplayDebug"})
public final class DebugAnalyticsViewModel extends im.vector.app.core.platform.VectorViewModel<im.vector.app.features.debug.analytics.DebugAnalyticsViewState, im.vector.app.features.debug.analytics.DebugAnalyticsViewActions, im.vector.app.core.platform.EmptyViewEvents> {
    private final im.vector.app.features.analytics.store.AnalyticsStore analyticsStore = null;
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.features.debug.analytics.DebugAnalyticsViewModel.Companion Companion = null;
    
    @dagger.assisted.AssistedInject()
    public DebugAnalyticsViewModel(@org.jetbrains.annotations.NotNull()
    @dagger.assisted.Assisted()
    im.vector.app.features.debug.analytics.DebugAnalyticsViewState initialState, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.analytics.store.AnalyticsStore analyticsStore) {
        super(null);
    }
    
    private final void observerStore() {
    }
    
    @java.lang.Override()
    public void handle(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.analytics.DebugAnalyticsViewActions action) {
    }
    
    private final void handleResetAnalyticsOptInDisplayed() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0003H&\u00a8\u0006\u0006"}, d2 = {"Lim/vector/app/features/debug/analytics/DebugAnalyticsViewModel$Factory;", "Lim/vector/app/core/di/MavericksAssistedViewModelFactory;", "Lim/vector/app/features/debug/analytics/DebugAnalyticsViewModel;", "Lim/vector/app/features/debug/analytics/DebugAnalyticsViewState;", "create", "initialState", "Secretum-v1.0.0(1)_gplayDebug"})
    @dagger.assisted.AssistedFactory()
    public static abstract interface Factory extends im.vector.app.core.di.MavericksAssistedViewModelFactory<im.vector.app.features.debug.analytics.DebugAnalyticsViewModel, im.vector.app.features.debug.analytics.DebugAnalyticsViewState> {
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public abstract im.vector.app.features.debug.analytics.DebugAnalyticsViewModel create(@org.jetbrains.annotations.NotNull()
        im.vector.app.features.debug.analytics.DebugAnalyticsViewState initialState);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0096\u0001J\u0013\u0010\t\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0096\u0001\u00a8\u0006\n"}, d2 = {"Lim/vector/app/features/debug/analytics/DebugAnalyticsViewModel$Companion;", "Lcom/airbnb/mvrx/MavericksViewModelFactory;", "Lim/vector/app/features/debug/analytics/DebugAnalyticsViewModel;", "Lim/vector/app/features/debug/analytics/DebugAnalyticsViewState;", "()V", "create", "viewModelContext", "Lcom/airbnb/mvrx/ViewModelContext;", "state", "initialState", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class Companion implements com.airbnb.mvrx.MavericksViewModelFactory<im.vector.app.features.debug.analytics.DebugAnalyticsViewModel, im.vector.app.features.debug.analytics.DebugAnalyticsViewState> {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        public im.vector.app.features.debug.analytics.DebugAnalyticsViewModel create(@org.jetbrains.annotations.NotNull()
        com.airbnb.mvrx.ViewModelContext viewModelContext, @org.jetbrains.annotations.NotNull()
        im.vector.app.features.debug.analytics.DebugAnalyticsViewState state) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        public im.vector.app.features.debug.analytics.DebugAnalyticsViewState initialState(@org.jetbrains.annotations.NotNull()
        com.airbnb.mvrx.ViewModelContext viewModelContext) {
            return null;
        }
    }
}