package com.example.ask_answer_ui.fragments.RuleFragment

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card_data.model.AL_Card
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.VA_Card
import com.example.ask_answer_ui.R
import com.example.ask_answer_ui.databinding.FragmentRuleBinding
import com.example.ask_answer_ui.viewModel.cardProvider
import org.koin.android.ext.android.inject
import java.time.LocalDateTime
import java.util.*

class RuleFragment : Fragment() {

    val cardProvider: cardProvider by inject()
    var isDialogShown = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentRuleBinding.inflate(inflater, container, false)

        val themeId = requireArguments().getInt("id")
        cardProvider.downloadCards(themeId)

        cardProvider.cardList.observe(requireActivity()) {
            when (cardProvider._currentCard) {
                // TO_DO_MILLER_LAW at 1
                null -> {
                    if (!isDialogShown) {
                        isDialogShown = true
                        callEndDialog(themeId)
                    }
                }
                is LearningCardDomain -> {
                    val currentCard = cardProvider._currentCard as LearningCardDomain
                    when (currentCard.themeType) {
                        2 -> {
                            view.ruleTile.text = "Meta cognition test rule: "
                            view.ruleText.text =
                                "1) Write down description of the question field. try to write as much information sa possible. This information have not to be true, this is oly your general knowledge test\n\n" +
                                "2) Write down what is it hardest thing in question, why it could be hard exactly to yo to remember this answer\n\n " +
                                "3) Write down how you could use information in this question\n\n " +
                                "4) Answer the question, your time is restricted \n\n "
                        }
                        5 -> {
                            view.ruleTile.text = "Description association test rule: "
                            view.ruleText.text =
                                "1) First you will see long description fo this field / subject of the question \n\n" +
                                "2) Answer the question, your time is restricted\n\n "
                        }
                        else -> {
                            // NEED REFACTOR
                            view.ruleTile.text = "Description association test rule: "
                            view.ruleText.text =
                                "1) First you will see long description fo this field / subject of the question \n\n" +
                                "2) Answer the question, your time is restricted\n\n "
                        }
                    }
                }
                is VA_Card -> {
                    view.ruleTile.text = "Visual association test rule: "
                    view.ruleText.text =
                        "1) First you will see the photo association on answer to question\n\n" +
                        "2) Answer the question, your time is restricted\n\n "
                }
                is AL_Card -> {
                    view.ruleTile.text = "Audio association test rule: "
                    view.ruleText.text =
                        "1) First you will hear the audio association on answer to question\n\n" +
                        "2) Answer the question, your time is restricted\n\n "
                }
            }
        }

        view.startButton.setOnClickListener {
            when (cardProvider._currentCard) {
                // TO_DO_MILLER_LAW at 1
                is LearningCardDomain -> {
                    val currentCard = cardProvider._currentCard as LearningCardDomain
                    when (currentCard.themeType) {
                        2 -> {
                            lifecycleScope.launchWhenResumed {
                                findNavController().navigate(R.id.to_MCFragment)
                            }
                        }
                        5 -> {
                            lifecycleScope.launchWhenResumed {
                                findNavController().navigate(R.id.to_DAFragment)
                            }
                        }
                        else -> {
                            lifecycleScope.launchWhenResumed {
                                findNavController().navigate(R.id.to_DAFragment) // NEED_REFACTOR
                            }
                        }
                    }
                }
                is VA_Card -> {
                    lifecycleScope.launchWhenResumed {
                        findNavController().navigate(R.id.to_VAFragment)
                    }
                }
                is AL_Card -> {
                    lifecycleScope.launchWhenResumed {
                        findNavController().navigate(R.id.to_SAFragment)
                    }
                }
                else -> {
                    TODO()
                }
                // TO_DO_COMBINED_APPROACH at 6
            }
        }
        return view.root
    }

    fun callEndDialog(themeId: Int) {
        CardEndDialog(correctAsw = {
            cardProvider.startFromFirstCard(themeId)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cardProvider.saveGameResult(
                    currentDay = LocalDateTime.now().dayOfWeek.value,
                    themeId = themeId,
                    date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date()),

                )
            } else {
                TODO()
            }
            isDialogShown = false
        }, wrongAsw = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                cardProvider.saveGameResult(
                    currentDay = LocalDateTime.now().dayOfWeek.value,
                    themeId = themeId,
                    date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(Date()),

                )
            } else {
                TODO()
            }
            isDialogShown = false

            lifecycleScope.launchWhenResumed {
                findNavController().popBackStack()
            }
        }).show(parentFragmentManager, "description_dialog")
    }
}
