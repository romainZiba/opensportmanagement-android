package com.zcorp.opensportmanagement

import android.view.View
import com.zcorp.opensportmanagement.ui.main.MainActivity
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.standalone.StandAloneContext
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Created by romainz on 07/02/18.
 * REFER to https://github.com/robolectric/robolectric/issues/3698
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = TestApplication::class)
class MainActivityTest {

    private lateinit var activity: MainActivity
    private lateinit var myProfile: View

    @Before
    fun setup() {
        activity = Robolectric.setupActivity(MainActivity::class.java)
        myProfile = activity.findViewById(R.id.navigation_account_details)
    }

    @After
    fun tearDown() {
        StandAloneContext.stopKoin()
    }

    @Test
    fun activityShouldContainEventFragment() {
        assertNotNull(activity)
        val eventFragment = activity.eventsFragment
        val otherFragment = activity.myProfileFragment
        assertNotNull(eventFragment)
        assertNotNull(otherFragment)
        assertTrue(eventFragment.isAdded)
        assertTrue(eventFragment.isVisible)
        assertTrue(otherFragment.isVisible.not())
    }

    @Test
    fun activityShouldChangeTheFragment() {
        assertNotNull(activity)
        myProfile.performClick()
        assertNotNull(activity.eventsFragment)
        assertTrue(activity.eventsFragment.isVisible.not())
        assertNotNull(activity.myProfileFragment)
        assertTrue(activity.myProfileFragment.isVisible)
    }
}