package com.team3009.foodie;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Intent;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.toPackage;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Tester {

    private String mStringToBetyped;
    private String mNumberToBetyped;

    @Rule
    public ActivityTestRule<MainActivity> MainActivity = new ActivityTestRule<>(
            MainActivity.class);


    @Rule
    public IntentsTestRule<HomeActivity> intentsTestRule =
            new IntentsTestRule<>(HomeActivity.class,
                    true,    // initialTouchMode
                    false); // launchActivity. False to set intent.


    @Before
    public void initValidString() {
        // Specify a valid string.
        mStringToBetyped = "thandomy@gmail.com";
        mNumberToBetyped = "12345678";
    }

    @Test
    public void inputText_sameActivity() {
        // Type text.
        onView(withId(R.id.txt_email))
                .perform(typeText(mStringToBetyped), closeSoftKeyboard());
        onView(withId(R.id.txt_pass))
                .perform(typeText(mNumberToBetyped), closeSoftKeyboard());


        onView(withId(R.id.txt_email)).check(matches(withText( mStringToBetyped)));
        onView(withId(R.id.txt_pass)).check(matches(withText( mNumberToBetyped)));

        onView(withId(R.id.butn_login)).perform(click());

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        intentsTestRule.launchActivity(intent);
    }
}