package com.zcorp.opensportmanagement

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.zcorp.opensportmanagement.model.OtherEvent
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventsModel
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventsPresenterImpl
import com.zcorp.opensportmanagement.screens.main.fragments.EventFragment.EventsView
import io.reactivex.Observable
import org.junit.Test
import java.util.*

/**
 * Created by romainz on 09/02/18.
 */
class EventsPresenterTest {
    @Test
    fun shouldShowNetworkError() {
        val view: EventsView = mock()
        val model: EventsModel = mock()
        val presenterTest = EventsPresenterImpl(view, model)
        whenever(model.provideEvents()).thenReturn(Observable.just(listOf(OtherEvent("", "", Date(), Date(), ""))))
        presenterTest.getEvents()
        verify(view, times(1)).onDataAvailable()
    }
}