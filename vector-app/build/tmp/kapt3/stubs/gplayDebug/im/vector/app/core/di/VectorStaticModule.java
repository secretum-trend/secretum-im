package im.vector.app.core.di;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import androidx.preference.PreferenceManager;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import im.vector.app.EmojiCompatWrapper;
import im.vector.app.EmojiSpanify;
import im.vector.app.SpaceStateHandler;
import im.vector.app.SpaceStateHandlerImpl;
import im.vector.app.config.Config;
import im.vector.app.core.debug.FlipperProxy;
import im.vector.app.core.device.DefaultGetDeviceInfoUseCase;
import im.vector.app.core.device.GetDeviceInfoUseCase;
import im.vector.app.core.dispatchers.CoroutineDispatchers;
import im.vector.app.core.error.DefaultErrorFormatter;
import im.vector.app.core.error.ErrorFormatter;
import im.vector.app.core.resources.BuildMeta;
import im.vector.app.core.time.Clock;
import im.vector.app.core.time.DefaultClock;
import im.vector.app.core.utils.AndroidSystemSettingsProvider;
import im.vector.app.core.utils.SystemSettingsProvider;
import im.vector.app.features.analytics.AnalyticsTracker;
import im.vector.app.features.analytics.VectorAnalytics;
import im.vector.app.features.analytics.errors.ErrorTracker;
import im.vector.app.features.analytics.impl.DefaultVectorAnalytics;
import im.vector.app.features.analytics.metrics.VectorPlugins;
import im.vector.app.features.configuration.VectorCustomEventTypesProvider;
import im.vector.app.features.invite.AutoAcceptInvites;
import im.vector.app.features.invite.CompileTimeAutoAcceptInvites;
import im.vector.app.features.navigation.DefaultNavigator;
import im.vector.app.features.navigation.Navigator;
import im.vector.app.features.pin.PinCodeStore;
import im.vector.app.features.pin.SharedPrefPinCodeStore;
import im.vector.app.features.room.VectorRoomDisplayNameFallbackProvider;
import im.vector.app.features.settings.FontScalePreferences;
import im.vector.app.features.settings.FontScalePreferencesImpl;
import im.vector.app.features.settings.VectorPreferences;
import im.vector.app.features.ui.SharedPreferencesUiStateRepository;
import im.vector.app.features.ui.UiStateRepository;
import im.vector.application.BuildConfig;
import kotlinx.coroutines.DelicateCoroutinesApi;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;
import org.matrix.android.sdk.api.Matrix;
import org.matrix.android.sdk.api.MatrixConfiguration;
import org.matrix.android.sdk.api.SyncConfig;
import org.matrix.android.sdk.api.auth.AuthenticationService;
import org.matrix.android.sdk.api.auth.HomeServerHistoryService;
import org.matrix.android.sdk.api.legacy.LegacySessionImporter;
import org.matrix.android.sdk.api.raw.RawService;
import org.matrix.android.sdk.api.session.Session;
import org.matrix.android.sdk.api.session.sync.filter.SyncFilterParams;
import org.matrix.android.sdk.api.settings.LightweightSettingsStorage;
import javax.inject.Singleton;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0098\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c7\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0007J\b\u0010\t\u001a\u00020\nH\u0007J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0007J\b\u0010\u000f\u001a\u00020\u0010H\u0007J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u0014H\u0007J\u0010\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\fH\u0007J\b\u0010\u0018\u001a\u00020\u0004H\u0007J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0018\u0010\u001f\u001a\u00020\b2\u0006\u0010\u0017\u001a\u00020\f2\u0006\u0010 \u001a\u00020!H\u0007J0\u0010\"\u001a\u00020!2\u0006\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\u0007J\b\u0010-\u001a\u00020.H\u0007J\u0010\u0010/\u001a\u0002002\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u0010\u00101\u001a\u0002022\u0006\u0010\u0017\u001a\u00020\fH\u0007J\u0010\u00103\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\fH\u0007\u00a8\u00064"}, d2 = {"Lim/vector/app/core/di/VectorStaticModule;", "", "()V", "providesApplicationCoroutineScope", "Lkotlinx/coroutines/CoroutineScope;", "providesAuthenticationService", "Lorg/matrix/android/sdk/api/auth/AuthenticationService;", "matrix", "Lorg/matrix/android/sdk/api/Matrix;", "providesBuildMeta", "Lim/vector/app/core/resources/BuildMeta;", "providesContext", "Landroid/content/Context;", "application", "Landroid/app/Application;", "providesCoroutineDispatchers", "Lim/vector/app/core/dispatchers/CoroutineDispatchers;", "providesCurrentSession", "Lorg/matrix/android/sdk/api/session/Session;", "activeSessionHolder", "Lim/vector/app/core/di/ActiveSessionHolder;", "providesDefaultSharedPreferences", "Landroid/content/SharedPreferences;", "context", "providesGlobalScope", "providesHomeServerHistoryService", "Lorg/matrix/android/sdk/api/auth/HomeServerHistoryService;", "providesLegacySessionImporter", "Lorg/matrix/android/sdk/api/legacy/LegacySessionImporter;", "providesLightweightSettingsStorage", "Lorg/matrix/android/sdk/api/settings/LightweightSettingsStorage;", "providesMatrix", "configuration", "Lorg/matrix/android/sdk/api/MatrixConfiguration;", "providesMatrixConfiguration", "vectorPreferences", "Lim/vector/app/features/settings/VectorPreferences;", "vectorRoomDisplayNameFallbackProvider", "Lim/vector/app/features/room/VectorRoomDisplayNameFallbackProvider;", "flipperProxy", "Lim/vector/app/core/debug/FlipperProxy;", "vectorPlugins", "Lim/vector/app/features/analytics/metrics/VectorPlugins;", "vectorCustomEventTypesProvider", "Lim/vector/app/features/configuration/VectorCustomEventTypesProvider;", "providesPhoneNumberUtil", "Lcom/google/i18n/phonenumbers/PhoneNumberUtil;", "providesRawService", "Lorg/matrix/android/sdk/api/raw/RawService;", "providesResources", "Landroid/content/res/Resources;", "providesSharedPreferences", "vector-app_gplayDebug"})
@dagger.Module()
public final class VectorStaticModule {
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.core.di.VectorStaticModule INSTANCE = null;
    
    private VectorStaticModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final android.content.Context providesContext(@org.jetbrains.annotations.NotNull()
    android.app.Application application) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final android.content.res.Resources providesResources(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final android.content.SharedPreferences providesSharedPreferences(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.MatrixConfiguration providesMatrixConfiguration(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.VectorPreferences vectorPreferences, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.room.VectorRoomDisplayNameFallbackProvider vectorRoomDisplayNameFallbackProvider, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.debug.FlipperProxy flipperProxy, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.analytics.metrics.VectorPlugins vectorPlugins, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.configuration.VectorCustomEventTypesProvider vectorCustomEventTypesProvider) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.Matrix providesMatrix(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.MatrixConfiguration configuration) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.session.Session providesCurrentSession(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.di.ActiveSessionHolder activeSessionHolder) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.legacy.LegacySessionImporter providesLegacySessionImporter(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.Matrix matrix) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.auth.AuthenticationService providesAuthenticationService(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.Matrix matrix) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.raw.RawService providesRawService(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.Matrix matrix) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.settings.LightweightSettingsStorage providesLightweightSettingsStorage(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.Matrix matrix) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final org.matrix.android.sdk.api.auth.HomeServerHistoryService providesHomeServerHistoryService(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.Matrix matrix) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final kotlinx.coroutines.CoroutineScope providesApplicationCoroutineScope() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final im.vector.app.core.dispatchers.CoroutineDispatchers providesCoroutineDispatchers() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.DelicateCoroutinesApi.class})
    @NamedGlobalScope()
    @dagger.Provides()
    public final kotlinx.coroutines.CoroutineScope providesGlobalScope() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Provides()
    public final com.google.i18n.phonenumbers.PhoneNumberUtil providesPhoneNumberUtil() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final im.vector.app.core.resources.BuildMeta providesBuildMeta() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @DefaultPreferences()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final android.content.SharedPreferences providesDefaultSharedPreferences(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}