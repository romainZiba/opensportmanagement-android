package com.zcorp.opensportmanagement

import android.content.res.Configuration
import android.support.multidex.MultiDexApplication
import com.jakewharton.threetenabp.AndroidThreeTen
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.zcorp.opensportmanagement.di.app
import org.koin.android.ext.android.startKoin
import java.util.Locale

/**
 * Created by romainz on 02/02/18.
 */
class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin(app)
        systemLanguage = Locale.getDefault()
        AndroidThreeTen.init(this)
        Iconify.with(FontAwesomeModule())
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (newConfig != null) {
            systemLanguage = newConfig.locale
        }
    }

    companion object {
        lateinit var systemLanguage: Locale
    }
}