package com.example.edit_ui.card_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Card
import com.example.ask_answer_data.ResultOf
import com.example.core.domain.ILError
import kotlinx.coroutines.launch

class CardListViewModel(
    private val repo: CardRepository,
) : ViewModel() {

    private val cardList = MutableLiveData<ResultOf<List<Card>>>()
    val _cardList: MutableLiveData<ResultOf<List<Card>>>
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
                cardList.postValue(ResultOf.Success(currentList))
            } catch (e: Throwable) {
                cardList.postValue(ResultOf.Failure(ILError.IO_ERROR))
            }
        }
    }
}
