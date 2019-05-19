package com.example.bakingapp;

import android.content.Intent;

import com.example.bakingapp.models.Recipe;

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
public class RecipeDetailActivityScreenTest {
    private static final String STEP_INSTRUCTION = "Recipe Introduction";
    private Recipe testRecipe = Recipe.testObject();

    @Rule
    public ActivityTestRule<RecipeDetailActivity> recipeDetailActivityActivityTestRule =
            new ActivityTestRule<RecipeDetailActivity>(RecipeDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra(RecipeCardFragment.EXTRA, testRecipe);
                    return intent;
                }
            };

    @Test
    public void clickRecyclerViewItem_ShowStepDetails() {
        Espresso.onView(ViewMatchers.withId(R.id.rv_recipe_steps))
                .perform(RecyclerViewActions.actionOnItemAtPosition(0, ViewActions.click()));

        Espresso.onView(ViewMatchers.withId(R.id.tv_step_instruction))
                .check(ViewAssertions.matches(ViewMatchers.withText(STEP_INSTRUCTION)));
    }
}
