package com.example.dienstdirekt.ui.register

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.dienstdirekt.MainDatabaseHelper

class RegisterDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null,
    DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "dienstdirekt.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "dienstleister"

        private const val COLUMN_ID = "dienstleister_id"
        private const val COLUMN_NAME= "name"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PHONENUMBER = "telefonnummer"
        private const val COLUMN_PASSWORD = "passwort"


    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_EMAIL TEXT NOT NULL,
                $COLUMN_PHONENUMBER TEXT NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL
            );
        """
        db?.execSQL(createTableQuery)
        Log.d("DatabaseCreation", "Database created successfully")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertCompany(registerInput: RegisterInput) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, registerInput.companyName)
            put(COLUMN_EMAIL, registerInput.email)
            put(COLUMN_PHONENUMBER, registerInput.phoneNumber)
            put(COLUMN_PASSWORD, registerInput.password)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun checkIfExists(companyName: String, email: String, phoneNumber: String): Boolean {
        val db = writableDatabase
        val query = "SELECT * FROM dienstleister WHERE name=? OR email=? OR telefonnummer=?"
        val cursor = db.rawQuery(query, arrayOf(companyName, email, phoneNumber))

        if (cursor.count == 0) {
            cursor.close()
            db.close()
            return false
        }

        cursor.close()
        db.close()
        return true
    }
}