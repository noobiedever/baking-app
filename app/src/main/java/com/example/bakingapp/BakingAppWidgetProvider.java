package com.example.bakingapp;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

import com.example.bakingapp.models.Recipe;

public class BakingAppWidgetProvider extends AppWidgetProvider {

    private Recipe[] mRecipe;
    private int mPosition;

    public static final String POSITION_EXTRA = "id-extra";
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for(int i = 0; i < appWidgetIds.length; i++) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_item);

        }
        super.onUpdate(context, appWidgetManager, appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {

        super.onEnabled(context);
    }
}
