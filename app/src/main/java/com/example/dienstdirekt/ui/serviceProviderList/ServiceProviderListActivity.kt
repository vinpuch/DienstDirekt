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

// Diese Klasse repräsentiert die Aktivität, in der die Liste der Dienstleister angezeigt wird.
class ServiceProviderListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDienstleistungenBinding
    private lateinit var serviceProviderAdapter: ServiceProviderAdapter
    private lateinit var db: UnternehmenDatabaseHelper
    private lateinit var backButton: ImageButton
    private lateinit var buttonAZ: Button
    private lateinit var buttonZA: Button

    // Diese Methode wird aufgerufen, wenn die Aktivität erstellt wird.
    // Sie initialisiert die Benutzeroberfläche und setzt die Event-Handler.
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

    // Diese Methode aktualisiert die Daten in der Liste der Dienstleister.
    // Sie wird aufgerufen, wenn die Daten in der Datenbank geändert wurden.
    private fun refreshData() {
        val companyList = db.getAll()
        serviceProviderAdapter.refreshData(companyList)
    }

    // Diese Methode holt die Daten aus der Datenbank und sortiert sie nach dem Namen der Unternehmen.
    // Wenn der Parameter 'asc' auf 'true' gesetzt ist, werden die Daten in aufsteigender Reihenfolge sortiert.
    // Wenn 'asc' auf 'false' gesetzt ist, werden die Daten in absteigender Reihenfolge sortiert.
    private fun getDataOrderByNameAsc(asc: Boolean) {
        val companyList = db.getOrderByName(asc)
        serviceProviderAdapter.refreshData(companyList)
    }

    // Diese Methode wird aufgerufen, wenn die Aktivität wieder in den Vordergrund kommt.
    // Sie aktualisiert die Daten in der Liste der Dienstleister.
    override fun onResume() {
        super.onResume()
        refreshData()
    }
}
