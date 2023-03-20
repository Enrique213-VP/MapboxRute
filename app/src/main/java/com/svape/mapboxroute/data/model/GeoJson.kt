package com.svape.mapboxroute.data.model


import com.google.gson.annotations.SerializedName

data class GeoJson(
    @SerializedName("features")
    val features: List<Feature>,
    @SerializedName("type")
    val type: String
){
    data class Feature(
        @SerializedName("geometry")
        val geometry: List<Geometry>,
        @SerializedName("properties")
        val properties: List<Properties>,
        @SerializedName("type")
        val type: String
    ) {
        data class Properties(
            @SerializedName("adm0_a3")
            val adm0A3: String,
            @SerializedName("adm0cap")
            val adm0cap: Int,
            @SerializedName("adm0name")
            val adm0name: String,
            @SerializedName("adm1name")
            val adm1name: String,
            @SerializedName("capalt")
            val capalt: Int,
            @SerializedName("capin")
            val capin: String,
            @SerializedName("changed")
            val changed: Int,
            @SerializedName("checkme")
            val checkme: Int,
            @SerializedName("diffascii")
            val diffascii: Int,
            @SerializedName("diffnote")
            val diffnote: String,
            @SerializedName("featureclass")
            val featureclass: String,
            @SerializedName("geonameid")
            val geonameid: Int,
            @SerializedName("iso_a2")
            val isoA2: String,
            @SerializedName("labelrank")
            val labelrank: Int,
            @SerializedName("latitude")
            val latitude: Double,
            @SerializedName("longitude")
            val longitude: Double,
            @SerializedName("ls_match")
            val lsMatch: Int,
            @SerializedName("ls_name")
            val lsName: String,
            @SerializedName("megacity")
            val megacity: Int,
            @SerializedName("meganame")
            val meganame: String,
            @SerializedName("name")
            val name: String,
            @SerializedName("namealt")
            val namealt: String,
            @SerializedName("nameascii")
            val nameascii: String,
            @SerializedName("namediff")
            val namediff: Int,
            @SerializedName("namepar")
            val namepar: String,
            @SerializedName("natscale")
            val natscale: Int,
            @SerializedName("note")
            val note: String,
            @SerializedName("pop_max")
            val popMax: Int,
            @SerializedName("pop_min")
            val popMin: Int,
            @SerializedName("pop_other")
            val popOther: Int,
            @SerializedName("rank_max")
            val rankMax: Int,
            @SerializedName("rank_min")
            val rankMin: Int,
            @SerializedName("scalerank")
            val scalerank: Int,
            @SerializedName("sov0name")
            val sov0name: String,
            @SerializedName("sov_a3")
            val sovA3: String,
            @SerializedName("worldcity")
            val worldcity: Int
        )

        data class Geometry(
            @SerializedName("coordinates")
            val coordinates: List<Double>,
            @SerializedName("type")
            val type: String
        )
    }
}