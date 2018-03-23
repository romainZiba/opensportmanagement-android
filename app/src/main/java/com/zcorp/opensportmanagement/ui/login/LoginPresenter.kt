package com.zcorp.opensportmanagement.ui.login

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.dto.LoginResponseDto
import com.zcorp.opensportmanagement.model.LoginRequest
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable
import javax.inject.Inject

class LoginPresenter @Inject constructor(
        private val dataManager: IDataManager,
        private val schedulerProvider: SchedulerProvider,
        private val disposables: CompositeDisposable,
        private val objectMapper: ObjectMapper) : ILoginPresenter {

    private var loginView: ILoginView? = null

    override fun validateCredentials(username: String, password: String) {
        loginView?.showProgress()
        val loginRequest = LoginRequest(username, password)
        disposables.add(dataManager.login(loginRequest)
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({ loginResponse ->
                    run {
                        if (loginResponse == null || loginResponse.headers().get("Authorization") == null) {
                            loginView?.hideProgress()
                            loginView?.setPasswordError()
                        } else {
                            if (loginResponse.body() != null) {
                                val content = loginResponse.body()!!.string()
                                val responseDto: LoginResponseDto = objectMapper.readValue(content)
                                dataManager.updateUserInfo(loginResponse.headers().get("Authorization")!!,
                                        IDataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                        responseDto.username,
                                        "",
                                        "")
                                loginView?.navigateToHome()
                            } else {
                                loginView?.hideProgress()
                                loginView?.setPasswordError()
                            }
                        }
                    }
                }, {
                    loginView?.hideProgress()
                    loginView?.setPasswordError()
                })
        )
    }

    override fun onAttach(view: ILoginView, vararg args: Serializable) {
        loginView = view
    }

    override fun onDetach() {
        disposables.clear()
        loginView = null
    }
}
