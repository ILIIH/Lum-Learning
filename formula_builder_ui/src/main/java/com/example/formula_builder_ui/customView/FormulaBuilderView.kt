package com.example.formula_builder_ui.customView

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class FormulaBuilderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    val operands = ArrayList<FormulaOperand>(10)
    private var mPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    fun addOperand(bitmap: Bitmap) {
        operands.add(FormulaOperand(bitmap))
        invalidate()
    }

    fun clear() {
        operands.clear()
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        operands.forEach {
            canvas?.drawBitmap(it.image, it.x, it.y, mPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        super.onTouchEvent(event)

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                operands.forEach {
                    if (it.isNeedToRedraw(event.x, event.y)) {
                        it.needToRedraw = true
                        invalidate()
                    }
                }
            }
            MotionEvent.ACTION_MOVE -> {
                operands.forEach {
                    if (it.needToRedraw) {
                        it.setCoordinate(event.x - it.image.width / 2, event.y - it.image.height / 2)
                        invalidate()
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                operands.forEach {
                    it.needToRedraw = false
                }
                invalidate()
            }
        }
        return true
    }
}
