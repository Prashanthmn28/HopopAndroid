package com.hopop.hopop.communicators;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hopop.hopop.communicators.interceptor.ApiRequestInterceptor;
import com.hopop.hopop.communicators.services.RegisterClass;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommunicatorClass {

    private static RegisterClass registerClass;

    public static RegisterClass getRegisterClass() {
        if (registerClass == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                   .addInterceptor(interceptor)
                    .addInterceptor(new ApiRequestInterceptor())
                    .addNetworkInterceptor(new StethoInterceptor()).build();

            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://redbeak.azurewebsites.net/php/")
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            registerClass = retrofit.create(RegisterClass.class);
        }
        return registerClass;

    }
}