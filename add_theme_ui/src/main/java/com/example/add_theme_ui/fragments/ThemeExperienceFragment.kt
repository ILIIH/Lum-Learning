package com.example.add_theme_ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.add_theme_ui.R
import com.example.add_theme_ui.databinding.FragmentThemeExperienceBinding
import com.example.add_theme_ui.viewModels.ThemeAddViewModel
import org.koin.android.ext.android.inject

class ThemeExperienceFragment : Fragment() {

    private val viewModel: ThemeAddViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThemeExperienceBinding.inflate(inflater, container, false)
        view.beginnerLevelCard.setOnClickListener {
            viewModel.setExperience(0)
            findNavController().navigate(R.id.to_themeImportanceFragment)
        }
        view.middleLevelCard.setOnClickListener {
            viewModel.setExperience(5)
            findNavController().navigate(R.id.to_themeImportanceFragment)
        }
        view.expertLevelCard.setOnClickListener {
            viewModel.setExperience(20)
            findNavController().navigate(R.id.to_themeImportanceFragment)
        }
        requestThemeName()
        return view.root
    }

    fun requestThemeName() {
        ThemeNameFragment().show(parentFragmentManager, ThemeNameFragment.THEME_NAME_FRAGMENT_TAG)
    }

}