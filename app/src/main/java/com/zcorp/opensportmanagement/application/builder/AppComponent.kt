package com.zcorp.opensportmanagement.application.builder

import android.content.Context
import com.zcorp.opensportmanagement.api.UserApi

import dagger.Component

/**
 * Created by romainz on 02/02/18.
 */
@ApplicationScope
@Component(modules = arrayOf(AppContextModule::class, UserApiModule::class))
interface AppComponent {
    fun userApi(): UserApi
    fun appContext(): Context
}
