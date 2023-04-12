package im.vector.app.features.debug.di;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.multibindings.IntoMap;
import im.vector.app.core.di.MavericksAssistedViewModelFactory;
import im.vector.app.core.di.MavericksViewModelComponent;
import im.vector.app.core.di.MavericksViewModelKey;
import im.vector.app.features.debug.analytics.DebugAnalyticsViewModel;
import im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel;
import im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel;

@dagger.hilt.InstallIn(value = {im.vector.app.core.di.MavericksViewModelComponent.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0018\u0010\u0006\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\u0007H\'J\u0018\u0010\b\u001a\n\u0012\u0002\b\u0003\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u00020\tH\'\u00a8\u0006\n"}, d2 = {"Lim/vector/app/features/debug/di/MavericksViewModelDebugModule;", "", "debugAnalyticsViewModelFactory", "Lim/vector/app/core/di/MavericksAssistedViewModelFactory;", "factory", "Lim/vector/app/features/debug/analytics/DebugAnalyticsViewModel$Factory;", "debugMemoryLeaksViewModelFactory", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel$Factory;", "debugPrivateSettingsViewModelFactory", "Lim/vector/app/features/debug/settings/DebugPrivateSettingsViewModel$Factory;", "Secretum-v1.0.0(1)_gplayDebug"})
@dagger.Module()
public abstract interface MavericksViewModelDebugModule {
    
    @org.jetbrains.annotations.NotNull()
    @im.vector.app.core.di.MavericksViewModelKey(value = im.vector.app.features.debug.analytics.DebugAnalyticsViewModel.class)
    @dagger.multibindings.IntoMap()
    @dagger.Binds()
    public abstract im.vector.app.core.di.MavericksAssistedViewModelFactory<?, ?> debugAnalyticsViewModelFactory(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.analytics.DebugAnalyticsViewModel.Factory factory);
    
    @org.jetbrains.annotations.NotNull()
    @im.vector.app.core.di.MavericksViewModelKey(value = im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel.class)
    @dagger.multibindings.IntoMap()
    @dagger.Binds()
    public abstract im.vector.app.core.di.MavericksAssistedViewModelFactory<?, ?> debugPrivateSettingsViewModelFactory(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.settings.DebugPrivateSettingsViewModel.Factory factory);
    
    @org.jetbrains.annotations.NotNull()
    @im.vector.app.core.di.MavericksViewModelKey(value = im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel.class)
    @dagger.multibindings.IntoMap()
    @dagger.Binds()
    public abstract im.vector.app.core.di.MavericksAssistedViewModelFactory<?, ?> debugMemoryLeaksViewModelFactory(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel.Factory factory);
}