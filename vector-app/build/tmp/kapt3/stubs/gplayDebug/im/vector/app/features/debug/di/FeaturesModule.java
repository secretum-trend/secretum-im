package im.vector.app.features.debug.di;

import android.content.Context;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import im.vector.app.features.DefaultVectorFeatures;
import im.vector.app.features.DefaultVectorOverrides;
import im.vector.app.features.VectorFeatures;
import im.vector.app.features.VectorOverrides;
import im.vector.app.features.debug.features.DebugVectorFeatures;
import im.vector.app.features.debug.features.DebugVectorOverrides;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bg\u0018\u0000 \n2\u00020\u0001:\u0001\nJ\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H\'J\u0010\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\'\u00a8\u0006\u000b"}, d2 = {"Lim/vector/app/features/debug/di/FeaturesModule;", "", "bindFeatures", "Lim/vector/app/features/VectorFeatures;", "debugFeatures", "Lim/vector/app/features/debug/features/DebugVectorFeatures;", "bindOverrides", "Lim/vector/app/features/VectorOverrides;", "debugOverrides", "Lim/vector/app/features/debug/features/DebugVectorOverrides;", "Companion", "Secretum-v1.0.0(1)_gplayDebug"})
@dagger.Module()
public abstract interface FeaturesModule {
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.features.debug.di.FeaturesModule.Companion Companion = null;
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.VectorFeatures bindFeatures(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.features.DebugVectorFeatures debugFeatures);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.VectorOverrides bindOverrides(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.features.DebugVectorOverrides debugOverrides);
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H\u0007J\b\u0010\u000b\u001a\u00020\bH\u0007J\b\u0010\f\u001a\u00020\rH\u0007\u00a8\u0006\u000e"}, d2 = {"Lim/vector/app/features/debug/di/FeaturesModule$Companion;", "", "()V", "providesDebugVectorFeatures", "Lim/vector/app/features/debug/features/DebugVectorFeatures;", "context", "Landroid/content/Context;", "defaultVectorFeatures", "Lim/vector/app/features/DefaultVectorFeatures;", "providesDebugVectorOverrides", "Lim/vector/app/features/debug/features/DebugVectorOverrides;", "providesDefaultVectorFeatures", "providesDefaultVectorOverrides", "Lim/vector/app/features/DefaultVectorOverrides;", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @dagger.Provides()
        public final im.vector.app.features.DefaultVectorFeatures providesDefaultVectorFeatures() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @dagger.Provides()
        public final im.vector.app.features.debug.features.DebugVectorFeatures providesDebugVectorFeatures(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        im.vector.app.features.DefaultVectorFeatures defaultVectorFeatures) {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @dagger.Provides()
        public final im.vector.app.features.DefaultVectorOverrides providesDefaultVectorOverrides() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        @dagger.Provides()
        public final im.vector.app.features.debug.features.DebugVectorOverrides providesDebugVectorOverrides(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}