package im.vector.app.leakcanary;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
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
public final class LeakCanaryLeakDetector_Factory implements Factory<LeakCanaryLeakDetector> {
  @Override
  public LeakCanaryLeakDetector get() {
    return newInstance();
  }

  public static LeakCanaryLeakDetector_Factory create() {
    return InstanceHolder.INSTANCE;
  }

  public static LeakCanaryLeakDetector newInstance() {
    return new LeakCanaryLeakDetector();
  }

  private static final class InstanceHolder {
    private static final LeakCanaryLeakDetector_Factory INSTANCE = new LeakCanaryLeakDetector_Factory();
  }
}
