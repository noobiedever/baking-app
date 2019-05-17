package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class StepDetailActivity extends AppCompatActivity {
    private static final String FRAGMENT_KEY = "fragment-key";
    private StepDetailFragment mStepDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null){
            mStepDetailFragment = (StepDetailFragment)getSupportFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_KEY);
        } else {
            mStepDetailFragment = new StepDetailFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, mStepDetailFragment, FRAGMENT_KEY)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, mStepDetailFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mStepDetailFragment = (StepDetailFragment) getSupportFragmentManager()
                .getFragment(savedInstanceState, FRAGMENT_KEY);
    }
}
