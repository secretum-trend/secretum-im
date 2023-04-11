package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import kotlinx.coroutines.CoroutineScope;

@ScopeMetadata
@QualifierMetadata("im.vector.app.core.di.NamedGlobalScope")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class VectorStaticModule_ProvidesGlobalScopeFactory implements Factory<CoroutineScope> {
  @Override
  public CoroutineScope get() {
    return providesGlobalScope();
  }

  public static VectorStaticModule_ProvidesGlobalScopeFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineScope providesGlobalScope() {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesGlobalScope());
  }

  private static final class InstanceHolder {
    private static final VectorStaticModule_ProvidesGlobalScopeFactory INSTANCE = new VectorStaticModule_ProvidesGlobalScopeFactory();
  }
}
