package com.example.adminwavesoffoods

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwavesoffoods.databinding.ActivityAdminProfileBinding
import com.example.adminwavesoffoods.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AdminProfile : AppCompatActivity() {
    private val binding: ActivityAdminProfileBinding by lazy {
        ActivityAdminProfileBinding.inflate(layoutInflater)
    }
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        // Initialize Firebase Database with the specific URL provided
        database = FirebaseDatabase.getInstance("https://wavesoffood-f7b61-default-rtdb.firebaseio.com/")

        ViewCompat.setOnApplyWindowInsetsListener(binding.adminProfileLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.backButton.setOnClickListener {
            finish()
        }

        retrieveUserData()

        binding.saveButton.setOnClickListener {
            updateUserData()
        }
    }

    private fun retrieveUserData() {
        val userId = auth.currentUser?.uid ?: ""
        if (userId.isNotEmpty()) {
            database.reference.child("user").child(userId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            val userProfile = snapshot.getValue(UserModel::class.java)
                            if (userProfile != null) {
                                binding.nameInput.setText(userProfile.name)
                                binding.emailInput.setText(userProfile.email)
                                binding.addressInput.setText(userProfile.address)
                                binding.phoneInput.setText(userProfile.phone)
                                binding.passwordInput.setText(userProfile.password)
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Toast.makeText(this@AdminProfile, "Error fetching data: ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }

    private fun updateUserData() {
        val updateName = binding.nameInput.text.toString()
        val updateEmail = binding.emailInput.text.toString()
        val updatePassword = binding.passwordInput.text.toString()
        val updateAddress = binding.addressInput.text.toString()
        val updatePhone = binding.phoneInput.text.toString()

        val userId = auth.currentUser?.uid ?: ""
        if (userId.isNotEmpty()) {
            val userUpdates = mapOf(
                "name" to updateName,
                "email" to updateEmail,
                "password" to updatePassword,
                "address" to updateAddress,
                "phone" to updatePhone
            )
            database.reference.child("user").child(userId).updateChildren(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Update Failed: ${it.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
