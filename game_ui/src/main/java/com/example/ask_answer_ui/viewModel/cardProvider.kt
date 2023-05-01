package com.example.ask_answer_ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card
import com.example.add_new_card_data.model.changeRA
import com.example.core.data.usecases.insertGameResult
import com.example.core.domain.models.gameResult
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

var DAY_IN_MS = (1000 * 60 * 60 * 24).toLong()

class cardProvider(
    private val repo: CardRepository,
    private val saveResult: insertGameResult,
) : ViewModel() {

    private var currentCardIndex = 0

    var K: Double = 0.0
    var D: Double = 0.0
    var Ch: Double = 0.0
    var T: Double = 0.0
    var Tw1: Double = 0.0
    var Tw2: Double = 0.0

    val cardList = MutableLiveData<ArrayList<Any>>()
    val _currentCard: Any?
        get() {
            return if (currentCardIndex < cardList.value!!.size) {
                cardList.value?.get(currentCardIndex)
            } else {
                Log.i("game_logging", "Index out of Boundless")
                null
            }
        }

    fun saveGameResult(currentDay: Int, themeId: Int, date: String) {
        GlobalScope.launch {
            saveResult.execute(
                gameResult(
                    K = K * 100 / cardList.value!!.size,
                    D = D * 100 / cardList.value!!.size,
                    Ch = Ch * 100 / cardList.value!!.size,
                    T = T * 100 / cardList.value!!.size,
                    Tw1 = Tw1 * 100 / cardList.value!!.size,
                    Tw2 = Tw2 * 100 / cardList.value!!.size,
                    CurrentDay = currentDay,
                    learningMethodId = 0,
                    themeId = themeId,
                    date = date,
                ),
            )
            K = 0.0
            D = 0.0
            Ch = 0.0
            T = 0.0
            Tw1 = 0.0
            Tw2 = 0.0
            cardList.value!!.clear()
        }
    }

    fun updateVACardInfoAndMetrics(
        currentDate: Date,
        cardDateCreation: Date,
        result: Boolean,
        AverageRA: Double,
        Time: Long,
        card: VA_Card,
    ) {
        GlobalScope.launch {
            repo.editVACard(card.changeRA(result, currentDate.month))
        }
        calculateGameMetrics(
            currentDate = currentDate,
            cardDateCreation = cardDateCreation,
            AverageRA = AverageRA,
            Time = Time,
            result = result,
        )
    }

    fun updateSACardInfoAndMetrics(
        currentDate: Date,
        cardDateCreation: Date,
        result: Boolean,
        AverageRA: Double,
        Time: Long,
        card: AL_Card,
    ) {
        GlobalScope.launch {
            repo.editALCard(card.changeRA(result, currentDate.month))
        }
        calculateGameMetrics(
            currentDate = currentDate,
            cardDateCreation = cardDateCreation,
            AverageRA = AverageRA,
            Time = Time,
            result = result,
        )
    }

    fun updateLearningCardInfoAndMetrics(
        currentDate: Date,
        cardDateCreation: Date,
        result: Boolean,
        AverageRA: Double,
        Time: Long,
        card: LearningCardDomain,
    ) {
        val launch = GlobalScope.launch {
            repo.editLearningCard(card.changeRA(result, currentDate.month))
        }
        calculateGameMetrics(
            currentDate = currentDate,
            cardDateCreation = cardDateCreation,
            AverageRA = AverageRA,
            Time = Time,
            result = result,
        )
    }

    fun calculateGameMetrics(
        currentDate: Date,
        cardDateCreation: Date,
        result: Boolean,
        AverageRA: Double,
        Time: Long,
    ) {
        T += Time
        if (currentDate.time - (7 * DAY_IN_MS) < cardDateCreation.time &&
            currentDate.time - (14 * DAY_IN_MS) > cardDateCreation.time && !result
        ) {
            K += 1.0
        } else if (currentDate.time - (14 * DAY_IN_MS) < cardDateCreation.time && !result) {
            D += 1.0
        } else if (AverageRA < 15 && currentDate.time - (14 * DAY_IN_MS) < cardDateCreation.time && !result) {
            Tw1 += Time
            Ch += 1
        } else if (AverageRA < 15 && currentDate.time - (14 * DAY_IN_MS) < cardDateCreation.time && result) {
            Tw2 += Time
        }
    }

    fun startFromFirstCard() {
        this.currentCardIndex = 0
    }

    fun goToNextCard() {
        this.currentCardIndex = currentCardIndex + 1
    }

    fun hasALCard(): Boolean {
        return if (currentCardIndex < cardList.value!!.size) {
            cardList.value?.get(currentCardIndex) is AL_Card
        } else {
            false
        }
    }

    fun hasVACard(): Boolean {
        return if (currentCardIndex < cardList.value!!.size) {
            cardList.value?.get(currentCardIndex) is VA_Card
        } else {
            false
        }
    }

    fun hasDACard(): Boolean {
        return if (currentCardIndex < cardList.value!!.size) {
            if (cardList.value?.get(currentCardIndex) is LearningCardDomain) {
                (cardList.value?.get(currentCardIndex) as LearningCardDomain).themeType == 5
            } else {
                false
            }
        } else {
            false
        }
    }

    fun hasMCCard(): Boolean {
        return if (currentCardIndex < cardList.value!!.size) {
            if (cardList.value?.get(currentCardIndex) is LearningCardDomain) {
                (cardList.value?.get(currentCardIndex) as LearningCardDomain).themeType == 2
            } else {
                false
            }
        } else {
            false
        }
    }

    fun downloadCards(id: Int) {
        GlobalScope.launch {
            val currentList = ArrayList<Any>(100)
            currentList.addAll(repo.getAllALCardByThemeId(id))
            currentList.addAll(repo.getAllVACardByThemeId(id))
            currentList.addAll(repo.getAllCardByThemeId(id))
            cardList.postValue(currentList)
        }
    }
}
