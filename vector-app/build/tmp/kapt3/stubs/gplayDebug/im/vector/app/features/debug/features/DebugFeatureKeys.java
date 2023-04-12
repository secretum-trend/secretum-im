package im.vector.app.features.debug.features;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import im.vector.app.config.OnboardingVariant;
import im.vector.app.features.DefaultVectorFeatures;
import im.vector.app.features.VectorFeatures;
import kotlin.reflect.KClass;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b!\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0007R\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0007R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0007R\u0017\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0007R\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0007R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0007R\u0017\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0007R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0007R\u0017\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0007R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0007R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0007R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010\u0007R\u0017\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0007R\u0017\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b#\u0010\u0007R\u0017\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b%\u0010\u0007\u00a8\u0006&"}, d2 = {"Lim/vector/app/features/debug/features/DebugFeatureKeys;", "", "()V", "allowExternalUnifiedPushDistributors", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "getAllowExternalUnifiedPushDistributors", "()Landroidx/datastore/preferences/core/Preferences$Key;", "forceUsageOfOpusEncoder", "getForceUsageOfOpusEncoder", "liveLocationSharing", "getLiveLocationSharing", "newAppLayoutEnabled", "getNewAppLayoutEnabled", "onboardingAlreadyHaveAnAccount", "getOnboardingAlreadyHaveAnAccount", "onboardingCombinedLogin", "getOnboardingCombinedLogin", "onboardingCombinedRegister", "getOnboardingCombinedRegister", "onboardingPersonalize", "getOnboardingPersonalize", "onboardingSplashCarousel", "getOnboardingSplashCarousel", "onboardingUseCase", "getOnboardingUseCase", "qrCodeLoginEnabled", "getQrCodeLoginEnabled", "qrCodeLoginForAllServers", "getQrCodeLoginForAllServers", "reciprocateQrCodeLogin", "getReciprocateQrCodeLogin", "screenSharing", "getScreenSharing", "unverifiedSessionsAlertEnabled", "getUnverifiedSessionsAlertEnabled", "voiceBroadcastEnabled", "getVoiceBroadcastEnabled", "Secretum-v1.0.0(1)_gplayDebug"})
public final class DebugFeatureKeys {
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.features.debug.features.DebugFeatureKeys INSTANCE = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> onboardingAlreadyHaveAnAccount = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> onboardingSplashCarousel = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> onboardingUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> onboardingPersonalize = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> onboardingCombinedRegister = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> onboardingCombinedLogin = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> allowExternalUnifiedPushDistributors = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> liveLocationSharing = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> screenSharing = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> forceUsageOfOpusEncoder = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> newAppLayoutEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> qrCodeLoginEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> qrCodeLoginForAllServers = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> reciprocateQrCodeLogin = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> voiceBroadcastEnabled = null;
    @org.jetbrains.annotations.NotNull()
    private static final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> unverifiedSessionsAlertEnabled = null;
    
    private DebugFeatureKeys() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getOnboardingAlreadyHaveAnAccount() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getOnboardingSplashCarousel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getOnboardingUseCase() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getOnboardingPersonalize() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getOnboardingCombinedRegister() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getOnboardingCombinedLogin() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getAllowExternalUnifiedPushDistributors() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getLiveLocationSharing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getScreenSharing() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getForceUsageOfOpusEncoder() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getNewAppLayoutEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getQrCodeLoginEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getQrCodeLoginForAllServers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getReciprocateQrCodeLogin() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getVoiceBroadcastEnabled() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getUnverifiedSessionsAlertEnabled() {
        return null;
    }
}