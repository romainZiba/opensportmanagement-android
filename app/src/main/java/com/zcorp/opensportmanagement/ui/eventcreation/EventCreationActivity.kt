package com.zcorp.opensportmanagement.ui.eventcreation

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseActivity
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.PunctualEventFragment
import com.zcorp.opensportmanagement.ui.eventcreation.fragments.RecurentEventFragment
import kotlinx.android.synthetic.main.activity_event_creation.*
import javax.inject.Inject


class EventCreationActivity : BaseActivity(),
        IEventCreationView {

    @Inject
    lateinit var mPunctualFragment: PunctualEventFragment
    @Inject
    lateinit var mRecurentEventFragment: RecurentEventFragment
    @Inject
    lateinit var mPresenter: IEventCreationPresenter

    override fun onPunctualEventRequested() {
        switch_event_recurrence.setCheckedPosition(0)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_event_creation_container, mPunctualFragment)
                .commit()
    }

    override fun onRecurentEventRequest() {
        switch_event_recurrence.setCheckedPosition(1)
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_event_creation_container, mRecurentEventFragment)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_creation)
        setSupportActionBar(event_creation_toolbar as Toolbar)
        super.mActivityComponent.inject(this)

        mPresenter.onAttach(this)
        mPresenter.initView()
        switch_event_recurrence.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(position: Int) {
                when (position) {
                    0 -> mPresenter.onPunctualSelected()
                    1 -> mPresenter.onRecurrentSelected()
                }
            }

        }
    }
}
