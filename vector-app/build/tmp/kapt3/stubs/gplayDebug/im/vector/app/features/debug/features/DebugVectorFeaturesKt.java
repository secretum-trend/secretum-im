package im.vector.app.features.debug.features;

import android.content.Context;
import androidx.datastore.core.DataStore;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import im.vector.app.config.OnboardingVariant;
import im.vector.app.features.DefaultVectorFeatures;
import im.vector.app.features.VectorFeatures;
import kotlin.reflect.KClass;

@kotlin.Metadata(mv = {1, 8, 0}, k = 2, d1 = {"\u0000<\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0010\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a!\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\"\u0010\b\u0000\u0010\u000b\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u000b0\fH\u0082\b\u001a,\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t\"\u000e\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000eH\u0002\u001a*\u0010\u000f\u001a\u00020\u0010\"\u000e\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f*\u00020\u00022\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000eH\u0002\u001a&\u0010\u0011\u001a\u0004\u0018\u0001H\u000b\"\u0010\b\u0000\u0010\u000b\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u000b0\f*\u00020\u0002H\u0082\b\u00a2\u0006\u0002\u0010\u0012\u001a7\u0010\u0013\u001a\u00020\u0014\"\u000e\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f*\u00020\u00152\u0006\u0010\u0016\u001a\u0002H\u000b2\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000eH\u0002\u00a2\u0006\u0002\u0010\u0017\u001a*\u0010\u0018\u001a\u00020\u0014\"\u000e\b\u0000\u0010\u000b*\b\u0012\u0004\u0012\u0002H\u000b0\f*\u00020\u00152\f\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u000eH\u0002\"%\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005\u00a8\u0006\u0019"}, d2 = {"dataStore", "Landroidx/datastore/core/DataStore;", "Landroidx/datastore/preferences/core/Preferences;", "Landroid/content/Context;", "getDataStore", "(Landroid/content/Context;)Landroidx/datastore/core/DataStore;", "dataStore$delegate", "Lkotlin/properties/ReadOnlyProperty;", "enumPreferencesKey", "Landroidx/datastore/preferences/core/Preferences$Key;", "", "T", "", "type", "Lkotlin/reflect/KClass;", "containsEnum", "", "getEnum", "(Landroidx/datastore/preferences/core/Preferences;)Ljava/lang/Enum;", "putEnum", "", "Landroidx/datastore/preferences/core/MutablePreferences;", "value", "(Landroidx/datastore/preferences/core/MutablePreferences;Ljava/lang/Enum;Lkotlin/reflect/KClass;)V", "removeEnum", "vector-app_gplayDebug"})
public final class DebugVectorFeaturesKt {
    private static final kotlin.properties.ReadOnlyProperty dataStore$delegate = null;
    
    private static final androidx.datastore.core.DataStore<androidx.datastore.preferences.core.Preferences> getDataStore(android.content.Context $this$dataStore) {
        return null;
    }
    
    private static final <T extends java.lang.Enum<T>>void removeEnum(androidx.datastore.preferences.core.MutablePreferences $this$removeEnum, kotlin.reflect.KClass<T> type) {
    }
    
    private static final <T extends java.lang.Enum<T>>boolean containsEnum(androidx.datastore.preferences.core.Preferences $this$containsEnum, kotlin.reflect.KClass<T> type) {
        return false;
    }
    
    private static final <T extends java.lang.Enum<T>>void putEnum(androidx.datastore.preferences.core.MutablePreferences $this$putEnum, T value, kotlin.reflect.KClass<T> type) {
    }
    
    private static final <T extends java.lang.Enum<T>>androidx.datastore.preferences.core.Preferences.Key<java.lang.String> enumPreferencesKey(kotlin.reflect.KClass<T> type) {
        return null;
    }
}