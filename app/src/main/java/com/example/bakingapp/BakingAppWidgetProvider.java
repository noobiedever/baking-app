package com.example.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

public class BakingAppWidgetProvider extends AppWidgetProvider {
    private static final String TAG = BakingAppWidgetProvider.class.getSimpleName();


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.v(TAG, context.getString(R.string.bawp_log));

        for(int i = 0; i < appWidgetIds.length; i++) {
            // Intent to start BakingAppWidgetService
            Intent intent = new Intent(context, BakingAppWidgetService.class);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));

            // RemoteViews object for views associated with AdapterView that holds a collection of data
            RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget);
            // set RemoteAdapter for AdapterView
            remoteViews.setRemoteAdapter(R.id.lv_widget_recipes, intent);
            // set view to be shown when the AdapterView's Adapter has no data
            remoteViews.setEmptyView(R.id.lv_widget_recipes, R.id.tv_empty_view);

            // Intent to open RecipeDetailActivity
            Intent intentToStartActivity = new Intent(context, RecipeDetailActivity.class);
            PendingIntent pendingIntent = PendingIntent
                    .getActivity(context, 0, intentToStartActivity,0);
            // set PendingIntent template for AdapterView
            remoteViews.setPendingIntentTemplate(R.id.lv_widget_recipes, pendingIntent);

            // update widget for current iteration
            appWidgetManager.updateAppWidget(appWidgetIds[i], remoteViews);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
