package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.support.v7.widget.RecyclerView
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.ui.base.IBasePresenter

/**
 * Created by romainz on 03/02/18.
 */
interface IEventsPresenter : IBasePresenter<IEventsView> {

    fun onBindEventRowViewAtPosition(position: Int, holder: RecyclerView.ViewHolder)
    fun getEventsFromModel()
    fun getEventsCount(): Int
    fun onItemClicked(adapterPosition: Int)
    fun onFloatingMenuClicked()
    fun onAddMatchClicked()
    fun onAddEventClicked()
    fun getEventType(position: Int): Int
}