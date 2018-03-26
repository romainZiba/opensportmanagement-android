package com.zcorp.opensportmanagement.di.module

import android.content.Context
import com.zcorp.opensportmanagement.data.FakeDataManager
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.pref.FakePreferencesHelper
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by romainz on 09/03/18.
 */
@Module
class DebugApplicationModule(context: Context) : ApplicationModule(context) {

    @Provides
    @Singleton
    internal fun provideDataManager(dataManager: FakeDataManager): IDataManager {
        return dataManager
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(fakePreferencesHelper: FakePreferencesHelper): IPreferencesHelper {
        return fakePreferencesHelper
    }
}