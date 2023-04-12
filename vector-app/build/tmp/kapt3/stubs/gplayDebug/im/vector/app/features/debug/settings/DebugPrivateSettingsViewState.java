package im.vector.app.features.debug.settings;

import com.airbnb.mvrx.MavericksState;
import im.vector.app.features.debug.settings.OverrideDropdownView.OverrideDropdown;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0010\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0011\u001a\u00020\u0006H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003J1\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\u0003H\u00c6\u0001J\u0013\u0010\u0014\u001a\u00020\u00032\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0018H\u00d6\u0001J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0007\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n\u00a8\u0006\u001b"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewState;", "Lcom/airbnb/mvrx/MavericksState;", "dialPadVisible", "", "forceLoginFallback", "homeserverCapabilityOverrides", "Lim/vector/app/features/debug/settings/HomeserverCapabilityOverrides;", "releaseNotesActivityHasBeenDisplayed", "(ZZLim/vector/app/features/debug/settings/HomeserverCapabilityOverrides;Z)V", "getDialPadVisible", "()Z", "getForceLoginFallback", "getHomeserverCapabilityOverrides", "()Lim/vector/app/features/debug/settings/HomeserverCapabilityOverrides;", "getReleaseNotesActivityHasBeenDisplayed", "component1", "component2", "component3", "component4", "copy", "equals", "other", "", "hashCode", "", "toString", "", "Secretum-v1.0.0(1)_gplayDebug"})
public final class DebugPrivateSettingsViewState implements com.airbnb.mvrx.MavericksState {
    private final boolean dialPadVisible = false;
    private final boolean forceLoginFallback = false;
    @org.jetbrains.annotations.NotNull()
    private final im.vector.app.features.debug.settings.HomeserverCapabilityOverrides homeserverCapabilityOverrides = null;
    private final boolean releaseNotesActivityHasBeenDisplayed = false;
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.debug.settings.DebugPrivateSettingsViewState copy(boolean dialPadVisible, boolean forceLoginFallback, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.settings.HomeserverCapabilityOverrides homeserverCapabilityOverrides, boolean releaseNotesActivityHasBeenDisplayed) {
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
    
    public DebugPrivateSettingsViewState() {
        super();
    }
    
    public DebugPrivateSettingsViewState(boolean dialPadVisible, boolean forceLoginFallback, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.settings.HomeserverCapabilityOverrides homeserverCapabilityOverrides, boolean releaseNotesActivityHasBeenDisplayed) {
        super();
    }
    
    public final boolean component1() {
        return false;
    }
    
    public final boolean getDialPadVisible() {
        return false;
    }
    
    public final boolean component2() {
        return false;
    }
    
    public final boolean getForceLoginFallback() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.debug.settings.HomeserverCapabilityOverrides component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.debug.settings.HomeserverCapabilityOverrides getHomeserverCapabilityOverrides() {
        return null;
    }
    
    public final boolean component4() {
        return false;
    }
    
    public final boolean getReleaseNotesActivityHasBeenDisplayed() {
        return false;
    }
}