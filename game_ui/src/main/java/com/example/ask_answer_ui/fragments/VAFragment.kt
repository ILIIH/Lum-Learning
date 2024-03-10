package com.example.ask_answer_ui.fragments

import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card_data.model.VA_Card
import com.example.ask_answer_ui.R
import com.example.ask_answer_ui.adapter.AnswerAdapter
import com.example.ask_answer_ui.databinding.FragmentVisualAssosiationBinding
import com.example.ask_answer_ui.fragments.DAFragment.DescriptionDialog
import com.example.ask_answer_ui.viewModel.VA_ViewModel
import com.example.ask_answer_ui.viewModel.cardProvider
import kotlinx.coroutines.delay
import org.koin.android.ext.android.inject
import java.util.*

class VAFragment : Fragment() {

    val viewModel: VA_ViewModel by inject()
    val cardProvider: cardProvider by inject()
    lateinit var answerAdapter: AnswerAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentVisualAssosiationBinding.inflate(inflater, container, false)
        lifecycleScope.launchWhenStarted {
            cardProvider.getCurrentCard().apply{
                val currentCard = this as VA_Card
                val begin = System.nanoTime()

                answerAdapter = AnswerAdapter {
                    if (it) {
                        cardProvider.updateCardStatsAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat(getString(com.example.core.R.string.data_format)).parse(
                                currentCard.dateCreation,
                            ),
                            result = true,
                            time = begin - System.nanoTime(),
                            cardId = currentCard.Id,
                        )
                    } else {
                        cardProvider.updateCardStatsAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat(getString(com.example.core.R.string.data_format)).parse(
                                currentCard.dateCreation,
                            ),
                            result = false,
                            time = begin - System.nanoTime(),
                            cardId = currentCard.Id,
                        )
                    }

                    goToNextCard()
                }

                view.answerList.adapter = answerAdapter

                view.question.text = currentCard.question
                view.answerList.isNestedScrollingEnabled = false
                val photo =
                    BitmapFactory.decodeByteArray(currentCard.photo, 0, currentCard.photo.size)
                view.assosiationImg.setImageBitmap(photo)
                val endTime = 10000L
                view.timeView.setEndingTime(endTime.toFloat())

                val timer = object : CountDownTimer(endTime, 100) {
                    override fun onTick(millisUntilFinished: Long) {
                        view.timeView.setCurTime(millisUntilFinished.toFloat())
                    }

                    override fun onFinish() {
                        if (isResumed) {
                            DescriptionDialog(getString(R.string.tile_ended)).show(
                                parentFragmentManager,
                                DescriptionDialog.DESCRIPTION_DIALOG_TAG,
                            )
                            cardProvider.updateCardStatsAndMetrics(
                                currentDate = Date(),
                                cardDateCreation = SimpleDateFormat(getString(com.example.core.R.string.data_format)).parse(
                                    currentCard.dateCreation,
                                ),
                                result = false,
                                time = begin - System.nanoTime(),
                                cardId = currentCard.Id,
                            )
                            goToNextCard()
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
        cardProvider.goToNextCard()
        lifecycleScope.launchWhenResumed {
            delay(1000)
            findNavController().popBackStack()
        }
    }
}
