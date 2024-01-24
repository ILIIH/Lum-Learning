package com.example.add_new_card.fragments.AddAudioCard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Answer
import com.example.add_new_card_data.model.SA_Card
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class AddAudioCardViewmodel(private val repo: CardRepository) : ViewModel() {

    private val ciclableStopBtn = MutableLiveData<Boolean>()
    val _ciclableStopBtn: LiveData<Boolean>
        get() = ciclableStopBtn

    val answers = mutableListOf(Answer("", "", true))
    val recordIdMutex = Mutex()

    var maxId = 0

    fun addAnswer() {
        answers.add(Answer("", "", true))
    }

    fun getAudioFilePath(playRecord: (maxID: Int) -> Unit) {
        playRecord(maxId)
    }

    fun addRecordPath(startRecord: (maxID: Int) -> Unit) {
        viewModelScope.launch {
            recordIdMutex.withLock {
                val max = repo.getAllALCard().maxByOrNull { it.Id }
                maxId = max?.Id?.plus(1) ?: 0
                startRecord(maxId)
            }
        }
    }

    fun addNewCard(
        themeId: Int,
        answers: List<Answer>,
        question: String,
        currentDate: String,
        monthNumber: Int,
    ) {
        viewModelScope.launch {
            recordIdMutex.withLock {
                repo.insertALCard(
                    SA_Card(
                        themeId = themeId,
                        question = question,
                        answers = answers,
                        RALastMonth = 0.0,
                        RACurrentMonth = 0.0,
                        AverageRA = 0.0,
                        Id = maxId,
                        dateCreation = currentDate,
                        repetitionAmount = 0,
                        lastMonthUpdateNumber = monthNumber,
                        lastMonthRepetitionNumber = 0,
                    ),
                )
            }
        }
    }

    fun setStopBtnClickable() {
        ciclableStopBtn.value = true
    }
    fun setStopBtnNonClickable() {
        ciclableStopBtn.value = false
    }
}
