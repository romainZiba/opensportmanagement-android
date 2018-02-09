package com.zcorp.opensportmanagement.ui.main

/**
 * Created by romainz on 06/02/18.
 */
interface MainPresenter {
    fun onDisplayEvents()
    fun onDisplayGoogle()
    fun onDisplayThirdFragment()
    fun onAttach(view: MainView)
    fun onDetach()
}