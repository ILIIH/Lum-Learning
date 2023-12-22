package com.example.add_new_card.fragments.AddLearningCard

import android.app.AlertDialog
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.adapters.AnswersAdapters
import com.example.add_new_card.databinding.FragmentAddLearningCardBinding
import com.example.add_new_card.fragments.RuleFragment.ThemeInfoProvider
import com.example.add_new_card.util.hideKeyboard
import com.example.add_new_card_data.model.Answer
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject
import java.util.*

class AddLearningCardFragment : Fragment() {

    val textFields = ArrayList<TextInputLayout>(13)
    val viewModel: AddLearningCardViewmodel by inject()
    val mainViewModel: ThemeInfoProvider by inject()

    val adapter = AnswersAdapters()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAddLearningCardBinding.inflate(inflater, container, false)

        view.answers.adapter = adapter
        adapter.submitList(viewModel.answers)

        view.addNewAnswer.setOnClickListener {
            viewModel.addAnswer()
            adapter.submitList(viewModel.answers)
        }

        val themeType = mainViewModel.getThemeType().value!!
        if (themeType == 5) {
            view.description.visibility = View.VISIBLE
            view.descriptionTextInput.visibility = View.VISIBLE
        }

        textFields.add(view.question)

        val themeID = mainViewModel.getThemeId()

        view.continueBtn.setOnClickListener {
            var isAnyFieldEmpty = false
            textFields.forEach {
                if (it.editText!!.text.isEmpty()) {
                    isAnyFieldEmpty = true
                    it.apply {
                        boxStrokeColor = Color.parseColor("#FF0000")
                    }
                    it.requestFocus()
                }
            }

            if (!isAnyFieldEmpty) {
                val answers = mutableListOf<Answer>()

                AlertDialog.Builder(context)
                    .setTitle("Creation card")
                    .setMessage("Do you want to continue creation or add this card and exit?")
                    .setPositiveButton(
                        getString(R.string.continue_creation),
                    ) { _, _ ->
                        if (themeType == 5) {
                            viewModel.addNewCard(
                                themeId = themeID,
                                question = view.question.editText!!.text.toString(),
                                answers = answers,
                                themeType = themeType,
                                currentDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(
                                    Date(),
                                ),
                                description = view.description.editText!!.text.toString(),
                                monthNumber = Date().month,
                            )
                        } else {
                            viewModel.addNewCard(
                                themeId = themeID,
                                question = view.question.editText!!.text.toString(),
                                answers = answers,
                                themeType = themeType,
                                currentDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(
                                    Date(),
                                ),
                                description = "-",
                                monthNumber = Date().month,
                            )
                        }

                        textFields.forEach {
                            it.editText?.text?.clear()
                        }
                        view.Title.requestFocus()
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
                                currentDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(
                                    Date(),
                                ),
                                description = view.description.editText!!.text.toString(),
                                monthNumber = Date().month,
                            )
                        } else {
                            viewModel.addNewCard(
                                themeId = themeID,
                                question = view.question.editText!!.text.toString(),
                                answers = answers,
                                themeType = themeType,
                                currentDate = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(
                                    Date(),
                                ),
                                description = "-",
                                monthNumber = Date().month,

                            )
                        }
                        hideKeyboard(requireActivity())
                        findNavController().popBackStack()
                    }
                    .setIcon(R.drawable.baseline_credit_card_24)
                    .show()
            }
        }

        return view.root
    }
}
