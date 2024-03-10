package com.example.add_new_card.fragments.RuleFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.core.data.usecases.getMnemoTypePrediction
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ThemeInfoProvider(private val getPrediction: getMnemoTypePrediction) {

    private var themeId: Int = 1

    private val themeType = MutableLiveData<Int> ()
    val _themeType: LiveData<Int>
        get() = themeType

    suspend fun generatePrediction() {
        val prediction = getPrediction.execute()
        themeType.postValue(prediction)
    }

    fun setThemeId(id: Int) {
        this.themeId = id
    }
    fun getThemeId() = themeId
    fun getThemeType() = themeType
}
