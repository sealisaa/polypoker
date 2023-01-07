package com.example.polypoker.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import com.example.polypoker.R
import com.example.polypoker.retrofit.RetrofitService
import com.example.polypoker.retrofit.RoomApi
import com.example.polypoker.websocket.stomp.WebSocketViewModel
import com.example.polypoker.websocket.nv.SocketConnectionManager
import org.greenrobot.eventbus.Subscribe
import com.example.polypoker.websocket.nv.RealTimeEvent
import org.greenrobot.eventbus.EventBus


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

class RoomFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var socketConnectionManager: SocketConnectionManager

    private lateinit var mainViewModel: WebSocketViewModel

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
        val retrofitService = RetrofitService()
        val roomApi: RoomApi = retrofitService.retrofit.create(RoomApi::class.java)

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                TODO("Not yet implemented")
            }

        }

//        EventBus.getDefault().register(this)
//        socketConnectionManager = SocketConnectionManager()
//        socketConnectionManager.init(requireContext(), requireActivity().application)

        mainViewModel = WebSocketViewModel()

        return inflater.inflate(R.layout.room_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hidePlayerSeat(
            view.findViewById(R.id.player2Avatar), view.findViewById(R.id.player2Name),
            view.findViewById(R.id.player2ChipsIcon), view.findViewById(R.id.player2DollarSign),
            view.findViewById(R.id.player2Cash), view.findViewById(R.id.player2Card1),
            view.findViewById(R.id.player2Card2))

        hidePlayerSeat(
            view.findViewById(R.id.player3Avatar), view.findViewById(R.id.player3Name),
            view.findViewById(R.id.player3ChipsIcon), view.findViewById(R.id.player3DollarSign),
            view.findViewById(R.id.player3Cash), view.findViewById(R.id.player3Card1),
            view.findViewById(R.id.player3Card2))

        hidePlayerSeat(
            view.findViewById(R.id.player4Avatar), view.findViewById(R.id.player4Name),
            view.findViewById(R.id.player4ChipsIcon), view.findViewById(R.id.player4DollarSign),
            view.findViewById(R.id.player4Cash), view.findViewById(R.id.player4Card1),
            view.findViewById(R.id.player4Card2))

        hidePlayerSeat(
            view.findViewById(R.id.player5Avatar), view.findViewById(R.id.player5Name),
            view.findViewById(R.id.player5ChipsIcon), view.findViewById(R.id.player5DollarSign),
            view.findViewById(R.id.player5Cash), view.findViewById(R.id.player5Card1),
            view.findViewById(R.id.player5Card2))

        hidePlayerSeat(
            view.findViewById(R.id.player6Avatar), view.findViewById(R.id.player6Name),
            view.findViewById(R.id.player6ChipsIcon), view.findViewById(R.id.player6DollarSign),
            view.findViewById(R.id.player6Cash), view.findViewById(R.id.player6Card1),
            view.findViewById(R.id.player6Card2))

//        while (socketConnectionManager.getSocketConnection().clientWebSocket.connection == null ) {
//            println("NUUUULLL")
//        }
//        socketConnectionManager.getSocketConnection().clientWebSocket.connection.sendText("JOPA")

        mainViewModel.sendMessage("JOPA")
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RoomFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    fun hidePlayerSeat(playerAvatar: ImageView, playerName: TextView,
                       playerChipIcon: ImageView, playerDollarSign: TextView, playerCash: TextView,
                       playerCard1: ImageView, playerCard2: ImageView) {
        playerAvatar.visibility = View.INVISIBLE
        playerName.visibility = View.INVISIBLE
        playerChipIcon.visibility = View.INVISIBLE
        playerDollarSign.visibility = View.INVISIBLE
        playerCash.visibility = View.INVISIBLE
        playerCard1.visibility = View.INVISIBLE
        playerCard2.visibility = View.INVISIBLE
    }

    @Subscribe
    fun handleRealTimeMessage(event: RealTimeEvent?) {
        // processing of all real-time events
    }

}
