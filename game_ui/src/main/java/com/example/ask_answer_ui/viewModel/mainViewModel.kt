package com.example.ask_answer_ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.usecases.insertGameResult
import com.example.core.domain.models.gameResult
import kotlinx.coroutines.launch

class mainViewModel(val insertResult: insertGameResult) : ViewModel() {
    fun saveGameResult(gameResult: gameResult) {
        viewModelScope.launch {
            insertResult.execute(gameResult)
        }
    }
}
