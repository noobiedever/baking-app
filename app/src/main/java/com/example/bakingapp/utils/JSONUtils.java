package com.example.bakingapp.utils;

import com.example.bakingapp.models.Recipe;
import com.google.gson.Gson;

public class JSONUtils {

    public static Recipe[] processFromJSON(String jsonString) {
        Gson gson = new Gson();

        Recipe[] recipes = gson.fromJson(jsonString, Recipe[].class);

        return recipes;
    }
}
