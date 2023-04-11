package im.vector.app.features.debug.settings;

import com.airbnb.mvrx.MavericksViewModelFactory;
import dagger.assisted.Assisted;
import dagger.assisted.AssistedFactory;
import dagger.assisted.AssistedInject;
import im.vector.app.core.di.MavericksAssistedViewModelFactory;
import im.vector.app.core.platform.EmptyViewEvents;
import im.vector.app.core.platform.VectorViewModel;
import im.vector.app.features.debug.features.DebugVectorOverrides;
import im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetAvatarCapabilityOverride;
import im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetDisplayNameCapabilityOverride;
import im.vector.app.features.home.room.list.home.release.ReleaseNotesPreferencesStore;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00192\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001:\u0002\u0019\u001aB!\b\u0007\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0002\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u00a2\u0006\u0002\u0010\nJ\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0003H\u0016J\b\u0010\u000e\u001a\u00020\fH\u0002J\u0010\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0002J\u0010\u0010\u0011\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0012H\u0002J\u0010\u0010\u0013\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0014H\u0002J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\fH\u0002J\b\u0010\u0018\u001a\u00020\fH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewModel;", "Lim/vector/app/core/platform/VectorViewModel;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewState;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions;", "Lim/vector/app/core/platform/EmptyViewEvents;", "initialState", "debugVectorOverrides", "Lim/vector/app/features/debug/features/DebugVectorOverrides;", "releaseNotesPreferencesStore", "Lim/vector/app/features/home/room/list/home/release/ReleaseNotesPreferencesStore;", "(Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewState;Lim/vector/app/features/debug/features/DebugVectorOverrides;Lim/vector/app/features/home/room/list/home/release/ReleaseNotesPreferencesStore;)V", "handle", "", "action", "handleResetReleaseNotesActivityHasBeenDisplayed", "handleSetAvatarCapabilityOverride", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetAvatarCapabilityOverride;", "handleSetDialPadVisibility", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetDialPadVisibility;", "handleSetDisplayNameCapabilityOverride", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetDisplayNameCapabilityOverride;", "handleSetForceLoginFallbackEnabled", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewActions$SetForceLoginFallbackEnabled;", "observeReleaseNotesPreferencesStore", "observeVectorOverrides", "Companion", "Factory", "vector-app_gplayDebug"})
public final class DebugPrivateSettingsViewModel extends im.vector.app.core.platform.VectorViewModel<im.vector.app.features.debug.settings.DebugPrivateSettingsViewState, im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions, im.vector.app.core.platform.EmptyViewEvents> {
    private final im.vector.app.features.debug.features.DebugVectorOverrides debugVectorOverrides = null;
    private final im.vector.app.features.home.room.list.home.release.ReleaseNotesPreferencesStore releaseNotesPreferencesStore = null;
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel.Companion Companion = null;
    
    @dagger.assisted.AssistedInject()
    public DebugPrivateSettingsViewModel(@org.jetbrains.annotations.NotNull()
    @dagger.assisted.Assisted()
    im.vector.app.features.debug.settings.DebugPrivateSettingsViewState initialState, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.features.DebugVectorOverrides debugVectorOverrides, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.home.room.list.home.release.ReleaseNotesPreferencesStore releaseNotesPreferencesStore) {
        super(null);
    }
    
    private final void observeReleaseNotesPreferencesStore() {
    }
    
    private final void observeVectorOverrides() {
    }
    
    @java.lang.Override()
    public void handle(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions action) {
    }
    
    private final void handleResetReleaseNotesActivityHasBeenDisplayed() {
    }
    
    private final void handleSetDialPadVisibility(im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetDialPadVisibility action) {
    }
    
    private final void handleSetForceLoginFallbackEnabled(im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetForceLoginFallbackEnabled action) {
    }
    
    private final void handleSetDisplayNameCapabilityOverride(im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetDisplayNameCapabilityOverride action) {
    }
    
    private final void handleSetAvatarCapabilityOverride(im.vector.app.features.debug.settings.DebugPrivateSettingsViewActions.SetAvatarCapabilityOverride action) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\bg\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0003H&\u00a8\u0006\u0006"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewModel$Factory;", "Lim/vector/app/core/di/MavericksAssistedViewModelFactory;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewModel;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewState;", "create", "initialState", "vector-app_gplayDebug"})
    @dagger.assisted.AssistedFactory()
    public static abstract interface Factory extends im.vector.app.core.di.MavericksAssistedViewModelFactory<im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel, im.vector.app.features.debug.settings.DebugPrivateSettingsViewState> {
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public abstract im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel create(@org.jetbrains.annotations.NotNull()
        im.vector.app.features.debug.settings.DebugPrivateSettingsViewState initialState);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0004J\u001b\u0010\u0005\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0003H\u0096\u0001J\u0013\u0010\t\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0006\u001a\u00020\u0007H\u0096\u0001\u00a8\u0006\n"}, d2 = {"Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewModel$Companion;", "Lcom/airbnb/mvrx/MavericksViewModelFactory;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewModel;", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewState;", "()V", "create", "viewModelContext", "Lcom/airbnb/mvrx/ViewModelContext;", "state", "initialState", "vector-app_gplayDebug"})
    public static final class Companion implements com.airbnb.mvrx.MavericksViewModelFactory<im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel, im.vector.app.features.debug.settings.DebugPrivateSettingsViewState> {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        public im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel create(@org.jetbrains.annotations.NotNull()
        com.airbnb.mvrx.ViewModelContext viewModelContext, @org.jetbrains.annotations.NotNull()
        im.vector.app.features.debug.settings.DebugPrivateSettingsViewState state) {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        @java.lang.Override()
        public im.vector.app.features.debug.settings.DebugPrivateSettingsViewState initialState(@org.jetbrains.annotations.NotNull()
        com.airbnb.mvrx.ViewModelContext viewModelContext) {
            return null;
        }
    }
}