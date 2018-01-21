package in.jatindhankhar.bakingapp.widget.ui;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.ui.activity.RecipeListActivity;
import in.jatindhankhar.bakingapp.ui.interfaces.RecyclerViewClickListener;
import in.jatindhankhar.bakingapp.utils.Constants;
import in.jatindhankhar.bakingapp.utils.Utils;
import in.jatindhankhar.bakingapp.widget.ui.adapter.RecipeConfigureAdapter;

/**
 * The configuration screen for the {@link BakingAppWidget BakingAppWidget} AppWidget.
 */
public class BakingAppWidgetConfigureActivity extends Activity implements RecyclerViewClickListener {

    @BindView(R.id.configure_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.fab_done)
    FloatingActionButton mFabDone;
    @BindView(R.id.selected_recipe)
    TextView mSelectedRecipe;

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    EditText mAppWidgetText;
    private String selectedJSON;

    private Gson mGson;
    /*View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = BakingAppWidgetConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetText = mAppWidgetText.getText().toString();

        }
    }; */
    private List<Recipes> mRecipesList;

    public BakingAppWidgetConfigureActivity() {
        super();
    }


    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);
        setContentView(R.layout.baking_app_widget_configure);
        ButterKnife.bind(this);
        selectedJSON = Utils.loadTitlePref(getApplication(), mAppWidgetId, Constants.WIDGET_INGREDIENT_DATA);
        mSelectedRecipe.setText(Utils.loadTitlePref(getApplication(), mAppWidgetId, Constants.WIDGET_RECIPE_NAME));
        mGson = new Gson();

        String jsonData = Utils.getJSONData(getApplicationContext());
        if (!jsonData.isEmpty()) {
            Type t = new TypeToken<List<Recipes>>() {
            }.getType();
            mRecipesList = mGson.fromJson(jsonData, t);
            setupRecyclerView(mRecipesList);
        }


        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
        }

    }

    private void setupRecyclerView(List<Recipes> recipesList) {
        mRecyclerView.setAdapter(new RecipeConfigureAdapter((RecipeListActivity) getParent(), recipesList, false, this));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        Recipes recipe = mRecipesList.get(position);
        String recipeName = mRecipesList.get(position).getName();
        mSelectedRecipe.setText(mRecipesList.get(position).getName());
        selectedJSON = mGson.toJson(recipe);
        Utils.saveTitlePref(getApplicationContext(), mAppWidgetId, recipeName, selectedJSON);
        mFabDone.setEnabled(true);
    }

    @OnClick(R.id.fab_done)
    public void onDone() {
        if (!selectedJSON.isEmpty()) {
            finishConfiguration();
        } else {
            Toast.makeText(this, R.string.user_recipe_select_prompt, Toast.LENGTH_SHORT).show();
        }
    }

    private void finishConfiguration() {
        //   saveTitlePref(context, mAppWidgetId, widgetText);

        // It is the responsibility of the configuration activity to update the app widget
        final Context context = BakingAppWidgetConfigureActivity.this;

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        BakingAppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
        // Make sure we pass back the original appWidgetId
        Intent resultValue = new Intent();
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
        setResult(RESULT_OK, resultValue);
        finish();
    }

}

