package com.team3009.foodie;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import android.app.Activity;
import android.app.Instrumentation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.DrawerActions;
import android.support.test.espresso.contrib.NavigationViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.DrawerMatchers.isOpen;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;


import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class Tester {

    private String mStringToBetyped;
    private String mNumberToBetyped;
    Uri imageUri;


    @Rule
    public ActivityTestRule<MainActivity> mMainActivity = new ActivityTestRule<>(
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

        Resources resources = InstrumentationRegistry.getTargetContext().getResources();
        imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(R.mipmap.ic_launcher) + '/' +
                resources.getResourceTypeName(R.mipmap.ic_launcher) + '/' +
                resources.getResourceEntryName(R.mipmap.ic_launcher));



    }
    @Test
    public void HomeActivityTest() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    
        onView(withId(R.id.txt_email)).perform(typeText(mStringToBetyped)).perform(closeSoftKeyboard());
        onView(withId(R.id.txt_pass)).perform(typeText(mNumberToBetyped)).perform(closeSoftKeyboard());
        onView(withId(R.id.butn_signup)).perform(click());
        onView(withId(R.id.butn_login)).perform(click());

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intentsTestRule.launchActivity(intent);


        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        //Here's the difference
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_serve)) ;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.mealtitle)).perform(typeText(mStringToBetyped)).perform(closeSoftKeyboard());
        onView(withId(R.id.mealDescription)).perform(typeText(mStringToBetyped)).perform(closeSoftKeyboard());
        onView(withId(R.id.Ingredients)).perform(typeText(mStringToBetyped)).perform(closeSoftKeyboard());
        onView(withId(R.id.price)).perform(typeText(mNumberToBetyped)).perform(closeSoftKeyboard());

        Intent intenty = new Intent();
        intenty.setData(imageUri);
        intenty.setType("image/*");
        intenty.setAction(Intent.ACTION_PICK);
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, intenty));
        onView(withId(R.id.getPic)).perform(click());
        onView(withId(R.id.subImage)).perform(click());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        pressBack();

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open());
        onView(withId(R.id.drawer_layout)).check(matches(isOpen()));
        //Here's the difference
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.nav_list_view)) ;

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pressBack();
    }
}
