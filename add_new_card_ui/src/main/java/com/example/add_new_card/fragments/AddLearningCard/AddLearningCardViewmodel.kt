package com.example.add_new_card.fragments.AddLearningCard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Answer
import com.example.add_new_card_data.model.LearningCardDomain
import kotlinx.coroutines.launch

class AddLearningCardViewmodel(private val repo: CardRepository) : ViewModel() {

    val answers = mutableListOf(Answer("", "", true))

    fun addAnswer() {
        answers.add(Answer("", "", true))
    }

    fun addNewCard(
        themeId: Int,
        question: String,
        answers: List<Answer>,
        themeType: Int,
        currentDate: String,
        description: String,
        monthNumber: Int,
    ) {
        viewModelScope.launch {
            repo.insertCard(
                LearningCardDomain(
                    themeId = themeId,
                    question = question,
                    answers = answers,
                    RALastMonth = 0.0,
                    RACurrentMonth = 0.0,
                    AverageRA = 0.0,
                    themeType = themeType,
                    dateCreation = currentDate,
                    discription = description,
                    repetitionAmount = 0,
                    lastMonthUpdateNumber = monthNumber,
                    lastMonthRepetitionNumber = 0,
                    Id = 0,
                ),
            )
        }
    }
}
