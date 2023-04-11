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
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u00a8\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\'J\u0010\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\'J\u0010\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020 H\'J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020$H\'J\u0010\u0010%\u001a\u00020&2\u0006\u0010\'\u001a\u00020(H\'J\u0010\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,H\'J\u0010\u0010-\u001a\u00020.2\u0006\u0010/\u001a\u000200H\'J\u0010\u00101\u001a\u0002022\u0006\u00103\u001a\u000204H\'J\u0010\u00105\u001a\u0002062\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u00067"}, d2 = {"Lim/vector/app/core/di/VectorBindModule;", "", "()V", "bindAnalyticsTracker", "Lim/vector/app/features/analytics/AnalyticsTracker;", "analytics", "Lim/vector/app/features/analytics/impl/DefaultVectorAnalytics;", "bindAutoAcceptInvites", "Lim/vector/app/features/invite/AutoAcceptInvites;", "autoAcceptInvites", "Lim/vector/app/features/invite/CompileTimeAutoAcceptInvites;", "bindDefaultClock", "Lim/vector/app/core/time/Clock;", "clock", "Lim/vector/app/core/time/DefaultClock;", "bindEmojiSpanify", "Lim/vector/app/EmojiSpanify;", "emojiCompatWrapper", "Lim/vector/app/EmojiCompatWrapper;", "bindErrorFormatter", "Lim/vector/app/core/error/ErrorFormatter;", "formatter", "Lim/vector/app/core/error/DefaultErrorFormatter;", "bindErrorTracker", "Lim/vector/app/features/analytics/errors/ErrorTracker;", "bindFontScale", "Lim/vector/app/features/settings/FontScalePreferences;", "fontScale", "Lim/vector/app/features/settings/FontScalePreferencesImpl;", "bindGetDeviceInfoUseCase", "Lim/vector/app/core/device/GetDeviceInfoUseCase;", "getDeviceInfoUseCase", "Lim/vector/app/core/device/DefaultGetDeviceInfoUseCase;", "bindNavigator", "Lim/vector/app/features/navigation/Navigator;", "navigator", "Lim/vector/app/features/navigation/DefaultNavigator;", "bindPinCodeStore", "Lim/vector/app/features/pin/PinCodeStore;", "store", "Lim/vector/app/features/pin/SharedPrefPinCodeStore;", "bindSpaceStateHandler", "Lim/vector/app/SpaceStateHandler;", "spaceStateHandlerImpl", "Lim/vector/app/SpaceStateHandlerImpl;", "bindSystemSettingsProvide", "Lim/vector/app/core/utils/SystemSettingsProvider;", "provider", "Lim/vector/app/core/utils/AndroidSystemSettingsProvider;", "bindUiStateRepository", "Lim/vector/app/features/ui/UiStateRepository;", "repository", "Lim/vector/app/features/ui/SharedPreferencesUiStateRepository;", "bindVectorAnalytics", "Lim/vector/app/features/analytics/VectorAnalytics;", "vector-app_gplayDebug"})
@dagger.Module()
public abstract class VectorBindModule {
    
    public VectorBindModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.navigation.Navigator bindNavigator(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.navigation.DefaultNavigator navigator);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.analytics.VectorAnalytics bindVectorAnalytics(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.analytics.impl.DefaultVectorAnalytics analytics);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.analytics.errors.ErrorTracker bindErrorTracker(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.analytics.impl.DefaultVectorAnalytics analytics);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.analytics.AnalyticsTracker bindAnalyticsTracker(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.analytics.impl.DefaultVectorAnalytics analytics);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.error.ErrorFormatter bindErrorFormatter(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.error.DefaultErrorFormatter formatter);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.ui.UiStateRepository bindUiStateRepository(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.ui.SharedPreferencesUiStateRepository repository);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.pin.PinCodeStore bindPinCodeStore(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.pin.SharedPrefPinCodeStore store);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.invite.AutoAcceptInvites bindAutoAcceptInvites(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.invite.CompileTimeAutoAcceptInvites autoAcceptInvites);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.time.Clock bindDefaultClock(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.time.DefaultClock clock);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.EmojiSpanify bindEmojiSpanify(@org.jetbrains.annotations.NotNull()
    im.vector.app.EmojiCompatWrapper emojiCompatWrapper);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.settings.FontScalePreferences bindFontScale(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.FontScalePreferencesImpl fontScale);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.utils.SystemSettingsProvider bindSystemSettingsProvide(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.utils.AndroidSystemSettingsProvider provider);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.SpaceStateHandler bindSpaceStateHandler(@org.jetbrains.annotations.NotNull()
    im.vector.app.SpaceStateHandlerImpl spaceStateHandlerImpl);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.device.GetDeviceInfoUseCase bindGetDeviceInfoUseCase(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.device.DefaultGetDeviceInfoUseCase getDeviceInfoUseCase);
}