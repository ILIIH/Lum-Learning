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
import kotlin.math.roundToInt

class AboutThemeFragment : Fragment() {

    val viewModel: ThemeAboutViewModel by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAboutThemeBinding.inflate(inflater, container, false)

        // TODO: change to safe args
        val themeId = requireArguments().getInt("id")
        viewModel.loadThemeInfo(themeId)

        val data = PieData()

        data.add(getString(R.string.true_answ), 0.0, getString(R.string.true_answ_color))
        data.add(getString(R.string.wrong_answ), 0.0, getString(R.string.wrong_answ_color))
        data.add(getString(R.string.last_mounth), 100.0, getString(R.string.last_mounth_color))

        view.pieChart.setData(data)

        viewModel._themeInfo.observe(viewLifecycleOwner) {
            if (it.AverageRA != 0.0) {
                val dataNotNull = PieData()
                view.pieChart.visibility = View.VISIBLE

                view.horizontalPrevMonthRight.visibility = View.VISIBLE
                view.horizontalChartWrong.visibility = View.VISIBLE
                view.horizontalChartRight.visibility = View.VISIBLE

                view.rightRate.visibility = View.VISIBLE
                view.wrongRate.visibility = View.VISIBLE
                view.lastMonthWrongAnswers.visibility = View.VISIBLE

                dataNotNull.add(getString(R.string.true_answ), it.AverageRA * 100, getString(R.string.true_answ_color))
                dataNotNull.add(getString(R.string.wrong_answ), 100 - it.AverageRA * 100, getString(R.string.wrong_answ_color))
                dataNotNull.add(getString(R.string.last_mounth), it.AverageLastMonthRA * 100, getString(R.string.last_mounth_color))

                view.pieChart.setData(dataNotNull)

                view.horizontalPrevMonthRight.setData(it.AverageLastMonthRA.toFloat() * 100, Color.parseColor(getString(R.string.last_mounth_color)))
                view.horizontalChartWrong.setData(100 - it.AverageRA.toFloat() * 100, Color.parseColor(getString(R.string.wrong_answ_color)))
                view.horizontalChartRight.setData(it.AverageRA.toFloat() * 100, Color.parseColor(getString(R.string.true_answ_color)))

                view.rightRate.text = "${(it.AverageRA * 100.0).roundToInt()}% \n right answers"
                view.wrongRate.text = "${(100 - it.AverageRA * 100.0).roundToInt()}% \n wrong answers"
                view.lastMonthWrongAnswers.text = "${(it.AverageLastMonthRA * 100.0).roundToInt()}% \n prev month right answers"
            } else {
                view.emptyStatsImg.visibility = View.VISIBLE
            }

            view.themeType.text = it.themeType.toString()
            view.title.text = it.title
        }

        view.toTrain.setOnClickListener {
            val bundle = Bundle()
            // TODO: change to safe args
            bundle.putInt("mnemo_type", viewModel.getMnemoType())
            bundle.putInt("theme_type", viewModel.getThemeType())
            bundle.putInt("id", themeId)

            findNavController().navigate(R.id.to_ask_answer_game, bundle)
        }

        view.toEdit.setOnClickListener {
            val bundle = Bundle()
            // TODO: change to safe args
            bundle.putInt("id", themeId)
            findNavController().navigate(R.id.to_edit_cards_navgraph, bundle)
        }

        view.createNewItem.setOnClickListener {
            val bundle = Bundle()
            // TODO: change to safe args
            bundle.putInt("id", themeId)
            findNavController().navigate(R.id.to_add_new_card, bundle)
        }

        return view.root
    }
}
