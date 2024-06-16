package com.example.dienstdirekt.ui.serviceProviderList

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dienstdirekt.databinding.ActivityDienstleistungenBinding
import com.example.dienstdirekt.ui.register.RegisterDatabaseHelper

class ServiceProviderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDienstleistungenBinding
    private lateinit var serviceProviderAdapter: ServiceProviderAdapter
    private lateinit var db: RegisterDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDienstleistungenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RegisterDatabaseHelper(this)

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

    override fun onResume() {
        super.onResume()
        // Refresh data when activity resumes
        refreshData()
    }
}
