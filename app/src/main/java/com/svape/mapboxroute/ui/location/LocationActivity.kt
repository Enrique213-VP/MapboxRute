package com.svape.mapboxroute.ui.location

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.svape.mapboxroute.data.model.GeoJson
import com.svape.mapboxroute.databinding.ActivityLocationBinding
import com.svape.mapboxroute.repository.GeoAPIRepository
import com.svape.mapboxroute.ui.location.adapter.LocationAdapter
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@AndroidEntryPoint
class LocationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLocationBinding

    @Inject
    lateinit var geoAPIRepository: GeoAPIRepository

    @Inject
    lateinit var locationAdapter: LocationAdapter

    val TAG = "LocationActivity"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            progressLocation.visibility = View.VISIBLE
            geoAPIRepository.getGeoApi().enqueue(object : Callback<GeoJson.Feature> {
                override fun onResponse(
                    call: Call<GeoJson.Feature>,
                    response: Response<GeoJson.Feature>
                ) {
                    progressLocation.visibility = View.VISIBLE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            progressLocation.visibility = View.GONE
                            response.body()?.let { itBody ->
                                itBody.properties.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        locationAdapter.differ.submitList(itData)
                                        //Recycler
                                        recyclerLocation.apply {
                                            layoutManager =
                                                LinearLayoutManager(this@LocationActivity)
                                            adapter = locationAdapter
                                        }
                                    }
                                }
                            }
                        }
                        in 300..399 -> {
                            Log.d("Response Code", " Redirection messages : ${response.code()}")
                        }
                        in 400..499 -> {
                            Log.d("Response Code", " Client error responses : ${response.code()}")
                        }
                        in 500..599 -> {
                            Log.d("Response Code", " Server error responses : ${response.code()}")
                        }
                    }
                }

                override fun onFailure(call: Call<GeoJson.Feature>, t: Throwable) {
                    progressLocation.visibility = View.GONE
                    Log.d(TAG, t.message.toString())
                }

            })
        }
    }
}