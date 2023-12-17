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

    private val photo = MutableLiveData<Bitmap>()
    val _photo: LiveData<Bitmap>
        get() = photo

    val answers: List<Answer> = listOf(Answer("", "", true), Answer("", "", true), Answer("", "", true), Answer("", "", true))

    fun setPhoto(bitmap: Bitmap) {
        photo.postValue(bitmap)
    }

    fun addNewCard(themeId: Int, question: String, answers: List<Answer>, currentDate: String, monthNumber: Int) {
        viewModelScope.launch {
            val bitmap = photo.value
            val stream = ByteArrayOutputStream()
            bitmap!!.compress(Bitmap.CompressFormat.PNG, 90, stream)
            val image = stream.toByteArray()
            repo.insertVACard(
                VA_Card(
                    themeId = themeId,
                    question = question,
                    answers = answers,
                    photo = image,
                    RALastMonth = 0.0,
                    RACurrentMonth = 0.0,
                    AverageRA = 0.0,
                    dateCreation = currentDate,
                    repetitionAmount = 0,
                    lastMonthUpdateNumber = monthNumber,
                    lastMonthRepetitionNumber = 0,
                    Id = 0,
                ),
            )
        }
    }
}
