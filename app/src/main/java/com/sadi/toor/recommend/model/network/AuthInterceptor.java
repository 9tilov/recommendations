package com.sadi.toor.recommend.model.network;

import com.sadi.toor.recommend.model.data.preferences.PreferenceRepo;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    private PreferenceRepo preferenceRepo;

    @Inject
    public AuthInterceptor(PreferenceRepo preferenceRepo) {
        this.preferenceRepo = preferenceRepo;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request().newBuilder()
                .addHeader("Custom-Token", preferenceRepo.getUserToken())
                .build();
        return chain.proceed(request);
    }
}
