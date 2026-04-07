package com.example.wavesoffood.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.MenuAdapter
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        val foodName = listOf("Burger", "Sandwich", "Shwarma", "Sweets", "Roll", "Paratha", "Shwarma", "Sweets", "Roll", "Paratha")
        val prices = listOf("$5", "$9", "$15", "$10", "$20", "$13", "$15", "$10", "$20", "$13")
        val foodImages = listOf(R.drawable.menu4,
            R.drawable.menu5,
            R.drawable.menu6,
            R.drawable.menu7,
            R.drawable.menu_1,
            R.drawable.menu_2,
            R.drawable.menu6,
            R.drawable.menu7,
            R.drawable.menu_1,
            R.drawable.menu_2)
        val adapter = MenuAdapter(ArrayList(foodName), ArrayList(prices), ArrayList(foodImages))
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter
        return binding.root
    }

    companion object {
        fun newInstance(param1: String, param2: String) =
            BottomSheetFragment().apply {
            }
    }
}