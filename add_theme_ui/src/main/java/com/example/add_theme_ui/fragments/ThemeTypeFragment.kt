package com.example.add_theme_ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.add_theme_ui.R
import com.example.add_theme_ui.databinding.FragmentThemeTypeBinding

class ThemeTypeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentThemeTypeBinding.inflate(inflater, container, false)
        return view.root
    }

}