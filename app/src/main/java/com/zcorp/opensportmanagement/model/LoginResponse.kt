package com.zcorp.opensportmanagement.model

/**
 * Created by romainz on 16/02/18.
 */
data class LoginResponse(val userId: Int,
                         val accessToken: String,
                         val username: String,
                         var userEmail: String,
                         var userPictureUrl: String,
                         var fbPictureUrl: String,
                         var googlePictureUrl: String)