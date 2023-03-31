package com.example.ai.base_ai_classes.loss

import com.example.ai.base_ai_classes.Matrix

abstract class Loss {

    abstract fun computeLoss(y_true: Matrix, y_pred: Matrix): Matrix
    abstract fun computeLossGradient(y_true: Matrix, y_pred: Matrix): Matrix
}
