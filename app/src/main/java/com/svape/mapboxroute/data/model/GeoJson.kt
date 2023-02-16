package com.svape.mapboxroute.data.model


import com.google.gson.annotations.SerializedName

data class GeoJson(
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("type")
    val type: String
)