package com.zcorp.opensportmanagement.view

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import assertk.assert
import assertk.assertions.isEqualTo
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.argumentCaptor
import com.nhaarman.mockito_kotlin.given
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.ui.FailedEvent
import com.zcorp.opensportmanagement.ui.LoadingEvent
import com.zcorp.opensportmanagement.ui.SuccessEvent
import com.zcorp.opensportmanagement.ui.ViewModelEvent
import com.zcorp.opensportmanagement.ui.user_profile.MyProfileViewModel
import com.zcorp.opensportmanagement.util.TestSchedulerProvider
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MyProfileViewModelTest {

    private lateinit var viewModel: MyProfileViewModel
    private val eventsView: Observer<ViewModelEvent> = mock()
    private val repository: TeamRepository = mock()
    private val preferences: PreferencesHelper = mock()


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        viewModel = MyProfileViewModel(repository, preferences, TestSchedulerProvider())
        viewModel.updateEvents.observeForever(eventsView)
    }

    @Test
    fun testUpdateTeamMemberSuccessful() {
        given(repository.updateTeamMemberProfile(any(), any()))
                .willReturn(Completable.create { it.onComplete() })

        viewModel.updateTeamMember(
                firstName = "firstName",
                licenceNumber = "0199333",
                phoneNumber = "030303",
                email = "rpo@dpdp.fr",
                lastName = "lastName"
        )

        val arg = argumentCaptor<ViewModelEvent>()
        verify(eventsView, Mockito.times(2)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assert(values.size).isEqualTo(2)
        assert(values[0]).isEqualTo(LoadingEvent)
        assert(values[1]).isEqualTo(SuccessEvent)
    }

    @Test
    fun testUpdateTeamMemberFailure() {
        val throwable = Throwable()
        given(repository.updateTeamMemberProfile(any(), any()))
                .willReturn(Completable.create { it.onError(throwable) })

        viewModel.updateTeamMember(
                firstName = "firstName",
                licenceNumber = "0199333",
                phoneNumber = "030303",
                email = "rpo@dpdp.fr",
                lastName = "lastName"
        )

        val arg = argumentCaptor<ViewModelEvent>()
        verify(eventsView, Mockito.times(2)).onChanged(arg.capture())

        val values = arg.allValues
        // Test obtained values in order
        assert(values.size).isEqualTo(2)
        assert(values[0]).isEqualTo(LoadingEvent)
        assert(values[1]).isEqualTo(FailedEvent(throwable))
    }
}