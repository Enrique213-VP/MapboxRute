package com.svape.mapboxroute.data.remote.service

import com.svape.mapboxroute.core.Constants
import com.svape.mapboxroute.data.model.GeoJson
import retrofit2.Call
import retrofit2.http.GET

interface GeoAPI {
    @GET(Constants.ENDPOINT)
    fun getGeo(): Call<GeoJson.Feature>
}