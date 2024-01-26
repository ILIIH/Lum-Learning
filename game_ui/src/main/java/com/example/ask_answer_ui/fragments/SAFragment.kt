package com.example.ask_answer_ui.fragments

import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card_data.model.SA_Card
import com.example.ask_answer_ui.adapter.AnswerAdapter
import com.example.ask_answer_ui.databinding.FragmentSABinding
import com.example.ask_answer_ui.fragments.DAFragment.DescriptionDialog
import com.example.ask_answer_ui.viewModel.SA_ViewModel
import com.example.ask_answer_ui.viewModel.cardProvider
import java.io.File
import org.koin.android.ext.android.inject
import java.util.*

class SAFragment : Fragment() {

    val viewModel: SA_ViewModel by inject()
    val cardProvider: cardProvider by inject()
    lateinit var answerAdapter: AnswerAdapter
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var  mp: MediaPlayer
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentSABinding.inflate(inflater, container, false)
        cardProvider.setCurrentCard()
        progressBar = view.progressBar

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
                view.playSound.isClickable = false
                mp = MediaPlayer.create(requireContext(), File(requireActivity().cacheDir, "record${currentCard.audioFileId}").toUri())
                view.progressBar
                mp.setOnCompletionListener {
                    view.playSound.isClickable = true
                    handler.removeCallbacks(updateProgress)
                    view.progressBar.progress = 0
                }

                startPlayback()
            }

            view.question.text = currentCard.question
            view.answerList.adapter = answerAdapter

            view.answerList.isNestedScrollingEnabled = false

            answerAdapter.submitList(currentCard.answers)
        }
        return view.root
    }

    private fun startPlayback() {
        // Reset progress handler
        handler.removeCallbacks(updateProgress)
        handler.post(updateProgress)

        // Start playback
        mp.start()
    }

    // Runnable to update progress
    private val updateProgress = object : Runnable {
        override fun run() {
            val percentage = (mp.currentPosition.toFloat() / mp.duration) * 100
            progressBar.progress = percentage.toInt()
            handler.postDelayed(this, 100) // Update every 100 milliseconds
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mp.stop()
    }
    fun goToNextCard() {
        Handler().postDelayed({
            lifecycleScope.launchWhenResumed {
                findNavController().popBackStack()
            }
        }, 1000)
    }
}
