package com.zcorp.opensportmanagement.data

import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.dto.MessageDto
import com.zcorp.opensportmanagement.model.*
import io.reactivex.Single
import okhttp3.Headers
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import retrofit2.Response
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Created by romainz on 10/02/18.
 */
class FakeDataManager @Inject constructor(val mPreferencesHelper: IPreferencesHelper) : IDataManager {

    private val mFirstNames = listOf(
            "Albert", "Jean", "Paul", "William", "Richard", "René", "Benjamin", "Camille",
            "Dominique", "Edouard", "Fabien", "Gabrielle", "Henry", "Julie", "Lucie", "Marine", "Michel",
            "Nathalie", "Nathan", "Olivier", "Patricia", "Pierre")

    private val mLastNames = listOf("Martin", "Dubois", "Thomas", "Moreau", "Lefevre", "Lambert", "Roussel",
            "Guerin", "Rousseau", "Chevalier", "Lopez", "Smith", "Durant", "James", "Sanchez", "Curry",
            "Brown", "Nguyen", "Marchand", "Blanchard", "Leroux", "Renard", "Rolland", "Lacroix", "Davis",
            "Harden", "Carpentier", "Deschamps")

    private val mConversationId1 = "AAAA-BBBB-CCC"
    private val mConversationId2 = "ABAA-BBBB-CCC"
    private val mConversationId3 = "ACAA-BBBB-CCC"

    private val mConversations: MutableList<Conversation> = mutableListOf(
            Conversation(mConversationId1, "Bonjour à tous"),
            Conversation(mConversationId2, "Match programmé"),
            Conversation(mConversationId3, "Entraînement annulé")
    )

    private val mMessagesConversation1: MutableList<InAppMessage> = mutableListOf()
    private val mMessagesConversation2: MutableList<InAppMessage> = mutableListOf()
    private val mMessagesConversation3: MutableList<InAppMessage> = mutableListOf()

    private var mCurrentTeamId: Int = 0
    private val mUsername = "Romain"

    private val mPresentPlayers: MutableSet<TeamMember> = mutableSetOf()
    private val mAbsentPlayers: MutableSet<TeamMember> = mutableSetOf()


    init {
        val messagesFromUserConversation1 = IntRange(1, 6)
                .filter { it % 2 == 0 }
                .map { createDummyMessage(mConversationId1, it, mUsername) }
                .toList()
        val messagesFromFriendConversation1 = IntRange(1, 6)
                .filter { (it + 1) % 2 == 0 }
                .map { createDummyMessage(mConversationId1, it, "Ami") }
                .toList()
        mMessagesConversation1.addAll(messagesFromUserConversation1)
        mMessagesConversation1.addAll(messagesFromFriendConversation1)
        val messagesFromUserConversation2 = IntRange(1, 6)
                .map { createDummyMessage(mConversationId2, it, mUsername) }
                .toList()
        mMessagesConversation2.addAll(messagesFromUserConversation2)
        val messagesFromFriendConversation3 = IntRange(1, 6)
                .map { createDummyMessage(mConversationId3, it, "un autre") }
                .toList()
        mMessagesConversation3.addAll(messagesFromFriendConversation3)

        val teamMembers = createDummyTeamMembers(30)
        mPresentPlayers.addAll(teamMembers.filter { it.firstName.contains("a") })
        mAbsentPlayers.addAll(teamMembers.filter { !it.firstName.contains("a") })
    }

    override fun getEvent(id: Int): Single<Event> {
        return Single.create {
            it.onSuccess(createDummyEvent(id))
        }
    }

    override fun getMatch(id: Int): Single<Match> {
        return Single.create {
            it.onSuccess(createDummyMatch(id))
        }
    }

    override fun getConversations(): Single<List<Conversation>> {
        return Single.create {
            it.onSuccess(getConversationFromNetwork())
        }
    }

    override fun getCurrentTeamId(): Int {
        return mCurrentTeamId
    }

    override fun setCurrentTeamId(teamId: Int) {
        mCurrentTeamId = teamId
    }

    override fun getMessagesOrderedByDate(conversationId: String): Single<List<InAppMessage>> {
        return Single.create {
            when (conversationId) {
                mConversationId1 -> it.onSuccess(mMessagesConversation1.sortedBy { it.time }.toList())
                mConversationId2 -> it.onSuccess(mMessagesConversation2.sortedBy { it.time }.toList())
                mConversationId3 -> it.onSuccess(mMessagesConversation3.sortedBy { it.time }.toList())
                else -> it.onError(Exception("Conversation does not exist"))
            }
        }
    }

    override fun createMessage(conversationId: String, messageDto: MessageDto): Single<InAppMessage> {
        return Single.create {
            val message = InAppMessage(conversationId, "", mUsername, messageDto.message, OffsetDateTime.now())
            when (conversationId) {
                mConversationId1 -> mMessagesConversation1.add(message)
                mConversationId2 -> mMessagesConversation2.add(message)
                mConversationId3 -> mMessagesConversation3.add(message)
            }
            it.onSuccess(message)
        }
    }

    private fun createDummyMessage(conversationId: String, position: Int, username: String) =
            InAppMessage(conversationId, "", username, "Message $position",
                    OffsetDateTime.now().minusMonths(1).plusHours(position.toLong()))

    override fun login(loginRequest: LoginRequest): Single<Response<ResponseBody>> {
        return Single.create({
            loginFromNetwork(loginRequest.username)
            val responseBody = ResponseBody.create(MediaType.parse("application/json"), "{\"username\": \"$mUsername\"}")
            val headers = Headers.of("Authorization", "Bearer kjfkkf.kdjdjd.kkff")
            it.onSuccess(Response.success(responseBody, headers))
        })
    }

    override fun getTeams(): Single<List<Team>> {
        return Single.create {
            it.onSuccess(listOf(Team(1, "Astropico", Team.Sport.BASKETBALL, Team.Gender.BOTH, Team.AgeGroup.ADULTS)))
        }
    }

    override fun getEvents(teamId: Int): Single<List<Event>> {
        val rand = Math.random()
        if (rand > 0.95) {
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
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            // error
        }
        var events: MutableList<Event> = IntRange(1, 10).map { createDummyEvent(it) }.toMutableList()
        events.addAll(IntRange(10, 21).map { createDummyMatch(it) }.toList())
        return events.toList()
    }

    private fun getConversationFromNetwork(): List<Conversation> {
        try {
            Thread.sleep(1500)
        } catch (e: InterruptedException) {
            // error
        }
        return mConversations
    }

    private fun createDummyEvent(position: Int): Event {
        return Event(position,
                "Apéro " + (position).toString(),
                "Une soirée " + position,
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "Ici",
                mPresentPlayers,
                mAbsentPlayers)
    }

    private fun createDummyMatch(position: Int): Match {
        return Match(position,
                "Match de championnat",
                "",
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "ici",
                "TCMS2",
                mPresentPlayers,
                mAbsentPlayers)
    }

    private fun createDummyTeamMembers(number: Int): MutableSet<TeamMember> {
        val teamMembers: MutableSet<TeamMember> = mutableSetOf()
        while (teamMembers.size != number) {
            teamMembers.add(createDummyTeamMember())
        }
        return teamMembers
    }

    private fun createDummyTeamMember(): TeamMember {
        val firstName = mFirstNames[Random().nextInt(this.mFirstNames.size)]
        val lastName = mLastNames[Random().nextInt(this.mLastNames.size)]
        val username = StringBuilder().append(firstName[0]).append(lastName[0]).append(lastName[1]).toString()
        return TeamMember(username, firstName, lastName)
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