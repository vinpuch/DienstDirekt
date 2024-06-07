package com.example.dienstdirekt

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Unternehmen.db"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${DatabaseContract.UnternehmenEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${DatabaseContract.UnternehmenEntry.COLUMN_NAME} TEXT," +
                    "${DatabaseContract.UnternehmenEntry.COLUMN_DIENSTLEISTUNG} TEXT," +
                    "${DatabaseContract.UnternehmenEntry.COLUMN_ORT} TEXT)"

        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${DatabaseContract.UnternehmenEntry.TABLE_NAME}")
        onCreate(db)
    }
}