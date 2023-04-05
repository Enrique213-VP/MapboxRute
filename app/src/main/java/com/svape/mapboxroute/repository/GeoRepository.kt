package com.svape.mapboxroute.repository

import com.svape.mapboxroute.data.model.GeoJson

interface GeoRepository {
   suspend fun getGeoJson(): GeoJson
}