package com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent

import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime
import java.io.Serializable

class RecurrentEventPresenter : IRecurrentEventPresenter {

    private var mView: IRecurrentEventView? = null

    private enum class SelectionDateType {
        FROM, TO
    }

    private enum class SelectionTimeType {
        FROM, TO
    }

    private var mSelectionDateType = SelectionDateType.FROM
    private var mSelectionTimeType = SelectionTimeType.FROM
    private var mFromDate: LocalDate? = null
    private var mToDate: LocalDate? = null
    private var mFromTime: LocalTime? = null
    private var mToTime: LocalTime? = null
    private var mSelectedDays: List<DayOfWeek> = emptyList()

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onDateSelected(date: LocalDate) {
        when (mSelectionDateType) {
            SelectionDateType.FROM -> {
                mFromDate = date
                mView?.showSelectedFromDate(date.toString())
            }
            SelectionDateType.TO -> {
                mToDate = date
                mView?.showSelectedToDate(date.toString())
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onTimeSelected(time: LocalTime) {
        when (mSelectionTimeType) {
            SelectionTimeType.FROM -> {
                mFromTime = time
                mView?.showSelectedFromTime(time.toString())
            }
            SelectionTimeType.TO -> {
                mToTime = time
                mView?.showSelectedToTime(time.toString())
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    override fun onDaysSelected(days: List<DayOfWeek>) {
        mSelectedDays = days
        val daysAsString = days.map { it.toString() }.reduce { day1, day2 -> "$day1, $day2 " }
        mView?.showSelectedDays(daysAsString)
    }

    override fun onSelectDays() {
        mView?.showDaysSelection()
    }

    override fun onSelectFromDate() {
        mSelectionDateType = SelectionDateType.FROM
        mView?.showFromDateSelection()
    }

    override fun onSelectToDate() {
        mSelectionDateType = SelectionDateType.TO
        mView?.showToDateSelection()
    }

    override fun onSelectFromTime() {
        mSelectionTimeType = SelectionTimeType.FROM
        mView?.showFromTimeSelection()
    }

    override fun onSelectToTime() {
        mSelectionTimeType = SelectionTimeType.TO
        mView?.showToTimeSelection()
    }

    override fun getFromDateTime(): LocalDateTime? {
        if (mFromDate != null && mFromTime != null) {
            return LocalDateTime.of(mFromDate, mFromTime)
        }
        return null
    }

    override fun getToDateTime(): LocalDateTime? {
        if (mToDate != null && mToTime != null) {
            return LocalDateTime.of(mToDate, mToTime)
        }
        return null
    }

    override fun onAttach(view: IRecurrentEventView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mView = null
    }
}