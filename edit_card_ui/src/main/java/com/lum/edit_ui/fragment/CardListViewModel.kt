package com.lum.edit_ui.fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lum.add_new_card_data.CardRepository
import com.lum.add_new_card_data.model.Card
import com.lum.ask_answer_data.ResultOf
import com.lum.core.domain.ILError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CardListViewModel(
    private val repo: CardRepository,
) : ViewModel() {

    private val cardList = MutableStateFlow<ResultOf<List<Card>>>(ResultOf.Loading(listOf()))
    val _cardList: StateFlow<ResultOf<List<Card>>>
        get() = cardList

    fun deleteCardById(id: Int) {
        viewModelScope.launch {
            repo.deleteCard(id)
        }
    }

    fun downloadCards(id: Int) {
        viewModelScope.launch {
            try {
                val currentList = repo.getAllCardByThemeId(id)
                cardList.tryEmit(ResultOf.Success(currentList))
            } catch (e: Throwable) {
                cardList.tryEmit(ResultOf.Failure(ILError.IO_ERROR))
            }
        }
    }
}
