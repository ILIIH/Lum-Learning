package com.example.add_new_card.fragments.AddVisualCard

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Answer
import com.example.add_new_card_data.model.VA_Card
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AddVisualCardViewmodel(private val repo: CardRepository) : ViewModel() {

    private val photo = MutableStateFlow<Bitmap?>(null)
    val _photo: StateFlow<Bitmap?>
        get() = photo

    private var _answers = mutableListOf(Answer("", "", true))
    fun getAnswers():List<Answer>  = _answers
    fun setPhoto(bitmap: Bitmap) {
        photo.tryEmit(bitmap)
    }

    fun reInitAnswers() {
        _answers.clear()
        _answers.add(Answer("", "", true))
    }
    fun addAnswer() {
        _answers.add(Answer("", "", true))
    }

    fun addNewCard(themeId: Int, question: String, answers: List<Answer>, currentDate: String) {
        viewModelScope.launch {
            val stream = ByteArrayOutputStream()
            photo.value?.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()
            repo.insertCard(
                VA_Card(
                    themeId = themeId,
                    question = question,
                    answers = answers,
                    photo = image,
                    dateCreation = currentDate,
                    id = 0,
                ),
            )
        }
    }
}
