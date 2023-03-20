package com.svape.mapboxroute.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.svape.mapboxroute.core.Constants.TABLE_FAVORITE

open class SaveLocationDatabase(context: Context) :
    SQLiteOpenHelper(context, "MapsDataBase", null, 1) {

    private val createTable = "CREATE TABLE $TABLE_FAVORITE(" +
            "name STRING NOT NULL, " +
            "longitude DOUBLE NOT NULL," +
            " latitude DOUBLE NOT NULL)";

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}