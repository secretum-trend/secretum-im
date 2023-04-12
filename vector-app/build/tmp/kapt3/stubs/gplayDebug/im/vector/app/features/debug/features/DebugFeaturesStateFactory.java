package im.vector.app.features.debug.features;

import androidx.datastore.preferences.core.Preferences;
import im.vector.app.features.DefaultVectorFeatures;
import im.vector.app.features.VectorFeatures;
import javax.inject.Inject;
import kotlin.reflect.KFunction1;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0017\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u0007\u001a\u00020\bJ2\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\r0\u0011H\u0002J8\u0010\u0013\u001a\u00020\n\"\u0010\b\u0000\u0010\u0014\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00140\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0016\u001a\u0002H\u00142\u0006\u0010\u0017\u001a\u0002H\u0014H\u0082\b\u00a2\u0006\u0002\u0010\u0018R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lim/vector/app/features/debug/features/DebugFeaturesStateFactory;", "", "debugFeatures", "Lim/vector/app/features/debug/features/DebugVectorFeatures;", "defaultFeatures", "Lim/vector/app/features/DefaultVectorFeatures;", "(Lim/vector/app/features/debug/features/DebugVectorFeatures;Lim/vector/app/features/DefaultVectorFeatures;)V", "create", "Lim/vector/app/features/debug/features/FeaturesState;", "createBooleanFeature", "Lim/vector/app/features/debug/features/Feature;", "key", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "label", "", "factory", "Lkotlin/reflect/KFunction1;", "Lim/vector/app/features/VectorFeatures;", "createEnumFeature", "T", "", "featureOverride", "featureDefault", "(Ljava/lang/String;Ljava/lang/Enum;Ljava/lang/Enum;)Lim/vector/app/features/debug/features/Feature;", "Secretum-v1.0.0(1)_gplayDebug"})
public final class DebugFeaturesStateFactory {
    private final im.vector.app.features.debug.features.DebugVectorFeatures debugFeatures = null;
    private final im.vector.app.features.DefaultVectorFeatures defaultFeatures = null;
    
    @javax.inject.Inject()
    public DebugFeaturesStateFactory(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.features.DebugVectorFeatures debugFeatures, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.DefaultVectorFeatures defaultFeatures) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.debug.features.FeaturesState create() {
        return null;
    }
    
    private final im.vector.app.features.debug.features.Feature createBooleanFeature(androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> key, java.lang.String label, kotlin.reflect.KFunction<java.lang.Boolean> factory) {
        return null;
    }
}