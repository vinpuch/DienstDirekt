package com.example.dienstdirekt.ui.unternehmen

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityUnternehmensprofilBinding
import com.example.dienstdirekt.ui.register.RegisterDatabaseHelper
import java.io.ByteArrayOutputStream

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
            intent.type = "*/*"
            intent.action = Intent.ACTION_GET_CONTENT
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
            finish()

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
                    editText.setPadding(20, 0, 0, 0)
                } else {
                    editText.gravity = Gravity.START or Gravity.TOP
                    editText.textSize = 14f
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Removed the code that changes the background color to red
                if (isValidLocation(s.toString())) {
                    // Reset the background color when the input is valid
                    editText.setBackgroundColor(Color.WHITE)
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
            val drawable = BitmapDrawable(resources, bitmap)
            binding.pictureButton.background = drawable
            binding.pictureButton.text = ""
            Toast.makeText(this, "Foto erfolgreich hochgeladen", Toast.LENGTH_SHORT).show()
        } else if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            val fileName = uri?.path?.substring(uri.path?.lastIndexOf("/") ?: 0)
            Toast.makeText(this, "Dokument erfolgreich hochgeladen", Toast.LENGTH_SHORT).show()
            binding.certificateButton.text = fileName
        }
    }


    private fun isValidLocation(location: String): Boolean {
        // Use a more specific regex for address validation
        return location.matches(Regex("^[a-zA-ZäöüÄÖÜß.\\-\\s]+\\s\\d+,\\s[a-zA-ZäöüÄÖÜß.\\-\\s]+,\\s\\d{5}$"))
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
            val db = UnternehmenDatabaseHelper(this).writableDatabase

            // Convert the image and certificate to byte arrays
            val picture = (binding.pictureButton.background as BitmapDrawable).bitmap
            val pictureStream = ByteArrayOutputStream()
            picture.compress(Bitmap.CompressFormat.PNG, 100, pictureStream)
            val pictureByteArray = pictureStream.toByteArray()

            val certificate = (binding.certificateButton.background as BitmapDrawable).bitmap
            val certificateStream = ByteArrayOutputStream()
            certificate.compress(Bitmap.CompressFormat.PNG, 100, certificateStream)
            val certificateByteArray = certificateStream.toByteArray()

            // Get the description
            val beschreibung = binding.descriptionView.toString()

            val values = ContentValues().apply {
                put("name", name)
                put("dienstleistung", dienstleistung)
                put("ort", ort)
                put("picture", pictureByteArray)
                put("certificate", certificateByteArray)
                put("beschreibung", beschreibung)
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
    }
}
