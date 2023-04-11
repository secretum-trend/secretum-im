package im.vector.app.gplay.features.settings.troubleshoot;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import im.vector.app.R;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.pushers.PushersManager;
import im.vector.app.core.resources.StringProvider;
import im.vector.app.features.settings.troubleshoot.TroubleshootTest;
import org.matrix.android.sdk.api.session.pushers.PusherState;
import javax.inject.Inject;

/**
 * Force registration of the token to HomeServer
 */
@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B/\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u00a2\u0006\u0002\u0010\fJ\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lim/vector/app/gplay/features/settings/troubleshoot/TestTokenRegistration;", "Lim/vector/app/features/settings/troubleshoot/TroubleshootTest;", "context", "Landroidx/fragment/app/FragmentActivity;", "stringProvider", "Lim/vector/app/core/resources/StringProvider;", "pushersManager", "Lim/vector/app/core/pushers/PushersManager;", "activeSessionHolder", "Lim/vector/app/core/di/ActiveSessionHolder;", "fcmHelper", "Lim/vector/app/core/pushers/FcmHelper;", "(Landroidx/fragment/app/FragmentActivity;Lim/vector/app/core/resources/StringProvider;Lim/vector/app/core/pushers/PushersManager;Lim/vector/app/core/di/ActiveSessionHolder;Lim/vector/app/core/pushers/FcmHelper;)V", "perform", "", "testParameters", "Lim/vector/app/features/settings/troubleshoot/TroubleshootTest$TestParameters;", "vector-app_gplayDebug"})
public final class TestTokenRegistration extends im.vector.app.features.settings.troubleshoot.TroubleshootTest {
    private final androidx.fragment.app.FragmentActivity context = null;
    private final im.vector.app.core.resources.StringProvider stringProvider = null;
    private final im.vector.app.core.pushers.PushersManager pushersManager = null;
    private final im.vector.app.core.di.ActiveSessionHolder activeSessionHolder = null;
    private final im.vector.app.core.pushers.FcmHelper fcmHelper = null;
    
    @javax.inject.Inject()
    public TestTokenRegistration(@org.jetbrains.annotations.NotNull()
    androidx.fragment.app.FragmentActivity context, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.resources.StringProvider stringProvider, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.PushersManager pushersManager, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.di.ActiveSessionHolder activeSessionHolder, @org.jetbrains.annotations.NotNull()
    im.vector.app.core.pushers.FcmHelper fcmHelper) {
        super(0);
    }
    
    @java.lang.Override()
    public void perform(@org.jetbrains.annotations.NotNull()
    im.vector.app.features.settings.troubleshoot.TroubleshootTest.TestParameters testParameters) {
    }
}