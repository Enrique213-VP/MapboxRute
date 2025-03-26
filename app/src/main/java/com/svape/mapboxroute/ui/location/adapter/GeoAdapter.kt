package com.svape.mapboxroute.ui.location.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.svape.mapboxroute.core.BaseViewHolder
import com.svape.mapboxroute.data.model.Features
import com.svape.mapboxroute.databinding.CardLayoutLocationBinding
import java.util.Locale

class GeoAdapter(
    private val originalList: List<Features>,
    private val itemClickListener: OnGeoClickListener
) : RecyclerView.Adapter<GeoAdapter.GeoViewHolder>(), Filterable {

    private var filteredList: List<Features> = originalList

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
            itemClickListener.onGeoClick(filteredList[position])
        }

        return holder
    }

    override fun getItemCount(): Int = filteredList.size

    override fun onBindViewHolder(holder: GeoViewHolder, position: Int) {
        when (holder) {
            is GeoViewHolder -> holder.bind(filteredList[position])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString().lowercase(Locale.ROOT)
                val resultList = if (charSearch.isEmpty()) {
                    originalList
                } else {
                    originalList.filter { location ->
                        location.properties?.name?.lowercase(Locale.ROOT)?.contains(charSearch) == true ||
                                location.properties?.adm0name?.lowercase(Locale.ROOT)?.contains(charSearch) == true
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = resultList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as List<Features>
                notifyDataSetChanged()
            }
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