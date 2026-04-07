package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.PopularItemBinding

class PopularItemsAdapter (private val items:List<String>, private val prices:List<String>, private val images:List<Int>) : RecyclerView.Adapter<PopularItemsAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(PopularItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun onBindViewHolder(holder: PopularViewHolder, position: Int) {
        val item = items[position]
        val image = images[position]
        val price = prices[position]
        holder.bind(item, image, price)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class PopularViewHolder (private val binding : PopularItemBinding) : RecyclerView.ViewHolder(binding.root){
        private val imagesView = binding.foodImage
        fun bind(item:String, images: Int, price:String){
            binding.foodName.text = item
            binding.foodPrice.text = price
            imagesView.setImageResource(images)
        }
    }

}