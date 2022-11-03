package com.example.newsapp.api

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log


class ApiManager {

    companion object{
        private var retrofit:Retrofit? = null;

        private fun getInstance():Retrofit{
            if (retrofit == null){

                val logging = HttpLoggingInterceptor(
                    HttpLoggingInterceptor.Logger {
                        Log.e("api",it)
                    }
                )
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                val client = OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
                retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!
        }

        fun getApis():WepServices{
            return getInstance().create(WepServices::class.java)
        }

    }
}