package com.zcorp.opensportmanagement.data

import com.fasterxml.jackson.databind.ObjectMapper
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.data.api.MessagesApi
import com.zcorp.opensportmanagement.data.api.TeamApi
import com.zcorp.opensportmanagement.data.api.UserApi
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.dto.MessageDto
import com.zcorp.opensportmanagement.model.*
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

/**
 * A nearly not fake data manager as for now, it only makes few real API calls
 */
class DataManager @Inject constructor(private val mPreferencesHelper: IPreferencesHelper,
                                      private val retrofit: Retrofit,
                                      private val objectMapper: ObjectMapper) : IDataManager {
    override fun getEvent(id: Int): Single<Event> {
        TODO("not implemented") //To change message of created functions use File | Settings | File Templates.
    }

    override fun getMatch(id: Int): Single<Match> {
        TODO("not implemented") //To change message of created functions use File | Settings | File Templates.
    }

    override fun getCurrentTeamId(): Int {
        return mPreferencesHelper.getCurrentTeamId()
    }

    override fun setCurrentTeamId(teamId: Int) {
        mPreferencesHelper.setCurrentTeamId(teamId)
    }

    override fun getConversations(): Single<List<Conversation>> {
        return retrofit.create(MessagesApi::class.java).getConversations()
    }

    override fun getMessagesOrderedByDate(conversationId: String): Single<List<InAppMessage>> {
        return retrofit.create(MessagesApi::class.java).getMessagesOrderedByDate(conversationId)
    }

    override fun createMessage(conversationId: String, messageDto: MessageDto): Single<InAppMessage> {
        return retrofit.create(MessagesApi::class.java).createMessage(conversationId, messageDto)
    }

    override fun login(loginRequest: LoginRequest): Single<Response<ResponseBody>> {
        return retrofit.create(UserApi::class.java).login(loginRequest)
    }

    override fun getTeams(): Single<List<Team>> {
        return retrofit.create(TeamApi::class.java).getTeams()
    }

    override fun getTeam(teamId: Int): Single<Team> {
        return retrofit.create(TeamApi::class.java).getTeam(teamId)
    }

    override fun getEventsCount(): Single<Int> {
//        return retrofit.create(EventApi::class.java).getConversationsCount()
        //TODO: remove this by a real API call
        return Single.just(20)
    }

    override fun getEvents(teamId: Int): Single<List<Event>> {
        return retrofit.create(EventApi::class.java).getEvents(teamId)
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
        TODO("not implemented") //To change message of created functions use File | Settings | File Templates.
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