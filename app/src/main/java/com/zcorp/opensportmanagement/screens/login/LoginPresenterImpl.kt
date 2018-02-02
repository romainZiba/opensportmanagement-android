package com.zcorp.opensportmanagement.screens.login

class LoginPresenterImpl(private val loginView: LoginView,
                         private val loginModel: LoginModel) : LoginPresenter {

    override fun validateCredentials(username: String, password: String) {
        loginView.showProgress()
        var observable = loginModel.login(username, password)
        observable.subscribe{ aBoolean ->
            run {
                if (aBoolean) {
                    loginView.navigateToHome()
                } else {
                    loginView.setPasswordError()
                }
            }
        }
    }

    override fun finish() { }
}
