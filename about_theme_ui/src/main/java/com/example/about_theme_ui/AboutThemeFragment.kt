package com.example.about_theme_ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.about_theme_ui.customView.PieData
import com.example.about_theme_ui.databinding.FragmentAboutThemeBinding
import org.koin.android.ext.android.inject
import org.koin.java.KoinJavaComponent.inject

class AboutThemeFragment : Fragment() {

    val viewModel: ThemeAboutViewModel by inject()
    val data = PieData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentAboutThemeBinding.inflate(inflater,  container, false)
        data.add("True answ", 20.0, "#FF00A49D")
        data.add("Wrong nasw", 20.0, "#900D09")
        data.add("Triple wrong answ", 40.0, "#000000")

        view.pieChart.setData(data)

        view.horizontalChart.setData(100.0F, Color.parseColor("#000000"))
        return view.root
    }
}
