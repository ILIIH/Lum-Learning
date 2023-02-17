package com.example.about_theme_ui.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View

class PieChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    // Data
    private var data: PieData? = null

    // Graphics
    private val borderPaint = Paint()
    private val linePaint = Paint()
    private val indicatorCirclePaint = Paint()
    private var indicatorCircleRadius = 0f
    private val mainTextPaint = Paint()
    private val ovals: MutableList<RectF> = ArrayList<RectF>(10)

    init {
        borderPaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = Color.WHITE
        }
        indicatorCirclePaint.apply {
            style = Paint.Style.FILL
            isAntiAlias = true
            color = Color.LTGRAY
            alpha = 0
        }
        linePaint.apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = Color.LTGRAY
            alpha = 0
        }
        mainTextPaint.apply {
            isAntiAlias = true
            color = Color.BLACK
            alpha = 0
        }
    }

    fun setData(data: PieData) {
        this.data = data
        setPieSliceDimensions()
        invalidate()
    }

    private fun setPieSliceDimensions() {
        var lastAngle = 0f
        data?.pieSlices?.forEach {
            // starting angle is the location of the last angle drawn
            it.value.startAngle = lastAngle
            // sweep angle is determined by multiplying the percentage of the project time with respect
            // to the total time recorded and scaling it to unit circle degrees by multiplying by 360
            it.value.sweepAngle = (((it.value.value / data?.totalValue!!)) * 360f).toFloat()
            lastAngle += it.value.sweepAngle

            setIndicatorLocation(it.key)
        }
    }

    private fun setIndicatorLocation(key: String) {
        data?.pieSlices?.get(key)?.let {
            val middleAngle = it.sweepAngle / 2 + it.startAngle

            it.indicatorCircleLocation.x = (layoutParams.height.toFloat() / 2 - layoutParams.height / 8) *
                Math.cos(Math.toRadians(middleAngle.toDouble())).toFloat() + width / 2
            it.indicatorCircleLocation.y = (layoutParams.height.toFloat() / 2 - layoutParams.height / 8) *
                Math.sin(Math.toRadians(middleAngle.toDouble())).toFloat() + layoutParams.height / 2
        }
    }

    private fun setCircleBounds(
        top: Float = 0f,
        bottom: Float = layoutParams.height.toFloat(),
        left: Float = ((width / 2).toFloat()),
        right: Float = (width / 2).toFloat(),
        value: Int
    ) {
        val random =  (value..value*8).random().toFloat()

        val oval = RectF()

        oval.top = top + random
        oval.bottom = bottom - random
        oval.left = left - (layoutParams.height / 2).toFloat() + random
        oval.right = right + (layoutParams.height / 2).toFloat() - random
        ovals.add(oval)

    }


    private fun setGraphicSizes() {
        mainTextPaint.textSize = height / 15f
        borderPaint.strokeWidth = height / 80f
        linePaint.strokeWidth = height / 120f
        indicatorCircleRadius = height / 70f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setGraphicSizes()
        data?.pieSlices?.forEach {
            setCircleBounds( value = it.value.value.toInt())
            setIndicatorLocation(it.key)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var index = 0
        data?.pieSlices?.let { slices ->
            slices.forEach {
                canvas?.drawArc(ovals[index], it.value.startAngle, it.value.sweepAngle, true, it.value.paint)
                canvas?.drawArc(ovals[index], it.value.startAngle, it.value.sweepAngle, true, borderPaint)
                drawIndicators(canvas, it.value)
                index++
            }
        }
    }

    private fun drawIndicators(canvas: Canvas?, pieItem: PieSlice) {
        // draw line & text for indicator circle if on left side of the pie chart
        if (pieItem.indicatorCircleLocation.x < width / 2) {
            drawIndicatorLine(canvas, pieItem, IndicatorAlignment.LEFT)
            drawIndicatorText(canvas, pieItem, IndicatorAlignment.LEFT)
            // draw line & text for indicator circle if on right side of the pie chart
        } else {
            drawIndicatorLine(canvas, pieItem, IndicatorAlignment.RIGHT)
            drawIndicatorText(canvas, pieItem, IndicatorAlignment.RIGHT)
        }
        // draw indicator circles for pie slice
        canvas?.drawCircle(
            pieItem.indicatorCircleLocation.x,
            pieItem.indicatorCircleLocation.y,
            indicatorCircleRadius,
            indicatorCirclePaint
        )
    }

    private fun drawIndicatorLine(canvas: Canvas?, pieItem: PieSlice, alignment: IndicatorAlignment) {
        val xOffset = if (alignment == IndicatorAlignment.LEFT) width / 4 * -1 else width / 4
        canvas?.drawLine(
            pieItem.indicatorCircleLocation.x,
            pieItem.indicatorCircleLocation.y,
            pieItem.indicatorCircleLocation.x + xOffset,
            pieItem.indicatorCircleLocation.y,
            linePaint
        )
    }

    private fun drawIndicatorText(canvas: Canvas?, pieItem: PieSlice, alignment: IndicatorAlignment) {
        val xOffset = if (alignment == IndicatorAlignment.LEFT) width / 4 * -1 else width / 4
        if (alignment == IndicatorAlignment.LEFT) mainTextPaint.textAlign = Paint.Align.LEFT
        else mainTextPaint.textAlign = Paint.Align.RIGHT
        canvas?.drawText(
            pieItem.name,
            pieItem.indicatorCircleLocation.x + xOffset,
            pieItem.indicatorCircleLocation.y - 10,
            mainTextPaint
        )
    }
}

enum class IndicatorAlignment {
    LEFT, RIGHT
}
