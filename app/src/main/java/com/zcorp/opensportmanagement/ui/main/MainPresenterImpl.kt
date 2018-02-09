package com.zcorp.opensportmanagement.ui.main

/**
 * Created by romainz on 06/02/18.
 */
class MainPresenterImpl: MainPresenter {

    private var mainView: MainView? = null

    override fun onDisplayEvents() {
        mainView?.displayEvents()
    }

    override fun onDisplayGoogle() {
        mainView?.displayGoogle()
    }

    override fun onDisplayThirdFragment() {
        mainView?.displayThirdFragment()
    }

    override fun onAttach(view: MainView) {
        mainView = view
        mainView?.displayEvents()
    }

    override fun onDetach() {
        mainView = null
    }
}