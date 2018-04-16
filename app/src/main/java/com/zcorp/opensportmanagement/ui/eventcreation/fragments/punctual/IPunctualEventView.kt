package com.zcorp.opensportmanagement.ui.eventcreation.fragments.punctual

import com.zcorp.opensportmanagement.ui.base.IBaseView
import org.threeten.bp.LocalDateTime

interface IPunctualEventView : IBaseView {
    fun showSelectedStartDate(dateTime: String)
    fun showDatePicker()
    fun showTimePicker()
    fun showSelectedEndDate(dateTime: String)
    fun getStartDateTime(): LocalDateTime?
    fun getEndDateTime(): LocalDateTime?
}