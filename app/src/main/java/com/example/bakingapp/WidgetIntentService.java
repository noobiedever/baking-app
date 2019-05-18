package com.example.bakingapp;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utils.FileUtils;
import com.example.bakingapp.utils.JSONUtils;

import androidx.annotation.Nullable;

public class WidgetIntentService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WidgetIntentService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Context context = getApplicationContext();
        String data = FileUtils.loadJSONFromFile(context);
        Recipe[] recipes = JSONUtils.processFromJSON(data);

    }
}
