package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.model.InAppMessage
import io.reactivex.Single
import java.io.IOException

/**
 * Created by romainz on 01/02/18.
 */
interface MessagesApi {

    @Throws(IOException::class)
    fun getMessages(): Single<List<InAppMessage>>
}