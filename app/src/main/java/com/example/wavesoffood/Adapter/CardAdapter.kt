package com.example.wavesoffood.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wavesoffood.databinding.CartItemBinding

class CardAdapter(private val cartItems: MutableList<String>, private val cartItemPrices: MutableList<String>, private val cartImages: MutableList<Int>): RecyclerView.Adapter<CardAdapter.CartViewHolder>() {

    private val itemQuantities = IntArray(cartItems.size){1}
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = CartItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CartViewHolder(binding)

    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return cartItems.size
    }
    inner class CartViewHolder(private val binding: CartItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int){
            binding.apply {
                val quantity = itemQuantities[position]
                cartFoodName.text = cartItems[position]
                cartPrice.text = cartItemPrices[position]
                cartImage.setImageResource(cartImages[position])
                cartQuantity.text = quantity.toString()

                minusButton.setOnClickListener {
                    decreaseQuantity(position)
                }
                plusButton.setOnClickListener {
                    increaseQuantity(position)
                }
                deleteButton.setOnClickListener {
                    val itemPosition = adapterPosition
                    if (itemPosition != RecyclerView.NO_POSITION){
                        deleteItems(itemPosition)
                    }
                }
            }
        }
        private fun decreaseQuantity(position: Int){
            if(itemQuantities[position] > 1){
                itemQuantities[position] --
                binding.cartQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun increaseQuantity(position: Int){
            if(itemQuantities[position] < 10){
                itemQuantities[position] ++
                binding.cartQuantity.text = itemQuantities[position].toString()
            }
        }
        private fun deleteItems(position: Int){
            cartItems.removeAt(position)
            cartImages.removeAt(position)
            cartItemPrices.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, cartItems.size)
        }
    }
}