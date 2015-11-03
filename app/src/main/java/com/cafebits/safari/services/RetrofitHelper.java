package com.cafebits.safari.services;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Gilbert on 10/30/15.
 */
public class RetrofitHelper {

    final private String EXHIBITS_FEED = "http://cafebits.com";

    public SafariService safariService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(EXHIBITS_FEED)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(SafariService.class);
    }

}
