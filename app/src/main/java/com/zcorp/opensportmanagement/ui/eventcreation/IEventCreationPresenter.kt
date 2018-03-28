package com.zcorp.opensportmanagement.ui.eventcreation

import com.zcorp.opensportmanagement.ui.base.IBasePresenter

interface IEventCreationPresenter : IBasePresenter<IEventCreationView> {
    fun onPunctualSelected()
    fun onRecurrentSelected()
    fun initView()
}