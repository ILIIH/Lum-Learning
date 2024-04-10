package com.lum.ai.base_ai_classes.loss

import com.lum.ai.base_ai_classes.Matrix

class MeanSquaredError : Loss() {

    override fun computeLoss(y_true: Matrix, y_pred: Matrix): Matrix {
        TODO("Not yet implemented")
    }

    override fun computeLossGradient(y_true: Matrix, y_pred: Matrix): Matrix {
        return y_pred - y_true
    }
}
