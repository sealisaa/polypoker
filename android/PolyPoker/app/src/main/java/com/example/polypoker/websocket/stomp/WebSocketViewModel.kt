package com.example.polypoker.websocket.stomp

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.polypoker.Utilities
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent
import ua.naiksoftware.stomp.dto.StompMessage
import ua.naiksoftware.stomp.provider.OkHttpConnectionProvider.TAG
import java.time.LocalDateTime

class WebSocketViewModel : ViewModel() {
    companion object{
        const val SOCKET_URL = "ws://192.168.1.116:8080/room/websocket"
        const val SERVER_ANSWERS_LINK = "/room/user"
        const val LINK_SOCKET = "/room/api/socket"
    }

    @SuppressLint("NewApi")
    private val gson: Gson = GsonBuilder().registerTypeAdapter(LocalDateTime::class.java,
        GsonLocalDateTimeAdapter()
    ).create()
    private var mStompClient: StompClient? = null
    private var compositeDisposable: CompositeDisposable? = null

    private val _chatState = MutableLiveData<Message?>()
    val liveChatState: LiveData<Message?> = _chatState

    init {
//            val headerMap: Map<String, String> =
//                Collections.singletonMap("Authorization", "Token")
        mStompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, SOCKET_URL/*, headerMap*/)
            .withServerHeartbeat(30000)
        resetSubscriptions()
        init()
    }

    private fun init() {
        resetSubscriptions()

        if (mStompClient != null) {
            val topicSubscribe = mStompClient!!.topic(SERVER_ANSWERS_LINK)
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ topicMessage: StompMessage ->
                    Log.d(TAG, "RECEIVED message from server -> " + topicMessage.payload)
                    val message: SocketMessage =
                        gson.fromJson(topicMessage.payload, SocketMessage::class.java)
                    val newMessage = dtoToEntity(message)
                },
                    {
                        Log.e(TAG, "Error!", it)
                    }
                )

            val lifecycleSubscribe = mStompClient!!.lifecycle()
                .subscribeOn(Schedulers.io(), false)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { lifecycleEvent: LifecycleEvent ->
                    when (lifecycleEvent.type!!) {
                        LifecycleEvent.Type.OPENED -> Log.d(TAG, "Stomp connection opened")
                        LifecycleEvent.Type.ERROR -> Log.e(TAG, "Error", lifecycleEvent.exception)
                        LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT,
                        LifecycleEvent.Type.CLOSED -> {
                            Log.d(TAG, "Stomp connection closed")
                        }
                    }
                }

            compositeDisposable!!.add(lifecycleSubscribe)
            compositeDisposable!!.add(topicSubscribe)

            if (!mStompClient!!.isConnected) {
                mStompClient!!.connect()
            }


        } else {
            Log.e(TAG, "mStompClient is null!")
        }
    }

    fun sendMessage(text: String) {
        val message = Message(text = text, author = Utilities.USER_LOGIN)
        val chatSocketMessage = entityToDto(message)
        sendCompletable(mStompClient!!.send(LINK_SOCKET, gson.toJson(chatSocketMessage)), gson.toJson(chatSocketMessage))
    }

    private fun sendCompletable(request: Completable, message: String) {
        compositeDisposable?.add(
            request.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        Log.d(TAG, "SENT message to server -> $message")
                    },
                    {
                        Log.e(TAG, "Stomp error", it)
                    }
                )
        )
    }

    private fun resetSubscriptions() {
        if (compositeDisposable != null) {
            compositeDisposable!!.dispose()
        }

        compositeDisposable = CompositeDisposable()
    }

    override fun onCleared() {
        super.onCleared()

        mStompClient?.disconnect()
        compositeDisposable?.dispose()
    }
}