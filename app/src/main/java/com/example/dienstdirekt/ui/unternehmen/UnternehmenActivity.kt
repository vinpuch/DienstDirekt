package com.example.dienstdirekt.ui.unternehmen

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.DatabaseHelper
import com.example.dienstdirekt.DatabaseContract
import com.example.dienstdirekt.databinding.ActivityUnternehmensprofilBinding

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

            if (name.isEmpty() || dienstleistung.isEmpty() || ort.isEmpty()) {
                Toast.makeText(this, "Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show()
            } else {

                // hier noch bild etc

                insertData(name, dienstleistung, ort,)
                Toast.makeText(this, "Daten erfolgreich eingefügt", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            binding.imageView.setImageURI(uri)
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            // ...
        }
    }

    fun insertData(name: String, dienstleistung: String, ort: String) {
        val db = DatabaseHelper(this).writableDatabase

        val values = ContentValues().apply {
            put(DatabaseContract.UnternehmenEntry.COLUMN_NAME, name)
            put(DatabaseContract.UnternehmenEntry.COLUMN_DIENSTLEISTUNG, dienstleistung)
            put(DatabaseContract.UnternehmenEntry.COLUMN_ORT, ort)
        }

        val newRowId = db?.insert(DatabaseContract.UnternehmenEntry.TABLE_NAME, null, values)
    }
}