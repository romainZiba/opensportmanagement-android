package com.zcorp.opensportmanagement.model

import retrofit2.http.Header

/**
 * Created by romainz on 16/02/18.
 */
data class LoginResponse(@Header("Authorization") val accessToken: String)