package com.example.s8081735assignment2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.s8081735assignment2.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

// Main activity hosting the navigation fragment.
// Entry point for the app.
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

