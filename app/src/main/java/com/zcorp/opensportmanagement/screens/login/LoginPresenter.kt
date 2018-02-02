package com.zcorp.opensportmanagement.screens.login

interface LoginPresenter {
    fun validateCredentials(username: String, password: String)

    fun finish()
}
