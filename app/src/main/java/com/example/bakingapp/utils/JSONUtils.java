package com.example.bakingapp.utils;

import com.example.bakingapp.models.Recipe;
import com.google.gson.Gson;

public class JSONUtils {
    private static final String TAG = JSONUtils.class.getSimpleName();

    public static Recipe[] processFromJSON(String jsonString) {
        Gson gson = new Gson();

        Recipe[] recipes = gson.fromJson(jsonString, Recipe[].class);

        return recipes;
    }
}
