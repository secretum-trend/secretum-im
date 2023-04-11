package im.vector.app.features.debug.sas;

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
import org.matrix.android.sdk.api.session.crypto.verification.EmojiRepresentation;

@EpoxyBuildScope
public interface SasEmojiItemBuilder {
  SasEmojiItemBuilder onBind(OnModelBoundListener<SasEmojiItem_, SasEmojiItem.Holder> listener);

  SasEmojiItemBuilder onUnbind(OnModelUnboundListener<SasEmojiItem_, SasEmojiItem.Holder> listener);

  SasEmojiItemBuilder onVisibilityStateChanged(
      OnModelVisibilityStateChangedListener<SasEmojiItem_, SasEmojiItem.Holder> listener);

  SasEmojiItemBuilder onVisibilityChanged(
      OnModelVisibilityChangedListener<SasEmojiItem_, SasEmojiItem.Holder> listener);

  SasEmojiItemBuilder index(int index);

  SasEmojiItemBuilder emojiRepresentation(@NonNull EmojiRepresentation emojiRepresentation);

  SasEmojiItemBuilder id(long p0);

  SasEmojiItemBuilder id(@Nullable Number... p0);

  SasEmojiItemBuilder id(long p0, long p1);

  SasEmojiItemBuilder id(@Nullable CharSequence p0);

  SasEmojiItemBuilder id(@Nullable CharSequence p0, @Nullable CharSequence... p1);

  SasEmojiItemBuilder id(@Nullable CharSequence p0, long p1);

  SasEmojiItemBuilder layout(@LayoutRes int p0);

  SasEmojiItemBuilder spanSizeOverride(@Nullable EpoxyModel.SpanSizeOverrideCallback p0);
}
