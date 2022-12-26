package com.example.polypoker.retrofit;

import com.example.polypoker.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserApi {

    @GET("/user/get-all")
    Call<List<User>> getAllUsers();

    @GET("user/{login}")
    Call<User> getUserByLogin(@Path("login") String login);

    @POST("/user/save")
    Call<User> save(@Body User user);

}
