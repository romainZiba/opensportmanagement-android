package com.zcorp.opensportmanagement.screens.login.dagger

import com.zcorp.opensportmanagement.screens.login.LoginActivity

import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 02/02/18.
 */
@Module
class LoginContextModule(private var loginContext: LoginActivity) {

    @LoginScope
    @Provides
    internal fun provideLoginActivity(): LoginActivity {
        return loginContext
    }
}
