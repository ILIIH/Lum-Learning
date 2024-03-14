package com.example.add_theme_ui.viewModels

import android.content.Context
import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_theme_data.SaveTheme
import com.example.add_theme_data.Theme
import com.example.add_theme_ui.AddThemeNavigation
import com.example.core.domain.ILError
import com.example.core.domain.Scopes
import kotlinx.coroutines.launch
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.java.KoinJavaComponent.getKoin
import java.io.ByteArrayOutputStream
class ThemeAddViewModel() : ViewModel() {

    private val saveTheme: SaveTheme
    private val navigator: AddThemeNavigation
    private val scope: Scope = getKoin().getOrCreateScope(Scopes.ADD_NEW_THEME_SCOPE.scope, named(Scopes.ADD_NEW_THEME_SCOPE.scope) )

    private lateinit var themeName: String
    private lateinit var themeImportance: String
    private var themeType: Int = 1

    private var yearOfExperience: Int = 0

    private val photo = MutableLiveData<Bitmap>()
    val _photo: LiveData<Bitmap>
        get() = photo

    private val validation = MutableLiveData<ILError>()
    val _validation: LiveData<ILError>
        get() = validation

    private val photoURI = MutableLiveData<String>()

    init {
        saveTheme = scope.get<SaveTheme>()
        navigator = scope.get<AddThemeNavigation>()
    }

    override fun onCleared() {
        scope.close()
        super.onCleared()
    }
    fun setThemeName(name: String) : Boolean{
        return if(name.isEmpty()) {
            false
        } else {
            themeName = name
            true
        }
    }
    fun setThemeType(type: Int) {
        themeType = type
    }
    fun setThemeImportance(importance: String) {
        themeImportance = importance
    }
    fun setExperience(expLevel: Int) {
        yearOfExperience = expLevel
    }
    fun setPhotoURI(URI: String) {
        photoURI.postValue(URI)
    }

    fun setPhoto(bitmap: Bitmap) {
        photo.postValue(bitmap)
    }
    private fun validatePhoto(): Boolean {
        return if ( photoURI.value.isNullOrEmpty() )  {
            validation.postValue(ILError.VALIDATION_PHOTO)
            false
        } else true
    }

    fun addTheme() {
        if(!validatePhoto()) return
        else {
            viewModelScope.launch {
                val stream = ByteArrayOutputStream()
                photo.value!!.compress(Bitmap.CompressFormat.PNG, 100, stream)
                val imageByteArray: ByteArray = stream.toByteArray()
                photo.value!!.recycle()
                saveTheme.execute(
                    Theme(
                        themeName,
                        photoURI.value!!,
                        yearOfExperience,
                        themeImportance,
                        themeType,
                        imageByteArray,
                    ),
                )
            }

            navigator.submitNewTheme()
        }
    }
}
