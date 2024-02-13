package com.example.add_theme_ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.add_theme_ui.R
import com.example.add_theme_ui.databinding.FragmentThemeImportanceBinding
import com.example.add_theme_ui.viewModels.ThemeAddViewModel
import org.koin.android.ext.android.inject

class ThemeImportanceFragment : Fragment() {

    private val viewModel: ThemeAddViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThemeImportanceBinding.inflate(inflater,container, false)
        view.highImportanceCard.setOnClickListener {
            viewModel.setThemeImportance(getString(R.string.high))
            findNavController().navigate(R.id.to_themeTypeFragment)
        }
        view.middleLevelCard.setOnClickListener {
            viewModel.setThemeImportance(getString(R.string.middle))
            findNavController().navigate(R.id.to_themeTypeFragment)
        }
        view.lowImportanceCard.setOnClickListener {
            viewModel.setThemeImportance(getString(R.string.low))
            findNavController().navigate(R.id.to_themeTypeFragment)
        }
        return view.root
    }

}