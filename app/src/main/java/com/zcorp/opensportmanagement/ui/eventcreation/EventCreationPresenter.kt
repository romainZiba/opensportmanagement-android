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
        private const val PUNCTUAL_SWITCH_POSITION = 0
        private const val RECURRENT_SWITCH_POSITION = 1
    }

    private var mView: IEventCreationView? = null
    private var mSwitchPosition = 0

    override fun initView() {
        mView?.setPunctualChecked()
        mView?.showPunctualEventView()
    }

    override fun onPunctualSelected() {
        mSwitchPosition = PUNCTUAL_SWITCH_POSITION
        mView?.closeSoftKeyboard()
        mView?.showPunctualEventView()
    }

    override fun onRecurrentSelected() {
        mSwitchPosition = RECURRENT_SWITCH_POSITION
        mView?.closeSoftKeyboard()
        mView?.showRecurrentEventView()
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
                    //TODO: mView?.showError()
                    mView?.hideProgress()
                    mView?.enableCancellation()
                } else {
                    single = dataManager.createEvent(EventDto(mView!!.getEventName(),
                            mView!!.getEventDescription(),
                            fromDate,
                            toDate,
                            mView!!.getPlace(),
                            setOf(),
                            setOf()))
                }
            }
            RECURRENT_SWITCH_POSITION -> {
                val fromDate = mView!!.getRecurrentStartDate()
                val toDate = mView!!.getRecurrentEndDate()
                if (fromDate == null || toDate == null) {
                    //TODO: mView?.showError()
                    mView?.disableValidation()
                } else {
                    single = dataManager.createEvent(EventDto(mView!!.getEventName(),
                            mView!!.getEventDescription(),
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
                    mView?.enableValidation()
                    mView?.enableCancellation()
                }, {
                    mLogger.d(TAG, "Error occurred while creating event $it")
                    mView?.hideProgress()
                    mView?.enableValidation()
                    mView?.enableCancellation()
                })

    }

    override fun onAttach(view: IEventCreationView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}