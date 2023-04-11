package im.vector.app.features.debug.features;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import im.vector.app.config.OnboardingVariant;
import im.vector.app.features.DefaultVectorFeatures;
import im.vector.app.features.VectorFeatures;
import kotlin.reflect.KClass;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000f\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\b\u0010\n\u001a\u00020\u000bH\u0016J\b\u0010\f\u001a\u00020\u000bH\u0016J$\u0010\r\u001a\u00020\u000b\"\u000e\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u000f2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0011J\u001a\u0010\u0012\u001a\u00020\u000b\"\u0004\b\u0000\u0010\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0014J\b\u0010\u0015\u001a\u00020\u000bH\u0016J\b\u0010\u0016\u001a\u00020\u000bH\u0016J\b\u0010\u0017\u001a\u00020\u000bH\u0016J\b\u0010\u0018\u001a\u00020\u000bH\u0016J\b\u0010\u0019\u001a\u00020\u000bH\u0016J\b\u0010\u001a\u001a\u00020\u000bH\u0016J\b\u0010\u001b\u001a\u00020\u000bH\u0016J\b\u0010\u001c\u001a\u00020\u000bH\u0016J\b\u0010\u001d\u001a\u00020\u000bH\u0016J\b\u0010\u001e\u001a\u00020\u000bH\u0016J\b\u0010\u001f\u001a\u00020\u000bH\u0016J\b\u0010 \u001a\u00020\u000bH\u0016J\b\u0010!\u001a\u00020\u000bH\u0016J\b\u0010\"\u001a\u00020\u000bH\u0016J\b\u0010#\u001a\u00020$H\u0016J)\u0010%\u001a\u00020\t\"\u0004\b\u0000\u0010\u000e2\b\u0010&\u001a\u0004\u0018\u0001H\u000e2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0014\u00a2\u0006\u0002\u0010\'J3\u0010(\u001a\u00020\t\"\u000e\b\u0000\u0010\u000e*\b\u0012\u0004\u0012\u0002H\u000e0\u000f2\b\u0010&\u001a\u0004\u0018\u0001H\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u000e0\u0011\u00a2\u0006\u0002\u0010)J\u001d\u0010*\u001a\u0004\u0018\u00010\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0014H\u0002\u00a2\u0006\u0002\u0010+J\b\u0010,\u001a\u00020\tH\u0002J\u001c\u0010-\u001a\u00020\t2\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u0002010/H\u0002R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u00062"}, d2 = {"Lim/vector/app/features/debug/features/DebugVectorFeatures;", "Lim/vector/app/features/VectorFeatures;", "context", "Landroid/content/Context;", "vectorFeatures", "Lim/vector/app/features/DefaultVectorFeatures;", "(Landroid/content/Context;Lim/vector/app/features/DefaultVectorFeatures;)V", "dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "allowExternalUnifiedPushDistributors", "", "forceUsageOfOpusEncoder", "hasEnumOverride", "T", "", "type", "Lkotlin/reflect/KClass;", "hasOverride", "key", "Landroidx/datastore/preferences/core/Preferences$Key;", "isLocationSharingEnabled", "isNewAppLayoutFeatureEnabled", "isOnboardingAlreadyHaveAccountSplashEnabled", "isOnboardingCombinedLoginEnabled", "isOnboardingCombinedRegisterEnabled", "isOnboardingPersonalizeEnabled", "isOnboardingSplashCarouselEnabled", "isOnboardingUseCaseEnabled", "isQrCodeLoginEnabled", "isQrCodeLoginForAllServers", "isReciprocateQrCodeLogin", "isScreenSharingEnabled", "isUnverifiedSessionsAlertEnabled", "isVoiceBroadcastEnabled", "onboardingVariant", "Lim/vector/app/config/OnboardingVariant;", "override", "value", "(Ljava/lang/Object;Landroidx/datastore/preferences/core/Preferences$Key;)Landroidx/datastore/preferences/core/Preferences;", "overrideEnum", "(Ljava/lang/Enum;Lkotlin/reflect/KClass;)Landroidx/datastore/preferences/core/Preferences;", "read", "(Landroidx/datastore/preferences/core/Preferences$Key;)Ljava/lang/Boolean;", "readPreferences", "updatePreferences", "block", "Lkotlin/Function1;", "Landroidx/datastore/preferences/core/MutablePreferences;", "", "vector-app_gplayDebug"})
public final class DebugVectorFeatures implements im.vector.app.features.VectorFeatures {
    private final im.vector.app.features.DefaultVectorFeatures vectorFeatures = null;
    private final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> dataStore = null;
    
    public DebugVectorFeatures(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.DefaultVectorFeatures vectorFeatures) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public im.vector.app.config.OnboardingVariant onboardingVariant() {
        return null;
    }
    
    @java.lang.Override()
    public boolean isOnboardingAlreadyHaveAccountSplashEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isOnboardingSplashCarouselEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isOnboardingUseCaseEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isOnboardingPersonalizeEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isOnboardingCombinedRegisterEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isOnboardingCombinedLoginEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean allowExternalUnifiedPushDistributors() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isScreenSharingEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isLocationSharingEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean forceUsageOfOpusEncoder() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isNewAppLayoutFeatureEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isQrCodeLoginEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isQrCodeLoginForAllServers() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isReciprocateQrCodeLogin() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isVoiceBroadcastEnabled() {
        return false;
    }
    
    @java.lang.Override()
    public boolean isUnverifiedSessionsAlertEnabled() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final <T extends java.lang.Object>androidx.datastore.preferences.core.Preferences override(@org.jetbrains.annotations.Nullable()
    T value, @org.jetbrains.annotations.NotNull()
    androidx.datastore.preferences.core.Preferences.Key<T> key) {
        return null;
    }
    
    public final <T extends java.lang.Object>boolean hasOverride(@org.jetbrains.annotations.NotNull()
    androidx.datastore.preferences.core.Preferences.Key<T> key) {
        return false;
    }
    
    public final <T extends java.lang.Enum<T>>boolean hasEnumOverride(@org.jetbrains.annotations.NotNull()
    kotlin.reflect.KClass<T> type) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final <T extends java.lang.Enum<T>>androidx.datastore.preferences.core.Preferences overrideEnum(@org.jetbrains.annotations.Nullable()
    T value, @org.jetbrains.annotations.NotNull()
    kotlin.reflect.KClass<T> type) {
        return null;
    }
    
    private final java.lang.Boolean read(androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> key) {
        return null;
    }
    
    private final androidx.datastore.preferences.core.Preferences readPreferences() {
        return null;
    }
    
    private final androidx.datastore.preferences.core.Preferences updatePreferences(kotlin.jvm.functions.Function1<? super androidx.datastore.preferences.core.MutablePreferences, kotlin.Unit> block) {
        return null;
    }
}