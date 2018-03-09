package com.zcorp.opensportmanagement.di.module

import android.content.Context
import com.zcorp.opensportmanagement.data.FakeDataManager
import com.zcorp.opensportmanagement.data.IDataManager
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by romainz on 09/03/18.
 */
@Module
class DebugApplicationModule(private val context: Context) : ApplicationModule(context) {
    @Provides
    @Singleton
    internal fun provideDataManager(dataManager: FakeDataManager): IDataManager {
        return dataManager
    }
}