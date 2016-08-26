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
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request original = chain.request();
        Request.Builder request = original.newBuilder();
        request.addHeader(HEADER_AUTH, PrefManager.getAuehKey());
        return chain.proceed(request.build());
    }


  /*  public OkHttpClient getChain() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request.Builder request = original.newBuilder()
                        .header(HEADER_TENANT, PrefManager.getTenant())
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .method(original.method(), original.body());
                if(PrefManager.isAuthenticated())
                    request.header(HEADER_AUTH, PrefManager.getToken());
                return chain.proceed(request.build());
            }
        });
        return httpClient.build();
    }*/

}