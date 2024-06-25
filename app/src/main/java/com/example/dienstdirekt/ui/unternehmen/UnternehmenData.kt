package com.example.dienstdirekt.ui.unternehmen

data class UnternehmenData(
    val name: String,
    val dienstleistung: String,
    val ort: String,
    val picture: ByteArray?,
    val certificate: ByteArray?,
    val beschreibung: String,
)
