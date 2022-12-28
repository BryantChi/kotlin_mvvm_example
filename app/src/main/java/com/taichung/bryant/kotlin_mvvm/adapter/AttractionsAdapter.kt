package com.taichung.bryant.kotlin_mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.taichung.bryant.kotlin_mvvm.R
import com.taichung.bryant.kotlin_mvvm.data.attractions.AttractionsData
import com.taichung.bryant.kotlin_mvvm.databinding.ItemAttractionsBinding
import com.taichung.bryant.kotlin_mvvm.listeners.ItemClickListener

class AttractionsAdapter(var context: Context, var itemClick: ItemClickListener) :
    RecyclerView.Adapter<AttractionsAdapter.Holder>() {

    var attractionsList = mutableListOf<AttractionsData>()

    fun submitList(itemAttractions: List<AttractionsData>) {
        this.attractionsList.clear()
        this.attractionsList.addAll(itemAttractions)
        notifyDataSetChanged()
    }

    fun updateList(itemAttractions: List<AttractionsData>) {
        this.attractionsList.addAll(itemAttractions)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemAttractionsBinding.inflate(layoutInflater, parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(attractionsList[position])
    }

    override fun getItemCount(): Int {
        return attractionsList.size
    }

    inner class Holder(private val binding: ItemAttractionsBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(attractionsData: AttractionsData) {
            binding.data = attractionsData
            binding.imAttractions.load(attractionsList[position].images.getOrNull(0)?.src) {
                crossfade(true)
                placeholder(R.drawable.image_defult)
                error(R.drawable.image_defult)
                transformations(RoundedCornersTransformation(10f))
            }
        }

        init {
            itemView.setOnClickListener {
                itemClick.itemClick(attractionsList[position])
            }
        }
    }
}
