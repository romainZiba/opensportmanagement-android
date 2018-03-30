package com.zcorp.opensportmanagement.ui.eventcreation

import com.zcorp.opensportmanagement.ui.base.IBaseView
import org.threeten.bp.LocalDateTime

interface IEventCreationView : IBaseView {
    fun setTogglePosition(position: Int)
    fun showPunctualEventView()
    fun showRecurrentEventView()
    fun getEventName(): String
    fun getEventDescription(): String
    fun getPunctualStartDate(): LocalDateTime?
    fun getPunctualEndDate(): LocalDateTime?
    fun getPlace(): String
    fun getRecurrentStartDate(): LocalDateTime?
    fun getRecurrentEndDate(): LocalDateTime?
    fun showProgress()
    fun hideProgress()
    fun disableValidation()
    fun enableValidation()
    fun disableCancellation()
    fun enableCancellation()
    fun setPunctualChecked()

}