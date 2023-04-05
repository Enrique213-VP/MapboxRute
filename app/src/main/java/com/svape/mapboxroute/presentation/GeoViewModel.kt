package com.svape.mapboxroute.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.svape.mapboxroute.core.Resource
import com.svape.mapboxroute.repository.GeoRepository
import kotlinx.coroutines.Dispatchers

class GeoViewModel(private val repo: GeoRepository): ViewModel() {
    fun fetchGeoJson() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success(repo.getGeoJson()))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
}

class GeoViewModelFactory(private val repo: GeoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(GeoRepository::class.java).newInstance(repo)
    }
}