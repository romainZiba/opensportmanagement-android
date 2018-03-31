package com.zcorp.opensportmanagement.ui.main.fragments.events

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.model.Event
import com.zcorp.opensportmanagement.utils.log.ILogger
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.IOException
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 03/02/18.
 */
class EventsPresenter @Inject constructor(
        private val dataManager: IDataManager,
        private val schedulerProvider: SchedulerProvider,
        private val mDisposables: CompositeDisposable,
        private val mLogger: ILogger) : IEventsPresenter {

    companion object {
        val TAG = EventsPresenter::class.java.simpleName
    }

    private var mView: IEventsView? = null

    override fun getEvents() {
        try {
            mView?.showProgress()
            mDisposables.add(dataManager.getEvents(dataManager.getCurrentTeamId())
                    .subscribeOn(schedulerProvider.newThread())
                    .observeOn(schedulerProvider.ui())
                    .subscribe({
                        mView?.onDataAvailable(it)
                    }, {
                        mLogger.d(TAG, "Error while retrieving events $it")
                        mView?.showNetworkError()
                    })
            )
        } catch (e: IOException) {
            mLogger.d(TAG, "Error while retrieving events $e")
            mView?.showNetworkError()
        }
    }

    override fun onEventSelected(event: Event, eventPosition: Int) {
        if (mView == null) return
        if (mView!!.isFloatingMenuOpened()) {
            // Do nothing
        } else {
            mView?.showEventDetails(event, eventPosition)
        }
    }

    override fun onFloatingMenuClicked() {
        if (mView == null) return
        if (mView!!.isFloatingMenuOpened()) {
            closeFloatingMenu()
        } else {
            openFloatingMenu()
        }
    }

    override fun onAddMatchClicked() {
        closeFloatingMenu()
        mView?.showAddMatch()
    }

    override fun onAddEventClicked() {
        closeFloatingMenu()
        mView?.showAddEvent()
    }

    override fun onAttach(view: IEventsView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
        mDisposables.clear()
        mView = null
    }

    private fun closeFloatingMenu() {
        mView?.closeFloatingMenu()
        mView?.setBackgroundAlpha(1F)
    }

    private fun openFloatingMenu() {
        mView?.openFloatingMenu()
        mView?.setBackgroundAlpha(0.2F)
    }
}