package com.zcorp.opensportmanagement.ui.login

interface LoginPresenter {
    fun validateCredentials(username: String, password: String)
    fun onAttach(view: LoginView)
    fun onDetach()
}
