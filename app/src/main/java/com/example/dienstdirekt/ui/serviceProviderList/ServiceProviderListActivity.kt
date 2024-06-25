package com.example.dienstdirekt.ui.serviceProviderList

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dienstdirekt.R
import com.example.dienstdirekt.databinding.ActivityDienstleistungenBinding
import com.example.dienstdirekt.ui.categories.CategoriesActivity
import com.example.dienstdirekt.ui.unternehmen.UnternehmenDatabaseHelper

class ServiceProviderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDienstleistungenBinding
    private lateinit var serviceProviderAdapter: ServiceProviderAdapter
    private lateinit var db: UnternehmenDatabaseHelper
    private lateinit var backButton: ImageButton
    private lateinit var buttonAZ: Button
    private lateinit var buttonZA: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDienstleistungenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        backButton = findViewById(R.id.backButtonServiceProvider)
        backButton.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }

        buttonAZ = findViewById(R.id.buttonAZ)
        buttonAZ.setOnClickListener {
            getDataOrderByNameAsc(true)
        }

        buttonZA = findViewById(R.id.buttonZA)
        buttonZA.setOnClickListener {
            getDataOrderByNameAsc(false)
        }

        db = UnternehmenDatabaseHelper(this)
        serviceProviderAdapter = ServiceProviderAdapter(emptyList(), this)
        binding.serviceProviderRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ServiceProviderListActivity)
            adapter = serviceProviderAdapter
        }

        refreshData()
    }

    private fun refreshData() {
        val companyList = db.getAll()
        serviceProviderAdapter.refreshData(companyList)
    }

    private fun getDataOrderByNameAsc(asc: Boolean) {
        val companyList = db.getOrderByName(asc)
        serviceProviderAdapter.refreshData(companyList)
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }
}
