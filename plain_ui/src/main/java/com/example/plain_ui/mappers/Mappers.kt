package com.example.plain_ui.mappers

import com.example.plain_ui.customView.Task
import com.example.theme_list_data.Theme
import java.time.LocalDate

fun Theme.toTask() : Task  {
    val now = LocalDate.now()
    return Task(name = this.Title?:String(), now.minusMonths(1) ,now.minusWeeks(1))
}
