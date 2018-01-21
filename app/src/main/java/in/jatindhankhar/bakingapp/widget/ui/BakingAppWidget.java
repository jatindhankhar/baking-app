package in.jatindhankhar.bakingapp.widget.ui;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.ui.activity.RecipeDetailActivity;
import in.jatindhankhar.bakingapp.utils.Constants;
import in.jatindhankhar.bakingapp.utils.Utils;
import in.jatindhankhar.bakingapp.widget.service.WidgetService;

/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link BakingAppWidgetConfigureActivity BakingAppWidgetConfigureActivity}
 */
public class BakingAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = Utils.loadTitlePref(context, appWidgetId, Constants.WIDGET_RECIPE_NAME);
        // Construct the RemoteViews object
        String jsonData = Utils.loadTitlePref(context, appWidgetId, Constants.WIDGET_INGREDIENT_DATA);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        if (!jsonData.isEmpty()) {
            Intent activityIntent = new Intent(context, RecipeDetailActivity.class);
            activityIntent.putExtra(Constants.EXTERNAL_INVOKE,true);
            activityIntent.putExtra(Constants.WIDGET_DATA, jsonData);
            Log.d("Hachi","Sending data " + jsonData);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, activityIntent, 0);
            views.setOnClickPendingIntent(R.id.appwidget_text, pendingIntent);
        }
        Intent intent = new Intent(context, WidgetService.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

        views.setRemoteAdapter(R.id.widget_list_view, intent);
        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        //super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        for (int appWidgetId : appWidgetIds) {
            Utils.deleteTitlePref(context, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

