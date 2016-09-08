package com.hopop.hopop.communicators.interceptor;

import com.hopop.hopop.communicators.prefmanager.PrefManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ApiRequestInterceptor implements Interceptor {

    public static final String HEADER_AUTH = "Authorization";

    public ApiRequestInterceptor(){

    }

    public ApiRequestInterceptor(String authenticationToken) {
        authenticationToken = HEADER_AUTH;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request.Builder request = original.newBuilder();
        request.addHeader(HEADER_AUTH, PrefManager.getAuehKey());
        return chain.proceed(request.build());
    }
}