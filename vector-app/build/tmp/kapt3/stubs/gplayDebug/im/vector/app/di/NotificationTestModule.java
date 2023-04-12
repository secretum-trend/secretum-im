package im.vector.app.di;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import im.vector.app.features.push.NotificationTroubleshootTestManagerFactory;
import im.vector.app.push.fcm.GoogleNotificationTroubleshootTestManagerFactory;

@dagger.hilt.InstallIn(value = {dagger.hilt.android.components.ActivityComponent.class})
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\'\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\'\u00a8\u0006\u0007"}, d2 = {"Lim/vector/app/di/NotificationTestModule;", "", "()V", "bindsNotificationTestFactory", "Lim/vector/app/features/push/NotificationTroubleshootTestManagerFactory;", "factory", "Lim/vector/app/push/fcm/GoogleNotificationTroubleshootTestManagerFactory;", "Secretum-v1.0.0(1)_gplayDebug"})
@dagger.Module()
public abstract class NotificationTestModule {
    
    public NotificationTestModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @dagger.Binds()
    public abstract im.vector.app.features.push.NotificationTroubleshootTestManagerFactory bindsNotificationTestFactory(@org.jetbrains.annotations.NotNull()
    im.vector.app.push.fcm.GoogleNotificationTroubleshootTestManagerFactory factory);
}