package im.vector.app.features.debug.leak;

import com.airbnb.mvrx.MavericksViewModelFactory;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import im.vector.app.core.debug.LeakDetector;
import im.vector.app.core.di.MavericksAssistedViewModelFactory;
import im.vector.app.core.platform.EmptyViewEvents;
import im.vector.app.core.platform.VectorViewModel;
import im.vector.app.features.settings.VectorPreferences;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00112\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001:\u0002\u0011\u0012B!\b\u0007\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0016J\u0010\u0010\u000e\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000fH\u0002J\b\u0010\u0010\u001a\u00020\fH\u0002R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel;", "Lim/vector/app/core/platform/VectorViewModel;", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewState;", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewActions;", "Lim/vector/app/core/platform/EmptyViewEvents;", "initialState", "vectorPreferences", "Lim/vector/app/features/settings/VectorPreferences;", "leakDetector", "Lim/vector/app/core/debug/LeakDetector;", "(Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewState;Lim/vector/app/features/settings/VectorPreferences;Lim/vector/app/core/debug/LeakDetector;)V", "handle", "", "action", "handleEnableMemoryLeaksAnalysis", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewActions$EnableMemoryLeaksAnalysis;", "refreshStateFromPreferences", "Companion", "Factory", "Secretum-v1.0.0(1)_gplayDebug"})
public final class DebugMemoryLeaksViewModel extends im.vector.app.core.platform.VectorViewModel<im.vector.app.features.debug.leak.DebugMemoryLeaksViewState, im.vector.app.features.debug.leak.DebugMemoryLeaksViewActions, im.vector.app.core.platform.EmptyViewEvents> {
    private final im.vector.app.features.settings.VectorPreferences vectorPreferences = null;
    private final im.vector.app.core.debug.LeakDetector leakDetector = null;
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel.Companion Companion = null;
    
    @dagger.assisted.AssistedInject()
    public DebugMemoryLeaksViewModel(@org.jetbrains.annotations.NotNull()
    @dagger.assisted.Assisted()
    im.vector.app.features.debug.leak.DebugMemoryLeaksViewState initialState, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.VectorPreferences vectorPreferences, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.debug.LeakDetector leakDetector) {
        super(null);
    }
    
    @java.lang.Override()
    public void handle(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.leak.DebugMemoryLeaksViewActions action) {
    }
    
    private final void handleEnableMemoryLeaksAnalysis(im.vector.app.features.debug.leak.DebugMemoryLeaksViewActions.EnableMemoryLeaksAnalysis action) {
    }
    
    private final void refreshStateFromPreferences() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0003H&\u00a8\u0006\u0006"}, d2 = {"Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel$Factory;", "Lim/vector/app/core/di/MavericksAssistedViewModelFactory;", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel;", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewState;", "create", "initialState", "Secretum-v1.0.0(1)_gplayDebug"})
    @dagger.assisted.AssistedFactory()
    public static abstract interface Factory extends im.vector.app.core.di.MavericksAssistedViewModelFactory<im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel, im.vector.app.features.debug.leak.DebugMemoryLeaksViewState> {
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public abstract im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel create(@org.jetbrains.annotations.NotNull()
        im.vector.app.features.debug.leak.DebugMemoryLeaksViewState initialState);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0096\u0001J\u0013\u0010\t\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0096\u0001\u00a8\u0006\n"}, d2 = {"Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel$Companion;", "Lcom/airbnb/mvrx/MavericksViewModelFactory;", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel;", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewState;", "()V", "create", "viewModelContext", "Lcom/airbnb/mvrx/ViewModelContext;", "state", "initialState", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class Companion implements com.airbnb.mvrx.MavericksViewModelFactory<im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel, im.vector.app.features.debug.leak.DebugMemoryLeaksViewState> {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        public im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel create(@org.jetbrains.annotations.NotNull()
        com.airbnb.mvrx.ViewModelContext viewModelContext, @org.jetbrains.annotations.NotNull()
        im.vector.app.features.debug.leak.DebugMemoryLeaksViewState state) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        public im.vector.app.features.debug.leak.DebugMemoryLeaksViewState initialState(@org.jetbrains.annotations.NotNull()
        com.airbnb.mvrx.ViewModelContext viewModelContext) {
            return null;
        }
    }
}