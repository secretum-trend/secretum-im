package im.vector.app.features.debug.leak;

import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.codegen.OriginatingElement;
import dagger.hilt.internal.GeneratedEntryPoint;
import javax.annotation.processing.Generated;

@OriginatingElement(
    topLevelClass = DebugMemoryLeaksActivity.class
)
@GeneratedEntryPoint
@InstallIn(ActivityComponent.class)
@Generated("dagger.hilt.android.processor.internal.androidentrypoint.InjectorEntryPointGenerator")
public interface DebugMemoryLeaksActivity_GeneratedInjector {
  void injectDebugMemoryLeaksActivity(DebugMemoryLeaksActivity debugMemoryLeaksActivity);
}
