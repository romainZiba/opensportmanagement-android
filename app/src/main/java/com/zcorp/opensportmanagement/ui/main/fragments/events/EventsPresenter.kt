package com.zcorp.opensportmanagement.ui.main.fragments.events

import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.EventType
import com.zcorp.opensportmanagement.model.Match
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.EventViewHolder
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.IViewHolder
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.MatchViewHolder
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import org.threeten.bp.format.DateTimeFormatter
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
            mView.showProgress()
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
        val fmt: DateTimeFormatter = DateTimeFormatter.ofPattern("EEEE dd MMMM yyyy")
        holder.setDate(fmt.format(event.fromDate))
        holder.setListener()
        when (holder) {
            is MatchViewHolder -> {
                event as Match
                holder.setLocalTeamName("Local")
                holder.setVisitorTeamName(event.opponent)
            }
            is EventViewHolder -> {
                holder.setDescription(event.description)
            }
        }
    }

    override fun onItemClicked(adapterPosition: Int) {
        if (mView.isFloatingMenuOpened()) {
            // Do nothing
        } else {
            mView.showEventDetails(mEvents[adapterPosition].id)
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