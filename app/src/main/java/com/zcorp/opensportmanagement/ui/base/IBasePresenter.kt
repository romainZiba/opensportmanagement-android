package com.zcorp.opensportmanagement.ui.base

import java.io.Serializable

/**
 * Created by romainz on 10/02/18.
 */
interface IBasePresenter<in IBaseView> {
    fun onAttach(view: IBaseView, vararg args: Serializable)
    fun onDetach()
}