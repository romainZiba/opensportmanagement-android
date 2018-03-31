package com.zcorp.opensportmanagement.ui.eventcreation.fragments.recurrent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.utils.DatePickerFragment
import com.zcorp.opensportmanagement.ui.utils.DayOfWeekPickerFragment
import com.zcorp.opensportmanagement.ui.utils.TimePickerFragment
import kotlinx.android.synthetic.main.fragment_recurrent_event_creation.*
import kotlinx.android.synthetic.main.fragment_recurrent_event_creation.view.*
import org.greenrobot.eventbus.EventBus
import org.threeten.bp.LocalDateTime
import javax.inject.Inject

class RecurrentEventFragment : BaseFragment(), IRecurrentEventView {

    @Inject
    lateinit var mPresenter: IRecurrentEventPresenter
    @Inject
    lateinit var mDatePickerFragment: DatePickerFragment
    @Inject
    lateinit var mTimePickerFragment: TimePickerFragment
    @Inject
    lateinit var mDaysPickerFragment: DayOfWeekPickerFragment

    override fun showSelectedDays(days: String) {
        et_recurrence_event_days.setText(days)
    }

    override fun showDaysSelection() {
        mDaysPickerFragment.show(fragmentManager, "daysPicker")
    }

    override fun getFromDateTime(): LocalDateTime? {
        return mPresenter.getFromDateTime()
    }

    override fun getToDateTime(): LocalDateTime? {
        return mPresenter.getToDateTime()
    }

    override fun showFromDateSelection() {
        mDatePickerFragment.show(fragmentManager, "datePicker")
    }

    override fun showToDateSelection() {
        mDatePickerFragment.show(fragmentManager, "datePicker")
    }

    override fun showFromTimeSelection() {
        mTimePickerFragment.show(fragmentManager, "timePicker")
    }

    override fun showToTimeSelection() {
        mTimePickerFragment.show(fragmentManager, "timePicker")
    }

    override fun showSelectedFromDate(date: String) {
        et_event_from_date.setText(date)
    }

    override fun showSelectedToDate(date: String) {
        et_event_to_date.setText(date)
    }

    override fun showSelectedFromTime(time: String) {
        et_event_from_time.setText(time)
    }

    override fun showSelectedToTime(time: String) {
        et_event_to_time.setText(time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_recurrent_event_creation, container, false)
        view.et_recurrence_event_days.setOnClickListener {
            mPresenter.onSelectDays()
        }
        view.et_event_from_date.setOnClickListener {
            mPresenter.onSelectFromDate()
        }
        view.et_event_to_date.setOnClickListener {
            mPresenter.onSelectToDate()
        }
        view.et_event_from_time.setOnClickListener {
            mPresenter.onSelectFromTime()
        }
        view.et_event_to_time.setOnClickListener {
            mPresenter.onSelectToTime()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(mPresenter)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(mPresenter)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDetach()
    }
}