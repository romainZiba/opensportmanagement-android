package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

/**
 * Created by romainz on 03/02/18.
 */
interface EventsView {
    fun showProgress()
    fun hideProgress()
    fun showNetworkError()
    fun onDataChanged()
}