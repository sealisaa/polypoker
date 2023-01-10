package com.example.polypoker.model;

import android.view.View;
import android.widget.TextView;

import com.example.polypoker.R;
import com.example.polypoker.Utilities;
import com.example.polypoker.websocket.stomp.SocketMessage;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private int roomCode;
    private int minBlind;
    private int minRaise;
    private Map<String, Player> playersMap;

    public Room(int roomCode, int minBlind, int minRaise) {
        this.roomCode = roomCode;
        this.minBlind = minBlind;
        this.minRaise = minRaise;
        playersMap = new HashMap<>();
        GameManager.playersMap = playersMap;
    }

    public void addFirstPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.put(
                message.getContent().getUserLogin(),
                new Player(
                        message.getContent().getUserLogin(),
                        message.getContent().getUserName(),
                        message.getContent().getMoneyValue(),
                        0,
                        null,
                        null,
                        Utilities.currentRoomView.findViewById(R.id.player1Card1),
                        Utilities.currentRoomView.findViewById(R.id.player1Card2),
                        Utilities.currentRoomView.findViewById(R.id.player1Cash)
                        ));
    }

    public void addSecondPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.put(
                message.getContent().getUserLogin(),
                new Player(
                        message.getContent().getUserLogin(),
                        message.getContent().getUserName(),
                        message.getContent().getMoneyValue(),
                        0,
                        null,
                        null,
                        Utilities.currentRoomView.findViewById(R.id.player2Card1),
                        Utilities.currentRoomView.findViewById(R.id.player2Card2),
                        Utilities.currentRoomView.findViewById(R.id.player2Cash)
                ));


        TextView playerName = Utilities.currentRoomView.findViewById(R.id.player2Name);
        playerName.setText(message.getContent().getUserName());

        TextView playerCash = Utilities.currentRoomView.findViewById(R.id.player2Cash);
        playerCash.setText(message.getContent().getMoneyValue().toString());

        Utilities.currentRoomView.findViewById(R.id.player2Avatar).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2ChipsIcon).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2DollarSign).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Name).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Card1).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Card2).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Cash).setVisibility(View.VISIBLE);
    }

    public void addThirdPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.put(
                message.getContent().getUserLogin(),
                new Player(
                        message.getContent().getUserLogin(),
                        message.getContent().getUserName(),
                        message.getContent().getMoneyValue(),
                        0,
                        null,
                        null,
                        Utilities.currentRoomView.findViewById(R.id.player3Card1),
                        Utilities.currentRoomView.findViewById(R.id.player3Card2),
                        Utilities.currentRoomView.findViewById(R.id.player3Cash)
                ));

        TextView playerName = Utilities.currentRoomView.findViewById(R.id.player3Name);
        playerName.setText(message.getContent().getUserName());

        TextView playerCash = Utilities.currentRoomView.findViewById(R.id.player3Cash);
        playerCash.setText(message.getContent().getMoneyValue().toString());

        Utilities.currentRoomView.findViewById(R.id.player3Avatar).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3ChipsIcon).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3DollarSign).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Name).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Card1).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Card2).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Cash).setVisibility(View.VISIBLE);

    }

    public void addFourthPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.put(
                message.getContent().getUserLogin(),
                new Player(
                        message.getContent().getUserLogin(),
                        message.getContent().getUserName(),
                        message.getContent().getMoneyValue(),
                        0,
                        null,
                        null,
                        Utilities.currentRoomView.findViewById(R.id.player4Card1),
                        Utilities.currentRoomView.findViewById(R.id.player4Card2),
                        Utilities.currentRoomView.findViewById(R.id.player4Cash)
                ));

        TextView playerName = Utilities.currentRoomView.findViewById(R.id.player4Name);
        playerName.setText(message.getContent().getUserName());

        TextView playerCash = Utilities.currentRoomView.findViewById(R.id.player4Cash);
        playerCash.setText(message.getContent().getMoneyValue().toString());

        Utilities.currentRoomView.findViewById(R.id.player4Avatar).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4ChipsIcon).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4DollarSign).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Name).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Card1).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Card2).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Cash).setVisibility(View.VISIBLE);

    }

    public void addFifthPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.put(
                message.getContent().getUserLogin(),
                new Player(
                        message.getContent().getUserLogin(),
                        message.getContent().getUserName(),
                        message.getContent().getMoneyValue(),
                        0,
                        null,
                        null,
                        Utilities.currentRoomView.findViewById(R.id.player5Card1),
                        Utilities.currentRoomView.findViewById(R.id.player5Card2),
                        Utilities.currentRoomView.findViewById(R.id.player5Cash)
                ));

        TextView playerName = Utilities.currentRoomView.findViewById(R.id.player5Name);
        playerName.setText(message.getContent().getUserName());

        TextView playerCash = Utilities.currentRoomView.findViewById(R.id.player5Cash);
        playerCash.setText(message.getContent().getMoneyValue().toString());

        Utilities.currentRoomView.findViewById(R.id.player5Avatar).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5ChipsIcon).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5DollarSign).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Name).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Card1).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Card2).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Cash).setVisibility(View.VISIBLE);

    }

    public void addSixthPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.put(
                message.getContent().getUserLogin(),
                new Player(
                        message.getContent().getUserLogin(),
                        message.getContent().getUserName(),
                        message.getContent().getMoneyValue(),
                        0,
                        null,
                        null,
                        Utilities.currentRoomView.findViewById(R.id.player6Card1),
                        Utilities.currentRoomView.findViewById(R.id.player6Card2),
                        Utilities.currentRoomView.findViewById(R.id.player6Cash)
                ));

        TextView playerName = Utilities.currentRoomView.findViewById(R.id.player6Name);
        playerName.setText(message.getContent().getUserName());

        TextView playerCash = Utilities.currentRoomView.findViewById(R.id.player6Cash);
        playerCash.setText(message.getContent().getMoneyValue().toString());

        Utilities.currentRoomView.findViewById(R.id.player6Avatar).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6ChipsIcon).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6DollarSign).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Name).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Card1).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Card2).setVisibility(View.VISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Cash).setVisibility(View.VISIBLE);

    }

    public void deleteSecondPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.remove(message.getContent().getUserLogin());
        Utilities.currentRoomView.findViewById(R.id.player2Avatar).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2ChipsIcon).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2DollarSign).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Name).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Card1).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Card2).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player2Cash).setVisibility(View.INVISIBLE);
    }

    public void deleteThirdPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.remove(message.getContent().getUserLogin());
        Utilities.currentRoomView.findViewById(R.id.player3Avatar).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3ChipsIcon).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3DollarSign).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Name).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Card1).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Card2).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player3Cash).setVisibility(View.INVISIBLE);
    }

    public void deleteFourthPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.remove(message.getContent().getUserLogin());
        Utilities.currentRoomView.findViewById(R.id.player4Avatar).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4ChipsIcon).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4DollarSign).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Name).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Card1).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Card2).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player4Cash).setVisibility(View.INVISIBLE);
    }

    public void deleteFifthPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.remove(message.getContent().getUserLogin());
        Utilities.currentRoomView.findViewById(R.id.player5Avatar).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5ChipsIcon).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5DollarSign).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Name).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Card1).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Card2).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player5Cash).setVisibility(View.INVISIBLE);
    }


    public void deleteSixthPlayer(SocketMessage message) {
        Utilities.currentRoom.playersMap.remove(message.getContent().getUserLogin());
        Utilities.currentRoomView.findViewById(R.id.player6Avatar).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6ChipsIcon).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6DollarSign).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Name).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Card1).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Card2).setVisibility(View.INVISIBLE);
        Utilities.currentRoomView.findViewById(R.id.player6Cash).setVisibility(View.INVISIBLE);
    }


    public int getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }

    public int getMinBlind() {
        return minBlind;
    }

    public void setMinBlind(int minBlind) {
        this.minBlind = minBlind;
    }

    public int getMinRaise() {
        return minRaise;
    }

    public void setMinRaise(int minRaise) {
        this.minRaise = minRaise;
    }

    public Map<String, Player> getPlayersMap() {
        return playersMap;
    }

    public void setPlayersMap(Map<String, Player> playersMap) {
        this.playersMap = playersMap;
    }
}
