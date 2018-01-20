package in.jatindhankhar.bakingapp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.IngredientsItem;
import in.jatindhankhar.bakingapp.ui.adapter.IngredientAdapter;
import in.jatindhankhar.bakingapp.ui.adapter.StepsAdapter;

import static in.jatindhankhar.bakingapp.utils.Constants.JSON_STRING;

/**
 * Created by jatin on 1/20/18.
 */

public class IngredientFragment extends Fragment {

    private List<IngredientsItem> ingredientsItems;
    private RecyclerView recyclerView;

    public static IngredientFragment newInstance(String jsonString) {
        IngredientFragment ingredientFragment = new IngredientFragment();
        Bundle args = new Bundle();
        //args.putInt("someInt", page);
        //args.putString("someTitle", title);
        args.putString(JSON_STRING, jsonString);
        ingredientFragment.setArguments(args);
        return ingredientFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ingredient_fragment, container, false);
        recyclerView = (RecyclerView) view;
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        recyclerView.setAdapter(new IngredientAdapter(ingredientsItems));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        Type t = new TypeToken<List<IngredientsItem>>() {}.getType();
        ingredientsItems = gson.fromJson(getArguments().getString(JSON_STRING), t);
    }
}
