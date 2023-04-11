package im.vector.app.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.services.GuardServiceStarter;
import javax.annotation.processing.Generated;

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
public final class FlavorModule_Companion_ProvideGuardServiceStarterFactory implements Factory<GuardServiceStarter> {
  @Override
  public GuardServiceStarter get() {
    return provideGuardServiceStarter();
  }

  public static FlavorModule_Companion_ProvideGuardServiceStarterFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static GuardServiceStarter provideGuardServiceStarter() {
    return Preconditions.checkNotNullFromProvides(FlavorModule.Companion.provideGuardServiceStarter());
  }

  private static final class InstanceHolder {
    private static final FlavorModule_Companion_ProvideGuardServiceStarterFactory INSTANCE = new FlavorModule_Companion_ProvideGuardServiceStarterFactory();
  }
}
