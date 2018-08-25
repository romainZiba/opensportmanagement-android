package com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information

import com.zcorp.opensportmanagement.ui.base.IBasePresenter

/**
 * Created by romainz on 16/03/18.
 */
interface IEventInformationPresenter : IBasePresenter<IEventInformationView> {
    fun onPresentSelected()
    fun onAbsentSelected()
}