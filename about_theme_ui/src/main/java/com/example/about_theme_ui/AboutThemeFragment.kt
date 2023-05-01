package com.example.about_theme_ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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

        val themeId = requireArguments().getInt("id")
        viewModel.loadThemeInfo(themeId)

        viewModel._themeInfo.observe(viewLifecycleOwner) {
            Log.i("RA_test", "AverageLastMonthRA = ${it.AverageLastMonthRA.toFloat() * 100}")
            Log.i("RA_test", "wrongRate = ${100 - it.AverageLastMonthRA.toFloat() * 100}")
            Log.i("RA_test", "lastMonthWrongAnswers = ${it.AverageLastMonthRA.toFloat() * 100}")

            if (it.AverageRA != 0.0) {
                data.add("True answ", it.AverageRA * 100, "#FF00A49D")
                data.add("Wrong answ", 100 - it.AverageRA * 100, "#900D09")
                data.add("Triple wrong answ", it.AverageLastMonthRA * 100, "#000000")

                view.pieChart.setData(data)

                view.horizontalChartTripleWrong.setData(it.AverageLastMonthRA.toFloat() * 100, Color.parseColor("#000000"))
                view.horizontalChartWrong.setData(100 - it.AverageRA.toFloat() * 100, Color.parseColor("#900D09"))
                view.horizontalChartRight.setData(it.AverageLastMonthRA.toFloat() * 100, Color.parseColor("#FF00A49D"))

                view.rightRate.text = "${it.AverageLastMonthRA.toFloat() * 100}% right answers"
                view.wrongRate.text = "${100 - it.AverageLastMonthRA.toFloat() * 100}% wrong answers"
                view.lastMonthWrongAnswers.text = "${it.AverageLastMonthRA.toFloat() * 100}% prev month right answers"
            } else {
                Log.i("RA_test", "inside zero")

                data.add("True answ", 280.0, "#FF00A49D")
                data.add("Wrong answ", 80.0, "#900D09")
                data.add("Triple wrong answ", 20.0, "#000000")

                view.pieChart.setData(data)

                view.horizontalChartTripleWrong.setData(0.0F, Color.parseColor("#000000"))
                view.horizontalChartWrong.setData(0.0F, Color.parseColor("#900D09"))
                view.horizontalChartRight.setData(0.0F, Color.parseColor("#FF00A49D"))

                view.rightRate.text = "0% right answers"
                view.wrongRate.text = "0% wrong answers"
                view.lastMonthWrongAnswers.text = "0% prev month right answers"
            }

            view.themeType.text = it.themeType
            view.title.text = it.title
        }


        view.toTrain.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("mnemo_type", viewModel.getMnemoType())
            bundle.putInt("theme_type", viewModel.getThemeType())
            bundle.putInt("id", themeId)

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
