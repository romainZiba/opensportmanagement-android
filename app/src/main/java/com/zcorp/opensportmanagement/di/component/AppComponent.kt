package com.zcorp.opensportmanagement.di.component

import android.content.Context
import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.api.UserApi
import com.zcorp.opensportmanagement.di.module.ApiModule
import com.zcorp.opensportmanagement.di.module.AppContextModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by romainz on 02/02/18.
 */
@Singleton
@Component(modules = arrayOf(AppContextModule::class, ApiModule::class))
interface AppComponent {
    fun appContext(): Context
    fun getUserApi(): UserApi
    fun getEventApi(): EventApi
}
