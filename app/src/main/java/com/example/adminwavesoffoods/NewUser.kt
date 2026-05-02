package com.example.adminwavesoffoods

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwavesoffoods.databinding.ActivityNewUserBinding
import com.example.adminwavesoffoods.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class NewUser : AppCompatActivity() {
    private val binding: ActivityNewUserBinding by lazy {
        ActivityNewUserBinding.inflate(layoutInflater)
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.button4.setOnClickListener {
            val name = binding.editText1.text.toString().trim()
            val email = binding.editText2.text.toString().trim()
            val password = binding.editTextTextPassword.text.toString().trim()

            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(this@NewUser, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                createNewUser(name, email, password)
            }
        }
    }

    private fun createNewUser(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: ""
                val userModel = UserModel(name, "Admin", email, password)
                database.reference.child("user").child(userId).setValue(userModel).addOnCompleteListener { databaseTask ->
                    if (databaseTask.isSuccessful) {
                        Toast.makeText(this@NewUser, "New Admin Created Successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@NewUser, "Failed to save data: ${databaseTask.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this@NewUser, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
