package com.zcorp.opensportmanagement

import android.view.View
import com.zcorp.opensportmanagement.ui.main.MainActivity
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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
    private lateinit var myProfile: View

    @Before
    fun setup() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
        myProfile = activity.findViewById(R.id.navigation_account_details)
    }

    @Test
    fun activityShouldContainEventFragment() {
        assertNotNull(activity)
        val eventFragment = activity.supportFragmentManager.findFragmentByTag(MainActivity.EVENTS_TAG)
        val otherFragment = activity.supportFragmentManager.findFragmentByTag(MainActivity.PROFILE_TAG)
        assertNotNull(eventFragment)
        assertTrue(eventFragment?.isAdded ?: false)
        assertNull(otherFragment)
    }

    @Test
    fun activityShouldChangeTheFragment() {
        myProfile.performClick()
        val eventFragment = activity.fragmentManager.findFragmentByTag(MainActivity.EVENTS_TAG)
        val otherFragment = activity.fragmentManager.findFragmentByTag(MainActivity.PROFILE_TAG)
        assertNotNull(eventFragment)
        assertTrue(eventFragment.isVisible.not())
        assertNotNull(otherFragment)
        assertTrue(otherFragment.isVisible)
    }
}