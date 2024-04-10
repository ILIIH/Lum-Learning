package com.lum.formula_builder_ui.customView

import android.graphics.Bitmap

class FormulaOperand(
    val image: Bitmap
) {
    var x: Float = 0F
    var y: Float = 0F
    var needToRedraw = false

    fun setCoordinate(X: Float, Y: Float) {
        this.x = X
        this.y = Y
    }

    fun isNeedToRedraw(x: Float, y: Float) =
        x > this.x && x < this.x + image.width &&
            y > this.y && y < this.y + image.height
}
