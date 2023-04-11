package im.vector.app.features.debug.leak;

import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.internal.GeneratedEntryPoint;
import javax.annotation.processing.Generated;

@OriginatingElement(
    topLevelClass = DebugMemoryLeaksFragment.class
)
@GeneratedEntryPoint
@InstallIn(FragmentComponent.class)
@Generated("dagger.hilt.android.processor.internal.androidentrypoint.InjectorEntryPointGenerator")
public interface DebugMemoryLeaksFragment_GeneratedInjector {
  void injectDebugMemoryLeaksFragment(DebugMemoryLeaksFragment debugMemoryLeaksFragment);
}
