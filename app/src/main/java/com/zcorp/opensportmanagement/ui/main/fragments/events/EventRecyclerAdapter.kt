package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.model.EventType

/**
 * [RecyclerView.Adapter] that can display a [Event]
 */
class EventRecyclerAdapter(private val presenter: IEventsPresenter) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            EventType.CHAMPIONSHIP.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_match_item, parent, false)
                MatchViewHolder(view, presenter)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.fragment_event_item, parent, false)
                EventViewHolder(view, presenter)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        presenter.onBindEventRowViewAtPosition(position, holder)
    }

    override fun getItemCount(): Int {
        return presenter.getEventsCount()
    }

    override fun getItemViewType(position: Int): Int {
        return presenter.getEventType(position)
    }
}
