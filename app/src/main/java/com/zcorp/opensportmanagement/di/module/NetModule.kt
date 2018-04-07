package com.zcorp.opensportmanagement.di.module

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.zcorp.opensportmanagement.BuildConfig
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.utils.network.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton


/**
 * Created by romainz on 21/02/18.
 */
@Module
class NetModule {

    @Provides
    @Singleton
    fun getJacksonMapper(): ObjectMapper {
        val mapper = jacksonObjectMapper()
        mapper.findAndRegisterModules()
        return mapper
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(preferencesHelper: IPreferencesHelper): OkHttpClient {
        val client = OkHttpClient.Builder()
                .addInterceptor(TokenInterceptor(preferencesHelper))
        return client.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(mapper: ObjectMapper, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("$SCHEME://${BuildConfig.HOST}:$PORT")
                .client(okHttpClient)
                .build()
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

    companion object {
        const val SCHEME = "https"
        const val WSSCHEME = "wss"
        const val PORT = 8090
    }
}