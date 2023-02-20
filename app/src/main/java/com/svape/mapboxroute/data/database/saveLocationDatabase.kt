package com.svape.mapboxroute.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.svape.mapboxroute.data.database.dao.saveLocationDao
import com.svape.mapboxroute.data.database.entities.saveLocationEntity

@Database(entities = [saveLocationEntity::class], version = 1)
abstract class saveLocationDatabase: RoomDatabase() {

    abstract fun getSaveLocationDao(): saveLocationDao
}