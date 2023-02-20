package com.example.about_theme_ui.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class horizontalChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val MIDDLE_STICK_WITH = 5F
    val PERCENTAGE_INDEX_WITH = 60F

    var percentage: Float = 0F
    var percentageColor = Color.parseColor("#FF22FF")

    // Graphics
    private val percentageIndexPaint = Paint()
    private val middleStickPaint = Paint()

    init {
        middleStickPaint.apply {
            color = Color.BLACK
            alpha = 100
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val topMiddleStickMagrin = +height / 2 - MIDDLE_STICK_WITH / 2
        val bottomMiddleStickMagrin = height / 2 + MIDDLE_STICK_WITH / 2

        canvas?.drawRect((width * 0.1).toFloat(), topMiddleStickMagrin, (width - width * 0.1).toFloat(), bottomMiddleStickMagrin, middleStickPaint)

        val topPercentageIndexMagrin = +height / 2 - PERCENTAGE_INDEX_WITH / 2
        val bottomPercentageIndexMagrin = height / 2 + PERCENTAGE_INDEX_WITH / 2

        percentageIndexPaint.color = percentageColor
        val percentageMargin = (0.6 * width * percentage) / 100
        canvas?.drawRect((width * 0.2).toFloat(), topPercentageIndexMagrin, (percentageMargin + width * 0.2).toFloat(), bottomPercentageIndexMagrin, percentageIndexPaint)
    }

    fun setData(percentage: Float, color: Int) {
        this.percentage = percentage
        this.percentageColor = color
        invalidate()
    }
}
