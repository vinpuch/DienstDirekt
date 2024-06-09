package com.example.dienstdirekt.ui.categories

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityCategoriesBinding
import com.example.dienstdirekt.databinding.ActivityRegistercompanyBinding

class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}