package com.zcorp.opensportmanagement.ui.main

import com.zcorp.opensportmanagement.ui.base.IBasePresenter

/**
 * Created by romainz on 06/02/18.
 */
interface IMainPresenter: IBasePresenter<IMainView> {
    fun onDisplayEvents()
    fun onDisplayGoogle()
    fun onDisplayThirdFragment()
}