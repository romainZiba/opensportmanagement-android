package com.zcorp.opensportmanagement.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    companion object {
        val TAG: String = MainViewModel::class.java.simpleName
        const val EVENTS = "EVENTS"
        const val CONVERSATIONS = "CONVERSATIONS"
    }

    val visibleFragmentLiveData = MutableLiveData<String>()

    init {
        visibleFragmentLiveData.value = EVENTS
    }

    fun showEvents() {
        visibleFragmentLiveData.value = EVENTS
    }

    fun showConversations() {
        visibleFragmentLiveData.value = CONVERSATIONS
    }


}