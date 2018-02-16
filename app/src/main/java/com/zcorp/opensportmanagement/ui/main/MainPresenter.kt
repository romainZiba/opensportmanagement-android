package com.zcorp.opensportmanagement.ui.main

/**
 * Created by romainz on 06/02/18.
 */
class MainPresenter : IMainPresenter {

    private var mainView: IMainView? = null

    override fun onDisplayEvents() {
        mainView?.displayEvents()
    }

    override fun onDisplayGoogle() {
        mainView?.displayGoogle()
    }

    override fun onDisplayThirdFragment() {
        mainView?.displayMessages()
    }

    override fun onAttach(view: IMainView) {
        mainView = view
        mainView?.displayEvents()
    }

    override fun onDetach() {
        mainView = null
    }
}