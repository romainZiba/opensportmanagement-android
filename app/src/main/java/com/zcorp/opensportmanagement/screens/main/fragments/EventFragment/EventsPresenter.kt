package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

/**
 * Created by romainz on 03/02/18.
 */
interface EventsPresenter {

    fun onBindEventRowViewAtPosition(position: Int, holder: EventViewRow)
    fun getEvents()
    fun getEventsCount(): Int
    fun finish()
}