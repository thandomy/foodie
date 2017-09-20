package com.team3009.foodie;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class homeTester {
    @Rule
    public ActivityTestRule<HomeActivity> home = new ActivityTestRule<>(
            HomeActivity.class);

    @Test
    public void homeActivity() {
        onView(withId(R.id.fab)).perform(click());
        onView(withId(R.id.list)).perform(click());
    }
}
