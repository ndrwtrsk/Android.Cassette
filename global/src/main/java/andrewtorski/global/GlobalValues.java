package andrewtorski.global;

import android.content.Context;

/**
 * Provides various different values - constant and variables - which would be otherwise
 * inaccessible without breaking the architecture.
 */
public final class GlobalValues {

    /**
     * Current context in the application scope.
     * The main reasoning for this field here is that it provides DbOpenHelper with Context which is
     * required to instantiate DbOpenHelper and subsequently open a connection to the SQLite
     * database.
     */
    private static Context context;

    /**
     * Private default constructor which disallows creation of instances of this classes.
     */
    private GlobalValues() {
    }

    @SuppressWarnings("unchecked")
    public static void setContext(Context context) {
        context = context;
    }

    @SuppressWarnings("unchecked")
    public static Context getContext() {
        return context;
    }

}
