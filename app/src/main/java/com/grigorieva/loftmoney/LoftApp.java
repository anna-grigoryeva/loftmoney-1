package com.grigorieva.loftmoney;

import android.app.Application;

import com.grigorieva.loftmoney.remote.MoneyApi;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftApp extends Application {

    private MoneyApi moneyApi;

    public MoneyApi getMoneyApi() {
        return moneyApi;
    }


    @Override
    public void onCreate() {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://loftschool.com/android-api/basic/v1/categories")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        moneyApi = retrofit.create(MoneyApi.class);
    }

    public MoneyApi getApi() {
        return moneyApi;
    }
}