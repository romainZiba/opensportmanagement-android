package com.zcorp.opensportmanagement

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isChecked
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zcorp.opensportmanagement.ui.main.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by romainz on 09/02/18.
 */
@RunWith(AndroidJUnit4::class)
class ClickFloatingButtonTest {

    private var mStringToBetyped: String? = null

    @Rule
    var mActivityRule = ActivityTestRule<MainActivity>(
            MainActivity::class.java)

    @Before
    fun initValidString() {
        // Specify a valid string.
        mStringToBetyped = "Espresso"
    }

    @Test
    fun clickButton() {
        // Type text and then press the button.
        onView(withId(R.id.menu_events))
                .perform(click())
                .check(matches(isChecked()))
        // Type text and then press the button.
        onView(withId(R.id.fabAddEvent))
                .perform(click())
        // Type text and then press the button.
        onView(withId(R.id.fabAddEvent))
                .perform(click())
        // Type text and then press the button.
        onView(withId(R.id.fabAddEvent))
                .perform(click())
    }
}