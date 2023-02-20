package com.example.ask_answer_ui

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import com.example.ask_answer_ui.adapter.AnswerAdapter
import com.example.ask_answer_ui.databinding.FragmentAskAnswerBinding
import com.example.ask_answer_ui.viewModel.askAnswerViewModel
import org.koin.android.ext.android.inject

class AskAnswerFragment : Fragment() {

    val viewModel: askAnswerViewModel by inject()
    val answerAdapter: AnswerAdapter = AnswerAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = FragmentAskAnswerBinding.inflate(inflater, container, false)


        // Nedd refactoring
        view.answerList.adapter = answerAdapter

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

        var needToBeHiden = true
        val transitionListener = object : MotionLayout.TransitionListener {

            override fun onTransitionStarted(p0: MotionLayout?, startId: Int, endId: Int) {
                if (needToBeHiden) {
                    view.assosiationImg.visibility = View.GONE
                    needToBeHiden = false
                } else {
                    view.assosiationImg.visibility = View.VISIBLE
                    needToBeHiden = true
                }
            }

            override fun onTransitionChange(
                p0: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                // nothing to do
            }

            override fun onTransitionCompleted(p0: MotionLayout?, currentId: Int) {
                if (!needToBeHiden) {
                    view.assosiationImg.visibility = View.GONE
                    needToBeHiden = false
                } else {
                    view.assosiationImg.visibility = View.VISIBLE
                    needToBeHiden = true
                }
            }

            override fun onTransitionTrigger(
                p0: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                // not used here
            }
        }

        view.motionLayout.setTransitionListener(transitionListener)

        timer.start()
        return view.root
    }
}
