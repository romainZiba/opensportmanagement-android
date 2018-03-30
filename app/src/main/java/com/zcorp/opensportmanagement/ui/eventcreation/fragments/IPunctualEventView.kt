package com.zcorp.opensportmanagement.ui.eventcreation.fragments

import com.zcorp.opensportmanagement.ui.base.IBaseView

interface IPunctualEventView: IBaseView {
    fun showSelectedStartDate(dateTime: String)
    fun showDatePicker()
    fun showTimePicker()
    fun showSelectedEndDate(dateTime: String)
}