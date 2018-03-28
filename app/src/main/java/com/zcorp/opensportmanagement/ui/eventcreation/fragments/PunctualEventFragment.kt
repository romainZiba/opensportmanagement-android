package com.zcorp.opensportmanagement.ui.eventcreation.fragments

import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import com.zcorp.opensportmanagement.ui.utils.DatePickerFragment
import com.zcorp.opensportmanagement.ui.utils.TimePickerFragment
import kotlinx.android.synthetic.main.fragment_punctual_event_creation.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class PunctualEventFragment : BaseFragment(), IPunctualEventView {

    @Inject
    lateinit var mPresenter: IPunctualEventPresenter
    @Inject
    lateinit var datePickerFragment: DatePickerFragment
    @Inject
    lateinit var timePickerFragment: TimePickerFragment


    override fun showSelectedDateAndTime(dateTime: String) {
        tv_event_date.setText(dateTime)
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
        view.findViewById<TextInputEditText>(R.id.tv_event_date).setOnClickListener {
            mPresenter.selectDateTime()
        }
        view.findViewById<TextInputLayout>(R.id.til_event_date).setOnClickListener {
            mPresenter.selectDateTime()
        }
        return view
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(mPresenter);
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