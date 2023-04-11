package im.vector.app.features.debug.sas;

import android.annotation.SuppressLint;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.airbnb.epoxy.EpoxyAttribute;
import com.airbnb.epoxy.EpoxyModelClass;
import im.vector.app.core.epoxy.VectorEpoxyHolder;
import im.vector.app.core.epoxy.VectorEpoxyModel;
import im.vector.application.R;
import org.matrix.android.sdk.api.session.crypto.verification.EmojiRepresentation;

@com.airbnb.epoxy.EpoxyModelClass()
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\'\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001:\u0001\u0013B\u0005\u00a2\u0006\u0002\u0010\u0003J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0002H\u0017R\u001e\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u0087.\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\u0006\u0010\u0007\"\u0004\b\b\u0010\tR\u001e\u0010\n\u001a\u00020\u000b8\u0006@\u0006X\u0087\u000e\u00a2\u0006\u000e\n\u0000\u001a\u0004\b\f\u0010\r\"\u0004\b\u000e\u0010\u000f\u00a8\u0006\u0014"}, d2 = {"Lim/vector/app/features/debug/sas/SasEmojiItem;", "Lim/vector/app/core/epoxy/VectorEpoxyModel;", "Lim/vector/app/features/debug/sas/SasEmojiItem$Holder;", "()V", "emojiRepresentation", "Lorg/matrix/android/sdk/api/session/crypto/verification/EmojiRepresentation;", "getEmojiRepresentation", "()Lorg/matrix/android/sdk/api/session/crypto/verification/EmojiRepresentation;", "setEmojiRepresentation", "(Lorg/matrix/android/sdk/api/session/crypto/verification/EmojiRepresentation;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "bind", "", "holder", "Holder", "vector-app_gplayDebug"})
public abstract class SasEmojiItem extends im.vector.app.core.epoxy.VectorEpoxyModel<im.vector.app.features.debug.sas.SasEmojiItem.Holder> {
    @com.airbnb.epoxy.EpoxyAttribute()
    private int index = 0;
    @com.airbnb.epoxy.EpoxyAttribute()
    public org.matrix.android.sdk.api.session.crypto.verification.EmojiRepresentation emojiRepresentation;
    
    public SasEmojiItem() {
        super(0);
    }
    
    public final int getIndex() {
        return 0;
    }
    
    public final void setIndex(int p0) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final org.matrix.android.sdk.api.session.crypto.verification.EmojiRepresentation getEmojiRepresentation() {
        return null;
    }
    
    public final void setEmojiRepresentation(@org.jetbrains.annotations.NotNull()
    org.matrix.android.sdk.api.session.crypto.verification.EmojiRepresentation p0) {
    }
    
    @android.annotation.SuppressLint(value = {"SetTextI18n"})
    @java.lang.Override()
    public void bind(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.debug.sas.SasEmojiItem.Holder holder) {
    }
    
    @kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u000e\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002R\u001b\u0010\u0003\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0007\u0010\b\u001a\u0004\b\u0005\u0010\u0006R\u001b\u0010\t\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000b\u0010\b\u001a\u0004\b\n\u0010\u0006R\u001b\u0010\f\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u000e\u0010\b\u001a\u0004\b\r\u0010\u0006R\u001b\u0010\u000f\u001a\u00020\u00048FX\u0086\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0011\u0010\b\u001a\u0004\b\u0010\u0010\u0006\u00a8\u0006\u0012"}, d2 = {"Lim/vector/app/features/debug/sas/SasEmojiItem$Holder;", "Lim/vector/app/core/epoxy/VectorEpoxyHolder;", "()V", "emojiView", "Landroid/widget/TextView;", "getEmojiView", "()Landroid/widget/TextView;", "emojiView$delegate", "Lkotlin/properties/ReadOnlyProperty;", "idView", "getIdView", "idView$delegate", "indexView", "getIndexView", "indexView$delegate", "textView", "getTextView", "textView$delegate", "vector-app_gplayDebug"})
    public static final class Holder extends im.vector.app.core.epoxy.VectorEpoxyHolder {
        @org.jetbrains.annotations.NotNull()
        private final kotlin.properties.ReadOnlyProperty indexView$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.properties.ReadOnlyProperty emojiView$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.properties.ReadOnlyProperty textView$delegate = null;
        @org.jetbrains.annotations.NotNull()
        private final kotlin.properties.ReadOnlyProperty idView$delegate = null;
        
        public Holder() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getIndexView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getEmojiView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getTextView() {
            return null;
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.widget.TextView getIdView() {
            return null;
        }
    }
}