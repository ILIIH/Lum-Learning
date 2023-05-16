package com.example.core.DB.domain

import android.graphics.Bitmap

interface photoLoader {
    fun getPhoto(imageURI: String): Bitmap
}