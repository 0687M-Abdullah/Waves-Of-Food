package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.MenuItemsBinding

class MenuAdapter(private val menuItemsName: MutableList<String>,private val menuItemPrices: MutableList<String>,private val menuImages: MutableList<Int>): RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemsBinding.inflate(LayoutInflater.from(parent.context), parent,false)

        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {return menuItemsName.size
    }

    inner class MenuViewHolder(private val binding: MenuItemsBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                menuFoodName.text = menuItemsName[position]
                menuPrice.text = menuItemPrices[position]
                menuImage.setImageResource(menuImages[position])
            }
        }
    }
}