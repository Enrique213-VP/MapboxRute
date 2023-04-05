package com.svape.mapboxroute.data.remote

import com.svape.mapboxroute.data.model.GeoJson
import com.svape.mapboxroute.repository.WebService

class GeoDataSource(private val webService: WebService) {

    suspend fun getGeoJson() : GeoJson = webService.getGeoJson()
}