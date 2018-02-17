package com.zcorp.opensportmanagement.ui.main.fragments.events

import com.zcorp.opensportmanagement.ui.base.IBasePresenter
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.IEventViewHolder

/**
 * Created by romainz on 03/02/18.
 */
interface IEventsPresenter : IBasePresenter<IEventsView> {
    fun onBindEventRowViewAtPosition(position: Int, holder: IEventViewHolder)
    fun getEventsFromModel()
    fun getEventsCount(): Int
    fun onItemClicked(adapterPosition: Int)
    fun onFloatingMenuClicked()
    fun onAddMatchClicked()
    fun onAddEventClicked()
    fun getEventType(position: Int): Int
}