package com.zcorp.opensportmanagement.ui.eventcreation.fragments

import com.zcorp.opensportmanagement.ui.base.IBasePresenter
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime

interface IPunctualEventPresenter : IBasePresenter<IPunctualEventView> {
    fun onDateSelected(date: LocalDate)
    fun onTimeSelected(time: LocalTime)
    fun selectDateTime()
}