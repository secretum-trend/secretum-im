package im.vector.app.features.debug.leak;

import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class DebugMemoryLeaksViewModel_Factory_Impl implements DebugMemoryLeaksViewModel.Factory {
  private final DebugMemoryLeaksViewModel_Factory delegateFactory;

  DebugMemoryLeaksViewModel_Factory_Impl(DebugMemoryLeaksViewModel_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public DebugMemoryLeaksViewModel create(DebugMemoryLeaksViewState initialState) {
    return delegateFactory.get(initialState);
  }

  public static Provider<DebugMemoryLeaksViewModel.Factory> create(
      DebugMemoryLeaksViewModel_Factory delegateFactory) {
    return InstanceFactory.create(new DebugMemoryLeaksViewModel_Factory_Impl(delegateFactory));
  }
}
