package com.example.dienstdirekt.ui.register

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.MainActivity
import com.example.dienstdirekt.R
import com.example.dienstdirekt.databinding.ActivityRegistercompanyBinding
import com.example.dienstdirekt.ui.unternehmen.UnternehmenActivity
import org.mindrot.jbcrypt.BCrypt

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegistercompanyBinding
    private lateinit var db: RegisterDatabaseHelper
    private lateinit var registerButton: ImageButton
    private lateinit var crossLeft: ImageView
    private lateinit var companyName: EditText
    private lateinit var eMail: EditText
    private lateinit var phoneNumber: EditText
    private lateinit var password: EditText
    private lateinit var passwordRepeat: EditText

    // Diese Funktion wird aufgerufen, wenn die Aktivität erstellt wird.
    // Sie initialisiert die Benutzeroberfläche und setzt die Listener für die Schaltflächen.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistercompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = RegisterDatabaseHelper(this)
        companyName = findViewById(R.id.textUnternehmennamen)
        eMail = findViewById(R.id.textEmail)
        phoneNumber = findViewById(R.id.textHandynummer)
        password = findViewById(R.id.textPasswort)
        passwordRepeat = findViewById(R.id.textPasswortNochmal)

        crossLeft = findViewById(R.id.crossLeftRegister)
        crossLeft.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        registerButton = findViewById(R.id.weiter_button_registrieren)
        registerButton.setOnClickListener {
            val companyNameInput = companyName.text.toString().trim()
            val eMailInput = eMail.text.toString().trim()
            val phoneNumberInput = phoneNumber.text.toString().trim()
            val passwordInput = password.text.toString().trim()
            val passwordRepeatInput = passwordRepeat.text.toString().trim()

            if (isValidRegistration(companyNameInput, eMailInput, phoneNumberInput, passwordInput, passwordRepeatInput)) {
                // Hash the password before saving to the database
                val hashedPassword = BCrypt.hashpw(passwordInput, BCrypt.gensalt())

                val registerInput = RegisterInput(companyNameInput, eMailInput, phoneNumberInput, hashedPassword)
                db.insertCompany(registerInput)

                val intent = Intent(this, UnternehmenActivity::class.java)
                startActivity(intent)
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "DB angelegt", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /// Diese Funktion überprüft, ob die Eingaben des Benutzers für die Registrierung gültig sind.
    // Sie überprüft den Firmennamen, die E-Mail, die Telefonnummer und das Passwort.
    private fun isValidRegistration(
        companyNameInput: String,
        eMailInput: String,
        phoneNumberInput: String,
        passwordInput: String,
        passwordRepeatInput: String,
    ): Boolean {
        if (!isValidCompanyName(companyNameInput)) {
            Toast.makeText(this, "please enter a valid Company Name", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidEmail(eMailInput)) {
            Toast.makeText(this, "please enter a valid email", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidPhoneNumber(phoneNumberInput)) {
            Toast.makeText(this, "please enter a valid phone number", Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidPassword(passwordInput)) {
            Toast.makeText(this, "please enter a valid password", Toast.LENGTH_SHORT).show()
            return false
        }

        if (passwordRepeatInput != passwordInput) {
            Toast.makeText(this, "passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    // Diese Funktion überprüft, ob der eingegebene Firmenname gültig ist.
    // Ein gültiger Firmenname muss mindestens einen Buchstaben enthalten und darf nur
    // Buchstaben, Zahlen, &, ., ', - und Leerzeichen enthalten.
    private fun isValidCompanyName(name: String): Boolean {
        return name.matches(Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9&.,'\\-\\s]+$"))
    }

    /// Diese Funktion überprüft, ob die eingegebene E-Mail-Adresse gültig ist.
    // Eine gültige E-Mail-Adresse muss der Standardform entsprechen.
    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    }

    // Diese Funktion überprüft, ob die eingegebene Telefonnummer gültig ist.
    // Eine gültige Telefonnummer muss einer Standardtelefonnummer entsprechen.
    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("^\\+?[0-9. ()-]{7,15}\$"))
    }

    // Diese Funktion überprüft, ob das eingegebene Passwort gültig ist.
    // Ein gültiges Passwort muss mindestens einen Kleinbuchstaben, einen Großbuchstaben, eine Zahl
    // und ein Sonderzeichen enthalten und mindestens 8 Zeichen lang sein.
    // Gutes Beispiel wäre: Passwort123!
    private fun isValidPassword(password: String): Boolean {
        return password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"))
    }
}
