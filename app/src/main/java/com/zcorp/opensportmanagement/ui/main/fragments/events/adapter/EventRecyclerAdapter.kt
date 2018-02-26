package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.ui.main.fragments.events.IEventsPresenter

/**
 * [RecyclerView.Adapter] that can display a [Event]
 */
class EventRecyclerAdapter(private val presenter: IEventsPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Event.EventType.CHAMPIONSHIP.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_match, parent, false)
                MatchViewHolder(view, presenter)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_event, parent, false)
                EventViewHolder(view, presenter)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        presenter.onBindEventRowViewAtPosition(position, holder as IEventViewHolder)
    }

    override fun getItemCount(): Int {
        return presenter.getEventsCount()
    }

    override fun getItemViewType(position: Int): Int {
        return presenter.getEventType(position)
    }
}
