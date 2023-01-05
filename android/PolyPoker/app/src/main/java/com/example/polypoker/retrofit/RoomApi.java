package com.example.polypoker.retrofit;

import com.example.polypoker.model.Room;
import com.example.polypoker.model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RoomApi {

    @POST("/create-room")
    Call<Boolean> createRoom(@Body Room room);

    @POST("/join-room/{roomCode}")
    Call<Boolean> joinRoom(@Path("roomCode") Integer roomCode, @Body User user);
}
