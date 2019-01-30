package com.ingic.lmslawyer.retrofit;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class WebServiceFactory {

    private static WebService webService;

    public static WebService getWebServiceInstanceWithCustomInterceptor(Context context, String endPoint) {

        /*Gson gson = new GsonBuilder()
                .setLenient()
                .create();*/
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        return retrofit.create(WebService.class);
/*
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OKHttpClientCreator.createCustomInterceptorClient(context))
                .build();

        webService = retrofit.create(WebService.class);

        return webService;*/

    }

    public static WebService getWebServiceInstanceWithCustomInterceptorandheader(Context context, String endPoint) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                //.addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(OKHttpClientCreator.createCustomInterceptorClientwithHeader(context))
                .build();

        webService = retrofit.create(WebService.class);

        return webService;

    }

    public static WebService getWebServiceInstanceWithDefaultInterceptor(Context context, String endPoint) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(OKHttpClientCreator.createDefaultInterceptorClient(context))
                .build();

        webService = retrofit.create(WebService.class);

        return webService;

    }

}
