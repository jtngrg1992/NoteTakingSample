package com.example.jatingarg.notes;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by jatingarg on 04/04/17.
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    private static final String TAG = "MainActivityTest";

    public static RecyclerViewMatcher withRecyclerView(final int recyclerViewId) {
        return new RecyclerViewMatcher(recyclerViewId);
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class);

    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.jatingarg.notes", appContext.getPackageName());
    }

    @Test
    public void testAddBtnExistsAndClickable() {
        onView(withId(R.id.addNote)).check(matches(isDisplayed()));
        onView(withId(R.id.addNote)).perform(click());
    }

    @Test
    public void testRecyclerViewExists() {
        onView(withId(R.id.mainRecycler)).check(matches(isDisplayed()));
    }

    @Test
    public void testNoteCreationAndEdition() {
        RecyclerView mRecycler = (RecyclerView) mActivityTestRule.getActivity().findViewById(R.id.mainRecycler);
        assertNotNull(mRecycler);

        onView(withId(R.id.addNote)).perform(click());
        onView(withId(R.id.noteDialogTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.noteContent)).perform(typeText("Hello Test Note"));
        onView(withText("Save")).perform(click());
        onView(withRecyclerView(R.id.mainRecycler).
                atPositionOnView(mRecycler.getChildCount() - 1, R.id.noteText))
                .check(matches(withText("Hello Test Note")));

        onView(withId(R.id.mainRecycler)).perform(RecyclerViewActions.actionOnItemAtPosition(mRecycler.getChildCount() - 1, click()));
        onView(withId(R.id.noteDialogTitle)).check(matches(isDisplayed()));
        onView(withId(R.id.noteContent)).perform(clearText());
        onView(withId(R.id.noteContent)).perform(typeText("Hello Again, Test Note"));
        onView(withText("Save")).perform(click());

        onView(withRecyclerView(R.id.mainRecycler)
                .atPositionOnView(mRecycler.getChildCount() - 1, R.id.noteText))
                .check(matches(withText("Hello Again, Test Note")));
    }

}
