package in.jatindhankhar.bakingapp.widget.ui.adapter;

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
import in.jatindhankhar.bakingapp.model.Recipes;
import in.jatindhankhar.bakingapp.ui.activity.RecipeDetailActivity;
import in.jatindhankhar.bakingapp.ui.activity.RecipeListActivity;
import in.jatindhankhar.bakingapp.ui.fragment.RecipeDetailFragment;
import in.jatindhankhar.bakingapp.ui.interfaces.RecyclerViewClickListener;

/**
 * Created by jatin on 1/19/18.
 */

public class RecipeConfigureAdapter
        extends RecyclerView.Adapter<RecipeConfigureAdapter.ViewHolder> {

    private final RecipeListActivity mParentActivity;
    private final List<Recipes> mRecipes;
    private final boolean mTwoPane;
    private static RecyclerViewClickListener mWidgetItemListener;
    private Gson gson;




    public RecipeConfigureAdapter(RecipeListActivity parent, List<Recipes> recipes, boolean twoPane, RecyclerViewClickListener recyclerListener) {
        // Constructor for Widget Configuration Activity
        mRecipes = recipes;
        mParentActivity = parent;
        mTwoPane = twoPane;
        gson = new Gson();
        mWidgetItemListener = recyclerListener;
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

    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            ButterKnife.bind(this, view);
            mParentView = view;
            if(mWidgetItemListener != null)
                view.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            mWidgetItemListener.recyclerViewListClicked(v,this.getLayoutPosition());
        }
    }
}
