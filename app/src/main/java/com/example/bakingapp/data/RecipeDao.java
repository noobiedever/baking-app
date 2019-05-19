package com.example.bakingapp.data;

import com.example.bakingapp.models.Recipe;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class RecipeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void insertRecipe(Recipe recipe);

    @Query("SELECT * FROM recipe")
    public abstract Recipe[] loadRecipes();

    @Transaction
    public void insertRecipes(Recipe[] recipes) {
        for(Recipe recipe: recipes) {
            insertRecipe(recipe);
        }
    }
}