package im.vector.app.features.debug.settings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import im.vector.application.databinding.ViewBooleanDropdownBinding;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002\u0011\u0012B\u001b\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u00a2\u0006\u0002\u0010\u0006J,\u0010\t\u001a\u00020\n\"\b\b\u0000\u0010\u000b*\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000e2\f\u0010\u000f\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0010R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0013"}, d2 = {"Lim/vector/app/features/debug/settings/OverrideDropdownView;", "Landroid/widget/LinearLayout;", "context", "Landroid/content/Context;", "attrs", "Landroid/util/AttributeSet;", "(Landroid/content/Context;Landroid/util/AttributeSet;)V", "binding", "Lim/vector/application/databinding/ViewBooleanDropdownBinding;", "bind", "", "T", "Lim/vector/app/features/debug/settings/OverrideOption;", "feature", "Lim/vector/app/features/debug/settings/OverrideDropdownView$OverrideDropdown;", "listener", "Lim/vector/app/features/debug/settings/OverrideDropdownView$Listener;", "Listener", "OverrideDropdown", "vector-app_gplayDebug"})
public final class OverrideDropdownView extends android.widget.LinearLayout {
    private final im.vector.application.databinding.ViewBooleanDropdownBinding binding = null;
    
    @kotlin.jvm.JvmOverloads()
    public OverrideDropdownView(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super(null);
    }
    
    @kotlin.jvm.JvmOverloads()
    public OverrideDropdownView(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.Nullable()
    android.util.AttributeSet attrs) {
        super(null);
    }
    
    public final <T extends im.vector.app.features.debug.settings.OverrideOption>void bind(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.settings.OverrideDropdownView.OverrideDropdown<T> feature, @org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.settings.OverrideDropdownView.Listener<T> listener) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u00e6\u0080\u0001\u0018\u0000*\u0004\b\u0000\u0010\u00012\u00020\u0002J\u0017\u0010\u0003\u001a\u00020\u00042\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000H&\u00a2\u0006\u0002\u0010\u0006\u00a8\u0006\u0007"}, d2 = {"Lim/vector/app/features/debug/settings/OverrideDropdownView$Listener;", "T", "", "onOverrideSelected", "", "option", "(Ljava/lang/Object;)V", "vector-app_gplayDebug"})
    public static abstract interface Listener<T extends java.lang.Object> {
        
        public abstract void onOverrideSelected(@org.jetbrains.annotations.Nullable()
        T option);
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B%\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u0012\b\u0010\b\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0005H\u00c6\u0003J\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007H\u00c6\u0003J\u0010\u0010\u0013\u001a\u0004\u0018\u00018\u0000H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000bJ:\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0004\u001a\u00020\u00052\u000e\b\u0002\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00018\u0000H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0015J\u0013\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0003H\u00d6\u0003J\t\u0010\u0019\u001a\u00020\u001aH\u00d6\u0001J\t\u0010\u001b\u001a\u00020\u0005H\u00d6\u0001R\u0015\u0010\b\u001a\u0004\u0018\u00018\u0000\u00a2\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00028\u00000\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u00a8\u0006\u001c"}, d2 = {"Lim/vector/app/features/debug/settings/OverrideDropdownView$OverrideDropdown;", "T", "Lim/vector/app/features/debug/settings/OverrideOption;", "", "label", "", "options", "", "activeOption", "(Ljava/lang/String;Ljava/util/List;Lim/vector/app/features/debug/settings/OverrideOption;)V", "getActiveOption", "()Lim/vector/app/features/debug/settings/OverrideOption;", "Lim/vector/app/features/debug/settings/OverrideOption;", "getLabel", "()Ljava/lang/String;", "getOptions", "()Ljava/util/List;", "component1", "component2", "component3", "copy", "(Ljava/lang/String;Ljava/util/List;Lim/vector/app/features/debug/settings/OverrideOption;)Lim/vector/app/features/debug/settings/OverrideDropdownView$OverrideDropdown;", "equals", "", "other", "hashCode", "", "toString", "vector-app_gplayDebug"})
    public static final class OverrideDropdown<T extends im.vector.app.features.debug.settings.OverrideOption> {
        @org.jetbrains.annotations.NotNull()
        private final java.lang.String label = null;
        @org.jetbrains.annotations.NotNull()
        private final java.util.List<T> options = null;
        @org.jetbrains.annotations.Nullable()
        private final T activeOption = null;
        
        @org.jetbrains.annotations.NotNull()
        public final im.vector.app.features.debug.settings.OverrideDropdownView.OverrideDropdown<T> copy(@org.jetbrains.annotations.NotNull()
        java.lang.String label, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends T> options, @org.jetbrains.annotations.Nullable()
        T activeOption) {
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
        
        public OverrideDropdown(@org.jetbrains.annotations.NotNull()
        java.lang.String label, @org.jetbrains.annotations.NotNull()
        java.util.List<? extends T> options, @org.jetbrains.annotations.Nullable()
        T activeOption) {
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
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<T> component2() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final java.util.List<T> getOptions() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final T component3() {
            return null;
        }
        
        @org.jetbrains.annotations.Nullable()
        public final T getActiveOption() {
            return null;
        }
    }
}