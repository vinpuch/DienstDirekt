package com.example.dienstdirekt.ui.categories

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.MainActivity
import com.example.dienstdirekt.ui.serviceProviderList.SeviceProviderList
import com.example.dienstdirekt.databinding.ActivityCategoriesBinding
import com.example.dienstdirekt.R
class CategoriesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCategoriesBinding
    private lateinit var accountId: ImageView
    private lateinit var montageButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCategoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        accountId = findViewById(R.id.accountIdImage)
        accountId.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        montageButton = findViewById(R.id.montageButton)
        montageButton.setOnClickListener {
            val intent = Intent(this, SeviceProviderList::class.java )
        }
    }
}