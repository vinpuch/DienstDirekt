package com.example.dienstdirekt.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast

class CompanyRegistration(private val activity: Activity) {
    private val PICK_IMAGE_REQUEST = 1
    fun onPictureButtonClicked() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        activity.startActivityForResult(
            Intent.createChooser(intent, "Wählen Sie ein Bild"),
            PICK_IMAGE_REQUEST
        )
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val filePath: Uri? = data.data
            Toast.makeText(activity, "Bild wurde ausgewählt: $filePath", Toast.LENGTH_SHORT).show()
            // Hier können Sie das ausgewählte Bild verwenden
        }
    }

}

