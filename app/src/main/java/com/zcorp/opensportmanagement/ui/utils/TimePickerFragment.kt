package com.zcorp.opensportmanagement.ui.utils

import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatDialogFragment
import android.widget.TimePicker
import org.greenrobot.eventbus.EventBus
import org.threeten.bp.LocalTime

class TimePickerFragment : AppCompatDialogFragment(), TimePickerDialog.OnTimeSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val localTime = LocalTime.now()
        return TimePickerDialog(activity, this, localTime.hour, localTime.minute, true)
    }

    override fun onTimeSet(view: TimePicker?, hour: Int, minute: Int) {
        EventBus.getDefault().post(LocalTime.of(hour, minute))
    }
}