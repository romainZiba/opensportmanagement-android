package com.zcorp.opensportmanagement.di.component


import com.zcorp.opensportmanagement.di.PerActivity
import com.zcorp.opensportmanagement.di.module.ActivityModule
import com.zcorp.opensportmanagement.ui.login.LoginActivity
import com.zcorp.opensportmanagement.ui.main.MainActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(AppComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: LoginActivity)
}
