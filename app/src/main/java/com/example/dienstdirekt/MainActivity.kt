package com.example.dienstdirekt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.dienstdirekt.databinding.ActivityMainBinding
import com.example.dienstdirekt.ui.categories.CategoriesActivity
import com.example.dienstdirekt.ui.register.RegisterActivity
import kotlin.math.log

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var button: ImageButton
    private lateinit var noAccountTextView : TextView
    private lateinit var db: MainDatabaseHelper

    private lateinit var emailOrPhone: EditText
    private lateinit var password: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = MainDatabaseHelper(this)


        noAccountTextView = findViewById(R.id.textView6)
        noAccountTextView.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
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


    private fun isValidLogin(emailOrPhone: EditText, password: EditText) : Boolean {
        val emailOrPhoneInput = emailOrPhone.text.toString().trim()
        val passwordInput = password.text.toString().trim()
        val loginInfo = db.getByEmail(emailOrPhoneInput)

        if (loginInfo == null){
            Toast.makeText(this, "E-Mail doesn't exist, please create a new account",
                Toast.LENGTH_SHORT).show()
            return false
        }

        val loginInfoEmailFound = loginInfo[0]

        if (loginInfoEmailFound.password != passwordInput) {
            Toast.makeText(this, "Password is wrong",
                Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }
}