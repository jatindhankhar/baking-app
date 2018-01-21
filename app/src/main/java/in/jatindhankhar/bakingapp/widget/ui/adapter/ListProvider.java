package in.jatindhankhar.bakingapp.widget.ui.adapter;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.google.gson.Gson;

import java.util.List;

import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.IngredientsItem;
import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.utils.Constants;
import in.jatindhankhar.bakingapp.widget.ui.BakingAppWidgetConfigureActivity;

import static in.jatindhankhar.bakingapp.utils.Utils.loadTitlePref;

/**
 * Created by jatin on 1/21/18.
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {
    private List<IngredientsItem> mIngredientsItems;
    private Context mContext;
    private int mAppWidgetId;


    public ListProvider(Context mContext, Intent intent) {
        this.mContext = mContext;
        this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        String jsonData = loadTitlePref(mContext, mAppWidgetId, Constants.WIDGET_INGREDIENT_DATA);
        if (!jsonData.isEmpty()) {
            Gson gson = new Gson();
            mIngredientsItems = gson.fromJson(jsonData, Recipes.class).getIngredients();
        }

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mIngredientsItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
         RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.ingredient_list_item);
         IngredientsItem ingredientItem = mIngredientsItems.get(position);
         CharSequence text = ingredientItem.getQuantity() + " " + ingredientItem.getMeasure() + " of " + ingredientItem.getIngredient();
        remoteViews.setTextViewText(R.id.ingredient_name,text);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
