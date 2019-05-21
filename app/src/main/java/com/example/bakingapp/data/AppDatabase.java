package com.example.bakingapp.data;

import android.content.Context;
import android.util.Log;

import com.example.bakingapp.models.Recipe;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Recipe.class}, version = 1, exportSchema = false)
@TypeConverters(RecipeTypeConverters.class)
public abstract class AppDatabase extends RoomDatabase {

    private static final String TAG = AppDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "recipes";
    private static final Object LOCK = new Object();
    private static AppDatabase appDatabaseInstance;

    public static AppDatabase getInstance(Context context) {

        if(appDatabaseInstance == null) {
            synchronized (LOCK) {
                Log.v(TAG, "Creating Database...");
                appDatabaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME).build();
            }
        }

        return appDatabaseInstance;
    }

    public abstract RecipeDao recipeDao();
}
