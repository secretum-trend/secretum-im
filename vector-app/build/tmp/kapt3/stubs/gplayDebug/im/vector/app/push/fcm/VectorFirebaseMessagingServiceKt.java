package im.vector.app.push.fcm;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import dagger.hilt.android.AndroidEntryPoint;
import im.vector.app.R;
import im.vector.app.core.di.ActiveSessionHolder;
import im.vector.app.core.pushers.FcmHelper;
import im.vector.app.core.pushers.PushParser;
import im.vector.app.core.pushers.PushersManager;
import im.vector.app.core.pushers.UnifiedPushHelper;
import im.vector.app.core.pushers.VectorPushHandler;
import im.vector.app.features.settings.VectorPreferences;
import org.matrix.android.sdk.api.logger.LoggerTag;
import timber.log.Timber;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0002"}, d2 = {"loggerTag", "Lorg/matrix/android/sdk/api/logger/LoggerTag;", "Secretum-v1.0.0(1)_gplayDebug"})
public final class VectorFirebaseMessagingServiceKt {
    private static final org.matrix.android.sdk.api.logger.LoggerTag loggerTag = null;
}