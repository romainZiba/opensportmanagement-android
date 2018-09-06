package com.zcorp.opensportmanagement.data.pref

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by romainz on 16/02/18.
 */
interface PreferencesHelper {
    fun getCurrentUserName(): String
    fun setCurrentUserName(username: String)
    fun getCurrentUserEmail(): String
    fun setCurrentUserEmail(email: String)
    fun getCurrentUserProfilePicUrl(): String
    fun setCurrentUserProfilePicUrl(profilePicUrl: String)
    fun getCurrentTeamId(): Int
    fun setCurrentTeamId(teamId: Int)
    fun getCurrentTeamMemberId(): Int
    fun setCurrentTeamMemberId(memberId: Int)
    fun setAvailableTeamIds(availableTeams: List<Int>)
    fun getAvailableTeamIds(): List<Int>
    fun clear()
}

class PreferencesHelperImpl(
    private val context: Context,
    private val prefFileName: String
) : PreferencesHelper {

    private val mPrefs: SharedPreferences = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getCurrentUserName(): String {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_NAME, "")
    }

    override fun setCurrentUserName(username: String) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_NAME, username).apply()
    }

    override fun getCurrentUserEmail(): String {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_EMAIL, "")
    }

    override fun setCurrentUserEmail(email: String) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_EMAIL, email).apply()
    }

    override fun getCurrentUserProfilePicUrl(): String {
        return mPrefs.getString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, "")
    }

    override fun setCurrentUserProfilePicUrl(profilePicUrl: String) {
        mPrefs.edit().putString(PREF_KEY_CURRENT_USER_PROFILE_PIC_URL, profilePicUrl).apply()
    }

    override fun getCurrentTeamId(): Int {
        return mPrefs.getInt(PREF_KEY_CURRENT_TEAM, -1)
    }

    override fun setCurrentTeamId(teamId: Int) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_TEAM, teamId).apply()
    }

    override fun getAvailableTeamIds(): List<Int> {
        return mPrefs.getStringSet(PREF_KEY_AVAILABLE_TEAMS, emptySet()).map { it.toInt() }.toList()
    }

    override fun setAvailableTeamIds(ids: List<Int>) {
        mPrefs.edit().putStringSet(PREF_KEY_AVAILABLE_TEAMS, ids.map { it.toString() }.toSet()).apply()
    }

    override fun getCurrentTeamMemberId(): Int {
        return mPrefs.getInt(PREF_KEY_CURRENT_MEMBER_ID, -1)
    }

    override fun setCurrentTeamMemberId(memberId: Int) {
        mPrefs.edit().putInt(PREF_KEY_CURRENT_MEMBER_ID, memberId).apply()
    }

    override fun clear() {
        with(mPrefs.edit()) {
            clear()
            apply()
        }
    }

    companion object {
        private const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
        private const val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
        private const val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_CURRENT_TEAM = "PREF_KEY_CURRENT_TEAM"
        private const val PREF_KEY_AVAILABLE_TEAMS = "PREF_KEY_AVAILABLE_TEAMS"
        private const val PREF_KEY_CURRENT_MEMBER_ID = "PREF_KEY_CURRENT_MEMBER"
    }
}
