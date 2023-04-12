package im.vector.app.features.debug.settings;

import im.vector.app.core.platform.VectorViewModelAction;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0005\u0002\u0003\u0004\u0005\u0006\u0082\u0001\u0005\u0007\b\t\n\u000b\u00a8\u0006\f"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions;", "Lim/vector/app/core/platform/VectorViewModelAction;", "ResetReleaseNotesActivityHasBeenDisplayed", "SetAvatarCapabilityOverride", "SetDialPadVisibility", "SetDisplayNameCapabilityOverride", "SetForceLoginFallbackEnabled", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$ResetReleaseNotesActivityHasBeenDisplayed;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetAvatarCapabilityOverride;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetDialPadVisibility;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetDisplayNameCapabilityOverride;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetForceLoginFallbackEnabled;", "Secretum-v1.0.0(1)_gplayDebug"})
public abstract interface DebugPrivateSettingsViewActions extends im.vector.app.core.platform.VectorViewModelAction {
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00d6\u0003J\t\u0010\f\u001a\u00020\rH\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetDialPadVisibility;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions;", "force", "", "(Z)V", "getForce", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class SetDialPadVisibility implements im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions {
        private final boolean force = false;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetDialPadVisibility copy(boolean force) {
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
        
        public SetDialPadVisibility(boolean force) {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final boolean getForce() {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003H\u00c6\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\u00032\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u00d6\u0003J\t\u0010\f\u001a\u00020\rH\u00d6\u0001J\t\u0010\u000e\u001a\u00020\u000fH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0010"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetForceLoginFallbackEnabled;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions;", "force", "", "(Z)V", "getForce", "()Z", "component1", "copy", "equals", "other", "", "hashCode", "", "toString", "", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class SetForceLoginFallbackEnabled implements im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions {
        private final boolean force = false;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetForceLoginFallbackEnabled copy(boolean force) {
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
        
        public SetForceLoginFallbackEnabled(boolean force) {
            super();
        }
        
        public final boolean component1() {
            return false;
        }
        
        public final boolean getForce() {
            return false;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetDisplayNameCapabilityOverride;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions;", "option", "Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "(Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;)V", "getOption", "()Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class SetDisplayNameCapabilityOverride implements im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions {
        @org.jetbrains.annotations.Nullable()
        private final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride option = null;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetDisplayNameCapabilityOverride copy(@org.jetbrains.annotations.Nullable()
        im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride option) {
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
        
        public SetDisplayNameCapabilityOverride(@org.jetbrains.annotations.Nullable()
        im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride option) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride getOption() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\u0002\u0010\u0004J\u000b\u0010\u0007\u001a\u0004\u0018\u00010\u0003H\u00c6\u0003J\u0015\u0010\b\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003H\u00c6\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fH\u00d6\u0003J\t\u0010\r\u001a\u00020\u000eH\u00d6\u0001J\t\u0010\u000f\u001a\u00020\u0010H\u00d6\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u00a8\u0006\u0011"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetAvatarCapabilityOverride;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions;", "option", "Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "(Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;)V", "getOption", "()Lim/vector/app/features/debug/settings/BooleanHomeserverCapabilitiesOverride;", "component1", "copy", "equals", "", "other", "", "hashCode", "", "toString", "", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class SetAvatarCapabilityOverride implements im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions {
        @org.jetbrains.annotations.Nullable()
        private final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride option = null;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetAvatarCapabilityOverride copy(@org.jetbrains.annotations.Nullable()
        im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride option) {
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
        
        public SetAvatarCapabilityOverride(@org.jetbrains.annotations.Nullable()
        im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride option) {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        public final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride component1() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final im.vector.app.features.debug.settings.BooleanHomeserverCapabilitiesOverride getOption() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002\u00a8\u0006\u0003"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$ResetReleaseNotesActivityHasBeenDisplayed;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions;", "()V", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class ResetReleaseNotesActivityHasBeenDisplayed implements im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions {
        @org.jetbrains.annotations.NotNull()
        public static final im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.ResetReleaseNotesActivityHasBeenDisplayed INSTANCE = null;
        
        private ResetReleaseNotesActivityHasBeenDisplayed() {
            super();
        }
    }
}