package com.zcorp.opensportmanagement.data

import com.zcorp.opensportmanagement.model.*
import io.reactivex.Single
import java.io.IOException
import java.util.*

/**
 * Created by romainz on 10/02/18.
 */
class FakeDataManager : IDataManager {
    override fun login(username: String, password: String): Single<String> {
        return Single.create({
            it.onSuccess(loginFromNetwork())
        })
    }

    override fun getTeams(user: User): List<Team> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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

    override fun getTeam(user: User, teamId: Int): Team {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEventsCount(): Single<Int> {
        return Single.just(40)
    }

    override fun updateUserInfo(accessToken: String, userId: Long?, loggedInMode: IDataManager.LoggedInMode, userName: String, email: String, profilePicPath: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createEvent(event: Event): Single<Event> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loginFromNetwork(): String {
        try {
            Thread.sleep(800)
        } catch (e: InterruptedException) {
            // error
        }
        return "aaabckdld"
    }

    private fun getEventsFromNetwork(): List<Event> {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            // error
        }
        var events: MutableList<Event> = IntRange(1, 10).map { createDummyEvent(it) }.toMutableList()
        events.addAll(3, IntRange(1, 10).map { createDummyMatch(it) }.toList())
        return events.toList()
    }

    private fun createDummyEvent(position: Int): Event {
        return OtherEvent("Apéro " + (position).toString(),
                "Une soirée " + position, Date(), Date(), "Ici")
    }

    private fun createDummyMatch(position: Int): Event {
        return Match("Match de championnat", "", Date(), Date(), "ici", "TCMS2")
    }

}