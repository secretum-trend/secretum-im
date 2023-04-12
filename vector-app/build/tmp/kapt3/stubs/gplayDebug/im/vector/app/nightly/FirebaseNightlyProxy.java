package im.vector.app.nightly;

import android.content.SharedPreferences;
import com.google.firebase.appdistribution.FirebaseAppDistribution;
import com.google.firebase.appdistribution.FirebaseAppDistributionException;
import im.vector.app.core.di.DefaultPreferences;
import im.vector.app.core.resources.BuildMeta;
import im.vector.app.core.time.Clock;
import im.vector.app.features.home.NightlyProxy;
import timber.log.Timber;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u0000 \u000e2\u00020\u0001:\u0001\u000eB!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0001\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\t\u001a\u00020\nH\u0016J\b\u0010\u000b\u001a\u00020\nH\u0016J\b\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lim/vector/app/nightly/FirebaseNightlyProxy;", "Lim/vector/app/features/home/NightlyProxy;", "clock", "Lim/vector/app/core/time/Clock;", "sharedPreferences", "Landroid/content/SharedPreferences;", "buildMeta", "Lim/vector/app/core/resources/BuildMeta;", "(Lim/vector/app/core/time/Clock;Landroid/content/SharedPreferences;Lim/vector/app/core/resources/BuildMeta;)V", "canDisplayPopup", "", "isNightlyBuild", "updateApplication", "", "Companion", "Secretum-v1.0.0(1)_gplayDebug"})
public final class FirebaseNightlyProxy implements im.vector.app.features.home.NightlyProxy {
    private final im.vector.app.core.time.Clock clock = null;
    private final android.content.SharedPreferences sharedPreferences = null;
    private final im.vector.app.core.resources.BuildMeta buildMeta = null;
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.nightly.FirebaseNightlyProxy.Companion Companion = null;
    private static final boolean POPUP_IS_ENABLED = false;
    private static final long A_DAY_IN_MILLIS = 8600000L;
    private static final java.lang.String SHARED_PREF_KEY = "LAST_NIGHTLY_POPUP_DAY";
    private static final java.util.List<java.lang.String> nightlyPackages = null;
    
    @javax.inject.Inject()
    public FirebaseNightlyProxy(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.time.Clock clock, @org.jetbrains.annotations.NotNull()
    @im.vector.app.core.di.DefaultPreferences()
    android.content.SharedPreferences sharedPreferences, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.resources.BuildMeta buildMeta) {
        super();
    }
    
    @java.lang.Override()
    public boolean isNightlyBuild() {
        return false;
    }
    
    @java.lang.Override()
    public void updateApplication() {
    }
    
    @java.lang.Override()
    public boolean canDisplayPopup() {
        return false;
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082T\u00a2\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000b"}, d2 = {"Lim/vector/app/nightly/FirebaseNightlyProxy$Companion;", "", "()V", "A_DAY_IN_MILLIS", "", "POPUP_IS_ENABLED", "", "SHARED_PREF_KEY", "", "nightlyPackages", "", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}