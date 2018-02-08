package com.zcorp.opensportmanagement

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
    fun clickFabInFragmentShouldOpenTheFabMenu() {
        val fragment = EventFragment.newInstance(1)
        startFragment(fragment)
        assertNotNull(fragment)
        val view = fragment.view
        assertNotNull(view)
        val fabMenu = view.findViewById<FloatingActionMenu>(R.id.menu)
        assertNotNull(fabMenu)
        assertFalse(fabMenu.isOpened)
        fabMenu.performClick()
        assertTrue(fabMenu.isOpened)
    }
}