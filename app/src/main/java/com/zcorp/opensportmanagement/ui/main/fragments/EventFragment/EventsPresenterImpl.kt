package com.zcorp.opensportmanagement.ui.main.fragments.EventFragment

import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.api.EventApi
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.model.User
import com.zcorp.opensportmanagement.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class EventsPresenterImpl @Inject constructor(val api: EventApi): EventsPresenter {

    private var mEvents: List<Event> = mutableListOf()

    @Inject
    lateinit var eventsView: EventsView

    override fun getEvents() {
        try {
            api.getEvents(User("", "", mutableSetOf()))
                    .subscribeOn(Schedulers.newThread())
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

    override fun onAttach(view: EventsView) {
        eventsView = view
    }

    override fun onDetach() {
    }
}