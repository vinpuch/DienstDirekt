package com.example.dienstdirekt

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.dienstdirekt.ui.register.RegisterInput

class MainDatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION,
) {

    companion object {
        private const val DATABASE_NAME = "dienstdirekt.db"
        private const val DATABASE_VERSION = 2
        private const val TABLE_NAME = "dienstleister"

        private const val COLUMN_ID = "dienstleister_id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PHONENUMBER = "telefonnummer"
        private const val COLUMN_PASSWORD = "passwort"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        Log.d("Database Operations", "Database created.")
        // TODO:
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        // TODO:
    }

    fun getByEmail(email: String): List<RegisterInput>? {
        val account = mutableListOf<RegisterInput>()
        val db = writableDatabase
        val query = "SELECT * FROM dienstleister WHERE email=?"
        val cursor = db.rawQuery(query, arrayOf(email))

        if (cursor.count == 0) {
            return null
        }

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val companyName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONENUMBER))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))

            val accountInfo = RegisterInput(companyName, email, phoneNumber, password)
            account.add(accountInfo)
        }

        cursor.close()
        db.close()
        return account
    }
}
