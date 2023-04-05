package com.svape.mapboxroute.ui.location.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.svape.mapboxroute.core.BaseViewHolder
import com.svape.mapboxroute.data.model.Features
import com.svape.mapboxroute.databinding.CardLayoutLocationBinding

class GeoAdapter(
    private val listG: List<Features>,
    private val itemClickListener: OnGeoClickListener
) : RecyclerView.Adapter<GeoAdapter.GeoViewHolder>() {

    interface OnGeoClickListener {
        fun onGeoClick(geo: Features)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeoViewHolder {
        val itemBinding =
            CardLayoutLocationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = GeoViewHolder(itemBinding, parent.context)

        itemBinding.locationP.setOnClickListener {
            val position =
                holder.bindingAdapterPosition.takeIf { it != DiffUtil.DiffResult.NO_POSITION }
                    ?: return@setOnClickListener
            itemClickListener.onGeoClick(listG[position])
        }

        return holder
    }

    override fun getItemCount(): Int = listG.size

    override fun onBindViewHolder(holder: GeoViewHolder, position: Int) {
        when (holder) {
            is GeoViewHolder -> holder.bind(listG[position])
        }
    }


    inner class GeoViewHolder(
        val binding: CardLayoutLocationBinding,
        val context: Context
    ) : BaseViewHolder<Features>(binding.root) {
        override fun bind(item: Features) {
            binding.nameLocation.text = item.properties?.name
            binding.CountryLocation.text = item.properties?.adm0name
        }
    }


}