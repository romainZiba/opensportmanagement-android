package com.zcorp.opensportmanagement.api

import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.OtherEvent
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Observable
import java.io.IOException
import java.util.*


/**
 * Created by romainz on 02/02/18.
 */
class EventApiImpl : EventApi {
    override fun getEventsCount(): Observable<Int> {
        return Observable.just(40)
    }

    @Throws(IOException::class)
    override fun getEvents(user: User): Observable<List<Event>> {
        val rand = Math.random()
        if (rand > 0.9) {
            throw IOException()
        }
        return Observable.create {
            it.onNext(longRunningOperation())
            it.onComplete()
        }
    }

    override fun createEvent(user: User, event: Event) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun longRunningOperation(): List<Event> {
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