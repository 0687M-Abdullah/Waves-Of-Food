package com.example.adminwavesoffoods

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.adminwavesoffoods.databinding.ActivitySignUpBinding
import com.example.adminwavesoffoods.model.UserModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    private val binding: ActivitySignUpBinding by lazy {
        ActivitySignUpBinding.inflate(layoutInflater)
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

        ViewCompat.setOnApplyWindowInsetsListener(binding.signUpLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.loginRedirectText.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.createAccountButton.setOnClickListener {
            val name = binding.nameInput.text.toString().trim()
            val restaurantName = binding.restaurantInput.text.toString().trim()
            val email = binding.emailInput.text.toString().trim()
            val password = binding.passwordInput.text.toString().trim()

            if (name.isEmpty() || restaurantName.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@SignUpActivity, "Please fill all fields", Toast.LENGTH_SHORT).show()
            } else {
                createAccount(name, restaurantName, email, password)
            }
        }
    }

    private fun createAccount(name: String, restaurantName: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val userId = auth.currentUser?.uid ?: ""
                val userModel = UserModel(name, restaurantName, email, password)
                database.reference.child("user").child(userId).setValue(userModel)
                    .addOnSuccessListener {
                        Toast.makeText(this@SignUpActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                        auth.signOut() // Sign out to force manual login
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this@SignUpActivity, "Failed to save user: ${e.message}", Toast.LENGTH_LONG).show()
                    }
            } else {
                Toast.makeText(this@SignUpActivity, "Registration failed: ${task.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}