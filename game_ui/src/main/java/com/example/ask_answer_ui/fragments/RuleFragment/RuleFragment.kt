package com.example.ask_answer_ui.fragments.RuleFragment

import android.graphics.Typeface
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.VA_Card
import com.example.ask_answer_data.ResultOf
import com.example.ask_answer_ui.R
import com.example.ask_answer_ui.databinding.FragmentGameRuleBinding
import com.example.ask_answer_ui.viewModel.cardProvider
import com.example.core.domain.ILError
import com.example.core.ui.BaseFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.ext.getFullName
import java.time.LocalDateTime
import java.util.*
const val ARG_THEME_ID = "id"
class RuleFragment : BaseFragment() {

    val cardProvider: cardProvider by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameRuleBinding.inflate(inflater, container, false)
        val themeId = requireArguments().getInt(ARG_THEME_ID)

        cardProvider.downloadCards(themeId)
        setupCardListObserver(binding, themeId)
        setupSkipDescriptionObserver()

        return binding.root
    }

    private fun setupCardListObserver(binding: FragmentGameRuleBinding, themeId: Int) {
        lifecycleScope.launchWhenStarted {
            cardProvider.cardList.collect { result ->
                when (result) {
                        is ResultOf.Success -> handleCardListSuccess(binding, result.value, themeId)
                        is ResultOf.Loading -> showLoading()
                        is ResultOf.Failure -> handleFailure(result.error)
                    }
                }
        }
    }

    private fun handleCardListSuccess(binding: FragmentGameRuleBinding, cardList: List<Any>, themeId: Int) {
        dismissLoading()
        if (cardList.isNotEmpty()) {
            showRuleScreen(binding, themeId)
        } else {
            showEmptyListRuleScreen(binding, themeId)
        }
    }

    private fun setupSkipDescriptionObserver() {
        lifecycleScope.launchWhenStarted {
            cardProvider.doSkipDescription.collect { card ->
                lifecycleScope.launchWhenResumed {
                    if (!cardProvider.isItTheEndOfCardList()&& count !=1) {
                        dismissLoading()
                        count++;
                        navigateToGame(card)
                    }
                }
            }
        }
    }

    var count = 0 ;

    private fun handleFailure(error: ILError?) {
        dismissLoading()
        showError(error)
    }

    fun showEmptyListRuleScreen(view: FragmentGameRuleBinding, themeId: Int) {
        view.startButton.text = getString(R.string.create_new_card)

        SpannableString(getString(R.string.no_card_was_created)).apply {
            val create = getString(R.string.create_btn_text)
            val createIndex = indexOf(create)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    findNavController().navigate(R.id.to_add_new_card, Bundle().apply { putInt("id", themeId) })
                }
            }

            setSpan(clickableSpan, createIndex, createIndex + create.length, 0)
            setSpan(ForegroundColorSpan(resources.getColor(android.R.color.black)), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            setSpan(StyleSpan(Typeface.BOLD), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(UnderlineSpan(), createIndex, +createIndex+ create.length, 0)

            view.ruleText.text = this
            view.ruleText.movementMethod = LinkMovementMethod.getInstance()
        }


        view.startButton.visibility = View.GONE
    }

    fun showRuleScreen(view: FragmentGameRuleBinding, themeId: Int) {
        view.startButton.visibility = View.VISIBLE
        view.exitButton.visibility = View.GONE

        view.startButton.text = getString(com.example.core.R.string.next)

        lifecycleScope.launchWhenStarted {
            cardProvider.getCurrentCard().apply {
                if (cardProvider.isItTheEndOfCardList()) {
                    dismissLoading()
                    view.exitButton.visibility = View.VISIBLE
                    callEndDialog(themeId, view)
                } else {
                    when (this) {
                        // TO_DO_MILLER_LAW at 1
                        is LearningCardDomain -> {
                            val currentCard = this
                            when (currentCard.themeType) {
                                2 -> {
                                    view.subTitle.text = "Meta cognition test rule: "
                                    view.ruleText.text =
                                        "1) Write down description of the question field. try to write as much information sa possible. This information have not to be true, this is oly your general knowledge test\n\n" +
                                                "2) Write down what is it hardest thing in question, why it could be hard exactly to yo to remember this answer\n\n " +
                                                "3) Write down how you could use information in this question\n\n " +
                                                "4) Answer the question, your time is restricted \n\n "
                                }

                                5 -> {
                                    view.subTitle.text = "Description association test rule: "
                                    view.ruleText.text =
                                        "1) First you will see long description fo this field / subject of the question \n\n" +
                                                "2) Answer the question, your time is restricted\n\n "
                                }

                                else -> {
                                    // NEED REFACTOR
                                    view.subTitle.text = "Description association test rule: "
                                    view.ruleText.text =
                                        "1) First you will see long description fo this field / subject of the question \n\n" +
                                                "2) Answer the question, your time is restricted\n\n "
                                }
                            }
                        }

                        is VA_Card -> {
                            view.subTitle.text = "Visual association test rule: "
                            view.ruleText.text =
                                "1) First you will see the photo association on answer to question\n\n" +
                                        "2) Answer the question, your time is restricted\n\n "
                        }

                        is SA_Card -> {
                            view.subTitle.text = "Audio association test rule: "
                            view.ruleText.text =
                                "1) First you will hear the audio association on answer to question\n\n" +
                                        "2) Answer the question, your time is restricted\n\n "
                        }
                    }
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            cardProvider.getCurrentCard().apply {
                view.startButton.setOnClickListener {
                    showSkipDescrDialog(this::class.getFullName()) { navigateToGame(this) }
                }
            }
        }
    }

    private fun navigateToGame(card:Any?) {
        when (card) {
            // TO_DO_MILLER_LAW at 1
            is LearningCardDomain -> {
                    when (card.themeType) {
                        2 -> {
                            findNavController().navigate(R.id.to_MCFragment)
                        }

                        5 -> {
                            findNavController().navigate(R.id.to_LearningCard)
                        }

                        else -> {
                            findNavController().navigate(R.id.to_LearningCard) // TO_DO NEED_REFACTOR
                        }
                    }
            }
            is VA_Card -> {
                    findNavController().navigate(R.id.to_VAFragment)
            }
            is SA_Card -> {
                    findNavController().navigate(R.id.to_SAFragment)
            }
            else -> {
                TODO()
            }
            // TO_DO_COMBINED_APPROACH at 6
        }
    }

    private fun showSkipDescrDialog(cardType: String, navigateToGame: () -> Unit) {
        CardSkipDialog({ cardProvider.setSkipCardDescription(cardType) },navigateToGame)
            .show(parentFragmentManager, "CardSkipDialogFragmentTag")
    }

    private fun callEndDialog(themeId: Int, view: FragmentGameRuleBinding) {
        view.startButton.visibility = View.GONE
        view.restartButton.visibility = View.VISIBLE
        view.ruleText.text = getString(R.string.card_end_text)

        view.restartButton.setOnClickListener {
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
        }
        view.exitButton.setOnClickListener {
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
            lifecycleScope.launchWhenResumed {
                findNavController().popBackStack()
            }
        }
    }
}
