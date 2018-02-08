package com.zcorp.opensportmanagement

import android.view.View
import android.widget.TextView
import com.github.clans.fab.FloatingActionMenu
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventFragment
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.util.FragmentTestUtil.startFragment


/**
 * Created by romainz on 07/02/18.
 */
@RunWith(RobolectricTestRunner::class)
class EventFragmentTest {
    @Test
    fun shouldShowNetworkError() {

        val fragment = EventFragment.newInstance(1)
        startFragment(fragment)
        assertNotNull(fragment)
        val view = fragment.view
        assertNotNull(view)
        val eventsNetworkError = view.findViewById<TextView>(R.id.eventsNetworkError)
        assertNotNull(eventsNetworkError)
        assertTrue(eventsNetworkError.visibility == View.VISIBLE)
    }
}