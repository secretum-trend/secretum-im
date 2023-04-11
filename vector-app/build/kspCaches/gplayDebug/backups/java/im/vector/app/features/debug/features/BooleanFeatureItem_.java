package im.vector.app.features.debug.features;

import android.view.ViewParent;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.airbnb.epoxy.EpoxyController;
import com.airbnb.epoxy.EpoxyModel;
import com.airbnb.epoxy.EpoxyViewHolder;
import com.airbnb.epoxy.GeneratedModel;
import com.airbnb.epoxy.OnModelBoundListener;
import com.airbnb.epoxy.OnModelUnboundListener;
import com.airbnb.epoxy.OnModelVisibilityChangedListener;
import com.airbnb.epoxy.OnModelVisibilityStateChangedListener;
import java.lang.CharSequence;
import java.lang.Number;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;

/**
 * Generated file. Do not modify!
 */
public class BooleanFeatureItem_ extends BooleanFeatureItem implements GeneratedModel<BooleanFeatureItem.Holder>, BooleanFeatureItemBuilder {
  private OnModelBoundListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> onModelBoundListener_epoxyGeneratedModel;

  private OnModelUnboundListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> onModelUnboundListener_epoxyGeneratedModel;

  private OnModelVisibilityStateChangedListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> onModelVisibilityStateChangedListener_epoxyGeneratedModel;

  private OnModelVisibilityChangedListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> onModelVisibilityChangedListener_epoxyGeneratedModel;

  public BooleanFeatureItem_() {
    super();
  }

  @Override
  public void addTo(EpoxyController controller) {
    super.addTo(controller);
    addWithDebugValidation(controller);
  }

  @Override
  public void handlePreBind(final EpoxyViewHolder holder, final BooleanFeatureItem.Holder object,
      final int position) {
    validateStateHasNotChangedSinceAdded("The model was changed between being added to the controller and being bound.", position);
  }

  @Override
  public void handlePostBind(final BooleanFeatureItem.Holder object, int position) {
    if (onModelBoundListener_epoxyGeneratedModel != null) {
      onModelBoundListener_epoxyGeneratedModel.onModelBound(this, object, position);
    }
    validateStateHasNotChangedSinceAdded("The model was changed during the bind call.", position);
  }

  /**
   * Register a listener that will be called when this model is bound to a view.
   * <p>
   * The listener will contribute to this model's hashCode state per the {@link
   * com.airbnb.epoxy.EpoxyAttribute.Option#DoNotHash} rules.
   * <p>
   * You may clear the listener by setting a null value, or by calling {@link #reset()}
   */
  public BooleanFeatureItem_ onBind(
      OnModelBoundListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener) {
    onMutation();
    this.onModelBoundListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void unbind(BooleanFeatureItem.Holder object) {
    super.unbind(object);
    if (onModelUnboundListener_epoxyGeneratedModel != null) {
      onModelUnboundListener_epoxyGeneratedModel.onModelUnbound(this, object);
    }
  }

  /**
   * Register a listener that will be called when this model is unbound from a view.
   * <p>
   * The listener will contribute to this model's hashCode state per the {@link
   * com.airbnb.epoxy.EpoxyAttribute.Option#DoNotHash} rules.
   * <p>
   * You may clear the listener by setting a null value, or by calling {@link #reset()}
   */
  public BooleanFeatureItem_ onUnbind(
      OnModelUnboundListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener) {
    onMutation();
    this.onModelUnboundListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void onVisibilityStateChanged(int visibilityState,
      final BooleanFeatureItem.Holder object) {
    if (onModelVisibilityStateChangedListener_epoxyGeneratedModel != null) {
      onModelVisibilityStateChangedListener_epoxyGeneratedModel.onVisibilityStateChanged(this, object, visibilityState);
    }
    super.onVisibilityStateChanged(visibilityState, object);
  }

  /**
   * Register a listener that will be called when this model visibility state has changed.
   * <p>
   * The listener will contribute to this model's hashCode state per the {@link
   * com.airbnb.epoxy.EpoxyAttribute.Option#DoNotHash} rules.
   */
  public BooleanFeatureItem_ onVisibilityStateChanged(
      OnModelVisibilityStateChangedListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener) {
    onMutation();
    this.onModelVisibilityStateChangedListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void onVisibilityChanged(float percentVisibleHeight, float percentVisibleWidth,
      int visibleHeight, int visibleWidth, final BooleanFeatureItem.Holder object) {
    if (onModelVisibilityChangedListener_epoxyGeneratedModel != null) {
      onModelVisibilityChangedListener_epoxyGeneratedModel.onVisibilityChanged(this, object, percentVisibleHeight, percentVisibleWidth, visibleHeight, visibleWidth);
    }
    super.onVisibilityChanged(percentVisibleHeight, percentVisibleWidth, visibleHeight, visibleWidth, object);
  }

  /**
   * Register a listener that will be called when this model visibility has changed.
   * <p>
   * The listener will contribute to this model's hashCode state per the {@link
   * com.airbnb.epoxy.EpoxyAttribute.Option#DoNotHash} rules.
   */
  public BooleanFeatureItem_ onVisibilityChanged(
      OnModelVisibilityChangedListener<BooleanFeatureItem_, BooleanFeatureItem.Holder> listener) {
    onMutation();
    this.onModelVisibilityChangedListener_epoxyGeneratedModel = listener;
    return this;
  }

  public BooleanFeatureItem_ feature(@NonNull Feature.BooleanFeature feature) {
    onMutation();
    super.setFeature(feature);
    return this;
  }

  @NonNull
  public Feature.BooleanFeature feature() {
    return super.getFeature();
  }

  public BooleanFeatureItem_ listener(@Nullable BooleanFeatureItem.Listener listener) {
    onMutation();
    super.setListener(listener);
    return this;
  }

  @Nullable
  public BooleanFeatureItem.Listener listener() {
    return super.getListener();
  }

  @Override
  public BooleanFeatureItem_ id(long p0) {
    super.id(p0);
    return this;
  }

  @Override
  public BooleanFeatureItem_ id(@Nullable Number... p0) {
    super.id(p0);
    return this;
  }

  @Override
  public BooleanFeatureItem_ id(long p0, long p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public BooleanFeatureItem_ id(@Nullable CharSequence p0) {
    super.id(p0);
    return this;
  }

  @Override
  public BooleanFeatureItem_ id(@Nullable CharSequence p0, @Nullable CharSequence... p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public BooleanFeatureItem_ id(@Nullable CharSequence p0, long p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public BooleanFeatureItem_ layout(@LayoutRes int p0) {
    super.layout(p0);
    return this;
  }

  @Override
  public BooleanFeatureItem_ spanSizeOverride(@Nullable EpoxyModel.SpanSizeOverrideCallback p0) {
    super.spanSizeOverride(p0);
    return this;
  }

  @Override
  public BooleanFeatureItem_ show() {
    super.show();
    return this;
  }

  @Override
  public BooleanFeatureItem_ show(boolean p0) {
    super.show(p0);
    return this;
  }

  @Override
  public BooleanFeatureItem_ hide() {
    super.hide();
    return this;
  }

  @Override
  protected BooleanFeatureItem.Holder createNewHolder(ViewParent parent) {
    return new BooleanFeatureItem.Holder();
  }

  @Override
  public BooleanFeatureItem_ reset() {
    onModelBoundListener_epoxyGeneratedModel = null;
    onModelUnboundListener_epoxyGeneratedModel = null;
    onModelVisibilityStateChangedListener_epoxyGeneratedModel = null;
    onModelVisibilityChangedListener_epoxyGeneratedModel = null;
    super.setFeature(null);
    super.setListener(null);
    super.reset();
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof BooleanFeatureItem_)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BooleanFeatureItem_ that = (BooleanFeatureItem_) o;
    if (((onModelBoundListener_epoxyGeneratedModel == null) != (that.onModelBoundListener_epoxyGeneratedModel == null))) {
      return false;
    }
    if (((onModelUnboundListener_epoxyGeneratedModel == null) != (that.onModelUnboundListener_epoxyGeneratedModel == null))) {
      return false;
    }
    if (((onModelVisibilityStateChangedListener_epoxyGeneratedModel == null) != (that.onModelVisibilityStateChangedListener_epoxyGeneratedModel == null))) {
      return false;
    }
    if (((onModelVisibilityChangedListener_epoxyGeneratedModel == null) != (that.onModelVisibilityChangedListener_epoxyGeneratedModel == null))) {
      return false;
    }
    if ((getFeature() != null ? !getFeature().equals(that.getFeature()) : that.getFeature() != null)) {
      return false;
    }
    if (((getListener() == null) != (that.getListener() == null))) {
      return false;
    }
    return true;
  }

  @Override
  public int hashCode() {
    int _result = super.hashCode();
    _result = 31 * _result + (onModelBoundListener_epoxyGeneratedModel != null ? 1 : 0);
    _result = 31 * _result + (onModelUnboundListener_epoxyGeneratedModel != null ? 1 : 0);
    _result = 31 * _result + (onModelVisibilityStateChangedListener_epoxyGeneratedModel != null ? 1 : 0);
    _result = 31 * _result + (onModelVisibilityChangedListener_epoxyGeneratedModel != null ? 1 : 0);
    _result = 31 * _result + (getFeature() != null ? getFeature().hashCode() : 0);
    _result = 31 * _result + (getListener() != null ? 1 : 0);
    return _result;
  }

  @Override
  public String toString() {
    return "BooleanFeatureItem_{" +
        "feature=" + getFeature() +
        ", listener=" + getListener() +
        "}" + super.toString();
  }
}
