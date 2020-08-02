package com.grigorieva.loftmoney.remote;

import com.grigorieva.loftmoney.cells.money.MoneyCellModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface MoneyApi {

    @GET("auth")
    Call<MoneyResponse> auth(@Query("social_user_id") String userId);

    @GET("items")
    Call<List<MoneyCellModel>> getItems(@Query("type") String type, @Query("auth-token") String token);

    @POST("items/add")
    Call<MoneyResponse> addItem(@Body MoneyItem request, @Query("auth-token") String token);
}
