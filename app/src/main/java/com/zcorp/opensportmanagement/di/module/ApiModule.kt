package com.zcorp.opensportmanagement.di.module

import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.FakeEventApiImpl
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.api.FakeUserApiImpl
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
        return FakeUserApiImpl()
    }

    @Singleton
    @Provides
    internal fun provideEventApi(): EventApi {
        return FakeEventApiImpl()
    }
}