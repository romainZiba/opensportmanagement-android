package com.zcorp.opensportmanagement.application

import android.app.Application
import com.zcorp.opensportmanagement.application.builder.AppComponent
import com.zcorp.opensportmanagement.application.builder.AppContextModule
import com.zcorp.opensportmanagement.application.builder.DaggerAppComponent
import com.zcorp.opensportmanagement.application.builder.UserApiModule

/**
 * Created by romainz on 02/02/18.
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appContextModule(AppContextModule(this))
                .userApiModule(UserApiModule())
                .build()
    }

    companion object {
        lateinit var appComponent: AppComponent
    }
}