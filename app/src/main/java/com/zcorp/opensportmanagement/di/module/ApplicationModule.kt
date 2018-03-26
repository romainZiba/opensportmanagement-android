package com.zcorp.opensportmanagement.di.module

import android.content.Context
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.log.Logger
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ApplicationModule(private val context: Context) {

    @Provides
    @ApplicationContext
    internal fun provideAppContext(): Context {
        return context
    }

    @Provides
    @Singleton
    internal fun provideEventApi(dataManager: IDataManager): EventApi {
        return dataManager
    }

    @Provides
    @Singleton
    internal fun provideUserApi(dataManager: IDataManager): UserApi {
        return dataManager
    }

    @Provides
    @Singleton
    internal fun provideLogger(): ILogger {
        return Logger()
    }
}
