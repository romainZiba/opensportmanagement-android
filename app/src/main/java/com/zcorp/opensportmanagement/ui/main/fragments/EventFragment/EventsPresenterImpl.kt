package com.zcorp.opensportmanagement.ui.main.fragments.EventFragment

import com.zcorp.opensportmanagement.MyApplication
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.data.api.EventApi
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.utils.Utils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.util.*
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class EventsPresenterImpl @Inject constructor(val api: EventApi) : EventsPresenter {

    private var mEvents: List<Event> = mutableListOf()

    @Inject
    lateinit var mView: EventsView

    override fun getEvents() {
        try {
            api.getEvents()
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
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

    override fun onBindEventRowViewAtPosition(position: Int, holder: EventViewRow) {
        holder.setLocalTeamName(mEvents[position].name)
        holder.setDescription(mEvents[position].description)
        holder.setDate(Utils.format(mEvents[position].fromDate, Locale(MyApplication.systemLanguage)))
        holder.setListener()
    }

    override fun onItemClicked(adapterPosition: Int) {
        if (mView.isFabButtonOpened()) {
            mView.closeFabButton()
        } else {
            mView.showRowClicked(mEvents[adapterPosition].name)
        }
    }

    override fun onAttach(view: EventsView) {
        mView = view
    }

    override fun onDetach() {
    }
}