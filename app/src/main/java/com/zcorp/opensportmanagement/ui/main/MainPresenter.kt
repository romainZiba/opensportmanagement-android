package com.zcorp.opensportmanagement.ui.main

import android.util.Log
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import javax.inject.Inject

/**
 * Created by romainz on 06/02/18.
 */
class MainPresenter @Inject constructor(val dataManager: IDataManager, val schedulerProvider: SchedulerProvider) : IMainPresenter {

    companion object {
        private val  TAG = MainPresenter::class.java.simpleName
    }

    private lateinit var mainView: IMainView

    override fun onDisplayEvents() {
        mainView.displayEvents()
    }

    override fun onDisplayGoogle() {
        mainView.displayGoogle()
    }

    override fun onDisplayThirdFragment() {
        mainView.displayMessages()
    }

    override fun onAttach(view: IMainView) {
        mainView = view
        dataManager.getTeams()
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    if (it.isNotEmpty()) {
                        dataManager.setCurrentTeamId(it[0]._id)
                        mainView.displayEvents()
                    }
                }, {
                    Log.d(TAG, "Error while retrieving teams")
                })
    }

    override fun onDetach() {
    }
}