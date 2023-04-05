package com.svape.mapboxroute.ui.location.fragmentlocation

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
    private lateinit var dialogMap: AlertDialog.Builder


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
        dialogMap = AlertDialog.Builder(requireContext())
        dialogMap.setTitle("El lugar que elegiste!")
            .setMessage("Nombre: ${geo.properties.name}, longitud: ${geo.properties.longitude}")
            .setPositiveButton("Vale") { dialog, id ->
            }
            .show()
    }
}