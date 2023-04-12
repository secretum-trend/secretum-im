package im.vector.app.flipper;

import android.content.Context;
import android.os.Build;
import com.facebook.flipper.android.AndroidFlipperClient;
import com.facebook.flipper.android.utils.FlipperUtils;
import com.facebook.flipper.plugins.crashreporter.CrashReporterPlugin;
import com.facebook.flipper.plugins.databases.DatabasesFlipperPlugin;
import com.facebook.flipper.plugins.inspector.DescriptorMapping;
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin;
import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor;
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin;
import com.facebook.flipper.plugins.sharedpreferences.SharedPreferencesFlipperPlugin;
import com.facebook.soloader.SoLoader;
import com.kgurgul.flipper.RealmDatabaseDriver;
import com.kgurgul.flipper.RealmDatabaseProvider;
import im.vector.app.core.debug.FlipperProxy;
import io.realm.RealmConfiguration;
import okhttp3.Interceptor;
import org.matrix.android.sdk.api.Matrix;
import javax.inject.Inject;
import javax.inject.Singleton;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\rH\u0016J\n\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u00068BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\u0005\u0010\u0007R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0010"}, d2 = {"Lim/vector/app/flipper/VectorFlipperProxy;", "Lim/vector/app/core/debug/FlipperProxy;", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "isEnabled", "", "()Z", "networkFlipperPlugin", "Lcom/facebook/flipper/plugins/network/NetworkFlipperPlugin;", "init", "", "matrix", "Lorg/matrix/android/sdk/api/Matrix;", "networkInterceptor", "Lokhttp3/Interceptor;", "Secretum-v1.0.0(1)_gplayDebug"})
@javax.inject.Singleton()
public final class VectorFlipperProxy implements im.vector.app.core.debug.FlipperProxy {
    private final android.content.Context context = null;
    private final com.facebook.flipper.plugins.network.NetworkFlipperPlugin networkFlipperPlugin = null;
    
    @javax.inject.Inject()
    public VectorFlipperProxy(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    private final boolean isEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public void init(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.Matrix matrix) {
    }
    
    @org.jetbrains.annotations.Nullable()
    @java.lang.Override()
    public okhttp3.Interceptor networkInterceptor() {
        return null;
    }
}