package com.example.add_new_card.fragments.AddAudioCard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.Answer
import kotlinx.coroutines.launch

class AddAudioCardViewmodel(private val repo: CardRepository) : ViewModel() {

    private val ciclableStopBtn = MutableLiveData<Boolean>()
    val _ciclableStopBtn: LiveData<Boolean>
        get() = ciclableStopBtn

    var maxId = 0

    fun getAudioFilePath(playRecord: (maxID: Int) -> Unit) {
        playRecord(maxId)
    }

    fun addRecordPath(startRecord: (maxID: Int) -> Unit) {
        viewModelScope.launch {
            val max = repo.getAllALCard().maxByOrNull { it.Id }
            maxId = max?.Id?.plus(1) ?: 0
            startRecord(maxId)
        }
    }

    fun addNewCard(themeId: Int, question: String, answers: List<Answer>) {
        viewModelScope.launch {
            repo.incestALCard(
                AL_Card(
                    themeId = themeId,
                    question = question,
                    answers = answers,
                    RALastMonth = 0.0,
                    RACurrentMonth = 0.0,
                    AverageRA = 0.0,
                    Id = maxId,
                ),
            )
        }
    }

    fun setStopBtnClickable() {
        ciclableStopBtn.value = true
    }
    fun setStopBtnNonClickable() {
        ciclableStopBtn.value = false
    }
}
