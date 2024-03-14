package com.example.add_new_card.fragments.AddLearningCard

import android.app.AlertDialog
import android.content.Context
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isEmpty
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.adapters.AnswersAdapters
import com.example.add_new_card.databinding.FragmentAddLearningCardBinding
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.core.domain.Scopes
import com.example.core.util.hideKeyboard
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.qualifier.named
import java.util.*

class AddLearningCardFragment : Fragment() {

    val viewModel: AddLearningCardViewmodel by inject()
    private lateinit var themeInfoProvider: ThemeInfoProvider

    val adapter = AnswersAdapters()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: FragmentAddLearningCardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_learning_card, container, false)

        view.questionInputText.addTextChangedListener {
            view.question.error = null
        }

        view.answers.adapter = adapter
        adapter.submitList(viewModel.getAnswers())

        view.addNewAnswer.setOnClickListener {
            viewModel.addAnswer()
            adapter.submitList(viewModel.getAnswers())
            adapter.notifyItemInserted(viewModel.getAnswers().size)
        }

        val themeType = themeInfoProvider.getThemeType().value!!
        if (themeType == 5) {
            view.description.visibility = View.VISIBLE
            view.descriptionTextInput.visibility = View.VISIBLE
        }

        val themeID = themeInfoProvider.getThemeId()

        view.continueBtn.setOnClickListener {
            val answers = adapter.getAllAnswers()

            AlertDialog.Builder(context)
                .setTitle(getString(R.string.card_creation_title))
                .setMessage(getString(R.string.card_creation_message))
                .setPositiveButton(
                    getString(R.string.continue_creation),
                ) { _, _ ->
                    if (themeType == 5) {
                        viewModel.addNewCard(
                            themeId = themeID,
                            question = view.question.editText!!.text.toString(),
                            answers = answers,
                            themeType = themeType,
                            currentDate = SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(
                                Date(),
                            ),
                            description = view.description.editText!!.text.toString(),
                        )
                    } else {
                        viewModel.addNewCard(
                            themeId = themeID,
                            question = view.question.editText!!.text.toString(),
                            answers = answers,
                            themeType = themeType,
                            currentDate = SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(
                                Date(),
                            ),
                            description = "-",
                        )
                    }
                    if(!adapter.validateAnswers() && !validateCard(view)){
                        view.Title.requestFocus()
                        view.questionInputText.text?.clear()
                        initEmptyAnswers()
                    }
                }
                .setNegativeButton(
                    R.string.save_and_exit,
                ) { _, _ ->
                    if (themeType == 5) {
                        viewModel.addNewCard(
                            themeId = themeID,
                            question = view.question.editText!!.text.toString(),
                            answers = answers,
                            themeType = themeType,
                            currentDate = SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(
                                Date(),
                            ),
                            description = view.description.editText!!.text.toString(),
                        )
                    } else {
                        viewModel.addNewCard(
                            themeId = themeID,
                            question = view.question.editText!!.text.toString(),
                            answers = answers,
                            themeType = themeType,
                            currentDate = SimpleDateFormat(getString(com.example.core.R.string.data_format)).format(
                                Date(),
                            ),
                            description = "-",
                        )
                    }
                    hideKeyboard(requireActivity())
                    findNavController().popBackStack()
                }
                .setIcon(R.drawable.baseline_credit_card_24)
                .show()
        }
        view.lifecycleOwner = viewLifecycleOwner
        return view.root
    }

    fun validateCard(view: FragmentAddLearningCardBinding): Boolean {
        return if(view.questionInputText.text.toString().isEmpty()) {
            view.question.error = getString(R.string.enter_question)
            view.question.requestFocus()
            true
        } else {
            false
        }
    }
    private fun initEmptyAnswers() {
        val size = viewModel.getAnswers().size-1
        viewModel.reInitAnswers()
        adapter.submitList(viewModel.getAnswers())
        adapter.notifyItemRangeRemoved(1, size)
        adapter.notifyItemChanged(0)
    }

    override fun onAttach(context: Context) {
        val  scope = getKoin().getOrCreateScope(Scopes.ADD_NEW_CARD_SCOPE.scope, named(Scopes.ADD_NEW_CARD_SCOPE.scope))
        themeInfoProvider = scope.get<ThemeInfoProvider>()
        super.onAttach(context)
    }

}
