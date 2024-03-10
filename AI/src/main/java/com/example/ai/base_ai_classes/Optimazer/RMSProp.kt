package com.example.ai.base_ai_classes.Optimazer

import com.example.ai.base_ai_classes.Dense
import com.example.ai.base_ai_classes.Matrix
import com.example.ai.base_ai_classes.MatrixOps
import com.example.ai.base_ai_classes.loss.Loss

class RMSProp : Optimizer() {

    var learningRate = 0.001
    var beta = 0.99
    private var S_dw: Matrix? = null
    private var S_db: Matrix? = null
    private var epsilon_1: Matrix? = null
    private var epsilon_2: Matrix? = null

    override fun optimize(layers: Array<Dense>, loss: Loss, y_pred: Matrix, y_true: Matrix): Array<Dense> {
        layers.reverse()
        val dJ_dyN = loss.computeLossGradient(y_true, y_pred)

        layers.forEach { layer -> layer.computeGradients() }

        val dyN_dthetaN = layers[ 0 ].dy_dtheta
        val dthetai_dwi = layers[ 0 ].dtheta_dW

        val dJ_dwN = MatrixOps.dot(dthetai_dwi.transpose(), (dJ_dyN * dyN_dthetaN))

        layers[ 0 ].W = optimize(dJ_dwN, layers[ 0 ].W)
        layers[ 0 ].B = optimize(dJ_dyN * dyN_dthetaN, layers[ 0 ].B)

        var dJ_dtheta = dJ_dyN * dyN_dthetaN
        var dtheta_dX = layers[ 0 ].dtheta_dX

        for (i in 1 until layers.size) {
            val dJ_dyi = MatrixOps.dot(dJ_dtheta, dtheta_dX.transpose())
            val dJ_dthetai = dJ_dyi * layers[ i ].dy_dtheta
            val dJ_dwi = MatrixOps.dot(layers[i].dtheta_dW.transpose(), dJ_dthetai)
            if (S_db == null && S_dw == null) {
                S_dw = MatrixOps.zerosLike(dJ_dwi)
                S_db = MatrixOps.zerosLike(dJ_dthetai)
            }
            S_dw = (S_dw!! * beta) + ((dJ_dwi * dJ_dwi) * (1.0 - beta))
            S_db = (S_db!! * beta) + ((dJ_dthetai * dJ_dthetai) * (1.0 - beta))
            epsilon_1 = MatrixOps.constant(S_dw!!, 10e-8)
            epsilon_2 = MatrixOps.constant(S_db!!, 10e-8)
            layers[ i ].W = optimize(
                dJ_dwi / (MatrixOps.sqrt(S_dw!!) + epsilon_1!!),
                layers[ i ].W,
            )
            layers[ i ].B = optimize(
                dJ_dthetai / (MatrixOps.sqrt(S_db!!) + epsilon_2!!),
                layers[ i ].B,
            )
            dJ_dtheta = dJ_dthetai
            dtheta_dX = layers[ i ].dtheta_dX
        }
        layers.reverse()
        return layers
    }

    private fun optimize(grad: Matrix, param: Matrix): Matrix {
        return param - (grad * learningRate)
    }

    override fun resetState() {
        // TODO : NOT IMPLEMENTED
    }
}
