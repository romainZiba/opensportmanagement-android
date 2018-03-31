package com.zcorp.opensportmanagement.ui.utils

import android.app.Dialog
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import com.zcorp.opensportmanagement.R
import org.greenrobot.eventbus.EventBus
import org.threeten.bp.DayOfWeek

class DayOfWeekPickerFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val mContext = activity!!
        val builder = AlertDialog.Builder(mContext)
        builder.setTitle("Choose days")
        val daysOfWeek = DayOfWeek.values().map { it.toString() }.toTypedArray()
        val selected = BooleanArray(7, { false })
        builder.setMultiChoiceItems(daysOfWeek, selected,
                { _, which, isChecked ->
                    selected[which] = isChecked
                })
        val positiveText = mContext.getString(R.string.select)
        builder.setPositiveButton(positiveText,
                { dialog, _ ->
                    val selectedPositions = selected.mapIndexed { index, checked ->
                        if (checked) index
                        else -1
                    }.filter { it != -1 }
                    val selectedDays = daysOfWeek.filterIndexed({ index, _ -> selectedPositions.contains(index) })
                            .map { DayOfWeek.valueOf(it) }
                            .toList()
                    this.onDaysSet(selectedDays)
                    this.dismiss()
                })
        val negativeText = mContext.getString(android.R.string.cancel)
        builder.setNegativeButton(negativeText, { _, _ -> this.dismiss() })
        return builder.create()
    }

    private fun onDaysSet(days: List<DayOfWeek>) {
        EventBus.getDefault().post(days)
    }
}