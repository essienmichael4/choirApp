package com.example.choirapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.choirapp.databinding.ActivityAdminBinding
import com.example.choirapp.databinding.ActivityLoginBinding

class AdminActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityAdminBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
    }
}