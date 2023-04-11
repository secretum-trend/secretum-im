package im.vector.app.leakcanary;

import im.vector.app.core.debug.LeakDetector;
import leakcanary.LeakCanary;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 8, 0}, k = 1, d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0007\b\u0007\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0005H\u0016\u00a8\u0006\u0006"}, d2 = {"Lim/vector/app/leakcanary/LeakCanaryLeakDetector;", "Lim/vector/app/core/debug/LeakDetector;", "()V", "enable", "", "", "vector-app_gplayDebug"})
public final class LeakCanaryLeakDetector implements im.vector.app.core.debug.LeakDetector {
    
    @javax.inject.Inject()
    public LeakCanaryLeakDetector() {
        super();
    }
    
    @java.lang.Override()
    public void enable(boolean enable) {
    }
}