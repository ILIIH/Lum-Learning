package com.lum.about_theme_ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.lum.about_theme_ui.AboutThemeNavigation
import com.lum.about_theme_ui.R
import com.lum.about_theme_ui.customView.PieData
import com.lum.about_theme_ui.databinding.FragmentAboutThemeBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt

class AboutThemeFragment : Fragment() {

    private val viewModel: ThemeAboutViewModel by viewModel()
    private val navigator: AboutThemeNavigation by inject()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentAboutThemeBinding.inflate(inflater, container, false)
        val themeId = AboutThemeFragmentArgs.fromBundle(requireArguments()).themeId

        viewModel.loadThemeInfo(themeId)

        val data = PieData()

        data.add(getString(R.string.true_answ), 0.0, getString(R.string.true_answ_color))
        data.add(getString(R.string.wrong_answ), 0.0, getString(R.string.wrong_answ_color))
        data.add(getString(R.string.last_mounth), 100.0, getString(R.string.last_mounth_color))

        view.pieChart.setData(data)

        viewModel._themeInfo.onEach {
            if (it?.AverageRA != 0.0 && it != null ) {
                val dataNotNull = PieData()
                view.pieChart.visibility = View.VISIBLE
                view.emptyStatsImg.visibility = View.GONE

                view.horizontalPrevMonthRight.visibility = View.VISIBLE
                view.horizontalChartWrong.visibility = View.VISIBLE
                view.horizontalChartRight.visibility = View.VISIBLE

                view.rightRate.visibility = View.VISIBLE
                view.wrongRate.visibility = View.VISIBLE
                view.lastMonthWrongAnswers.visibility = View.VISIBLE

                dataNotNull.add(getString(R.string.true_answ), it.AverageRA * 100, getString(R.string.true_answ_color))
                dataNotNull.add(getString(R.string.wrong_answ), 100 - it.AverageRA * 100, getString(
                    R.string.wrong_answ_color
                ))
                dataNotNull.add(getString(R.string.last_mounth), it.AverageLastMonthRA * 100, getString(
                    R.string.last_mounth_color
                ))

                view.pieChart.setData(dataNotNull)

                view.horizontalPrevMonthRight.setData(it.AverageLastMonthRA.toFloat() * 100, Color.parseColor(getString(
                    R.string.last_mounth_color
                )))
                view.horizontalChartWrong.setData(100 - it.AverageRA.toFloat() * 100, Color.parseColor(getString(
                    R.string.wrong_answ_color
                )))
                view.horizontalChartRight.setData(it.AverageRA.toFloat() * 100, Color.parseColor(getString(
                    R.string.true_answ_color
                )))

                view.rightRate.text = "${(it.AverageRA * 100.0).roundToInt()}% \n right answers"
                view.wrongRate.text = "${(100 - it.AverageRA * 100.0).roundToInt()}% \n wrong answers"
                view.lastMonthWrongAnswers.text = "${(it.AverageLastMonthRA * 100.0).roundToInt()}% \n prev month right answers"
            } else {
                view.emptyStatsImg.visibility = View.VISIBLE
            }

            view.themeType.text = it?.themeType
            view.title.text = it?.title
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        view.toTrain.setOnClickListener {
            navigator.toTrain(themeId)
        }

        view.toEdit.setOnClickListener {
            navigator.toEdit(themeId)
        }

        view.createNewItem.setOnClickListener {
            navigator.toCreateNewCard(themeId)
        }

        return view.root
    }
}
