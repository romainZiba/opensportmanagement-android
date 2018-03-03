package com.zcorp.opensportmanagement

import assertk.assert
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.dto.LoginResponseDto
import org.junit.Test

/**
 * Created by romainz on 21/02/18.
 */

class DataManagerTest {

    @Test
    fun readLoginResponse() {
        val response = "{ \"username\": \"me\"} "
        val mapper = jacksonObjectMapper()
        mapper.findAndRegisterModules()
        val loginResponse: LoginResponseDto = mapper.readValue(response)
        assert(loginResponse.username).isEqualTo("me")
    }

    internal class FakePreferencesHelper : IPreferencesHelper {
        override fun getCurrentTeamId(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setCurrentTeamId(teamId: Int) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCurrentUserLoggedInMode(): Int {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setCurrentUserLoggedInMode(mode: IDataManager.LoggedInMode) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCurrentUserName(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setCurrentUserName(username: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCurrentUserEmail(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setCurrentUserEmail(email: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getCurrentUserProfilePicUrl(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setCurrentUserProfilePicUrl(profilePicUrl: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun getAccessToken(): String {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun setAccessToken(accessToken: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }
}