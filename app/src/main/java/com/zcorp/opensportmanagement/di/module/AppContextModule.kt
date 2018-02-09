package com.zcorp.opensportmanagement.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule(private val context: Context) {

    @Singleton
    @Provides
    internal fun provideAppContext(): Context {
        return context
    }
}
