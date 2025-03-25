package com.svape.mapboxroute.ui.location.fragmentlocation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.svape.mapboxroute.R
import com.svape.mapboxroute.core.Resource
import com.svape.mapboxroute.data.model.Features
import com.svape.mapboxroute.data.remote.GeoDataSource
import com.svape.mapboxroute.databinding.FragmentLocationBinding
import com.svape.mapboxroute.presentation.GeoViewModel
import com.svape.mapboxroute.presentation.GeoViewModelFactory
import com.svape.mapboxroute.repository.GeoRepositoryImp
import com.svape.mapboxroute.repository.RetrofitClient
import com.svape.mapboxroute.ui.location.adapter.GeoAdapter

class LocationFragment : Fragment(R.layout.fragment_location), GeoAdapter.OnGeoClickListener {

    private lateinit var binding: FragmentLocationBinding
    private val viewModel by viewModels<GeoViewModel> {
        GeoViewModelFactory(GeoRepositoryImp(GeoDataSource(RetrofitClient.webService)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationBinding.bind(view)

        viewModel.fetchGeoJson().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    binding.rvGeoJson.adapter =
                        GeoAdapter(result.data.features, this@LocationFragment)
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("DataFind", "${result.exception}")
                }
            }
        })
    }

    override fun onGeoClick(geo: Features) {
        // Verificar si los datos son nulos
        Log.d("LocationFragment", "Geo clicked: $geo")

        // Obtener coordenadas directamente de properties
        val longitude = geo.properties.longitude
        val latitude = geo.properties.latitude
        val name = geo.properties.name

        Log.d("LocationFragment", "Longitude: $longitude, Latitude: $latitude, Name: $name")

        // Crear un intent para devolver a MainActivity con los detalles de ubicación
        val returnIntent = Intent().apply {
            putExtra("longitude", longitude)
            putExtra("latitude", latitude)
            putExtra("name", name)
        }

        // Establecer el resultado y finalizar la actividad
        requireActivity().setResult(RESULT_OK, returnIntent)
        requireActivity().finish()
    }
}