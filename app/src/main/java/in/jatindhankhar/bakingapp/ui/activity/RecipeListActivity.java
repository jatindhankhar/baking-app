package in.jatindhankhar.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wang.avi.AVLoadingIndicatorView;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.network.RecipeClient;
import in.jatindhankhar.bakingapp.network.ServiceGenerator;
import in.jatindhankhar.bakingapp.ui.adapter.RecipeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.jatindhankhar.bakingapp.utils.Utils.getJSONData;
import static in.jatindhankhar.bakingapp.utils.Utils.isCached;
import static in.jatindhankhar.bakingapp.utils.Utils.saveJSONData;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    private static final String TAG = RecipeListActivity.class.getSimpleName();
    @BindView(R.id.pacman_loading)
    AVLoadingIndicatorView pacmanIndicator;
    @Nullable
    @BindView(R.id.recipe_detail_container)
    View recipeDetailContainer;
    @BindView(R.id.recipe_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecipeClient mRecipeClient;
    private Gson mGson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        mGson = new Gson();

        if (recipeDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        if (!isCached(getApplicationContext()) || getJSONData(getApplicationContext()).isEmpty()) {
            // Fetch Data only if not locally stored
            fetchJSON();
        } else {
            List<Recipes> recipes;
            Type t = new TypeToken<List<Recipes>>() {
            }.getType();
            recipes = mGson.fromJson(getJSONData(getApplicationContext()), t);
            pacmanIndicator.hide();
            setupRecyclerView(recipes);
        }
    }

    private void setupRecyclerView(List<Recipes> recipes) {
        recyclerView.setAdapter(new RecipeAdapter(this, recipes, mTwoPane));
    }


    private void fetchJSON() {
        mRecipeClient = ServiceGenerator.createService(RecipeClient.class);
        mRecipeClient.processRecipes().enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipes>> call, @NonNull Response<List<Recipes>> response) {
                pacmanIndicator.hide();
                if (response.isSuccessful()) {
                    setupRecyclerView(response.body());
                    saveJSONData(getBaseContext(), mGson.toJson(response.body()));

                } else {
                    Log.d(TAG, "Response was not successful");
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Recipes>> call, @NonNull Throwable t) {

                Log.d(TAG, "Calling was unsuccessful" + t.getMessage());
                Toast.makeText(RecipeListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
