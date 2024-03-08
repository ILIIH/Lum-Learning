package com.example.plain_data

import java.time.LocalDate
import java.time.LocalDateTime

data class Task(
    val name: String,
    val dateStart: LocalDateTime,
    val dateEnd: LocalDateTime
)
