package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import com.zcorp.opensportmanagement.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException

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
        holder.setName(mEvents[position].name)
        holder.setDescription(mEvents[position].description)
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