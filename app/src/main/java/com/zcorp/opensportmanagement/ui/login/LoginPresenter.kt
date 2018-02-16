package com.zcorp.opensportmanagement.ui.login

import com.zcorp.opensportmanagement.data.IDataManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(val dataManager: IDataManager) : ILoginPresenter {

    private var loginView: ILoginView? = null

    override fun validateCredentials(username: String, password: String) {
        loginView?.showProgress()
        var observable = dataManager.login(username, password)
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { loginResponse ->
                    run {
                        if (loginResponse == null) {
                            loginView?.setPasswordError()
                        } else {
                            dataManager.updateUserInfo(loginResponse.accessToken,
                                    loginResponse.userId,
                                    IDataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                    loginResponse.username,
                                    loginResponse.userEmail,
                                    loginResponse.userPictureUrl)
                            loginView?.navigateToHome()
                        }
                    }
                }
    }

    override fun onAttach(view: ILoginView) {
        loginView = view
    }

    override fun onDetach() {
        loginView = null
    }
}
