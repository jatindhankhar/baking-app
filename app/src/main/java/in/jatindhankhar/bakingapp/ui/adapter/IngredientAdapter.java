package in.jatindhankhar.bakingapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.IngredientsItem;

/**
 * Created by jatin on 1/20/18.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.ViewHolder> {

    private List<IngredientsItem> ingredientsItem;
    public IngredientAdapter(List<IngredientsItem> ingredients)
    {
        this.ingredientsItem = ingredients;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_card, parent, false);
        return new  IngredientAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.ingredientName.setText(ingredientsItem.get(position).getIngredient());
        holder.measureText.setText(ingredientsItem.get(position).getMeasure());
        holder.quantityText.setText(ingredientsItem.get(position).getQuantity() + "");
    }

    @Override
    public int getItemCount() {
        return ingredientsItem.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ingredient_name)
        TextView ingredientName;
        @BindView(R.id.measure_text)
        TextView measureText;
        @BindView(R.id.quantity_text)
        TextView quantityText;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
