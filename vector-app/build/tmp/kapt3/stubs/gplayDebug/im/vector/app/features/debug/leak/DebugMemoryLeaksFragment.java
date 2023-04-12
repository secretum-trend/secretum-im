package im.vector.app.features.debug.leak;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import dagger.hilt.android.AndroidEntryPoint;
import im.vector.app.core.platform.VectorBaseFragment;
import im.vector.application.databinding.FragmentDebugMemoryLeaksBinding;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0003J\u001a\u0010\n\u001a\u00020\u00022\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u001a\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0015H\u0016J\b\u0010\u0016\u001a\u00020\u0010H\u0002R\u001b\u0010\u0004\u001a\u00020\u00058BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0017"}, d2 = {"Lim/vector/app/features/debug/leak/DebugMemoryLeaksFragment;", "Lim/vector/app/core/platform/VectorBaseFragment;", "Lim/vector/application/databinding/FragmentDebugMemoryLeaksBinding;", "()V", "viewModel", "Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel;", "getViewModel", "()Lim/vector/app/features/debug/leak/DebugMemoryLeaksViewModel;", "viewModel$delegate", "Lkotlin/Lazy;", "getBinding", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "invalidate", "", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "setViewListeners", "Secretum-v1.0.0(1)_gplayDebug"})
@dagger.hilt.android.AndroidEntryPoint()
public final class DebugMemoryLeaksFragment extends im.vector.app.core.platform.VectorBaseFragment<im.vector.application.databinding.FragmentDebugMemoryLeaksBinding> {
    private final kotlin.Lazy viewModel$delegate = null;
    
    public DebugMemoryLeaksFragment() {
        super();
    }
    
    private final im.vector.app.features.debug.leak.DebugMemoryLeaksViewModel getViewModel() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @java.lang.Override()
    public im.vector.application.databinding.FragmentDebugMemoryLeaksBinding getBinding(@org.jetbrains.annotations.NotNull()
    android.view.LayoutInflater inflater, @org.jetbrains.annotations.Nullable()
    android.view.ViewGroup container) {
        return null;
    }
    
    @java.lang.Override()
    public void onViewCreated(@org.jetbrains.annotations.NotNull()
    android.view.View view, @org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void setViewListeners() {
    }
    
    @java.lang.Override()
    public void invalidate() {
    }
}