package com.zcorp.opensportmanagement.ui.eventcreation

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.PunctualEventFragment
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.RecurentEventFragment
import kotlinx.android.synthetic.main.activity_event_creation.*
import org.greenrobot.eventbus.EventBus
import org.threeten.bp.LocalDateTime
import javax.inject.Inject


class EventCreationActivity : BaseActivity(),
        IEventCreationView {

    @Inject
    lateinit var mPunctualEventFragment: PunctualEventFragment
    @Inject
    lateinit var mRecurentEventFragment: RecurentEventFragment
    @Inject
    lateinit var mPresenter: IEventCreationPresenter

    override fun setTogglePosition(position: Int) {

    }

    override fun showPunctualEventView() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_event_datetime_selection_container, mPunctualEventFragment)
                .commit()
    }

    override fun showRecurrentEventView() {
        tb_recurrent_event.isChecked = true
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_event_datetime_selection_container, mRecurentEventFragment)
                .commit()
    }

    override fun getEventName(): String {
        return et_event_name.text.toString()
    }

    override fun getEventDescription(): String {
        return et_event_description.text.toString()
    }

    override fun getPunctualStartDate(): LocalDateTime? {
        return mPunctualEventFragment.getStartDate()
    }

    override fun getPunctualEndDate(): LocalDateTime? {
        return mPunctualEventFragment.getEndDate()
    }

    override fun getPlace(): String {
        return et_event_place.text.toString()
    }

    override fun getRecurrentStartDate(): LocalDateTime? {
        return mRecurentEventFragment.getStartDate()
    }

    override fun getRecurrentEndDate(): LocalDateTime? {
        return mRecurentEventFragment.getEndDate()
    }

    override fun showProgress() {
        pb_event_creation.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        pb_event_creation.visibility = View.GONE
    }

    override fun disableValidation() {
        button_validation_event_creation.isEnabled = false
    }

    override fun enableValidation() {
        button_validation_event_creation.isEnabled = true
    }

    override fun disableCancellation() {
        button_cancel_event_creation.isEnabled = false
    }

    override fun enableCancellation() {
        button_cancel_event_creation.isEnabled = true
    }

    override fun setPunctualChecked() {
        tb_punctual_event.isChecked = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation)
        setSupportActionBar(event_creation_toolbar as Toolbar)
        super.mActivityComponent.inject(this)

        mPresenter.onAttach(this)
        mPresenter.initView()
        switch_event_recurrence.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId[0]) {
                R.id.tb_punctual_event -> mPresenter.onPunctualSelected()
                R.id.tb_recurrent_event -> mPresenter.onRecurrentSelected()
            }
        }
        button_validation_event_creation.setOnClickListener {
            mPresenter.onCreateEvent()
        }
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(mPresenter)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(mPresenter)
    }
}