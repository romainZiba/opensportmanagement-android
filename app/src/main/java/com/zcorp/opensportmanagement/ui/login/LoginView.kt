package com.zcorp.opensportmanagement.ui.login

interface LoginView {
    fun showProgress()

    fun hideProgress()

    fun setUsernameError()

    fun setPasswordError()

    fun navigateToHome()
}
