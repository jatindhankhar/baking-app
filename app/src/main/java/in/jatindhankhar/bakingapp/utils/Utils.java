package in.jatindhankhar.bakingapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static in.jatindhankhar.bakingapp.utils.Constants.IS_CACHED;
import static in.jatindhankhar.bakingapp.utils.Constants.JSON_STRING;
import static in.jatindhankhar.bakingapp.utils.Constants.PREF_FILE;

/**
 * Created by jatin on 1/20/18.
 */

public final class Utils {
    private Utils() {

    }

    public static void saveJSONData(Context context, String target) {
        getSharedPref(context)
                .edit().putString(JSON_STRING, target)
                .putBoolean(IS_CACHED, true)
                .apply();
    }

    public static String getJSONData(Context context) {
        return getSharedPref(context)
                .getString(JSON_STRING, "");
    }

    public static SharedPreferences getSharedPref(Context context) {
        return context.getSharedPreferences(PREF_FILE, context.MODE_PRIVATE);
    }

    public static boolean isCached(Context context) {
        return getSharedPref(context).getBoolean(IS_CACHED, false);
    }
}
