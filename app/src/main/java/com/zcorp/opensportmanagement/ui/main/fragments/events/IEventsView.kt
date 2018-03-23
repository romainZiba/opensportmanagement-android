package com.zcorp.opensportmanagement.ui.main.fragments.events

import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.ui.base.IBaseView

/**
 * Created by romainz on 03/02/18.
 */
interface IEventsView : IBaseView {
    fun showNetworkError()
    fun onDataAvailable(events: List<Event>)
    fun showEventDetails(event: Event, adapterPosition: Int)
    fun isFloatingMenuOpened(): Boolean
    fun closeFloatingMenu()
    fun openFloatingMenu()
    fun setBackgroundAlpha(alpha: Float)
    fun setBackgroundColor(colorResourceId: Int)
    fun setBackground(drawableId: Int)
    fun showProgress()
}