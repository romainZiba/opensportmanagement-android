package com.zcorp.opensportmanagement.ui.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.widget.DatePicker
import org.greenrobot.eventbus.EventBus
import org.threeten.bp.LocalDate

class DatePickerFragment : AppCompatDialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val localDate = LocalDate.now()
        return DatePickerDialog(activity, this, localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        EventBus.getDefault().post(LocalDate.of(year, month + 1, day))
    }
}