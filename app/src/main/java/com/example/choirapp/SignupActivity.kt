package com.example.choirapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.choirapp.databinding.ActivitySignupBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

private val TAG = SignupActivity::class.java.name

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var auth: FirebaseAuth

    private lateinit var email: String
    private lateinit var password: String
//    private lateinit var name: String

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySignupBinding.inflate(layoutInflater)
        auth = FirebaseAuth.getInstance()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.signupUser.setOnClickListener {
            if (isFormValidated()) {
                signup(email, password, it)

            }
        }
    }

    private fun signup(email:String, password:String, view: View){
        widgetVisibility(false, View.VISIBLE)

        auth.createUserWithEmailAndPassword(email, password).
            addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        Log.e("SignupActivity", "${task.exception}")
                    }
            }.addOnFailureListener {
                widgetVisibility(true, View.GONE)
                Log.e(TAG, "${it.message}")

                Snackbar.make(view, "Signup failed, please try again", Snackbar.LENGTH_LONG).
                        apply {
                            animationMode =  Snackbar.ANIMATION_MODE_SLIDE
                        }.show()
            }
    }

    private fun isFormValidated():Boolean{
        var isValid = true
        if(binding.emailSignup.text?.isEmpty() == true) {
            binding.textInputLayout2.error = "Field cannot be empty"
            isValid = false
        } else{
            email = binding.emailSignup.text.toString()
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(binding.emailSignup.text.toString()).matches()) {
            binding.textInputLayout2.error = "Enter a valid Email"
        } else{
            email = binding.emailSignup.text.toString()
        }

        if(binding.pwdSignup.text?.isEmpty() == true) {
            binding.textInputLayout4.error = "Field cannot be empty"
            isValid = false
        } else{
            password = binding.pwdSignup.text.toString()
        }

//        if(binding.fullName.text?.isEmpty() == true) {
//            binding.textInputLayout4.error = "Field cannot be empty"
//            isValid = false
//        } else{
//            name = binding.fullName.text.toString()
//        }

        return isValid
    }


    private fun widgetVisibility(state: Boolean, visibility: Int) {
        binding.signupUser.isEnabled = state
        binding.progressSignUp.visibility = visibility
    }
}