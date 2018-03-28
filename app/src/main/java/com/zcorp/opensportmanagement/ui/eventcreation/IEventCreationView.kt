package com.zcorp.opensportmanagement.ui.eventcreation

import com.zcorp.opensportmanagement.ui.base.IBaseView

interface IEventCreationView : IBaseView {
    fun onPunctualEventRequested()
    fun onRecurentEventRequest()
}