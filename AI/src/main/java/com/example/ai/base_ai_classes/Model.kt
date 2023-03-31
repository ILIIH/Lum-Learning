package com.example.ai.base_ai_classes

import com.example.ai.base_ai_classes.Optimazer.Optimizer
import com.example.ai.base_ai_classes.loss.Loss

class Model(private var inputDims: Int, var layers: Array<Dense>) {

    private var y_pred: Matrix? = null
    private var y_true: Matrix? = null
    private var lossFunction: Loss? = null
    private var optimizer: Optimizer? = null

    fun compile(loss: Loss, optimizer: Optimizer) {
        this.lossFunction = loss
        this.optimizer = optimizer
        var inputDimForLayer = inputDims
        for (i in layers.indices) {
            layers[ i ].initWeights(inputDimForLayer)
            inputDimForLayer = layers[ i ].units
        }
    }

    fun forward(inputs: Matrix, labels: Matrix): Matrix {
        var layerInput = inputs
        for (i in layers.indices) {
            val theta = layers[ i ].forward(layerInput)
            layerInput = theta
        }
        y_pred = layerInput
        y_true = labels
        return y_pred!!
    }

    fun predict(inputs: Matrix): Matrix {
        var layerInput = inputs
        for (i in layers.indices) {
            val theta = layers[ i ].forward(layerInput)
            layerInput = theta
        }
        return layerInput!!
    }

    fun backward() {
        layers = optimizer?.optimize(layers, lossFunction!!, y_pred!!, y_true!!)!!
    }
}
