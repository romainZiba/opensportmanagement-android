package com.zcorp.opensportmanagement.ui.eventcreation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.ui.base.BaseFragment
import org.threeten.bp.LocalDateTime

class RecurentEventFragment : BaseFragment(), IRecurentEventView {

    override fun showSelectedDay(day: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.mFragmentComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_reccurent_event_creation, container, false)
        view.findViewById<Button>(R.id.button_fake_reccurent_event_creation).setOnClickListener {
            showSelectedDay(1)
        }
        return view
    }

    fun getStartDate(): LocalDateTime? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun getEndDate(): LocalDateTime? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}