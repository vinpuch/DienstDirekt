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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistercompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = RegisterDatabaseHelper(this)

        crossLeft = findViewById(R.id.imageView8)
        crossLeft.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        companyName = findViewById(R.id.textUnternehmenname)
        eMail = findViewById(R.id.textEmail)
        phoneNumber = findViewById(R.id.textHandynummer)
        password = findViewById(R.id.textPasswort)
        passwordRepeat = findViewById(R.id.textPasswortNochmal)


        registerButton = findViewById(R.id.buttonPage2)
        registerButton.setOnClickListener {
            val companyNameInput = companyName.text.toString().trim()
            val eMailInput = eMail.text.toString().trim()
            val phoneNumberInput = phoneNumber.text.toString().trim()
            val passwordInput = password.text.toString().trim()
            val passwordRepeatInput = passwordRepeat.text.toString().trim()

            if (isValidRegistration(companyNameInput, eMailInput, phoneNumberInput, passwordInput,
                    passwordRepeatInput)) {
                if (!db.checkIfExists(companyNameInput, eMailInput, phoneNumberInput)){
                    Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
                    val registerInput = RegisterInput(companyNameInput, eMailInput, phoneNumberInput,
                        passwordInput)
                    db.insertCompany(registerInput)
                    finish()
                    Toast.makeText(this, "DB angelegt", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "company name, email or phonenumber already used",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun isValidRegistration(companyNameInput: String, eMailInput: String,  phoneNumberInput: String,
                                    passwordInput: String, passwordRepeatInput: String) : Boolean {
        if (!isValidCompanyName(companyNameInput)) {
            Toast.makeText(this, "please enter a valid Company Name",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidEmail(eMailInput)) {
            Toast.makeText(this, "please enter a valid email",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidPhoneNumber(phoneNumberInput)) {
            Toast.makeText(this, "please enter a valid phone number",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidPassword(passwordInput)) {
            Toast.makeText(this, "please enter a valid password",
                Toast.LENGTH_SHORT).show()
            return false
        }

        if (!isValidPassword(passwordRepeatInput)) {
            if (passwordRepeatInput != passwordInput) {
                Toast.makeText(this, "passwords do not match",
                    Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }

    private fun isValidCompanyName(name: String): Boolean {
        return name.matches(Regex("^(?=.*[a-zA-Z])[a-zA-Z0-9&.,'\\-\\s]+$"))
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    }

    private fun isValidPhoneNumber(phoneNumber: String): Boolean {
        return phoneNumber.matches(Regex("^\\+?[0-9. ()-]{7,15}\$"))
    }


    // password is at least 8 characters long and contains at least one uppercase letter,
    // one lowercase letter, one digit, and one special character
    // Example: Password1!
    private fun isValidPassword(password: String): Boolean {
        return password.matches(
            Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
        )
    }
}