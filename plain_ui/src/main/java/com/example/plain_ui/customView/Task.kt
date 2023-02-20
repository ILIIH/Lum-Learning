package com.example.plain_ui.customView

import java.time.LocalDate

data class Task(
    val name: String,
    val dateStart: LocalDate,
    val dateEnd: LocalDate
)
