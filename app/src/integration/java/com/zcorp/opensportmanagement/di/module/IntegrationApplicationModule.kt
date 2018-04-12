package com.zcorp.opensportmanagement.di.module

import android.content.Context
import com.zcorp.opensportmanagement.data.DataManager
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by romainz on 09/03/18.
 */
@Module
class IntegrationApplicationModule(private val context: Context) : ApplicationModule(context) {
    @Provides
    @Singleton
    internal fun provideDataManager(dataManager: DataManager): IDataManager {
        return dataManager
    }
}