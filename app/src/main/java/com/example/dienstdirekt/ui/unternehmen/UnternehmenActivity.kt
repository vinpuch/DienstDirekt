package com.example.dienstdirekt.ui.unternehmen

import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.Color
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.content.ContentValues
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityUnternehmensprofilBinding
import java.io.ByteArrayOutputStream
import android.text.Editable
import android.text.TextWatcher


class UnternehmenActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUnternehmensprofilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUnternehmensprofilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        addTextChangedListenerToEditText(binding.editText)
        addTextChangedListenerToEditText(binding.companyNameView)
        addTextChangedListenerToEditText(binding.textViewLocation)
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
                if (isValidLocation(s.toString())) {
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
        return location.matches(Regex("^[a-zA-ZäöüÄÖÜß.\\-\\s]+\\s\\d+,\\s[a-zA-ZäöüÄÖÜß.\\-\\s]+,\\s\\d{5}$"))
    }

    fun insertData(name: String, dienstleistung: String, ort: String) {
        if (name.isEmpty() || dienstleistung.isEmpty() || ort.isEmpty()) {
            Toast.makeText(this, "Bitte füllen Sie alle Felder aus", Toast.LENGTH_SHORT).show()
        } else if (!isValidLocation(ort)) {
            Toast.makeText(this, "Bitte geben Sie eine gültige Adresse ein", Toast.LENGTH_SHORT)
                .show()
            binding.textViewLocation.setBackgroundColor(Color.RED)
        } else {
            val db = UnternehmenDatabaseHelper(this).writableDatabase
            val pictureByteArray = getByteArrayFromDrawable(binding.pictureButton.background)
            val certificateByteArray = getByteArrayFromDrawable(binding.certificateButton.background)
            val beschreibung = binding.editText.text.toString()
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

    private fun getByteArrayFromDrawable(drawable: Drawable): ByteArray? {
        return when (drawable) {
            is BitmapDrawable -> {
                val bitmap = drawable.bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.toByteArray()
            }
            is GradientDrawable -> {
                val width = 100
                val height = 100
                val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bitmap)
                drawable.setBounds(0, 0, width, height)
                drawable.draw(canvas)
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                stream.toByteArray()
            }
            else -> null
        }
    }

}
