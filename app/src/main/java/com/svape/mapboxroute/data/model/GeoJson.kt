package com.svape.mapboxroute.data.model


import com.google.gson.annotations.SerializedName

data class GeoJson(
    @SerializedName("features")
    val features: List<Feature> = listOf(),
    @SerializedName("type")
    val type: String = ""
) {
    data class Feature(
        @SerializedName("properties")
        val properties: List<Properties> = listOf(),
        @SerializedName("type")
        val type: String = ""
    ) {
        data class Properties(
            @SerializedName("adm0_a3")
            val adm0A3: String = "",
            @SerializedName("adm0cap")
            val adm0cap: Int = -1,
            @SerializedName("adm0name")
            val adm0name: String = "",
            @SerializedName("adm1name")
            val adm1name: String = "",
            @SerializedName("capalt")
            val capalt: Int = -1,
            @SerializedName("capin")
            val capin: String = "",
            @SerializedName("changed")
            val changed: Int = -1,
            @SerializedName("checkme")
            val checkme: Int = -1,
            @SerializedName("diffascii")
            val diffascii: Int = -1,
            @SerializedName("diffnote")
            val diffnote: String = "",
            @SerializedName("featureclass")
            val featureclass: String = "",
            @SerializedName("geonameid")
            val geonameid: Int = -1,
            @SerializedName("iso_a2")
            val isoA2: String = "",
            @SerializedName("labelrank")
            val labelrank: Int = -1,
            @SerializedName("latitude")
            val latitude: Double = 0.0,
            @SerializedName("longitude")
            val longitude: Double = 0.0,
            @SerializedName("ls_match")
            val lsMatch: Int = -1,
            @SerializedName("ls_name")
            val lsName: String = "",
            @SerializedName("megacity")
            val megacity: Int = -1,
            @SerializedName("meganame")
            val meganame: String = "",
            @SerializedName("name")
            val name: String = "",
            @SerializedName("namealt")
            val namealt: String = "",
            @SerializedName("nameascii")
            val nameascii: String = "",
            @SerializedName("namediff")
            val namediff: Int = -1,
            @SerializedName("namepar")
            val namepar: String = "",
            @SerializedName("natscale")
            val natscale: Int = -1,
            @SerializedName("note")
            val note: String = "",
            @SerializedName("pop_max")
            val popMax: Int = -1,
            @SerializedName("pop_min")
            val popMin: Int = -1,
            @SerializedName("pop_other")
            val popOther: Int = -1,
            @SerializedName("rank_max")
            val rankMax: Int = -1,
            @SerializedName("rank_min")
            val rankMin: Int = -1,
            @SerializedName("scalerank")
            val scalerank: Int = -1,
            @SerializedName("sov0name")
            val sov0name: String = "",
            @SerializedName("sov_a3")
            val sovA3: String = "",
            @SerializedName("worldcity")
            val worldcity: Int = -1
        )
    }
}