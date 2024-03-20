package com.example.ask_answer_ui.fragments

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card_data.model.SA_Card
import com.example.ask_answer_ui.R
import com.example.ask_answer_ui.adapter.AnswerAdapter
import com.example.ask_answer_ui.databinding.FragmentSABinding
import com.example.ask_answer_ui.fragments.DAFragment.CardEndsDialog
import com.example.ask_answer_ui.viewModel.cardProvider
import com.example.core.domain.Scopes
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.koin.android.ext.android.getKoin
import java.io.File
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.core.qualifier.named
import java.util.*

class SAFragment : Fragment() {

    lateinit var cardProvider: cardProvider
    private lateinit var answerAdapter: AnswerAdapter
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var  mp: MediaPlayer
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentSABinding.inflate(inflater, container, false)
        progressBar = view.progressBar
        lifecycleScope.launch {
            cardProvider.getCurrentCard().collect { card ->
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
                            CardEndsDialog(getString(R.string.tile_ended)).show(
                                parentFragmentManager,
                                CardEndsDialog.DESCRIPTION_DIALOG_TAG,
                            )
                            cardProvider.updateCardStatsAndMetrics(
                                currentDate = Date(),
                                cardDateCreation = SimpleDateFormat(getString(com.example.core.R.string.data_format)).parse(
                                    currentCard.dateCreation,
                                ),
                                result = false,
                                time = begin - System.nanoTime(),
                                cardId = currentCard.id,
                            )
                            this.cancel()
                            goToNextCard()
                        }
                    }
                }
                timer.start()

                answerAdapter = AnswerAdapter {
                    if (it) {
                        cardProvider.updateCardStatsAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat(getString(com.example.core.R.string.data_format)).parse(
                                currentCard.dateCreation,
                            ),
                            result = true,
                            time = begin - System.nanoTime(),
                            cardId = currentCard.id,
                        )
                    } else {
                        cardProvider.updateCardStatsAndMetrics(
                            currentDate = Date(),
                            cardDateCreation = SimpleDateFormat(getString(com.example.core.R.string.data_format)).parse(
                                currentCard.dateCreation,
                            ),
                            result = false,
                            time = begin - System.nanoTime(),
                            cardId = currentCard.id,
                        )
                    }
                    cardProvider.goToNextCard()
                    goToNextCard()
                }

                view.playSound.setOnClickListener {
                    view.playSound.isClickable = false
                    mp = MediaPlayer.create(
                        requireContext(),
                        File(
                            requireActivity().cacheDir,
                            getString(R.string.record) + currentCard.audioFileId.toString()
                        ).toUri()
                    )
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
        }
        return view.root
    }

    private fun startPlayback() {
        // Reset progress handler
        handler.removeCallbacks(updateProgress)
        handler.post(updateProgress)

        if(::mp.isInitialized){
            mp.start()
        }
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
        if(::mp.isInitialized){
            mp.stop()
        }
        super.onDestroy()
    }
    fun goToNextCard() {
        lifecycleScope.launchWhenResumed {
            delay(1000)
            findNavController().popBackStack()
        }
    }
    override fun onAttach(context: Context) {
        val  scope = getKoin().getOrCreateScope(Scopes.GAME_SCOPE.scope, named(Scopes.GAME_SCOPE.scope))
        cardProvider = scope.get<cardProvider>()
        super.onAttach(context)
    }
}
