package com.lum.add_theme_ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.lum.add_theme_ui.R
import com.lum.add_theme_ui.databinding.FragmentThemeTypeBinding
import com.lum.add_theme_ui.viewModels.ThemeAddViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class ThemeTypeFragment : Fragment() {

    private val viewModule: ThemeAddViewModel by sharedViewModel()
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
            viewModule.setThemeType(1)
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
        view.regularTheme.setOnClickListener {
            viewModule.setThemeType(2)
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
        view.themeFormula.setOnClickListener {
            viewModule.setThemeType(3)
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
        view.mixedTheme.setOnClickListener {
            viewModule.setThemeType(4)
            findNavController().navigate(R.id.to_themePhotoFragment)
        }
    }

}