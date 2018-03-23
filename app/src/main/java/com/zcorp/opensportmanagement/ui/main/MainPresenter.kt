package com.zcorp.opensportmanagement.ui.main

import android.util.Log
import com.zcorp.opensportmanagement.data.IDataManager
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import java.io.Serializable
import javax.inject.Inject

/**
 * Created by romainz on 06/02/18.
 */
class MainPresenter @Inject constructor(
        private val dataManager: IDataManager,
        private val schedulerProvider: SchedulerProvider,
        private val mDisposables: CompositeDisposable) : IMainPresenter {

    companion object {
        private val  TAG = MainPresenter::class.java.simpleName
    }

    private var mainView: IMainView? = null

    override fun onDisplayEvents() {
        mainView?.displayEvents()
    }

    override fun onDisplayGoogle() {
        mainView?.displayGoogle()
    }

    override fun onDisplayConversations() {
        mainView?.displayConversations()
    }

    override fun onAttach(view: IMainView, vararg args: Serializable) {
        mainView = view
        mDisposables.add(dataManager.getTeams()
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
        )
    }

    private fun displayFragment(fragment: String) {
        when (fragment) {
            MainActivity.EVENTS -> mainView?.displayEvents()
            MainActivity.CONVERSATIONS -> mainView?.displayConversations()
            MainActivity.PLUS_ONE -> mainView?.displayGoogle()
            else -> mainView?.displayEvents()
        }
    }

    override fun onDetach() {
        mDisposables.clear()
        mainView = null
    }
}