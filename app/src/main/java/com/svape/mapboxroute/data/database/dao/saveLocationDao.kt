package com.svape.mapboxroute.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.svape.mapboxroute.data.database.entities.saveLocationEntity
import com.svape.mapboxroute.data.model.saveLocation

@Dao
interface saveLocationDao {

    @Query("SELECT * FROM favTable")
    suspend fun getAllSaveLoc(): List<saveLocationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(saveLocation: List<saveLocationEntity>)
}