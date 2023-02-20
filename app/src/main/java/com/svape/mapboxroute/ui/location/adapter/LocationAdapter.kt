package com.svape.mapboxroute.ui.location.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.svape.mapboxroute.data.model.GeoJson
import com.svape.mapboxroute.databinding.CardLayoutBinding
import javax.inject.Inject

class LocationAdapter @Inject constructor() : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {

    private lateinit var binding: CardLayoutBinding
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        binding = CardLayoutBinding.inflate(inflater, parent, false)
        context = parent.context
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = 1

    override fun getItemViewType(position: Int): Int = position
    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: GeoJson.Feature.Properties) {
            binding.apply {
                nameLocation.text = item.name
                longLatLocation.text = "${item.longitude}, ${item.latitude}"

            }
        }
    }

    private var onItemClickListener: ((GeoJson.Feature.Properties) -> Unit)? = null

    fun setOnItemClickListener(listener: (GeoJson.Feature.Properties) -> Unit) {
        onItemClickListener = listener
    }

    private val differCallback = object : DiffUtil.ItemCallback<GeoJson.Feature.Properties>() {
        override fun areItemsTheSame(oldItem: GeoJson.Feature.Properties, newItem: GeoJson.Feature.Properties): Boolean {
            return oldItem.geonameid == newItem.geonameid
        }

        override fun areContentsTheSame(oldItem: GeoJson.Feature.Properties, newItem: GeoJson.Feature.Properties): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}