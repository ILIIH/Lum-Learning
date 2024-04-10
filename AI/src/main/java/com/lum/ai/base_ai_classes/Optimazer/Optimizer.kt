package com.lum.ai.base_ai_classes.Optimazer

import com.lum.ai.base_ai_classes.Dense
import com.lum.ai.base_ai_classes.Matrix
import com.lum.ai.base_ai_classes.loss.Loss

abstract class Optimizer {

    abstract fun optimize(layers: Array<Dense>, loss: Loss, y_pred: Matrix, y_true: Matrix): Array<Dense>
    abstract fun resetState()
}
