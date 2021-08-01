package com.example.choirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import com.example.choirapp.databinding.ActivityLoginBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var email:String
    private lateinit var password:String


    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.createAccountBtn.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginUser.setOnClickListener{
            if (isFormValidated()) {
                login(email, password, it)

            }
        }
    }

    private fun login(email:String,password:String,view: View){
        widgetVisibility(false,View.VISIBLE)
        auth.signInWithEmailAndPassword(email, password).
                addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val intent = Intent(this, AdminActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Log.e("LoginActivity", "${task.exception}")
                    }
                }.addOnFailureListener {
                    widgetVisibility(true, View.GONE)
                    Snackbar.make(view, "Login failed, please try again", Snackbar.LENGTH_LONG).
                    apply {
                        animationMode =  Snackbar.ANIMATION_MODE_SLIDE
                    }.show()
        }

    }

    private fun isFormValidated():Boolean{
        var isValid = true
        if(binding.emailLogin.text?.isEmpty() == true) {
            binding.textInputLayout2.error = "Field cannot be empty"
            isValid = false
        } else{
            email = binding.emailLogin.text.toString()
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailLogin.text.toString()).matches()) {
            binding.textInputLayout2.error = "Enter a valid Email"
        } else{
            email = binding.emailLogin.text.toString()
        }

        if(binding.pwdLogin.text?.isEmpty() == true) {
            binding.textInputLayout4.error = "Field cannot be empty"
            isValid = false
        } else{
            password = binding.pwdLogin.text.toString()
        }

        return isValid
    }

    private fun widgetVisibility(state: Boolean, visible: Int) {
        binding.loginUser.isEnabled = state
        binding.loginAdmin.isEnabled = state
        binding.progressLogin.visibility = visible

    }
}