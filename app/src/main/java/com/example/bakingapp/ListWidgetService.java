package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.bakingapp.models.Recipe;
import com.example.bakingapp.utils.FileUtils;
import com.example.bakingapp.utils.JSONUtils;

public class ListWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewsFactory(this.getApplicationContext());
    }

    class ListRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
        Context mContext;
        Recipe[] mRecipes;

        public ListRemoteViewsFactory(Context context) {
            this.mContext = context;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
                @Override
                protected String doInBackground(Void... voids) {
                    String data = FileUtils.loadJSONFromFile(mContext);
                    return data;
                }

                @Override
                protected void onPostExecute(String s) {
                    mRecipes = JSONUtils.processFromJSON(s);
                }
            };

            task.execute();
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
             if(mRecipes == null) return  0;
             return mRecipes.length;
        }

        @Override
        public RemoteViews getViewAt(int position) {

            if(mRecipes == null || mRecipes.length == 0) return null;

            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(),
                    R.layout.widget_item);

            String text = mRecipes[position].getName();
            remoteViews.setTextViewText(R.id.tv_widget_recipe_name, text);

            Bundle extra = new Bundle();
            extra.putInt(BakingAppWidgetProvider.POSITION_EXTRA, position);

            Intent fillInIntent = new Intent();
            fillInIntent.putExtras(extra);

            remoteViews.setOnClickFillInIntent(R.id.tv_widget_recipe_name, fillInIntent);

            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 0;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
