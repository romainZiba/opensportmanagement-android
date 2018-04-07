package com.zcorp.opensportmanagement.di.component

import android.arch.lifecycle.ViewModelProvider
import android.content.Context
import com.fasterxml.jackson.databind.ObjectMapper
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.db.EventDao
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import retrofit2.Retrofit

/**
 * Created by romainz on 02/02/18.
 */
interface AppComponent {
    fun inject(app: MyApplication)
    @ApplicationContext
    fun appContext(): Context
    fun getUserApi(): UserApi
    fun getEventApi(): EventApi
    fun getDataManager(): IDataManager
    fun getRetrofit(): Retrofit
    fun getMapper(): ObjectMapper
    fun getLogger(): ILogger
    fun getEventDao(): EventDao
    fun getViewModelFactory(): ViewModelProvider.Factory
    fun getSchedulerProvider(): SchedulerProvider
}
