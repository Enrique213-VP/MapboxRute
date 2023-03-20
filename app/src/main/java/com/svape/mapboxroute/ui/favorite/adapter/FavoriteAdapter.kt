package com.svape.mapboxroute.ui.favorite.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.svape.mapboxroute.R
import com.svape.mapboxroute.data.model.SaveSQLite
import com.svape.mapboxroute.databinding.CardLayoutBinding
import kotlin.math.round

class FavoriteAdapter(
    list: List<SaveSQLite>,
    clickFav: OnItemClickListener
) : RecyclerView.Adapter<FavoriteAdapter.Holder>() {

    var listLocations = list
    var click: OnItemClickListener = clickFav

    interface OnItemClickListener {
        fun onItemClick(fav: SaveSQLite)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = listLocations.size

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val bindingHolder = CardLayoutBinding.bind(view)

        fun onBind(position: Int) {
            bindingHolder.apply {
                latLocation.text = round(listLocations[position].latitude).toString()
                longLocation.text = round(listLocations[position].longitude).toString()
                nameLocation.text = listLocations[position].name
                locationP.setOnClickListener { click.onItemClick(listLocations[position]) }
            }
        }
    }
}