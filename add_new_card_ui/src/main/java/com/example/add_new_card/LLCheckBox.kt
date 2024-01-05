package com.example.add_new_card

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import java.lang.Integer.max

class LLCheckBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatCheckBox(context, attrs, defStyleAttr) {

    private val checkedDrawable: Drawable
    private val uncheckedDrawable: Drawable
    private val marginBetweenButtonAndText = 20

    init {
        // Initialize your drawables here
        checkedDrawable = ContextCompat.getDrawable(context, R.drawable.checked)!!
        uncheckedDrawable = ContextCompat.getDrawable(context, R.drawable.unchecked)!!

        // Set up a listener to handle checkbox state changes
        setOnCheckedChangeListener { _, _ ->
            // Redraw the checkbox when the state changes
            invalidate()
        }

        // Set up a click listener to handle checkbox clicks
        setOnClickListener {
            // Toggle the checkbox state
            isChecked = !isChecked
            // Redraw the checkbox
            invalidate()
        }

        // Adjust padding to accommodate drawables
        val drawablePadding = marginBetweenButtonAndText +
                max(checkedDrawable.intrinsicWidth, uncheckedDrawable.intrinsicWidth)
        setPadding(
            paddingLeft,
            paddingTop,
            paddingRight + drawablePadding,
            paddingBottom
        )
    }

    override fun onDraw(canvas: Canvas) {
        // Calculate the position to center the drawable with margin
        val drawableX = (width - checkedDrawable.intrinsicWidth) / 2
        val drawableY = (height - checkedDrawable.intrinsicHeight) / 2

        // Draw the appropriate drawable based on the checked state
        if (isChecked) {
            // Draw the checked drawable
            checkedDrawable.setBounds(
                drawableX,
                drawableY,
                drawableX + checkedDrawable.intrinsicWidth,
                drawableY + checkedDrawable.intrinsicHeight
            )
            checkedDrawable.draw(canvas)
        } else {
            // Draw the unchecked drawable
            uncheckedDrawable.setBounds(
                drawableX,
                drawableY,
                drawableX + uncheckedDrawable.intrinsicWidth,
                drawableY + uncheckedDrawable.intrinsicHeight
            )
            uncheckedDrawable.draw(canvas)
        }

        // Draw the text with the desired margin
        val textX = if (isChecked) {
            drawableX + checkedDrawable.intrinsicWidth + marginBetweenButtonAndText
        } else {
            drawableX + uncheckedDrawable.intrinsicWidth + marginBetweenButtonAndText
        }

        val textBounds = Rect()
        paint.getTextBounds(text.toString(), 0, text.length, textBounds)
        val textY = (height - textBounds.height()) / 2 - textBounds.top

        canvas.drawText(text.toString(), textX.toFloat(), textY.toFloat(), paint)
    }
}
