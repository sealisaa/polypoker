package com.example.polypoker.ui.main

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.navigation.findNavController
import com.example.polypoker.R
import com.example.polypoker.Utilities
import com.example.polypoker.model.User
import com.example.polypoker.model.UserStatistic
import com.example.polypoker.retrofit.RetrofitService
import com.example.polypoker.retrofit.RoomApi
import com.example.polypoker.retrofit.UserApi
import com.example.polypoker.retrofit.UserStatisticApi
import com.example.polypoker.websocket.nv.RealTimeEvent
import com.example.polypoker.websocket.nv.SocketConnectionManager
import com.example.polypoker.websocket.stomp.MessageContent
import com.example.polypoker.websocket.stomp.MessageType
import com.example.polypoker.websocket.stomp.SocketMessage
import com.example.polypoker.websocket.stomp.WebSocketViewModel
import org.greenrobot.eventbus.Subscribe
import retrofit2.Call
import retrofit2.Response
import ua.naiksoftware.stomp.dto.LifecycleEvent
import java.time.LocalDateTime
import java.util.logging.Level
import java.util.logging.Logger

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MainMenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MainMenuFragment : Fragment() {

    lateinit var userStatisticApi: UserStatisticApi
    lateinit var userApi: UserApi
    lateinit var roomApi: RoomApi

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.main_menu_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utilities.currentMainMenuView = view

        if (Utilities.webSocketViewModel == null) {
            Utilities.webSocketViewModel = WebSocketViewModel(view)
        }

        val retrofitService = RetrofitService()
        userStatisticApi = retrofitService.retrofit.create(UserStatisticApi::class.java)
        userApi = retrofitService.retrofit.create(UserApi::class.java)
        roomApi = retrofitService.retrofit.create(RoomApi::class.java)

        val userNameSurnameTextView = view.findViewById<TextView>(R.id.userNameSurname)
        val currentCoinsCountTextView = view.findViewById<TextView>(R.id.currentCoinsCountText)
        val totalGamesPlayedTextView = view.findViewById<TextView>(R.id.totalGamesPlayedText)
        val winGamesTextView = view.findViewById<TextView>(R.id.winGamesText)
        val totalEarnTextView = view.findViewById<TextView>(R.id.totalEarnText)


        val mainMenuFragment = this

        userApi.getUserByLogin(Utilities.USER_LOGIN)
            .enqueue(object: retrofit2.Callback<User> {
                override fun onResponse(call: Call<User>, response: Response<User>) {
                    if (response.isSuccessful) {
                        val user = response.body()

                        userNameSurnameTextView.text = "${user?.name} ${user?.surname}"
                        Utilities.USER_NAME = "${user?.name} ${user?.surname}"
                    }
                }

                override fun onFailure(call: Call<User>, t: Throwable) {
                    Toast.makeText(
                        mainMenuFragment.context,
                        "Ошибка получения имени",
                        Toast.LENGTH_LONG).show()
                    Logger.getLogger(mainMenuFragment::class.java.name)
                        .log(Level.SEVERE, "User NameSurname Error", t)
                }
            })

        userStatisticApi.getUserStatistic(Utilities.USER_LOGIN)
            .enqueue(object : retrofit2.Callback<UserStatistic> {
                override fun onResponse(
                    call: Call<UserStatistic>,
                    response: Response<UserStatistic>
                ) {
                    if (response.isSuccessful) {
                        val userStatistic = response.body()

                        currentCoinsCountTextView.text = userStatistic?.currentCoinsCount.toString()
                        Utilities.USER_CASH = userStatistic?.currentCoinsCount!!
                        totalGamesPlayedTextView.text = userStatistic?.totalGamesPlayed.toString()
                        winGamesTextView.text = userStatistic?.winGames.toString()
                        totalEarnTextView.text = userStatistic?.totalEarn.toString()
                    }
                }

                override fun onFailure(call: Call<UserStatistic>, t: Throwable) {
                    Toast.makeText(
                        mainMenuFragment.context,
                        "Ошибка получения статистики",
                        Toast.LENGTH_LONG).show()
                    Logger.getLogger(mainMenuFragment::class.java.name)
                        .log(Level.SEVERE, "User Statistic Error", t)
                }
            })



        view.findViewById<LinearLayout>(R.id.findGameLinearLayout).setOnClickListener {
            val user = User()
            user.login = Utilities.USER_LOGIN

            val dialogBuilder = AlertDialog.Builder(activity)
            dialogBuilder.setTitle("Введите код комнаты")

            val roomCodeEditText = EditText(activity)
            roomCodeEditText.inputType = InputType.TYPE_CLASS_NUMBER
            dialogBuilder.setView(roomCodeEditText)
            dialogBuilder.setPositiveButton("OK", object: DialogInterface.OnClickListener {
                override fun onClick(dialogInterface: DialogInterface?, i: Int) {
                    Utilities.currentMainMenuView.findNavController().navigate(
                        R.id.action_mainMenuFragment_to_roomFragment
                    )
                    Utilities.currentRoomCode = Integer.parseInt(roomCodeEditText.text.toString())
                    joinRoom(Utilities.currentRoomCode, user)
                }
            })

            dialogBuilder.show()
        }

        view.findViewById<LinearLayout>(R.id.createGameLinearLayout).setOnClickListener {

        }

    }

    @SuppressLint("NewApi")
    override fun onDestroy() {
        super.onDestroy()

        val disconnectMessage: SocketMessage = SocketMessage(
            MessageType.SOCKET_DISCONNECT,
            MessageContent(),
            Utilities.USER_LOGIN,
            LocalDateTime.now(),
            Utilities.HOST_ADDRESS
        )

        Utilities.webSocketViewModel!!.sendMessage(disconnectMessage)
        Utilities.webSocketViewModel = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment MainMenuFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            MainMenuFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    @SuppressLint("NewApi")
    private fun joinRoom(roomCode: Int, user: User) {
        val socketMessage = SocketMessage(
            MessageType.PLAYER_ROOM_JOIN,
            MessageContent(
                roomCode,
                user.login
            ),
            user.login,
            LocalDateTime.now(),
            Utilities.HOST_ADDRESS
        )
        Utilities.webSocketViewModel!!.sendMessage(socketMessage)
    }

    @Subscribe
    fun handleRealTimeMessage(event: RealTimeEvent?) {
        // processing of all real-time events
    }
}