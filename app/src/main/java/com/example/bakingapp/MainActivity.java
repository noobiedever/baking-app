package com.example.bakingapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.bakingapp.data.AppDatabase;
import com.example.bakingapp.utils.FileUtils;

public class MainActivity extends AppCompatActivity {
    private RecipeCardFragment mRecipeCardFragment;
    private static final String FRAGMENT_KEY = "fragment-key";
    private final int SMALLEST_WIDTH_QUALIFIER = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get smallest screen width of current device
        int sw = getResources().getConfiguration().smallestScreenWidthDp;

        // if device has smallest screen width of 600 lock orientation to landscape
        if(sw >= SMALLEST_WIDTH_QUALIFIER) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        setContentView(R.layout.activity_main);

        // if savedInstanceState is not null us FragmentManager to retrieve Fragment
        // else create a new instance of that Fragment
        if(savedInstanceState != null) {
            mRecipeCardFragment = (RecipeCardFragment) getSupportFragmentManager()
                    .getFragment(savedInstanceState, FRAGMENT_KEY);
        } else {
            mRecipeCardFragment = new RecipeCardFragment();

            FragmentManager fragmentManager = getSupportFragmentManager();

            fragmentManager.beginTransaction()
                    .add(R.id.fragment_container, mRecipeCardFragment)
                    .commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, FRAGMENT_KEY, mRecipeCardFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        getSupportFragmentManager().getFragment(savedInstanceState, FRAGMENT_KEY);
    }
}