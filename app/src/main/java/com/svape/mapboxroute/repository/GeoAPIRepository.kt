package com.svape.mapboxroute.repository

import com.svape.mapboxroute.data.database.dao.saveLocationDao
import com.svape.mapboxroute.data.remote.service.GeoAPI
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject


@ActivityScoped
class GeoAPIRepository @Inject constructor(
    private val geoAPI: GeoAPI,
    private val saveLocationDao: saveLocationDao
) {
    fun getGeoApi() = geoAPI.getGeo()

    fun getGeoDatabase() = geoAPI.getGeo()
}