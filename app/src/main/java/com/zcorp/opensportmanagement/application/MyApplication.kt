package com.zcorp.opensportmanagement.application

import android.app.Application
import android.content.res.Configuration
import com.zcorp.opensportmanagement.application.builder.AppComponent
import com.zcorp.opensportmanagement.application.builder.AppContextModule
import com.zcorp.opensportmanagement.application.builder.DaggerAppComponent
import java.util.*

/**
 * Created by romainz on 02/02/18.
 */
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .appContextModule(AppContextModule(this))
                .build()
        systemLanguage = Locale.getDefault().language
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig != null) {
            systemLanguage = newConfig.locale.language
        }
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var systemLanguage: String
    }
}