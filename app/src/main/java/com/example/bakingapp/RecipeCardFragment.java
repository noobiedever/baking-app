package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.data.AppDatabase;
import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utils.NetworkUtils;

import java.util.Arrays;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeCardFragment extends Fragment
        implements RecipeAdapter.RecipeClickHandler, LoaderManager.LoaderCallbacks<Recipe[]> {

    // RecyclerView
    @BindView(R.id.rv_recipe_list)
    RecyclerView mRecipeCardRecyclerView;

    private Recipe[] mRecipes;

    private RecipeAdapter mRecipeAdapter;

    private static final String TAG = RecipeCardFragment.class.getSimpleName();
    public static final String EXTRA = TAG + "-extra";
    public static final String PARCELABLE_EXTRA = "parcelable-extra";
    private static final int LOADER_ID = 1111;
    private static final int SMALLEST_WIDTH_QAULIFIER = 600;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, rootView);

        int sw = getActivity().getResources().getConfiguration().smallestScreenWidthDp;

        RecyclerView.LayoutManager layoutManager;
        if(sw >= SMALLEST_WIDTH_QAULIFIER) {
            layoutManager = new GridLayoutManager(getContext(),
                    2, RecyclerView.VERTICAL, false);
        } else {
            layoutManager = new LinearLayoutManager(getContext(),
                    RecyclerView.VERTICAL, false);
        }

        mRecipeCardRecyclerView.setLayoutManager(layoutManager);
        mRecipeAdapter = new RecipeAdapter(this);
        mRecipeCardRecyclerView.setAdapter(mRecipeAdapter);
        if(savedInstanceState == null) {
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.initLoader(LOADER_ID, null, this).forceLoad();
        } else {
            Parcelable[] parcelables = savedInstanceState.getParcelableArray(PARCELABLE_EXTRA);
            mRecipes = Arrays.copyOf(parcelables, parcelables.length, Recipe[].class);
            mRecipeAdapter.setRecipeData(mRecipes);
        }
        return rootView;
    }

    @Override
    public void onClick(Recipe recipe) {
        Log.v(TAG, recipe.getName() + " contains " + recipe.getIngredients().length +
                " ingredients and " + recipe.getSteps().length + " steps");

        Context context = getContext();
        Class activityToStart = RecipeDetailActivity.class;

        Intent intent = new Intent(context, activityToStart);
        intent.putExtra(EXTRA, recipe);

        startActivity(intent);
    }

    @NonNull
    @Override
    public Loader<Recipe[]> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<Recipe[]>(getContext()) {
            @Nullable
            @Override
            public Recipe[] loadInBackground() {
                Recipe[] recipes = NetworkUtils.getRecipes();

                AppDatabase.getInstance(getContext()).movieDao().insertRecipes(recipes);
                Log.v(TAG, recipes.length + "");
                return recipes;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Recipe[]> loader, Recipe[] data) {
        mRecipes = data;
        mRecipeAdapter.setRecipeData(data);

        mRecipeCardRecyclerView.setAdapter(mRecipeAdapter);

        Log.v(TAG, "Loaded " + data.length + " recipes... ");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Recipe[]> loader) {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArray(PARCELABLE_EXTRA, mRecipes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState != null) {
            Parcelable[] parcelables = savedInstanceState.getParcelableArray(PARCELABLE_EXTRA);
            mRecipes = Arrays.copyOf(parcelables, parcelables.length, Recipe[].class);
            mRecipeAdapter.setRecipeData(mRecipes);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            Parcelable[] parcelables = savedInstanceState.getParcelableArray(PARCELABLE_EXTRA);
            mRecipes = Arrays.copyOf(parcelables, parcelables.length, Recipe[].class);
            if(mRecipeAdapter == null) mRecipeAdapter = new RecipeAdapter(this);
            mRecipeAdapter.setRecipeData(mRecipes);
        }
    }
}
