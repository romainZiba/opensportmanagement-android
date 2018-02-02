package com.zcorp.opensportmanagement.screens.login

class LoginPresenterImpl(view: LoginView?,
                         loginModel: LoginModelImpl) : LoginPresenter {

    private val loginModel: LoginModel = loginModel
    private var loginView: LoginView?

    init {
        loginView = view
    }

    override fun validateCredentials(username: String, password: String) {
        if (loginView != null) {
            loginView!!.showProgress()
        }
        var observable = loginModel.login(username, password)
        observable.subscribe{ aBoolean ->
            run {
                if (aBoolean) {
                    loginView!!.navigateToHome()
                } else {
                    loginView!!.setPasswordError()
                }
            }
        }
    }

    override fun finish() {
        loginView = null
    }
}
