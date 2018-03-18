package com.zcorp.opensportmanagement.ui.eventdetails.fragments.Information

import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 16/03/18.
 */
class EventInformationPresenter @Inject constructor(
        val dataManager: IDataManager,
        val schedulerProvider: SchedulerProvider) : IEventInformationPresenter {

    private lateinit var mView: IEventInformationView

    override fun onPresentSelected() {
        mView.markAsPresent()
    }

    override fun onAbsentSelected() {
        mView.markAsAbsent()
    }

    override fun onAttach(view: IEventInformationView, vararg args: Serializable) {
        mView = view
    }

    override fun onDetach() {
    }

}