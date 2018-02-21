package com.zcorp.opensportmanagement.di.module

import android.content.Context
import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.data.DataManager
import com.zcorp.opensportmanagement.data.FakeDataManager
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.pref.FakePreferencesHelper
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.di.PreferenceInfo
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

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

    @Provides
    @Singleton
    internal fun providePreferencesHelper(fakePreferencesHelper: FakePreferencesHelper): IPreferencesHelper {
        return fakePreferencesHelper
    }

    @Provides
    @Singleton
    internal fun provideDataManager(dataManager: DataManager): IDataManager {
        return dataManager
    }

    @Provides
    @Singleton
    internal fun provideEventApi(dataManager: IDataManager): EventApi {
        return dataManager
    }

    @Provides
    @Singleton
    internal fun provideUserApi(dataManager: IDataManager): UserApi {
        return dataManager
    }
}
