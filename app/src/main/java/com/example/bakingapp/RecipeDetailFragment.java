package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.models.Step;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailFragment extends Fragment implements StepAdapter.StepClickHandler {
    private static final String TAG = RecipeDetailFragment.class.getSimpleName();
    public static final String EXTRA = TAG + "-extra";
    public static final String EXTRA_POSITION = "extra-position";

    private Recipe mRecipe;

    @BindView(R.id.rv_ingredient_steps)
    RecyclerView mStepsRecyclerView;

    @BindView(R.id.tv_recipe_ingredients)
    TextView mIngredientsTextView;

    public RecipeDetailFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();
        mRecipe = intent.getParcelableExtra(RecipeCardFragment.EXTRA);

        Log.v(TAG, mRecipe.getName() + "... Ingredients -> " + mRecipe.getIngredients().length +
                "... Steps -> " + mRecipe.getSteps().length);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int orientation = getActivity().getResources().getConfiguration().orientation;

        View rootView = inflater.inflate(R.layout.activity_recipe_detail,
                container, false);
        ButterKnife.bind(this, rootView);

        mStepsRecyclerView.setNestedScrollingEnabled(false);

        for(Ingredient ingredient : mRecipe.getIngredients()) {
            String text = ingredient.toString() + "\n";
            mIngredientsTextView.append(text);
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        mStepsRecyclerView.setLayoutManager(layoutManager);

        StepAdapter adapter = new StepAdapter(this, mRecipe.getIngredients());
        mStepsRecyclerView.setAdapter(adapter);
        adapter.setStepsData(mRecipe.getSteps());

        return rootView;
    }

    @Override
    public void onClick(Step[] steps, int position) {

        Context context = getContext();
        Class classToStart = StepDetailActivity.class;

        Intent intent = new Intent(context, classToStart);
        intent.putExtra(EXTRA_POSITION, position);
        intent.putExtra(EXTRA, steps);

        startActivity(intent);
    }
}
