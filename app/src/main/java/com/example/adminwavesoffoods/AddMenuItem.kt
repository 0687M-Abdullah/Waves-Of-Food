package com.example.adminwavesoffoods

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwavesoffoods.databinding.ActivityAddMenuItemBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class AddMenuItem : AppCompatActivity() {

    private var foodImageUri: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAddMenuItemBinding by lazy {
        ActivityAddMenuItemBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        database = FirebaseDatabase.getInstance(
            "https://wavesoffood-f7b61-default-rtdb.firebaseio.com/"
        )

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 📸 Pick Image
        binding.selectImage.setOnClickListener {
            pickImage.launch("image/*")
        }

        // 🔙 Back
        binding.backButton.setOnClickListener {
            finish()
        }

        // ➕ Add Item
        binding.addItemButton.setOnClickListener {

            if (auth.currentUser == null) {
                Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = binding.itemName.text.toString().trim()
            val price = binding.itemPrice.text.toString().trim()
            val desc = binding.shortDescription.text.toString().trim()
            val ingredients = binding.ingredients.text.toString().trim()

            if (name.isEmpty() || price.isEmpty() || desc.isEmpty() || ingredients.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            uploadData(name, price, desc, ingredients)
        }
    }

    // 🔥 MAIN UPLOAD FUNCTION
    private fun uploadData(
        name: String,
        price: String,
        desc: String,
        ingredients: String
    ) {

        val menuRef: DatabaseReference = database.getReference("menu")

        val itemId = menuRef.push().key
        if (itemId == null) {
            Toast.makeText(this, "Failed to generate ID", Toast.LENGTH_SHORT).show()
            return
        }

        binding.addItemButton.isEnabled = false
        Toast.makeText(this, "Saving item...", Toast.LENGTH_SHORT).show()

        // 🖼 IF IMAGE EXISTS → TRY UPLOAD
        if (foodImageUri != null) {

            val storageRef = FirebaseStorage.getInstance()
                .reference
                .child("menu_images")
                .child("$itemId.jpg")

            storageRef.putFile(foodImageUri!!)
                .addOnSuccessListener {

                    storageRef.downloadUrl.addOnSuccessListener { uri ->

                        saveToDatabase(
                            menuRef,
                            itemId,
                            name,
                            price,
                            desc,
                            ingredients,
                            uri.toString()
                        )
                    }
                }
                .addOnFailureListener {

                    // ❌ IMAGE FAILED → STILL SAVE DATA
                    saveToDatabase(
                        menuRef,
                        itemId,
                        name,
                        price,
                        desc,
                        ingredients,
                        ""
                    )
                }

        } else {

            // 📌 NO IMAGE SELECTED → SAVE ONLY DATA
            saveToDatabase(
                menuRef,
                itemId,
                name,
                price,
                desc,
                ingredients,
                ""
            )
        }
    }

    // 💾 SAVE TO FIREBASE DATABASE
    private fun saveToDatabase(
        menuRef: DatabaseReference,
        itemId: String,
        name: String,
        price: String,
        desc: String,
        ingredients: String,
        imageUrl: String
    ) {

        val data = HashMap<String, Any>()
        data["foodName"] = name
        data["foodPrice"] = price
        data["foodDescription"] = desc
        data["foodIngredient"] = ingredients
        data["foodImage"] = imageUrl

        menuRef.child(itemId)
            .setValue(data)
            .addOnSuccessListener {

                binding.addItemButton.isEnabled = true

                Toast.makeText(
                    this,
                    "Item added successfully",
                    Toast.LENGTH_SHORT
                ).show()

                finish()
            }
            .addOnFailureListener {

                binding.addItemButton.isEnabled = true

                Toast.makeText(
                    this,
                    "Database Error: ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    // 📸 Image Picker
    private val pickImage =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                foodImageUri = uri
                binding.selectedImage.setImageURI(uri)
            }
        }
}