package com.example.dienstdirekt

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityMainBinding
import com.example.dienstdirekt.ui.categories.CategoriesActivity
import com.example.dienstdirekt.ui.register.RegisterActivity
import org.mindrot.jbcrypt.BCrypt

// Diese Klasse repräsentiert die Hauptaktivität der Anwendung.
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var button: ImageButton
    private lateinit var alsGastButton: TextView
    private lateinit var db: MainDatabaseHelper

    private lateinit var emailOrPhone: EditText
    private lateinit var password: EditText

    // Diese Methode wird aufgerufen, wenn die Aktivität erstellt wird.
    // Sie initialisiert die Benutzeroberfläche und setzt die Event-Handler.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MainDatabaseHelper(this)
        val registerButton: TextView = findViewById(R.id.registrieren)
        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        alsGastButton = findViewById(R.id.alsGastButton)
        alsGastButton.setOnClickListener {
            val intent = Intent(this, CategoriesActivity::class.java)
            startActivity(intent)
        }

        button = findViewById(R.id.buttonPage1)
        button.setOnClickListener {
            if (isValidLogin(emailOrPhone, password)) {
                val intent = Intent(this, CategoriesActivity::class.java)
                startActivity(intent)
            }
        }

        emailOrPhone = findViewById(R.id.eMailorPhoneText)
        password = findViewById(R.id.passwordText)
    }

    // Diese Methode überprüft, ob die Anmeldedaten gültig sind.
    // Sie gibt true zurück, wenn die Anmeldedaten gültig sind, und false, wenn sie ungültig sind.
    private fun isValidLogin(
        emailOrPhone: EditText,
        password: EditText,
    ): Boolean {
        val emailOrPhoneInput = emailOrPhone.text.toString().trim()
        val passwordInput = password.text.toString().trim()
        val loginInfo = db.getByEmail(emailOrPhoneInput)

        if (loginInfo == null) {
            Toast.makeText(
                this,
                "E-Mail doesn't exist, please create a new account",
                Toast.LENGTH_SHORT,
            ).show()
            return false
        }

        val loginInfoEmailFound = loginInfo[0]

        if (!BCrypt.checkpw(passwordInput, loginInfoEmailFound.password)) {
            Toast.makeText(
                this,
                "Password is wrong",
                Toast.LENGTH_SHORT,
            ).show()
            return false
        }

        return true
    }
}
