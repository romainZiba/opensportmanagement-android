package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.synthetic.main.fragment_event_item.view.*

/**
 * Created by romainz on 03/02/18.
 */
class EventViewHolder(private val mView: View, private val presenter: IEventsPresenter) : RecyclerView.ViewHolder(mView), IEventViewHolder {

    override fun setDate(dateAsString: String) {
        mView.tv_date_event.text = dateAsString
    }

    override fun setDescription(description: String) {
        mView.tv_description_event.text = description
    }

    override fun setListener() {
        mView.setOnClickListener({
            presenter.onItemClicked(adapterPosition)
        })
    }
}
