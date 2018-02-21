package com.zcorp.opensportmanagement.di.component

import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.di.module.ApplicationModule
import com.zcorp.opensportmanagement.di.module.NetModule
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Created by romainz on 02/02/18.
 */
@Singleton
@Component(modules = [(ApplicationModule::class), (NetModule::class)])
interface AppComponent {

    @ApplicationContext
    fun appContext(): Context
    fun getUserApi(): UserApi
    fun getEventApi(): EventApi
    fun getDataManager(): IDataManager
    fun getRetrofit(): Retrofit
    fun getMapper(): ObjectMapper
}
