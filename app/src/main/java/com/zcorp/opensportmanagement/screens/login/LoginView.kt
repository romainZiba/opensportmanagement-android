package com.zcorp.opensportmanagement.screens.login

interface LoginView {
    fun showProgress()

    fun hideProgress()

    fun setUsernameError()

    fun setPasswordError()

    fun navigateToHome()
}
