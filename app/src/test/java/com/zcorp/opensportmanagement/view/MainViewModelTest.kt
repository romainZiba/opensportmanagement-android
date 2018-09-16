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
import com.zcorp.opensportmanagement.ConnectivityRepository
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.ui.FailedEvent
import com.zcorp.opensportmanagement.ui.LoadingEvent
import com.zcorp.opensportmanagement.ui.SuccessEvent
import com.zcorp.opensportmanagement.ui.ViewModelEvent
import com.zcorp.opensportmanagement.ui.main.MainViewModel
import com.zcorp.opensportmanagement.util.TestSchedulerProvider
import io.reactivex.Completable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    private val eventsView: Observer<ViewModelEvent> = mock()
    private val userRepository: UserRepository = mock()
    private val teamRepository: TeamRepository = mock()
    private val eventRepository: EventRepository = mock()
    private val connectivityRepository: ConnectivityRepository = mock()

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun before() {
        viewModel = MainViewModel(userRepository, teamRepository, eventRepository, connectivityRepository, TestSchedulerProvider())
        viewModel.updateProfileEvents.observeForever(eventsView)
    }

    @Test
    fun testUpdateTeamMemberSuccessful() {
        given(teamRepository.updateTeamMemberProfile(any(), any()))
                .willReturn(Completable.create { it.onComplete() })

        viewModel.updateTeamMember(
                teamId = 1,
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
        given(teamRepository.updateTeamMemberProfile(any(), any()))
                .willReturn(Completable.create { it.onError(throwable) })

        viewModel.updateTeamMember(
                teamId = 1,
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