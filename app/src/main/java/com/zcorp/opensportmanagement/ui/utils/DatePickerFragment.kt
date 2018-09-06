package com.zcorp.opensportmanagement.ui.utils

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.widget.DatePicker
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.threeten.bp.LocalDate

class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun newInstance(): DatePickerFragment {
            val fragment = DatePickerFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    private val _subject = PublishSubject.create<LocalDate>()
    val observable: Observable<LocalDate>
        get() = _subject.ofType(LocalDate::class.java)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)
        val localDate = LocalDate.now()
        return DatePickerDialog(activity, this, localDate.year, localDate.monthValue - 1, localDate.dayOfMonth)
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        _subject.onNext(LocalDate.of(year, month + 1, day))
    }

    fun showDialog(manager: FragmentManager, tag: String) {
        if (this.isAdded) {
            return
        }
        this.show(manager, tag)
    }
}