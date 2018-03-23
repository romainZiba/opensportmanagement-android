package com.zcorp.opensportmanagement.ui.main.fragments.events

import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.ui.base.IBasePresenter

/**
 * Created by romainz on 03/02/18.
 */
interface IEventsPresenter : IBasePresenter<IEventsView> {
    fun getEvents()
    fun onEventSelected(event: Event, eventPosition: Int)
    fun onFloatingMenuClicked()
    fun onAddMatchClicked()
    fun onAddEventClicked()
}