package com.zcorp.opensportmanagement.ui.main.fragments.events.adapter

import android.arch.paging.PagedListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.datasource.local.EventEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.EventDto
import com.zcorp.opensportmanagement.repository.NetworkState
import com.zcorp.opensportmanagement.repository.NetworkStatus
import com.zcorp.opensportmanagement.utils.datetime.DateTimeFormatter

class EventsAdapter : PagedListAdapter<EventDto, RecyclerView.ViewHolder>(EVENT_COMPARATOR) {

    companion object {

        val EVENT_COMPARATOR = object : DiffUtil.ItemCallback<EventDto>() {
            override fun areContentsTheSame(oldItem: EventDto, newItem: EventDto): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: EventDto, newItem: EventDto): Boolean =
                    oldItem._id == newItem._id
        }
    }

    interface OnEventClickListener {
        fun onEventClicked(event: EventEntity, adapterPosition: Int)
    }

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.rv_item_loading -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_loading, parent, false)
                LoadingViewHolder(view)
            }
            R.layout.rv_item_event -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.rv_item_event, parent, false)
                EventViewHolder(view)
            }
            else -> throw IllegalArgumentException("unknown view type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.rv_item_event -> {
                val event = getItem(position)
                (holder as EventViewHolder).setDate(DateTimeFormatter.dateFormatterWithDayOfWeek.format(event!!.fromDateTime))
                holder.setPlace(event.placeName)
                holder.setParticipantsNumber(event.presentMembers.size)
                holder.itemView.setOnClickListener {
                    //            mCallback.onEventClicked(event, position)
                }
            }
            R.layout.rv_item_loading -> {
                holder as LoadingViewHolder
                when (networkState?.status) {
                    NetworkStatus.RUNNING -> holder.progressBar.visibility = View.VISIBLE
                    else -> holder.progressBar.visibility = View.GONE
                }
            }
        }

    }

    fun setNetworkState(newNetworkState: NetworkState?) {
        val previousState = this.networkState
        val hadExtraRow = hasExtraRow()
        this.networkState = newNetworkState
        val hasExtraRow = hasExtraRow()
        if (hadExtraRow != hasExtraRow) {
            if (hadExtraRow) {
                notifyItemRemoved(super.getItemCount())
            } else {
                notifyItemInserted(super.getItemCount())
            }
        } else if (hasExtraRow && previousState != newNetworkState) {
            notifyItemChanged(itemCount - 1)
        }
    }

    private fun hasExtraRow() = networkState != null && networkState != NetworkState.LOADED

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.rv_item_loading
        } else {
            R.layout.rv_item_event
        }
    }
}
