package com.example.core.data

import android.graphics.Bitmap
import android.net.Uri

interface PhotoManager {
    fun saveImageToGallery(bitmap: Bitmap): Uri?
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap
    fun imageDecode(photoUri:Uri): Bitmap
}