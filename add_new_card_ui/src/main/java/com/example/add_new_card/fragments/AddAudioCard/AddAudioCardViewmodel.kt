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

    private val clickableStopBtn = MutableLiveData<Boolean>()
    val _clickableStopBtn: LiveData<Boolean>
        get() = clickableStopBtn

    private var _answers = mutableListOf(Answer("", "", true))
    private val recordIdMutex = Mutex()
    private var maxId = 0
    fun getAnswers():List<Answer>  = _answers
    fun reInitAnswers() {
        _answers.clear()
        _answers.add(Answer("", "", true))
    }
    fun addAnswer() {
        _answers.add(Answer("", "", true))
    }
    fun getAudioFilePath(playRecord: (maxID: Int) -> Unit) {
        playRecord(maxId)
    }
    fun addRecordPath(themeId: Int, startRecord: (maxID: Int) -> Unit) {
        viewModelScope.launch {
            recordIdMutex.withLock {
                val max = repo.getAllCardByThemeId(themeId).filterIsInstance<SA_Card>().maxByOrNull { it.audioFileId }
                maxId = max?.audioFileId?.plus(1) ?: 0
                startRecord(maxId)
            }
        }
    }
    fun addNewCard(
        themeId: Int,
        answers: List<Answer>,
        question: String,
        currentDate: String,
    ) {
        viewModelScope.launch {
            recordIdMutex.withLock {
                repo.insertCard(
                    SA_Card(
                        id = 0,
                        themeId = themeId,
                        question = question,
                        answers = answers,
                        audioFileId = maxId,
                        dateCreation = currentDate
                    ),
                )
            }
        }
    }
    fun setStopBtnClickable() {
        clickableStopBtn.value = true
    }
    fun setStopBtnNonClickable() {
        clickableStopBtn.value = false
    }
}
