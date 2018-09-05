package com.zcorp.opensportmanagement

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

interface ConnectivityListener {
    fun onConnected()
    fun onDisconnected()
}

interface ConnectivityRepository {
    val connectivityStates: Observable<ConnectivityState>
}

class ConnectivityRepositoryImpl : ConnectivityListener, ConnectivityRepository {

    private val mConnectivityStates = PublishSubject.create<ConnectivityState>()

    override val connectivityStates: Observable<ConnectivityState>
        get() = mConnectivityStates

    override fun onConnected() {
        mConnectivityStates.onNext(ConnectivityState.CONNECTED)
    }

    override fun onDisconnected() {
        mConnectivityStates.onNext(ConnectivityState.DISCONNECTED)
    }
}

class NetworkChangesListener : BroadcastReceiver(), KoinComponent {
    private val connectivityListener: ConnectivityListener by inject()

    override fun onReceive(context: Context, intent: Intent) {
        val noConnectivity = intent.getBooleanExtra(ConnectivityManager.EXTRA_NO_CONNECTIVITY, false)

        if (!noConnectivity) {
            connectivityListener.onConnected()
        } else {
            connectivityListener.onDisconnected()
        }
    }
}

enum class ConnectivityState {
    CONNECTED, DISCONNECTED
}