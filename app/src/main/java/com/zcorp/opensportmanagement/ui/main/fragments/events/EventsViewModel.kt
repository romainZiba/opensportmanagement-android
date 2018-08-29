package com.zcorp.opensportmanagement.ui.main.fragments.events

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.data.datasource.local.EventEntity
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.repository.EventRepository
import com.zcorp.opensportmanagement.repository.State
import com.zcorp.opensportmanagement.repository.Status
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

/**
 * Created by romainz on 03/02/18.
 */
class EventsViewModel(
        private val mEventRepository: EventRepository,
        private val mPreferencesHelper: PreferencesHelper,
        private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    companion object {
        val TAG: String = EventsViewModel::class.java.simpleName
    }

    private val mStates = MutableLiveData<State<List<EventEntity>>>()
    val states: LiveData<State<List<EventEntity>>>
        get() = mStates

    fun getEvents(forceRefresh: Boolean = false) {
        mStates.value = State.loading(true)
        launch {
            mEventRepository.loadEvents(mPreferencesHelper.getCurrentTeamId(), forceRefresh)
                    .with(mSchedulerProvider)
                    .subscribe { resource ->
                        mStates.value = State.loading(false)
                        when (resource.status) {
                            Status.ERROR -> {
                                mStates.value = State.failure(resource.message)
                            }
                            else -> {
                                mStates.value = State.success(resource.data!!)
                            }
                        }
                    }
        }
    }
}