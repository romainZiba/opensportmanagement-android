package com.zcorp.opensportmanagement.screens.main.fragments.EventFragment

import com.zcorp.opensportmanagement.model.Event
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by romainz on 03/02/18.
 */
class EventsPresenterImpl(private val eventsView: EventsView,
                          private val eventsModel: EventsModel) : EventsPresenter {

    private var mEvents: List<Event> = mutableListOf()

    init {
        getEvents()
    }

    override fun getEvents() {
        eventsView.showProgress()
        eventsModel.provideEvents().subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    eventsView.hideProgress()
                    mEvents = it
                    eventsView.onDataChanged()
                })
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
    }
}