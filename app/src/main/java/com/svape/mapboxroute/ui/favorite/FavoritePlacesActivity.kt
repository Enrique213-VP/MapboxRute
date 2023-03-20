package com.svape.mapboxroute.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.svape.mapboxroute.data.database.entitie.SaveLocationEntity
import com.svape.mapboxroute.data.model.SaveSQLite
import com.svape.mapboxroute.databinding.ActivityFavoritePlacesBinding
import com.svape.mapboxroute.ui.favorite.adapter.FavoriteAdapter

class FavoritePlacesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoritePlacesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritePlacesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val dataBase = SaveLocationEntity(this@FavoritePlacesActivity)
        val listFav = dataBase.showLocation()

        var linear = LinearLayoutManager(
            this,
            RecyclerView.VERTICAL, false
        )
        var adapter = FavoriteAdapter(listFav, object : FavoriteAdapter.OnItemClickListener {
            override fun onItemClick(itemClick: SaveSQLite) {
                place(itemClick)
            }
        })

        binding.apply {
            recyclerSaveLocation.adapter = adapter
            recyclerSaveLocation.layoutManager = linear
            recyclerSaveLocation.addItemDecoration(
                DividerItemDecoration(
                    this@FavoritePlacesActivity,
                    linear.orientation
                )
            )
        }
    }


    fun place(itemclick : SaveSQLite){
        val intent = Intent()
        intent.putExtra("longitude",itemclick.longitude)
        intent.putExtra("latitude", itemclick.latitude)
        intent.putExtra("name", itemclick.name)
        setResult(RESULT_OK,intent)
        finish()
    }

}