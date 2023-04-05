package com.svape.mapboxroute.repository

import com.google.gson.GsonBuilder
import com.svape.mapboxroute.core.Constants.ENDPOINT
import com.svape.mapboxroute.core.Constants.URL_API
import com.svape.mapboxroute.data.model.GeoJson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {

    @GET(ENDPOINT)
    suspend fun getGeoJson(): GeoJson
}

object RetrofitClient {

    private val client = OkHttpClient.Builder().build()

    val webService by lazy {
        Retrofit.Builder()
            .baseUrl(URL_API)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
            .build().create(WebService::class.java)

    }
}