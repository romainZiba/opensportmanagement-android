package com.zcorp.opensportmanagement.ui.login

import com.zcorp.opensportmanagement.data.api.UserApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenterImpl @Inject constructor(val api: UserApi) : LoginPresenter {

    private var loginView: LoginView? = null

    override fun validateCredentials(username: String, password: String) {
        loginView?.showProgress()
        var observable = api.login(username, password)
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { token ->
                    run {
                        if (token == null) {
                            loginView?.setPasswordError()
                        } else {
                            loginView?.navigateToHome()
                        }
                    }
                }
    }

    override fun onAttach(view: LoginView) {
        loginView = view
    }

    override fun onDetach() {
        loginView = null
    }
}
