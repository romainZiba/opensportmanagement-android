package com.zcorp.opensportmanagement.utils.stomp

import ua.naiksoftware.stomp.client.StompClient

/**
 * Created by romainz on 19/02/18.
 */
interface IStompClientProvider {
    fun client(uri: String): StompClient
}