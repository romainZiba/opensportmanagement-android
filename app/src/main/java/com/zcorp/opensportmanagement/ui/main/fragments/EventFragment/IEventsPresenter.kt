package com.zcorp.opensportmanagement.ui.main.fragments.EventFragment

import com.zcorp.opensportmanagement.ui.base.IBasePresenter

/**
 * Created by romainz on 03/02/18.
 */
interface IEventsPresenter : IBasePresenter<IEventsView> {

    fun onBindEventRowViewAtPosition(position: Int, holder: IEventViewRow)
    fun getEvents()
    fun getEventsCount(): Int
    fun onItemClicked(adapterPosition: Int)
    fun onFloatingMenuClicked()
    fun onAddMatchClicked()
    fun onAddEventClicked()
}