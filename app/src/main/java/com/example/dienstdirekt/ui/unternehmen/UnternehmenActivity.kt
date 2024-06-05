package com.example.dienstdirekt.ui.unternehmen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityUnternehmensprofilBinding

class UnternehmenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnternehmensprofilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnternehmensprofilBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}