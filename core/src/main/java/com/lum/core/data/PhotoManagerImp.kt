package com.lum.core.data

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class PhotoManagerImp(private val context: Context): PhotoManager {
    override fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width: Int = image.width
        var height: Int = image.height
        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }
        return Bitmap.createScaledBitmap(image, width, height, true)
    }

    override fun imageDecode(photoUri: Uri): Bitmap {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(
                ImageDecoder.createSource(
                    context.contentResolver,
                    photoUri
                )
            )
        } else {
            MediaStore.Images.Media.getBitmap(
                context.contentResolver,
                photoUri
            )
        }
    }

    override fun saveImageToGallery(bitmap: Bitmap): Uri? {
        val contentResolver = context.contentResolver

        val contentValues = ContentValues().apply {put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg") }

        val collection = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }

        val imageUri: Uri? = contentResolver?.insert(collection, contentValues)

        imageUri?.let { uri ->
            try {
                val outputStream = contentResolver.openOutputStream(uri)
                if(outputStream!=null)
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                outputStream?.close()
            } catch (e: Exception) {
                e.printStackTrace()
                return null
            }

            return imageUri
        }

        return null
    }


}