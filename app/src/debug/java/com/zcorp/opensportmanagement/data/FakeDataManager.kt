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

    private var mConversations: MutableList<Conversation> = mutableListOf(
            Conversation("AAAA-BBBB-CCC", "Bonjour à tous"),
            Conversation("ABCD-KKKF-JKFKKF", "Match programmé"),
            Conversation("AAAA-KOFOOFF-CCC", "Entraînement annulé")
    )

    private var mMessages: MutableList<InAppMessage> = mutableListOf()
    private var mCurrentTeamId: Int = 0
    private val mUsername = "Romain"

    override fun getMatch(id: Int): Single<Match> {
        val teamMembers = createDummyTeamMembers(20)
        val presentPlayers = teamMembers.filter { teamMember -> teamMember.firstName.contains("a") }.toMutableSet()
        val absentPlayers = teamMembers.filter { teamMember -> !teamMember.firstName.contains("a") }.toMutableSet()

        return Single.create {
            it.onSuccess(Match(1, "Match de championnat", "Match contre une équipe",
                    LocalDateTime.of(2018, 3, 17, 20, 0, 0),
                    LocalDateTime.of(2018, 3, 17, 22, 0, 0),
                    "Stade Valmy",
                    "TCMS2",
                    presentPlayers,
                    absentPlayers))
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
            val messagesFromRomain = IntRange(1, 21).filter { it % 2 == 0 }.map { createDummyMessage(it, mUsername) }.toList()
            val messagesFromFriend = IntRange(1, 21).filter { (it + 1) % 2 == 0 }.map { createDummyMessage(it, "Ami") }.toList()
            mMessages = listOf(messagesFromRomain, messagesFromFriend).flatMap { it.toList() }.sortedBy { it.time }.toMutableList()
            it.onSuccess(mMessages.toList())
        }
    }

    override fun createMessage(conversationId: String, messageDto: MessageDto): Single<InAppMessage> {
        val message = InAppMessage("", "", "", messageDto.message, OffsetDateTime.now())
        mMessages.add(message)
        return Single.create {
            it.onSuccess(message)
        }
    }

    private fun createDummyMessage(position: Int, username: String) =
            InAppMessage("AAA", "Hello World", username,"Message $position",
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
        val teamMembers = createDummyTeamMembers(20)
        val presentPlayers = teamMembers.filter { teamMember -> teamMember.firstName.contains("a") }.toMutableSet()
        val absentPlayers = teamMembers.filter { teamMember -> !teamMember.firstName.contains("a") }.toMutableSet()

        return Event(position,
                "Apéro " + (position).toString(),
                "Une soirée " + position,
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "Ici",
                presentPlayers,
                absentPlayers)
    }

    private fun createDummyMatch(position: Int): Event {
        val teamMembers = createDummyTeamMembers(20)
        val presentPlayers = teamMembers.filter { teamMember -> teamMember.firstName.contains("a") }.toMutableSet()
        val absentPlayers = teamMembers.filter { teamMember -> !teamMember.firstName.contains("a") }.toMutableSet()
        return Match(position,
                "Match de championnat",
                "",
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "ici",
                "TCMS2",
                presentPlayers,
                absentPlayers)
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