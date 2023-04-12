package im.vector.app.features.debug.di;

import android.content.Context;
import android.content.Intent;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import im.vector.app.core.debug.DebugNavigator;
import im.vector.app.core.debug.DebugReceiver;
import im.vector.app.core.debug.FlipperProxy;
import im.vector.app.core.debug.LeakDetector;
import im.vector.app.features.debug.DebugMenuActivity;
import im.vector.app.flipper.VectorFlipperProxy;
import im.vector.app.leakcanary.LeakCanaryLeakDetector;
import im.vector.app.receivers.VectorDebugReceiver;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u000f2\u00020\u0001:\u0001\u000fB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'\u00a8\u0006\u0010"}, d2 = {"Lim/vector/app/features/debug/di/DebugModule;", "", "()V", "bindsDebugReceiver", "Lim/vector/app/core/debug/DebugReceiver;", "receiver", "Lim/vector/app/receivers/VectorDebugReceiver;", "bindsFlipperProxy", "Lim/vector/app/core/debug/FlipperProxy;", "flipperProxy", "Lim/vector/app/flipper/VectorFlipperProxy;", "bindsLeakDetector", "Lim/vector/app/core/debug/LeakDetector;", "leakDetector", "Lim/vector/app/leakcanary/LeakCanaryLeakDetector;", "Companion", "Secretum-v1.0.0(1)_gplayDebug"})
@dagger.Module()
public abstract class DebugModule {
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.features.debug.di.DebugModule.Companion Companion = null;
    
    public DebugModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.debug.DebugReceiver bindsDebugReceiver(@org.jetbrains.annotations.NotNull()
    im.vector.app.receivers.VectorDebugReceiver receiver);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.debug.FlipperProxy bindsFlipperProxy(@org.jetbrains.annotations.NotNull()
    im.vector.app.flipper.VectorFlipperProxy flipperProxy);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.debug.LeakDetector bindsLeakDetector(@org.jetbrains.annotations.NotNull()
    im.vector.app.leakcanary.LeakCanaryLeakDetector leakDetector);
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007\u00a8\u0006\u0005"}, d2 = {"Lim/vector/app/features/debug/di/DebugModule$Companion;", "", "()V", "providesDebugNavigator", "Lim/vector/app/core/debug/DebugNavigator;", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @dagger.Provides()
        public final im.vector.app.core.debug.DebugNavigator providesDebugNavigator() {
            return null;
        }
    }
}