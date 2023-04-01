package com.example.about_theme_ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
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
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAboutThemeBinding.inflate(inflater, container, false)
        data.add("True answ", 20.0, "#FF00A49D")
        data.add("Wrong nasw", 20.0, "#900D09")
        data.add("Triple wrong answ", 60.0, "#000000")

        val themeId = requireArguments().getInt("id")
        viewModel.loadThemeInfo(themeId)

        viewModel._themeInfo.observe(viewLifecycleOwner) {
            view.themeType.text = it.themeType
            view.title.text = it.title
        }

        view.pieChart.setData(data)

        view.horizontalChartTripleWrong.setData(60.0F, Color.parseColor("#000000"))
        view.horizontalChartWrong.setData(20.0F, Color.parseColor("#900D09"))
        view.horizontalChartRight.setData(20.0F, Color.parseColor("#FF00A49D"))

        view.toTrain.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("mnemo_type", viewModel.getMnemoType())
            bundle.putInt("theme_type", viewModel.getThemeType())

            findNavController().navigate(R.id.to_ask_answer_game, bundle)
        }

        view.createNewItem.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", themeId)
            findNavController().navigate(R.id.to_add_new_card, bundle)
        }

        return view.root
    }
}
