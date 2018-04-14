package com.zcorp.opensportmanagement.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.di.ApplicationContext
import com.zcorp.opensportmanagement.di.PreferenceInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesHelper @Inject constructor(@ApplicationContext context: Context, @PreferenceInfo prefFileName: String) : IPreferencesHelper {

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

    override fun getCurrentUserLoggedInMode(): Int {
        return mPrefs.getInt(PREF_KEY_USER_LOGGED_IN_MODE,
                IDataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type)
    }

    override fun setCurrentUserLoggedInMode(mode: IDataManager.LoggedInMode) {
        mPrefs.edit().putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.type).apply()
    }

    override fun getAccessToken(): String {
        return mPrefs.getString(PREF_KEY_ACCESS_TOKEN, "")
    }

    override fun setAccessToken(accessToken: String) {
        mPrefs.edit().putString(PREF_KEY_ACCESS_TOKEN, accessToken).apply()
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


    companion object {
        private const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
        private const val PREF_KEY_CURRENT_USER_NAME = "PREF_KEY_CURRENT_USER_NAME"
        private const val PREF_KEY_CURRENT_USER_EMAIL = "PREF_KEY_CURRENT_USER_EMAIL"
        private const val PREF_KEY_CURRENT_USER_PROFILE_PIC_URL = "PREF_KEY_CURRENT_USER_PROFILE_PIC_URL"
        private const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        private const val PREF_KEY_CURRENT_TEAM = "PREF_KEY_CURRENT_TEAM"
        private const val PREF_KEY_AVAILABLE_TEAMS = "PREF_KEY_AVAILABLE_TEAMS"
    }
}
