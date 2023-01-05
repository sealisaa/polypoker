package com.example.polypoker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.polypoker.websocket.SocketConnectionManager

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}