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
    private lateinit var registerButton: ImageButton
    private lateinit var crossLeft: ImageView
    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var birthDate: EditText
    private lateinit var eMail: EditText
    private lateinit var handyNumber: EditText
    private lateinit var password: EditText
    private lateinit var passwordRepeat: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistercompanyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        crossLeft = findViewById(R.id.imageView8)
        crossLeft.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        firstName = findViewById(R.id.textVorname)
        lastName = findViewById(R.id.textNachname)
        birthDate = findViewById(R.id.textGeburtstag)
        eMail = findViewById(R.id.textEmail)
        handyNumber = findViewById(R.id.textHandynummer)
        password = findViewById(R.id.textPasswort)
        passwordRepeat = findViewById(R.id.textPasswortNochmal)

        registerButton = findViewById(R.id.buttonPage2)
        registerButton.setOnClickListener {
            if (isValidRegistration(firstName, lastName, birthDate, eMail, password,
                    passwordRepeat)) {
                Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun isValidRegistration(firstName: EditText, lastName: EditText, birthDate: EditText,
                                    eMail: EditText, password: EditText,
                                    passwordRepeat: EditText) : Boolean {
        val firstNameInput = firstName.text.toString().trim()
        if (!isValidName(firstNameInput)) {
            Toast.makeText(this, "please enter a valid first name",
                Toast.LENGTH_SHORT).show()
            return false
        }

        val lastNameInput = lastName.text.toString().trim()
        if (!isValidName(lastNameInput)) {
            Toast.makeText(this, "please enter a valid last name",
                Toast.LENGTH_SHORT).show()
            return false
        }

        val birthDateInput = birthDate.text.toString().trim()
        if (!isValidBirthDate(birthDateInput)) {
            Toast.makeText(this, "please enter a valid birth date",
                Toast.LENGTH_SHORT).show()
            return false
        }

        val eMailInput = eMail.text.toString().trim()
        if (!isValidEmail(eMailInput)) {
            Toast.makeText(this, "please enter a valid email ",
                Toast.LENGTH_SHORT).show()
            return false
        }

        val passwordInput = password.text.toString().trim()
        if (!isValidPassword(passwordInput)) {
            Toast.makeText(this, "please enter a valid password ",
                Toast.LENGTH_SHORT).show()
            return false
        }

        val passwordRepeatInput = passwordRepeat.text.toString().trim()
        if (!isValidPassword(passwordRepeatInput)) {
            if (passwordRepeatInput != passwordInput) {
                Toast.makeText(this, "passwords do not match ",
                    Toast.LENGTH_SHORT).show()
                return false
            }
        }

        return true
    }

    private fun isValidName(name: String): Boolean {
        return name.matches(Regex("^[a-zA-Z]+$"))
    }

    private fun isValidBirthDate(birthDate: String): Boolean {
        return birthDate.matches(Regex("^\\d{4}-\\d{2}-\\d{2}$"))
    }

    private fun isValidEmail(email: String): Boolean {
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
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