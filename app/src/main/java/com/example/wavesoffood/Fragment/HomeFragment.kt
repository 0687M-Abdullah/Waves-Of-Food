package com.example.wavesoffood.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.interfaces.ItemClickListener
import com.denzcoskun.imageslider.models.SlideModel
import com.example.wavesoffood.Adapter.PopularItemsAdapter
import com.example.wavesoffood.Fragment.BottomSheetFragment
import com.example.wavesoffood.R
import com.example.wavesoffood.databinding.FragmentHomeBinding

private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        binding.viewAllMenu.setOnClickListener {
            val bottomSheetDialog = BottomSheetFragment()
            bottomSheetDialog.show(parentFragmentManager, "test")
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.banner_1, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner_2, ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.banner_3, ScaleTypes.FIT))

        val imageSlider = binding.imageSlider
        imageSlider.setImageList(imageList)
        imageSlider.setImageList(imageList, ScaleTypes.FIT)

        imageSlider.setItemClickListener(object: ItemClickListener{
            override fun doubleClick(position: Int) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(position: Int) {
                val itemPosition = imageList[position]
                val itemMessage = "Selected Image ${position}"
                Toast.makeText(requireContext(),itemMessage,Toast.LENGTH_SHORT).show()

            }
        })
        val foodName = listOf("Burger", "Sandwich", "Shwarma", "Item")
        val prices = listOf("$5", "$9", "$15", "$10")
        val foodImages = listOf(R.drawable.menu_3, R.drawable.menu_2, R.drawable.menu_1, R.drawable.menu_3)
        val adapter = PopularItemsAdapter(foodName, prices, foodImages)
        binding.RecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.RecyclerView.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
            }
    }
}