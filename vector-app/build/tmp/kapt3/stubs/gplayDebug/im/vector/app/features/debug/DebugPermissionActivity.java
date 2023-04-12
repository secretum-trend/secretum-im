package im.vector.app.features.debug;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.widget.Toast;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import dagger.hilt.android.AndroidEntryPoint;
import im.vector.app.core.platform.VectorBaseActivity;
import im.vector.application.R;
import im.vector.application.databinding.ActivityDebugPermissionBinding;
import timber.log.Timber;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\b\u0010\r\u001a\u00020\u000eH\u0002J\u000e\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0002J\b\u0010\u0010\u001a\u00020\u0002H\u0016J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\b\u0010\u0013\u001a\u00020\u0006H\u0002J\b\u0010\u0014\u001a\u00020\u000eH\u0016J\b\u0010\u0015\u001a\u00020\u000eH\u0014J\b\u0010\u0016\u001a\u00020\u000eH\u0002R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001a\u0010\n\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\f0\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2 = {"Lim/vector/app/features/debug/DebugPermissionActivity;", "Lim/vector/app/core/platform/VectorBaseActivity;", "Lim/vector/application/databinding/ActivityDebugPermissionBinding;", "()V", "allPermissions", "", "", "dialogOrSnackbar", "", "lastPermissions", "launcher", "Landroidx/activity/result/ActivityResultLauncher;", "", "checkPerm", "", "getAndroid13Permissions", "getBinding", "getCoordinatorLayout", "Landroidx/coordinatorlayout/widget/CoordinatorLayout;", "getStatus", "initUiAndData", "onResume", "refresh", "Secretum-v1.0.0(1)_gplayDebug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class DebugPermissionActivity extends im.vector.app.core.platform.VectorBaseActivity<im.vector.application.databinding.ActivityDebugPermissionBinding> {
    private final java.util.List<java.lang.String> allPermissions = null;
    private java.util.List<java.lang.String> lastPermissions;
    private boolean dialogOrSnackbar = false;
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String[]> launcher = null;
    
    public DebugPermissionActivity() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public im.vector.application.databinding.ActivityDebugPermissionBinding getBinding() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public androidx.coordinatorlayout.widget.CoordinatorLayout getCoordinatorLayout() {
        return null;
    }
    
    private final java.util.List<java.lang.String> getAndroid13Permissions() {
        return null;
    }
    
    @java.lang.Override()
    public void initUiAndData() {
    }
    
    private final void checkPerm() {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void refresh() {
    }
    
    private final java.lang.String getStatus() {
        return null;
    }
}