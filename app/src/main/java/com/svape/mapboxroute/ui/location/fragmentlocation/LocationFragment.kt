package com.svape.mapboxroute.ui.location.fragmentlocation

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.widget.SearchView
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
    private lateinit var geoAdapter: GeoAdapter
    private val viewModel by viewModels<GeoViewModel> {
        GeoViewModelFactory(GeoRepositoryImp(GeoDataSource(RetrofitClient.webService)))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentLocationBinding.bind(view)

        setupSearchView()

        viewModel.fetchGeoJson().observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Resource.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Resource.Success -> {
                    binding.progressBar.visibility = View.GONE

                    geoAdapter = GeoAdapter(result.data.features, this@LocationFragment)
                    binding.rvGeoJson.adapter = geoAdapter
                }
                is Resource.Failure -> {
                    binding.progressBar.visibility = View.GONE
                    Log.d("DataFind", "${result.exception}")
                }
            }
        })
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                geoAdapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onGeoClick(geo: Features) {
        Log.d("LocationFragment", "Geo clicked: $geo")

        val longitude = geo.properties.longitude
        val latitude = geo.properties.latitude
        val name = geo.properties.name

        Log.d("LocationFragment", "Valores enviados - Longitud: $longitude, Latitud: $latitude, Nombre: $name")

        val returnIntent = Intent().apply {
            putExtra("longitude", longitude)
            putExtra("latitude", latitude)
            putExtra("name", name)
        }

        requireActivity().setResult(RESULT_OK, returnIntent)
        Log.d("LocationFragment", "Resultado enviado a MainActivity")
        requireActivity().finish()
    }
}