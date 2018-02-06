package com.zcorp.opensportmanagement.screens.main

/**
 * Created by romainz on 06/02/18.
 */
class MainPresenterImpl(val mainView: MainView): MainPresenter {
    override fun onDisplayEvents() {
        mainView.displayEvents()
    }

    override fun onDisplayGoogle() {
        mainView.displayGoogle()
    }

    override fun onDisplayThirdFragment() {
        mainView.displayThirdFragment()
    }

    override fun onViewReady() {
        mainView.displayEvents()
    }

}