package com.example.bakingapp;

import android.content.Intent;

import com.example.bakingapp.models.Recipe;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

@RunWith(AndroidJUnit4.class)
public class StepDetailActivityScreenTest {
    private Recipe testRecipe = Recipe.testObject();
    private static final int POSITION = 0;

    @Rule
    public ActivityTestRule<StepDetailActivity> activityTestRule =
            new ActivityTestRule<StepDetailActivity>(StepDetailActivity.class) {
                @Override
                protected Intent getActivityIntent() {
                    Intent intent = new Intent();
                    intent.putExtra(RecipeDetailFragment.EXTRA_POSITION, POSITION);
                    intent.putExtra(RecipeDetailFragment.EXTRA, testRecipe.getSteps());

                    return intent;
                }
            };

    @Test
    public void clickNextButton_DisplayNextStep () {
        Espresso.onView(ViewMatchers.withId(R.id.b_next_step)).perform(ViewActions.click());

        String testInstruction = testRecipe.getSteps()[1].getDesctription();
        Espresso.onView(ViewMatchers.withId(R.id.tv_step_instruction))
                .check(ViewAssertions.matches(ViewMatchers.withText(testInstruction)));
        Espresso.onView(ViewMatchers.withId(R.id.b_previous_step))
                .check(ViewAssertions.matches(ViewMatchers.isCompletelyDisplayed()));
    }
}
