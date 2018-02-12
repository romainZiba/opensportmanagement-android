package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.zcorp.opensportmanagement.ui.main.fragments.events.IEventsPresenter
import kotlinx.android.synthetic.main.event_date_and_participants.view.*
import kotlinx.android.synthetic.main.event_participant_number.view.*

/**
 * Created by romainz on 12/02/18.
 */
abstract class BaseViewHolder(private val mView: View,
                              private val presenter: IEventsPresenter) : RecyclerView.ViewHolder(mView), IViewHolder {

    override fun setListener() {
        mView.setOnClickListener({
            presenter.onItemClicked(adapterPosition)
        })
    }

    override fun setDate(dateAsString: String) {
        mView.tv_date_event.text = dateAsString
    }

    override fun setParticipantsNumber(number: Int) {
        mView.tv_participants_number.text = number.toString()
    }
}