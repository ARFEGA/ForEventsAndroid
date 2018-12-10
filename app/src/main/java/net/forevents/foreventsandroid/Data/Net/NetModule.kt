package com.forevents.AndroidForEvents.Data.Net


import android.content.Context
import net.forevents.foreventsandroid.Util.getFromPreferenceManagerTypeString
import net.forevents.foreventsandroid.BuildConfig

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class NetModule (val context:Context) {

    fun provideOkhttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                //Lo que quiero que me imprima
                level= HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("token", getFromPreferenceManagerTypeString(context,"TOKEN"))
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.URL_API)
            .client(okHttpClient)
            .build()
}