package im.vector.app.features.debug.features;

import androidx.datastore.preferences.core.Preferences;
import com.airbnb.epoxy.TypedEpoxyController;
import javax.inject.Inject;
import kotlin.reflect.KClass;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\rB\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0003J\u0012\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0002H\u0014R\u001c\u0010\u0004\u001a\u0004\u0018\u00010\u0005X\u0086\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\t\u00a8\u0006\u000e"}, d2 = {"Lim/vector/app/features/debug/features/FeaturesController;", "Lcom/airbnb/epoxy/TypedEpoxyController;", "Lim/vector/app/features/debug/features/FeaturesState;", "()V", "listener", "Lim/vector/app/features/debug/features/FeaturesController$Listener;", "getListener", "()Lim/vector/app/features/debug/features/FeaturesController$Listener;", "setListener", "(Lim/vector/app/features/debug/features/FeaturesController$Listener;)V", "buildModels", "", "data", "Listener", "vector-app_gplayDebug"})
public final class FeaturesController extends com.airbnb.epoxy.TypedEpoxyController<im.vector.app.features.debug.features.FeaturesState> {
    @org.jetbrains.annotations.Nullable()
    private im.vector.app.features.debug.features.FeaturesController.Listener listener;
    
    @javax.inject.Inject()
    public FeaturesController() {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final im.vector.app.features.debug.features.FeaturesController.Listener getListener() {
        return null;
    }
    
    public final void setListener(@org.jetbrains.annotations.Nullable()
    im.vector.app.features.debug.features.FeaturesController.Listener p0) {
    }
    
    @java.lang.Override()
    protected void buildModels(@org.jetbrains.annotations.Nullable()
    im.vector.app.features.debug.features.FeaturesState data) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u00012\u00020\u0002\u00a8\u0006\u0003"}, d2 = {"Lim/vector/app/features/debug/features/FeaturesController$Listener;", "Lim/vector/app/features/debug/features/EnumFeatureItem$Listener;", "Lim/vector/app/features/debug/features/BooleanFeatureItem$Listener;", "vector-app_gplayDebug"})
    public static abstract interface Listener extends im.vector.app.features.debug.features.EnumFeatureItem.Listener, im.vector.app.features.debug.features.BooleanFeatureItem.Listener {
    }
}