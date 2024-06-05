package com.example.dienstdirekt.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.MainActivity
import com.example.dienstdirekt.R
import com.example.dienstdirekt.databinding.ActivityRegistercompanyBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistercompanyBinding
    private lateinit var crossLeft: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistercompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crossLeft = findViewById(R.id.imageView8)
        crossLeft.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}