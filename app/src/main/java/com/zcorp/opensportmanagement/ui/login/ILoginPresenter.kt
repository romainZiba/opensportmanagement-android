package com.zcorp.opensportmanagement.ui.login

import com.zcorp.opensportmanagement.ui.base.IBasePresenter

interface ILoginPresenter: IBasePresenter<ILoginView> {
    fun validateCredentials(username: String, password: String)
}
