package com.zcorp.opensportmanagement.application.builder

import android.content.Context
import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.api.UserApi

import dagger.Component

/**
 * Created by romainz on 02/02/18.
 */
@ApplicationScope
@Component(modules = arrayOf(AppContextModule::class, ApiModule::class))
interface AppComponent {
    fun userApi(): UserApi
    fun eventApi(): EventApi
    fun appContext(): Context
}
