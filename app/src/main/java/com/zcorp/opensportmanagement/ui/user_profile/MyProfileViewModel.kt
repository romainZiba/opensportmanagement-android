package com.zcorp.opensportmanagement.ui.user_profile

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.zcorp.opensportmanagement.data.datasource.local.TeamMemberEntity
import com.zcorp.opensportmanagement.data.datasource.remote.dto.TeamMemberUpdateDto
import com.zcorp.opensportmanagement.data.pref.PreferencesHelper
import com.zcorp.opensportmanagement.mvvm.RxViewModel
import com.zcorp.opensportmanagement.mvvm.SingleLiveEvent
import com.zcorp.opensportmanagement.repository.Resource
import com.zcorp.opensportmanagement.repository.TeamRepository
import com.zcorp.opensportmanagement.ui.FailedEvent
import com.zcorp.opensportmanagement.ui.LoadingEvent
import com.zcorp.opensportmanagement.ui.SuccessEvent
import com.zcorp.opensportmanagement.ui.ViewModelEvent
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import com.zcorp.opensportmanagement.utils.rx.with

class MyProfileViewModel(
    private val mTeamRepository: TeamRepository,
    private val mPreferencesHelper: PreferencesHelper,
    private val mSchedulerProvider: SchedulerProvider
) : RxViewModel() {

    private val mProfileLiveData = MutableLiveData<Resource<List<TeamMemberEntity>>>()
    val profileLiveData: LiveData<Resource<List<TeamMemberEntity>>>
        get() = mProfileLiveData

    private val mUpdateEvents = SingleLiveEvent<ViewModelEvent>()
    val updateEvents: LiveData<ViewModelEvent>
        get() = mUpdateEvents

    fun getTeamMemberInfo() {
        launch {
            mTeamRepository.getTeamMemberInfo(
                    mPreferencesHelper.getCurrentTeamMemberId(),
                    mPreferencesHelper.getCurrentTeamId())
                    .with(mSchedulerProvider)
                    .subscribe { resource ->
                        mProfileLiveData.value = resource
                    }
        }
    }

    fun updateTeamMember(
        firstName: String,
        lastName: String,
        email: String,
        phoneNumber: String,
        licenceNumber: String
    ) {
        mUpdateEvents.value = LoadingEvent
        val updateDto = TeamMemberUpdateDto(
                firstName = firstName,
                lastName = lastName,
                email = email,
                phoneNumber = phoneNumber,
                licenceNumber = licenceNumber
        )
        launch {
            mTeamRepository.updateTeamMemberProfile(mPreferencesHelper.getCurrentTeamId(), updateDto)
                    .with(mSchedulerProvider)
                    .subscribe({ mUpdateEvents.value = SuccessEvent },
                            { error -> mUpdateEvents.value = FailedEvent(error) })
        }
    }
}