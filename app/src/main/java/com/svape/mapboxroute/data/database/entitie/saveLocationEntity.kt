package com.svape.mapboxroute.data.database.entitie

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.svape.mapboxroute.data.database.SaveLocationDatabase
import com.svape.mapboxroute.core.Constants.TABLE_FAVORITE
import com.svape.mapboxroute.data.model.SaveSQLite

data class SaveLocationEntity(val context: Context) : SaveLocationDatabase(context) {
    fun insertLocation(name: String, longitude: Double, latitude: Double) {
        val db = SaveLocationDatabase(context)
        val sqLite = db.writableDatabase
        val values = ContentValues()
        values.put("name", name)
        values.put("longitude", longitude)
        values.put("latitude", latitude)

        sqLite.insert(TABLE_FAVORITE, null, values)
    }

    fun showLocation(): ArrayList<SaveSQLite> {
        val db = SaveLocationDatabase(context)
        val sqLite = db.writableDatabase

        val favPlaces = ArrayList<SaveSQLite>()
        var location: SaveSQLite

        val cursor: Cursor = sqLite.rawQuery("SELECT * FROM $TABLE_FAVORITE", null)
        if (cursor.moveToFirst()) {
            do {
                location = SaveSQLite(
                    cursor.getString(0),
                    cursor.getDouble(1),
                    cursor.getDouble(2)
                )
                favPlaces.add(location)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return favPlaces
    }
}