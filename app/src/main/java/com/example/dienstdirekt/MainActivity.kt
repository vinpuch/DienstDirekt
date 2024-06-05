package com.example.dienstdirekt

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityMainBinding
import com.example.dienstdirekt.ui.register.RegisterActivity
import com.example.dienstdirekt.ui.unternehmen.UnternehmenActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var button: ImageButton
    private lateinit var noAccountTextView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        noAccountTextView = findViewById(R.id.textView6)
        noAccountTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        button = findViewById(R.id.buttonPage1)
        button.setOnClickListener {
            val intent = Intent(this, UnternehmenActivity::class.java)
            startActivity(intent)
        }

    }
}