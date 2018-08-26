package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.db.EventEntity
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.utils.datetime.DateTimeFormatter

/**
 * [RecyclerView.Adapter] that can display a list of [Event]
 */
class EventsAdapter(
    private val mCallback: OnEventClickListener,
    private var mEvents: List<EventEntity>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface OnEventClickListener {
        fun onEventClicked(event: EventEntity, adapterPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Event.EventType.CHAMPIONSHIP.ordinal -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_match, parent, false)
                MatchViewHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_event, parent, false)
                EventViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val event = mEvents[position]
        (holder as BaseViewHolder).setDate(DateTimeFormatter.dateFormatterWithDayOfWeek.format(event.fromDate))
        holder.itemView.setOnClickListener {
            mCallback.onEventClicked(mEvents[position], position)
        }
        when (holder) {
            is MatchViewHolder -> {
                holder.setLocalTeamName("Local")
                holder.setVisitorTeamName(event.opponent ?: "")
            }
            is EventViewHolder -> {
                holder.setDescription(event.description)
            }
        }
    }

    override fun getItemCount(): Int {
        return mEvents.size
    }

    override fun getItemViewType(position: Int): Int {
        val event = mEvents[position]
        return when (event.opponent != null) {
            true -> Event.EventType.CHAMPIONSHIP.ordinal // TODO: distinguish between championship / Tournament /Friendly...
            else -> Event.EventType.OTHER.ordinal
        }
    }

    fun updateEvents(events: List<EventEntity>) {
        mEvents = events
        notifyDataSetChanged()
    }
}
