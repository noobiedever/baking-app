package com.example.bakingapp.utils;

import com.example.bakingapp.models.Recipe;

import retrofit2.Call;
import retrofit2.http.GET;

public interface NetworkService {
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<Recipe[]> retrieveRecipes();
}
