package im.vector.app.push.fcm;

import androidx.fragment.app.Fragment;
import im.vector.app.core.pushers.UnifiedPushHelper;
import im.vector.app.features.VectorFeatures;
import im.vector.app.features.push.NotificationTroubleshootTestManagerFactory;
import im.vector.app.features.settings.troubleshoot.NotificationTroubleshootTestManager;
import im.vector.app.features.settings.troubleshoot.TestAccountSettings;
import im.vector.app.features.settings.troubleshoot.TestAvailableUnifiedPushDistributors;
import im.vector.app.features.settings.troubleshoot.TestCurrentUnifiedPushDistributor;
import im.vector.app.features.settings.troubleshoot.TestDeviceSettings;
import im.vector.app.features.settings.troubleshoot.TestEndpointAsTokenRegistration;
import im.vector.app.features.settings.troubleshoot.TestNotification;
import im.vector.app.features.settings.troubleshoot.TestPushFromPushGateway;
import im.vector.app.features.settings.troubleshoot.TestPushRulesSettings;
import im.vector.app.features.settings.troubleshoot.TestSystemSettings;
import im.vector.app.features.settings.troubleshoot.TestUnifiedPushEndpoint;
import im.vector.app.features.settings.troubleshoot.TestUnifiedPushGateway;
import im.vector.app.gplay.features.settings.troubleshoot.TestFirebaseToken;
import im.vector.app.gplay.features.settings.troubleshoot.TestPlayServices;
import im.vector.app.gplay.features.settings.troubleshoot.TestTokenRegistration;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000x\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0087\u0001\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\u0006\u0010 \u001a\u00020!\u00a2\u0006\u0002\u0010\"J\u0010\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020&H\u0016R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010 \u001a\u00020!X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\'"}, d2 = {"Lim/vector/app/push/fcm/GoogleNotificationTroubleshootTestManagerFactory;", "Lim/vector/app/features/push/NotificationTroubleshootTestManagerFactory;", "unifiedPushHelper", "Lim/vector/app/core/pushers/UnifiedPushHelper;", "testSystemSettings", "Lim/vector/app/features/settings/troubleshoot/TestSystemSettings;", "testAccountSettings", "Lim/vector/app/features/settings/troubleshoot/TestAccountSettings;", "testDeviceSettings", "Lim/vector/app/features/settings/troubleshoot/TestDeviceSettings;", "testPushRulesSettings", "Lim/vector/app/features/settings/troubleshoot/TestPushRulesSettings;", "testPlayServices", "Lim/vector/app/gplay/features/settings/troubleshoot/TestPlayServices;", "testFirebaseToken", "Lim/vector/app/gplay/features/settings/troubleshoot/TestFirebaseToken;", "testTokenRegistration", "Lim/vector/app/gplay/features/settings/troubleshoot/TestTokenRegistration;", "testCurrentUnifiedPushDistributor", "Lim/vector/app/features/settings/troubleshoot/TestCurrentUnifiedPushDistributor;", "testUnifiedPushGateway", "Lim/vector/app/features/settings/troubleshoot/TestUnifiedPushGateway;", "testUnifiedPushEndpoint", "Lim/vector/app/features/settings/troubleshoot/TestUnifiedPushEndpoint;", "testAvailableUnifiedPushDistributors", "Lim/vector/app/features/settings/troubleshoot/TestAvailableUnifiedPushDistributors;", "testEndpointAsTokenRegistration", "Lim/vector/app/features/settings/troubleshoot/TestEndpointAsTokenRegistration;", "testPushFromPushGateway", "Lim/vector/app/features/settings/troubleshoot/TestPushFromPushGateway;", "testNotification", "Lim/vector/app/features/settings/troubleshoot/TestNotification;", "vectorFeatures", "Lim/vector/app/features/VectorFeatures;", "(Lim/vector/app/core/pushers/UnifiedPushHelper;Lim/vector/app/features/settings/troubleshoot/TestSystemSettings;Lim/vector/app/features/settings/troubleshoot/TestAccountSettings;Lim/vector/app/features/settings/troubleshoot/TestDeviceSettings;Lim/vector/app/features/settings/troubleshoot/TestPushRulesSettings;Lim/vector/app/gplay/features/settings/troubleshoot/TestPlayServices;Lim/vector/app/gplay/features/settings/troubleshoot/TestFirebaseToken;Lim/vector/app/gplay/features/settings/troubleshoot/TestTokenRegistration;Lim/vector/app/features/settings/troubleshoot/TestCurrentUnifiedPushDistributor;Lim/vector/app/features/settings/troubleshoot/TestUnifiedPushGateway;Lim/vector/app/features/settings/troubleshoot/TestUnifiedPushEndpoint;Lim/vector/app/features/settings/troubleshoot/TestAvailableUnifiedPushDistributors;Lim/vector/app/features/settings/troubleshoot/TestEndpointAsTokenRegistration;Lim/vector/app/features/settings/troubleshoot/TestPushFromPushGateway;Lim/vector/app/features/settings/troubleshoot/TestNotification;Lim/vector/app/features/VectorFeatures;)V", "create", "Lim/vector/app/features/settings/troubleshoot/NotificationTroubleshootTestManager;", "fragment", "Landroidx/fragment/app/Fragment;", "Secretum-v1.0.0(1)_gplayDebug"})
public final class GoogleNotificationTroubleshootTestManagerFactory implements im.vector.app.features.push.NotificationTroubleshootTestManagerFactory {
    private final im.vector.app.core.pushers.UnifiedPushHelper unifiedPushHelper = null;
    private final im.vector.app.features.settings.troubleshoot.TestSystemSettings testSystemSettings = null;
    private final im.vector.app.features.settings.troubleshoot.TestAccountSettings testAccountSettings = null;
    private final im.vector.app.features.settings.troubleshoot.TestDeviceSettings testDeviceSettings = null;
    private final im.vector.app.features.settings.troubleshoot.TestPushRulesSettings testPushRulesSettings = null;
    private final im.vector.app.gplay.features.settings.troubleshoot.TestPlayServices testPlayServices = null;
    private final im.vector.app.gplay.features.settings.troubleshoot.TestFirebaseToken testFirebaseToken = null;
    private final im.vector.app.gplay.features.settings.troubleshoot.TestTokenRegistration testTokenRegistration = null;
    private final im.vector.app.features.settings.troubleshoot.TestCurrentUnifiedPushDistributor testCurrentUnifiedPushDistributor = null;
    private final im.vector.app.features.settings.troubleshoot.TestUnifiedPushGateway testUnifiedPushGateway = null;
    private final im.vector.app.features.settings.troubleshoot.TestUnifiedPushEndpoint testUnifiedPushEndpoint = null;
    private final im.vector.app.features.settings.troubleshoot.TestAvailableUnifiedPushDistributors testAvailableUnifiedPushDistributors = null;
    private final im.vector.app.features.settings.troubleshoot.TestEndpointAsTokenRegistration testEndpointAsTokenRegistration = null;
    private final im.vector.app.features.settings.troubleshoot.TestPushFromPushGateway testPushFromPushGateway = null;
    private final im.vector.app.features.settings.troubleshoot.TestNotification testNotification = null;
    private final im.vector.app.features.VectorFeatures vectorFeatures = null;
    
    @javax.inject.Inject()
    public GoogleNotificationTroubleshootTestManagerFactory(@org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.UnifiedPushHelper unifiedPushHelper, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestSystemSettings testSystemSettings, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestAccountSettings testAccountSettings, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestDeviceSettings testDeviceSettings, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestPushRulesSettings testPushRulesSettings, @org.jetbrains.annotations.NotNull()
    im.vector.app.gplay.features.settings.troubleshoot.TestPlayServices testPlayServices, @org.jetbrains.annotations.NotNull()
    im.vector.app.gplay.features.settings.troubleshoot.TestFirebaseToken testFirebaseToken, @org.jetbrains.annotations.NotNull()
    im.vector.app.gplay.features.settings.troubleshoot.TestTokenRegistration testTokenRegistration, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestCurrentUnifiedPushDistributor testCurrentUnifiedPushDistributor, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestUnifiedPushGateway testUnifiedPushGateway, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestUnifiedPushEndpoint testUnifiedPushEndpoint, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestAvailableUnifiedPushDistributors testAvailableUnifiedPushDistributors, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestEndpointAsTokenRegistration testEndpointAsTokenRegistration, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestPushFromPushGateway testPushFromPushGateway, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TestNotification testNotification, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.VectorFeatures vectorFeatures) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public im.vector.app.features.settings.troubleshoot.NotificationTroubleshootTestManager create(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.Fragment fragment) {
        return null;
    }
}