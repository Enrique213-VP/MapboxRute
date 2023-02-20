package com.svape.mapboxroute.di

import android.content.Context
import androidx.room.Room
import com.svape.mapboxroute.core.Constants.SAVE_LOCATION_NAME_DATABASE
import com.svape.mapboxroute.data.database.saveLocationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {


    @Provides
    @Singleton
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, saveLocationDatabase::class.java, SAVE_LOCATION_NAME_DATABASE)
            .build()


    @Provides
    @Singleton
    fun provideSaveLocationDao(db: saveLocationDatabase) = db.getSaveLocationDao()
}