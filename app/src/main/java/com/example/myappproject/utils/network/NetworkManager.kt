package com.example.myappproject.utils.network
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.app.projectname.utils.event.Event

class NetworkManager(val application: Application) {
    private val _networkState = MutableLiveData<Event<NetworkState>>()
    val networkState: LiveData<Event<NetworkState>> = _networkState

    private var networkRequest: NetworkRequest = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
        .build()

    init {
        observeInternetConnection()
    }

    private fun observeInternetConnection() {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        cm.registerNetworkCallback(networkRequest, object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
//                _networkState.postValue(Event(NetworkState.Available("Internet Available")))
            }

            override fun onUnavailable() {
                super.onUnavailable()
                _networkState.postValue(Event(NetworkState.UnAvailable("Internet UnAvailable")))
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                _networkState.postValue(Event(NetworkState.Lost("Internet Lost")))
            }
        })

    }
}