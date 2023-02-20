package com.svape.mapboxroute.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.svape.mapboxroute.R
import com.svape.mapboxroute.data.model.saveLocation
import com.svape.mapboxroute.databinding.ActivityFavoritePlacesBinding
import com.svape.mapboxroute.ui.favorite.adapter.FavoriteAdapter

class FavoritePlacesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritePlacesBinding
    private var listPoints: MutableList<saveLocation> = mutableListOf()
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritePlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val namePlaces = intent.getStringArrayListExtra("LIST_FAV_NAME")
        val locationPlaces = intent.getStringArrayListExtra("LIST_FAV_POSITION")
        adapter()

        listPoints.add(saveLocation(namePlaces.toString(), locationPlaces.toString()))
        listPoints.add(saveLocation(namePlaces.toString(), locationPlaces.toString()))
    }

    private fun adapter(){
        recyclerView = binding.recyclerSaveLocation
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FavoriteAdapter(this, listPoints)
    }
}