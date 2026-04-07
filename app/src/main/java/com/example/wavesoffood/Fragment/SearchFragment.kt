package com.example.wavesoffood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.MenuAdapter
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentCartBinding
import com.example.wavesoffood.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: MenuAdapter
    private val foodName = listOf("Burger", "Sandwich", "Shwarma", "Sweets", "Roll", "Paratha")
    val prices = listOf("$5", "$9", "$15", "$10", "$20", "$13")
    val foodImages = listOf(R.drawable.menu4,
        R.drawable.menu5,
        R.drawable.menu6,
        R.drawable.menu7,
        R.drawable.menu_1,
        R.drawable.menu_2)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    private val filteredMenuFoodName = mutableListOf<String>()
    private val filteredMenuPrices = mutableListOf<String>()
    private val filteredMenuImages = mutableListOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater,container,false)
        adapter = MenuAdapter(filteredMenuFoodName,filteredMenuPrices,filteredMenuImages)
        binding.menuRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.menuRecyclerView.adapter = adapter

        showAllMenu()
        setUpSearchView()
        return binding.root
    }

    private fun showAllMenu(){
        filteredMenuFoodName.clear()
        filteredMenuPrices.clear()
        filteredMenuImages.clear()

        filteredMenuFoodName.addAll(foodName)
        filteredMenuPrices.addAll(prices)
        filteredMenuImages.addAll(foodImages)

        adapter.notifyDataSetChanged()
    }

    private fun setUpSearchView(){
       binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
           override fun onQueryTextSubmit(query: String): Boolean {
               filteredMenuItems(query)
               return true
           }

           override fun onQueryTextChange(newText: String): Boolean {
               if (newText.isEmpty()) {
                   showAllMenu()
               } else {
                   filteredMenuItems(newText)
               }
               return true
           }
       })
    }
    private fun filteredMenuItems(query: String){
        filteredMenuFoodName.clear()
        filteredMenuPrices.clear()
        filteredMenuImages.clear()
        foodName.forEachIndexed { index, foodName->
            if (foodName.contains(query, ignoreCase = true)){
                filteredMenuFoodName.add(foodName)
                filteredMenuPrices.add(prices[index])
                filteredMenuImages.add(foodImages[index])
            }
        }
        adapter.notifyDataSetChanged()

    }

    companion object {

    }
}