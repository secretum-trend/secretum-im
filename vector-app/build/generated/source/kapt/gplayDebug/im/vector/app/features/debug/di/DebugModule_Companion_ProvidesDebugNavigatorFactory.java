package im.vector.app.features.debug.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.debug.DebugNavigator;
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
public final class DebugModule_Companion_ProvidesDebugNavigatorFactory implements Factory<DebugNavigator> {
  @Override
  public DebugNavigator get() {
    return providesDebugNavigator();
  }

  public static DebugModule_Companion_ProvidesDebugNavigatorFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static DebugNavigator providesDebugNavigator() {
    return Preconditions.checkNotNullFromProvides(DebugModule.Companion.providesDebugNavigator());
  }

  private static final class InstanceHolder {
    private static final DebugModule_Companion_ProvidesDebugNavigatorFactory INSTANCE = new DebugModule_Companion_ProvidesDebugNavigatorFactory();
  }
}
