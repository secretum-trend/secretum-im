package im.vector.app.gplay.features.settings.troubleshoot;

import androidx.fragment.app.FragmentActivity;
import com.google.firebase.messaging.FirebaseMessaging;
import im.vector.app.R;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.resources.StringProvider;
import im.vector.app.features.settings.troubleshoot.TroubleshootTest;
import timber.log.Timber;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\r"}, d2 = {"Lim/vector/app/gplay/features/settings/troubleshoot/TestFirebaseToken;", "Lim/vector/app/features/settings/troubleshoot/TroubleshootTest;", "context", "Landroidx/fragment/app/FragmentActivity;", "stringProvider", "Lim/vector/app/core/resources/StringProvider;", "fcmHelper", "Lim/vector/app/core/pushers/FcmHelper;", "(Landroidx/fragment/app/FragmentActivity;Lim/vector/app/core/resources/StringProvider;Lim/vector/app/core/pushers/FcmHelper;)V", "perform", "", "testParameters", "Lim/vector/app/features/settings/troubleshoot/TroubleshootTest$TestParameters;", "Secretum-v1.0.0(1)_gplayDebug"})
public final class TestFirebaseToken extends im.vector.app.features.settings.troubleshoot.TroubleshootTest {
    private final androidx.fragment.app.FragmentActivity context = null;
    private final im.vector.app.core.resources.StringProvider stringProvider = null;
    private final im.vector.app.core.pushers.FcmHelper fcmHelper = null;
    
    @javax.inject.Inject()
    public TestFirebaseToken(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.FragmentActivity context, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.resources.StringProvider stringProvider, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.FcmHelper fcmHelper) {
        super(0);
    }
    
    @java.lang.Override()
    public void perform(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TroubleshootTest.TestParameters testParameters) {
    }
}