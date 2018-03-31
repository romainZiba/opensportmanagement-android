package com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_event_details_information.view.*
import javax.inject.Inject

/**
 * Created by romainz on 15/03/18.
 */
class EventInformationFragment : BaseFragment(), IEventInformationView {

    @Inject
    lateinit var mPresenter: IEventInformationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
        mPresenter.onAttach(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_event_details_information, container, false)
        view.tb_present.setOnClickListener {
            mPresenter.onPresentSelected()
        }
        view.tb_absent.setOnClickListener {
            mPresenter.onAbsentSelected()
        }
        return view
    }
}