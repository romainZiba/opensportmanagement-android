package com.zcorp.opensportmanagement

import android.support.v7.widget.AppCompatTextView
import android.view.View
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.EventFragment
import com.zcorp.opensportmanagement.ui.main.fragments.EventFragment.IEventsPresenter
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
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

        val presenter: IEventsPresenter = mock()
        whenever(presenter.getEvents()).then {}
        whenever(presenter.getEventsCount()).doReturn(3)

        val fragment = EventFragment.newInstance(1)
        fragment.setPresenterForTest(presenter)
        startFragment(fragment)
        assertNotNull(fragment)

        val view = fragment.view
        val networkView = view.findViewById<AppCompatTextView>(R.id.eventsNetworkError)
        assertTrue(networkView.visibility == View.INVISIBLE)
        fragment.showNetworkError()
        assertTrue(networkView.visibility == View.VISIBLE)
    }
}