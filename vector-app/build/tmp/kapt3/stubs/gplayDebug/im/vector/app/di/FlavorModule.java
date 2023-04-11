package im.vector.app.di;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import im.vector.app.GoogleFlavorLegals;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.resources.AppNameProvider;
import im.vector.app.core.resources.DefaultAppNameProvider;
import im.vector.app.core.resources.DefaultLocaleProvider;
import im.vector.app.core.resources.LocaleProvider;
import im.vector.app.core.services.GuardServiceStarter;
import im.vector.app.features.home.NightlyProxy;
import im.vector.app.features.settings.legals.FlavorLegals;
import im.vector.app.nightly.FirebaseNightlyProxy;
import im.vector.app.push.fcm.GoogleFcmHelper;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\'\u0018\u0000 \u00172\u00020\u0001:\u0001\u0017B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\'J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\'J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\'J\u0010\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u0016H\'\u00a8\u0006\u0018"}, d2 = {"Lim/vector/app/di/FlavorModule;", "", "()V", "bindsAppNameProvider", "Lim/vector/app/core/resources/AppNameProvider;", "appNameProvider", "Lim/vector/app/core/resources/DefaultAppNameProvider;", "bindsFcmHelper", "Lim/vector/app/core/pushers/FcmHelper;", "fcmHelper", "Lim/vector/app/push/fcm/GoogleFcmHelper;", "bindsFlavorLegals", "Lim/vector/app/features/settings/legals/FlavorLegals;", "legals", "Lim/vector/app/GoogleFlavorLegals;", "bindsLocaleProvider", "Lim/vector/app/core/resources/LocaleProvider;", "localeProvider", "Lim/vector/app/core/resources/DefaultLocaleProvider;", "bindsNightlyProxy", "Lim/vector/app/features/home/NightlyProxy;", "nightlyProxy", "Lim/vector/app/nightly/FirebaseNightlyProxy;", "Companion", "vector-app_gplayDebug"})
@dagger.Module()
public abstract class FlavorModule {
    @org.jetbrains.annotations.NotNull()
    public static final im.vector.app.di.FlavorModule.Companion Companion = null;
    
    public FlavorModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.home.NightlyProxy bindsNightlyProxy(@org.jetbrains.annotations.NotNull()
    im.vector.app.nightly.FirebaseNightlyProxy nightlyProxy);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.pushers.FcmHelper bindsFcmHelper(@org.jetbrains.annotations.NotNull()
    im.vector.app.push.fcm.GoogleFcmHelper fcmHelper);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.resources.LocaleProvider bindsLocaleProvider(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.resources.DefaultLocaleProvider localeProvider);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.core.resources.AppNameProvider bindsAppNameProvider(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.resources.DefaultAppNameProvider appNameProvider);
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.settings.legals.FlavorLegals bindsFlavorLegals(@org.jetbrains.annotations.NotNull()
    im.vector.app.GoogleFlavorLegals legals);
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007\u00a8\u0006\u0005"}, d2 = {"Lim/vector/app/di/FlavorModule$Companion;", "", "()V", "provideGuardServiceStarter", "Lim/vector/app/core/services/GuardServiceStarter;", "vector-app_gplayDebug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        @dagger.Provides()
        public final im.vector.app.core.services.GuardServiceStarter provideGuardServiceStarter() {
            return null;
        }
    }
}