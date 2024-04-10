package com.lum.ai.base_ai_classes

class Dense(
    var units: Int,
    var activation: Activation,
    var requiresBias: Boolean = false
) {

    // Inputs to this layer
    lateinit var X: Matrix

    // Weights
    lateinit var W: Matrix

    // Biases
    var B: Matrix = MatrixOps.onesLike(1, units)

    // The output this layer will produce
    lateinit var y: Matrix

    // The value of WX + B
    lateinit var theta: Matrix

    // Some gradients
    lateinit var dy_dtheta: Matrix
    lateinit var dtheta_dW: Matrix
    lateinit var dtheta_dX: Matrix

    fun forward(inputs: Matrix): Matrix {
        X = inputs
        // Check is bias is required
        if (requiresBias) {
            theta = MatrixOps.dot(inputs, W) + B
        } else {
            theta = MatrixOps.dot(inputs, W)
        }
        // Call the activation function
        y = activation.call(theta)
        return y
    }

    // These methods are called by the back propagation algorithm.
    fun initWeights(inputDims: Int) {
        W = MatrixOps.initEmpty(inputDims, units)
    }
    fun computeGradients() {
        dy_dtheta = activation.gradient(theta)
        dtheta_dW = X
        dtheta_dX = W
    }
}
