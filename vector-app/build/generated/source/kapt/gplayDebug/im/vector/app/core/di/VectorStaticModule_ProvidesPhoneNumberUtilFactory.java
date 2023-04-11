package im.vector.app.core.di;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class VectorStaticModule_ProvidesPhoneNumberUtilFactory implements Factory<PhoneNumberUtil> {
  @Override
  public PhoneNumberUtil get() {
    return providesPhoneNumberUtil();
  }

  public static VectorStaticModule_ProvidesPhoneNumberUtilFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static PhoneNumberUtil providesPhoneNumberUtil() {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesPhoneNumberUtil());
  }

  private static final class InstanceHolder {
    private static final VectorStaticModule_ProvidesPhoneNumberUtilFactory INSTANCE = new VectorStaticModule_ProvidesPhoneNumberUtilFactory();
  }
}
