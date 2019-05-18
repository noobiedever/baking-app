package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.models.Step;

public class RecipeDetailActivity extends AppCompatActivity {
    private RecipeDetailFragment mRecipeDetailFragment;
    private StepDetailFragment mStepDetailFragment;
    private static final String RECIPE_FRAGMENT_KEY = "recipe-fragment-key";
    private static final String STEP_FRAGMENT_KEY = "step-fragment-key";
    private static final int SMALLEST_WIDTH_QUALIFIER = 600;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int sw = getResources().getConfiguration().smallestScreenWidthDp;

        if(sw >= SMALLEST_WIDTH_QUALIFIER) {
            mTwoPane = true;
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

            setContentView(R.layout.activity_recipe_detail);

            if(savedInstanceState != null) {
                mRecipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, RECIPE_FRAGMENT_KEY);

                mStepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, STEP_FRAGMENT_KEY);
            } else {
                mTwoPane = false;
                mRecipeDetailFragment = new RecipeDetailFragment();
                mStepDetailFragment = new StepDetailFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.fl_recipe_detail_container, mRecipeDetailFragment)
                        .add(R.id.fl_step_detail_container, mStepDetailFragment)
                        .commit();

                Intent intent = getIntent();

                Recipe recipe = intent.getParcelableExtra(RecipeCardFragment.EXTRA);
                Step[] steps = recipe.getSteps();

                Bundle bundle = new Bundle();
                final int DEFAULT_VALUE = 0;
                bundle.putInt(RecipeDetailFragment.EXTRA_POSITION, DEFAULT_VALUE);
                bundle.putParcelableArray(RecipeDetailFragment.EXTRA, steps);

                mStepDetailFragment.setArguments(bundle);
            }
        } else {
            setContentView(R.layout.activity_recipe_detail);

            if(savedInstanceState != null) {
                mRecipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager()
                        .getFragment(savedInstanceState, RECIPE_FRAGMENT_KEY);
            } else {

                mRecipeDetailFragment = new RecipeDetailFragment();

                FragmentManager fragmentManager = getSupportFragmentManager();

                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, mRecipeDetailFragment)
                        .commit();
            }
        }


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, RECIPE_FRAGMENT_KEY, mRecipeDetailFragment);
        if(mTwoPane)
        getSupportFragmentManager().putFragment(outState, STEP_FRAGMENT_KEY, mStepDetailFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager()
                .getFragment(savedInstanceState, RECIPE_FRAGMENT_KEY);
        if(mTwoPane) mStepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                .getFragment(savedInstanceState, STEP_FRAGMENT_KEY);
    }
}
