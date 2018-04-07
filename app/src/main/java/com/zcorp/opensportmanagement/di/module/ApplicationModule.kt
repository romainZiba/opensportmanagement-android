package com.zcorp.opensportmanagement.di.module

import android.arch.persistence.room.Room
import android.content.Context
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.db.EventDao
import com.zcorp.opensportmanagement.data.db.OpenDatabase
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.repository.EventRepository
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
    internal fun provideEventRepository(eventApi: EventApi,
                                        eventDao: EventDao,
                                        schedulerProvider: SchedulerProvider,
                                        logger: ILogger): EventRepository {
        return EventRepository(eventApi, eventDao, schedulerProvider, logger)
    }

    @Provides
    @Singleton
    internal fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

}
