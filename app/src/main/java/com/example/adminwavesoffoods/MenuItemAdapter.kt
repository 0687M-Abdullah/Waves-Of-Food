package com.example.adminwavesoffoods

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.adminwavesoffoods.databinding.ItemItemBinding
import com.example.adminwavesoffoods.model.AllMenu

class MenuItemAdapter(
    private val context: Context,
    private val menuList: MutableList<AllMenu>,
    private val onDeleteClickListener: (position: Int) -> Unit,
    private val onQuantityChangeListener: (position: Int, quantity: Int) -> Unit
) : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = ItemItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int = menuList.size

    inner class MenuItemViewHolder(private val binding: ItemItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(position: Int) {
            binding.apply {
                val menuItem = menuList[position]
                val quantity = menuItem.foodQuantity ?: 1

                menuFoodName.text = menuItem.foodName
                menuPrice.text = menuItem.foodPrice
                
                // Load image using Glide
                Glide.with(context)
                    .load(menuItem.foodImage)
                    .centerCrop()
                    .placeholder(R.drawable.foodimage)
                    .into(menuImage)
                
                menuQuantity.text = quantity.toString()

                minusButton.setOnClickListener {
                    handleQuantity(false, bindingAdapterPosition)
                }
                plusButton.setOnClickListener {
                    handleQuantity(true, bindingAdapterPosition)
                }
                deleteButton.setOnClickListener {
                    val currentPosition = bindingAdapterPosition
                    if (currentPosition != RecyclerView.NO_POSITION) {
                        onDeleteClickListener(currentPosition)
                    }
                }
            }
        }

        private fun handleQuantity(isIncrement: Boolean, position: Int) {
            if (position != RecyclerView.NO_POSITION) {
                val menuItem = menuList[position]
                var currentQuantity = menuItem.foodQuantity ?: 1
                
                if (isIncrement) {
                    if (currentQuantity < 20) {
                        currentQuantity++
                        onQuantityChangeListener(position, currentQuantity)
                    }
                } else {
                    if (currentQuantity > 1) {
                        currentQuantity--
                        onQuantityChangeListener(position, currentQuantity)
                    }
                }
            }
        }
    }

    fun removeItem(position: Int) {
        if (position in 0 until menuList.size) {
            menuList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, menuList.size)
        }
    }
    
    fun updateQuantity(position: Int, newQuantity: Int) {
        if (position in 0 until menuList.size) {
            menuList[position] = menuList[position].copy(foodQuantity = newQuantity)
            notifyItemChanged(position)
        }
    }
}
