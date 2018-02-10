package com.zcorp.opensportmanagement.data.api

import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.OtherEvent
import io.reactivex.Single
import java.io.IOException
import java.util.*


/**
 * Created by romainz on 02/02/18.
 */
class FakeEventApiImpl : EventApi {

    override fun getEventsCount(): Single<Int> {
        return Single.just(40)
    }

    override fun getEvents(): Single<List<Event>> {
        val rand = Math.random()
        if (rand > 0.9) {
            return Single.create {
                it.onError(IOException())
            }
        }
        return Single.create {
            it.onSuccess(getEventsFromNetwork())
        }
    }

    override fun createEvent(event: Event): Single<Event> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getEventsFromNetwork(): List<Event> {
        try {
            Thread.sleep(5000)
        } catch (e: InterruptedException) {
            // error
        }
        return IntRange(1, 41).map { createDummyEvent(it) }.toList()
    }

    private fun createDummyEvent(position: Int): Event {
        return OtherEvent("Apéro " + (position).toString(),
                "Une soirée " + position, Date(), Date(), "Ici")
    }
}