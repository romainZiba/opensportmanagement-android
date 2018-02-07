package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import com.zcorp.opensportmanagement.application.MyApplication
import com.zcorp.opensportmanagement.application.Utils
import com.zcorp.opensportmanagement.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*

/**
 * Created by romainz on 03/02/18.
 */
class EventsPresenterImpl(private val eventsView: EventsView,
                          private val eventsModel: EventsModel) : EventsPresenter {

    private var mEvents: List<Event> = mutableListOf()

    override fun getEvents() {
        try {
            eventsModel.provideEvents().subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        mEvents = it
                        eventsView.onDataAvailable()
                    })
        } catch (e: IOException) {
            eventsView.showNetworkError()
        }
    }

    override fun getEventsCount(): Int {
        return mEvents.size
    }

    override fun finish() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindEventRowViewAtPosition(position: Int, holder: EventViewRow) {
        holder.setLocalTeamName(mEvents[position].name)
        holder.setDescription(mEvents[position].description)
        holder.setDate(Utils.format(mEvents[position].fromDate, Locale(MyApplication.systemLanguage)))
        holder.setListener()
    }

    override fun onItemClicked(adapterPosition: Int) {
        if (eventsView.isFabButtonOpened()) {
            eventsView.closeFabButton()
        } else {
            eventsView.showRowClicked(mEvents[adapterPosition].name)
        }
    }
}