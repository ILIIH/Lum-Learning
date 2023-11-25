package com.example.add_theme_ui

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_theme_data.SaveTheme
import com.example.add_theme_data.Theme
import com.example.ask_answer_data.ResultOf
import com.example.core.domain.ILError
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class ThemeAddViewModel(
    private val saveTheme: SaveTheme,
    private val navigator: AddThemeNavigation,
) : ViewModel() {

    private val photo = MutableLiveData<Bitmap>()
    val _photo: LiveData<Bitmap>
        get() = photo

    private val validation = MutableLiveData<ILError>()
    val _validation: LiveData<ILError>
        get() = validation

    private val photoURI = MutableLiveData<String>()
    fun setPhotoURI(URI: String) {
        photoURI.postValue(URI)
    }

    fun setPhoto(bitmap: Bitmap) {
        photo.postValue(bitmap)
    }
    fun validateFields(title: String, yearExperience: String): Boolean {
        return when {
            title.isEmpty() -> {
                validation.postValue(ILError.VALIDATION_TITLE)
                false
            }
            yearExperience.isEmpty() -> {
                validation.postValue(ILError.VALIDATION_YEAR)
                false
            }
            photoURI.value.isNullOrEmpty() -> {
                validation.postValue(ILError.VALIDATION_PHOTO)
                false
            }
            else -> true
        }
    }

    fun addTheme(tile: String, yearExperience: String, themeImportance: String, themeTesis: String) {
        if(!validateFields(tile, yearExperience)) return
        viewModelScope.launch {
            val stream = ByteArrayOutputStream()
            photo.value!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageByteArray: ByteArray = stream.toByteArray()
            photo.value!!.recycle()
            saveTheme.execute(
                Theme(
                    tile,
                    photoURI.value!!,
                    yearExperience.toInt(),
                    themeImportance,
                    themeTesis,
                    imageByteArray,
                ),
            )
        }

        navigator.submit()
    }
}
