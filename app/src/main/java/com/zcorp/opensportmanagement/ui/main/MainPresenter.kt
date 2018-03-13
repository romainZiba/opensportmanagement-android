package com.zcorp.opensportmanagement.ui.main

import android.util.Log
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import java.io.Serializable
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

    override fun onDisplayConversations() {
        mainView.displayConversations()
    }

    override fun onAttach(view: IMainView, vararg args: Serializable) {
        mainView = view
        dataManager.getTeams()
                .subscribeOn(schedulerProvider.newThread())
                .observeOn(schedulerProvider.ui())
                .subscribe({
                    if (it.isNotEmpty()) {
                        dataManager.setCurrentTeamId(it[0]._id)
                        displayFragment(args[0].toString())
                    }
                }, {
                    Log.d(TAG, "Error while retrieving teams")
                })
    }

    private fun displayFragment(fragment: String) {
        when (fragment) {
            MainActivity.EVENTS -> mainView.displayEvents()
            MainActivity.CONVERSATIONS -> mainView.displayConversations()
            MainActivity.PLUS_ONE -> mainView.displayGoogle()
            else -> mainView.displayEvents()
        }
    }

    override fun onDetach() {
    }
}