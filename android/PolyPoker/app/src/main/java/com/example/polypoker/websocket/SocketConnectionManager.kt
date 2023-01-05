package com.example.polypoker.websocket

import android.app.Application
import android.content.Context
import android.content.OperationApplicationException
import android.util.Log

class SocketConnectionManager {

    private lateinit var socketConnection: SocketConnection

    fun init(context: Context, application: Application) {
        socketConnection =
            SocketConnection(context)
        BackgroundManager.get(application).registerListener(appActivityListener)
        openSocketConnection()
    }

    fun closeSocketConnection() {
        socketConnection.closeConnection()
    }

    fun openSocketConnection() {
        socketConnection.openConnection()
    }

    fun isSocketConnected(): Boolean {
        return socketConnection.isConnected()
    }

    fun reconnect() {
        socketConnection.openConnection()
    }


    private val appActivityListener: BackgroundManager.Listener =
        object : BackgroundManager.Listener {
            override fun onBecameForeground() {
                openSocketConnection()
                Log.i("Websocket", "Became Foreground")
            }

            override fun onBecameBackground() {
                closeSocketConnection()
                Log.i("Websocket", "Became Background")
            }
        }
}