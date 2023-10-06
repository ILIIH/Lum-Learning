package com.example.ai

import com.example.ai.base_ai_classes.Matrix

val trueResult = Matrix(2, 2, "First Matrix")

fun testMatrixMultiplication() {
    var m1 = Matrix(2, 2, "First Matrix")
    m1.set(0, 0, 2.0)
    m1.set(0, 1, 2.0)
    m1.set(1, 0, 4.0)
    m1.set(1, 1, 1.0)

    var m2 = Matrix(2, 2, "First Matrix")

    m2.set(0, 0, 0.0)
    m2.set(0, 1, 0.0)
    m2.set(1, 0, 10.0)
    m2.set(1, 1, 10.0)

    asserEquals(m1.plus(m2), trueResult)
}

fun asserEquals(m1: Matrix, m2: Matrix) {
}
