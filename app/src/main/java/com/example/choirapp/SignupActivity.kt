package com.example.choirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.choirapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signupUser.setOnClickListener{
            signup(binding.emailSignup.text.toString(), binding.pwdSignup.text.toString())
        }

    }

    private fun signup(email:String, password:String){
        

        auth.createUserWithEmailAndPassword(email, password).
                addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else{
                        Log.e("SignupActivity", "${task.exception}")
                    }
                }
    }
}