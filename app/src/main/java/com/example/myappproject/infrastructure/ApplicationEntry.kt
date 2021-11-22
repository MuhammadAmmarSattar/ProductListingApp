package com.example.myappproject.infrastructure

import android.app.Application
import com.example.myappproject.utils.network.NetworkManager
import com.squareup.otto.Bus
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class ApplicationEntry : Application() {
    private lateinit var networkManager: NetworkManager

    fun getNetworkObservable() = networkManager.networkState
    override fun onCreate() {
        super.onCreate()
//        Timber.plant(Timber.DebugTree())
        networkManager = NetworkManager(this)


    }


}