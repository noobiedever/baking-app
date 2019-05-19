package com.example.bakingapp.data;

import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Step;
import com.google.gson.Gson;

import androidx.room.TypeConverter;

public class RecipeTypeConverters {

    private static Gson gson = new Gson();

    @TypeConverter
    public static String ingredientsToDb(Ingredient[] ingredients) {
        return gson.toJson(ingredients);
    }

    @TypeConverter
    public static Ingredient[] ingredientsFromDb(String json) {
        return gson.fromJson(json, Ingredient[].class);
    }

    @TypeConverter
    public static String stepsToDb(Step[] steps) {
        return gson.toJson(steps);
    }

    @TypeConverter
    public static Step[] stepsFromDb(String json) {
        return gson.fromJson(json, Step[].class);
    }
}
