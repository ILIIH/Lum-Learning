package com.example.ask_answer_data

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R) : ResultOf<R>()
    data class Loading<out R>(val value: R) : ResultOf<R>()
    data class Failure(
        val message: String?,
        val throwable: Throwable?,
    ) : ResultOf<Nothing>()
}
