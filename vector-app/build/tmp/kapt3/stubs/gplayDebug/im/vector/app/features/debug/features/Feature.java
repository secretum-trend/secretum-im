package im.vector.app.features.debug.features;

import androidx.datastore.preferences.core.Preferences;
import com.airbnb.epoxy.TypedEpoxyController;
import javax.inject.Inject;
import kotlin.reflect.KClass;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0002\u0002\u0003\u0082\u0001\u0002\u0004\u0005\u00a8\u0006\u0006"}, d2 = {"Lim/vector/app/features/debug/features/Feature;", "", "BooleanFeature", "EnumFeature", "Lim/vector/app/features/debug/features/Feature$BooleanFeature;", "Lim/vector/app/features/debug/features/Feature$EnumFeature;", "Secretum-v1.0.0(1)_gplayDebug"})
public abstract interface Feature {
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0013\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00020\u0003B;\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000\u0012\u0006\u0010\u0007\u001a\u00028\u0000\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u0012\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\u00a2\u0006\u0002\u0010\fJ\t\u0010\u0017\u001a\u00020\u0005H\u00c6\u0003J\u0010\u0010\u0018\u001a\u0004\u0018\u00018\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u000e\u0010\u0019\u001a\u00028\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\u000f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00028\u00000\tH\u00c6\u0003J\u000f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u00c6\u0003JT\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00018\u00002\b\b\u0002\u0010\u0007\u001a\u00028\u00002\u000e\b\u0002\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t2\u000e\b\u0002\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u001dJ\u0013\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010!H\u00d6\u0003J\t\u0010\"\u001a\u00020#H\u00d6\u0001J\t\u0010$\u001a\u00020\u0005H\u00d6\u0001R\u0013\u0010\u0007\u001a\u00028\u0000\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0015\u0010\u0006\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\n\n\u0002\u0010\u000f\u001a\u0004\b\u0014\u0010\u000eR\u0017\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00000\u000b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016\u00a8\u0006%"}, d2 = {"Lim/vector/app/features/debug/features/Feature$EnumFeature;", "T", "", "Lim/vector/app/features/debug/features/Feature;", "label", "", "override", "default", "options", "", "type", "Lkotlin/reflect/KClass;", "(Ljava/lang/String;Ljava/lang/Enum;Ljava/lang/Enum;Ljava/util/List;Lkotlin/reflect/KClass;)V", "getDefault", "()Ljava/lang/Enum;", "Ljava/lang/Enum;", "getLabel", "()Ljava/lang/String;", "getOptions", "()Ljava/util/List;", "getOverride", "getType", "()Lkotlin/reflect/KClass;", "component1", "component2", "component3", "component4", "component5", "copy", "(Ljava/lang/String;Ljava/lang/Enum;Ljava/lang/Enum;Ljava/util/List;Lkotlin/reflect/KClass;)Lim/vector/app/features/debug/features/Feature$EnumFeature;", "equals", "", "other", "", "hashCode", "", "toString", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class EnumFeature<T extends java.lang.Enum<T>> implements im.vector.app.features.debug.features.Feature {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String label = null;
        @org.jetbrains.annotations.Nullable()
        private final T override = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<T> options = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.reflect.KClass<T> type = null;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.features.Feature.EnumFeature<T> copy(@org.jetbrains.annotations.NotNull()
        java.lang.String label, @org.jetbrains.annotations.Nullable()
        T override, @org.jetbrains.annotations.NotNull()
        T p2_772401952, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends T> options, @org.jetbrains.annotations.NotNull()
        kotlin.reflect.KClass<T> type) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public EnumFeature(@org.jetbrains.annotations.NotNull()
        java.lang.String label, @org.jetbrains.annotations.Nullable()
        T override, @org.jetbrains.annotations.NotNull()
        T p2_772401952, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends T> options, @org.jetbrains.annotations.NotNull()
        kotlin.reflect.KClass<T> type) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final T component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final T getOverride() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final T component3() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final T getDefault() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<T> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<T> getOptions() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlin.reflect.KClass<T> component5() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final kotlin.reflect.KClass<T> getType() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0012\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0013\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u0014\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\rJ\t\u0010\u0015\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00c6\u0003J>\u0010\u0017\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\bH\u00c6\u0001\u00a2\u0006\u0002\u0010\u0018J\u0013\u0010\u0019\u001a\u00020\u00052\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u00d6\u0003J\t\u0010\u001c\u001a\u00020\u001dH\u00d6\u0001J\t\u0010\u001e\u001a\u00020\u0003H\u00d6\u0001R\u0011\u0010\u0006\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0015\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\n\n\u0002\u0010\u000e\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00050\b\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012\u00a8\u0006\u001f"}, d2 = {"Lim/vector/app/features/debug/features/Feature$BooleanFeature;", "Lim/vector/app/features/debug/features/Feature;", "label", "", "featureOverride", "", "featureDefault", "key", "Landroidx/datastore/preferences/core/Preferences$Key;", "(Ljava/lang/String;Ljava/lang/Boolean;ZLandroidx/datastore/preferences/core/Preferences$Key;)V", "getFeatureDefault", "()Z", "getFeatureOverride", "()Ljava/lang/Boolean;", "Ljava/lang/Boolean;", "getKey", "()Landroidx/datastore/preferences/core/Preferences$Key;", "getLabel", "()Ljava/lang/String;", "component1", "component2", "component3", "component4", "copy", "(Ljava/lang/String;Ljava/lang/Boolean;ZLandroidx/datastore/preferences/core/Preferences$Key;)Lim/vector/app/features/debug/features/Feature$BooleanFeature;", "equals", "other", "", "hashCode", "", "toString", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class BooleanFeature implements im.vector.app.features.debug.features.Feature {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String label = null;
        @org.jetbrains.annotations.Nullable()
        private final java.lang.Boolean featureOverride = null;
        private final boolean featureDefault = false;
        @org.jetbrains.annotations.NotNull()
        private final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> key = null;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.features.Feature.BooleanFeature copy(@org.jetbrains.annotations.NotNull()
        java.lang.String label, @org.jetbrains.annotations.Nullable()
        java.lang.Boolean featureOverride, boolean featureDefault, @org.jetbrains.annotations.NotNull()
        androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> key) {
            return null;
        }
        
        @java.lang.Override()
        public boolean equals(@org.jetbrains.annotations.Nullable()
        java.lang.Object other) {
            return false;
        }
        
        @java.lang.Override()
        public int hashCode() {
            return 0;
        }
        
        @org.jetbrains.annotations.NotNull()
        @java.lang.Override()
        public java.lang.String toString() {
            return null;
        }
        
        public BooleanFeature(@org.jetbrains.annotations.NotNull()
        java.lang.String label, @org.jetbrains.annotations.Nullable()
        java.lang.Boolean featureOverride, boolean featureDefault, @org.jetbrains.annotations.NotNull()
        androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> key) {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String component1() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.lang.String getLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Boolean component2() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final java.lang.Boolean getFeatureOverride() {
            return null;
        }
        
        public final boolean component3() {
            return false;
        }
        
        public final boolean getFeatureDefault() {
            return false;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> component4() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final androidx.datastore.preferences.core.Preferences.Key<java.lang.Boolean> getKey() {
            return null;
        }
    }
}