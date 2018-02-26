package com.zcorp.opensportmanagement.data

import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.model.*
import io.reactivex.Single
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

/**
 * Created by romainz on 10/02/18.
 */
class FakeDataManager @Inject constructor(val mPreferencesHelper: IPreferencesHelper) : IDataManager {

    private var mMessages: MutableList<InAppMessage> = mutableListOf()

    override fun getCurrentTeamId(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setCurrentTeamId(teamId: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getMessagesOrderedByDate(): Single<List<InAppMessage>> {
        return Single.create {
            val messagesFromRomain = IntRange(1, 21).filter { it % 2 == 0 }.map { createDummyMessage(it, "Romain") }.toList()
            val messagesFromFriend = IntRange(1, 21).filter { (it + 1) % 2 == 0 }.map { createDummyMessage(it, "Ami") }.toList()
            mMessages = listOf(messagesFromRomain, messagesFromFriend).flatMap { it.toList() }.sortedBy { it.time }.toMutableList()
            it.onSuccess(mMessages.toList())
        }
    }

    override fun createMessage(message: InAppMessage): Single<InAppMessage> {
        mMessages.add(message)
        return Single.create {
            it.onSuccess(message)
        }
    }

    private fun createDummyMessage(position: Int, username: String) =
            InAppMessage("Message " + position, username, OffsetDateTime.now().minusMonths(1).plusHours(position.toLong()))

    override fun login(loginRequest: LoginRequest): Single<Response<ResponseBody>> {
        return Single.create({
            loginFromNetwork(loginRequest.username)
            val responseBody = ResponseBody.create(MediaType.parse("application/json"), "")
            val headers = Headers.of("Authorization", "Bearer kjfkkf.kdjdjd.kkff")
            it.onSuccess(Response.success(responseBody, headers))
        })
    }

    override fun getTeams(): Single<List<Team>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEvents(teamId: Int): Single<List<Event>> {
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

    override fun getTeam(teamId: Int): Single<Team> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getEventsCount(): Single<Int> {
        return Single.just(40)
    }

    override fun updateUserInfo(accessToken: String, loggedInMode: IDataManager.LoggedInMode,
                                userName: String, email: String, profilePicPath: String) {
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
        return LoginResponse("")
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
        return Event(position,
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

    override fun setCurrentUserName(username: String) {
        mPreferencesHelper.setCurrentUserName(username)
    }
}