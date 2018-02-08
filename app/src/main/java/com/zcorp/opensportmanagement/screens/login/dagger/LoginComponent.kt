package com.zcorp.opensportmanagement.screens.login.dagger

import com.zcorp.opensportmanagement.application.builder.AppComponent
import com.zcorp.opensportmanagement.screens.login.LoginActivity
import com.zcorp.opensportmanagement.screens.login.LoginPresenter

import dagger.Component

/**
 * Created by romainz on 02/02/18.
 */
@LoginScope
@Component(modules = arrayOf(LoginContextModule::class, LoginModule::class), dependencies = arrayOf(AppComponent::class))
interface LoginComponent {
    fun inject(activity: LoginActivity)
}
