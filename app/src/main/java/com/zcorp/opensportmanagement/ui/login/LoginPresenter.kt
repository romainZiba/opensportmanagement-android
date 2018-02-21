package com.zcorp.opensportmanagement.ui.login

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.dto.LoginResponseDto
import com.zcorp.opensportmanagement.model.LoginRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class LoginPresenter @Inject constructor(val dataManager: IDataManager, val objectMapper: ObjectMapper) : ILoginPresenter {

    private lateinit var loginView: ILoginView

    override fun validateCredentials(username: String, password: String) {
        loginView.showProgress()
        val loginRequest = LoginRequest(username, password)
        val observable = dataManager.login(loginRequest)
        observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginResponse ->
                    run {
                        if (loginResponse == null || loginResponse.headers().get("Authorization") == null) {
                            loginView.hideProgress()
                            loginView.setPasswordError()
                        } else {
                            if (loginResponse.body() != null) {
                                val content = loginResponse.body()!!.string()
                                val responseDto: LoginResponseDto = objectMapper.readValue(content)
                                dataManager.updateUserInfo(loginResponse.headers().get("Authorization")!!,
                                        IDataManager.LoggedInMode.LOGGED_IN_MODE_SERVER,
                                        responseDto.username,
                                        "",
                                        "")
                                loginView.navigateToHome()
                            } else {
                                loginView.hideProgress()
                                loginView.setPasswordError()
                            }
                        }
                    }
                }, {
                    loginView.hideProgress()
                    loginView.setPasswordError()
                })
    }

    override fun onAttach(view: ILoginView) {
        loginView = view
    }

    override fun onDetach() {
    }
}
