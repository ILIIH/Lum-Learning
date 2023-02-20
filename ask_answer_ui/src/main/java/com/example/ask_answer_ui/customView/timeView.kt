package com.example.ask_answer_ui.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View

class timeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var backgroundRect: Path = Path()
    private val backgroundColor = Paint()
    private val backgroundClock = Paint()
    private val backgroundTimeIndex = Paint()
    private val rectCircleIndex = RectF()
    private var traitRect: Path = Path()

    val MAX_TRAIT = 17
    var endTime = 100F
    var currentTimeAngle = 180F
    var lineTrait = 0

    init {

        backgroundColor.apply {
            color = Color.BLACK
            alpha = 40
        }
        backgroundClock.apply {
            color = Color.WHITE
        }
        backgroundTimeIndex.apply {
            color = Color.parseColor("#E2E3E5")
        }
    }

    fun setEndingTime(endTime: Float) {
        this.endTime = endTime
        invalidate()
    }
    fun setCurTime(currentTime: Float) {
        this.currentTimeAngle = ((360 * currentTime) / this.endTime)
        val timeAttitude = (100 * currentTime) / this.endTime
        this.lineTrait = -((timeAttitude * MAX_TRAIT) / 100).toInt() + MAX_TRAIT
        if (this.lineTrait > MAX_TRAIT) this.lineTrait = 0
        if (currentTime > this.endTime) this.currentTimeAngle = 0F
        invalidate()
    }

    private fun prepareBackground() {
        val radius = 20F
        backgroundRect.apply {
            moveTo(radius, 0F)
            lineTo(width - radius, 0F)
            arcTo(width - 2 * radius, 0F, width.toFloat(), 2 * radius, -90F, 90F, false)
            lineTo(width.toFloat(), radius)
            arcTo(width - 2 * radius, height - 2 * radius, width.toFloat(), height.toFloat(), 0F, 90F, false)
            lineTo(radius, width.toFloat())
            arcTo(0F, height.toFloat() - 2 * radius, 2 * radius, height.toFloat(), 90F, 90F, false)
            lineTo(0F, radius)
            arcTo(0F, 0F, 2 * radius, 2 * radius, 180F, 90F, false)
        }
    }

    private fun prepareTrait(widthOffset: Float) {
        val radius = 10F
        val heightOffset = 30F

        val rectWith = (width * 3 / 100).toFloat()

        traitRect.apply {
            moveTo(radius + widthOffset, heightOffset)
            lineTo(rectWith + widthOffset - radius, heightOffset)
            arcTo(
                rectWith + widthOffset - 2 * radius,
                heightOffset,
                rectWith + widthOffset,
                2 * radius + heightOffset,
                -90F,
                90F,
                false
            )
            lineTo(rectWith + widthOffset, radius + heightOffset)

            arcTo(rectWith + widthOffset - 2 * radius, height - heightOffset - 2 * radius, rectWith + widthOffset, height.toFloat() - heightOffset, 0F, 90F, false)
            lineTo(widthOffset + radius, height - heightOffset)
            arcTo(widthOffset, height.toFloat() - heightOffset - 2 * radius, 2 * radius + widthOffset, height.toFloat() - heightOffset, 90F, 90F, false)
            lineTo(widthOffset, heightOffset)
            arcTo(widthOffset, heightOffset, 2 * radius + widthOffset, 2 * radius + heightOffset, 180F, 90F, false)
        }
    }

    private fun setTimeIndexOval() {
        rectCircleIndex.apply {
            top = (height / 2).toFloat() - 30F
            left = 70F
            bottom = (height / 2).toFloat() + 30F
            right = 130F
        }
    }
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        prepareBackground()
        val clockRadius = 40F
        val clockSecondRadius = 15F

        canvas?.drawPath(backgroundRect, backgroundColor)
        canvas?.drawCircle(100F, (height / 2).toFloat(), clockRadius, backgroundClock)
        canvas?.drawCircle(70F, (height / 3).toFloat(), clockSecondRadius, backgroundClock)
        canvas?.drawCircle(130F, (height / 3).toFloat(), clockSecondRadius, backgroundClock)

        setTimeIndexOval()

        var traitWithOffset = (width * 20 / 100).toFloat()
        for (i in 0..this.lineTrait) {
            prepareTrait(traitWithOffset)
            canvas?.drawPath(traitRect, backgroundClock)
            traitWithOffset += (width * 3 / 100).toFloat() + (width * 1 / 100).toFloat()
        }

        canvas?.drawArc(rectCircleIndex, -90F, currentTimeAngle, true, backgroundTimeIndex)
    }
}
