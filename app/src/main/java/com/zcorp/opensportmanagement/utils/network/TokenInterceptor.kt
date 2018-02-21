package com.zcorp.opensportmanagement.utils.network

import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by romainz on 23/02/18.
 */
class TokenInterceptor(private val preferencesHelper: IPreferencesHelper) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        val accessToken = preferencesHelper.getAccessToken()
        if (accessToken != "") {
            request = chain.request().newBuilder()
                    .addHeader("Authorization", accessToken)
                    .build()
        }
        val response = chain.proceed(request)
        val refreshedToken = response.header("Authorization")
        if (refreshedToken != null) {
            preferencesHelper.setAccessToken(refreshedToken)
        }
        val unauthorized = response.code() == 401
        if (unauthorized) {
            // Ask to log in again
        }
        return response
    }

}