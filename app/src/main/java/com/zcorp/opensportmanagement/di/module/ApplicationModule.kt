package com.zcorp.opensportmanagement.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.MessagesApi
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.db.EventDao
import com.zcorp.opensportmanagement.data.db.OpenDatabase
import com.zcorp.opensportmanagement.data.db.TeamDao
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.di.PreferenceInfo
import com.zcorp.opensportmanagement.repository.*
import com.zcorp.opensportmanagement.utils.Constants
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.log.Logger
import com.zcorp.opensportmanagement.utils.rx.AppSchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class ApplicationModule(private val context: Context) {

    @Provides
    @ApplicationContext
    internal fun provideContext(): Context {
        return context
    }

    @Provides
    @Singleton
    internal fun provideLogger(): ILogger {
        return Logger()
    }

    @Provides
    @Singleton
    internal fun provideDatabase(@ApplicationContext appContext: Context): OpenDatabase {
        return Room.databaseBuilder(appContext, OpenDatabase::class.java!!, "open.db").build()
    }

    @Provides
    @Singleton
    internal fun provideEventDao(db: OpenDatabase): EventDao {
        return db.eventDao()
    }

    @Provides
    @Singleton
    internal fun provideTeamDao(db: OpenDatabase): TeamDao {
        return db.teamDao()
    }

    @Provides
    @Singleton
    internal fun provideEventRepository(eventApi: EventApi,
                                        eventDao: EventDao): EventRepository {
        return EventRepositoryImpl(eventApi, eventDao)
    }

    @Provides
    @Singleton
    internal fun provideUserRepository(userApi: UserApi,
                                       teamApi: TeamApi,
                                       teamDao: TeamDao,
                                       preferences: IPreferencesHelper): UserRepository {
        return UserRepositoryImpl(userApi, teamApi, teamDao, preferences)
    }

    @Provides
    @Singleton
    internal fun provideMessageRepository(messageApi: MessagesApi): MessageRepository {
        return MessageRepositoryImpl(messageApi)
    }


    @Provides
    @Singleton
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    internal fun providePreferencesHelper(helper: PreferencesHelper): IPreferencesHelper {
        return helper
    }

    @Provides
    @Singleton
    @PreferenceInfo
    internal fun providePrefFileName(): String {
        return Constants.PREF_FILE_NAME
    }
}
