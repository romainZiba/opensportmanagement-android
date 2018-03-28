package com.zcorp.opensportmanagement.ui.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.DatePicker
import org.greenrobot.eventbus.EventBus
import org.threeten.bp.LocalDate

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val localDate = LocalDate.now()
        return DatePickerDialog(activity, this, localDate.year, localDate.monthValue, localDate.dayOfMonth)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        EventBus.getDefault().post(LocalDate.of(year, month, day))
    }
}