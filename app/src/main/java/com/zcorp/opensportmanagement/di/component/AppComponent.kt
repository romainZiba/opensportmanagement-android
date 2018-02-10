package com.zcorp.opensportmanagement.di.component

import android.content.Context
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by romainz on 02/02/18.
 */
@Singleton
@Component(modules = arrayOf(ApplicationModule::class))
interface AppComponent {

    @ApplicationContext
    fun appContext(): Context
    fun getUserApi(): UserApi
    fun getEventApi(): EventApi
    fun getDataManager(): IDataManager
}
