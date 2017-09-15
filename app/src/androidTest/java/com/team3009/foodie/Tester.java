package com.team3009.foodie;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.support.test.espresso.ViewAction;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Tester {

    private String mStringToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> MainActivity = new ActivityTestRule<>(
            MainActivity.class);

    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "thandomy@gmail.com";
    }

    @Test
    public void changeText_sameActivity() {
        // Type text and then press the button.
        onView(withId(R.id.txt_email))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.butn_login)).perform(click());

        // Check that the text was changed.
        //onView(withId(R.id.textToBeChanged))
               // .check(matches(withText(mStringToBetyped)));
    }


}