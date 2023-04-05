package com.svape.mapboxroute.repository

import com.svape.mapboxroute.data.model.GeoJson
import com.svape.mapboxroute.data.remote.GeoDataSource

class GeoRepositoryImp(private val dataSource: GeoDataSource) : GeoRepository {
    override suspend fun getGeoJson(): GeoJson = dataSource.getGeoJson()
}