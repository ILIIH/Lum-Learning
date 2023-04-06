package com.example.add_new_card.fragments.AddLearningCard

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.add_new_card.R
import com.example.add_new_card.databinding.FragmentAddLearningCardBinding
import com.example.add_new_card.fragments.RuleFragment.MainFragmentViewModel
import com.example.add_new_card_data.model.Answer
import com.google.android.material.textfield.TextInputLayout
import org.koin.android.ext.android.inject

class AddLearningCardFragment : Fragment() {

    val textFields = ArrayList<TextInputLayout>(13)
    val viewModel: AddLearningCardViewmodel by inject()
    val mainViewModel: MainFragmentViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAddLearningCardBinding.inflate(inflater, container, false)

        textFields.add(view.question)
        textFields.add(view.answer1Discription)
        textFields.add(view.answer1)
        textFields.add(view.answer2Discription)
        textFields.add(view.answer2)
        textFields.add(view.answer3Discription)
        textFields.add(view.answer3)
        textFields.add(view.answer4Discription)
        textFields.add(view.answer4)

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
                val answers = listOf<Answer>(
                    Answer(
                        answer = view.answer1.editText!!.text.toString(),
                        description = view.answer1Discription.editText!!.text.toString(),
                        correct = view.answer1True.isChecked,
                    ),
                    Answer(
                        answer = view.answer2.editText!!.text.toString(),
                        description = view.answer2Discription.editText!!.text.toString(),
                        correct = view.answer2True.isChecked,
                    ),
                    Answer(
                        answer = view.answer3.editText!!.text.toString(),
                        description = view.answer3Discription.editText!!.text.toString(),
                        correct = view.answer3True.isChecked,
                    ),
                    Answer(
                        answer = view.answer4.editText!!.text.toString(),
                        description = view.answer4Discription.editText!!.text.toString(),
                        correct = view.answer4True.isChecked,
                    ),
                )

                AlertDialog.Builder(context)
                    .setTitle("Creation card")
                    .setMessage("Do you want to continue creation or add this card and exit?")
                    .setPositiveButton(
                        getString(R.string.continue_creation),
                    ) { _, _ ->
                        viewModel.addNewCard(
                            themeId = themeID,
                            question = view.question.editText!!.text.toString(),
                            answers,
                        )
                    }
                    .setNegativeButton(
                        R.string.save_and_exit,
                    ) { _, _ ->
                        viewModel.addNewCard(
                            themeId = themeID,
                            question = view.question.editText!!.text.toString(),
                            answers,
                        )
                    }
                    .setIcon(R.drawable.baseline_credit_card_24)
                    .show()
            }
        }

        return view.root
    }
}
