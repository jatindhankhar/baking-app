package in.jatindhankhar.bakingapp.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.google.gson.Gson;

import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.ui.fragment.IngredientFragment;
import in.jatindhankhar.bakingapp.ui.fragment.StepsFragment;

import static in.jatindhankhar.bakingapp.utils.Constants.TAB_ITEMS;

/**
 * Created by jatin on 1/20/18.
 */

public class TabPageAdapter extends FragmentPagerAdapter {
    private Recipes mRecipe;
    private Context mContext;
    private Gson mGson;
    public TabPageAdapter(FragmentManager fm,Recipes recipe,Context context) {
        super(fm);
        this.mRecipe = recipe;
        this.mContext = context;
        this.mGson = new Gson();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) return "Ingredients";
        return "Steps";
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return IngredientFragment.newInstance(mGson.toJson(mRecipe.getIngredients()));
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return StepsFragment.newInstance(mGson.toJson(mRecipe.getSteps()));
        }
        return null;
    }

    @Override
    public int getCount() {
        return TAB_ITEMS;
    }
}
