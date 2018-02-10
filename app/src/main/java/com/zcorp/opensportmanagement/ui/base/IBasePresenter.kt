package com.zcorp.opensportmanagement.ui.base

/**
 * Created by romainz on 10/02/18.
 */
interface IBasePresenter<in IBaseView> {
    fun onAttach(view: IBaseView)
    fun onDetach()
}