package com.example.memorizable.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.provider.MediaStore
import androidx.core.net.toUri
import com.example.core.DB.domain.photoLoader

class photoLoaderImp(private val context: Context) : photoLoader {

    override fun getPhoto(imageURI: String): Bitmap {
        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    imageURI.toUri()
                )
            )
        } else {
            MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                imageURI.toUri()
            )
        }
        return bitmap
    }
}
