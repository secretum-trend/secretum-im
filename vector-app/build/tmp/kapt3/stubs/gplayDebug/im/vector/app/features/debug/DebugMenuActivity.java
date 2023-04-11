package im.vector.app.features.debug;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.app.Person;
import dagger.hilt.android.AndroidEntryPoint;
import im.vector.app.R;
import im.vector.app.core.platform.VectorBaseActivity;
import im.vector.app.core.time.Clock;
import im.vector.app.features.debug.analytics.DebugAnalyticsActivity;
import im.vector.app.features.debug.features.DebugFeaturesSettingsActivity;
import im.vector.app.features.debug.jitsi.DebugJitsiActivity;
import im.vector.app.features.debug.leak.DebugMemoryLeaksActivity;
import im.vector.app.features.debug.sas.DebugSasEmojiActivity;
import im.vector.app.features.debug.settings.DebugPrivateSettingsActivity;
import im.vector.app.features.qrcode.QrCodeScannerActivity;
import im.vector.application.databinding.ActivityDebugMenuBinding;
import im.vector.lib.ui.styles.debug.DebugMaterialThemeDarkDefaultActivity;
import im.vector.lib.ui.styles.debug.DebugMaterialThemeDarkTestActivity;
import im.vector.lib.ui.styles.debug.DebugMaterialThemeDarkVectorActivity;
import im.vector.lib.ui.styles.debug.DebugMaterialThemeLightDefaultActivity;
import im.vector.lib.ui.styles.debug.DebugMaterialThemeLightTestActivity;
import im.vector.lib.ui.styles.debug.DebugMaterialThemeLightVectorActivity;
import im.vector.lib.ui.styles.debug.DebugVectorButtonStylesDarkActivity;
import im.vector.lib.ui.styles.debug.DebugVectorButtonStylesLightActivity;
import im.vector.lib.ui.styles.debug.DebugVectorTextViewDarkActivity;
import im.vector.lib.ui.styles.debug.DebugVectorTextViewLightActivity;
import timber.log.Timber;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\r\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\u0002H\u0016J\b\u0010\u0015\u001a\u00020\u0013H\u0016J\b\u0010\u0016\u001a\u00020\u0013H\u0002J\b\u0010\u0017\u001a\u00020\u0013H\u0002J\u0010\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u000fH\u0002J\b\u0010\u001a\u001a\u00020\u0013H\u0002J\b\u0010\u001b\u001a\u00020\u0013H\u0002J\b\u0010\u001c\u001a\u00020\u0013H\u0002J\b\u0010\u001d\u001a\u00020\u0013H\u0002J\b\u0010\u001e\u001a\u00020\u0013H\u0002J\b\u0010\u001f\u001a\u00020\u0013H\u0002R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082.\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR\u001a\u0010\f\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u000f0\u000e0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lim/vector/app/features/debug/DebugMenuActivity;", "Lim/vector/app/core/platform/VectorBaseActivity;", "Lim/vector/application/databinding/ActivityDebugMenuBinding;", "()V", "buffer", "", "clock", "Lim/vector/app/core/time/Clock;", "getClock", "()Lim/vector/app/core/time/Clock;", "setClock", "(Lim/vector/app/core/time/Clock;)V", "permissionCameraLauncher", "Landroidx/activity/result/ActivityResultLauncher;", "", "", "qrStartForActivityResult", "Landroid/content/Intent;", "doScanQRCode", "", "getBinding", "initUiAndData", "openMemoryLeaksSettings", "openPrivateSettings", "renderQrCode", "text", "scanQRCode", "setupViews", "showSasEmoji", "testCrash", "testNotification", "testTextViewLink", "vector-app_gplayDebug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class DebugMenuActivity extends im.vector.app.core.platform.VectorBaseActivity<im.vector.application.databinding.ActivityDebugMenuBinding> {
    @javax.inject.Inject()
    public im.vector.app.core.time.Clock clock;
    private byte[] buffer;
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> permissionCameraLauncher = null;
    private final androidx.activity.result.ActivityResultLauncher<android.content.Intent> qrStartForActivityResult = null;
    
    public DebugMenuActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public im.vector.application.databinding.ActivityDebugMenuBinding getBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.core.time.Clock getClock() {
        return null;
    }
    
    public final void setClock(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.time.Clock p0) {
    }
    
    @java.lang.Override()
    public void initUiAndData() {
    }
    
    private final void setupViews() {
    }
    
    private final void openPrivateSettings() {
    }
    
    private final void openMemoryLeaksSettings() {
    }
    
    private final void renderQrCode(java.lang.String text) {
    }
    
    private final void testTextViewLink() {
    }
    
    private final void showSasEmoji() {
    }
    
    private final void testNotification() {
    }
    
    private final void testCrash() {
    }
    
    private final void scanQRCode() {
    }
    
    private final void doScanQRCode() {
    }
}