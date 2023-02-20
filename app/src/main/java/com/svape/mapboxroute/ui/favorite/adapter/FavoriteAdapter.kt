package com.svape.mapboxroute.ui.favorite.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.svape.mapboxroute.R
import com.svape.mapboxroute.data.model.saveLocation
import javax.inject.Inject

class FavoriteAdapter @Inject constructor (
    var context: Context,
    var points: MutableList<saveLocation>
        ): RecyclerView.Adapter<FavoriteAdapter.Holder>() {


            inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
                lateinit var name: TextView
                lateinit var lat: TextView
                lateinit var long: TextView

                init {
                    name = itemView.findViewById(R.id.nameLocation)
                    lat = itemView.findViewById(R.id.longLatLocation)
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var inflater = LayoutInflater.from(context).inflate(R.layout.card_layout, parent, false)
        return Holder(inflater)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        var location = points[position]
        holder.name.text = location.name
        holder.lat.text = location.lat

    }

    override fun getItemCount(): Int = points.size
}