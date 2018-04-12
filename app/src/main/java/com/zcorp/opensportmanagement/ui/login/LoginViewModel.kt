package com.zcorp.opensportmanagement.ui.login

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.zcorp.opensportmanagement.model.User
import com.zcorp.opensportmanagement.repository.Resource
import com.zcorp.opensportmanagement.repository.UserRepository
import com.zcorp.opensportmanagement.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class LoginViewModel @Inject constructor(
        private val userRepository: UserRepository,
        private val mSchedulerProvider: SchedulerProvider) : ViewModel() {

    companion object {
        private val TAG = LoginViewModel::class.java.simpleName
    }

    // TODO: use dagger to inject in viewModel
    private val mDisposables = CompositeDisposable()
    private var loginStateLiveData = MutableLiveData<Resource<User>>()

    init {
        mDisposables.add(
                userRepository.userResource
                        .subscribeOn(mSchedulerProvider.io())
                        .observeOn(mSchedulerProvider.ui())
                        .subscribe({ loginStateLiveData.value = it }))
    }

    fun getLoginStateLiveData() = loginStateLiveData

    fun login(username: String, password: String): MutableLiveData<Resource<User>> {
        userRepository.login(username, password)
        return loginStateLiveData
    }

    override fun onCleared() {
        super.onCleared()
        mDisposables.clear()
    }
}
