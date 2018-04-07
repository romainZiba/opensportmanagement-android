package com.zcorp.opensportmanagement.ui.eventcreation

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.dto.EventDto
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.Single
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.io.Serializable
import javax.inject.Inject

class EventCreationPresenter @Inject constructor(
        val dataManager: IDataManager,
        val schedulerProvider: SchedulerProvider,
        val mLogger: ILogger) : IEventCreationPresenter {

    companion object {
        private val TAG = EventCreationPresenter::class.java.simpleName
        const val PUNCTUAL_SWITCH_POSITION = 0
        const val RECURRENT_SWITCH_POSITION = 1
    }

    private var mView: IEventCreationView? = null
    var mSwitchPosition = 0

    override fun initView() {
        mView?.setPunctualChecked()
        mView?.showPunctualEventView()
    }

    override fun onPunctualSelected() {
        if (mSwitchPosition != PUNCTUAL_SWITCH_POSITION) {
            mSwitchPosition = PUNCTUAL_SWITCH_POSITION
            enableButtons()
            mView?.showPunctualEventView()
        }
        mView?.closeSoftKeyboard()
    }

    override fun onRecurrentSelected() {
        if (mSwitchPosition != RECURRENT_SWITCH_POSITION) {
            mSwitchPosition = RECURRENT_SWITCH_POSITION
            enableButtons()
            mView?.showRecurrentEventView()
        }
        mView?.closeSoftKeyboard()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDateSelected(date: LocalDate) {
        mView?.enableValidation()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTimeSelected(time: LocalTime) {
        mView?.enableValidation()
    }

    override fun onCreateEvent() {
        mView?.showProgress()
        mView?.disableValidation()
        mView?.disableCancellation()
        var single: Single<Event>? = null
        when (mSwitchPosition) {
            PUNCTUAL_SWITCH_POSITION -> {
                val fromDate = mView?.getPunctualStartDate()
                val toDate = mView?.getPunctualEndDate()
                if (fromDate == null || toDate == null) {
                    mView?.showPunctualDatesNotProvidedError()
                    mView?.hideProgress()
                    mView?.enableCancellation()
                } else {
                    single = dataManager.createEvent(EventDto(mView!!.getEventName(),
                            mView!!.getEventDescription(),
                            dataManager.getCurrentTeamId(),
                            fromDate,
                            toDate,
                            mView!!.getPlace(),
                            setOf(),
                            setOf()))
                }
            }
            RECURRENT_SWITCH_POSITION -> {
                val fromDate = mView!!.getRecurrenFromDate()
                val toDate = mView!!.getRecurrentToDate()
                if (fromDate == null || toDate == null) {
                    mView?.showRecurrentDatesNotProvidedError()
                    mView?.disableValidation()
                } else {
                    single = dataManager.createEvent(EventDto(mView!!.getEventName(),
                            mView!!.getEventDescription(),
                            dataManager.getCurrentTeamId(),
                            fromDate,
                            toDate,
                            mView!!.getPlace(),
                            setOf(),
                            setOf()))
                }
            }
        }
        single?.subscribeOn(schedulerProvider.newThread())
                ?.observeOn(schedulerProvider.ui())
                ?.subscribe({
                    mLogger.d(TAG, "Creation of event '$it' successful")
                    mView?.hideProgress()
                    enableButtons()

                }, {
                    mLogger.d(TAG, "Error occurred while creating event $it")
                    mView?.hideProgress()
                    enableButtons()
                })

    }

    fun enableButtons() {
        mView?.enableValidation()
        mView?.enableCancellation()
    }

    override fun onAttach(view: IEventCreationView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}