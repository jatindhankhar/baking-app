package in.jatindhankhar.bakingapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import in.jatindhankhar.bakingapp.R;
import in.jatindhankhar.bakingapp.model.IngredientsItem;
import in.jatindhankhar.bakingapp.model.StepsItem;
import in.jatindhankhar.bakingapp.ui.adapter.StepsAdapter;

import static in.jatindhankhar.bakingapp.utils.Constants.JSON_STRING;

/**
 * Created by jatin on 1/20/18.
 */

public class StepsFragment extends Fragment {


    private RecyclerView recyclerView;
    private List<StepsItem> mSteps;
    public static StepsFragment newInstance( String jsonString)
    {

        StepsFragment stepsFragment = new StepsFragment();
        Bundle args = new Bundle();
        args.putString(JSON_STRING,jsonString);
        stepsFragment.setArguments(args);
        return stepsFragment;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.steps_fragment, container, false);
        recyclerView = (RecyclerView) view;
        setUpRecyclerView();
        return view;
    }

    private void setUpRecyclerView() {
        recyclerView.setAdapter(new StepsAdapter(getContext(),mSteps));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Gson gson = new Gson();
        Type t = new TypeToken<List<StepsItem>>() {}.getType();
        mSteps = gson.fromJson(getArguments().getString(JSON_STRING),t);
    }
}
