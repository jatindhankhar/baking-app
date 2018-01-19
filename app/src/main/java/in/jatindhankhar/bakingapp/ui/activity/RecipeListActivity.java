package in.jatindhankhar.bakingapp.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.wang.avi.AVLoadingIndicatorView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.network.RecipeClient;
import in.jatindhankhar.bakingapp.network.ServiceGenerator;
import in.jatindhankhar.bakingapp.ui.adapter.RecipeAdapter;
import in.jatindhankhar.bakingapp.dummy.DummyContent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

/**
 * An activity representing a list of Recipes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link RecipeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class RecipeListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private RecipeClient mRecipeClient;
    @BindView(R.id.pacman_loading)
    AVLoadingIndicatorView pacmanIndicator;
    @Nullable @BindView(R.id.recipe_detail_container)
    View recipeDetailContainer;
    @BindView(R.id.recipe_list)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private static final String TAG = RecipeListActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        if (recipeDetailContainer != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        mRecipeClient = ServiceGenerator.createService(RecipeClient.class);
        mRecipeClient.processRecipes().enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipes>> call, @NonNull Response<List<Recipes>> response) {
                pacmanIndicator.hide();
                if(response.isSuccessful())
                {
                    setupRecyclerView();
                    List<Recipes> recipes = response.body();
                    for(Recipes recipe : recipes)
                    {
                        Log.d(TAG,recipe.getName());
                    }
                }

                else
                {
                    Log.d(TAG,"Response was not successful");
                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Recipes>> call, @NonNull Throwable t) {

                    Log.d(TAG,"Calling was unsuccessful" + t.getMessage());
                Toast.makeText(RecipeListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void setupRecyclerView() {
        recyclerView.setAdapter(new RecipeAdapter(this, DummyContent.ITEMS, mTwoPane));
    }

}
