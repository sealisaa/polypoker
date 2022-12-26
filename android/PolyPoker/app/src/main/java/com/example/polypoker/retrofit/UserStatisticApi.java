package com.example.polypoker.retrofit;

import com.example.polypoker.model.UserStatistic;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserStatisticApi {

    @GET("/user/{userLogin}/get-statistic")
    Call<UserStatistic> getUserStatistic(
            @Path("userLogin") String userLogin
    );

    @POST("/user/{userLogin}/save-statistic")
    Call<UserStatistic> saveUserStatistic(
            @Path("userLogin") String userLogin,
            @Body UserStatistic userStatistic
    );
}
