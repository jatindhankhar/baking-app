package in.jatindhankhar.bakingapp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.ui.fragment.RecipeDetailFragment;
import in.jatindhankhar.bakingapp.utils.Constants;

/**
 * An activity representing a single Recipe detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link RecipeListActivity}.
 */
public class RecipeDetailActivity extends AppCompatActivity {

    @BindView(R.id.recipe_detail_container)
    NestedScrollView scrollView;
    @BindView(R.id.detail_toolbar)
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);
        Boolean extFragment = getIntent().getBooleanExtra(Constants.EXTERNAL_INVOKE, false);
        String widgetData = getIntent().getStringExtra(Constants.WIDGET_DATA);
        scrollView.setFillViewport(true);
        setSupportActionBar(toolbar);
        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();

            if (extFragment) {

                if (!widgetData.isEmpty()) {
                    arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, widgetData);

                }
            } else {

                arguments.putString(RecipeDetailFragment.ARG_ITEM_ID,
                        getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));
            }
            RecipeDetailFragment fragment = new RecipeDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipe_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpTo(this, new Intent(this, RecipeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
