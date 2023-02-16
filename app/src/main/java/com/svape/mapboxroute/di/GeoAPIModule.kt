package com.svape.mapboxroute.di

import com.google.gson.GsonBuilder
import com.svape.mapboxroute.core.Constants
import com.svape.mapboxroute.data.remote.service.GeoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoAPIModule {
    private val interceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    private val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

    @Provides
    @Singleton
    fun provideAPI(builder: Retrofit.Builder) : GeoAPI {
        return builder
            .build()
            .create(GeoAPI::class.java)
    }

    @Provides
    @Singleton
    fun providerRetrofit(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(Constants.URL_API)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .client(client)
    }
}