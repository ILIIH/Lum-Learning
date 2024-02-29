package com.example.add_theme_ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
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

        configureThemeDescriptionBtn(view)
        configureNavigation(view)

        return view.root
    }

    private fun configureThemeDescriptionBtn(view:FragmentThemeTypeBinding) {
        view.openThemeMoreInfoBtn.setOnClickListener {
            configureThemeDescription(view.openThemDescription, view.openThemeMoreInfoBtn)
        }
        view.regularThemeMoreInfoBtn.setOnClickListener {
            configureThemeDescription(view.simpleThemDescription, view.regularThemeMoreInfoBtn)
        }
        view.mixedThemeMoreInfoBtn.setOnClickListener {
            configureThemeDescription(view.mixedThemDescription, view.mixedThemeMoreInfoBtn)
        }
        view.themeFormulaMoreInfoBtn.setOnClickListener {
            configureThemeDescription(view.themFormulaDescription, view.themeFormulaMoreInfoBtn)
        }
    }
    private fun configureThemeDescription(themeDescription: TextView,openDescriptionImg: ImageView  ){
        if(themeDescription.visibility == View.GONE){
            openDescriptionImg.setImageDrawable(ContextCompat.getDrawable(themeDescription.context, R.drawable.close_more_ingo_icon));
            themeDescription.visibility = View.VISIBLE
        }
        else{
            openDescriptionImg.setImageDrawable(ContextCompat.getDrawable(themeDescription.context, R.drawable.more_info_icon));
            themeDescription.visibility = View.GONE
        }
    }
    private fun configureNavigation(view : FragmentThemeTypeBinding){
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
    }

}