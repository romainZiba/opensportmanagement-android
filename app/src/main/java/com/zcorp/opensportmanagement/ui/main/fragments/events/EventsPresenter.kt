package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.support.v7.widget.RecyclerView
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.EventType
import com.zcorp.opensportmanagement.model.Match
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import net.danlew.android.joda.DateUtils
import java.io.IOException
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class EventsPresenter @Inject constructor(val api: EventApi, val schedulerProvider: SchedulerProvider) : IEventsPresenter {

    private var mEvents: List<Event> = mutableListOf()
    lateinit var mView: IEventsView

    override fun getEventsFromModel() {
        try {
            api.getEvents()
                    .subscribeOn(schedulerProvider.newThread())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        mEvents = it
                        mView.onDataAvailable()
                    }, {
                        mView.showNetworkError()
                    })
        } catch (e: IOException) {
            mView.showNetworkError()
        }
    }

    override fun getEventsCount(): Int {
        return mEvents.size
    }

    override fun onBindEventRowViewAtPosition(position: Int, holder: IViewHolder) {
        val event = mEvents[position]
        when (holder) {
            is MatchViewHolder -> {
                event as Match
                holder.setLocalTeamName("Local")
                holder.setVisitorTeamName(event.opponent)
                holder.setDate(DateUtils.formatDateTime(null, event.fromDate, DateUtils.FORMAT_SHOW_DATE))
                holder.setListener()
            }
            is EventViewHolder -> {
                holder.setDescription(event.description)
                holder.setDate(DateUtils.formatDateTime(null, event.fromDate, DateUtils.FORMAT_SHOW_DATE))
                holder.setListener()
            }
        }
    }

    override fun onItemClicked(adapterPosition: Int) {
        if (mView.isFloatingMenuOpened()) {
            // Do nothing
        } else {
            mView.showRowClicked(mEvents[adapterPosition].name)
        }
    }

    override fun onFloatingMenuClicked() {
        if (mView.isFloatingMenuOpened()) {
            mView.closeFloatingMenu()
            mView.setBackgroundAlpha(1F)
            mView.setBackground(R.drawable.background_blue_design)
        } else {
            mView.openFloatingMenu()
            mView.setBackgroundAlpha(0.2F)
            mView.setBackground(R.drawable.background_light_design)
        }
    }

    override fun onAddMatchClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAddEventClicked() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onAttach(view: IEventsView) {
        mView = view
    }

    override fun onDetach() {
    }

    override fun getEventType(position: Int): Int {
        val event = mEvents[position]
        return when (event) {
            is Match -> EventType.CHAMPIONSHIP.ordinal //TODO: distinguish between championship / Tournament /Friendly...
            else -> EventType.OTHER.ordinal
        }
    }
}