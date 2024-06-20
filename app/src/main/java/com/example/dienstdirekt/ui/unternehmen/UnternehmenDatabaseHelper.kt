package com.example.dienstdirekt.ui.unternehmen

import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log

class UnternehmenDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        const val DATABASE_NAME = "dienstdirekt.db"
        const val DATABASE_VERSION = 2
    }

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TABLE_QUERY = "CREATE TABLE IF NOT EXISTS unternehmen (" +
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

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS unternehmen")
        onCreate(db)
    }

    @SuppressLint("Range")
    fun retrievePictureFromDatabase(context: Context, companyName: Long): Bitmap? {
        val db = readableDatabase
        val projection = arrayOf("picture")
        val selection = "name = ?"
        val selectionArgs = arrayOf(companyName.toString())
        val cursor = db.query(
            "unternehmen",   // Table name
            projection,      // Columns to fetch
            selection,       // WHERE clause
            selectionArgs,   // WHERE clause arguments
            null,            // GROUP BY
            null,            // HAVING
            null             // ORDER BY
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

    fun getOrderByName(asc: Boolean) : List<UnternehmenData> {
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