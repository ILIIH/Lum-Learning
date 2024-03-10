package com.example.add_new_card.fragments.AddVisualCard

import android.graphics.Bitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Answer
import com.example.add_new_card_data.model.VA_Card
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream

class AddVisualCardViewmodel(private val repo: CardRepository) : ViewModel() {

    private val photo = MutableLiveData<Bitmap?>(null)
    val _photo: LiveData<Bitmap?>
        get() = photo

    private var _answers = mutableListOf(Answer("", "", true))
    fun getAnswers():List<Answer>  = _answers
    fun setPhoto(bitmap: Bitmap) {
        photo.postValue(bitmap)
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
            repo.insertVACard(
                VA_Card(
                    themeId = themeId,
                    question = question,
                    answers = answers,
                    photo = image,
                    dateCreation = currentDate,
                    Id = 0,
                ),
            )
        }
    }
}
