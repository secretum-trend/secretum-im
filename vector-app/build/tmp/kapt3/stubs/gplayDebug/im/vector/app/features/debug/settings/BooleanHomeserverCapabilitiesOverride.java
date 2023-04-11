package im.vector.app.features.debug.settings;

import java.lang.System;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u0000 \u00022\u00020\u0001:\u0003\u0002\u0003\u0004\u0082\u0001\u0002\u0005\u0006\u00a8\u0006\u0007"}, d2 = {"Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "Lim/vector/app/features/debug/settings/OverrideOption;", "Companion", "ForceDisabled", "ForceEnabled", "Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride$ForceDisabled;", "Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride$ForceEnabled;", "vector-app_gplayDebug"})
public abstract interface BooleanHomeserverCapabilitiesOverride extends im.vector.app.features.debug.settings.OverrideOption {
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride.Companion Companion = null;
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride$ForceEnabled;", "Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "()V", "label", "", "getLabel", "()Ljava/lang/String;", "vector-app_gplayDebug"})
    public static final class ForceEnabled implements im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride {
        @org.jetbrains.annotations.NotNull()
        public static final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride.ForceEnabled INSTANCE = null;
        @org.jetbrains.annotations.NotNull()
        private static final java.lang.String label = "Force enabled";
        
        private ForceEnabled() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String getLabel() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride$ForceDisabled;", "Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "()V", "label", "", "getLabel", "()Ljava/lang/String;", "vector-app_gplayDebug"})
    public static final class ForceDisabled implements im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride {
        @org.jetbrains.annotations.NotNull()
        public static final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride.ForceDisabled INSTANCE = null;
        @org.jetbrains.annotations.NotNull()
        private static final java.lang.String label = "Force disabled";
        
        private ForceDisabled() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String getLabel() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0017\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0007\u00a8\u0006\b"}, d2 = {"Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride$Companion;", "", "()V", "from", "Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "value", "", "(Ljava/lang/Boolean;)Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "vector-app_gplayDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride from(@org.jetbrains.annotations.Nullable()
        java.lang.Boolean value) {
            return null;
        }
    }
}