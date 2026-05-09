package com.example.adminwavesoffoods

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminwavesoffoods.databinding.ActivityAllMenuItemsBinding
import com.example.adminwavesoffoods.model.AllMenu
import com.google.firebase.database.*

class AllMenuItems : AppCompatActivity() {

    private lateinit var database: FirebaseDatabase
    private var menuItems: MutableList<AllMenu> = mutableListOf()
    private lateinit var adapter: MenuItemAdapter

    private val binding: ActivityAllMenuItemsBinding by lazy {
        ActivityAllMenuItemsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance(
            "https://wavesoffood-f7b61-default-rtdb.firebaseio.com/"
        )

        setAdapter()
        retrieveMenuItem()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun retrieveMenuItem() {

        val foodRef = database.reference.child("menu")

        foodRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                menuItems.clear()

                for (foodSnapshot in snapshot.children) {

                    val menuItem = foodSnapshot.getValue(AllMenu::class.java)

                    if (menuItem != null) {
                        menuItem.key = foodSnapshot.key
                        menuItems.add(menuItem)
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@AllMenuItems,
                    "Error fetching data: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setAdapter() {

        adapter = MenuItemAdapter(
            this@AllMenuItems,
            menuItems,
            { position -> deleteMenuItem(position) },
            { position, quantity -> updateQuantity(position, quantity) }
        )

        binding.allMenuRecyclerView.layoutManager = LinearLayoutManager(this@AllMenuItems)
        binding.allMenuRecyclerView.adapter = adapter
    }

    private fun updateQuantity(position: Int, quantity: Int) {

        if (position !in menuItems.indices) return

        val key = menuItems[position].key ?: return

        database.reference.child("menu")
            .child(key)
            .child("foodQuantity")
            .setValue(quantity)
            .addOnSuccessListener {
                // The addValueEventListener will handle the list update
            }
            .addOnFailureListener {
                Toast.makeText(this, "Failed to update quantity", Toast.LENGTH_SHORT).show()
            }
    }

    private fun deleteMenuItem(position: Int) {

        if (position !in menuItems.indices) return

        val key = menuItems[position].key ?: return

        database.reference.child("menu")
            .child(key)
            .removeValue()
            .addOnSuccessListener {
                Toast.makeText(this, "Item Deleted", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener { error ->
                Toast.makeText(
                    this,
                    "Delete failed: ${error.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
