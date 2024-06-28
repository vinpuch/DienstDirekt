package com.example.dienstdirekt.ui.register

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class RegisterDatabaseHelper(context: Context) : SQLiteOpenHelper(
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

    // Diese Funktion erstellt die Datenbank, wenn sie noch nicht existiert.
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

    // Diese Funktion aktualisiert die Datenbank, wenn eine neue Version verfügbar ist.
    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Diese Funktion fügt ein neues Unternehmen in die Datenbank ein.
    fun insertCompany(registerInput: RegisterInput) {
        val db = writableDatabase
        val values =
            ContentValues().apply {
                put(COLUMN_NAME, registerInput.companyName)
                put(COLUMN_EMAIL, registerInput.email)
                put(COLUMN_PHONENUMBER, registerInput.phoneNumber)
                put(COLUMN_PASSWORD, registerInput.password)
            }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    // Diese Funktion überprüft, ob ein Unternehmen bereits in der Datenbank existiert.
    fun checkIfExists(
        companyName: String,
        email: String,
        phoneNumber: String,
    ): Boolean {
        val db = readableDatabase
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

    // Diese Funktion gibt eine Liste aller Unternehmen in der Datenbank zurück.
    fun getAll(): List<RegisterInput> {
        val companyList = mutableListOf<RegisterInput>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val companyName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
            val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONENUMBER))
            val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))

            val companyData = RegisterInput(companyName, email, phoneNumber, password)
            companyList.add(companyData)
        }

        cursor.close()
        db.close()
        return companyList
    }

    // Diese Funktion gibt eine Liste aller Unternehmen in der Datenbank zurück,
    // sortiert nach dem Firmennamen.
    fun getOrderByName(asc: Boolean): List<RegisterInput> {
        val companyList = mutableListOf<RegisterInput>()
        val db = readableDatabase
        if (asc) {
            val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_NAME ASC"
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val companyName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONENUMBER))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))

                val companyData = RegisterInput(companyName, email, phoneNumber, password)
                companyList.add(companyData)
            }

            cursor.close()
        } else {
            val query = "SELECT * FROM $TABLE_NAME ORDER BY $COLUMN_NAME DESC"
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val companyName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
                val email = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EMAIL))
                val phoneNumber = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHONENUMBER))
                val password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD))

                val companyData = RegisterInput(companyName, email, phoneNumber, password)
                companyList.add(companyData)
            }

            cursor.close()
        }

        db.close()
        return companyList
    }
}
