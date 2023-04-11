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
public class EnumFeatureItem_ extends EnumFeatureItem implements GeneratedModel<EnumFeatureItem.Holder>, EnumFeatureItemBuilder {
  private OnModelBoundListener<EnumFeatureItem_, EnumFeatureItem.Holder> onModelBoundListener_epoxyGeneratedModel;

  private OnModelUnboundListener<EnumFeatureItem_, EnumFeatureItem.Holder> onModelUnboundListener_epoxyGeneratedModel;

  private OnModelVisibilityStateChangedListener<EnumFeatureItem_, EnumFeatureItem.Holder> onModelVisibilityStateChangedListener_epoxyGeneratedModel;

  private OnModelVisibilityChangedListener<EnumFeatureItem_, EnumFeatureItem.Holder> onModelVisibilityChangedListener_epoxyGeneratedModel;

  public EnumFeatureItem_() {
    super();
  }

  @Override
  public void addTo(EpoxyController controller) {
    super.addTo(controller);
    addWithDebugValidation(controller);
  }

  @Override
  public void handlePreBind(final EpoxyViewHolder holder, final EnumFeatureItem.Holder object,
      final int position) {
    validateStateHasNotChangedSinceAdded("The model was changed between being added to the controller and being bound.", position);
  }

  @Override
  public void handlePostBind(final EnumFeatureItem.Holder object, int position) {
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
  public EnumFeatureItem_ onBind(
      OnModelBoundListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener) {
    onMutation();
    this.onModelBoundListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void unbind(EnumFeatureItem.Holder object) {
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
  public EnumFeatureItem_ onUnbind(
      OnModelUnboundListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener) {
    onMutation();
    this.onModelUnboundListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void onVisibilityStateChanged(int visibilityState, final EnumFeatureItem.Holder object) {
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
  public EnumFeatureItem_ onVisibilityStateChanged(
      OnModelVisibilityStateChangedListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener) {
    onMutation();
    this.onModelVisibilityStateChangedListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void onVisibilityChanged(float percentVisibleHeight, float percentVisibleWidth,
      int visibleHeight, int visibleWidth, final EnumFeatureItem.Holder object) {
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
  public EnumFeatureItem_ onVisibilityChanged(
      OnModelVisibilityChangedListener<EnumFeatureItem_, EnumFeatureItem.Holder> listener) {
    onMutation();
    this.onModelVisibilityChangedListener_epoxyGeneratedModel = listener;
    return this;
  }

  public EnumFeatureItem_ feature(@NonNull Feature.EnumFeature<?> feature) {
    onMutation();
    super.setFeature(feature);
    return this;
  }

  @NonNull
  public Feature.EnumFeature<?> feature() {
    return super.getFeature();
  }

  public EnumFeatureItem_ listener(@Nullable EnumFeatureItem.Listener listener) {
    onMutation();
    super.setListener(listener);
    return this;
  }

  @Nullable
  public EnumFeatureItem.Listener listener() {
    return super.getListener();
  }

  @Override
  public EnumFeatureItem_ id(long p0) {
    super.id(p0);
    return this;
  }

  @Override
  public EnumFeatureItem_ id(@Nullable Number... p0) {
    super.id(p0);
    return this;
  }

  @Override
  public EnumFeatureItem_ id(long p0, long p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public EnumFeatureItem_ id(@Nullable CharSequence p0) {
    super.id(p0);
    return this;
  }

  @Override
  public EnumFeatureItem_ id(@Nullable CharSequence p0, @Nullable CharSequence... p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public EnumFeatureItem_ id(@Nullable CharSequence p0, long p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public EnumFeatureItem_ layout(@LayoutRes int p0) {
    super.layout(p0);
    return this;
  }

  @Override
  public EnumFeatureItem_ spanSizeOverride(@Nullable EpoxyModel.SpanSizeOverrideCallback p0) {
    super.spanSizeOverride(p0);
    return this;
  }

  @Override
  public EnumFeatureItem_ show() {
    super.show();
    return this;
  }

  @Override
  public EnumFeatureItem_ show(boolean p0) {
    super.show(p0);
    return this;
  }

  @Override
  public EnumFeatureItem_ hide() {
    super.hide();
    return this;
  }

  @Override
  protected EnumFeatureItem.Holder createNewHolder(ViewParent parent) {
    return new EnumFeatureItem.Holder();
  }

  @Override
  public EnumFeatureItem_ reset() {
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
    if (!(o instanceof EnumFeatureItem_)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    EnumFeatureItem_ that = (EnumFeatureItem_) o;
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
    return "EnumFeatureItem_{" +
        "feature=" + getFeature() +
        ", listener=" + getListener() +
        "}" + super.toString();
  }
}
