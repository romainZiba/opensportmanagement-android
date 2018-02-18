package com.zcorp.opensportmanagement.data

import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.model.*
import io.reactivex.Single
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import java.io.IOException
import javax.inject.Inject

/**
 * Created by romainz on 10/02/18.
 */
class FakeDataManager @Inject constructor(val mPreferencesHelper: IPreferencesHelper) : IDataManager {


    override fun getMessagesOrderedByDate(): Single<List<InAppMessage>> {
        return Single.create {
            val messagesFromRomain = IntRange(1, 21).filter { it % 2 == 0 }.map { createDummyMessage(it, "Romain") }.toList()
            val messagesFromFriend = IntRange(1, 21).filter { (it + 1) % 2 == 0 }.map { createDummyMessage(it, "Ami") }.toList()
            val messages = listOf(messagesFromRomain, messagesFromFriend).flatMap { it.toList() }.sortedBy { it.dateTime }
            it.onSuccess(messages)
        }
    }

    private fun createDummyMessage(position: Int, username: String) =
            InAppMessage("Message " + position, username, OffsetDateTime.now().minusMonths(1).plusHours(position.toLong()))

    override fun login(username: String, password: String): Single<LoginResponse> {
        return Single.create({
            it.onSuccess(loginFromNetwork(username))
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

    override fun updateUserInfo(accessToken: String, userId: Int, loggedInMode: IDataManager.LoggedInMode,
                                userName: String, email: String, profilePicPath: String) {
        mPreferencesHelper.setCurrentUserId(userId)
        mPreferencesHelper.setCurrentUserName(userName)
        mPreferencesHelper.setAccessToken(accessToken)
        mPreferencesHelper.setCurrentUserLoggedInMode(loggedInMode)
        mPreferencesHelper.setCurrentUserEmail(email)
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicPath)
    }

    override fun createEvent(event: Event): Single<Event> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun loginFromNetwork(username: String): LoginResponse {
        try {
            Thread.sleep(800)
        } catch (e: InterruptedException) {
            // error
        }
        return LoginResponse(1, "abc", username, username + "@gmail.com", "",
                "", "")
    }

    private fun getEventsFromNetwork(): List<Event> {
        try {
            Thread.sleep(1000)
        } catch (e: InterruptedException) {
            // error
        }
        var events: MutableList<Event> = IntRange(1, 10).map { createDummyEvent(it) }.toMutableList()
        events.addAll(IntRange(10, 21).map { createDummyMatch(it) }.toList())
        return events.toList()
    }

    private fun createDummyEvent(position: Int): Event {
        return OtherEvent(position,
                "Apéro " + (position).toString(),
                "Une soirée " + position,
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "Ici")
    }

    private fun createDummyMatch(position: Int): Event {
        return Match(position,
                "Match de championnat",
                "",
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "ici",
                "TCMS2")
    }


    override fun getCurrentUserLoggedInMode(): Int {
        return mPreferencesHelper.getCurrentUserLoggedInMode()
    }

    override fun setCurrentUserLoggedInMode(mode: IDataManager.LoggedInMode) {
        mPreferencesHelper.setCurrentUserLoggedInMode(mode)
    }

    override fun getCurrentUserId(): Int {
        return mPreferencesHelper.getCurrentUserId()
    }

    override fun getCurrentUserName(): String {
        return mPreferencesHelper.getCurrentUserName()
    }

    override fun getCurrentUserEmail(): String {
        return mPreferencesHelper.getCurrentUserEmail()
    }

    override fun setCurrentUserEmail(email: String) {
        mPreferencesHelper.setCurrentUserEmail(email)
    }

    override fun getCurrentUserProfilePicUrl(): String {
        return mPreferencesHelper.getCurrentUserProfilePicUrl()
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String) {
        mPreferencesHelper.setCurrentUserProfilePicUrl(profilePicUrl)
    }

    override fun getAccessToken(): String {
        return mPreferencesHelper.getAccessToken()
    }

    override fun setAccessToken(accessToken: String) {
        mPreferencesHelper.setAccessToken(accessToken)
    }

    override fun setCurrentUserId(userId: Int) {
        mPreferencesHelper.setCurrentUserId(userId)
    }

    override fun setCurrentUserName(username: String) {
        mPreferencesHelper.setCurrentUserName(username)
    }
}