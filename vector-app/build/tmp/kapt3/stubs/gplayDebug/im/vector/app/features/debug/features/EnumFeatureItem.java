package im.vector.app.features.debug.features;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import im.vector.app.core.epoxy.VectorEpoxyHolder;
import im.vector.app.core.epoxy.VectorEpoxyModel;
import im.vector.application.R;

@com.airbnb.epoxy.EpoxyModelClass()
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\b\'\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0002\u0018\u0019B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0016J*\u0010\u0013\u001a\u00020\u0011\"\u000e\b\u0000\u0010\u0014*\b\u0012\u0004\u0012\u0002H\u00140\u0015*\b\u0012\u0004\u0012\u0002H\u00140\u00052\u0006\u0010\u0016\u001a\u00020\u0017H\u0002R\"\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR \u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001a"}, d2 = {"Lim/vector/app/features/debug/features/EnumFeatureItem;", "Lim/vector/app/core/epoxy/VectorEpoxyModel;", "Lim/vector/app/features/debug/features/EnumFeatureItem$Holder;", "()V", "feature", "Lim/vector/app/features/debug/features/Feature$EnumFeature;", "getFeature", "()Lim/vector/app/features/debug/features/Feature$EnumFeature;", "setFeature", "(Lim/vector/app/features/debug/features/Feature$EnumFeature;)V", "listener", "Lim/vector/app/features/debug/features/EnumFeatureItem$Listener;", "getListener", "()Lim/vector/app/features/debug/features/EnumFeatureItem$Listener;", "setListener", "(Lim/vector/app/features/debug/features/EnumFeatureItem$Listener;)V", "bind", "", "holder", "onOptionSelected", "T", "", "selection", "", "Holder", "Listener", "Secretum-v1.0.0(1)_gplayDebug"})
public abstract class EnumFeatureItem extends im.vector.app.core.epoxy.VectorEpoxyModel<im.vector.app.features.debug.features.EnumFeatureItem.Holder> {
    @com.airbnb.epoxy.EpoxyAttribute()
    public im.vector.app.features.debug.features.Feature.EnumFeature<?> feature;
    @org.jetbrains.annotations.Nullable()
    @com.airbnb.epoxy.EpoxyAttribute(value = {com.airbnb.epoxy.EpoxyAttribute.Option.DoNotHash})
    private im.vector.app.features.debug.features.EnumFeatureItem.Listener listener;
    
    public EnumFeatureItem() {
        super(0);
    }
    
    @org.jetbrains.annotations.NotNull()
    public final im.vector.app.features.debug.features.Feature.EnumFeature<?> getFeature() {
        return null;
    }
    
    public final void setFeature(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.features.Feature.EnumFeature<?> p0) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final im.vector.app.features.debug.features.EnumFeatureItem.Listener getListener() {
        return null;
    }
    
    public final void setListener(@org.jetbrains.annotations.Nullable()
    im.vector.app.features.debug.features.EnumFeatureItem.Listener p0) {
    }
    
    @java.lang.Override()
    public void bind(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.features.EnumFeatureItem.Holder holder) {
    }
    
    private final <T extends java.lang.Enum<T>>void onOptionSelected(im.vector.app.features.debug.features.Feature.EnumFeature<T> $this$onOptionSelected, int selection) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\n8FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\r\u0010\b\u001a\u0004\b\u000b\u0010\f\u00a8\u0006\u000e"}, d2 = {"Lim/vector/app/features/debug/features/EnumFeatureItem$Holder;", "Lim/vector/app/core/epoxy/VectorEpoxyHolder;", "()V", "label", "Landroid/widget/TextView;", "getLabel", "()Landroid/widget/TextView;", "label$delegate", "Lkotlin/properties/ReadOnlyProperty;", "optionsSpinner", "Landroid/widget/Spinner;", "getOptionsSpinner", "()Landroid/widget/Spinner;", "optionsSpinner$delegate", "Secretum-v1.0.0(1)_gplayDebug"})
    public static final class Holder extends im.vector.app.core.epoxy.VectorEpoxyHolder {
        @org.jetbrains.annotations.NotNull()
        private final kotlin.properties.ReadOnlyProperty label$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.properties.ReadOnlyProperty optionsSpinner$delegate = null;
        
        public Holder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getLabel() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.Spinner getOptionsSpinner() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0010\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bf\u0018\u00002\u00020\u0001J5\u0010\u0002\u001a\u00020\u0003\"\u000e\b\u0000\u0010\u0004*\b\u0012\u0004\u0012\u0002H\u00040\u00052\b\u0010\u0006\u001a\u0004\u0018\u0001H\u00042\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u0002H\u00040\bH&\u00a2\u0006\u0002\u0010\t\u00a8\u0006\n"}, d2 = {"Lim/vector/app/features/debug/features/EnumFeatureItem$Listener;", "", "onEnumOptionSelected", "", "T", "", "option", "feature", "Lim/vector/app/features/debug/features/Feature$EnumFeature;", "(Ljava/lang/Enum;Lim/vector/app/features/debug/features/Feature$EnumFeature;)V", "Secretum-v1.0.0(1)_gplayDebug"})
    public static abstract interface Listener {
        
        public abstract <T extends java.lang.Enum<T>>void onEnumOptionSelected(@org.jetbrains.annotations.Nullable()
        T option, @org.jetbrains.annotations.NotNull()
        im.vector.app.features.debug.features.Feature.EnumFeature<T> feature);
    }
}