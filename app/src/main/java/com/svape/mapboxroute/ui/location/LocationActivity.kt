package com.svape.mapboxroute.ui.location

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.svape.mapboxroute.core.Constants.URL_API_T
import com.svape.mapboxroute.data.model.GeoJson
import com.svape.mapboxroute.data.remote.service.GeoAPI
import com.svape.mapboxroute.databinding.ActivityLocationBinding
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
    lateinit var apiRepository: GeoAPI

    @Inject
    lateinit var adapter: LocationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            progressLocation.visibility = View.VISIBLE
            apiRepository.getGeo().enqueue(object : Callback<GeoJson.Feature> {
                override fun onResponse(
                    call: Call<GeoJson.Feature>,
                    response: Response<GeoJson.Feature>
                ) {
                    progressLocation.visibility = View.VISIBLE
                    when (response.code()) {
                        in 200..299 -> {
                            Log.d("Response Code", " success messages : ${response.code()}")
                            progressLocation.visibility=View.GONE
                            response.body()?.let { itBody ->
                                itBody.properties.let { itData ->
                                    if (itData.isNotEmpty()) {
                                        adapter.differ.submitList(itData)
                                        //Recycler
                                        recyclerLocation.apply {
                                            layoutManager = LinearLayoutManager(this@LocationActivity)
                                            adapter = adapter
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
                    progressLocation.visibility=View.GONE
                    Log.d(TAG,t.message.toString())
                }

            })
        }

    }
}