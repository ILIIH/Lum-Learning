package com.example.ask_answer_data

import com.example.core.domain.ILError

sealed class ResultOf<out T> {
    data class Success<out R>(val value: R) : ResultOf<R>()
    data class Loading<out R>(val value: R) : ResultOf<R>()
    data class Failure(val error: ILError?) : ResultOf<Nothing>()
}
