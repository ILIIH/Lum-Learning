package com.example.add_new_card.fragments.RuleFragment

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.add_new_card_data.CardRepository

class MainFragmentViewModel(private val repo: CardRepository) : ViewModel() {

    private var themeId: Int = 0
    fun setThemeId(id: Int){
        this.themeId = id
    }
    fun getThemeId() = themeId

}
