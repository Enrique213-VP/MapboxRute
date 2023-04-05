package com.svape.mapboxroute.data.model


import com.google.gson.annotations.SerializedName

data class GeoJson(
    @SerializedName("type") var type: String? = null,
    @SerializedName("features") var features: ArrayList<Features> = arrayListOf()
)

data class Properties(

    @SerializedName("scalerank") var scalerank: Int? = null,
    @SerializedName("natscale") var natscale: Int? = null,
    @SerializedName("labelrank") var labelrank: Int? = null,
    @SerializedName("name") var name: String = "",
    @SerializedName("namepar") var namepar: String? = null,
    @SerializedName("namealt") var namealt: String? = null,
    @SerializedName("diffascii") var diffascii: Int? = null,
    @SerializedName("nameascii") var nameascii: String? = null,
    @SerializedName("adm0cap") var adm0cap: Int? = null,
    @SerializedName("capalt") var capalt: Int? = null,
    @SerializedName("capin") var capin: String? = null,
    @SerializedName("worldcity") var worldcity: Int? = null,
    @SerializedName("megacity") var megacity: Int? = null,
    @SerializedName("sov0name") var sov0name: String? = null,
    @SerializedName("sov_a3") var sovA3: String? = null,
    @SerializedName("adm0name") var adm0name: String? = null,
    @SerializedName("adm0_a3") var adm0A3: String? = null,
    @SerializedName("adm1name") var adm1name: String? = null,
    @SerializedName("iso_a2") var isoA2: String? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("latitude") var latitude: Double = 0.0,
    @SerializedName("longitude") var longitude: Double = 0.0,
    @SerializedName("changed") var changed: Int? = null,
    @SerializedName("namediff") var namediff: Int? = null,
    @SerializedName("diffnote") var diffnote: String? = null,
    @SerializedName("pop_max") var popMax: Int? = null,
    @SerializedName("pop_min") var popMin: Int? = null,
    @SerializedName("pop_other") var popOther: Int? = null,
    @SerializedName("rank_max") var rankMax: Int? = null,
    @SerializedName("rank_min") var rankMin: Int? = null,
    @SerializedName("geonameid") var geonameid: Int? = null,
    @SerializedName("meganame") var meganame: String? = null,
    @SerializedName("ls_name") var lsName: String? = null,
    @SerializedName("ls_match") var lsMatch: Int? = null,
    @SerializedName("checkme") var checkme: Int? = null,
    @SerializedName("featureclass") var featureclass: String? = null

)

data class Geometry(

    @SerializedName("type") var type: String? = null,
    @SerializedName("coordinates") var coordinates: ArrayList<Double> = arrayListOf()

)

data class Features(

    @SerializedName("type") var type: String? = null,
    @SerializedName("properties") var properties: Properties = Properties(),
    @SerializedName("geometry") var geometry: Geometry? = Geometry()

)