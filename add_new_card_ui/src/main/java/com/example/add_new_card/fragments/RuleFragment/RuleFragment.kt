package com.example.add_new_card.fragments.RuleFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.databinding.FragmentRuleBinding
import org.koin.android.ext.android.inject

class RuleFragment : Fragment() {

    val themeInfoProvider: ThemeInfoProvider by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentRuleBinding.inflate(inflater, container, false)
        themeInfoProvider.generatePrediction()
        themeInfoProvider._themeType.observe(viewLifecycleOwner) { type ->
            when (type) {
                1 -> {
                    view.ruleText.text =
                        "1) Write down description of the question field. try to write as much information sa possible. This information have not to be true, this is oly your general knowledge test\n\n" +
                        "2) Write down what is it hardest thing in question, why it could be hard exactly to yo to remember this answer\n\n " +
                        "3) Write down how you could use information in this question\n\n " +
                        "4) Answer the question, your time is restricted \n\n "
                }
                2 -> {
                    view.ruleText.text =
                        "1) This is kind of simple learning card but when you will repit this question you will need to provide more specific information about this knowledge area\n\n" +
                        "2) Answer the question, your time is restricted \n\n "
                }
                3 -> {
                    view.ruleText.text =
                        "1) First you will see the photo association on answer to question\n\n" +
                        "2) Answer the question, your time is restricted\n\n "
                }
                4 -> {
                    view.ruleText.text =
                        "1) First you will hear the audio association on answer to question\n\n" +
                        "2) Answer the question, your time is restricted\n\n "
                }
                5 -> {
                    view.ruleText.text =
                        "1) First you will hear the audio association on answer to question\n\n" +
                        "2) Answer the question, your time is restricted\n\n "
                }
            }
            view.startButton.setOnClickListener {
                when (type) {
                    1 -> findNavController().navigate(R.id.to_addLearningCardFragment) // TO_DO_CHANGE
                    2 -> findNavController().navigate(R.id.to_addLearningCardFragment)
                    3 -> findNavController().navigate(R.id.to_addVA_Fragment)
                    4 -> findNavController().navigate(R.id.to_addAudioCardFragment)
                    5 -> findNavController().navigate(R.id.to_addLearningCardFragment)
                    6 -> findNavController().navigate(R.id.to_addLearningCardFragment) // TO_DO_CHANGE
                }
            }
        }

        val themeId = requireArguments().getInt("id")
        themeInfoProvider.setThemeId(themeId)
        return view.root
    }
}
