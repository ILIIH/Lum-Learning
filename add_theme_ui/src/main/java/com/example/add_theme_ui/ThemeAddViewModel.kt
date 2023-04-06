package com.example.add_theme_ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_theme_data.SaveTheme
import com.example.add_theme_data.Theme
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ThemeAddViewModel(
    private val saveTheme: SaveTheme,
    private val navigator: AddThemeNavigation,
) : ViewModel() {

    private val photo = MutableLiveData<Bitmap>()
    val _photo: LiveData<Bitmap>
        get() = photo

    private val photoURI = MutableLiveData<String>()
    fun setPhotoURI(URI: String) {
        photoURI.postValue(URI)
    }

    fun setPhoto(bitmap: Bitmap) {
        photo.postValue(bitmap)
    }

    fun addTheme(tile: String, yearExperience: Int, themeImportance: String, themeTesis: String) {
        viewModelScope.launch {
            val stream = ByteArrayOutputStream()
            photo.value!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageByteArray: ByteArray = stream.toByteArray()
            photo.value!!.recycle()
            saveTheme.execute(
                Theme(
                    tile,
                    photoURI.value!!,
                    yearExperience,
                    themeImportance,
                    themeTesis,
                    imageByteArray,
                ),
            )
        }

        navigator.submit()
    }
}
