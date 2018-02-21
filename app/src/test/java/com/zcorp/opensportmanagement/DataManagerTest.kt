package com.zcorp.opensportmanagement

import assertk.assert
import assertk.assertions.hasSize
import assertk.assertions.isEqualTo
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.zcorp.opensportmanagement.data.DataManager
import com.zcorp.opensportmanagement.data.FakeDataManager
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.pref.IPreferencesHelper
import com.zcorp.opensportmanagement.dto.LoginResponseDto
import org.junit.Test
import retrofit2.Retrofit

/**
 * Created by romainz on 21/02/18.
 */

class DataManagerTest {
    @Test
    fun readResponseCorrectly() {
        val response = "[ {\n" +
                "    \"from\" : \"moi\",\n" +
                "    \"id\" : \"992eefd6-e444-4320-94f8-e86c52ebdeec\",\n" +
                "    \"time\" : \"2018-02-21T11:46:42.566+01:00\",\n" +
                "    \"message\" : \"hello\"\n" +
                "}, {\n" +
                "    \"from\" : \"un autre\",\n" +
                "    \"id\" : \"90efaeca-cd40-4d33-ae1f-b91f3af2d0b8\",\n" +
                "    \"time\" : \"2018-02-21T11:46:55.925+01:00\",\n" +
                "    \"message\" : \"Bonjour\"\n" +
                "} ]"
        val mapper = jacksonObjectMapper()
        mapper.findAndRegisterModules()
        val dataManager = DataManager(FakePreferencesHelper(), Retrofit.Builder().baseUrl("http://fake").build(), mapper)
        val messages = dataManager.getMessagesFromServerResponse(response)
        assert(messages).hasSize(2)
    }

    @Test
    fun readLoginResponse() {
        val response = "{ \"username\": \"me\"} "
        val mapper = jacksonObjectMapper()
        mapper.findAndRegisterModules()
        val loginResponse: LoginResponseDto = mapper.readValue(response)
        assert(loginResponse.username).isEqualTo("me")
    }

    internal class FakePreferencesHelper : IPreferencesHelper {
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