package com.example.ask_answer_ui.fragments

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.ask_answer_ui.adapter.AnswerAdapter
import com.example.ask_answer_ui.databinding.FragmentMCBinding
import com.example.ask_answer_ui.fragments.DAFragment.DescriptionDialog
import com.example.ask_answer_ui.viewModel.MC_ViewModel
import com.example.ask_answer_ui.viewModel.cardProvider
import org.koin.android.ext.android.inject
import java.util.*

class MCFragment : Fragment() {

    val viewModel: MC_ViewModel by inject()
    val cardProvider: cardProvider by inject()
    lateinit var answerAdapter: AnswerAdapter
    var isFragmentClosed = false

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentMCBinding.inflate(inflater, container, false)
        cardProvider.setCurrentCard()
        cardProvider.currentCard.observe(viewLifecycleOwner) { card ->
            val currentCard = card as LearningCardDomain
            val begin = System.nanoTime()

            answerAdapter = AnswerAdapter {
                if (it) {
                    cardProvider.updateLearningCardInfoAndMetrics(
                        currentDate = Date(),
                        cardDateCreation = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(
                            currentCard.dateCreation,
                        ),
                        AverageRA = currentCard.AverageRA,
                        result = true,
                        Time = begin - System.nanoTime(),
                        card = currentCard,
                    )
                } else {
                    cardProvider.updateLearningCardInfoAndMetrics(
                        currentDate = Date(),
                        cardDateCreation = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(
                            currentCard.dateCreation,
                        ),
                        AverageRA = currentCard.AverageRA,
                        result = false,
                        Time = begin - System.nanoTime(),
                        card = currentCard,
                    )
                }
                isFragmentClosed = true
                goToNextCard()
            }

            view.question.text = currentCard.question

            view.answerList.setNestedScrollingEnabled(false)
            view.answerList.adapter = answerAdapter
            view.answerList.isNestedScrollingEnabled = false

            val endTime = 10000L
            view.timeView.setEndingTime(endTime.toFloat())

            val timer = object : CountDownTimer(endTime, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    view.timeView.setCurTime(millisUntilFinished.toFloat())
                }

                override fun onFinish() {
                    if (!isFragmentClosed) {
                        DescriptionDialog("Time is ended").show(
                            parentFragmentManager,
                            "description_dialog",
                        )
                        cardProvider.updateLearningCardInfoAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(
                                currentCard.dateCreation,
                            ),
                            AverageRA = currentCard.AverageRA,
                            result = false,
                            Time = begin - System.nanoTime(),
                            card = currentCard,
                        )
                        goToNextCard()
                    }
                }
            }

            answerAdapter.submitList(currentCard.answers)

            timer.start()
        }

        return view.root
    }

    fun goToNextCard() {
        cardProvider.goToNextCard()
        Handler().postDelayed({
            lifecycleScope.launchWhenResumed {
                findNavController().popBackStack()
            }
        }, 1000)
    }
}
