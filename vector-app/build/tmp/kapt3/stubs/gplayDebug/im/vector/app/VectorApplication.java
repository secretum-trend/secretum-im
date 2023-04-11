package im.vector.app;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.view.Gravity;
import androidx.core.provider.FontRequest;
import androidx.core.provider.FontsContractCompat;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ProcessLifecycleOwner;
import androidx.multidex.MultiDex;
import androidx.recyclerview.widget.SnapHelper;
import com.airbnb.epoxy.Carousel;
import com.airbnb.epoxy.EpoxyAsyncUtil;
import com.airbnb.epoxy.EpoxyController;
import com.airbnb.mvrx.Mavericks;
import com.facebook.stetho.Stetho;
import com.gabrielittner.threetenbp.LazyThreeTen;
import com.github.rubensousa.gravitysnaphelper.GravitySnapHelper;
import com.mapbox.mapboxsdk.Mapbox;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.google.GoogleEmojiProvider;
import dagger.hilt.android.HiltAndroidApp;
import im.vector.app.config.Config;
import im.vector.app.core.debug.FlipperProxy;
import im.vector.app.core.debug.LeakDetector;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.resources.BuildMeta;
import im.vector.app.features.analytics.VectorAnalytics;
import im.vector.app.features.call.webrtc.WebRtcCallManager;
import im.vector.app.features.configuration.VectorConfiguration;
import im.vector.app.features.disclaimer.DisclaimerDialog;
import im.vector.app.features.invite.InvitesAcceptor;
import im.vector.app.features.lifecycle.VectorActivityLifecycleCallbacks;
import im.vector.app.features.notifications.NotificationDrawerManager;
import im.vector.app.features.notifications.NotificationUtils;
import im.vector.app.features.pin.PinLocker;
import im.vector.app.features.popup.PopupAlertManager;
import im.vector.app.features.rageshake.VectorFileLogger;
import im.vector.app.features.rageshake.VectorUncaughtExceptionHandler;
import im.vector.app.features.settings.VectorLocale;
import im.vector.app.features.settings.VectorPreferences;
import im.vector.app.features.themes.ThemeUtils;
import im.vector.app.features.version.VersionProvider;
import im.vector.application.R;
import org.jitsi.meet.sdk.log.JitsiMeetDefaultLogHandler;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.auth.AuthenticationService;
import org.matrix.android.sdk.api.legacy.LegacySessionImporter;
import timber.log.Timber;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.Executors;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u008c\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\u00020\u00012\u00020\u0002B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0013\u0010\u00aa\u0001\u001a\u00030\u00ab\u00012\u0007\u0010\u00ac\u0001\u001a\u00020\u000bH\u0014J\n\u0010\u00ad\u0001\u001a\u00030\u00ab\u0001H\u0002J\t\u0010\u00ae\u0001\u001a\u00020GH\u0002J\n\u0010\u00af\u0001\u001a\u00030\u00ab\u0001H\u0002J\t\u0010\u00b0\u0001\u001a\u00020GH\u0002J\n\u0010\u00b1\u0001\u001a\u00030\u00b2\u0001H\u0016J\n\u0010\u00b3\u0001\u001a\u00030\u00ab\u0001H\u0002J\n\u0010\u00b4\u0001\u001a\u00030\u00ab\u0001H\u0002J\u0014\u0010\u00b5\u0001\u001a\u00030\u00ab\u00012\b\u0010\u00b6\u0001\u001a\u00030\u00b7\u0001H\u0016J\n\u0010\u00b8\u0001\u001a\u00030\u00ab\u0001H\u0016R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001a\u0010\n\u001a\u00020\u000bX\u0086.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0010\u001a\u00020\u00118\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0012\u0010\u0013\"\u0004\b\u0014\u0010\u0015R\u001e\u0010\u0016\u001a\u00020\u00178\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0018\u0010\u0019\"\u0004\b\u001a\u0010\u001bR\u001e\u0010\u001c\u001a\u00020\u001d8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u001e\u0010\u001f\"\u0004\b \u0010!R\u001e\u0010\"\u001a\u00020#8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b$\u0010%\"\u0004\b&\u0010\'R\u001e\u0010(\u001a\u00020)8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b*\u0010+\"\u0004\b,\u0010-R\u001e\u0010.\u001a\u00020/8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b0\u00101\"\u0004\b2\u00103R\u001e\u00104\u001a\u0002058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b6\u00107\"\u0004\b8\u00109R\u001e\u0010:\u001a\u00020;8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b<\u0010=\"\u0004\b>\u0010?R\u001e\u0010@\u001a\u00020A8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bB\u0010C\"\u0004\bD\u0010ER\u0010\u0010F\u001a\u0004\u0018\u00010GX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010H\u001a\u00020I8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bJ\u0010K\"\u0004\bL\u0010MR\u001e\u0010N\u001a\u00020O8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bP\u0010Q\"\u0004\bR\u0010SR\u001e\u0010T\u001a\u00020U8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bV\u0010W\"\u0004\bX\u0010YR\u001e\u0010Z\u001a\u00020[8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\\\u0010]\"\u0004\b^\u0010_R\u001e\u0010`\u001a\u00020a8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bb\u0010c\"\u0004\bd\u0010eR\u001e\u0010f\u001a\u00020g8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bh\u0010i\"\u0004\bj\u0010kR\u001e\u0010l\u001a\u00020m8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bn\u0010o\"\u0004\bp\u0010qR\u001e\u0010r\u001a\u00020s8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\bt\u0010u\"\u0004\bv\u0010wR\u000e\u0010x\u001a\u00020yX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u001e\u0010z\u001a\u00020{8\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b|\u0010}\"\u0004\b~\u0010\u007fR$\u0010\u0080\u0001\u001a\u00030\u0081\u00018\u0006@\u0006X\u0087.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u0082\u0001\u0010\u0083\u0001\"\u0006\b\u0084\u0001\u0010\u0085\u0001R$\u0010\u0086\u0001\u001a\u00030\u0087\u00018\u0006@\u0006X\u0087.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u0088\u0001\u0010\u0089\u0001\"\u0006\b\u008a\u0001\u0010\u008b\u0001R$\u0010\u008c\u0001\u001a\u00030\u008d\u00018\u0006@\u0006X\u0087.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u008e\u0001\u0010\u008f\u0001\"\u0006\b\u0090\u0001\u0010\u0091\u0001R$\u0010\u0092\u0001\u001a\u00030\u0093\u00018\u0006@\u0006X\u0087.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u0094\u0001\u0010\u0095\u0001\"\u0006\b\u0096\u0001\u0010\u0097\u0001R$\u0010\u0098\u0001\u001a\u00030\u0099\u00018\u0006@\u0006X\u0087.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u009a\u0001\u0010\u009b\u0001\"\u0006\b\u009c\u0001\u0010\u009d\u0001R$\u0010\u009e\u0001\u001a\u00030\u009f\u00018\u0006@\u0006X\u0087.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00a0\u0001\u0010\u00a1\u0001\"\u0006\b\u00a2\u0001\u0010\u00a3\u0001R$\u0010\u00a4\u0001\u001a\u00030\u00a5\u00018\u0006@\u0006X\u0087.\u00a2\u0006\u0012\n\u0000\u001a\u0006\b\u00a6\u0001\u0010\u00a7\u0001\"\u0006\b\u00a8\u0001\u0010\u00a9\u0001\u00a8\u0006\u00b9\u0001"}, d2 = {"Lim/vector/app/VectorApplication;", "Landroid/app/Application;", "Landroidx/work/Configuration$Provider;", "()V", "activeSessionHolder", "Lim/vector/app/core/di/ActiveSessionHolder;", "getActiveSessionHolder", "()Lim/vector/app/core/di/ActiveSessionHolder;", "setActiveSessionHolder", "(Lim/vector/app/core/di/ActiveSessionHolder;)V", "appContext", "Landroid/content/Context;", "getAppContext", "()Landroid/content/Context;", "setAppContext", "(Landroid/content/Context;)V", "authenticationService", "Lorg/matrix/android/sdk/api/auth/AuthenticationService;", "getAuthenticationService", "()Lorg/matrix/android/sdk/api/auth/AuthenticationService;", "setAuthenticationService", "(Lorg/matrix/android/sdk/api/auth/AuthenticationService;)V", "autoRageShaker", "Lim/vector/app/AutoRageShaker;", "getAutoRageShaker", "()Lim/vector/app/AutoRageShaker;", "setAutoRageShaker", "(Lim/vector/app/AutoRageShaker;)V", "buildMeta", "Lim/vector/app/core/resources/BuildMeta;", "getBuildMeta", "()Lim/vector/app/core/resources/BuildMeta;", "setBuildMeta", "(Lim/vector/app/core/resources/BuildMeta;)V", "callManager", "Lim/vector/app/features/call/webrtc/WebRtcCallManager;", "getCallManager", "()Lim/vector/app/features/call/webrtc/WebRtcCallManager;", "setCallManager", "(Lim/vector/app/features/call/webrtc/WebRtcCallManager;)V", "disclaimerDialog", "Lim/vector/app/features/disclaimer/DisclaimerDialog;", "getDisclaimerDialog", "()Lim/vector/app/features/disclaimer/DisclaimerDialog;", "setDisclaimerDialog", "(Lim/vector/app/features/disclaimer/DisclaimerDialog;)V", "emojiCompatFontProvider", "Lim/vector/app/EmojiCompatFontProvider;", "getEmojiCompatFontProvider", "()Lim/vector/app/EmojiCompatFontProvider;", "setEmojiCompatFontProvider", "(Lim/vector/app/EmojiCompatFontProvider;)V", "emojiCompatWrapper", "Lim/vector/app/EmojiCompatWrapper;", "getEmojiCompatWrapper", "()Lim/vector/app/EmojiCompatWrapper;", "setEmojiCompatWrapper", "(Lim/vector/app/EmojiCompatWrapper;)V", "fcmHelper", "Lim/vector/app/core/pushers/FcmHelper;", "getFcmHelper", "()Lim/vector/app/core/pushers/FcmHelper;", "setFcmHelper", "(Lim/vector/app/core/pushers/FcmHelper;)V", "flipperProxy", "Lim/vector/app/core/debug/FlipperProxy;", "getFlipperProxy", "()Lim/vector/app/core/debug/FlipperProxy;", "setFlipperProxy", "(Lim/vector/app/core/debug/FlipperProxy;)V", "fontThreadHandler", "Landroid/os/Handler;", "invitesAcceptor", "Lim/vector/app/features/invite/InvitesAcceptor;", "getInvitesAcceptor", "()Lim/vector/app/features/invite/InvitesAcceptor;", "setInvitesAcceptor", "(Lim/vector/app/features/invite/InvitesAcceptor;)V", "leakDetector", "Lim/vector/app/core/debug/LeakDetector;", "getLeakDetector", "()Lim/vector/app/core/debug/LeakDetector;", "setLeakDetector", "(Lim/vector/app/core/debug/LeakDetector;)V", "legacySessionImporter", "Lorg/matrix/android/sdk/api/legacy/LegacySessionImporter;", "getLegacySessionImporter", "()Lorg/matrix/android/sdk/api/legacy/LegacySessionImporter;", "setLegacySessionImporter", "(Lorg/matrix/android/sdk/api/legacy/LegacySessionImporter;)V", "matrix", "Lorg/matrix/android/sdk/api/Matrix;", "getMatrix", "()Lorg/matrix/android/sdk/api/Matrix;", "setMatrix", "(Lorg/matrix/android/sdk/api/Matrix;)V", "notificationDrawerManager", "Lim/vector/app/features/notifications/NotificationDrawerManager;", "getNotificationDrawerManager", "()Lim/vector/app/features/notifications/NotificationDrawerManager;", "setNotificationDrawerManager", "(Lim/vector/app/features/notifications/NotificationDrawerManager;)V", "notificationUtils", "Lim/vector/app/features/notifications/NotificationUtils;", "getNotificationUtils", "()Lim/vector/app/features/notifications/NotificationUtils;", "setNotificationUtils", "(Lim/vector/app/features/notifications/NotificationUtils;)V", "pinLocker", "Lim/vector/app/features/pin/PinLocker;", "getPinLocker", "()Lim/vector/app/features/pin/PinLocker;", "setPinLocker", "(Lim/vector/app/features/pin/PinLocker;)V", "popupAlertManager", "Lim/vector/app/features/popup/PopupAlertManager;", "getPopupAlertManager", "()Lim/vector/app/features/popup/PopupAlertManager;", "setPopupAlertManager", "(Lim/vector/app/features/popup/PopupAlertManager;)V", "powerKeyReceiver", "Landroid/content/BroadcastReceiver;", "spaceStateHandler", "Lim/vector/app/SpaceStateHandler;", "getSpaceStateHandler", "()Lim/vector/app/SpaceStateHandler;", "setSpaceStateHandler", "(Lim/vector/app/SpaceStateHandler;)V", "vectorAnalytics", "Lim/vector/app/features/analytics/VectorAnalytics;", "getVectorAnalytics", "()Lim/vector/app/features/analytics/VectorAnalytics;", "setVectorAnalytics", "(Lim/vector/app/features/analytics/VectorAnalytics;)V", "vectorConfiguration", "Lim/vector/app/features/configuration/VectorConfiguration;", "getVectorConfiguration", "()Lim/vector/app/features/configuration/VectorConfiguration;", "setVectorConfiguration", "(Lim/vector/app/features/configuration/VectorConfiguration;)V", "vectorFileLogger", "Lim/vector/app/features/rageshake/VectorFileLogger;", "getVectorFileLogger", "()Lim/vector/app/features/rageshake/VectorFileLogger;", "setVectorFileLogger", "(Lim/vector/app/features/rageshake/VectorFileLogger;)V", "vectorLocale", "Lim/vector/app/features/settings/VectorLocale;", "getVectorLocale", "()Lim/vector/app/features/settings/VectorLocale;", "setVectorLocale", "(Lim/vector/app/features/settings/VectorLocale;)V", "vectorPreferences", "Lim/vector/app/features/settings/VectorPreferences;", "getVectorPreferences", "()Lim/vector/app/features/settings/VectorPreferences;", "setVectorPreferences", "(Lim/vector/app/features/settings/VectorPreferences;)V", "vectorUncaughtExceptionHandler", "Lim/vector/app/features/rageshake/VectorUncaughtExceptionHandler;", "getVectorUncaughtExceptionHandler", "()Lim/vector/app/features/rageshake/VectorUncaughtExceptionHandler;", "setVectorUncaughtExceptionHandler", "(Lim/vector/app/features/rageshake/VectorUncaughtExceptionHandler;)V", "versionProvider", "Lim/vector/app/features/version/VersionProvider;", "getVersionProvider", "()Lim/vector/app/features/version/VersionProvider;", "setVersionProvider", "(Lim/vector/app/features/version/VersionProvider;)V", "attachBaseContext", "", "base", "configureEpoxy", "createFontThreadHandler", "enableStrictModeIfNeeded", "getFontThreadHandler", "getWorkManagerConfiguration", "Landroidx/work/Configuration;", "initMemoryLeakAnalysis", "logInfo", "onConfigurationChanged", "newConfig", "Landroid/content/res/Configuration;", "onCreate", "vector-app_gplayDebug"})
@dagger.hilt.android.HiltAndroidApp()
public final class VectorApplication extends android.app.Application implements androidx.work.Configuration.Provider {
    public android.content.Context appContext;
    @javax.inject.Inject()
    public org.matrix.android.sdk.api.legacy.LegacySessionImporter legacySessionImporter;
    @javax.inject.Inject()
    public org.matrix.android.sdk.api.auth.AuthenticationService authenticationService;
    @javax.inject.Inject()
    public im.vector.app.features.configuration.VectorConfiguration vectorConfiguration;
    @javax.inject.Inject()
    public im.vector.app.EmojiCompatFontProvider emojiCompatFontProvider;
    @javax.inject.Inject()
    public im.vector.app.EmojiCompatWrapper emojiCompatWrapper;
    @javax.inject.Inject()
    public im.vector.app.features.rageshake.VectorUncaughtExceptionHandler vectorUncaughtExceptionHandler;
    @javax.inject.Inject()
    public im.vector.app.core.di.ActiveSessionHolder activeSessionHolder;
    @javax.inject.Inject()
    public im.vector.app.features.notifications.NotificationDrawerManager notificationDrawerManager;
    @javax.inject.Inject()
    public im.vector.app.features.settings.VectorPreferences vectorPreferences;
    @javax.inject.Inject()
    public im.vector.app.features.version.VersionProvider versionProvider;
    @javax.inject.Inject()
    public im.vector.app.features.notifications.NotificationUtils notificationUtils;
    @javax.inject.Inject()
    public im.vector.app.SpaceStateHandler spaceStateHandler;
    @javax.inject.Inject()
    public im.vector.app.features.popup.PopupAlertManager popupAlertManager;
    @javax.inject.Inject()
    public im.vector.app.features.pin.PinLocker pinLocker;
    @javax.inject.Inject()
    public im.vector.app.features.call.webrtc.WebRtcCallManager callManager;
    @javax.inject.Inject()
    public im.vector.app.features.invite.InvitesAcceptor invitesAcceptor;
    @javax.inject.Inject()
    public im.vector.app.AutoRageShaker autoRageShaker;
    @javax.inject.Inject()
    public im.vector.app.features.rageshake.VectorFileLogger vectorFileLogger;
    @javax.inject.Inject()
    public im.vector.app.features.analytics.VectorAnalytics vectorAnalytics;
    @javax.inject.Inject()
    public im.vector.app.core.debug.FlipperProxy flipperProxy;
    @javax.inject.Inject()
    public org.matrix.android.sdk.api.Matrix matrix;
    @javax.inject.Inject()
    public im.vector.app.core.pushers.FcmHelper fcmHelper;
    @javax.inject.Inject()
    public im.vector.app.core.resources.BuildMeta buildMeta;
    @javax.inject.Inject()
    public im.vector.app.core.debug.LeakDetector leakDetector;
    @javax.inject.Inject()
    public im.vector.app.features.settings.VectorLocale vectorLocale;
    @javax.inject.Inject()
    public im.vector.app.features.disclaimer.DisclaimerDialog disclaimerDialog;
    private android.os.Handler fontThreadHandler;
    private final android.content.BroadcastReceiver powerKeyReceiver = null;
    
    public VectorApplication() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Context getAppContext() {
        return null;
    }
    
    public final void setAppContext(@org.jetbrains.annotations.NotNull()
    android.content.Context p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.matrix.android.sdk.api.legacy.LegacySessionImporter getLegacySessionImporter() {
        return null;
    }
    
    public final void setLegacySessionImporter(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.legacy.LegacySessionImporter p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.matrix.android.sdk.api.auth.AuthenticationService getAuthenticationService() {
        return null;
    }
    
    public final void setAuthenticationService(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.auth.AuthenticationService p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.configuration.VectorConfiguration getVectorConfiguration() {
        return null;
    }
    
    public final void setVectorConfiguration(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.configuration.VectorConfiguration p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.EmojiCompatFontProvider getEmojiCompatFontProvider() {
        return null;
    }
    
    public final void setEmojiCompatFontProvider(@org.jetbrains.annotations.NotNull()
    im.vector.app.EmojiCompatFontProvider p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.EmojiCompatWrapper getEmojiCompatWrapper() {
        return null;
    }
    
    public final void setEmojiCompatWrapper(@org.jetbrains.annotations.NotNull()
    im.vector.app.EmojiCompatWrapper p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.rageshake.VectorUncaughtExceptionHandler getVectorUncaughtExceptionHandler() {
        return null;
    }
    
    public final void setVectorUncaughtExceptionHandler(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.rageshake.VectorUncaughtExceptionHandler p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.di.ActiveSessionHolder getActiveSessionHolder() {
        return null;
    }
    
    public final void setActiveSessionHolder(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.di.ActiveSessionHolder p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.notifications.NotificationDrawerManager getNotificationDrawerManager() {
        return null;
    }
    
    public final void setNotificationDrawerManager(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.notifications.NotificationDrawerManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.settings.VectorPreferences getVectorPreferences() {
        return null;
    }
    
    public final void setVectorPreferences(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.VectorPreferences p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.version.VersionProvider getVersionProvider() {
        return null;
    }
    
    public final void setVersionProvider(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.version.VersionProvider p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.notifications.NotificationUtils getNotificationUtils() {
        return null;
    }
    
    public final void setNotificationUtils(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.notifications.NotificationUtils p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.SpaceStateHandler getSpaceStateHandler() {
        return null;
    }
    
    public final void setSpaceStateHandler(@org.jetbrains.annotations.NotNull()
    im.vector.app.SpaceStateHandler p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.popup.PopupAlertManager getPopupAlertManager() {
        return null;
    }
    
    public final void setPopupAlertManager(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.popup.PopupAlertManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.pin.PinLocker getPinLocker() {
        return null;
    }
    
    public final void setPinLocker(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.pin.PinLocker p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.call.webrtc.WebRtcCallManager getCallManager() {
        return null;
    }
    
    public final void setCallManager(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.call.webrtc.WebRtcCallManager p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.invite.InvitesAcceptor getInvitesAcceptor() {
        return null;
    }
    
    public final void setInvitesAcceptor(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.invite.InvitesAcceptor p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.AutoRageShaker getAutoRageShaker() {
        return null;
    }
    
    public final void setAutoRageShaker(@org.jetbrains.annotations.NotNull()
    im.vector.app.AutoRageShaker p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.rageshake.VectorFileLogger getVectorFileLogger() {
        return null;
    }
    
    public final void setVectorFileLogger(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.rageshake.VectorFileLogger p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.analytics.VectorAnalytics getVectorAnalytics() {
        return null;
    }
    
    public final void setVectorAnalytics(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.analytics.VectorAnalytics p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.debug.FlipperProxy getFlipperProxy() {
        return null;
    }
    
    public final void setFlipperProxy(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.debug.FlipperProxy p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.matrix.android.sdk.api.Matrix getMatrix() {
        return null;
    }
    
    public final void setMatrix(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.Matrix p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.pushers.FcmHelper getFcmHelper() {
        return null;
    }
    
    public final void setFcmHelper(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.FcmHelper p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.resources.BuildMeta getBuildMeta() {
        return null;
    }
    
    public final void setBuildMeta(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.resources.BuildMeta p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.debug.LeakDetector getLeakDetector() {
        return null;
    }
    
    public final void setLeakDetector(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.debug.LeakDetector p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.settings.VectorLocale getVectorLocale() {
        return null;
    }
    
    public final void setVectorLocale(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.VectorLocale p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.disclaimer.DisclaimerDialog getDisclaimerDialog() {
        return null;
    }
    
    public final void setDisclaimerDialog(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.disclaimer.DisclaimerDialog p0) {
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final void configureEpoxy() {
    }
    
    private final void enableStrictModeIfNeeded() {
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.work.Configuration getWorkManagerConfiguration() {
        return null;
    }
    
    private final void logInfo() {
    }
    
    @java.lang.Override()
    protected void attachBaseContext(@org.jetbrains.annotations.NotNull()
    android.content.Context base) {
    }
    
    @java.lang.Override()
    public void onConfigurationChanged(@org.jetbrains.annotations.NotNull()
    android.content.res.Configuration newConfig) {
    }
    
    private final android.os.Handler getFontThreadHandler() {
        return null;
    }
    
    private final android.os.Handler createFontThreadHandler() {
        return null;
    }
    
    private final void initMemoryLeakAnalysis() {
    }
}