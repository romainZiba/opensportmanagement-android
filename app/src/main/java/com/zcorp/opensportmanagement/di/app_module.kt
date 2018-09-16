package com.zcorp.opensportmanagement.di

import android.arch.persistence.room.Room
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.zcorp.opensportmanagement.ConnectivityListener
import com.zcorp.opensportmanagement.ConnectivityRepository
import com.zcorp.opensportmanagement.ConnectivityRepositoryImpl
import com.zcorp.opensportmanagement.data.datasource.local.OpenDatabase
import com.zcorp.opensportmanagement.data.datasource.remote.api.EventApi
import com.zcorp.opensportmanagement.data.datasource.remote.api.EventApiImpl
import com.zcorp.opensportmanagement.data.datasource.remote.api.MessagesApi
import com.zcorp.opensportmanagement.data.datasource.remote.api.MessagesApiImpl
import com.zcorp.opensportmanagement.data.datasource.remote.api.TeamApi
import com.zcorp.opensportmanagement.data.datasource.remote.api.TeamApiImpl
import com.zcorp.opensportmanagement.data.datasource.remote.api.UserApi
import com.zcorp.opensportmanagement.data.datasource.remote.api.UserApiImpl
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.data.pref.PreferencesHelperImpl
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.EventRepositoryImpl
import com.zcorp.opensportmanagement.repository.MessageRepository
import com.zcorp.opensportmanagement.repository.MessageRepositoryImpl
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.repository.TeamRepositoryImpl
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.repository.UserRepositoryImpl
import com.zcorp.opensportmanagement.ui.conversations.ConversationViewModel
import com.zcorp.opensportmanagement.ui.main.MainViewModel
import com.zcorp.opensportmanagement.ui.team_details.TeamDetailsViewModel
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.log.Logger
import com.zcorp.opensportmanagement.utils.rx.AppSchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import okhttp3.CookieJar
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.Executor
import java.util.concurrent.Executors

val appModule = module {

    single { UserRepositoryImpl(get(), get()) as UserRepository }
    viewModel { MainViewModel(get(), get(), get(), get(), get()) }
    viewModel { ConversationViewModel(get(), get()) }
    viewModel { TeamDetailsViewModel(get(), get(), get()) }

    // Network executor
    single { Executors.newFixedThreadPool(5) as Executor }

    single { EventRepositoryImpl(get(), get(), get()) as EventRepository }
    single { MessageRepositoryImpl(get()) as MessageRepository }
    single { TeamRepositoryImpl(get(), get(), get()) as TeamRepository }

    single { Logger() as ILogger }
    single { PreferencesHelperImpl(androidApplication(), "preferences") as PreferencesHelper }
    single { AppSchedulerProvider() as SchedulerProvider }

    // Room Database
    single {
        Room.databaseBuilder(androidApplication(), OpenDatabase::class.java, "open.db")
                .build()
    }

    // Expose DAO directly
    single { get<OpenDatabase>().eventDao() }
    single { get<OpenDatabase>().teamDao() }

    // Connectivity listener
    single { ConnectivityRepositoryImpl() } bind ConnectivityRepository::class bind ConnectivityListener::class

    // User api
    val SCHEME = "https"
    val WSSCHEME = "wss"
    val PORT = 8090
    val HOST = "ns3268474.ip-5-39-81.eu"

    single { PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(androidApplication())) } bind CookieJar::class bind PersistentCookieJar::class

    single {
        OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    this.level = HttpLoggingInterceptor.Level.BODY
                })
                .cookieJar(get())
                .build() as OkHttpClient
    }

    single { jacksonObjectMapper().findAndRegisterModules() as ObjectMapper }

    single {
        Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("$SCHEME://$HOST:$PORT")
                .client(get())
                .build() as Retrofit
    }

    single { UserApiImpl(get()) as UserApi }
}

val offlineModule = module {
    //    bean { FakeDataManager(get()) as EventApi } bind TeamApi::class bind IDataManager::class bind MessagesApi::class
}

val onlineModule = module {
    single { TeamApiImpl(get()) as TeamApi }
    single { EventApiImpl(get()) as EventApi }
    single { MessagesApiImpl(get()) as MessagesApi }
}

val app = listOf(appModule, onlineModule)