package com.zcorp.opensportmanagement.ui.eventcreation

import java.io.Serializable

class EventCreationPresenter: IEventCreationPresenter {

    private var mView: IEventCreationView? = null

    override fun initView() {
        mView?.onPunctualEventRequested()
    }

    override fun onPunctualSelected() {
        mView?.closeSoftKeyboard()
        mView?.onPunctualEventRequested()
    }

    override fun onRecurrentSelected() {
        mView?.closeSoftKeyboard()
        mView?.onRecurentEventRequest()
    }

    override fun onAttach(view: IEventCreationView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}