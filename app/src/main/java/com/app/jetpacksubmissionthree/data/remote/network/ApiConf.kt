package com.app.jetpacksubmissionthree.data.remote.network

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConf {

    companion object {
        const val baseUrl = "https://api.themoviedb.org/3/"
        fun getApiServiceInstance(): Retrofit {
            val interceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS)
            val client = OkHttpClient.Builder().apply {
                addInterceptor(interceptor)
                connectTimeout(20, TimeUnit.SECONDS)
                readTimeout(20, TimeUnit.SECONDS)
                writeTimeout(20, TimeUnit.SECONDS)
            }.build()
            return Retrofit.Builder().apply {
                baseUrl(baseUrl)
                addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                client(client)
            }.build()
        }
    }

}