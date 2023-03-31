package com.example.ai.base_ai_classes

abstract class Activation {

    abstract fun call(x: Matrix): Matrix
    abstract fun gradient(x: Matrix): Matrix
}
