package in.jatindhankhar.bakingapp.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.dummy.DummyContent;
import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.ui.activity.RecipeDetailActivity;
import in.jatindhankhar.bakingapp.ui.activity.RecipeListActivity;
import in.jatindhankhar.bakingapp.ui.fragment.RecipeDetailFragment;

/**
 * Created by jatin on 1/19/18.
 */

public class RecipeAdapter
        extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private final RecipeListActivity mParentActivity;
    private final List<Recipes> mRecipes;
    private final boolean mTwoPane;
    private Gson gson;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Recipes r = (Recipes) view.getTag();
            if (mTwoPane) {
                Bundle arguments = new Bundle();
                arguments.putString(RecipeDetailFragment.ARG_ITEM_ID, gson.toJson(r));
                RecipeDetailFragment fragment = new RecipeDetailFragment();
                fragment.setArguments(arguments);
                mParentActivity.getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .commit();
            } else {
                Context context = view.getContext();
                Intent intent = new Intent(context, RecipeDetailActivity.class);
                intent.putExtra(RecipeDetailFragment.ARG_ITEM_ID, gson.toJson(r));

                context.startActivity(intent);
            }
        }
    };

    public RecipeAdapter(RecipeListActivity parent,
                         List<Recipes> recipes,
                         boolean twoPane) {
        mRecipes = recipes;
        mParentActivity = parent;
        mTwoPane = twoPane;
        gson = new Gson();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // holder.mIdView.setText(mRecipes.get(position).getName());
        //  holder.mContentView.setText(mRecipes.get(position).getServings());

        holder.itemView.setTag(mRecipes.get(position));
        holder.mIdView.setText(mRecipes.get(position).getName());
        holder.mServingCount.setText(mRecipes.get(position).getServings() + "");
        holder.mIngredientCount.setText(mRecipes.get(position).getIngredients().size() + "");
        holder.mStepsCount.setText(mRecipes.get(position).getSteps().size() + "");
        holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.id_text)
        TextView mIdView;
        @BindView(R.id.serving_count)
        TextView mServingCount;
        View mParentView;
        @BindView(R.id.step_count)
        TextView mStepsCount;
        @BindView(R.id.ingredient_count)
        TextView mIngredientCount;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
            mParentView = view;

        }
    }
}
