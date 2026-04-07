package com.example.wavesoffood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.CardAdapter
import com.example.wavesoffood.Adapter.PopularItemsAdapter
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    private lateinit var binding: FragmentCartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCartBinding.inflate(inflater,container,false)

        val foodName = listOf("Burger", "Sandwich", "Shwarma", "Sweets", "Roll", "Paratha")
        val prices = listOf("$5", "$9", "$15", "$10", "$20", "$13")
        val foodImages = listOf(R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
            R.drawable.menu7,
            R.drawable.menu_1,
            R.drawable.menu_2)
        val adapter = CardAdapter(ArrayList(foodName), ArrayList(prices), ArrayList(foodImages))
        binding.cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.cartRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {

            }
    }
}