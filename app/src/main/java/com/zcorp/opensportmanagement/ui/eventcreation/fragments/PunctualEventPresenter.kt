package com.zcorp.opensportmanagement.ui.eventcreation.fragments

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import java.io.Serializable

class PunctualEventPresenter : IPunctualEventPresenter {

    private var mView: IPunctualEventView? = null
    private var mSelectedDate: LocalDate? = null
    private var mSelectedTime: LocalTime? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onDateSelected(date: LocalDate) {
        mSelectedDate = date
        mView?.showTimePicker()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onTimeSelected(time: LocalTime) {
        mSelectedTime = time
        mView?.showSelectedDateAndTime("${mSelectedDate.toString()} ${mSelectedTime.toString()}")
    }

    override fun selectDateTime() {
        mView?.closeSoftKeyboard()
        mView?.showDatePicker()
    }

    override fun onAttach(view: IPunctualEventView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}