package com.example.bakingapp.data;


import android.app.Application;
import android.widget.ViewFlipper;

import com.example.bakingapp.models.Recipe;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class RecipeViewModel extends AndroidViewModel {
    private LiveData<Recipe[]> recipes;

    public RecipeViewModel(@NonNull Application application) {
        super(application);

        recipes = AppDatabase.getInstance(this.getApplication()).recipeDao().loadRecipes();
    }

    public LiveData<Recipe[]> getRecipes() {
        return this.recipes;
    }
}
