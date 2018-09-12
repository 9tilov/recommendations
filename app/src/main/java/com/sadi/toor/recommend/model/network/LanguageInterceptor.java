package com.sadi.toor.recommend.model.network;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LanguageInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("lan", Locale.getDefault().getLanguage())
                .build();
        return chain.proceed(request);
    }
}