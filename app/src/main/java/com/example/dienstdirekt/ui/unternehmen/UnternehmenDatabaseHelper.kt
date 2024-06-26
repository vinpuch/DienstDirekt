package com.example.dienstdirekt.ui.unternehmen

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

// Diese Klasse hilft bei der Interaktion mit der Unternehmensdatenbank.
class UnternehmenDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "dienstdirekt.db"
        const val DATABASE_VERSION = 2
    }

    // Diese Methode wird aufgerufen, wenn die Datenbank erstellt wird.
    // Sie erstellt die Tabelle "unternehmen".
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_QUERY =
            "CREATE TABLE IF NOT EXISTS unternehmen (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "dienstleistung TEXT," +
                "ort TEXT," +
                "picture BLOB," +
                "certificate BLOB," +
                "beschreibung TEXT)"
        db.execSQL(CREATE_TABLE_QUERY)

        Log.d("TEST", "Datenbank wurde angelegt")
    }

    // Diese Methode wird aufgerufen, wenn die Datenbank aktualisiert wird.
    // Sie löscht die alte Tabelle und erstellt eine neue.
    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int,
    ) {
        db.execSQL("DROP TABLE IF EXISTS unternehmen")
        onCreate(db)
    }

    // Diese Methode holt ein Bild aus der Datenbank.
    // Sie konvertiert das Bild von einem ByteArray zu einem Bitmap.
    @SuppressLint("Range")
    fun retrievePictureFromDatabase(
        context: Context,
        companyName: Long,
    ): Bitmap? {
        val db = readableDatabase
        val projection = arrayOf("picture")
        val selection = "name = ?"
        val selectionArgs = arrayOf(companyName.toString())
        val cursor =
            db.query(
                "unternehmen", // Table name
                projection, // Columns to fetch
                selection, // WHERE clause
                selectionArgs, // WHERE clause arguments
                null, // GROUP BY
                null, // HAVING
                null, // ORDER BY
            )

        var pictureBitmap: Bitmap? = null
        if (cursor.moveToFirst()) {
            val pictureByteArray = cursor.getBlob(cursor.getColumnIndex("picture"))
            pictureBitmap = BitmapFactory.decodeByteArray(pictureByteArray, 0, pictureByteArray.size)
        }

        cursor.close()
        db.close()

        return pictureBitmap
    }

    // Diese Methode holt alle Unternehmen aus der Datenbank.
    // Sie gibt eine Liste von UnternehmenData-Objekten zurück.
    fun getAll(): List<UnternehmenData> {
        val companyList = mutableListOf<UnternehmenData>()
        val db = readableDatabase
        val query = "SELECT * FROM unternehmen"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val companyName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
            val dienstleistung = cursor.getString(cursor.getColumnIndexOrThrow("dienstleistung"))
            val ort = cursor.getString(cursor.getColumnIndexOrThrow("ort"))
            val pictureByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("picture"))
            val certificateByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("certificate"))
            val beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("beschreibung"))
            val companyData = UnternehmenData(companyName, dienstleistung, ort, pictureByteArray, certificateByteArray, beschreibung)
            companyList.add(companyData)
        }

        cursor.close()
        db.close()
        return companyList
    }

    // Diese Methode holt alle Unternehmen aus der Datenbank und sortiert sie nach dem Namen.
    // Sie gibt eine Liste von UnternehmenData-Objekten zurück.
    // Wenn asc true ist, werden die Unternehmen in aufsteigender Reihenfolge sortiert.
    // Wenn asc false ist, werden die Unternehmen in absteigender Reihenfolge sortiert.
    fun getOrderByName(asc: Boolean): List<UnternehmenData> {
        val companyList = mutableListOf<UnternehmenData>()
        val db = readableDatabase
        if (asc) {
            val query = "SELECT * FROM unternehmen ORDER BY name ASC"
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val companyName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val dienstleistung = cursor.getString(cursor.getColumnIndexOrThrow("dienstleistung"))
                val ort = cursor.getString(cursor.getColumnIndexOrThrow("ort"))
                val pictureByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("picture"))
                val certificateByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("certificate"))
                val beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("beschreibung"))
                val companyData = UnternehmenData(companyName, dienstleistung, ort, pictureByteArray, certificateByteArray, beschreibung)
                companyList.add(companyData)
            }

            cursor.close()
        } else {
            val query = "SELECT * FROM unternehmen ORDER BY name  DESC"
            val cursor = db.rawQuery(query, null)

            while (cursor.moveToNext()) {
                val companyName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val dienstleistung = cursor.getString(cursor.getColumnIndexOrThrow("dienstleistung"))
                val ort = cursor.getString(cursor.getColumnIndexOrThrow("ort"))
                val pictureByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("picture"))
                val certificateByteArray = cursor.getBlob(cursor.getColumnIndexOrThrow("certificate"))
                val beschreibung = cursor.getString(cursor.getColumnIndexOrThrow("beschreibung"))
                val companyData = UnternehmenData(companyName, dienstleistung, ort, pictureByteArray, certificateByteArray, beschreibung)
                companyList.add(companyData)
            }

            cursor.close()
        }

        db.close()
        return companyList
    }
}
