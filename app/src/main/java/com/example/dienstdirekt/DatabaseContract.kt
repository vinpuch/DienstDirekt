package com.example.dienstdirekt

import android.provider.BaseColumns

object DatabaseContract {

    object UnternehmenEntry : BaseColumns {
        const val TABLE_NAME = "unternehmen"
        const val COLUMN_NAME = "name"
        const val COLUMN_DIENSTLEISTUNG = "dienstleistung"
        const val COLUMN_ORT = "ort"
    }
}