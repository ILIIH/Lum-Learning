package com.lum.ai.base_ai_classes

import java.lang.Math.exp

class Sigmoid : Activation() {

    override fun call(x: Matrix): Matrix {
        val activation = Matrix(x.m, x.n)
        for (i in 0 until x.m) {
            for (j in 0 until x.n) {
                activation.set(i, j, sigmoid_(x.get(i, j)))
            }
        }
        return activation
    }

    override fun gradient(x: Matrix): Matrix {
        val gradient = Matrix(x.m, x.n)
        for (i in 0 until x.m) {
            for (j in 0 until x.n) {
                gradient.set(i, j, sigmoidGradient_(x.get(i, j)))
            }
        }
        return gradient
    }

    private fun sigmoid_(x: Double): Double {
        return 1.0 / (1.0 + exp(-x))
    }
    private fun sigmoidGradient_(x: Double): Double {
        return sigmoid_(x) * (1.0 - sigmoid_(x))
    }
}
