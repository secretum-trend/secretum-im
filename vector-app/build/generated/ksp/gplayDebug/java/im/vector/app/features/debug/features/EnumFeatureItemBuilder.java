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
public interface EnumFeatureItemBuilder {
  EnumFeatureItemBuilder onBind(
      OnModelBoundListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener);

  EnumFeatureItemBuilder onUnbind(
      OnModelUnboundListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener);

  EnumFeatureItemBuilder onVisibilityStateChanged(
      OnModelVisibilityStateChangedListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener);

  EnumFeatureItemBuilder onVisibilityChanged(
      OnModelVisibilityChangedListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener);

  EnumFeatureItemBuilder feature(@NonNull Feature.EnumFeature<?> feature);

  EnumFeatureItemBuilder listener(@Nullable EnumFeatureItem.Listener listener);

  EnumFeatureItemBuilder id(long p0);

  EnumFeatureItemBuilder id(@Nullable Number... p0);

  EnumFeatureItemBuilder id(long p0, long p1);

  EnumFeatureItemBuilder id(@Nullable CharSequence p0);

  EnumFeatureItemBuilder id(@Nullable CharSequence p0, @Nullable CharSequence... p1);

  EnumFeatureItemBuilder id(@Nullable CharSequence p0, long p1);

  EnumFeatureItemBuilder layout(@LayoutRes int p0);

  EnumFeatureItemBuilder spanSizeOverride(@Nullable EpoxyModel.SpanSizeOverrideCallback p0);
}
