package com.lum.ask_answer_ui.fragments.DAFragment

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.lum.add_new_card_data.model.LearningCardDomain
import com.lum.ask_answer_ui.R
import com.lum.ask_answer_ui.adapter.AnswerAdapter
import com.lum.ask_answer_ui.databinding.FragmentDABinding
import com.lum.ask_answer_ui.viewModel.cardProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.util.*

class DAFragment : Fragment() {

    val cardProvider: cardProvider by inject()
    private lateinit var answerAdapter: AnswerAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentDABinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenStarted {
            cardProvider.getCurrentCard().collect {
                val currentCard = it as LearningCardDomain

                val begin = System.nanoTime()

                answerAdapter = AnswerAdapter {
                    if (it) {
                        cardProvider.updateCardStatsAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat(getString(com.lum.core.R.string.data_format)).parse(
                                currentCard.dateCreation,
                            ),
                            result = true,
                            time = begin - System.nanoTime(),
                            cardId = currentCard.id,
                        )
                    } else {
                        cardProvider.updateCardStatsAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat(getString(com.lum.core.R.string.data_format)).parse(
                                currentCard.dateCreation,
                            ),
                            result = false,
                            time = begin - System.nanoTime(),
                            cardId = currentCard.id,
                        )
                    }
                    goToNextCard()
                }

                CardEndsDialog(currentCard.description).show(
                    parentFragmentManager,
                    CardEndsDialog.DESCRIPTION_DIALOG_TAG,
                )

                view.answerList.adapter = answerAdapter

                view.answerList.isNestedScrollingEnabled = false

                view.question.text = currentCard.question

                val endTime = 10000L
                view.timeView.setEndingTime(endTime.toFloat())

                val timer = object : CountDownTimer(endTime, 100) {
                    override fun onTick(millisUntilFinished: Long) {
                        view.timeView.setCurTime(millisUntilFinished.toFloat())
                    }

                    override fun onFinish() {
                        if (isResumed) {
                            CardEndsDialog(getString(R.string.tile_ended), ::goToNextCard).show(
                                parentFragmentManager,
                                CardEndsDialog.DESCRIPTION_DIALOG_TAG,
                            )
                            cardProvider.updateCardStatsAndMetrics(
                                currentDate = Date(),
                                cardDateCreation = SimpleDateFormat(getString(com.lum.core.R.string.data_format)).parse(
                                    currentCard.dateCreation,
                                ),
                                result = false,
                                time = begin - System.nanoTime(),
                                cardId = currentCard.id,
                            )
                        }
                    }
                }

                answerAdapter.submitList(currentCard.answers)

                timer.start()
            }
        }
        return view.root
    }

    fun goToNextCard() {
        lifecycleScope.launch{
            cardProvider.goToNextCard()
            lifecycleScope.launchWhenResumed {
                delay(1000)
                findNavController().popBackStack()
            }
        }
    }

}
