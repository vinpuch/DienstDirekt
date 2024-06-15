package com.example.dienstdirekt.ui.unternehmen

import android.content.ContentValues
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
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

        addTextChangedListenerToEditText(binding.editText)

        // TextChangedListener für das Unternehmensnamenfeld
        addTextChangedListenerToEditText(binding.companyNameView)

        // TextChangedListener für das Ortfeld
        addTextChangedListenerToEditText(binding.textViewLocation)

        // TextChangedListener für das Dienstleistungsfeld
        addTextChangedListenerToEditText(binding.categoryView)

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

    private fun addTextChangedListenerToEditText(editText: EditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                // Nichts zu tun
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.isNotEmpty()) {
                    editText.gravity = Gravity.CENTER
                    editText.textSize = 24f
                    editText.setTextColor(Color.BLACK)
                    editText.setPadding(20,0,0,0)
                } else {
                    editText.gravity = Gravity.START or Gravity.TOP
                    editText.textSize = 14f
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Nichts zu tun
            }
        })
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
            if (newRowId != null && newRowId > -1) {
                Toast.makeText(
                    this,
                    "Daten erfolgreich in der Datenbank abgelegt",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    this,
                    "Fehler beim Einfügen der Daten in die Datenbank",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }}