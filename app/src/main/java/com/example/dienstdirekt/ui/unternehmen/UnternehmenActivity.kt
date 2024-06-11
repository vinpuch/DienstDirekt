package com.example.dienstdirekt.ui.unternehmen

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityUnternehmensprofilBinding
import com.example.dienstdirekt.ui.register.RegisterDatabaseHelper

class UnternehmenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnternehmensprofilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnternehmensprofilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.pictureButton.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_PICK
            startActivityForResult(intent, 1)
        }
        binding.certificateButton.setOnClickListener {
            val intent = Intent()
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 2)
        }

        binding.checkButton.setOnClickListener {
            val name = binding.companyNameView.text.toString()
            val dienstleistung = binding.categoryView.text.toString()
            val ort = binding.textViewLocation.text.toString()

            insertData(name, dienstleistung, ort)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            binding.imageView.setImageURI(uri)
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
        }
    }

    private fun isValidLocation(location: String): Boolean {
        // This is a very basic check and might not catch all invalid addresses.
        // Consider using a more sophisticated method for production code.
        return location.matches(Regex("^[a-zA-Z0-9.,'\\-\\s]+$"))
    }

    fun insertData(name: String, dienstleistung: String, ort: String) {
        if (name.isEmpty() || dienstleistung.isEmpty() || ort.isEmpty()) {
            Toast.makeText(this, "Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show()
        } else if (!isValidLocation(ort)) {
            Toast.makeText(this, "Bitte geben Sie eine gültige Adresse ein", Toast.LENGTH_SHORT)
                .show()
            // Highlight the location field to indicate the error.
            binding.textViewLocation.setBackgroundColor(Color.RED)
        } else {
            val db = RegisterDatabaseHelper(this).writableDatabase

            val values = ContentValues().apply {
                put("name", name)
                put("dienstleistung", dienstleistung)
                put("ort", ort)
            }

            val newRowId = db?.insert("unternehmen", null, values)
            if (newRowId != -1L) {
                Toast.makeText(this, "Daten erfolgreich eingefügt", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Fehler beim Einfügen der Daten", Toast.LENGTH_SHORT).show()
            }
        }
    }
}