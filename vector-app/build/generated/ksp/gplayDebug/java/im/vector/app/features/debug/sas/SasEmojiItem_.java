package im.vector.app.features.debug.sas;

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
import org.matrix.android.sdk.api.session.crypto.verification.EmojiRepresentation;

/**
 * Generated file. Do not modify!
 */
public class SasEmojiItem_ extends SasEmojiItem implements GeneratedModel<SasEmojiItem.Holder>, SasEmojiItemBuilder {
  private OnModelBoundListener<SasEmojiItem_, SasEmojiItem.Holder> onModelBoundListener_epoxyGeneratedModel;

  private OnModelUnboundListener<SasEmojiItem_, SasEmojiItem.Holder> onModelUnboundListener_epoxyGeneratedModel;

  private OnModelVisibilityStateChangedListener<SasEmojiItem_, SasEmojiItem.Holder> onModelVisibilityStateChangedListener_epoxyGeneratedModel;

  private OnModelVisibilityChangedListener<SasEmojiItem_, SasEmojiItem.Holder> onModelVisibilityChangedListener_epoxyGeneratedModel;

  public SasEmojiItem_() {
    super();
  }

  @Override
  public void addTo(EpoxyController controller) {
    super.addTo(controller);
    addWithDebugValidation(controller);
  }

  @Override
  public void handlePreBind(final EpoxyViewHolder holder, final SasEmojiItem.Holder object,
      final int position) {
    validateStateHasNotChangedSinceAdded("The model was changed between being added to the controller and being bound.", position);
  }

  @Override
  public void handlePostBind(final SasEmojiItem.Holder object, int position) {
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
  public SasEmojiItem_ onBind(OnModelBoundListener<SasEmojiItem_, SasEmojiItem.Holder> listener) {
    onMutation();
    this.onModelBoundListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void unbind(SasEmojiItem.Holder object) {
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
  public SasEmojiItem_ onUnbind(
      OnModelUnboundListener<SasEmojiItem_, SasEmojiItem.Holder> listener) {
    onMutation();
    this.onModelUnboundListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void onVisibilityStateChanged(int visibilityState, final SasEmojiItem.Holder object) {
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
  public SasEmojiItem_ onVisibilityStateChanged(
      OnModelVisibilityStateChangedListener<SasEmojiItem_, SasEmojiItem.Holder> listener) {
    onMutation();
    this.onModelVisibilityStateChangedListener_epoxyGeneratedModel = listener;
    return this;
  }

  @Override
  public void onVisibilityChanged(float percentVisibleHeight, float percentVisibleWidth,
      int visibleHeight, int visibleWidth, final SasEmojiItem.Holder object) {
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
  public SasEmojiItem_ onVisibilityChanged(
      OnModelVisibilityChangedListener<SasEmojiItem_, SasEmojiItem.Holder> listener) {
    onMutation();
    this.onModelVisibilityChangedListener_epoxyGeneratedModel = listener;
    return this;
  }

  public SasEmojiItem_ index(int index) {
    onMutation();
    super.setIndex(index);
    return this;
  }

  public int index() {
    return super.getIndex();
  }

  public SasEmojiItem_ emojiRepresentation(@NonNull EmojiRepresentation emojiRepresentation) {
    onMutation();
    super.setEmojiRepresentation(emojiRepresentation);
    return this;
  }

  @NonNull
  public EmojiRepresentation emojiRepresentation() {
    return super.getEmojiRepresentation();
  }

  @Override
  public SasEmojiItem_ id(long p0) {
    super.id(p0);
    return this;
  }

  @Override
  public SasEmojiItem_ id(@Nullable Number... p0) {
    super.id(p0);
    return this;
  }

  @Override
  public SasEmojiItem_ id(long p0, long p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public SasEmojiItem_ id(@Nullable CharSequence p0) {
    super.id(p0);
    return this;
  }

  @Override
  public SasEmojiItem_ id(@Nullable CharSequence p0, @Nullable CharSequence... p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public SasEmojiItem_ id(@Nullable CharSequence p0, long p1) {
    super.id(p0, p1);
    return this;
  }

  @Override
  public SasEmojiItem_ layout(@LayoutRes int p0) {
    super.layout(p0);
    return this;
  }

  @Override
  public SasEmojiItem_ spanSizeOverride(@Nullable EpoxyModel.SpanSizeOverrideCallback p0) {
    super.spanSizeOverride(p0);
    return this;
  }

  @Override
  public SasEmojiItem_ show() {
    super.show();
    return this;
  }

  @Override
  public SasEmojiItem_ show(boolean p0) {
    super.show(p0);
    return this;
  }

  @Override
  public SasEmojiItem_ hide() {
    super.hide();
    return this;
  }

  @Override
  protected SasEmojiItem.Holder createNewHolder(ViewParent parent) {
    return new SasEmojiItem.Holder();
  }

  @Override
  public SasEmojiItem_ reset() {
    onModelBoundListener_epoxyGeneratedModel = null;
    onModelUnboundListener_epoxyGeneratedModel = null;
    onModelVisibilityStateChangedListener_epoxyGeneratedModel = null;
    onModelVisibilityChangedListener_epoxyGeneratedModel = null;
    super.setIndex(0);
    super.setEmojiRepresentation(null);
    super.reset();
    return this;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof SasEmojiItem_)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    SasEmojiItem_ that = (SasEmojiItem_) o;
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
    if ((getIndex() != that.getIndex())) {
      return false;
    }
    if ((getEmojiRepresentation() != null ? !getEmojiRepresentation().equals(that.getEmojiRepresentation()) : that.getEmojiRepresentation() != null)) {
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
    _result = 31 * _result + getIndex();
    _result = 31 * _result + (getEmojiRepresentation() != null ? getEmojiRepresentation().hashCode() : 0);
    return _result;
  }

  @Override
  public String toString() {
    return "SasEmojiItem_{" +
        "index=" + getIndex() +
        ", emojiRepresentation=" + getEmojiRepresentation() +
        "}" + super.toString();
  }
}
