package com.example.ask_answer_ui.fragments.RuleFragment

import android.content.Context
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.fragments.RuleFragment.RuleFragmentArgs
import com.example.add_new_card_data.model.LearningCardDomain
import com.example.add_new_card_data.model.SA_Card
import com.example.add_new_card_data.model.VA_Card
import com.example.ask_answer_data.ResultOf
import com.example.ask_answer_ui.R
import com.example.ask_answer_ui.databinding.FragmentGameRuleBinding
import com.example.ask_answer_ui.navigation.GameNavigation
import com.example.ask_answer_ui.viewModel.cardProvider
import com.example.core.domain.ILError
import com.example.core.domain.Scopes
import com.example.core.ui.BaseFragment
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope
import org.koin.ext.getFullName
import java.time.LocalDateTime
import java.util.*
class RuleFragment : BaseFragment() , AndroidScopeComponent {

    override val scope: Scope by fragmentScope()

    private lateinit var cardProvider: cardProvider
    private val navigator: GameNavigation by inject()

    override fun onAttach(context: Context) {
        val ourSession = getKoin().getOrCreateScope(Scopes.GAME_SCOPE.scope, named(Scopes.GAME_SCOPE.scope))
        scope.linkTo(ourSession)
        cardProvider = get<cardProvider>()
        super.onAttach(context)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentGameRuleBinding.inflate(inflater, container, false)
        val themeId = RuleFragmentArgs.fromBundle(requireArguments()).themeId

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
        view.teacher.startButton.text = getString(R.string.create_new_card)

        SpannableString(getString(R.string.no_card_was_created)).apply {
            val create = getString(R.string.create_btn_text)
            val createIndex = indexOf(create)
            val clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    navigator.fromGameToAddNewCard(themeId)
                }
            }

            setSpan(clickableSpan, createIndex, createIndex + create.length, 0)
            setSpan(ForegroundColorSpan(ContextCompat.getColor(requireContext(), android.R.color.black)), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            setSpan(StyleSpan(Typeface.BOLD), createIndex, createIndex + create.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            setSpan(UnderlineSpan(), createIndex, +createIndex+ create.length, 0)

            view.teacher.ruleText.text = this
            view.teacher.ruleText.movementMethod = LinkMovementMethod.getInstance()
        }


        view.teacher.startButton.visibility = View.GONE
    }

    fun showRuleScreen(view: FragmentGameRuleBinding, themeId: Int) {
        view.teacher.startButton.visibility = View.VISIBLE
        view.teacher.exitButton.visibility = View.GONE
        view.teacher.restartButton.visibility = View.GONE

        view.teacher.startButton.text = getString(com.example.core.R.string.next)

        if (cardProvider.isItTheEndOfCardList()) {
            dismissLoading()
            view.teacher.exitButton.visibility = View.VISIBLE
            callEndDialog(themeId, view)
        }

        lifecycleScope.launchWhenStarted {
            cardProvider.getCurrentCard().collect {card ->
                 when (card) {
                        // TODO: MILLER_LAW at 1
                        is LearningCardDomain -> {
                            when (card.themeType) {
                                2 -> {
                                    view.subTitle.text = getString(com.example.core.R.string.meta_mnem_title)
                                    view.teacher.ruleText.text = getString(com.example.core.R.string.meta_mnem_rule)
                                }

                                5 -> {
                                    view.subTitle.text = getString(com.example.core.R.string.description_mnem_title)
                                    view.teacher.ruleText.text = getString(com.example.core.R.string.description_mnem_rule)
                                }
                            }
                        }

                        is VA_Card -> {
                            view.subTitle.text = getString(com.example.core.R.string.visual_mnem_title)
                            view.teacher.ruleText.text = getString(com.example.core.R.string.visual_mnem_rule)
                        }

                        is SA_Card -> {
                            view.subTitle.text = getString(com.example.core.R.string.sound_mnem_title)
                            view.teacher.ruleText.text =getString(com.example.core.R.string.sound_mnem_rule)
                        }
                    }
            }
        }
        lifecycleScope.launchWhenStarted {
            cardProvider.getCurrentCard().collect {card ->
                view.teacher.startButton.setOnClickListener {
                    showSkipDescrDialog(card::class.getFullName()) {
                        navigateToGame(card)
                    }
                }
            }
        }
    }

    private fun navigateToGame(card:Any?) {
        when (card) {
            // TODO: MILLER_LAW at 1
            is LearningCardDomain -> {
                when (card.themeType) {
                    2 -> {
                        findNavController().navigate(R.id.to_MCFragment)
                    }

                    5 -> {
                        findNavController().navigate(R.id.to_LearningCard)
                    }

                    else -> {
                        findNavController().navigate(R.id.to_LearningCard) // TODO: NEED_REFACTOR
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
                // TODO() A
            }
            // TODO: ADD COMBINED_APPROACH at 6
        }
    }

    private fun showSkipDescrDialog(cardType: String, navigateToGame: () -> Unit) {
        CardSkipDialog({ cardProvider.setSkipCardDescription(cardType) },navigateToGame)
            .show(parentFragmentManager, CardSkipDialog.CARD_SKIP_FRAGMENT_TAG)
    }

    private fun callEndDialog(themeId: Int, view: FragmentGameRuleBinding) {
        with(view.teacher) {
            startButton.visibility = View.GONE
            restartButton.visibility = View.VISIBLE
            ruleText.text = getString(R.string.card_end_text)

            restartButton.setOnClickListener {
                cardProvider.startFromFirstCard(themeId)
                saveGameResult(themeId)
            }

            exitButton.setOnClickListener {
                cardProvider.exitTheme()
                saveGameResult(themeId)
                scope.close()
                lifecycleScope.launchWhenResumed {
                    findNavController().popBackStack()
                }
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveGameResult(themeId: Int) {
        val date = SimpleDateFormat(context?.getString(com.example.core.R.string.data_format)).format(Date())
        cardProvider.saveGameResult(
            currentDay = LocalDateTime.now().dayOfWeek.value,
            themeId = themeId,
            date = date
        )
    }

}