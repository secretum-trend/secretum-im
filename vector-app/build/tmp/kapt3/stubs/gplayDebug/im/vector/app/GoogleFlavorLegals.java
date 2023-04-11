package im.vector.app;

import android.content.Context;
import android.content.Intent;
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;
import im.vector.app.features.settings.legals.FlavorLegals;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0016J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\t"}, d2 = {"Lim/vector/app/GoogleFlavorLegals;", "Lim/vector/app/features/settings/legals/FlavorLegals;", "()V", "hasThirdPartyNotices", "", "navigateToThirdPartyNotices", "", "context", "Landroid/content/Context;", "vector-app_gplayDebug"})
public final class GoogleFlavorLegals implements im.vector.app.features.settings.legals.FlavorLegals {
    
    @javax.inject.Inject()
    public GoogleFlavorLegals() {
        super();
    }
    
    @java.lang.Override()
    public boolean hasThirdPartyNotices() {
        return false;
    }
    
    @java.lang.Override()
    public void navigateToThirdPartyNotices(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}