package com.dds.loftmoney;

import android.app.Application;


import com.dds.loftmoney.utils.faces.web.IWebAuthorization;
import com.dds.loftmoney.utils.faces.web.IWebMoneyApi;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoftMoney extends Application {
    private IWebMoneyApi moneyApi;
    private IWebAuthorization authApi;
    private static LoftMoney loftMoney;

    public IWebAuthorization getAuthApi() {return authApi;}
    public IWebMoneyApi getMoneyApi() {
        return moneyApi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loftMoney = this;
        configApp();
    }

    public static LoftMoney GetInstance(){
        return loftMoney;
    }

    private void configApp(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://loftschool.com/android-api/basic/v1/").build();

        moneyApi = retrofit.create(IWebMoneyApi.class);
        authApi = retrofit.create(IWebAuthorization.class);
    }
}

