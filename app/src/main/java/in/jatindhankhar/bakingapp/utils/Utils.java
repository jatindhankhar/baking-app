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

    // Write the prefix to the SharedPreferences object for this widget
    public static void saveTitlePref(Context context, int appWidgetId, String recipeName, String jsonData) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREF_FILE, 0).edit();
        prefs.putString(Constants.WIDGET_RECIPE_NAME + appWidgetId, recipeName)
        .putString(Constants.WIDGET_INGREDIENT_DATA + appWidgetId,jsonData);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    public static String loadTitlePref(Context context, int appWidgetId, String key) {
        SharedPreferences prefs = context.getSharedPreferences(PREF_FILE, 0);
         return prefs.getString(key + appWidgetId, "");

    }

    public static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREF_FILE, 0).edit();
        prefs.remove(Constants.WIDGET_RECIPE_NAME + appWidgetId);
        prefs.remove(Constants.WIDGET_INGREDIENT_DATA + appWidgetId);
        prefs.apply();
    }
}
