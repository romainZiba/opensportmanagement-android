package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import java.io.Serializable

/**
 * Created by romainz on 03/02/18.
 */
interface EventsPresenter : Serializable {

    fun onBindEventRowViewAtPosition(position: Int, holder: EventViewRow)
    fun getEvents()
    fun getEventsCount(): Int
    fun finish()
    fun onItemClicked(s: String)
}