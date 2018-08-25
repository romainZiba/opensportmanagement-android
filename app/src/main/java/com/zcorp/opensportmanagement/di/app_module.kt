package com.zcorp.opensportmanagement.di

import android.arch.persistence.room.Room
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.MessagesApi
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.datasource.local.FakeDataManager
import com.zcorp.opensportmanagement.data.datasource.remote.DataManager
import com.zcorp.opensportmanagement.data.db.OpenDatabase
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.EventRepositoryImpl
import com.zcorp.opensportmanagement.repository.MessageRepository
import com.zcorp.opensportmanagement.repository.MessageRepositoryImpl
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.repository.UserRepositoryImpl
import com.zcorp.opensportmanagement.ui.login.LoginViewModel
import com.zcorp.opensportmanagement.ui.main.MainViewModel
import com.zcorp.opensportmanagement.ui.main.fragments.conversations.ConversationViewModel
import com.zcorp.opensportmanagement.ui.main.fragments.events.EventsViewModel
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.log.Logger
import com.zcorp.opensportmanagement.utils.network.TokenInterceptor
import com.zcorp.opensportmanagement.utils.rx.AppSchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import okhttp3.OkHttpClient
import org.koin.android.architecture.ext.viewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.applicationContext
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.jackson.JacksonConverterFactory

val appModule = applicationContext {
    viewModel { LoginViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { EventsViewModel(get(), get(), get()) }
    viewModel { ConversationViewModel(get(), get()) }

    bean { Logger() as ILogger }
    bean { EventRepositoryImpl(get(), get()) as EventRepository }
    bean { PreferencesHelper(androidApplication(), "preferences") as IPreferencesHelper }
    bean { AppSchedulerProvider() as SchedulerProvider }

    // Repositories
    bean { EventRepositoryImpl(get(), get()) as EventRepository }
    bean { UserRepositoryImpl(get(), get(), get(), get()) as UserRepository }
    bean { MessageRepositoryImpl(get()) as MessageRepository }

    // Room Database
    bean {
        Room.databaseBuilder(androidApplication(), OpenDatabase::class.java, "open.db")
                .build()
    }

    // Expose DAO directly
    bean { get<OpenDatabase>().eventDao() }
    bean { get<OpenDatabase>().teamDao() }
}

val offlineModule = applicationContext {
    bean { FakeDataManager(get()) as EventApi } bind UserApi::class bind TeamApi::class bind IDataManager::class bind MessagesApi::class
}

val onlineModule = applicationContext {
    val SCHEME = "https"
    val WSSCHEME = "wss"
    val PORT = 8090
    val HOST = ""

    bean {
        OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor(get())).build() as OkHttpClient
    }

    bean { jacksonObjectMapper().findAndRegisterModules() as ObjectMapper }

    bean {
        Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("$SCHEME://$HOST:$PORT")
                .client(get())
                .build() as Retrofit
    }

    bean { DataManager(get(), get()) as EventApi }
}

val app = listOf(appModule, offlineModule)