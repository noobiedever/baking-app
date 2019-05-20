package com.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.data.AppDatabase;
import com.example.bakingapp.models.Ingredient;
import com.example.bakingapp.models.Recipe;

public class BakingAppWidgetService extends RemoteViewsService {
    private static final String TAG = BakingAppWidgetService.class.getSimpleName();

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(getApplicationContext(), intent);
    }

    class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Recipe[] mRecipes;
        private Context mContext;
        private int mAppWidgetId;

        public StackRemoteViewsFactory(Context context, Intent intent) {
            this.mContext = context;
            this.mAppWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            mRecipes = AppDatabase.getInstance(mContext).movieDao().loadRecipes();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if(mRecipes == null) return 0;

            return mRecipes.length;
        }

        @Override
        public RemoteViews getViewAt(int position) {
            Log.v(TAG, "Adapter contains " + getCount() + " items");
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

            remoteViews.setTextViewText(R.id.tv_widget_recipe_name, mRecipes[position].getName());

            StringBuilder stringBuilder = new StringBuilder();

            for(Ingredient ingredient: mRecipes[position].getIngredients()) {
                stringBuilder.append("\n" + ingredient.toString());
            }

            remoteViews.setTextViewText(R.id.tv_widget_ingredients, stringBuilder.toString());

            Intent fillInIntent = new Intent();
            fillInIntent.putExtra(RecipeCardFragment.EXTRA, mRecipes[position]);

            remoteViews.setOnClickFillInIntent(R.id.ll_widget_item_container, fillInIntent);


            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }


    }
}
