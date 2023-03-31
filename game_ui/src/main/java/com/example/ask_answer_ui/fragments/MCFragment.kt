package com.example.ask_answer_ui.fragments

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.ask_answer_ui.adapter.AnswerAdapter
import com.example.ask_answer_ui.databinding.FragmentMCBinding
import com.example.ask_answer_ui.viewModel.MC_ViewModel
import org.koin.android.ext.android.inject

class MCFragment : Fragment() {

    val answerAdapter: AnswerAdapter = AnswerAdapter()
    val viewModel: MC_ViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = FragmentMCBinding.inflate(inflater, container, false)

        // Nedd refactoring
        view.answerList.setNestedScrollingEnabled(false)

        view.answerList.adapter = answerAdapter

        view.answerList.isNestedScrollingEnabled = false

        val endTime = 10000L
        view.timeView.setEndingTime(endTime.toFloat())

        val timer = object : CountDownTimer(endTime, 100) {
            override fun onTick(millisUntilFinished: Long) {
                view.timeView.setCurTime(millisUntilFinished.toFloat())
            }

            override fun onFinish() {
                // do something
            }
        }

        viewModel._question.observe(requireActivity()) { question ->
            answerAdapter.submitList(question.answers)
        }

        timer.start()

        return view.root
    }
}
