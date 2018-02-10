package com.zcorp.opensportmanagement.ui.login

interface ILoginView {
    fun showProgress()

    fun hideProgress()

    fun setUsernameError()

    fun setPasswordError()

    fun navigateToHome()
}
