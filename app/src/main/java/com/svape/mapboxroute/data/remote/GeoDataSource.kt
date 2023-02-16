package com.svape.mapboxroute.data.remote

import com.svape.mapboxroute.data.model.GeoJson
import com.svape.mapboxroute.data.remote.service.GeoAPI
import javax.inject.Inject

class GeoDataSource @Inject constructor(
 private val geo: GeoAPI
) {
    suspend fun getGeo(): List<GeoJson> {
        return geo.getGeo()
    }
}