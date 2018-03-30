package com.zcorp.opensportmanagement.ui.eventcreation.fragments

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.utils.DatePickerFragment
import com.zcorp.opensportmanagement.ui.utils.TimePickerFragment
import kotlinx.android.synthetic.main.fragment_punctual_event_creation.*
import org.greenrobot.eventbus.EventBus
import org.threeten.bp.LocalDateTime
import javax.inject.Inject


class PunctualEventFragment : BaseFragment(), IPunctualEventView {

    @Inject
    lateinit var mPresenter: IPunctualEventPresenter
    @Inject
    lateinit var datePickerFragment: DatePickerFragment
    @Inject
    lateinit var timePickerFragment: TimePickerFragment


    override fun showSelectedStartDate(dateTime: String) {
        tv_start_event_date.setText(dateTime)
    }

    override fun showSelectedEndDate(dateTime: String) {
        tv_end_event_date.setText(dateTime)
    }

    override fun showDatePicker() {
        datePickerFragment.show(fragmentManager, "timePicker")

    }

    override fun showTimePicker() {
        timePickerFragment.show(fragmentManager, "timePicker")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_punctual_event_creation, container, false)
        view.findViewById<TextInputEditText>(R.id.tv_start_event_date).setOnClickListener {
            mPresenter.selectStartDate()
        }
        view.findViewById<TextInputEditText>(R.id.tv_end_event_date).setOnClickListener {
            mPresenter.selectEndDate()
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

    fun getStartDate(): LocalDateTime? {
        return mPresenter.getStartDate()
    }

    fun getEndDate(): LocalDateTime? {
        return mPresenter.getEndDate()
    }
}