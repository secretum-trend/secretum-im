package im.vector.app.features.debug.features;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.Preferences;
import im.vector.app.features.HomeserverCapabilitiesOverride;
import im.vector.app.features.VectorOverrides;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J\u0019\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0007H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0012J*\u0010\u0014\u001a\u00020\u00102\u0017\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\u0016\u00a2\u0006\u0002\b\u0017H\u0086@\u00f8\u0001\u0000\u00a2\u0006\u0002\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001a\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\tR\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0096\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\t\u0082\u0002\u0004\n\u0002\b\u0019\u00a8\u0006\u0019"}, d2 = {"Lim/vector/app/features/debug/features/DebugVectorOverrides;", "Lim/vector/app/features/VectorOverrides;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "forceDialPad", "Lkotlinx/coroutines/flow/Flow;", "", "getForceDialPad", "()Lkotlinx/coroutines/flow/Flow;", "forceHomeserverCapabilities", "Lim/vector/app/features/HomeserverCapabilitiesOverride;", "getForceHomeserverCapabilities", "forceLoginFallback", "getForceLoginFallback", "setForceDialPadDisplay", "", "force", "(ZLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "setForceLoginFallback", "setHomeserverCapabilities", "block", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "(Lkotlin/jvm/functions/Function1;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Secretum-v1.0.0(1)_gplayDebug"})
public final class DebugVectorOverrides implements im.vector.app.features.VectorOverrides {
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> forceDialPad = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<java.lang.Boolean> forceLoginFallback = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.Flow<im.vector.app.features.HomeserverCapabilitiesOverride> forceHomeserverCapabilities = null;
    
    public DebugVectorOverrides(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getForceDialPad() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<java.lang.Boolean> getForceLoginFallback() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public kotlinx.coroutines.flow.Flow<im.vector.app.features.HomeserverCapabilitiesOverride> getForceHomeserverCapabilities() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setForceDialPadDisplay(boolean force, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setForceLoginFallback(boolean force, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object setHomeserverCapabilities(@org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super im.vector.app.features.HomeserverCapabilitiesOverride, im.vector.app.features.HomeserverCapabilitiesOverride> block, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> continuation) {
        return null;
    }
}