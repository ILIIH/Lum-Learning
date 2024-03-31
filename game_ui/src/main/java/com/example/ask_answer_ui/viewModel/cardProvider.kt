package com.example.ask_answer_ui.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.add_new_card_data.CardRepository
import com.example.add_new_card_data.model.Card
import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card
import com.example.add_new_card_data.model.changeRA
import com.example.ask_answer_data.ResultOf
import com.example.core.SharedPrefManager.SharedPrefManager
import com.example.core.data.usecases.insertGameResult
import com.example.core.domain.ILError
import com.example.core.domain.models.gameResult
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.lastOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.produceIn
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.koin.ext.getFullName
import java.util.*
import kotlin.collections.ArrayList

var DAY_IN_MS = (1000 * 60 * 60 * 24).toLong()

class cardProvider(
    private val repo: CardRepository,
    private val saveResult: insertGameResult,
    private val sharedPrefManager: SharedPrefManager
) : ViewModel() {

    private var currentCardIndex = 0
    private var themeIdDeferred: Int = 0

    var K: Double = 0.0
    var D: Double = 0.0
    var Ch: Double = 0.0
    var T: Double = 0.0
    var Tw1: Double = 0.0
    var Tw2: Double = 0.0

    val doSkipDescription: SharedFlow<Any>
        get() = _skipDescription.asSharedFlow()
    private val _skipDescription = MutableSharedFlow<Any>(extraBufferCapacity = 1)
    val cardList: StateFlow<ResultOf<List<Card>>>
        get() = _cardList.asStateFlow()
    private val _cardList = MutableStateFlow<ResultOf<List<Card>>>(ResultOf.Success(listOf()))
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
            currentCardIndex >= (cardList.value as ResultOf.Success).value.size
        } else {
            false
        }
    }

    fun setSkipCardDescription(className: String){
        sharedPrefManager.setSkipCardDescription(className)
    }
    fun getCurrentCard() = _cardList.filter { it is ResultOf.Success }.
        filter{(it as ResultOf.Success).value.size > currentCardIndex}.
        map { (it as ResultOf.Success).value[currentCardIndex] }

    fun saveGameResult(currentDay: Int, themeId: Int, date: String) {
        viewModelScope.launch {
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
                else -> {}
            }
        }
    }
    fun updateCardStatsAndMetrics(
        currentDate: Date,
        cardDateCreation: Date,
        result: Boolean,
        time: Long,
        cardId: Int,
    ) {
        viewModelScope.launch {
            repo.getCardStat(cardId)?.let { cardStat ->
                repo.editCardStat(cardStat.changeRA(result, currentDate.month))
                calculateGameMetrics(
                    currentDate = currentDate,
                    cardDateCreation = cardDateCreation,
                    averageRA = cardStat.AverageRA,
                    time = time,
                    result = result,
                )
            }
        }
    }

    fun calculateGameMetrics(
        currentDate: Date,
        cardDateCreation: Date,
        result: Boolean,
        averageRA: Double,
        time: Long,
    ) {
        T += time
        if (currentDate.time - (7 * DAY_IN_MS) < cardDateCreation.time &&
            currentDate.time - (14 * DAY_IN_MS) > cardDateCreation.time && !result
        ) {
            K += 1.0
        } else if (currentDate.time - (14 * DAY_IN_MS) < cardDateCreation.time && !result) {
            D += 1.0
        } else if (averageRA < 15 && currentDate.time - (14 * DAY_IN_MS) < cardDateCreation.time && !result) {
            Tw1 += time
            Ch += 1
        } else if (averageRA < 15 && currentDate.time - (14 * DAY_IN_MS) < cardDateCreation.time && result) {
            Tw2 += time
        }
    }

    fun startFromFirstCard(themeId: Int) {
        this.currentCardIndex = 0
        downloadCards(themeId)
    }

    fun goToNextCard() {
        this.currentCardIndex = currentCardIndex + 1
    }

    private fun doSkipDescription(card: Any) {
        val className = card::class.getFullName()
        val doSkip = sharedPrefManager.doSkipCardDescription(className)
        if(doSkip) {
            _skipDescription.tryEmit(card)
        }
    }
    fun downloadCards(id: Int) {
        themeIdDeferred = id

        val loadingTimerCoroutine = viewModelScope.launch {
            var mseconds = 0
            while (isActive) {
                if(mseconds == 2){
                    _cardList.tryEmit(ResultOf.Loading(arrayListOf()))
                }
                mseconds++
                delay(100)
            }
        }

        viewModelScope.launch {
            try {
                val currentList = repo.getAllCardByThemeId(id)
                _cardList.tryEmit(ResultOf.Success(currentList))
                loadingTimerCoroutine.cancel()
                if(currentCardIndex<currentList.size){
                    doSkipDescription(currentList[currentCardIndex])
                }

            } catch (e: Throwable) {
                loadingTimerCoroutine.cancel()
                _cardList.tryEmit(ResultOf.Failure(ILError.IO_ERROR))

            }
        }
    }
}