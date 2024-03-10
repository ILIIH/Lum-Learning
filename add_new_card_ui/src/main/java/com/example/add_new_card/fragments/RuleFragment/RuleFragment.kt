package com.example.add_new_card.fragments.RuleFragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.add_new_card.R
import com.example.add_new_card.databinding.FragmentAddVisualCardBinding
import com.example.add_new_card.databinding.FragmentRuleBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class RuleFragment : Fragment() {

    val themeInfoProvider: ThemeInfoProvider by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: FragmentRuleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rule, container, false)
        configureThemeType(view)
        // TODO: change to safe args
        val themeId = requireArguments().getInt("id")
        themeInfoProvider.setThemeId(themeId)
        view.lifecycleOwner = viewLifecycleOwner
        return view.root
    }

    private fun configureThemeType(view: FragmentRuleBinding) {
        lifecycleScope.launch {
            themeInfoProvider.generatePrediction()
            themeInfoProvider._themeType.observe(viewLifecycleOwner) { type ->
                when (type) {
                    1 -> {
                        view.ruleText.text =getString(com.example.core.R.string.meta_mnem_rule)
                    }
                    2 -> {
                        view.ruleText.text =getString(com.example.core.R.string.learning_card_rule)
                    }
                    3 -> {
                        view.ruleText.text =getString(com.example.core.R.string.visual_mnem_rule)
                    }
                    4 -> {
                        view.ruleText.text =getString(com.example.core.R.string.sound_mnem_rule)
                    }
                    5 -> {
                        view.ruleText.text =getString(com.example.core.R.string.sound_mnem_rule)
                    }
                }
                view.startButton.setOnClickListener {
                    when (type) {
                        1 -> findNavController().navigate(R.id.to_addLearningCardFragment) // TODO: Change approach
                        2 -> findNavController().navigate(R.id.to_addLearningCardFragment)
                        3 -> findNavController().navigate(R.id.to_addVA_Fragment)
                        4 -> findNavController().navigate(R.id.to_addAudioCardFragment)
                        5 -> findNavController().navigate(R.id.to_addLearningCardFragment)
                        6 -> findNavController().navigate(R.id.to_addLearningCardFragment) // TODO: Change approach
                    }
                }
            }
        }
    }
}
