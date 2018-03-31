package com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent

import com.zcorp.opensportmanagement.ui.base.IBasePresenter
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

interface IRecurrentEventPresenter : IBasePresenter<IRecurrentEventView> {
    fun onSelectDays()
    fun onSelectFromDate()
    fun onSelectToDate()
    fun onSelectFromTime()
    fun onSelectToTime()
    fun onDateSelected(date: LocalDate)
    fun onTimeSelected(time: LocalTime)
    fun onDaysSelected(days: List<DayOfWeek>)
    fun getFromDateTime(): LocalDateTime?
    fun getToDateTime(): LocalDateTime?
}