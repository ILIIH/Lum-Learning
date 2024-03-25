package com.example.add_new_card.fragments.RuleFragment

import android.util.Log
import com.example.core.data.usecases.getMnemoTypePrediction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ThemeInfoProvider(private val getPrediction: getMnemoTypePrediction) {

    private var themeId: Int = 1

    private val themeType = MutableStateFlow<Int> (0)
    val _themeType: StateFlow<Int>
        get() = themeType

    suspend fun generatePrediction(changeType: Boolean = false) {
        val prediction = getPrediction.execute(changeType)
        themeType.tryEmit(prediction)
    }

    fun setThemeId(id: Int) {
        this.themeId = id
    }
    fun getThemeId() = themeId
    fun getThemeType() = themeType
}
