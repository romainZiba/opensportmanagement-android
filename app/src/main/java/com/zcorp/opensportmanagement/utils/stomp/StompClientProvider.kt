package com.zcorp.opensportmanagement.utils.stomp

import okhttp3.WebSocket
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.client.StompClient

/**
 * Created by romainz on 19/02/18.
 */
class StompClientProvider : IStompClientProvider {
    override fun client(uri: String): StompClient {
        return Stomp.over(WebSocket::class.java, uri)
    }
}