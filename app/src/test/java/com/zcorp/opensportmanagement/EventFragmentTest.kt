package com.zcorp.opensportmanagement

import android.view.View
import android.widget.TextView
import com.nhaarman.mockito_kotlin.*
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventFragment
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventsPresenter
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

        val presenter: EventsPresenter = mock()
        whenever(presenter.getEvents()).then{}
        whenever(presenter.getEventsCount()).doReturn(3)

        val fragment = EventFragment.newInstance(1)
        fragment.setPresenterForTest(presenter)
        startFragment(fragment)
        assertNotNull(fragment)

        val view = fragment.view
        val networkView = view.findViewById<TextView>(R.id.eventsNetworkError)
        assertTrue(networkView.visibility == View.INVISIBLE)
        fragment.showNetworkError()
        assertTrue(networkView.visibility == View.VISIBLE)
    }
}