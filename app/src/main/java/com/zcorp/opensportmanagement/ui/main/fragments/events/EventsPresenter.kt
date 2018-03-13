package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.util.Log
import com.zcorp.opensportmanagement.R
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.Match
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.EventViewHolder
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.IEventViewHolder
import com.zcorp.opensportmanagement.ui.main.fragments.events.adapter.MatchViewHolder
import com.zcorp.opensportmanagement.utils.datetime.DateTimeFormatter
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import java.io.IOException
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class EventsPresenter @Inject constructor(val dataManager: IDataManager, val schedulerProvider: SchedulerProvider) : IEventsPresenter {
    companion object {
        val TAG = EventsPresenter::class.java.simpleName
    }
    private var mEvents: List<Event> = mutableListOf()
    private lateinit var mView: IEventsView

    override fun getEventsFromModel() {
        try {
            mView.showProgress()
            dataManager.getEvents(dataManager.getCurrentTeamId())
                    .subscribeOn(schedulerProvider.newThread())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        mEvents = it
                        mView.onDataAvailable()
                    }, {
                        Log.d(TAG, "Error while retrieving events $it")
                        mView.showNetworkError()
                    })
        } catch (e: IOException) {
            Log.d(TAG, "Error while retrieving events $e")
            mView.showNetworkError()
        }
    }

    override fun getEventsCount(): Int {
        return mEvents.size
    }

    override fun onBindEventRowViewAtPosition(position: Int, holder: IEventViewHolder) {
        val event = mEvents[position]
        holder.setDate(DateTimeFormatter.dateFormatterWithDayOfWeek.format(event.fromDate))
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
            mView.showEventDetails(mEvents[adapterPosition]._id)
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

    override fun onAttach(view: IEventsView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
    }

    override fun getEventType(position: Int): Int {
        val event = mEvents[position]
        return when (event) {
            is Match -> Event.EventType.CHAMPIONSHIP.ordinal //TODO: distinguish between championship / Tournament /Friendly...
            else -> Event.EventType.OTHER.ordinal
        }
    }
}