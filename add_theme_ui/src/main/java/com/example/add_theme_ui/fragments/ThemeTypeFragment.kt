package com.example.add_theme_ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.add_theme_ui.R
import com.example.add_theme_ui.databinding.FragmentThemeTypeBinding
import com.example.add_theme_ui.viewModels.ThemeAddViewModel
import org.koin.android.ext.android.inject

class ThemeTypeFragment : Fragment() {

    private val viewModule: ThemeAddViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = FragmentThemeTypeBinding.inflate(inflater, container, false)

        view.openTheme.setOnClickListener {
            viewModule.setThemeType(getString(R.string.open_theme))
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
        view.regularTheme.setOnClickListener {
            viewModule.setThemeType(getString(R.string.regular_theme))
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
        view.themeFormula.setOnClickListener {
            viewModule.setThemeType(getString(R.string.theme_formula))
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
        view.mixedTheme.setOnClickListener {
            viewModule.setThemeType(getString(R.string.theme_mixed))
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
        return view.root
    }

}