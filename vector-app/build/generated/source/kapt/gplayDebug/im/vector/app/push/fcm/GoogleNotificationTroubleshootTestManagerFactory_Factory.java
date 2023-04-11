package im.vector.app.push.fcm;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.pushers.UnifiedPushHelper;
import im.vector.app.features.VectorFeatures;
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
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class GoogleNotificationTroubleshootTestManagerFactory_Factory implements Factory<GoogleNotificationTroubleshootTestManagerFactory> {
  private final Provider<UnifiedPushHelper> unifiedPushHelperProvider;

  private final Provider<TestSystemSettings> testSystemSettingsProvider;

  private final Provider<TestAccountSettings> testAccountSettingsProvider;

  private final Provider<TestDeviceSettings> testDeviceSettingsProvider;

  private final Provider<TestPushRulesSettings> testPushRulesSettingsProvider;

  private final Provider<TestPlayServices> testPlayServicesProvider;

  private final Provider<TestFirebaseToken> testFirebaseTokenProvider;

  private final Provider<TestTokenRegistration> testTokenRegistrationProvider;

  private final Provider<TestCurrentUnifiedPushDistributor> testCurrentUnifiedPushDistributorProvider;

  private final Provider<TestUnifiedPushGateway> testUnifiedPushGatewayProvider;

  private final Provider<TestUnifiedPushEndpoint> testUnifiedPushEndpointProvider;

  private final Provider<TestAvailableUnifiedPushDistributors> testAvailableUnifiedPushDistributorsProvider;

  private final Provider<TestEndpointAsTokenRegistration> testEndpointAsTokenRegistrationProvider;

  private final Provider<TestPushFromPushGateway> testPushFromPushGatewayProvider;

  private final Provider<TestNotification> testNotificationProvider;

  private final Provider<VectorFeatures> vectorFeaturesProvider;

  public GoogleNotificationTroubleshootTestManagerFactory_Factory(
      Provider<UnifiedPushHelper> unifiedPushHelperProvider,
      Provider<TestSystemSettings> testSystemSettingsProvider,
      Provider<TestAccountSettings> testAccountSettingsProvider,
      Provider<TestDeviceSettings> testDeviceSettingsProvider,
      Provider<TestPushRulesSettings> testPushRulesSettingsProvider,
      Provider<TestPlayServices> testPlayServicesProvider,
      Provider<TestFirebaseToken> testFirebaseTokenProvider,
      Provider<TestTokenRegistration> testTokenRegistrationProvider,
      Provider<TestCurrentUnifiedPushDistributor> testCurrentUnifiedPushDistributorProvider,
      Provider<TestUnifiedPushGateway> testUnifiedPushGatewayProvider,
      Provider<TestUnifiedPushEndpoint> testUnifiedPushEndpointProvider,
      Provider<TestAvailableUnifiedPushDistributors> testAvailableUnifiedPushDistributorsProvider,
      Provider<TestEndpointAsTokenRegistration> testEndpointAsTokenRegistrationProvider,
      Provider<TestPushFromPushGateway> testPushFromPushGatewayProvider,
      Provider<TestNotification> testNotificationProvider,
      Provider<VectorFeatures> vectorFeaturesProvider) {
    this.unifiedPushHelperProvider = unifiedPushHelperProvider;
    this.testSystemSettingsProvider = testSystemSettingsProvider;
    this.testAccountSettingsProvider = testAccountSettingsProvider;
    this.testDeviceSettingsProvider = testDeviceSettingsProvider;
    this.testPushRulesSettingsProvider = testPushRulesSettingsProvider;
    this.testPlayServicesProvider = testPlayServicesProvider;
    this.testFirebaseTokenProvider = testFirebaseTokenProvider;
    this.testTokenRegistrationProvider = testTokenRegistrationProvider;
    this.testCurrentUnifiedPushDistributorProvider = testCurrentUnifiedPushDistributorProvider;
    this.testUnifiedPushGatewayProvider = testUnifiedPushGatewayProvider;
    this.testUnifiedPushEndpointProvider = testUnifiedPushEndpointProvider;
    this.testAvailableUnifiedPushDistributorsProvider = testAvailableUnifiedPushDistributorsProvider;
    this.testEndpointAsTokenRegistrationProvider = testEndpointAsTokenRegistrationProvider;
    this.testPushFromPushGatewayProvider = testPushFromPushGatewayProvider;
    this.testNotificationProvider = testNotificationProvider;
    this.vectorFeaturesProvider = vectorFeaturesProvider;
  }

  @Override
  public GoogleNotificationTroubleshootTestManagerFactory get() {
    return newInstance(unifiedPushHelperProvider.get(), testSystemSettingsProvider.get(), testAccountSettingsProvider.get(), testDeviceSettingsProvider.get(), testPushRulesSettingsProvider.get(), testPlayServicesProvider.get(), testFirebaseTokenProvider.get(), testTokenRegistrationProvider.get(), testCurrentUnifiedPushDistributorProvider.get(), testUnifiedPushGatewayProvider.get(), testUnifiedPushEndpointProvider.get(), testAvailableUnifiedPushDistributorsProvider.get(), testEndpointAsTokenRegistrationProvider.get(), testPushFromPushGatewayProvider.get(), testNotificationProvider.get(), vectorFeaturesProvider.get());
  }

  public static GoogleNotificationTroubleshootTestManagerFactory_Factory create(
      Provider<UnifiedPushHelper> unifiedPushHelperProvider,
      Provider<TestSystemSettings> testSystemSettingsProvider,
      Provider<TestAccountSettings> testAccountSettingsProvider,
      Provider<TestDeviceSettings> testDeviceSettingsProvider,
      Provider<TestPushRulesSettings> testPushRulesSettingsProvider,
      Provider<TestPlayServices> testPlayServicesProvider,
      Provider<TestFirebaseToken> testFirebaseTokenProvider,
      Provider<TestTokenRegistration> testTokenRegistrationProvider,
      Provider<TestCurrentUnifiedPushDistributor> testCurrentUnifiedPushDistributorProvider,
      Provider<TestUnifiedPushGateway> testUnifiedPushGatewayProvider,
      Provider<TestUnifiedPushEndpoint> testUnifiedPushEndpointProvider,
      Provider<TestAvailableUnifiedPushDistributors> testAvailableUnifiedPushDistributorsProvider,
      Provider<TestEndpointAsTokenRegistration> testEndpointAsTokenRegistrationProvider,
      Provider<TestPushFromPushGateway> testPushFromPushGatewayProvider,
      Provider<TestNotification> testNotificationProvider,
      Provider<VectorFeatures> vectorFeaturesProvider) {
    return new GoogleNotificationTroubleshootTestManagerFactory_Factory(unifiedPushHelperProvider, testSystemSettingsProvider, testAccountSettingsProvider, testDeviceSettingsProvider, testPushRulesSettingsProvider, testPlayServicesProvider, testFirebaseTokenProvider, testTokenRegistrationProvider, testCurrentUnifiedPushDistributorProvider, testUnifiedPushGatewayProvider, testUnifiedPushEndpointProvider, testAvailableUnifiedPushDistributorsProvider, testEndpointAsTokenRegistrationProvider, testPushFromPushGatewayProvider, testNotificationProvider, vectorFeaturesProvider);
  }

  public static GoogleNotificationTroubleshootTestManagerFactory newInstance(
      UnifiedPushHelper unifiedPushHelper, TestSystemSettings testSystemSettings,
      TestAccountSettings testAccountSettings, TestDeviceSettings testDeviceSettings,
      TestPushRulesSettings testPushRulesSettings, TestPlayServices testPlayServices,
      TestFirebaseToken testFirebaseToken, TestTokenRegistration testTokenRegistration,
      TestCurrentUnifiedPushDistributor testCurrentUnifiedPushDistributor,
      TestUnifiedPushGateway testUnifiedPushGateway,
      TestUnifiedPushEndpoint testUnifiedPushEndpoint,
      TestAvailableUnifiedPushDistributors testAvailableUnifiedPushDistributors,
      TestEndpointAsTokenRegistration testEndpointAsTokenRegistration,
      TestPushFromPushGateway testPushFromPushGateway, TestNotification testNotification,
      VectorFeatures vectorFeatures) {
    return new GoogleNotificationTroubleshootTestManagerFactory(unifiedPushHelper, testSystemSettings, testAccountSettings, testDeviceSettings, testPushRulesSettings, testPlayServices, testFirebaseToken, testTokenRegistration, testCurrentUnifiedPushDistributor, testUnifiedPushGateway, testUnifiedPushEndpoint, testAvailableUnifiedPushDistributors, testEndpointAsTokenRegistration, testPushFromPushGateway, testNotification, vectorFeatures);
  }
}
