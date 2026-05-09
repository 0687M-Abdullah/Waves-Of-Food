package com.example.wavesoffood.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.DetailsActivity
import com.example.wavesoffood.databinding.PopularItemBinding

class PopularItemsAdapter(
    private val items: List<String>,
    private val prices: List<String>,
    private val images: List<Int>
) : RecyclerView.Adapter<PopularItemsAdapter.PopularViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularViewHolder {
        return PopularViewHolder(
            PopularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

    class PopularViewHolder(private val binding: PopularItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        
        init {
            binding.root.setOnClickListener {
                val context = binding.root.context
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val intent = Intent(context, DetailsActivity::class.java)
                    // Note: Here we'd ideally pass data. In this simple adapter structure, 
                    // we need to access the lists. We'll rely on the parent adapter or pass data via bind.
                }
            }
        }

        fun bind(item: String, images: Int, price: String) {
            binding.foodName.text = item
            binding.foodPrice.text = price
            binding.foodImage.setImageResource(images)
            
            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailsActivity::class.java)
                intent.putExtra("MenuItemName", item)
                intent.putExtra("MenuItemImage", images)
                binding.root.context.startActivity(intent)
            }
        }
    }
}
