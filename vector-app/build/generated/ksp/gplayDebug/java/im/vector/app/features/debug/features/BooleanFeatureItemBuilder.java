package im.vector.app.features.debug.features;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.epoxy.EpoxyBuildScope;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.OnModelBoundListener;
import com.airbnb.epoxy.OnModelUnboundListener;
import com.airbnb.epoxy.OnModelVisibilityChangedListener;
import com.airbnb.epoxy.OnModelVisibilityStateChangedListener;
import java.lang.CharSequence;
import java.lang.Number;

@EpoxyBuildScope
public interface BooleanFeatureItemBuilder {
  BooleanFeatureItemBuilder onBind(
      OnModelBoundListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener);

  BooleanFeatureItemBuilder onUnbind(
      OnModelUnboundListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener);

  BooleanFeatureItemBuilder onVisibilityStateChanged(
      OnModelVisibilityStateChangedListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener);

  BooleanFeatureItemBuilder onVisibilityChanged(
      OnModelVisibilityChangedListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener);

  BooleanFeatureItemBuilder feature(@NonNull Feature.BooleanFeature feature);

  BooleanFeatureItemBuilder listener(@Nullable BooleanFeatureItem.Listener listener);

  BooleanFeatureItemBuilder id(long p0);

  BooleanFeatureItemBuilder id(@Nullable Number... p0);

  BooleanFeatureItemBuilder id(long p0, long p1);

  BooleanFeatureItemBuilder id(@Nullable CharSequence p0);

  BooleanFeatureItemBuilder id(@Nullable CharSequence p0, @Nullable CharSequence... p1);

  BooleanFeatureItemBuilder id(@Nullable CharSequence p0, long p1);

  BooleanFeatureItemBuilder layout(@LayoutRes int p0);

  BooleanFeatureItemBuilder spanSizeOverride(@Nullable EpoxyModel.SpanSizeOverrideCallback p0);
}
