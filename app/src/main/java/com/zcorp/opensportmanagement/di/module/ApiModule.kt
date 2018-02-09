package com.zcorp.opensportmanagement.di.module

import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.api.EventApiImpl
import com.zcorp.opensportmanagement.api.UserApi
import com.zcorp.opensportmanagement.api.UserApiImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by romainz on 02/02/18.
 */
@Module
class ApiModule {
    @Singleton
    @Provides
    internal fun provideUserApi(): UserApi {
        return UserApiImpl()
    }

    @Singleton
    @Provides
    internal fun provideEventApi(): EventApi {
        return EventApiImpl()
    }
}