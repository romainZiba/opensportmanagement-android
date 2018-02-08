package com.zcorp.opensportmanagement

import android.view.View
import com.zcorp.opensportmanagement.screens.main.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner


/**
 * Created by romainz on 07/02/18.
 */
@RunWith(RobolectricTestRunner::class)
class MainActivityTest {

    private lateinit var activity: MainActivity
    private lateinit var google: View

    @Before
    fun setup() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
        google = activity.findViewById(R.id.navigation_google)
    }


    @Test
    fun activityShouldContainEventFragment() {
        assertNotNull(activity)
        val eventFragment = activity.fragmentManager.findFragmentByTag(MainActivity.EVENTS)
        val otherFragment = activity.fragmentManager.findFragmentByTag(MainActivity.PLUS_ONE)
        assertNotNull(eventFragment)
        assertTrue(eventFragment.isAdded)
        assertNull(otherFragment)
    }

    @Test
    fun activityShouldChangeTheFragment() {
        google.performClick()
        val eventFragment = activity.fragmentManager.findFragmentByTag(MainActivity.EVENTS)
        val otherFragment = activity.fragmentManager.findFragmentByTag(MainActivity.PLUS_ONE)
        assertNotNull(eventFragment)
        assertTrue(eventFragment.isVisible.not())
        assertNotNull(otherFragment)
        assertTrue(otherFragment.isVisible)
    }
}