package com.example.chinesehelpchinese

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object ServiceCreator {

    private const val BASE_URL="http://10.0.2.2:8000/v1/"

    private val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()

    fun <T> create(serviceClass:Class<T>):T= retrofit.create(serviceClass)

}