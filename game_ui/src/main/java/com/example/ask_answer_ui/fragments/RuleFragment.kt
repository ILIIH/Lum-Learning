package com.example.ask_answer_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.ask_answer_ui.R
import com.example.ask_answer_ui.databinding.FragmentRuleBinding

class RuleFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentRuleBinding.inflate(inflater, container, false)

        val mnemoType = requireArguments().getInt("mnemo_type")
        val themeType = requireArguments().getInt("theme_type")

        view.startButton.setOnClickListener {
            findNavController().navigate(R.id.to_MCFragment)
           // findNavController().navigate(R.id.to_VAFragment)
        }
        return view.root
    }
}
