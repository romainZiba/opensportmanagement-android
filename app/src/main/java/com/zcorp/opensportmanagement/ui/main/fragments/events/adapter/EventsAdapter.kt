package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.datasource.local.EventEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventType
import com.zcorp.opensportmanagement.utils.datetime.DateTimeFormatter

class EventsAdapter(
    private val mCallback: OnEventClickListener,
    private var mEvents: List<EventEntity>
) : RecyclerView.Adapter<BaseViewHolder>() {

    interface OnEventClickListener {
        fun onEventClicked(event: EventEntity, adapterPosition: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            EventType.MATCH.ordinal -> {
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

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        val event = mEvents[position]
        holder.setDate(DateTimeFormatter.dateFormatterWithDayOfWeek.format(event.fromDateTime))
        holder.setPlace(event.place)
        holder.setParticipantsNumber(event.presentTeamMembers.size)
        holder.itemView.setOnClickListener {
            mCallback.onEventClicked(mEvents[position], position)
        }
        when (holder) {
            is MatchViewHolder -> {
                holder.setLocalTeamName("Local")
                holder.setVisitorTeamName(event.opponent ?: "")
            }
        }
    }

    override fun getItemCount(): Int {
        return mEvents.size
    }

    override fun getItemViewType(position: Int): Int {
        val event = mEvents[position]
        return when (event.opponent != null) {
            true -> EventType.MATCH.ordinal // TODO: distinguish between championship / Tournament /Friendly...
            else -> EventType.OTHER.ordinal
        }
    }

    fun updateEvents(events: List<EventEntity>) {
        mEvents = events
        notifyDataSetChanged()
    }
}
