package com.example.add_new_card.adapters

import android.animation.ValueAnimator
import android.view.View
import androidx.core.widget.NestedScrollView

class AddCardAnimations {

    fun addTopBardCloseAnimation(view: View, scrollView:NestedScrollView) {
        var isItAnimated = false
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > 400 && !isItAnimated){
                isItAnimated = true
                view.post {
                    val startHeight = view.height
                    val endHeight = startHeight / 1.4 // Change the height as desired
                    val duration = 500L
                    animateViewHeight(view, startHeight, endHeight.toInt(), duration)
                }
            }
        }
    }

    private fun animateViewHeight(view: View, startHeight: Int, endHeight: Int, duration: Long) {
        val animator = ValueAnimator.ofInt(startHeight, endHeight)
        animator.addUpdateListener { valueAnimator ->
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        animator.duration = duration
        animator.start()
    }
}