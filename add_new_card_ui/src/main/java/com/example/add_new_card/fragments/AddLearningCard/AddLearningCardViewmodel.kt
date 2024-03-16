package com.example.add_new_card.fragments.AddLearningCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Answer
import com.example.add_new_card_data.model.LearningCardDomain
import kotlinx.coroutines.launch

class AddLearningCardViewmodel(private val repo: CardRepository) : ViewModel() {

    private var _answers = mutableListOf(Answer("", "", true))
    fun getAnswers():List<Answer>  = _answers

    fun reInitAnswers() {
        _answers.clear()
        _answers.add(Answer("", "", true))
    }
    fun addAnswer() {
        _answers.add(Answer("", "", true))
    }

    fun addNewCard(
        themeId: Int,
        question: String,
        answers: List<Answer>,
        themeType: Int,
        currentDate: String,
        description: String,
    ) {
        viewModelScope.launch {
            repo.insertCard(
                LearningCardDomain(
                    themeId = themeId,
                    question = question,
                    answers = answers,
                    themeType = themeType,
                    dateCreation = currentDate,
                    description = description,
                    id = 0,
                ),
            )
        }
    }
}
