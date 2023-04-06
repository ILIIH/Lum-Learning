package com.example.add_new_card.fragments.AddLearningCard

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Answer
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VL_Card
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AddLearningCardViewmodel(private val repo: CardRepository) : ViewModel() {


    fun addNewCard(themeId: Int, question: String, answers: List<Answer>) {
        viewModelScope.launch {
               repo.incestCard(
                LearningCardDomain(
                    themeId = themeId,
                    question = question,
                    answers = answers,
                    RALastMonth = 0.0,
                    RACurrentMonth = 0.0,
                    AverageRA = 0.0,
                ),
            )
        }
    }
}
