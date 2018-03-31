package com.zcorp.opensportmanagement.ui.eventcreation.fragments.punctual

import com.zcorp.opensportmanagement.ui.base.IBasePresenter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.LocalTime

interface IPunctualEventPresenter : IBasePresenter<IPunctualEventView> {
    fun onDateSelected(date: LocalDate)
    fun onTimeSelected(time: LocalTime)
    fun selectStartDate()
    fun selectEndDate()
    fun getStartDate(): LocalDateTime?
    fun getEndDate(): LocalDateTime?
}