package com.example.bakingapp.utils;

import android.content.Context;
import android.util.Log;
import com.example.bakingapp.R;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class FileUtils {
    // tag for logging purposes
    private static final String TAG = FileUtils.class.getSimpleName();

    public static String loadJSONFromFile(Context context) {
        // open file (recipes.json in res/raw) and read it's contents
        InputStream inputStream = context.getResources().openRawResource(R.raw.recipes);
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(reader);

        // create String to hold the contents of each line read from the file and append each line
        // to the StringBuilder
        StringBuilder stringBuilder = new StringBuilder();
        String inputLine;

        try {
            while((inputLine = bufferedReader.readLine()) != null) {
                stringBuilder.append(inputLine);
            }

            // close stream
            bufferedReader.close();

            Log.v(TAG, context.getString(R.string.json_read_successful));

            // convert StringBuilder to String and return it
            return stringBuilder.toString();
        } catch (IOException exception) {
            Log.v(TAG, exception.toString());
            return null;
        }
    }
}
