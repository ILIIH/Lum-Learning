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
import com.example.add_new_card_data.model.SA_Card
import com.example.ask_answer_ui.adapter.AnswerAdapter
import com.example.ask_answer_ui.databinding.FragmentSABinding
import com.example.ask_answer_ui.fragments.DAFragment.DescriptionDialog
import com.example.ask_answer_ui.viewModel.SA_ViewModel
import com.example.ask_answer_ui.viewModel.cardProvider
import org.koin.android.ext.android.inject
import java.util.*

class SAFragment : Fragment() {

    val viewModel: SA_ViewModel by inject()
    val cardProvider: cardProvider by inject()
    lateinit var answerAdapter: AnswerAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentSABinding.inflate(inflater, container, false)
        cardProvider.setCurrentCard()

        cardProvider.currentCard.observe(viewLifecycleOwner) { card ->
            val currentCard = card as SA_Card
            val begin = System.nanoTime()
            val endTime = 10000L
            view.timeView.setEndingTime(endTime.toFloat())

            val timer = object : CountDownTimer(endTime, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    view.timeView.setCurTime(millisUntilFinished.toFloat())
                }

                override fun onFinish() {
                    if (isResumed) {
                        DescriptionDialog("Time is ended").show(
                            parentFragmentManager,
                            "description_dialog",
                        )
                        cardProvider.updateSACardInfoAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(
                                currentCard.dateCreation,
                            ),
                            AverageRA = currentCard.AverageRA,
                            result = false,
                            Time = begin - System.nanoTime(),
                            card = currentCard,
                        )
                        this.cancel()
                        goToNextCard()
                    }
                }
            }
            timer.start()

            answerAdapter = AnswerAdapter {
                if (it) {
                    cardProvider.updateSACardInfoAndMetrics(
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
                    cardProvider.updateSACardInfoAndMetrics(
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
                cardProvider.goToNextCard()
                goToNextCard()
            }

            view.playSound.setOnClickListener {
            }

            view.question.text = currentCard.question
            view.answerList.adapter = answerAdapter

            view.answerList.isNestedScrollingEnabled = false

            answerAdapter.submitList(currentCard.answers)
        }
        return view.root
    }

    fun goToNextCard() {
        Handler().postDelayed({
            lifecycleScope.launchWhenResumed {
                findNavController().popBackStack()
            }
        }, 1000)
    }
}
