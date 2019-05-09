package com.example.bakingapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utils.FileUtils;
import com.example.bakingapp.utils.JSONUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeCardFragment extends Fragment
        implements RecipeAdapter.RecipeClickHandler, LoaderManager.LoaderCallbacks<String> {

    // RecyclerView
    @BindView(R.id.rv_recipe_list)
    RecyclerView mRecipeCardRecyclerView;

    RecipeAdapter mRecipeAdapter;

    public static final String TAG = RecipeCardFragment.class.getSimpleName();
    private static final int LOADER_ID = 1111;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LoaderManager loaderManager = LoaderManager.getInstance(this);
        loaderManager.initLoader(LOADER_ID, null, this).forceLoad();

        View rootView = inflater.inflate(R.layout.fragment_recipes, container, false);
        ButterKnife.bind(this, rootView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                RecyclerView.VERTICAL, false);
        mRecipeCardRecyclerView.setLayoutManager(layoutManager);

        mRecipeAdapter = new RecipeAdapter(this);
        mRecipeCardRecyclerView.setAdapter(null);

        return rootView;
    }

    @Override
    public void onClick(Recipe recipe) {

    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        return new AsyncTaskLoader<String>(getContext()) {
            @Nullable
            @Override
            public String loadInBackground() {
                String data = FileUtils.loadJSONFromFile(getContext());

                Log.v(TAG, data);
                return data;
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        Recipe[] recipes = JSONUtils.processFromJSON(data);
        mRecipeAdapter.setRecipeData(recipes);
        mRecipeCardRecyclerView.setAdapter(mRecipeAdapter);
        Log.i(TAG, recipes.length + "");
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }
}
