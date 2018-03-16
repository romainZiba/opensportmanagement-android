package com.zcorp.opensportmanagement.ui.eventdetails.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.llollox.androidtoggleswitch.widgets.ToggleSwitch
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_event_details_information.*
import kotlinx.android.synthetic.main.fragment_event_details_information.view.*
import javax.inject.Inject

/**
 * Created by romainz on 15/03/18.
 */
class EventInformationFragment : BaseFragment() {

    @Inject
    lateinit var dataManager: IDataManager

    @Inject
    lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_details_information, container, false)

        view.switch_presence_event_detail.onChangeListener = object : ToggleSwitch.OnChangeListener {
            override fun onToggleSwitchChanged(position: Int) {
                when (position) {
                    0 -> {
                        switch_presence_event_detail.checkedBackgroundColor = ContextCompat.getColor(
                                mContext, R.color.green_500)
                        switch_presence_event_detail.reDraw()
                    }
                    1 -> {
                        switch_presence_event_detail.checkedBackgroundColor = ContextCompat.getColor(
                                mContext, R.color.red_900)
                        switch_presence_event_detail.reDraw()
                    }
                }
            }
        }
        return view
    }
}