package com.zcorp.opensportmanagement.data.datasource.local

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.dto.EventDto
import com.zcorp.opensportmanagement.dto.MessageDto
import com.zcorp.opensportmanagement.model.Conversation
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.InAppMessage
import com.zcorp.opensportmanagement.model.Team
import com.zcorp.opensportmanagement.model.TeamMember
import com.zcorp.opensportmanagement.model.User
import io.reactivex.Single
import org.threeten.bp.LocalDateTime
import org.threeten.bp.OffsetDateTime
import java.util.Random

/**
 * Created by romainz on 10/02/18.
 */
class FakeDataManager(private val mPreferencesHelper: IPreferencesHelper) : IDataManager {

    companion object {
        private val user1 = User("Robert", "Albert", "RA", "ra@caram.com", "", true) // has access to brooklynNets and nyKnicks teams
        private val user2 = User("Pierre", "Mousquetaire", "PM", "pm@caram.com", "", false) // has access to brooklynNets only
        private val user3 = User("Jean", "Lament", "JL", "jl@gg.com", "", false) // has access to Houston Rockets only
        private val PASSWORD = "password"
        val DUMMY_CREDENTIALS = mapOf(Pair(user1.username, PASSWORD), Pair(user2.username, PASSWORD), Pair(user3.username, PASSWORD))
    }

    private var mLoggedUser: User = user1

    private val brooklynNets = Team(1, "Brooklyn Nets", Team.Sport.BASKETBALL, Team.Gender.MALE, Team.AgeGroup.ADULTS)
    private val nyKnicks = Team(2, "New York Knicks", Team.Sport.BASKETBALL, Team.Gender.MALE, Team.AgeGroup.ADULTS)
    private val houstonRockets = Team(3, "Houston Rockets", Team.Sport.BASKETBALL, Team.Gender.MALE, Team.AgeGroup.ADULTS)

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

    private val mPresentPlayers: MutableSet<TeamMember> = mutableSetOf()
    private val mAbsentPlayers: MutableSet<TeamMember> = mutableSetOf()

    init {
        val messagesFromUserConversation1 = IntRange(1, 6)
                .filter { it % 2 == 0 }
                .map { createDummyMessage(mConversationId1, it, user1.username) }
                .toList()
        val messagesFromFriendConversation1 = IntRange(1, 6)
                .filter { (it + 1) % 2 == 0 }
                .map { createDummyMessage(mConversationId1, it, user2.username) }
                .toList()
        mMessagesConversation1.addAll(messagesFromUserConversation1)
        mMessagesConversation1.addAll(messagesFromFriendConversation1)
        val messagesFromUserConversation2 = IntRange(1, 6)
                .map { createDummyMessage(mConversationId2, it, user1.username) }
                .toList()
        mMessagesConversation2.addAll(messagesFromUserConversation2)
        val messagesFromFriendConversation3 = IntRange(1, 6)
                .map { createDummyMessage(mConversationId3, it, user2.username) }
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

    override fun getMatch(id: Int): Single<Event> {
        return Single.create {
            it.onSuccess(createDummyMatch(id))
        }
    }

    override fun getTeams(): Single<List<Team>> {
        return Single.create {
            when (mLoggedUser) {
                user1 -> it.onSuccess(listOf(brooklynNets, nyKnicks))
                user2 -> it.onSuccess(listOf(brooklynNets))
                user3 -> it.onSuccess(listOf(houstonRockets))
                else -> it.onSuccess(emptyList())
            }
        }
    }

    override fun getEvents(teamId: Int): Single<List<Event>> {
        if ((mLoggedUser.username == user1.username && (teamId == brooklynNets._id || teamId == nyKnicks._id)) ||
                (mLoggedUser.username == user2.username && teamId == brooklynNets._id) ||
                (mLoggedUser.username == user3.username && teamId == houstonRockets._id)) {
            return when (teamId) {
                brooklynNets._id -> Single.create {
                    it.onSuccess(getEventsFromNetwork(teamId, 20))
                }
                nyKnicks._id -> Single.create {
                    it.onSuccess(getEventsFromNetwork(teamId, 8))
                }
                houstonRockets._id -> Single.create {
                    it.onSuccess(getEventsFromNetwork(teamId, 2))
                }
                else -> Single.create {
                    it.onError(Exception())
                }
            }
        }
        return Single.create {
            it.onError(Exception())
        }
    }

    override fun getTeam(teamId: Int): Single<Team> {
        return when (teamId) {
            nyKnicks._id -> Single.create { it.onSuccess(nyKnicks) }
            brooklynNets._id -> Single.create { it.onSuccess(brooklynNets) }
            else -> Single.create { it.onError(Exception()) }
        }
    }

    override fun createEvent(eventDto: EventDto): Single<Event> {
        val event = Event(0, eventDto.name, eventDto.description, eventDto.teamId,
                eventDto.fromDate, eventDto.toDate, eventDto.place, eventDto.presentTeamMembers,
                eventDto.absentTeamMembers, null)
        return Single.create {
            Thread.sleep(3000)
            it.onSuccess(event)
        }
    }

    private fun getEventsFromNetwork(teamId: Int, totalEvents: Int): List<Event> {
        val numberOfEvents = totalEvents / 2
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            // error
        }
        val events = IntRange(1, numberOfEvents).map { createDummyEvent(it, teamId) }.toMutableList()
        events.addAll(IntRange(numberOfEvents, totalEvents).map { createDummyMatch(it, teamId) }.toList())
        return events
    }

    private fun createDummyEvent(position: Int, teamId: Int = mPreferencesHelper.getCurrentTeamId()): Event {
        return Event(position,
                "Apéro " + (position).toString(),
                "Une soirée $position",
                teamId,
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "Ici",
                mPresentPlayers,
                mAbsentPlayers,
                null)
    }

    private fun createDummyMatch(position: Int, teamId: Int = mPreferencesHelper.getCurrentTeamId()): Event {
        return Event(position,
                "Match de championnat",
                "",
                teamId,
                LocalDateTime.of(2018, 1, 1 + position % 28, 20, 30, 0),
                LocalDateTime.of(2018, 1, 1 + position % 28, 22, 30, 0),
                "ici",
                mPresentPlayers,
                mAbsentPlayers,
                "TCMS2")
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

    override fun getConversations(): Single<List<Conversation>> {
        return Single.create {
            try {
                Thread.sleep(1500)
            } catch (e: InterruptedException) {
                // error
            }
            it.onSuccess(mConversations)
        }
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
            val message = InAppMessage(conversationId, "", mLoggedUser.username, messageDto.message, OffsetDateTime.now())
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

    override fun setAvailableTeamIds(availableTeams: List<Int>) {
        mPreferencesHelper.setAvailableTeamIds(availableTeams)
    }

    override fun getCurrentTeamId(): Int {
        return mPreferencesHelper.getCurrentTeamId()
    }

    override fun setCurrentTeamId(teamId: Int) {
        mPreferencesHelper.setCurrentTeamId(teamId)
    }

    override fun getAvailableTeamIds(): List<Int> {
        return mPreferencesHelper.getAvailableTeamIds()
    }
}