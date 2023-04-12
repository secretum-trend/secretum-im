package im.vector.app.push.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import dagger.hilt.android.AndroidEntryPoint;
import im.vector.app.R;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.pushers.PushParser;
import im.vector.app.core.pushers.PushersManager;
import im.vector.app.core.pushers.UnifiedPushHelper;
import im.vector.app.core.pushers.VectorPushHandler;
import im.vector.app.features.settings.VectorPreferences;
import org.matrix.android.sdk.api.logger.LoggerTag;
import timber.log.Timber;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\u0016J\u0010\u00101\u001a\u00020.2\u0006\u00102\u001a\u000203H\u0016R\u001e\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001e\u0010\t\u001a\u00020\n8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001e\u0010\u000f\u001a\u00020\u00108\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001e\u0010\u0015\u001a\u00020\u00168\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0017\u0010\u0018\"\u0004\b\u0019\u0010\u001aR\u001e\u0010\u001b\u001a\u00020\u001c8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 R\u001e\u0010!\u001a\u00020\"8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b#\u0010$\"\u0004\b%\u0010&R\u001e\u0010\'\u001a\u00020(8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b)\u0010*\"\u0004\b+\u0010,\u00a8\u00064"}, d2 = {"Lim/vector/app/push/fcm/VectorFirebaseMessagingService;", "Lcom/google/firebase/messaging/FirebaseMessagingService;", "()V", "activeSessionHolder", "Lim/vector/app/core/di/ActiveSessionHolder;", "getActiveSessionHolder", "()Lim/vector/app/core/di/ActiveSessionHolder;", "setActiveSessionHolder", "(Lim/vector/app/core/di/ActiveSessionHolder;)V", "fcmHelper", "Lim/vector/app/core/pushers/FcmHelper;", "getFcmHelper", "()Lim/vector/app/core/pushers/FcmHelper;", "setFcmHelper", "(Lim/vector/app/core/pushers/FcmHelper;)V", "pushParser", "Lim/vector/app/core/pushers/PushParser;", "getPushParser", "()Lim/vector/app/core/pushers/PushParser;", "setPushParser", "(Lim/vector/app/core/pushers/PushParser;)V", "pushersManager", "Lim/vector/app/core/pushers/PushersManager;", "getPushersManager", "()Lim/vector/app/core/pushers/PushersManager;", "setPushersManager", "(Lim/vector/app/core/pushers/PushersManager;)V", "unifiedPushHelper", "Lim/vector/app/core/pushers/UnifiedPushHelper;", "getUnifiedPushHelper", "()Lim/vector/app/core/pushers/UnifiedPushHelper;", "setUnifiedPushHelper", "(Lim/vector/app/core/pushers/UnifiedPushHelper;)V", "vectorPreferences", "Lim/vector/app/features/settings/VectorPreferences;", "getVectorPreferences", "()Lim/vector/app/features/settings/VectorPreferences;", "setVectorPreferences", "(Lim/vector/app/features/settings/VectorPreferences;)V", "vectorPushHandler", "Lim/vector/app/core/pushers/VectorPushHandler;", "getVectorPushHandler", "()Lim/vector/app/core/pushers/VectorPushHandler;", "setVectorPushHandler", "(Lim/vector/app/core/pushers/VectorPushHandler;)V", "onMessageReceived", "", "message", "Lcom/google/firebase/messaging/RemoteMessage;", "onNewToken", "token", "", "Secretum-v1.0.0(1)_gplayDebug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class VectorFirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    @javax.inject.Inject()
    public im.vector.app.core.pushers.FcmHelper fcmHelper;
    @javax.inject.Inject()
    public im.vector.app.features.settings.VectorPreferences vectorPreferences;
    @javax.inject.Inject()
    public im.vector.app.core.di.ActiveSessionHolder activeSessionHolder;
    @javax.inject.Inject()
    public im.vector.app.core.pushers.PushersManager pushersManager;
    @javax.inject.Inject()
    public im.vector.app.core.pushers.PushParser pushParser;
    @javax.inject.Inject()
    public im.vector.app.core.pushers.VectorPushHandler vectorPushHandler;
    @javax.inject.Inject()
    public im.vector.app.core.pushers.UnifiedPushHelper unifiedPushHelper;
    
    public VectorFirebaseMessagingService() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.pushers.FcmHelper getFcmHelper() {
        return null;
    }
    
    public final void setFcmHelper(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.FcmHelper p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.settings.VectorPreferences getVectorPreferences() {
        return null;
    }
    
    public final void setVectorPreferences(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.VectorPreferences p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.di.ActiveSessionHolder getActiveSessionHolder() {
        return null;
    }
    
    public final void setActiveSessionHolder(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.di.ActiveSessionHolder p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.pushers.PushersManager getPushersManager() {
        return null;
    }
    
    public final void setPushersManager(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.PushersManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.pushers.PushParser getPushParser() {
        return null;
    }
    
    public final void setPushParser(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.PushParser p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.pushers.VectorPushHandler getVectorPushHandler() {
        return null;
    }
    
    public final void setVectorPushHandler(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.VectorPushHandler p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.pushers.UnifiedPushHelper getUnifiedPushHelper() {
        return null;
    }
    
    public final void setUnifiedPushHelper(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.UnifiedPushHelper p0) {
    }
    
    @java.lang.Override()
    public void onNewToken(@org.jetbrains.annotations.NotNull()
    java.lang.String token) {
    }
    
    @java.lang.Override()
    public void onMessageReceived(@org.jetbrains.annotations.NotNull()
    com.google.firebase.messaging.RemoteMessage message) {
    }
}