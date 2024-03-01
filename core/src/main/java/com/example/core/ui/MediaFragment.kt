package com.example.core.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts


abstract class MediaFragment : BaseFragment() {

    abstract fun saveGalleryImageData(uri: Uri)
    abstract fun saveCameraImageData(bitmap: Bitmap)

    val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val data = result.data!!

                if (data.data != null) {
                    // Selected from gallery
                    val photoUri: Uri = data.data!!
                    saveGalleryImageData(photoUri)
                } else {
                    // Captured from camera
                    val imageBitmap: Bitmap = data.extras?.get("data") as Bitmap
                    saveCameraImageData(imageBitmap)
                }
            }
        }
}
