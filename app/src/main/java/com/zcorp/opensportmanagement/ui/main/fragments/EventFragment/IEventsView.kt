package com.zcorp.opensportmanagement.ui.main.fragments.EventFragment

import com.zcorp.opensportmanagement.ui.base.IBaseView

/**
 * Created by romainz on 03/02/18.
 */
interface IEventsView : IBaseView {
    fun showNetworkError()
    fun onDataAvailable()
    fun showRowClicked(s: String)
    fun isFabButtonOpened(): Boolean
    fun closeFabButton()
}