package com.example.bakingapp;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    @Rule
    ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_OpensRecipeDetailActivity() {
        Espresso.onData(Matchers.anything()).inAdapterView(ViewMatchers.withId(R.id.rv_recipe_list))
                .atPosition(0).perform(ViewActions.click());
    }
}
