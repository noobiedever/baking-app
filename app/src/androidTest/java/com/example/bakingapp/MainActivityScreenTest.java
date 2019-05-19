package com.example.bakingapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {
    private static String RECIPE_NAME = "Nutella Pie";

    @Rule
    public ActivityTestRule<MainActivity> mainActivityActivityTestRule =
            new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_OpensRecipeDetailActivity() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_recipe_list))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Espresso.onView(ViewMatchers.withId(R.id.tv_recipe_name))
                .check(ViewAssertions.matches(ViewMatchers.withText(RECIPE_NAME)));
    }
}
