package com.example.bakingapp;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityScreenTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> recipeDetailActivityActivityTestRule =
            new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void clickRecyclerViewItem_ShowStepDetails() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_recipe_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

    }
}
