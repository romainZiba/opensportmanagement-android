package com.zcorp.opensportmanagement.application.builder

import android.content.Context

import dagger.Module
import dagger.Provides

@Module
class AppContextModule(private val context: Context) {

    @ApplicationScope
    @Provides
    internal fun provideAppContext(): Context {
        return context
    }
}
