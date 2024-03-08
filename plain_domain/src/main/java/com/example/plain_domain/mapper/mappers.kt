package com.example.plain_domain.mapper

// Documentation the topology of the neural network for determining the determination of the learning interval

val TRANSITION_MATRIX = intArrayOf(21,18,15,12,9,6,3,0)

fun Int.learningIntervalStart() = TRANSITION_MATRIX[this]

fun Int.learningIntervalEnd() : Int{
    val index = if(this + 1 > TRANSITION_MATRIX.size) 0 else this + 1
    return  TRANSITION_MATRIX[index-1]
}