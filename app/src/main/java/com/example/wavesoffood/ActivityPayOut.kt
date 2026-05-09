package com.example.wavesoffood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.wavesoffood.databinding.ActivityPayOutBinding

class ActivityPayOut : AppCompatActivity() {
    private val binding: ActivityPayOutBinding by lazy {
        ActivityPayOutBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.imageButton.setOnClickListener {
            finish()
        }
        binding.placeMyOrderButton.setOnClickListener {
            val bottomSheetFragment = CongratsBottomSheet()
            bottomSheetFragment.show(supportFragmentManager, "Test")
        }
    }
}
