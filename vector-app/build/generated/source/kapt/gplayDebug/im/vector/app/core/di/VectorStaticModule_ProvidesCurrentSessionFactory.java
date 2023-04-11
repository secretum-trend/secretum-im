package im.vector.app.core.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import org.matrix.android.sdk.api.session.Session;

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
public final class VectorStaticModule_ProvidesCurrentSessionFactory implements Factory<Session> {
  private final Provider<ActiveSessionHolder> activeSessionHolderProvider;

  public VectorStaticModule_ProvidesCurrentSessionFactory(
      Provider<ActiveSessionHolder> activeSessionHolderProvider) {
    this.activeSessionHolderProvider = activeSessionHolderProvider;
  }

  @Override
  public Session get() {
    return providesCurrentSession(activeSessionHolderProvider.get());
  }

  public static VectorStaticModule_ProvidesCurrentSessionFactory create(
      Provider<ActiveSessionHolder> activeSessionHolderProvider) {
    return new VectorStaticModule_ProvidesCurrentSessionFactory(activeSessionHolderProvider);
  }

  public static Session providesCurrentSession(ActiveSessionHolder activeSessionHolder) {
    return Preconditions.checkNotNullFromProvides(VectorStaticModule.INSTANCE.providesCurrentSession(activeSessionHolder));
  }
}
