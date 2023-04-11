package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.dispatchers.CoroutineDispatchers;
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
public final class VectorStaticModule_ProvidesCoroutineDispatchersFactory implements Factory<CoroutineDispatchers> {
  @Override
  public CoroutineDispatchers get() {
    return providesCoroutineDispatchers();
  }

  public static VectorStaticModule_ProvidesCoroutineDispatchersFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineDispatchers providesCoroutineDispatchers() {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesCoroutineDispatchers());
  }

  private static final class InstanceHolder {
    private static final VectorStaticModule_ProvidesCoroutineDispatchersFactory INSTANCE = new VectorStaticModule_ProvidesCoroutineDispatchersFactory();
  }
}
