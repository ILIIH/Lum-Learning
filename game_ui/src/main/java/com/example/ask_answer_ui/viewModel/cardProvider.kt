package com.example.ask_answer_ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card
import com.example.add_new_card_data.model.changeRA
import com.example.ask_answer_data.ResultOf
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

    private val cardList = MutableLiveData<ResultOf<ArrayList<Any>>>()
    val _cardList: MutableLiveData<ResultOf<ArrayList<Any>>>
        get() = cardList
    val currentCard = MutableLiveData<Any?>()

    fun exitTheme() {
        this.K = 0.0
        this.D = 0.0
        this.Ch = 0.0
        this.T = 0.0
        this.Tw1 = 0.0
        this.Tw2 = 0.0

        this.currentCardIndex = 0
    }

    fun isItTheEndOfCardList(): Boolean {
        return if (cardList.value is ResultOf.Success) {
            currentCardIndex > (cardList.value as ResultOf.Success).value.size - 1
        } else {
            false
        }
    }

    fun setCurrentCard() {
        when (cardList.value) {
            is ResultOf.Success -> {
                with((cardList.value as ResultOf.Success)) {
                    if (currentCardIndex < this.value.size) {
                        currentCard.postValue(this.value[currentCardIndex])
                    }
                }
            }
        }
    }

    fun saveGameResult(currentDay: Int, themeId: Int, date: String) {
        GlobalScope.launch {
            when (cardList.value) {
                is ResultOf.Success -> {
                    val list = (cardList.value as ResultOf.Success<ArrayList<Any>>).value

                    saveResult.execute(
                        gameResult(
                            K = K * 100 / list.size,
                            D = D * 100 / list.size,
                            Ch = Ch * 100 / list.size,
                            T = T * 100 / list.size,
                            Tw1 = Tw1 * 100 / list.size,
                            Tw2 = Tw2 * 100 / list.size,
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
                    list.clear()
                }
            }
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
        card: SA_Card,
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
        GlobalScope.launch {
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

    fun startFromFirstCard(themeId: Int) {
        this.currentCardIndex = 0
        downloadCards(themeId)
    }

    fun goToNextCard() {
        Log.i("CardList1", "go to next card ")

        this.currentCardIndex = currentCardIndex + 1
    }

    fun hasALCard(): Boolean {
        when (cardList.value) {
            is ResultOf.Success -> {
                with((cardList.value as ResultOf.Success)) {
                    return if (currentCardIndex < this.value.size) {
                        this.value[currentCardIndex] is SA_Card
                    } else {
                        false
                    }
                }
            }
            else -> {
                return false
            }
        }
    }

    fun hasVACard(): Boolean {
        when (cardList.value) {
            is ResultOf.Success -> {
                with((cardList.value as ResultOf.Success)) {
                    return if (currentCardIndex < this.value.size) {
                        this.value[currentCardIndex] is VA_Card
                    } else {
                        false
                    }
                }
            }
            else -> {
                return false
            }
        }
    }

    fun hasLearningCard(): Boolean {
        when (cardList.value) {
            is ResultOf.Success -> {
                with((cardList.value as ResultOf.Success)) {
                    return if (currentCardIndex < this.value.size) {
                        if (this.value[currentCardIndex] is LearningCardDomain) {
                            (this.value.get(currentCardIndex) as LearningCardDomain).themeType == 5
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                }
            }
            else -> {
                return false
            }
        }
    }

    fun hasMCCard(): Boolean {
        when (cardList.value) {
            is ResultOf.Success -> {
                with((cardList.value as ResultOf.Success)) {
                    return if (currentCardIndex < this.value.size) {
                        if (this.value[currentCardIndex] is LearningCardDomain) {
                            (this.value[currentCardIndex] as LearningCardDomain).themeType == 2
                        } else {
                            false
                        }
                    } else {
                        false
                    }
                }
            }
            else -> {
                return false
            }
        }
    }

    fun downloadCards(id: Int) {
        cardList.postValue(ResultOf.Loading(arrayListOf()))

        GlobalScope.launch {
            try {
                val currentList = ArrayList<Any>(100)
                currentList.addAll(repo.getAllALCardByThemeId(id))
                currentList.addAll(repo.getAllVACardByThemeId(id))
                currentList.addAll(repo.getAllCardByThemeId(id))
                cardList.postValue(ResultOf.Success(currentList))
            } catch (e: Throwable) {
                cardList.postValue(ResultOf.Failure(e.message, e))
            }
        }
    }
}
