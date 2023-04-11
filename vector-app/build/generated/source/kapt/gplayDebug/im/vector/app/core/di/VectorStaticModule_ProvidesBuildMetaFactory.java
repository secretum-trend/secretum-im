package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import im.vector.app.core.resources.BuildMeta;
import javax.annotation.processing.Generated;

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
public final class VectorStaticModule_ProvidesBuildMetaFactory implements Factory<BuildMeta> {
  @Override
  public BuildMeta get() {
    return providesBuildMeta();
  }

  public static VectorStaticModule_ProvidesBuildMetaFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static BuildMeta providesBuildMeta() {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesBuildMeta());
  }

  private static final class InstanceHolder {
    private static final VectorStaticModule_ProvidesBuildMetaFactory INSTANCE = new VectorStaticModule_ProvidesBuildMetaFactory();
  }
}
