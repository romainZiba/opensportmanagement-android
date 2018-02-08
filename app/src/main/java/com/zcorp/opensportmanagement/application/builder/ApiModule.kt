package com.zcorp.opensportmanagement.application.builder

import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.api.EventApiImpl
import com.zcorp.opensportmanagement.api.UserApi
import com.zcorp.opensportmanagement.api.UserApiImpl
import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 02/02/18.
 */
@Module
class ApiModule {
    @ApplicationScope
    @Provides
    internal fun provideUserApi(): UserApi {
        return UserApiImpl()
    }

    @ApplicationScope
    @Provides
    internal fun provideEventApi(): EventApi {
        return EventApiImpl()
    }
}