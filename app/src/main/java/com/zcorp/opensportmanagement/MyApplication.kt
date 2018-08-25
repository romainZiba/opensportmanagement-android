package com.zcorp.opensportmanagement

import android.content.res.Configuration
import android.support.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import com.zcorp.opensportmanagement.di.component.AppComponent
import com.zcorp.opensportmanagement.di.component.ComponentFactory
import java.util.Locale

/**
 * Created by romainz on 02/02/18.
 */
class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        appComponent = ComponentFactory.create(this)
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