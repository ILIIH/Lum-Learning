package com.lum.add_new_card.fragments.RuleFragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.lum.add_new_card.R
import com.lum.add_new_card.databinding.FragmentRuleBinding
import com.lum.core.domain.Scopes
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.get
import org.koin.androidx.scope.ScopeFragment
import org.koin.core.qualifier.named

class RuleFragment : ScopeFragment() {

    private lateinit var themeInfoProvider: ThemeInfoProvider
    val ourSession = getKoin().getOrCreateScope(Scopes.ADD_NEW_CARD_SCOPE.scope, named(Scopes.ADD_NEW_CARD_SCOPE.scope))


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view: FragmentRuleBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_rule, container, false)
        configureThemeType(view)

        themeInfoProvider.setThemeId(RuleFragmentArgs.fromBundle(requireArguments()).themeId)
        view.lifecycleOwner = viewLifecycleOwner

        return view.root
    }

    private fun configureThemeType(view: FragmentRuleBinding) {
            ChooseTypeDialog().show(parentFragmentManager, ChooseTypeDialog.CHOOSE_TYPE_DIALOG_TAG)

            themeInfoProvider._themeType.onEach{ type ->
                when (type) {
                    1 -> {
                        view.teacher.ruleText.text =getString(com.lum.core.R.string.meta_mnem_rule)
                        view.subTitle.text = getString(com.lum.core.R.string.meta_mnem_title)
                    }
                    2 -> {
                        view.teacher.ruleText.text =getString(com.lum.core.R.string.learning_card_rule)
                        view.subTitle.text = getString(com.lum.core.R.string.simple_card)
                    }
                    3 -> {
                        view.teacher.ruleText.text =getString(com.lum.core.R.string.visual_mnem_rule)
                        view.subTitle.text = getString(com.lum.core.R.string.visual_title)
                    }
                    4 -> {
                        view.teacher.ruleText.text =getString(com.lum.core.R.string.sound_mnem_rule)
                        view.subTitle.text = getString(com.lum.core.R.string.sound_mnem_title)
                    }
                    else -> {
                        view.teacher.ruleText.text =getString(com.lum.core.R.string.learning_card_rule)
                        view.subTitle.text = getString(com.lum.core.R.string.simple_card)
                    }
                }
                view.teacher.startButton.setOnClickListener {
                    when (type) {
                        1 -> findNavController().navigate(R.id.to_addLearningCardFragment)
                        2 -> findNavController().navigate(R.id.to_addLearningCardFragment)
                        3 -> findNavController().navigate(R.id.to_addVA_Fragment)
                        4 -> findNavController().navigate(R.id.to_addAudioCardFragment)
                        else -> findNavController().navigate(R.id.to_addLearningCardFragment)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onAttach(context: Context) {
        scope.linkTo(ourSession)
        themeInfoProvider = get<ThemeInfoProvider>()
        super.onAttach(context)
    }

}
