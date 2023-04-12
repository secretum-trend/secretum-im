package im.vector.app.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import im.vector.app.core.debug.DebugReceiver;
import im.vector.app.core.di.DefaultPreferences;
import timber.log.Timber;
import javax.inject.Inject;

/**
 * Receiver to handle some command from ADB
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 \u00132\u00020\u00012\u00020\u0002:\u0001\u0013B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0003\u001a\u00020\u0004\u00a2\u0006\u0002\u0010\u0005J\b\u0010\u0006\u001a\u00020\u0007H\u0002J\b\u0010\b\u001a\u00020\u0007H\u0002J\u001a\u0010\t\u001a\u00020\u00072\u0006\u0010\n\u001a\u00020\u000b2\b\u0010\u0003\u001a\u0004\u0018\u00010\u0004H\u0002J\u0018\u0010\f\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016J\u0010\u0010\u0011\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0012\u001a\u00020\u00072\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0014"}, d2 = {"Lim/vector/app/receivers/VectorDebugReceiver;", "Landroid/content/BroadcastReceiver;", "Lim/vector/app/core/debug/DebugReceiver;", "sharedPreferences", "Landroid/content/SharedPreferences;", "(Landroid/content/SharedPreferences;)V", "alterScalarToken", "", "dumpPreferences", "logPrefs", "name", "", "onReceive", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "register", "unregister", "Companion", "Secretum-v1.0.0(1)_gplayDebug"})
public final class VectorDebugReceiver extends android.content.BroadcastReceiver implements im.vector.app.core.debug.DebugReceiver {
    private final android.content.SharedPreferences sharedPreferences = null;
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.receivers.VectorDebugReceiver.Companion Companion = null;
    private static final java.lang.String DEBUG_ACTION_DUMP_FILESYSTEM = ".DEBUG_ACTION_DUMP_FILESYSTEM";
    private static final java.lang.String DEBUG_ACTION_DUMP_PREFERENCES = ".DEBUG_ACTION_DUMP_PREFERENCES";
    private static final java.lang.String DEBUG_ACTION_ALTER_SCALAR_TOKEN = ".DEBUG_ACTION_ALTER_SCALAR_TOKEN";
    
    @javax.inject.Inject()
    public VectorDebugReceiver(@org.jetbrains.annotations.NotNull()
    @im.vector.app.core.di.DefaultPreferences()
    android.content.SharedPreferences sharedPreferences) {
        super();
    }
    
    @java.lang.Override()
    public void register(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void unregister(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    private final void dumpPreferences() {
    }
    
    private final void logPrefs(java.lang.String name, android.content.SharedPreferences sharedPreferences) {
    }
    
    private final void alterScalarToken() {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lim/vector/app/receivers/VectorDebugReceiver$Companion;", "", "()V", "DEBUG_ACTION_ALTER_SCALAR_TOKEN", "", "DEBUG_ACTION_DUMP_FILESYSTEM", "DEBUG_ACTION_DUMP_PREFERENCES", "getIntentFilter", "Landroid/content/IntentFilter;", "context", "Landroid/content/Context;", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.content.IntentFilter getIntentFilter(@org.jetbrains.annotations.NotNull()
        android.content.Context context) {
            return null;
        }
    }
}