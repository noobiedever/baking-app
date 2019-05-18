package com.example.bakingapp.utils;

import android.util.Log;

import com.example.bakingapp.models.Recipe;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL =
            "https://d17h27t6h515a5.cloudfront.net/";

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static NetworkService networkService = retrofit.create(NetworkService.class);

    public static Recipe[] getRecipes() {
        Call<Recipe[]> call = networkService.retrieveRecipes();
        Response<Recipe[]> response;

        try {
            response = call.execute();
            return response.body();
        } catch (IOException e) {
            Log.e(TAG, e.toString());
            return null;
        }
    }

}
