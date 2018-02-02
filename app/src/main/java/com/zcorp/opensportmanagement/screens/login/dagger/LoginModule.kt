package com.zcorp.opensportmanagement.screens.login.dagger

import com.zcorp.opensportmanagement.api.UserApi
import com.zcorp.opensportmanagement.screens.login.*
import dagger.Module
import dagger.Provides

/**
 * Created by romainz on 02/02/18.
 */
@Module
class LoginModule {

    @LoginScope
    @Provides
    internal fun provideLoginPresenter(view: LoginView, model: LoginModel): LoginPresenter {
        return LoginPresenterImpl(view, model)
    }

    @LoginScope
    @Provides
    internal fun provideLoginView(loginActivity: LoginActivity): LoginView {
        return loginActivity
    }

    @LoginScope
    @Provides
    internal fun provideLoginModel(api: UserApi): LoginModel {
        return LoginModelImpl(api)
    }

}

