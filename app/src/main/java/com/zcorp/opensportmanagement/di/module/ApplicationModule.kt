package com.zcorp.opensportmanagement.di.module

import android.content.Context
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.di.PreferenceInfo
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @ApplicationContext
    internal fun provideAppContext(): Context {
        return context
    }

    @Provides
    @PreferenceInfo
    internal fun providePreferenceName(): String {
        return MyApplication.PREF_NAME
    }
}
