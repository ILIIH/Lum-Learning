package com.example.ask_answer_ui.fragments.RuleFragment

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.VA_Card
import com.example.ask_answer_data.ResultOf
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

        cardProvider._cardList.observe(requireActivity()) { result ->
            when (result) {
                is ResultOf.Success -> {
                    if (result.value.isNotEmpty()) {
                        cardProvider.setCurrentCard()
                        showRuleScreen(view, themeId)
                    } else {
                        showEmptyListRuleScreen(view)
                    }
                }
            }
        }

        return view.root
    }
    fun showEmptyListRuleScreen(view: FragmentRuleBinding) {
        view.noCardIcon.visibility = View.VISIBLE
        view.ruleTile.visibility = View.GONE
        view.startButton.text = getString(R.string.create_new_card)
        view.ruleText.text = getString(R.string.no_card_was_created)
        view.startButton.visibility = View.GONE
        view.ruleText.gravity = Gravity.CENTER
    }

    fun showRuleScreen(view: FragmentRuleBinding, themeId: Int) {
        cardProvider.currentCard.observe(requireActivity()) {
            if (cardProvider.isItTheEndOfCardList()) {
                if (!isDialogShown) {
                    isDialogShown = true
                    callEndDialog(themeId)
                }
            }
            when (it) {
                // TO_DO_MILLER_LAW at 1
                is LearningCardDomain -> {
                    val currentCard = it

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
                is SA_Card -> {
                    view.ruleTile.text = "Audio association test rule: "
                    view.ruleText.text =
                        "1) First you will hear the audio association on answer to question\n\n" +
                        "2) Answer the question, your time is restricted\n\n "
                }
            }
        }

        cardProvider.currentCard.observe(requireActivity()) { card ->
            view.startButton.setOnClickListener {
                when (card) {
                    // TO_DO_MILLER_LAW at 1
                    is LearningCardDomain -> {
                        when (card.themeType) {
                            2 -> {
                                lifecycleScope.launchWhenResumed {
                                    findNavController().navigate(R.id.to_MCFragment)
                                }
                            }
                            5 -> {
                                lifecycleScope.launchWhenResumed {
                                    findNavController().navigate(R.id.to_LearningCard)
                                }
                            }
                            else -> {
                                lifecycleScope.launchWhenResumed {
                                    findNavController().navigate(R.id.to_LearningCard) // TO_DO NEED_REFACTOR
                                }
                            }
                        }
                    }
                    is VA_Card -> {
                        lifecycleScope.launchWhenResumed {
                            findNavController().navigate(R.id.to_VAFragment)
                        }
                    }
                    is SA_Card -> {
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
        }
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
            cardProvider.exitTheme()
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
