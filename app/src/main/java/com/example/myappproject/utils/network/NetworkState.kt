package com.example.myappproject.utils.network


sealed class NetworkState(val message: String) {
    class Available(message: String) : NetworkState(message)
    class UnAvailable(message: String) : NetworkState(message)
    class Lost(message: String) : NetworkState(message)
}