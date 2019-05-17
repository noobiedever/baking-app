package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

public class RecipeDetailActivity extends AppCompatActivity {
    private RecipeDetailFragment mRecipeDetailFragment;
    private static final String FRAGMENT_KEY = "fragment-key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState != null) {
            mRecipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_KEY);
        } else {

            mRecipeDetailFragment = new RecipeDetailFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, mRecipeDetailFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, mRecipeDetailFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mRecipeDetailFragment = (RecipeDetailFragment) getSupportFragmentManager()
                .getFragment(savedInstanceState, FRAGMENT_KEY);
    }
}
