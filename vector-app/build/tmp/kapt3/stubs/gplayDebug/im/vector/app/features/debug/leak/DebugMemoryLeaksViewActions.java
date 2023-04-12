package im.vector.app.features.debug.leak;

import im.vector.app.core.platform.VectorViewModelAction;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0010\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0001\u0002\u0082\u0001\u0001\u0003\u00a8\u0006\u0004"}, d2 = {"Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewActions;", "Lim/vector/app/core/platform/VectorViewModelAction;", "EnableMemoryLeaksAnalysis", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewActions$EnableMemoryLeaksAnalysis;", "Secretum-v1.0.0(1)_gplayDebug"})
public abstract interface DebugMemoryLeaksViewActions extends im.vector.app.core.platform.VectorViewModelAction {
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0006\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\u0007\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\b\u001a\u00020\u00032\b\u0010\t\u001a\u0004\u0018\u00010\nH\u00d6\u0003J\t\u0010\u000b\u001a\u00020\fH\u00d6\u0001J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0005\u00a8\u0006\u000f"}, d2 = {"Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewActions$EnableMemoryLeaksAnalysis;", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewActions;", "isEnabled", "", "(Z)V", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class EnableMemoryLeaksAnalysis implements im.vector.app.features.debug.leak.DebugMemoryLeaksViewActions {
        private final boolean isEnabled = false;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.leak.DebugMemoryLeaksViewActions.EnableMemoryLeaksAnalysis copy(boolean isEnabled) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public EnableMemoryLeaksAnalysis(boolean isEnabled) {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final boolean isEnabled() {
            return false;
        }
    }
}