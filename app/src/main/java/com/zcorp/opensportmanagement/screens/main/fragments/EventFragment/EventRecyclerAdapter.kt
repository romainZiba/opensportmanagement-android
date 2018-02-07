package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class EventRecyclerAdapter(private val presenter: EventsPresenter) : RecyclerView.Adapter<EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_event_cardview, parent, false)
        return EventViewHolder(view, presenter)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        presenter.onBindEventRowViewAtPosition(position, holder)
    }

    override fun getItemCount(): Int {
        return presenter.getEventsCount()
    }
}
