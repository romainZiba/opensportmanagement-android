package com.zcorp.opensportmanagement.screens.login

import com.zcorp.opensportmanagement.application.MyApplication
import com.zcorp.opensportmanagement.screens.login.dagger.DaggerLoginComponent
import com.zcorp.opensportmanagement.screens.login.dagger.LoginContextModule
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenterImpl(private val loginView: LoginView,
                         private val loginModel: LoginModel) : LoginPresenter {

    override fun validateCredentials(username: String, password: String) {
        loginView.showProgress()
        var observable = loginModel.login(username, password)
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { aBoolean ->
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
