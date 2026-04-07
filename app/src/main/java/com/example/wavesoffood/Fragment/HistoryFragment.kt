package com.example.wavesoffood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wavesoffood.Adapter.BuyAgainAdapter
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentHistoryBinding
import com.example.wavesoffood.databinding.FragmentSearchBinding

class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var buyAgainAdapter: BuyAgainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)

        setupRecyclerView()
        return binding.root
    }

    private fun setupRecyclerView() {

        val buyAgainFoodName = arrayListOf("Food 2", "Food 2", "Food 3")
        val buyAgainFoodPrice = arrayListOf("$10", "$8", "$30")
        val buyAgainFoodImage = arrayListOf(
            R.drawable.menu_1,
            R.drawable.menu4,
            R.drawable.menu5
        )

        buyAgainAdapter = BuyAgainAdapter(
            buyAgainFoodName,
            buyAgainFoodPrice,
            buyAgainFoodImage
        )

        binding.buyAgainRecyclerView.adapter = buyAgainAdapter
        binding.buyAgainRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())

    }


    companion object {
    }
}