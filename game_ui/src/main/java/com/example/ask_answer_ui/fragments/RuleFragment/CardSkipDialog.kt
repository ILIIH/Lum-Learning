package com.example.ask_answer_ui.fragments.RuleFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.ask_answer_ui.databinding.SkipDescriptionDialogBinding


class CardSkipDialog (
    private val setSkipDescription: () -> Unit,
    private val navigateToGame: () -> Unit,
) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = SkipDescriptionDialogBinding.inflate(inflater, container, false)

        binding.answerFalse.setOnClickListener {
            dismiss()
            navigateToGame()
        }
        binding.answerTrue.setOnClickListener {
            dismiss()
            setSkipDescription()
            navigateToGame()
        }
        return binding.root
    }
}