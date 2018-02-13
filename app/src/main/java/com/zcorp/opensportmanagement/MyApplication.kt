package com.zcorp.opensportmanagement

import android.app.Application
import android.content.res.Configuration
import com.jakewharton.threetenabp.AndroidThreeTen
import com.zcorp.opensportmanagement.di.component.AppComponent
import com.zcorp.opensportmanagement.di.component.DaggerAppComponent
import com.zcorp.opensportmanagement.di.module.ApplicationModule
import java.util.*

/**
 * Created by romainz on 02/02/18.
 */
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        systemLanguage = Locale.getDefault()
        AndroidThreeTen.init(this)
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig != null) {
            systemLanguage = newConfig.locale
        }
    }

    companion object {
        lateinit var appComponent: AppComponent
        lateinit var systemLanguage: Locale

        // Application constants
        val PREF_NAME = "zcorp_pref"
    }
}