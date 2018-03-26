package com.zcorp.opensportmanagement.ui.login

import com.zcorp.opensportmanagement.ui.base.IBaseView

interface ILoginView : IBaseView {
    fun showProgress()

    fun hideProgress()

    fun setUsernameError()

    fun setPasswordError()

    fun navigateToHome(availableTeams: List<String>)
}
