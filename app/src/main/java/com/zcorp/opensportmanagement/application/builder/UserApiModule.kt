package com.zcorp.opensportmanagement.application.builder

import com.zcorp.opensportmanagement.api.UserApi
import com.zcorp.opensportmanagement.api.UserApiImpl
import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 02/02/18.
 */
@Module
class UserApiModule {
    @ApplicationScope
    @Provides
    internal fun provideApiService(): UserApi {
        return UserApiImpl()
    }
}