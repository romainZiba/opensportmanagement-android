package com.zcorp.opensportmanagement

import assertk.assert
import assertk.assertions.isEqualTo
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.ui.eventcreation.EventCreationPresenter
import com.zcorp.opensportmanagement.ui.eventcreation.IEventCreationView
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class EventCreationPresenterTest {
    private lateinit var mPresenter: EventCreationPresenter
    private lateinit var viewMock: IEventCreationView

    @Before
    fun setUp() {
        viewMock = Mockito.mock(IEventCreationView::class.java)
        val dataManagerMock = Mockito.mock(IDataManager::class.java)
        val schedulerProviderMock = Mockito.mock(SchedulerProvider::class.java)
        val loggerMock = Mockito.mock(ILogger::class.java)
        mPresenter = Mockito.spy(EventCreationPresenter(dataManagerMock, schedulerProviderMock, loggerMock))
        mPresenter.onAttach(viewMock)
    }

    @Test
    fun onPunctualSelected_should_enable_buttons() {
        mPresenter.mSwitchPosition = EventCreationPresenter.RECURRENT_SWITCH_POSITION
        mPresenter.onPunctualSelected()
        assert(mPresenter.mSwitchPosition).isEqualTo(EventCreationPresenter.PUNCTUAL_SWITCH_POSITION)
        verify(viewMock, times(1)).showPunctualEventView()
        verify(mPresenter, times(1)).enableButtons()
        verify(viewMock, times(1)).closeSoftKeyboard()
    }

    @Test
    fun onPunctualSelected_should_not_enable_buttons() {
        mPresenter.mSwitchPosition = EventCreationPresenter.PUNCTUAL_SWITCH_POSITION
        mPresenter.onPunctualSelected()
        assert(mPresenter.mSwitchPosition).isEqualTo(EventCreationPresenter.PUNCTUAL_SWITCH_POSITION)
        verify(viewMock, times(0)).showPunctualEventView()
        verify(mPresenter, times(0)).enableButtons()
        verify(viewMock, times(1)).closeSoftKeyboard()
    }

    @Test
    fun onRecurrentSelected_should_enable_buttons() {
        mPresenter.mSwitchPosition = EventCreationPresenter.PUNCTUAL_SWITCH_POSITION
        mPresenter.onRecurrentSelected()
        assert(mPresenter.mSwitchPosition).isEqualTo(EventCreationPresenter.RECURRENT_SWITCH_POSITION)
        verify(viewMock, times(1)).showRecurrentEventView()
        verify(mPresenter, times(1)).enableButtons()
        verify(viewMock, times(1)).closeSoftKeyboard()
    }

    @Test
    fun onRecurrentSelected_should_not_enable_buttons() {
        mPresenter.mSwitchPosition = EventCreationPresenter.RECURRENT_SWITCH_POSITION
        mPresenter.onRecurrentSelected()
        assert(mPresenter.mSwitchPosition).isEqualTo(EventCreationPresenter.RECURRENT_SWITCH_POSITION)
        verify(viewMock, times(0)).showRecurrentEventView()
        verify(mPresenter, times(0)).enableButtons()
        verify(viewMock, times(1)).closeSoftKeyboard()
    }

    @Test
    fun enableButtons_should_enable_view_buttons() {
        mPresenter.enableButtons()
        verify(viewMock, times(1)).enableCancellation()
        verify(viewMock, times(1)).enableValidation()
    }
}