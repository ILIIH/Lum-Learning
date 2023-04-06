package com.example.add_new_card.fragments.RuleFragment

import androidx.lifecycle.ViewModel
import com.example.add_new_card_data.CardRepository

class MainFragmentViewModel(private val repo: getMnemoTypePrediction) : ViewModel() {

    private var themeId: Int = 0
    fun setThemeId(id: Int) {
        this.themeId = id
    }
    fun getThemeId() = themeId
}
