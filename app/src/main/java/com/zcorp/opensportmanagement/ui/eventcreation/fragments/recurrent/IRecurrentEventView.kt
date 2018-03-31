package com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent

import com.zcorp.opensportmanagement.ui.base.IBaseView
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDateTime

interface IRecurrentEventView: IBaseView {
    fun showSelectedDays(days: String)
    fun showDaysSelection()
    fun showFromDateSelection()
    fun showToDateSelection()
    fun showFromTimeSelection()
    fun showToTimeSelection()
    fun showSelectedFromDate(date: String)
    fun showSelectedToDate(date: String)
    fun showSelectedFromTime(time: String)
    fun showSelectedToTime(time: String)
    fun getFromDateTime(): LocalDateTime?
    fun getToDateTime(): LocalDateTime?
}