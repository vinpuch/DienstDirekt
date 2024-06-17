package com.example.dienstdirekt.ui.serviceProviderList

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dienstdirekt.databinding.ActivityDienstleistungenBinding
import com.example.dienstdirekt.ui.register.RegisterDatabaseHelper
import com.example.dienstdirekt.R
import com.example.dienstdirekt.ui.categories.CategoriesActivity
import com.example.dienstdirekt.ui.unternehmen.UnternehmenDatabaseHelper

class ServiceProviderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDienstleistungenBinding
    private lateinit var serviceProviderAdapter: ServiceProviderAdapter
    private lateinit var db: UnternehmenDatabaseHelper
    private lateinit var backButton: ImageButton
    private lateinit var buttonAZ : Button
    private lateinit var buttonZA : Button

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

        // Initialize the adapter with an empty list
        serviceProviderAdapter = ServiceProviderAdapter(emptyList(), this)

        // Set up the RecyclerView
        binding.serviceProviderRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@ServiceProviderListActivity)
            adapter = serviceProviderAdapter
        }

        // Load data initially
        refreshData()
    }

    private fun refreshData() {
        // Fetch data from database
        val companyList = db.getAll()

        // Update adapter with new data
        serviceProviderAdapter.refreshData(companyList)
    }

    private fun getDataOrderByNameAsc(asc: Boolean) {
        // Fetch data from database
        val companyList = db.getOrderByName(asc)

        // Update adapter with new data
        serviceProviderAdapter.refreshData(companyList)
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when activity resumes
        refreshData()
    }
}
