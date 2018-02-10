package com.zcorp.opensportmanagement.ui.login

interface ILoginPresenter {
    fun validateCredentials(username: String, password: String)
    fun onAttach(view: ILoginView)
    fun onDetach()
}
