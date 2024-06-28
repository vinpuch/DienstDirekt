package com.example.dienstdirekt.ui.unternehmen

// Diese Datenklasse repräsentiert die Daten eines Unternehmens.
data class UnternehmenData(
    val name: String,
    val dienstleistung: String,
    val ort: String,
    val picture: ByteArray?,
    val certificate: ByteArray?,
    val beschreibung: String,
)
