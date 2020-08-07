package com.grigorieva.loftmoney.remote;

import retrofit2.http.GET;
import retrofit2.http.Query;
import io.reactivex.Single;

public interface AuthApi {
    @GET("./auth")
    Single<AuthResponse> performLogin(@Query("social_user_id") String socialUserId);
}