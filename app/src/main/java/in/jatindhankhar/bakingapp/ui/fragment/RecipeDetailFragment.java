package in.jatindhankhar.bakingapp.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.ui.activity.RecipeDetailActivity;
import in.jatindhankhar.bakingapp.ui.activity.RecipeListActivity;
import in.jatindhankhar.bakingapp.ui.adapter.TabPageAdapter;

public class RecipeDetailFragment extends Fragment {

    public static final String ARG_ITEM_ID = "item_id";


    private Recipes mRecipe;
    private Gson mGson;


    public RecipeDetailFragment() {
        mGson = new Gson();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {

            mRecipe = mGson.fromJson(getArguments().getString(ARG_ITEM_ID), Recipes.class);

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mRecipe.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_detail, container, false);
        ViewPager vpPager = (ViewPager) rootView.findViewById(R.id.view_pager);
         TabPageAdapter tabPageAdapter = new TabPageAdapter(this.getFragmentManager(),mRecipe,getContext());
        vpPager.setAdapter(tabPageAdapter);
        return rootView;
    }
}
