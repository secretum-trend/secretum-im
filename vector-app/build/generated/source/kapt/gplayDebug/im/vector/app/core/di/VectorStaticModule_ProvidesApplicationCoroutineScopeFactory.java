package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;

@ScopeMetadata("javax.inject.Singleton")
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
public final class VectorStaticModule_ProvidesApplicationCoroutineScopeFactory implements Factory<CoroutineScope> {
  @Override
  public CoroutineScope get() {
    return providesApplicationCoroutineScope();
  }

  public static VectorStaticModule_ProvidesApplicationCoroutineScopeFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineScope providesApplicationCoroutineScope() {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesApplicationCoroutineScope());
  }

  private static final class InstanceHolder {
    private static final VectorStaticModule_ProvidesApplicationCoroutineScopeFactory INSTANCE = new VectorStaticModule_ProvidesApplicationCoroutineScopeFactory();
  }
}
