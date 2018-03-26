package com.zcorp.opensportmanagement.ui.login

import com.fasterxml.jackson.databind.ObjectMapper
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.LoginRequest
import com.zcorp.opensportmanagement.model.User
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable
import javax.inject.Inject

class LoginPresenter @Inject constructor(
        private val dataManager: IDataManager,
        private val schedulerProvider: SchedulerProvider,
        private val disposables: CompositeDisposable,
        private val objectMapper: ObjectMapper,
        private val mLogger: ILogger) : ILoginPresenter {

    companion object {
        private val TAG = LoginPresenter.javaClass.simpleName
    }

    private var loginView: ILoginView? = null

    override fun validateCredentials(username: String, password: String) {
        loginView?.showProgress()
        val loginRequest = LoginRequest(username, password)

        val disposable = dataManager.login(loginRequest)
                .flatMap { dataManager.whoAmI() }
                .flatMap { user: User ->
                    dataManager.updateUserInfo(
                            IDataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                            user.username,
                            user.email,
                            "",
                            mutableListOf())
                    dataManager.getTeams()
                }
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({ teams ->
                    loginView?.hideProgress()
                    loginView?.navigateToHome(teams.map { it.name })
                }, {
                    mLogger.d(TAG, "Error occured $it")
                    loginView?.hideProgress()
                    loginView?.setPasswordError()
                })
        disposables.add(disposable)
    }

    override fun onAttach(view: ILoginView, vararg args: Serializable) {
        loginView = view
    }

    override fun onDetach() {
        disposables.clear()
        loginView = null
    }
}
