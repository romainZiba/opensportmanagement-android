package com.zcorp.opensportmanagement.ui.main.fragments.EventFragment

/**
 * Created by romainz on 03/02/18.
 */
interface IEventsView {
    fun showNetworkError()
    fun onDataAvailable()
    fun showRowClicked(s: String)
    fun isFabButtonOpened(): Boolean
    fun closeFabButton()
}