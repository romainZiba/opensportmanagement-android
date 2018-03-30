package com.zcorp.opensportmanagement.ui.eventcreation.fragments

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.io.Serializable

class PunctualEventPresenter : IPunctualEventPresenter {

    private var mView: IPunctualEventView? = null
    private var mSelectedStartDate: LocalDate? = null
    private var mSelectedStartTime: LocalTime? = null
    private var mSelectedEndDate: LocalDate? = null
    private var mSelectedEndTime: LocalTime? = null
    private var mSelectionDateType: SelectionDateType = SelectionDateType.START

    private enum class SelectionDateType {
        END, START
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onDateSelected(date: LocalDate) {
        when (mSelectionDateType) {
            SelectionDateType.START -> mSelectedStartDate = date
            SelectionDateType.END -> mSelectedEndDate = date
        }
        mView?.showTimePicker()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onTimeSelected(time: LocalTime) {
        when (mSelectionDateType) {
            SelectionDateType.START -> {
                mSelectedStartTime = time
                mView?.showSelectedStartDate("${mSelectedStartDate.toString()} ${mSelectedStartTime.toString()}")
            }
            SelectionDateType.END -> {
                mSelectedEndTime = time
                mView?.showSelectedEndDate("${mSelectedEndDate.toString()} ${mSelectedEndTime.toString()}")
            }
        }

    }

    override fun selectStartDate() {
        mSelectionDateType = SelectionDateType.START
        mView?.closeSoftKeyboard()
        mView?.showDatePicker()
    }

    override fun selectEndDate() {
        mSelectionDateType = SelectionDateType.END
        mView?.closeSoftKeyboard()
        mView?.showDatePicker()
    }

    override fun getStartDate(): LocalDateTime? {
        if (mSelectedStartDate != null && mSelectedStartTime != null) {
            return LocalDateTime.of(mSelectedStartDate, mSelectedStartTime)
        }
        return null
    }

    override fun getEndDate(): LocalDateTime? {
        if (mSelectedEndDate != null && mSelectedEndTime != null) {
            return LocalDateTime.of(mSelectedEndDate, mSelectedEndTime)
        }
        return null
    }

    override fun onAttach(view: IPunctualEventView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}