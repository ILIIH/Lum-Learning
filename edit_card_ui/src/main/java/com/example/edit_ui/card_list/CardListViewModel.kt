package com.example.edit_ui.card_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.ask_answer_data.ResultOf
import com.example.core.domain.ILError
import com.example.edit_ui.data.Card
import com.example.edit_ui.data.toCard
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Error
import java.util.ArrayList

class CardListViewModel(
    private val repo: CardRepository,
) : ViewModel() {

    private val cardList = MutableLiveData<ResultOf<ArrayList<Card>>>()
    val _cardList: MutableLiveData<ResultOf<ArrayList<Card>>>
        get() = cardList

    fun deleteLearningCardById(id: Int) {
        viewModelScope.launch {
            repo.deleteLearningCard(id)
        }
    }

    fun deleteALCardById(id: Int) {
        viewModelScope.launch {
            repo.deleteALCard(id)
        }
    }

    fun deleteVACardById(id: Int) {
        viewModelScope.launch {
            repo.deleteVACard(id)
        }
    }

    fun downloadCards(id: Int) {
        viewModelScope.launch {
            try {
                val currentList = ArrayList<Card>(100)
                currentList.addAll(repo.getAllALCardByThemeId(id).map { it.toCard() })
                currentList.addAll(repo.getAllVACardByThemeId(id).map { it.toCard() })
                currentList.addAll(repo.getAllCardByThemeId(id).map { it.toCard() })
                cardList.postValue(ResultOf.Success(currentList))
            } catch (e: Throwable) {
                cardList.postValue(ResultOf.Failure(ILError.IO_ERROR))
            }
        }
    }
}
