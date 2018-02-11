package com.zcorp.opensportmanagement

import android.app.Application
import android.content.res.Configuration
import com.zcorp.opensportmanagement.di.component.AppComponent
import com.zcorp.opensportmanagement.di.component.DaggerAppComponent
import com.zcorp.opensportmanagement.di.module.ApplicationModule
import net.danlew.android.joda.JodaTimeAndroid
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
        systemLanguage = Locale.getDefault().language
        JodaTimeAndroid.init(this)
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

        // Application constants
        val PREF_NAME = "zcorp_pref"
    }
}